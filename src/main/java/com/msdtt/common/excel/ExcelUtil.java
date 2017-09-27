package com.msdtt.common.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhuxd@wjs.com
 * @date 2017/9/26 15:52
 */
public class ExcelUtil{

	private static XSSFFormulaEvaluator evaluator;

	public static List<List<String>> readXlsx(String path){
		List<List<String>> targetList = new ArrayList<List<String>>();
		XSSFWorkbook xssfWorkbook = null;
		try{
			InputStream is = new FileInputStream(path);
			xssfWorkbook = new XSSFWorkbook(is);
		}catch(IOException e){
			e.printStackTrace();
		}
		evaluator = new XSSFFormulaEvaluator(xssfWorkbook);
		if(xssfWorkbook != null){
			for(int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++){
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if(xssfSheet == null){
					continue;
				}
				for(int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++){
					List<String> list = new ArrayList<String>();
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if(xssfRow != null){
						for(Cell cell : xssfRow){
							list.add(getValue((XSSFCell) cell));
						}
					}
					targetList.add(list);
				}
			}
		}
		return targetList;
	}

	private static String getValue(XSSFCell cell){
		switch(cell.getCellTypeEnum()){
			case _NONE:
				return null;
			case BLANK:
				return "";
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case ERROR:
				return String.valueOf(cell.getErrorCellValue());
			case FORMULA:
				CellValue cellValue = evaluator.evaluate(cell);
				switch(cellValue.getCellTypeEnum()){
					case _NONE:
						return null;
					case BLANK:
						return "";
					case BOOLEAN:
						return String.valueOf(cell.getBooleanCellValue());
					case ERROR:
						return String.valueOf(cell.getErrorCellValue());
					case NUMERIC:
						return formatNumericCell(cellValue.getNumberValue(), cell);
					case STRING:
						return cell.getStringCellValue();
					default:
						return null;
				}
			case NUMERIC:
				return formatNumericCell(cell.getNumericCellValue(), cell);
			case STRING:
				return cell.getStringCellValue();
			default:
				return null;
		}
	}

	/**
	 * 原样返回数值单元格的内容
	 */
	private static String formatNumericCell(Double value, Cell cell){
		if(cell.getCellTypeEnum() != CellType.NUMERIC && cell.getCellTypeEnum() != CellType.FORMULA){
			return null;
		}
		//判断该单元格是"时间格式"或者该"单元格的公式算出来的是时间格式"
		if(DateUtil.isCellDateFormatted(cell)){
			Date date = cell.getDateCellValue();
			DataFormatter dataFormatter = new DataFormatter();
			Format format = dataFormatter.createFormat(cell);
			return format.format(date);
		}else{
			DataFormatter dataFormatter = new DataFormatter();
			Format format = dataFormatter.createFormat(cell);
			return format.format(value);
		}
	}

	public static void main(String[] args){
		List a = readXlsx("C:\\Users\\zhuxd\\Desktop\\wjs_product_20170923.xlsx");
		System.out.println(a);
 	}


}
