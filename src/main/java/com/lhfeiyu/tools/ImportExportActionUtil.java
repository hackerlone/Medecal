package com.lhfeiyu.tools;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhfeiyu.util.ExportExcel;

public class ImportExportActionUtil {
	
	/** Action通用导出 */
	public static String exportConsumableExcel(HttpServletRequest request,HttpServletResponse response,
			String filename,String columnsStr,String templateFileName,String labelFileName,List<?> dataList){
		try {
			response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".xls").getBytes(), "iso-8859-1"));
			response.setBufferSize(1024);
			String path = request.getSession().getServletContext().getRealPath("/file/template/");
			ExportExcel ee = new ExportExcel();
			InputStream excelStream = ee.exportExcelTest(dataList, columnsStr, path, templateFileName, labelFileName,0,0);
			
			int i=excelStream.available(); 
	        byte data[]=new byte[i]; 
	        excelStream.read(data);  //读数据   
	        OutputStream ops = response.getOutputStream();
	        ops.write(data);
	        ops.flush();  
	        ops.close();   
	        excelStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
