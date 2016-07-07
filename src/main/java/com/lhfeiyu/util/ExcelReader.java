package com.lhfeiyu.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 操作Excel表格的功能类   导入
 */
public class ExcelReader{
    private Workbook wb;
    private Sheet sheet;
    private Row row;

    /**
     * 读取Excel表格表头的内容
     * @param InputStream
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
    	String[] title = null;
    	try {
            wb = WorkbookFactory.create(is);
            sheet = wb.getSheetAt(0);
            row = sheet.getRow(0);
            // 标题总列数
            int colNum = row.getPhysicalNumberOfCells();
            System.out.println("colNum:" + colNum);
            title = new String[colNum];
            for (int i = 0; i < colNum; i++) {
            	title[i] = getCellFormatValue(row.getCell((short) i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     * @param InputStream
     * @return ArrayList<String>返回导入Excel行数据
     */
    private List<String[]> readExcelContent(InputStream is) {
    	List<String[]> content = new ArrayList<String[]>();
        try {
        	wb = WorkbookFactory.create(is);
            sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();// 得到总行数
            row = sheet.getRow(0);
            int colNum = row.getPhysicalNumberOfCells();
            String[] fields = new String[colNum];
            int flag = 0;
            for (int i = 1; i <= rowNum; i++) {
            	row = sheet.getRow(i);
            	int j = 0;
            	while (j < colNum) {
            		fields[j] = getCellFormatValue(row.getCell((short)j)).trim();
            		if(fields[j] == null || "".equals(fields[j])){
            			flag++;
            		}
            		j++;
            	}
            	if(flag >= colNum){
            		break;
            	}
            	flag = 0;
            	content.add(fields);
            	fields = new String[colNum];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * <strong>@Title: readAll(读取导入Excel所有行数据)</strong><p> 
     * <strong>@throws: </strong><p> 
     * <strong>@return ArrayList<ArrayList<String>>(返回导入Excel所有行数据)</strong><p>
     */
    public List<String[]> readAll(InputStream is) {  
        return this.readExcelContent(is);  
    } 
    
    public void close(InputStream is){
    	try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) throws Exception {
        String cellvalue = "";
        DecimalFormat df = new DecimalFormat("0");
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
            case Cell.CELL_TYPE_FORMULA: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                }else {
                    //取得当前Cell的数值
                    cellvalue = df.format(cell.getNumericCellValue());
                }
                break;
            }
            case Cell.CELL_TYPE_STRING:
            	cellvalue = String.valueOf(cell.getStringCellValue());
                break;
            default:
                cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
}


