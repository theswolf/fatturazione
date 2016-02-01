
import org.apache.poi.xssf.usermodel._
import org.apache.poi.ss.usermodel._
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.util.Map
import java.util.HashMap
import java.io.FileOutputStream
//remove if not needed
import scala.collection.JavaConversions._

object LoanCalculator {

	def intestazione(wb: Workbook, sheet: Sheet, styles: Map[String, CellStyle]):Workbook = {
			//val titleRow = sheet.createRow(0)
			row(0,1,5,sheet,styles,"Geymonat Christian",None)
			/*var titleRow = sheet.createRow(0)
					titleRow.setHeightInPoints(35)
					//var i = 1

					for (i <- 1 to 5) {
						titleRow.createCell(i).setCellStyle(styles.get("title"))
					}

			var titleCell = titleRow.getCell(2)
					titleCell.setCellValue("Simple Loan Calculator")
					sheet.addMergedRegion(CellRangeAddress.valueOf("$C$1:$F$1"))
			 */

			wb
	}

	def row(rownum: Int, from: Int, to:Int, sheet: Sheet,styles: Map[String, CellStyle],label: String,value: Option[Any]) : Row = {
			var titleRow = sheet.createRow(rownum)
					titleRow.setHeightInPoints(35)
					for (i <- from to to) {
						titleRow.createCell(i).setCellStyle(styles.get("title"))
					}
			var titleCell = titleRow.getCell(2)
					titleCell.setCellValue(label)
					//sheet.addMergedRegion(CellRangeAddress.valueOf("$C$1:$F$1"))
					titleCell = titleRow.getCell(2+to)
	
					value match {
					  case None           => Unit
					  case Some(_) => titleCell.setCellValue(value.toString())
					  
					}
					titleRow
	} 

	def main(args: Array[String]) {
		var wb: Workbook = null
				wb = if (args.length > 0 && args(0) == "-xls") new HSSFWorkbook() else new XSSFWorkbook()
	val styles = createStyles(wb)
	val sheet = wb.createSheet("Loan Calculator")
	sheet.setPrintGridlines(false)
	sheet.setDisplayGridlines(false)
	val printSetup = sheet.getPrintSetup
	printSetup.setLandscape(true)
	sheet.setFitToPage(true)
	sheet.setHorizontallyCenter(true)
	sheet.setColumnWidth(0, 3 * 256)
	sheet.setColumnWidth(1, 3 * 256)
	sheet.setColumnWidth(2, 11 * 256)
	sheet.setColumnWidth(3, 14 * 256)
	sheet.setColumnWidth(4, 14 * 256)
	sheet.setColumnWidth(5, 14 * 256)
	sheet.setColumnWidth(6, 14 * 256)
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
	intestazione(wb, sheet,styles)
	var row = sheet.createRow(2)
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
	cell.setCellStyle(styles.get("formula_$"))
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
					val itemFont = wb.createFont()
					itemFont.setFontHeightInPoints(9.toShort)
					itemFont.setFontName("Trebuchet MS")
					style = wb.createCellStyle()
					style.setAlignment(CellStyle.ALIGN_LEFT)
					style.setFont(itemFont)
					styles.put("item_left", style)
					style = wb.createCellStyle()
					style.setAlignment(CellStyle.ALIGN_RIGHT)
					style.setFont(itemFont)
					styles.put("item_right", style)
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
