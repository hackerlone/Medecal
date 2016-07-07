package com.lhfeiyu.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.dao.HospitalMapper;
import com.lhfeiyu.dao.PatientReportDetailMapper;
import com.lhfeiyu.dao.PatientReportMapper;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.PatientReport;
import com.lhfeiyu.po.PatientReportDetail;
import com.lhfeiyu.tools.Check;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-PatientReport <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong>2016年3月20日22:22:22<p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class PatientReportService extends CommonService<PatientReport> {

	@Autowired
	PatientReportMapper patientReportMapper;
	@Autowired
	PatientReportDetailMapper prdMapper;
	@Autowired
	HospitalMapper hospitalMapper;
	
	public PatientReport selectService(Map<String, Object> paramMap) {
		return super.selectByCondition(paramMap);
	}
	
	public List<PatientReport> selectListService(Map<String, Object> paramMap) {
		return super.selectListByCondition(paramMap);
	}
	
	public int updateService(PatientReport patientReport) {
		return super.updateByPrimaryKeySelective(patientReport);
	}
	
	public int insertService(PatientReport patientReport) {
		return super.insertSelective(patientReport);
	}
	
	public int updateDeleteService(Integer id, String updatedBy) {
		return super.updateDeletedNowById(id, updatedBy);
	}
	
	public int deleteService(Integer id) {
		return super.deleteByPrimaryKey(id);
	}

	public void insertService(JSONObject json, PatientReport patientReport, Doctor session_doctor) {
		Integer hospitalId = session_doctor.getHospitalId();
		Hospital hos = hospitalMapper.selectByPrimaryKey(hospitalId);
		if(null != hospitalId && null != hos){
			patientReport.setHospitalId(hospitalId);
			patientReport.setHospitalName(hos.getWholeName());
		}
		patientReport.setDoctorId(session_doctor.getId());
		Date date = new Date();
		patientReport.setMainStatus(1);//1未出报告2.已出报告
		patientReport.setServiceFlag("1");//1：不是2：是(服务器获取还是创建)
		patientReport.setAgeType("岁");
		patientReport.setCreatedBy(session_doctor.getUsername());
		patientReport.setCreatedAt(date);
		super.insertSelective(patientReport);
	}

	public List<PatientReport> getOnlyAdiconBarcode(Map<String, Object> map) {
		return patientReportMapper.selectOnlyAdiconBarcode(map);
	}
	
	
	
	private static final String loginId = "H14037";
	private static final String password = "2kwtcr";
	private static final String url = "http://59.46.35.154:8088/";
	
	public void getbloadTestResult(){
		Date date = new Date();
		String key = resultKey();
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject json = getAllSampleList(key);
		if(null == json || json.size() <= 0)return;
		String listtableString = json.getString("NewDataSet");
		if( Check.isNull(listtableString) )return;
		JSONObject jso = JSONObject.parseObject(listtableString);
		JSONArray ja = jso.getJSONArray("listtable");
		if(null == ja || ja.size() <= 0)return;
		for(int i=0; i<ja.size(); i++){
			JSONObject item = ja.getJSONObject(i);
			String AdiconBarcode = item.getString("AdiconBarcode").replace("[\"", "").replace("\"]", "");
			JSONObject json2 = getJSONReportItemListByAdiconBarocde(key, AdiconBarcode);
			if(null == json2 || json2.size() <= 0)continue;
			String listtableString2 = json2.getString("常规报告");
			if( Check.isNull(listtableString2))continue;
			JSONObject jso2 = JSONObject.parseObject(listtableString2);
			JSONArray ja2 = jso2.getJSONArray("item");
			List<PatientReportDetail> patientReportDetailList = JSONObject.parseArray(ja2.toString(), PatientReportDetail.class);
			if( Check.isNull(patientReportDetailList) )continue;
			for(PatientReportDetail prd : patientReportDetailList){
				//Map<String,Object> map = new HashMap<String,Object>();
				map.clear();
				String itemCode = prd.getItemCode();
				String adiconBarcode = prd.getAdiconBarcode();
				map.put("itemCode", itemCode);
				map.put("serialNumber", prd.getSerialNumber());
				map.put("adiconBarcode", adiconBarcode);
				PatientReportDetail patientReportDetail = prdMapper.selectByCondition(map);
				if(null == patientReportDetail){
					prd.setServiceFlag("2");//1：不是2：是(服务器获取还是创建)
					prd.setMainStatus(2);//1未出报告2.已出报告
					prd.setCreatedAt(date);
					prdMapper.insert(prd);
					
					/*map.clear();
					map.put("itemCode", prd.getItemCode());
					map.put("adiconBarcode", prd.getAdiconBarcode());
					int count = patientReportMapper.selectCountByCondition(map);
					if(count <= 0){//没有对应patientReport，新增一个
						PatientReport pr = new PatientReport();
						pr.setAdiconBarcode(adiconBarcode);
						patientReportMapper.insert(pr);
					}*/
					
				}
				/*else{
					pr.setId(patientReport.getId());
					pr.setServiceFlag("2");//1：不是2：是(服务器获取还是创建)
					pr.setMainStatus(2);//1未出报告2.已出报告
					patientReportService.updateByPrimaryKey(pr);
				}*/
			}
		}
	}
//	
	private JSONObject getAllSampleList(String key){
		//Key＝有效授权码，BeginDateTime＝起始时间， EndDateTime＝结束时间，TypeDateTime＝1=按采集时间统计，2＝按报告时间统计；AgainFlag:1=重新下载已下载过的标本，0＝只下载未下载的标本
		JSONObject json = new JSONObject();
		int TypeDateTime = 1;
		int AgainFlag = 0;
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		String beginTime;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int total = prdMapper.selectCountByCondition(null);
		if(total < 10000){
			int year = c.get(Calendar.YEAR);
			beginTime = year + "-01-01";
		}else{
			c.add(Calendar.DATE, -7);//如果数据超过一万条了，就只加载7天之内的数据
			beginTime = format.format(c.getTime());
		}
		String endTime = format.format(date);
		System.out.println("adicon api : beginTime - "+beginTime+" , endTime - "+ endTime);
		String getAllSampleList = HttpService.sendGet(url+"ADReportWebService.asmx/GetAllSampleList", "Key="+key+"&BeginDateTime="+beginTime+"&EndDateTime="+endTime+"&TypeDateTime="+TypeDateTime+"&AgainFlag="+AgainFlag+""); 
		//System.out.println("getAllSampleList:"+getAllSampleList);
		String jsonString = HttpService.xml2JSON(content2lt(contentSubHeadEnd(getAllSampleList)));
		//String jsonString = HttpService.xml2JSON(contentSubHeadEnd(getAllSampleList));
		if(null != jsonString){
			json = JSONObject.parseObject(jsonString);
		}
		System.out.println("adicon api : result_json - "+json.toJSONString());
		return json;
	}
	
	private JSONObject getJSONReportItemListByAdiconBarocde(String key,String AdiconBarcode){
		JSONObject json = new JSONObject();
		String  getJSONReportItemListByAdiconBarocdeString = HttpService.sendGet(url+"ADReportWebService.asmx/GetJSONReportItemListByAdiconBarocde", "AdiconBarcode="+AdiconBarcode+"&Key="+key+"");
		if(null != getJSONReportItemListByAdiconBarocdeString){
			json = JSONObject.parseObject(contentSubHeadEnd(getJSONReportItemListByAdiconBarocdeString));
		}
		return json;
	}
	
	private  String resultKey(){
		String result = HttpService.sendGet(url+"ADReportWebService.asmx/Login", "logid="+loginId+"&password="+password+"");
		return contentSubHeadEnd(result);
	}
	
	private static String contentSubHeadEnd(String content){
		String subContent = content.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://www.adicon.com.cn/\">","");
		return subContent.replace("</string>", "");
	}
	
	private static String content2lt(String content){
		String subContent = content.replaceAll("&lt;", "<");
		return subContent.replaceAll("&gt;", ">");
	}

}