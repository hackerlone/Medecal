package com.lhfeiyu.util.dust;

//import java.util.Date;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * <strong>@ClassName: DateFormat(日期格式常量与日期格式转换)</strong><p>
 * <strong>@Author: 成都蓝海飞鱼科技有限公司开发人员 </strong><p>
 * <strong>@Date: 2014年1月2日 上午11:10:17</strong><p>
 * <strong>@Description: 日期格式常量与日期格式转换</strong><p>
 * <strong>@UpdateAuthor: 无</strong><p>
 * <strong>@UpdateDate: 无</strong><p>
 * <strong>@Description: 无</strong><p>
 * <strong>@CompanyName:成都蓝海飞鱼科技有限公司</strong><p>
 * <strong>@version:</strong>v1.0<p>
 */
public class MyCommentGenerator extends DefaultCommentGenerator implements CommentGenerator {
	
	public MyCommentGenerator(){
		super();
	}
	
	@Override
	public void addJavaFileComment(CompilationUnit compilationUnit) {
    	compilationUnit.addFileCommentLine("/**");
    	//compilationUnit.addFileCommentLine(" * @mbggenerated");
    	compilationUnit.addFileCommentLine(" * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 持久层PO类 <p>");
    	compilationUnit.addFileCommentLine(" * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 成都蓝海飞鱼科技有限公司开发人员 <p>");
    	compilationUnit.addFileCommentLine(" * <strong> 编写时间：</strong> 2015-2016 <p>");
    	compilationUnit.addFileCommentLine(" * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>");
    	compilationUnit.addFileCommentLine(" * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>");
    	compilationUnit.addFileCommentLine(" */");
        return;
    }
	
	/**
     * PO字段注释
     */
	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
	    if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
	        field.addJavaDocLine("/**");
	        field.addJavaDocLine("* @mbggenerated");
	        StringBuilder sb = new StringBuilder();
	        sb.append(" * ");
	        sb.append(introspectedColumn.getRemarks());
	        field.addJavaDocLine(sb.toString());
	        field.addJavaDocLine(" */");
	     }
	  }
	
	/**
     * xml中的注释
     * @param xmlElement
     */
    @Override
	public void addComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));
        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        xmlElement.addElement(new TextElement(sb.toString()));
        xmlElement.addElement(new TextElement("-->"));
    }
	
}
