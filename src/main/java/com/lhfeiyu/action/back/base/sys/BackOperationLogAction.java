package com.lhfeiyu.action.back.base.sys;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.OperationLog;
import com.lhfeiyu.service.OperationLogService;
import com.lhfeiyu.tools.ImportExportActionUtil;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;

@Controller
@RequestMapping(value="/back")
public class BackOperationLogAction {
	@Autowired
	private OperationLogService  operationLogService;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/operationLog")
	public ModelAndView  operationLog(ModelMap modelMap,HttpSession session,
			@RequestParam(required=false,value="typeId") Integer typeId){
		String path = PagePath.backOperationLog;
		try{
			modelMap.put("typeId", typeId);
		}catch(Exception e){
			e.printStackTrace();
			path = PagePath.error;
			logger.error("LH_ERROR_加载操作日志页面出现异常_"+e.getMessage());
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getOperationLogList",method=RequestMethod.POST)
	public JSONObject getOperationLogList(HttpServletRequest request) {
		List<OperationLog> operationLogList = null;
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			//sc_order格式例子：id___asc,created_at___desc,如果传递了request,则可自动获取分页参数
			map = Pagination.getOrderByAndPage(map,request);
			operationLogList = operationLogService.selectListByCondition(map);
			Integer total = operationLogService.selectCountByCondition(map);
			json.put("rows", operationLogList);
			json.put("total", total);
			json.put("status", "success");
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR_加载操作日志列表出现异常_", json);
		}
		return json;
	}
	
	/** 操作日志导出 */
	@ResponseBody
	@RequestMapping(value = "/exportOperationLog")
	public JSONObject exportOperationLog(HttpServletRequest request,HttpServletResponse response){
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);
			List<OperationLog> operationLogList =  operationLogService.selectListByCondition(map);
			String filename="操作日志导出数据";
			String columnsStr="id,username";
			String templateFileName="操作日志导出模板.xls";
			String labelFileName="操作日志导出数据";
	        ImportExportActionUtil.exportConsumableExcel(request, response, filename, columnsStr, templateFileName, labelFileName, operationLogList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*@ResponseBody
	@RequestMapping(value = "/exportOperationLogTest",method=RequestMethod.GET)
	public JSONObject exportOperationLogTest(HttpServletRequest request,HttpServletResponse response){
		try {
			HashMap<String, Object> map = RequestUtil.getRequestParam(request);
			List<OperationLog> operationLogList =  operationLogService.selectListByCondition(map);
			String filename="操作日志导出数据";
			response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".xls").getBytes(), "iso-8859-1"));
			response.setBufferSize(1024);
			String columnsStr="id,username";
			String templateFileName="操作日志导出模板.xls";
			String labelFileName="操作日志导出数据";
			String path = request.getSession().getServletContext().getRealPath("/file/template/");
			ExportExcel ee = new ExportExcel();
			InputStream excelStream = ee.exportExcelTest(operationLogList, columnsStr, path, templateFileName, labelFileName,0,0);
			
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
	*/
}
