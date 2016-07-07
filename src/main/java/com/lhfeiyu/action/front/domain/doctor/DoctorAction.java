package com.lhfeiyu.action.front.domain.doctor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.dao.ArticleMapper;
import com.lhfeiyu.dao.CommentMapper;
import com.lhfeiyu.po.Article;
import com.lhfeiyu.po.Bespeak;
import com.lhfeiyu.po.Cancer;
import com.lhfeiyu.po.Comment;
import com.lhfeiyu.po.Consult;
import com.lhfeiyu.po.Dict;
import com.lhfeiyu.po.Doctor;
import com.lhfeiyu.po.DoctorPatient;
import com.lhfeiyu.po.Hospital;
import com.lhfeiyu.po.Message;
import com.lhfeiyu.po.Notice;
import com.lhfeiyu.po.ProvinceCityArea;
import com.lhfeiyu.po.User;
import com.lhfeiyu.service.BespeakService;
import com.lhfeiyu.service.CancerService;
import com.lhfeiyu.service.ConsultService;
import com.lhfeiyu.service.DiagnoseApplyService;
import com.lhfeiyu.service.DiagnoseService;
import com.lhfeiyu.service.DictService;
import com.lhfeiyu.service.DoctorPatientService;
import com.lhfeiyu.service.DoctorService;
import com.lhfeiyu.service.MessageService;
import com.lhfeiyu.service.NoticeService;
import com.lhfeiyu.service.ProvinceCityAreaService;
import com.lhfeiyu.service.UserService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.Md5Util;
import com.lhfeiyu.util.RequestUtil;

@Controller
public class DoctorAction {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private DoctorPatientService doctorPatientService;
	@Autowired
	private ProvinceCityAreaService provinceCityAreaService;
	@Autowired
	private BespeakService bespeakService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private ConsultService consultService;
	@Autowired
	private DiagnoseApplyService diagnoseApplyService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private DictService dictService;
	@Autowired
	private CancerService cancerService;
	@Autowired
	private UserService userService;
	@Autowired
	private DiagnoseService diagnoseService;
	
	@Autowired
	ArticleMapper mapper;
	@Autowired
	CommentMapper commentMapper;
	
	private static Logger logger = Logger.getLogger("R");
	
	@RequestMapping(value="/doctor/{id}")
	public ModelAndView  doctor(ModelMap modelMap,@PathVariable Integer id){
		String path = PagePath.frontDoctorHome;
		try{
			modelMap = doctorService.getDoctorData(modelMap,null,id);
			modelMap.put("doctorId", id);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctor-加载医生首页出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/patientLibrary")
	public ModelAndView  patientLibrary(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.patientLibrary;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
			//modelMap.put("doctorId", value)
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/patientLibrary-加载患者库出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/doctorDiagnoseApply")
	public ModelAndView  doctorDiagnoseApply(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.doctorDiagnoseApply;
		try{
			Doctor doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getDoctorData(modelMap,doctor,null);
			modelMap.put("doctorId", doctor.getId());
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorDiagnoseApply-加载医生授权出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/doctorConsult")
	public ModelAndView  doctorConsult(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.doctorConsult;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorConsult-加载医生咨询出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/doctorBespeak")
	public ModelAndView  doctorBespeak(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.doctorBespeak;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			modelMap.put("doctorId", session_doctor.getId());
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorBespeak-加载预约出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	@RequestMapping(value="/doctorChat")
	public ModelAndView  doctorChat(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.doctorChat;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorChat-加载消息出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/doctorMessage")
	public ModelAndView  doctorMessage(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.doctorMessage;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorMessage-加载留言出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/bespeakDeatil/{bespeakId}")
	public ModelAndView  bespeakDeatil(ModelMap modelMap,HttpServletRequest request
			,@PathVariable Integer bespeakId){
		String path = PagePath.bespeakDeatil;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			modelMap = doctorService.getBespeakData(modelMap,session_doctor,bespeakId);
			List<Cancer> cancerTypes = cancerService.selectListByCondition(null);
			modelMap.put("cancerTypes", cancerTypes);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/bespeakDeatil-加载预约详情出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/messageDetail/{id}")
	public ModelAndView  messageDetail(ModelMap modelMap,HttpServletRequest request,
			@PathVariable Integer id){
		String path = PagePath.messageDetail;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("id", id);
			Message message = messageService.selectByCondition(map);
			modelMap.put("message", message);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorMessage-加载留言出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	
	@RequestMapping(value="/doctorArticle")
	public ModelAndView  doctorArticle(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.doctorArticle;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			modelMap.put("doctorId", session_doctor.getId());
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("parentCode", "article_type");
			List<Dict> dictList = dictService.selectListByCondition(map);
			modelMap.put("articleType", dictList);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorArticle-加载医生文章出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	@RequestMapping(value="/doctorInformation")
	public ModelAndView  doctorInformation(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.doctorInformation;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
			modelMap.put("doctorId", session_doctor.getId());
			Map<String,Object> map = CommonGenerator.getHashMap();
			map.put("parentCode", "article_type");
			List<Dict> dictList = dictService.selectListByCondition(map);
			modelMap.put("articleType", dictList);
			map.put("parentCode", "education");
			List<Dict> educationList = dictService.selectListByCondition(map);
			modelMap.put("educationList", educationList);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorInformation-加载医生资讯出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/doctorArticleDetails/{articleId}")
	public ModelAndView  doctorArticleDetails(ModelMap modelMap,HttpServletRequest request,@PathVariable Integer articleId){
		String path = PagePath.doctorArticleDetails;
		try{
//			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
//			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			Article article = mapper.selectByPrimaryKey(articleId);//当前文章
			modelMap.put("article", article);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("objectId", articleId);
			map.put("commentTypeId", 1);
			List<Comment> commengList = commentMapper.selectListByCondition(map);
			modelMap.put("commengList", commengList);
			modelMap = doctorService.getFansData(modelMap,null,article.getUserId());
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorInformation-加载医生文章详情出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/doctorHome")
	public ModelAndView  doctorHome(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.frontDoctor;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getDoctorData(modelMap,session_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorHome-加载医生首页出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/doctorNewMsg", method = RequestMethod.POST)
	public JSONObject doctorNewMsg(HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("doctorId", session_doctor.getId());//当前医生的咨询
			map.put("main_status", 1);//1 未回复的咨询
			int consultCount = consultService.selectCountByCondition(map);//查询当前医生未回复咨询总条数
			map.clear();
			map.put("doctorId", session_doctor.getId());//当前医生的预约
			map.put("main_status", 1);//1 未回复的预约
			int bespeakCount = bespeakService.selectCountByCondition(map);//查询当前医生未回复预约总条数
			map.clear();
			map.put("doctorId", session_doctor.getId());//当前医生的病历授权
			map.put("logic_status", 1);//1 未回复的病历授权
			int diagnoseApplyCount = diagnoseApplyService.selectCountByCondition(map);//查询当前医生未授权病历总条数

			map.clear();
			map.put("receiverId", session_doctor.getId());//当前医生的留言
			map.put("main_status", 1);//1 未回复的留言
			int messageCount = messageService.selectCountByCondition(map);//查询当前医生留言历总条数
			json.put("consultCount", consultCount);
			json.put("bespeakCount", bespeakCount);
			json.put("diagnoseApplyCount", diagnoseApplyCount);
			json.put("messageCount", messageCount);
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/addOrUpdateDoctor-新增或修改医生出现异常", json);
		}
		return json;
	}
	
	@RequestMapping(value="/consultDetail/{id}")
	public ModelAndView  consultDetail(ModelMap modelMap,HttpServletRequest request
			,@PathVariable Integer id){
		String path = PagePath.doctorConsultDetail;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			if(Check.isNotNull(id)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", id);
				Consult consult = consultService.selectByCondition(map);
				modelMap.put("consult", consult);
			}
			modelMap = doctorService.getFansData(modelMap,session_doctor,null);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Consult-PAGE-/consultDetail-加载咨询详情页面出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@RequestMapping(value="/doctorBaseInformation")
	public ModelAndView  doctorBaseInformation(ModelMap modelMap,HttpServletRequest request){
		String path = PagePath.doctorBaseInformation;
		try{
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(modelMap,PagePath.doDctorLogin,"doctor");
			modelMap = doctorService.getDoctorData(modelMap,session_doctor,null);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("higherIdISNULL", 1);
			map.put("mainStatus", 1);
			List<ProvinceCityArea> provinceCityAreaList = provinceCityAreaService.selectListByCondition(map);
			modelMap.put("provinceCityAreaList", provinceCityAreaList);
			map.clear();
			map.put("parentCode", "title");
			List<Dict> dictList = dictService.selectListByCondition(map);
			modelMap.put("titleIdsList", dictList);
			map.put("parentCode", "education");
			List<Dict> educationList = dictService.selectListByCondition(map);
			modelMap.put("educationList", educationList);
		}catch(Exception e){
			path = PagePath.error;
			Result.catchError(e, logger, "LH_ERROR-Doctor-PAGE-/doctorHome-加载医生基本信息出现异常", modelMap);
		}
		return new ModelAndView(path,modelMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDoctorList", method=RequestMethod.POST)
	public JSONObject getDoctorList(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			Hospital hospital = ActionUtil.checkSession4Hospital(request.getSession());//验证session中的user，存在即返回
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());
			if(hospital!=null){
				map.put("hospitalId", hospital.getId());
			}
			if(session_doctor!=null){
				map.put("fansUserId", session_doctor.getId());
			}
			List<Doctor> doctorList = doctorService.selectListByCondition(map);
			Integer total = doctorService.selectCountByCondition(map);
			Result.gridData(doctorList, total, json);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/getDoctorList-加载医生列表出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDoctor", method = RequestMethod.POST)
	public JSONObject updateDoctor(@ModelAttribute Doctor doctor,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			doctorService.updateService(json, doctor, session_doctor);
			Result.success(json);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/addOrUpdateDoctor-新增或修改医生出现异常", json);
		}
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateBespeakRead", method = RequestMethod.POST)
	public JSONObject updateBespeakRead(HttpServletRequest request,@RequestParam Integer id){
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			if(null != id){
				Bespeak bespeak = bespeakService.selectByPrimaryKey(id);
				if(null != bespeak){
					String doctorName = session_doctor.getUsername();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("id", id);
					map.put("expression1", "main_status = 1");//(1.确认2.驳回)
					bespeakService.updateFieldById(map);
					Notice notice = new Notice();
					notice.setReceiverId(Integer.valueOf(bespeak.getUserId()));
					notice.setSenderId(session_doctor.getId());
					notice.setContent("医生:["+doctorName+"]已确认您的预约");
					notice.setTitle("医生:["+doctorName+"]已确认您的预约");
					notice.setMainStatus(1);
					notice.setCreatedAt(new Date());
					notice.setCreatedBy(doctorName);
					noticeService.insert(notice);
					Result.success(json);
				}else{
					Result.failure(json, "信息不存在", null);
				}
			}else{
				Result.failure(json, "参数有误", null);
			}
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/updateBespeakRead-预约已读出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateBespeaNotRead", method = RequestMethod.POST)
	public JSONObject updateBespeaNotRead(HttpServletRequest request,@RequestParam Integer id){
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			if(null != id){
				Bespeak bespeak = bespeakService.selectByPrimaryKey(id);
				if(null != bespeak){
					String doctorName = session_doctor.getUsername();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("id", id);
					map.put("expression1", "main_status = 2");//(1.确认2.驳回)
					bespeakService.updateFieldById(map);
					Notice notice = new Notice();
					notice.setReceiverId(Integer.valueOf(bespeak.getUserId()));
					notice.setSenderId(session_doctor.getId());
					notice.setContent("医生:["+doctorName+"]驳回您的预约");
					notice.setTitle("医生:["+doctorName+"]驳回您的预约");
					notice.setMainStatus(1);
					notice.setCreatedAt(new Date());
					notice.setCreatedBy(doctorName);
					noticeService.insert(notice);
					Result.success(json);
				}else{
					Result.failure(json, "信息不存在", null);
				}
			}else{
				Result.failure(json, "参数有误", null);
			}
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/updateBespeaNotRead-预约未读出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/searchPatient", method=RequestMethod.POST)
	public JSONObject searchPatient(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {//自动获取所有参数（查询条件）
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
			Integer doctorId = session_doctor.getId();
			map.put("doctorId", doctorId);
			User user = userService.selectByCondition(map);
			if(null != user){
				map.clear();
				map.put("doctorId", doctorId);
				map.put("userId", user.getId());
				DoctorPatient dp = doctorPatientService.selectByCondition(map);
				if(null == dp){
					DoctorPatient dpNew =  new DoctorPatient();
					dpNew.setDoctorId(doctorId);
					dpNew.setUserId(user.getId());
				}
			}
			int diagnoseCount = diagnoseService.selectCountByCondition(map);
			json.put("patient", user);
			json.put("diagnoseCount", diagnoseCount);
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/searchPatient-加载患者出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/doctorReplyMessage", method = RequestMethod.POST)
	public JSONObject doctorReplyMessage(@ModelAttribute Message message,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Integer messageId = message.getId();
			if(null != messageId){
				Message db_message = messageService.selectByPrimaryKey(messageId);
				if(null == db_message){
					return Result.failure(json, "留言信息不存在", "message_null");
				}
				Integer sessionDoctorId = session_doctor.getId();
				Integer receiverId = db_message.getReceiverId();
				if(!Check.integerEqual(sessionDoctorId, receiverId)){
					return Result.failure(json, "您没有权限回复该留言信息", "authority_error");
				}
				messageService.updateReplyContentAndSendNoctice(json,message,session_doctor,db_message);
			}else{
				Result.failure(json, "传入数据有误,请检查数据", null);
			}
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/doctorReplyMessage-医生回复咨询出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDoctorPassword",method=RequestMethod.POST)
	public JSONObject updateUserPassword(HttpServletRequest request,
			@RequestParam String password) {
		JSONObject json = new JSONObject();
		try {
			Doctor session_doctor = ActionUtil.checkSession4Doctor(request.getSession());//验证session中的user，存在即返回
			if(null == session_doctor)return Result.userSessionInvalid(json, "doctor");
			Integer sessionDoctorId = session_doctor.getId();
			Doctor db_doctor = doctorService.selectByPrimaryKey(sessionDoctorId);
			if(null == db_doctor){
				return Result.failure(json, "该用户不存在", "user_null");
			}
			Integer db_doctorId = db_doctor.getId();
			if(!Check.integerEqual(sessionDoctorId, db_doctorId)){
				return Result.failure(json, "您没有权限修改该医生信息", "authority_error");
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", sessionDoctorId);
			map.put("expression1","password = '"+Md5Util.encrypt(password)+"'");
			doctorService.updateFieldById(map);
			request.getSession().invalidate();
			Result.success(json);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-Doctor-AJAX-/updateDoctorPassword-修改医生密码出现异常", json);
		}
		return json;
	}
	
	
}
