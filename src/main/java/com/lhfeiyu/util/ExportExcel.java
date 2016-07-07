package com.lhfeiyu.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * <strong>@ClassName: ExportExcel(导出Excel公共类)</strong><p>
 * <strong>@Author: 王科</strong><p>
 * <strong>@Date: 2014年1月6日 下午9:00:44</strong><p>
 * <strong>@Description: 导出Excel公共类</strong><p>
 * <strong>@UpdateAuthor: 无</strong><p>
 * <strong>@UpdateDate: 无</strong><p>
 * <strong>@Description: 无</strong><p>
 * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
 * <strong>@version:</strong>v1.0<p>
 */
public class ExportExcel {
	private Workbook dealWithFlow(String path,String templateFileName,String labelFileName) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append(path);
		sb.append("\\");
		sb.append(templateFileName);
		InputStream is = new FileInputStream(sb.toString());
		Workbook wb = WorkbookFactory.create(is);
		return wb;
	}
	
	public InputStream exportExcelTest(List<?> dataList,String columnOrder,
			String path,String templateFileName,String labelFileName,int startLine,int startColumn) throws Exception{
		Row row = null;
		Workbook wb = dealWithFlow(path,templateFileName,labelFileName);
		Sheet sheet = wb.getSheetAt(0);
		String[] lineArray = columnOrder.split(",");
		for (int i = 0, len = dataList.size(); i < len; i++) {
			Object obj = dataList.get(i);
			row = sheet.createRow(i+1);
			for(int j=0;j<lineArray.length;j++){
				row.createCell((short)j).setCellValue(getValue(obj,lineArray[j]));
			}
		}
		return new ByteArrayInputStream(createResultBytes(wb, sheet));  
	}
	
	/**
	 * 由虞荣华添加，在上个方法的基础上加入了单元格的类型
	 * http://javacrazyer.iteye.com/blog/894850
	 */
	public InputStream exportExcelTest(List<?> dataList,String columnOrder,String columnOrderType,
			String path,String templateFileName,String labelFileName,int startLine,int startColumn) throws Exception{
		Row row = null;
		Workbook wb = dealWithFlow(path,templateFileName,labelFileName);
		Sheet sheet = wb.getSheetAt(0);
		String[] lineArray = columnOrder.split(",");
		String[] typeArray = columnOrderType.split(",");
		for (int i = 0, len = dataList.size(); i < len; i++) {
			Object obj = dataList.get(i);
			row = sheet.createRow(i+1);
			for(int j=0;j<lineArray.length;j++){
				Cell cell = row.createCell((short)j);
				//cell.setCellValue(getValue(obj,lineArray[j]));
				String value = getValue(obj,lineArray[j]);
				if(null == value || "".equals(value)){
					cell.setCellValue("");
				}else{
					if("String".equals(typeArray[j])){
						cell.setCellValue(new HSSFRichTextString(value));//设置值
					}else if("int".equals(typeArray[j])){
						cell.setCellValue(Long.parseLong(value));//设置值
						CellStyle cellStyle = wb.createCellStyle();  
						cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));  
						cell.setCellStyle(cellStyle);  
					}
				}
			}
		}
		return new ByteArrayInputStream(createResultBytes(wb, sheet));  
	}
	
	public static String getValue(Object obj,String property) throws Exception{
		if("".equals(property) || property == null){
			return "";
		}
		String methodName = "get"+ property.substring(0,1).toUpperCase()
				+ property.substring(1);
		Method method = obj.getClass().getMethod(methodName, new Class[]{});
		Object value = method.invoke(obj, new Object[]{});
		if(value == null){
			return "";
		}else{
			return value.toString();
		}
	}
	
	// 创建输出的字节码
	private byte[] createResultBytes(Workbook workbook, Sheet excelSheet) throws Exception {
		excelSheet.setDisplayGridlines(true);
		Footer footer = excelSheet.getFooter();
		footer.setRight("page" + HeaderFooter.page() + " of"
				+ HeaderFooter.numPages());
		ByteArrayOutputStream os = new ByteArrayOutputStream();// 创建输出流
		byte[] resultBytes;
		try {
			workbook.write(os);// 写入输出流
			resultBytes = os.toByteArray();// 转换字节码
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				os.close();// 关闭输出流
			} catch (IOException e) {
				throw e;
			}
		}
		return resultBytes;
	}
}