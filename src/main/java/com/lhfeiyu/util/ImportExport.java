package com.lhfeiyu.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;  

/**
 * <strong>@ClassName: ImportExport(导入Excel，解析数据处理类)</strong><p>
 * <strong>@Author: 王科</strong><p>
 * <strong>@Date: 2014年1月9日 下午2:25:38</strong><p>
 * <strong>@Description: 导入Excel，解析数据处理类</strong><p>
 * <strong>@UpdateAuthor: 无</strong><p>
 * <strong>@UpdateDate: 无</strong><p>
 * <strong>@Description: 无</strong><p>
 * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
 * <strong>@version:</strong>v1.0<p>
 */
public class ImportExport { 
	/**创建excel*/
    private Workbook readBook;  
  
    private int currIndex;  
    /**excel一行数据*/
    private ArrayList<String> alLineContent;  
    /**excel单元格*/
    private Cell cell;  
  
    private int firstDataCount;  
  
    private boolean isFirst;  
    /**excel标签*/
    private Sheet sheet;  
  
    /**
     * <strong>@Title: read(根据文件名称，获得导入的excel)</strong><p> 
     * <strong>@Author: 王科</strong><p>
     * <strong>@Date: 2014年1月9日 下午1:52:10</strong><p>
     * <strong>@Description: 根据文件名称，获得导入的excel</strong><p>
     * <strong>@UpdateAuthor: 无</strong><p>
     * <strong>@UpdateDate: 无</strong><p>
     * <strong>@Description: 无</strong><p>
     * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
     * <strong>@throws: </strong><p> 
     * <strong>@param fileName void(要导入的文件名称)</strong><p>
     */
     void read(String fileName) {  
        try {  
            readBook = Workbook.getWorkbook(new File(fileName));  
            sheet = readBook.getSheet(0);  
            currIndex = 0;  
            isFirst = true;  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
  
    /**
     * <strong>@Title: read(根据导入的文件流，获得上传的Excel)</strong><p> 
     * <strong>@Author: 王科</strong><p>
     * <strong>@Date: 2014年1月9日 下午1:53:18</strong><p>
     * <strong>@Description: 根据导入的文件流，获得上传的Excel</strong><p>
     * <strong>@UpdateAuthor: 无</strong><p>
     * <strong>@UpdateDate: 无</strong><p>
     * <strong>@Description: 无</strong><p>
     * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
     * <strong>@throws: </strong><p> 
     * <strong>@param is 上传的Excel文件流
     * <strong>@throws Exception void(TODO)</strong><p>
     */
     public void read(InputStream is) {  
        try {  
            readBook = Workbook.getWorkbook(is);  
            sheet = readBook.getSheet(0);  
            currIndex = 0;  
            isFirst = true;  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
  
    /**
     * <strong>@Title: readLine(读取导入的Excel的行数据)</strong><p> 
     * <strong>@Author: 王科</strong><p>
     * <strong>@Date: 2014年1月9日 下午1:56:30</strong><p>
     * <strong>@Description: 读取导入的Excel的行数据</strong><p>
     * <strong>@UpdateAuthor: 无</strong><p>
     * <strong>@UpdateDate: 无</strong><p>
     * <strong>@Description: 无</strong><p>
     * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
     * <strong>@throws: </strong><p> 
     * <strong>@return ArrayList<String>(返回导入Excel行数据)</strong><p>
     */
    private ArrayList<String> readLine() {  
        alLineContent = new ArrayList<String>();  
        int i = 0;  
        String content = null;  
        while (true) {  
            if (!isFirst && i >= firstDataCount)  
                break;  
            try {  
                // 读取一个单元格的数据  
                cell = sheet.getCell(i, currIndex);  
                i++;  
            } catch (Exception e) {  
                // 没有数据可读取  
                if (i == 0)  
                    return null;  
                if (isFirst) {  
                    firstDataCount = i;  
                    isFirst = false;  
                    break;  
                } else  
                    content = "";  
            }  
            content = cell.getContents();  
            // 首行存在空值时认为提取数据完毕  
            if (isFirst && "".equals(content)) {  
                firstDataCount = i - 1;  
                isFirst = false;  
                break;  
            }  
            alLineContent.add(content);  
        }  
        currIndex++;  
        return alLineContent;  
    }  
  
    /**
     * <strong>@Title: readAll(读取导入Excel所有行数据)</strong><p> 
     * <strong>@Author: 王科</strong><p>
     * <strong>@Date: 2014年1月9日 下午2:00:40</strong><p>
     * <strong>@Description: 读取导入Excel所有行数据</strong><p>
     * <strong>@UpdateAuthor: 无</strong><p>
     * <strong>@UpdateDate: 无</strong><p>
     * <strong>@Description: 无</strong><p>
     * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
     * <strong>@throws: </strong><p> 
     * <strong>@return ArrayList<ArrayList<String>>(返回导入Excel所有行数据)</strong><p>
     */
    public ArrayList<ArrayList<String>> readAll() {  
        ArrayList<ArrayList<String>> alAllData = new ArrayList<ArrayList<String>>();  
        ArrayList<String> data = null;  
        while (true) {  
            data = this.readLine();  
            if (data == null)  
                break;  
            alAllData.add(data);  
        }  
        return alAllData;  
    }  
    
    /**
     * <strong>@Title: closeRead(关闭创建的Excel)</strong><p> 
     * <strong>@Author: 王科</strong><p>
     * <strong>@Date: 2014年1月9日 下午2:09:27</strong><p>
     * <strong>@Description: 关闭创建的Excel</strong><p>
     * <strong>@UpdateAuthor: 无</strong><p>
     * <strong>@UpdateDate: 无</strong><p>
     * <strong>@Description: 无</strong><p>
     * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
     * <strong>@throws: </strong><p> 
     * <strong> void(无)</strong><p>
     */
     public void closeRead() {  
        readBook.close();  
    }  
     
     public static void main(String[] args) {
    	 String filePath="E:\\Download\\export.xls";
		 File f=new File(filePath);
		 System.out.println(f.getName());
	}
}  