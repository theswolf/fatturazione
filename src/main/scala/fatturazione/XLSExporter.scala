package fatturazione

import org.apache.poi.xssf.usermodel._
import org.apache.poi.ss.usermodel._
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.util.Map
import java.util.HashMap
import java.io.FileOutputStream
import scala.collection.JavaConversions._
import model.DatiFatturazione
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.DateTime
import model.DatiFatturazione
import org.joda.time.format.DateTimeFormat
import model.Persona
import model.Persona
import model.Prestazione


object LoanCalculator {

	def intestazione(wb: Workbook, sheet: Sheet)(implicit styles: Map[String, CellStyle]):Workbook = {
		//val titleRow = sheet.createRow(0)
		row(0,1,10,sheet,Option(styles.get("title")),"Geymonat Christian",true)
		row(1,1,10,sheet,Option(styles.get("subtitle")),"Regione Paschetti 1, 10060 – Garzigliana ( TO )",true)
		row(2,1,10,sheet,Option(styles.get("subtitle")),"Cell. 3492109906",true)
		row(3,1,10,sheet,Option(styles.get("subtitle")),"P.IVA  09951550012 -C.Fisc. GYMCRS81P22G674W",true)
		row(3,11,15,sheet,Option(styles.get("simpleBold")),"FATTURA / PARCELLA",true)

		wb
	}

	def dati(wb: Workbook, sheet: Sheet, datiFatturazione:DatiFatturazione)(implicit styles: Map[String, CellStyle]):Workbook = {
		//val titleRow = sheet.createRow(0)
		row(5,1,1,sheet,Option(styles.get("item_left")),"Data Fatt.",true)
		row(5,2,2,sheet,Option(styles.get("item_rigth")),
				Option(new DateTime(datiFatturazione.dataFatt)).getOrElse(DateTime.now()).toString("dd MMM yyyy")
				,true)

    row(6,1,2,sheet,Option(styles.get("item_left_bold")),"Num Fatt.",true)
		row(6,2,3,sheet,Option(styles.get("item_rigth")),
						datiFatturazione.numFatt.toString()
						,true)

		row(7,1,2,sheet,Option(styles.get("item_left_bold")),"Riferim. ",true)
		row(7,2,3,sheet,Option(styles.get("item_rigth")),
								datiFatturazione.riferim
								,true)

		row(8,1,2,sheet,Option(styles.get("item_left_bold")),"Data Scad. ",true)
		row(8,2,3,sheet,Option(styles.get("item_rigth")),
										datiFatturazione.dataScad
										,true)

		wb
	}

	def modalitaPagamento(wb: Workbook, sheet: Sheet)(implicit styles: Map[String, CellStyle]):Workbook = {
		row(28,8,10,sheet,Option(styles.get("orange")),"MODALITA' PAGAMENTO",false)
		row(29,8,10,sheet,Option(styles.get("orange")),"rimessa diretta con",false)
		row(30,8,10,sheet,Option(styles.get("orange")),"Bonifico bancario sul  C/C",false)
		row(31,8,10,sheet,Option(styles.get("orange")),"n. 000100748266",false)
		row(32,8,10,sheet,Option(styles.get("orange")),"UniCredit – S.Secondo di Pinerolo",false)
		row(33,8,10,sheet,Option(styles.get("orange")),"IBAN: IT87M0200830950000100748266",false)
		wb
	}

	def riepilogo(wb: Workbook, sheet: Sheet, datiFatturazione:DatiFatturazione)(implicit styles: Map[String, CellStyle]):Workbook = {
		val totImponibile = imponibile(datiFatturazione)
				val iva = (22 * totImponibile) / 100;
		row(28,11,13,sheet,Option(styles.get("yellow")),Seq("TOTALE IMPONIBILE","","€ "+((d:Double) => f"$d%1.2f")(totImponibile)),false)
		row(29,11,13,sheet,Option(styles.get("yellow")),Seq("IVA","22%","€ "+((d:Double) => f"$d%1.2f")(iva)),false)
		row(30,11,13,sheet,Option(styles.get("yellow")),Seq("TOTALE FATTURA","","€ "+((d:Double) => f"$d%1.2f")(totImponibile+iva)),false)
		row(33,11,13,sheet,Option(styles.get("yellow_bold")),Seq("NETTO DA VERSARE","","€ "+((d:Double) => f"$d%1.2f")(totImponibile+iva)),false)

		wb
	}

	def imponibile(d: DatiFatturazione):Double = {(d.prestazioni.map { p => p.euro() }(collection.breakOut): List[(Double)]).sum}

	def fattura(wb: Workbook, sheet: Sheet, datiFatturazione:DatiFatturazione)(implicit styles: Map[String, CellStyle]):Workbook = {
		val startrow = 12
				val height = 15
				val startcol=8
				val width=5
				row(startrow,startcol,startcol+width,sheet,Option(styles.get("fattura_head")),Seq("Data","Rif.","Prestazione","GG","€/Unit","Euro"),false)
				var counter = 1;
		datiFatturazione.prestazioni.map { p =>  

		row(startrow+counter,startcol,startcol+width,sheet,Option(styles.get("fattura")),Seq(new DateTime(p.data).toString("dd/MM/yyyy"),p.rif,p.prestazione,p.gg,p.euroUnit,"€ "+((d:Double) => f"$d%1.2f")(p.euro)),false)
		counter+=1
		}

		for(i <- (1 to height-1)) {
			row(startrow+i,startcol,startcol+width,sheet,Option(styles.get("fattura")),Seq(),false)
		} 

		row(startrow+height,startcol,startcol+width,sheet,Option(styles.get("fattura_head")),
				Seq("","","IMPONIBILE","","","€ "+((d:Double) => f"$d%1.2f")(imponibile(datiFatturazione)))
				,false)

				aroundBorder(sheet, startrow, startrow+height, startcol, startcol+width)
				wb
	}

	def aroundBorder(sheet:Sheet,start:Int,height:Int,left:Int,right:Int):Unit = {

		for(rownum <- (start to height)) { 
			var r = sheet.getRow(rownum)
					println(s"row $rownum")
					rownum match {
					case `start` => aroundBorderSide(r,left,right,0)
					case `height`  => aroundBorderSide(r,left,right,2)
					case _ => aroundBorderSide(r,left,right,-1)
			}
		}

	}

	def aroundBorderSide(r:Row,left:Int,right:Int,border:Short):Unit = {

		for( col <- (left to right)) {
			var c =  r.getCell(col)
					applyStyle(c,border)

					col match {
					case `left` =>  applyStyle(c,3)
					case `right` => applyStyle(c,1)
					case _ =>
			}

		}
	}

	def applyStyle(c:Cell,direction:Short) {
		//top right bottom left
		var style = c.getCellStyle
				direction match {
				case 0 => style.setBorderTop(CellStyle.BORDER_HAIR)
						//case 1 =>  style.setBorderRight(CellStyle.BORDER_THIN)
				case 2 => style.setBorderBottom(CellStyle.BORDER_HAIR)
				//case 3 =>  style.setBorderLeft(CellStyle.BORDER_THIN)
				case _ => 
		}
	}

	def destinatario(wb: Workbook, sheet: Sheet, pf:Persona)(implicit styles: Map[String, CellStyle]):Workbook = {
		//val titleRow = sheet.createRow(0)
		row(5,11,11,sheet,Option(styles.get("item_left")),Option(pf.sigla).getOrElse(""),true)
		row(6,11,11,sheet,Option(styles.get("item_left")),Option(pf.denominazione).getOrElse(""),true)

		row(8,11,11,sheet,Option(styles.get("item_left")),Option(pf.via).getOrElse(""),true)
		row(9,11,11,sheet,Option(styles.get("item_left")),Option(pf.cap).getOrElse("")+" "+Option(pf.indirizzo).getOrElse(""),true)

		row(10,11,11,sheet,Option(styles.get("item_left")),"P.IVA",true)
		row(10,12,12,sheet,Option(styles.get("item_left")),Option(pf.pIva).getOrElse(""),true)


		wb
	}


	def row(rownum: Int, from: Int, to:Int, sheet: Sheet,style: Option[CellStyle],value: Any,space:Boolean) : Row =  {
		row(rownum,from,to,sheet,style,Seq(value),space)
	}

	def row(rownum: Int, from: Int, to:Int, sheet: Sheet,style: Option[CellStyle],values: Seq[Any],space:Boolean) : Row = {
		implicit var titleRow = Option(sheet.getRow(rownum)) match {
		case None => sheet.createRow(rownum)
		case Some(s) => s
		}

		val getCell = (num:Int) => {
			Option(implicitly[Row].getCell(num)) match {
			case None =>  implicitly[Row].createCell(num)
			case Some(c) => c
			}
		}

		titleRow.setHeightInPoints(20)
		for (i <- from to ((from: Int,to:Int) => to match {
		case `from` => to+1
		case _ => to
		})(from,to)) {
			style match {
			case Some(s) => getCell(i).setCellStyle(s)
			case None =>  getCell(i)
			}

		}

		var counter:Int = space match {
		case true => 1
		case false => 0
		}

		for(i <- values) {
			getCell(from+counter).setCellValue(
					//i.getOrElse("").toString()
					Option(i) match {
					case None => ""
					case Some(s) => s.toString()
					}
					)
					counter+=1
		}

		titleRow
	} 
	
	def exportToXls(datiFatturazione:DatiFatturazione,dest:Persona) = {
	  
	var  wb = new XSSFWorkbook()
	implicit val styles = createStyles(wb)
	val sheet = wb.createSheet("Fattura")
	sheet.setPrintGridlines(false)
	sheet.setDisplayGridlines(false)
	val printSetup = sheet.getPrintSetup
	printSetup.setLandscape(true)
	sheet.setFitToPage(true)
	sheet.setHorizontallyCenter(true)
	for (i:Int <- (0 to 6)) {
		i match {
		case 0 | 1 => sheet.setColumnWidth(i, 3 * 256)
		case 2 => sheet.setColumnWidth(i, 11 * 256)
		case _ => sheet.setColumnWidth(i, 1 * 256)
		}
	}

	for(i <- (8 to 13)) {
		sheet.setColumnWidth(i, 20 * 256)
	}


	    intestazione(wb, sheet)
	  dati(wb, sheet, datiFatturazione)
	  destinatario(wb, sheet, dest)
    fattura(wb,sheet,datiFatturazione)
    modalitaPagamento(wb,sheet)
	  riepilogo(wb,sheet,datiFatturazione)

	 var file = "loan-calculator"+System.currentTimeMillis+".xls"
	  if (wb.isInstanceOf[XSSFWorkbook]) file += "x"
	  val out = new FileOutputStream(file)
	  wb.write(out)
	  out.close()
	  
	}

	def main(args: Array[String]) {
	



					
			
	val datiFatturazione = new DatiFatturazione(
					DateTimeFormat.forPattern("yyyyMMdd").parseDateTime("20160131").toDate() ,
					1,
					"Consulenza Gennaio ALTEN",
					3586.80,
					"rimessa diretta"
					)
					
		val destinatario = new Persona(
							"Spettabile",
							"ULIXE TECHNOLOGIES MILANO SRL",
							"Corso Italia 7/Bis",
							"Busto Arsizio (Va)",
							"21052",
							"03359310129"

							)

			exportToXls(datiFatturazione, destinatario)
	}

	private def createStyles(wb: Workbook): Map[String, CellStyle] = {
			val styles = new HashMap[String, CellStyle]()
					var style: CellStyle = null
					val titleFont = wb.createFont()
					titleFont.setFontHeightInPoints(14.toShort)
					titleFont.setFontName("Trebuchet MS")
					style = wb.createCellStyle()
					style.setFont(titleFont)
					style.setBorderBottom(CellStyle.BORDER_DOTTED)
					style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
					styles.put("title", style)

					val subtitleFont = wb.createFont()
					subtitleFont.setFontHeightInPoints(9.toShort)
					subtitleFont.setFontName("Trebuchet MS")
					style = wb.createCellStyle()
					style.setFont(subtitleFont)
					style.setBorderBottom(CellStyle.BORDER_DOTTED)
					style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
					styles.put("subtitle", style)

					val simpleBold = wb.createFont()
					simpleBold.setFontHeightInPoints(9.toShort)
					simpleBold.setFontName("Trebuchet MS")
					simpleBold.setBold(true)
					style = wb.createCellStyle()
					style.setFont(simpleBold)
					styles.put("simpleBold", style)

					val itemFont = wb.createFont()
					itemFont.setFontHeightInPoints(9.toShort)
					itemFont.setFontName("Trebuchet MS")
					style = wb.createCellStyle()
					style.setAlignment(CellStyle.ALIGN_LEFT)
					style.setFont(itemFont)
					styles.put("item_left", style)

					val itemFontBold = wb.createFont()
					itemFontBold.setFontHeightInPoints(9.toShort)
					itemFontBold.setFontName("Trebuchet MS")
					itemFontBold.setBold(true)
					style = wb.createCellStyle()
					style.setAlignment(CellStyle.ALIGN_LEFT)
					style.setFont(itemFont)
					styles.put("item_left_bold", style)

					style = wb.createCellStyle()
					style.setAlignment(CellStyle.ALIGN_RIGHT)
					style.setFont(itemFont)
					styles.put("item_right", style)

					style = wb.createCellStyle()
					style.setAlignment(CellStyle.ALIGN_LEFT)
					style.setFont(itemFont)
					style.setBorderRight(CellStyle.BORDER_DOTTED)
					style.setBorderLeft(CellStyle.BORDER_DOTTED)
					styles.put("fattura", style)

					style = wb.createCellStyle()
					style.setAlignment(CellStyle.ALIGN_CENTER)
					style.setFont(itemFont)
					style.setBorderRight(CellStyle.BORDER_DOTTED)
					style.setBorderLeft(CellStyle.BORDER_DOTTED)
					//style.setFillBackgroundColor(IndexedColors.BLUE.getIndex)
					style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex)
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styles.put("fattura_head", style)

			style = wb.createCellStyle()
			style.setAlignment(CellStyle.ALIGN_LEFT)
			style.setFont(itemFont)
			//style.setBorderRight(CellStyle.BORDER_DOTTED)
			//style.setBorderLeft(CellStyle.BORDER_DOTTED)
			//style.setFillBackgroundColor(IndexedColors.BLUE.getIndex)
			style.setFillForegroundColor(IndexedColors.ORANGE.getIndex)
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styles.put("orange", style)

			style = wb.createCellStyle()
			style.setAlignment(CellStyle.ALIGN_RIGHT)
			style.setFont(itemFont)
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex)
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styles.put("yellow", style)

			style = wb.createCellStyle()
			style.setAlignment(CellStyle.ALIGN_RIGHT)
			style.setFont(simpleBold)
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex)
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styles.put("yellow_bold", style)

			style = wb.createCellStyle()
			style.setAlignment(CellStyle.ALIGN_RIGHT)
			style.setFont(itemFont)
			style.setBorderRight(CellStyle.BORDER_DOTTED)
			style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderBottom(CellStyle.BORDER_DOTTED)
			style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderLeft(CellStyle.BORDER_DOTTED)
			style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderTop(CellStyle.BORDER_DOTTED)
			style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setDataFormat(wb.createDataFormat().getFormat("_($* #,##0.00_);_($* (#,##0.00);_($* \"-\"??_);_(@_)"))
			styles.put("input_$", style)
			style = wb.createCellStyle()
			style.setAlignment(CellStyle.ALIGN_RIGHT)
			style.setFont(itemFont)
			style.setBorderRight(CellStyle.BORDER_DOTTED)
			style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderBottom(CellStyle.BORDER_DOTTED)
			style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderLeft(CellStyle.BORDER_DOTTED)
			style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderTop(CellStyle.BORDER_DOTTED)
			style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setDataFormat(wb.createDataFormat().getFormat("0.000%"))
			styles.put("input_%", style)
			style = wb.createCellStyle()
			style.setAlignment(CellStyle.ALIGN_RIGHT)
			style.setFont(itemFont)
			style.setBorderRight(CellStyle.BORDER_DOTTED)
			style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderBottom(CellStyle.BORDER_DOTTED)
			style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderLeft(CellStyle.BORDER_DOTTED)
			style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderTop(CellStyle.BORDER_DOTTED)
			style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setDataFormat(wb.createDataFormat().getFormat("0"))
			styles.put("input_i", style)
			style = wb.createCellStyle()
			style.setAlignment(CellStyle.ALIGN_CENTER)
			style.setFont(itemFont)
			style.setDataFormat(wb.createDataFormat().getFormat("m/d/yy"))
			styles.put("input_d", style)
			style = wb.createCellStyle()
			style.setAlignment(CellStyle.ALIGN_RIGHT)
			style.setFont(itemFont)
			style.setBorderRight(CellStyle.BORDER_DOTTED)
			style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderBottom(CellStyle.BORDER_DOTTED)
			style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderLeft(CellStyle.BORDER_DOTTED)
			style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderTop(CellStyle.BORDER_DOTTED)
			style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setDataFormat(wb.createDataFormat().getFormat("$##,##0.00"))
			style.setBorderBottom(CellStyle.BORDER_DOTTED)
			style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex)
			style.setFillPattern(CellStyle.SOLID_FOREGROUND)
			styles.put("formula_$", style)
			style = wb.createCellStyle()
			style.setAlignment(CellStyle.ALIGN_RIGHT)
			style.setFont(itemFont)
			style.setBorderRight(CellStyle.BORDER_DOTTED)
			style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderBottom(CellStyle.BORDER_DOTTED)
			style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderLeft(CellStyle.BORDER_DOTTED)
			style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setBorderTop(CellStyle.BORDER_DOTTED)
			style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setDataFormat(wb.createDataFormat().getFormat("0"))
			style.setBorderBottom(CellStyle.BORDER_DOTTED)
			style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex)
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex)
			style.setFillPattern(CellStyle.SOLID_FOREGROUND)
			styles.put("formula_i", style)
			styles
	}

	
}
