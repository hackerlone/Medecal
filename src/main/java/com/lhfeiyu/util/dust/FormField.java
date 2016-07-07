package com.lhfeiyu.util.dust;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 数据库所有表的字段描述：用于页面新增修改表单统一读取字段数据 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2015年8月7日09:09:21 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class FormField {
	
	public static final int USER = 0;
	public static final int MESSAGE = 1;

	public static String[] user_names = {"id","username","updated_at"};
	public static String[] user_ch = {"编号","用户名","修改时间"};
	public static String[] user_types = {"text","combo","date"};
	
	public static String[] message_names = {"id","username","updated_at"};
	public static String[] message_ch = {"编号","用户名","修改时间"};
	public static String[] message_types = {"text","combo","date"};
	
	private static String[][] fieldNameAry = {user_names,message_names};
	private static String[][] fieldChAry = {user_ch,message_ch};
	private static String[][] fieldTypeAry = {user_types,message_types};
	
	
	public static String[] getFieldNames(int position){
		return fieldNameAry[position];
	}
	public static String[] getFieldCh(int position){
		return fieldChAry[position];
	}
	public static String[] getFieldTypes(int position){
		return fieldTypeAry[position];
	}
	
	public static void main(String[] args) {
		/**
		 * 		String[] fieldNames = FormField.getFieldNames(FormField.MESSAGE);
				String[] fieldCh = FormField.getFieldCh(FormField.MESSAGE);
				String[] fieldTypes = FormField.getFieldTypes(FormField.MESSAGE);
				modelMap.put("fieldNames", fieldNames);
				modelMap.put("fieldCh", fieldCh);
				modelMap.put("fieldTypes", fieldTypes);
				
				return new ModelAndView("/back/message/common_update", modelMap);
		 */
	}
	
}
