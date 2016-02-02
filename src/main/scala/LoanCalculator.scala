
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
import model.DatiFattura

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
				Option(datiFatturazione.dataFatt).getOrElse(DateTime.now()).toString("dd MMM yyyy")
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
	
	def fattura(wb: Workbook, sheet: Sheet, datiFatturazione:DatiFattura)(implicit styles: Map[String, CellStyle]):Workbook = {
	  //Data	Rif.	Prestazione					GG	€/unit.	EURO

	  row(12,8,13,sheet,Option(styles.get("fattura_head")),Seq("Data","Rif.","Prestazione","GG","€/Unit","Euro"),false)
	
	  
	  wb
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
		  counter=counter+1
		}

		titleRow
	} 

	def main(args: Array[String]) {
		var wb: Workbook = null
				wb = if (args.length > 0 && args(0) == "-xls") new HSSFWorkbook() else new XSSFWorkbook()
	implicit val styles = createStyles(wb)
	val sheet = wb.createSheet("Loan Calculator")
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
	  sheet.setColumnWidth(i, 15 * 256)
	}
	createNames(wb)
	/*val titleRow = sheet.createRow(0)
	titleRow.setHeightInPoints(35)
	var i = 1
	while (i <= 7) {
		titleRow.createCell(i).setCellStyle(styles.get("title"))
		i += 1
	}
	val titleCell = titleRow.getCell(2)
			titleCell.setCellValue("Simple Loan Calculator")
			sheet.addMergedRegion(CellRangeAddress.valueOf("$C$1:$H$1"))*/
	intestazione(wb, sheet)
	dati(wb, sheet, new DatiFatturazione(
			DateTimeFormat.forPattern("yyyyMMdd").parseDateTime("20160131") ,
			1,
			"Consulenza Gennaio ALTEN",
			3586.80,
			"rimessa diretta"
			))
			destinatario(wb,sheet,new Persona(
					"Spettabile",
					"ULIXE TECHNOLOGIES MILANO SRL",
					"Corso Italia 7/Bis",
					"Busto Arsizio (Va)",
					"21052",
					"03359310129"

					))

					fattura(wb,sheet,new DatiFattura(
							Set(new Prestazione(
							  DateTimeFormat.forPattern("yyyyMMdd").parseDateTime("20160131") ,
							  "consulenza",
							  "Consulenza Gennaio",
							  14,
							  210									
							  ))    
							))
							/*var row = sheet.createRow(2)
	var cell = row.createCell(4)
	cell.setCellValue("Enter values")
	cell.setCellStyle(styles.get("item_right"))
	row = sheet.createRow(3)
	cell = row.createCell(2)
	cell.setCellValue("Loan amount")
	cell.setCellStyle(styles.get("item_left"))
	cell = row.createCell(4)
	cell.setCellStyle(styles.get("input_$"))
	cell.setAsActiveCell()
	row = sheet.createRow(4)
	cell = row.createCell(2)
	cell.setCellValue("Annual interest rate")
	cell.setCellStyle(styles.get("item_left"))
	cell = row.createCell(4)
	cell.setCellStyle(styles.get("input_%"))
	row = sheet.createRow(5)
	cell = row.createCell(2)
	cell.setCellValue("Loan period in years")
	cell.setCellStyle(styles.get("item_left"))
	cell = row.createCell(4)
	cell.setCellStyle(styles.get("input_i"))
	row = sheet.createRow(6)
	cell = row.createCell(2)
	cell.setCellValue("Start date of loan")
	cell.setCellStyle(styles.get("item_left"))
	cell = row.createCell(4)
	cell.setCellStyle(styles.get("input_d"))
	row = sheet.createRow(8)
	cell = row.createCell(2)
	cell.setCellValue("Monthly payment")
	cell.setCellStyle(styles.get("item_left"))
	cell = row.createCell(4)
	cell.setCellFormula("IF(Values_Entered,Monthly_Payment,\"\")")
	cell.setCellStyle(styles.get("formula_$"))
	row = sheet.createRow(9)
	cell = row.createCell(2)
	cell.setCellValue("Number of payments")
	cell.setCellStyle(styles.get("item_left"))
	cell = row.createCell(4)
	cell.setCellFormula("IF(Values_Entered,Loan_Years*12,\"\")")
	cell.setCellStyle(styles.get("formula_i"))
	row = sheet.createRow(10)
	cell = row.createCell(2)
	cell.setCellValue("Total interest")
	cell.setCellStyle(styles.get("item_left"))
	cell = row.createCell(4)
	cell.setCellFormula("IF(Values_Entered,Total_Cost-Loan_Amount,\"\")")
	cell.setCellStyle(styles.get("formula_$"))
	row = sheet.createRow(11)
	cell = row.createCell(2)
	cell.setCellValue("Total cost of loan")
	cell.setCellStyle(styles.get("item_left"))
	cell = row.createCell(4)
	cell.setCellFormula("IF(Values_Entered,Monthly_Payment*Number_of_Payments,\"\")")
	cell.setCellStyle(styles.get("formula_$"))*/
							var file = "loan-calculator"+System.currentTimeMillis+".xls"
							if (wb.isInstanceOf[XSSFWorkbook]) file += "x"
							val out = new FileOutputStream(file)
	wb.write(out)
	out.close()
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

	def createNames(wb: Workbook) {
		var name: Name = null
				name = wb.createName()
				name.setNameName("Interest_Rate")
				name.setRefersToFormula("'Loan Calculator'!$E$5")
				name = wb.createName()
				name.setNameName("Loan_Amount")
				name.setRefersToFormula("'Loan Calculator'!$E$4")
				name = wb.createName()
				name.setNameName("Loan_Start")
				name.setRefersToFormula("'Loan Calculator'!$E$7")
				name = wb.createName()
				name.setNameName("Loan_Years")
				name.setRefersToFormula("'Loan Calculator'!$E$6")
				name = wb.createName()
				name.setNameName("Number_of_Payments")
				name.setRefersToFormula("'Loan Calculator'!$E$10")
				name = wb.createName()
				name.setNameName("Monthly_Payment")
				name.setRefersToFormula("-PMT(Interest_Rate/12,Number_of_Payments,Loan_Amount)")
				name = wb.createName()
				name.setNameName("Total_Cost")
				name.setRefersToFormula("'Loan Calculator'!$E$12")
				name = wb.createName()
				name.setNameName("Total_Interest")
				name.setRefersToFormula("'Loan Calculator'!$E$11")
				name = wb.createName()
				name.setNameName("Values_Entered")
				name.setRefersToFormula("IF(Loan_Amount*Interest_Rate*Loan_Years*Loan_Start>0,1,0)")
	}
}
