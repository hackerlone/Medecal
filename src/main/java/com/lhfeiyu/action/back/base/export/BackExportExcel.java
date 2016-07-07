package com.lhfeiyu.action.back.base.export;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.po.Diagnose;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.Medication;
import com.lhfeiyu.po.Student;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.DiagnoseService;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.HospitalService;
import com.lhfeiyu.service.MedicationService;
import com.lhfeiyu.service.StudentService;
import com.lhfeiyu.service.UserService;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.ExportExcel;
import com.lhfeiyu.util.RequestUtil;
@Controller
@RequestMapping("/back")
public class BackExportExcel {
	
	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private DiagnoseService diagnoseService;
	@Autowired
	private MedicationService medicationService;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private DoctorService doctorService;
	
	private static Logger logger = Logger.getLogger("R");
	
	/**导出*/
	@ResponseBody
	@RequestMapping(value = "/userExcel")
	public String exportUserExcel(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		try {
				HashMap<String, Object> paramMap = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
				List<User> userList = userService.selectListByCondition(paramMap);
				String	filename="用户信息";
				response.reset();
		        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		        response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".xls").getBytes(), "iso-8859-1"));
				response.setBufferSize(1024);
				String columnOrder="username,realName,phone,idcardNum,email,address,provinceName,cityName";
				String columnOrderType="String,String,int,String,String,String,String,String";
				String templateFileName="user.xls";
				String labelFileName="用户信息";
				String path = request.getSession().getServletContext().getRealPath("/file/template/");
				ExportExcel ee = new ExportExcel();
				InputStream excelStream = ee.exportExcelTest(userList, columnOrder,columnOrderType, path, templateFileName, labelFileName,0,0);
				int i=excelStream.available(); 
		        byte data[]=new byte[i]; 
		        excelStream.read(data);  //读数据   
		        OutputStream ops = response.getOutputStream();
		        ops.write(data);
		        ops.flush();  
		        ops.close();   
		        excelStream.close();
		        Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ExportExcel-PAGE-/back/exportUserExcel-导出用户信息出现异常", json);
		}
		return null;
	}
	
	/**导出*/
	@ResponseBody
	@RequestMapping(value = "/studentExcel")
	public String studentExcel(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> paramMap = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			List<Student> userList = studentService.selectListByCondition(paramMap);
			String	filename="学生信息";
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".xls").getBytes(), "iso-8859-1"));
			response.setBufferSize(1024);
			String columnOrder="name,area,phone,sex,qq,weixin";
			String columnOrderType="String,String,String,String,String,String";
			String templateFileName="student.xls";
			String labelFileName="学生信息";
			String path = request.getSession().getServletContext().getRealPath("/file/template/");
			ExportExcel ee = new ExportExcel();
			InputStream excelStream = ee.exportExcelTest(userList, columnOrder,columnOrderType, path, templateFileName, labelFileName,0,0);
			int i=excelStream.available(); 
			byte data[]=new byte[i]; 
			excelStream.read(data);  //读数据   
			OutputStream ops = response.getOutputStream();
			ops.write(data);
			ops.flush();  
			ops.close();   
			excelStream.close();
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ExportExcel-PAGE-/back/exportUserExcel-导出用户信息出现异常", json);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/patientExcel")
	public String patientExcel(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		try {
				HashMap<String, Object> paramMap = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
				List<User> patientList = userService.selectListByCondition(paramMap);
				String	filename="患者信息";
				response.reset();
		        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		        response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".xls").getBytes(), "iso-8859-1"));
				response.setBufferSize(1024);
				String columnOrder="username,realName,phone,idcardNum,email,address,provinceName,cityName";
				String columnOrderType="String,String,int,String,String,String,String,String";
				String templateFileName="patient.xls";
				String labelFileName="患者信息";
				String path = request.getSession().getServletContext().getRealPath("/file/template/");
				ExportExcel ee = new ExportExcel();
				InputStream excelStream = ee.exportExcelTest(patientList, columnOrder,columnOrderType, path, templateFileName, labelFileName,0,0);
				int i=excelStream.available(); 
		        byte data[]=new byte[i]; 
		        excelStream.read(data);  //读数据   
		        OutputStream ops = response.getOutputStream();
		        ops.write(data);
		        ops.flush();  
		        ops.close();   
		        excelStream.close();
		        Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ExportExcel-PAGE-/back/patientExcel-导出患者信息出现异常", json);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/medicationExcel")
	public String medicationExcel(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		try {
				HashMap<String, Object> paramMap = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
				List<Medication> medicationList = medicationService.selectListByCondition(paramMap);
				String	filename="药物信息";
				response.reset();
		        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		        response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".xls").getBytes(), "iso-8859-1"));
				response.setBufferSize(1024);
				String columnOrder="name,englishName,attention,usageAndDosage,producer,produceAddress,producerTel,producerCode";
				String columnOrderType="String,String,String,String,String,String,int,String";
				String templateFileName="medication.xls";
				String labelFileName="药物信息";
				String path = request.getSession().getServletContext().getRealPath("/file/template/");
				ExportExcel ee = new ExportExcel();
				InputStream excelStream = ee.exportExcelTest(medicationList, columnOrder,columnOrderType, path, templateFileName, labelFileName,0,0);
				int i=excelStream.available(); 
		        byte data[]=new byte[i]; 
		        excelStream.read(data);  //读数据   
		        OutputStream ops = response.getOutputStream();
		        ops.write(data);
		        ops.flush();  
		        ops.close();   
		        excelStream.close();
		        Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ExportExcel-PAGE-/back/medicationExcel-导出药物信息出现异常", json);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/hospitalExcel")
	public String hospitalExcel(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> paramMap = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			List<Hospital> hospitalList = hospitalService.selectListByCondition(paramMap);
			String	filename="诊所信息";
			response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".xls").getBytes(), "iso-8859-1"));
			response.setBufferSize(1024);
			String columnOrder="wholeName,phone,email,address,provinceName,cityName";
			String columnOrderType="String,int,String,String,String,String";
			String templateFileName="hospital.xls";
			String labelFileName="诊所信息";
			String path = request.getSession().getServletContext().getRealPath("/file/template/");
			ExportExcel ee = new ExportExcel();
			InputStream excelStream = ee.exportExcelTest(hospitalList, columnOrder,columnOrderType, path, templateFileName, labelFileName,0,0);
			int i=excelStream.available(); 
	        byte data[]=new byte[i]; 
	        excelStream.read(data);  //读数据   
	        OutputStream ops = response.getOutputStream();
	        ops.write(data);
	        ops.flush();  
	        ops.close();   
	        excelStream.close();
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ExportExcel-PAGE-/back/hospitalExcel-导出诊所信息出现异常", json);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/doctorExcel")
	public String doctorExcel(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		try {
				HashMap<String, Object> paramMap = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
				List<Doctor> doctorList = doctorService.selectListByCondition(paramMap);
				String	filename="医生信息";
				response.reset();
		        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		        response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".xls").getBytes(), "iso-8859-1"));
				response.setBufferSize(1024);
				String columnOrder="username,realname,phone,idCard,email,address,provinceName,cityName";
				String columnOrderType="String,String,int,int,String,String,String,String";
				String templateFileName="doctor.xls";
				String labelFileName="医生信息";
				String path = request.getSession().getServletContext().getRealPath("/file/template/");
				ExportExcel ee = new ExportExcel();
				InputStream excelStream = ee.exportExcelTest(doctorList, columnOrder,columnOrderType, path, templateFileName, labelFileName,0,0);
				int i=excelStream.available(); 
		        byte data[]=new byte[i]; 
		        excelStream.read(data);  //读数据   
		        OutputStream ops = response.getOutputStream();
		        ops.write(data);
		        ops.flush();  
		        ops.close();   
		        excelStream.close();
		        Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ExportExcel-PAGE-/back/doctorExcel-导出医生信息出现异常", json);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/diagnoseExcel")
	public String diagnoseExcel(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		try {
			HashMap<String, Object> paramMap = RequestUtil.getRequestParam(request);//自动获取所有参数（查询条件）
			List<Diagnose> diagnoseList = diagnoseService.selectListByCondition(paramMap);
			String	filename="病历信息";
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ new String((filename + ".xls").getBytes(), "iso-8859-1"));
			response.setBufferSize(1024);
			String columnOrder="patientName,patientPhone,patientIdcardNum,allergyHistory,baseCondition";
			String columnOrderType="String,int,int,String,String";
			String templateFileName="diagnose.xls";
			String labelFileName="病历信息";
			String path = request.getSession().getServletContext().getRealPath("/file/template/");
			ExportExcel ee = new ExportExcel();
			InputStream excelStream = ee.exportExcelTest(diagnoseList, columnOrder,columnOrderType, path, templateFileName, labelFileName,0,0);
			int i=excelStream.available(); 
			byte data[]=new byte[i]; 
			excelStream.read(data);  //读数据   
			OutputStream ops = response.getOutputStream();
			ops.write(data);
			ops.flush();  
			ops.close();   
			excelStream.close();
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-ExportExcel-PAGE-/back/diagnoseExcel-导出病历信息出现异常", json);
		}
		return null;
	}
	
	
}

