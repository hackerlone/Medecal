package com.lhfeiyu.action.back.base.stu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.AssetsPath;
import com.lhfeiyu.config.PagePath;
import com.lhfeiyu.po.Admin;
import com.lhfeiyu.po.Student;
import com.lhfeiyu.service.AA_UtilService;
import com.lhfeiyu.service.StudentService;
import com.lhfeiyu.tools.ActionUtil;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.Pagination;
import com.lhfeiyu.tools.Result;
import com.lhfeiyu.util.RequestUtil;
 
/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 控制层 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 王家明 <p>
 * <strong> 编写时间：</strong>2016年6月30日 上午11:34:59<p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
@Controller
@RequestMapping(value="/back")
public class StudentAction {
	@Autowired //自动装配
	StudentService studentService;
	@Autowired
	private AA_UtilService utilService;
	private static Logger logger = Logger.getLogger("R");
	@RequestMapping(value="/stu")
	public ModelAndView stu(){
		String path = PagePath.frontStudent;
		
		return new ModelAndView(path);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getStudentList", method=RequestMethod.POST)
	public JSONObject getStudentList(HttpServletRequest request){
		JSONObject json = new JSONObject();
		HashMap<String, Object> map = Pagination.getOrderByAndPage(RequestUtil.getRequestParam(request), request);
		String ascOrdesc = request.getParameter("ascOrdesc");
			if(null != ascOrdesc){
				if(ascOrdesc.equals("1")){
					map.put("orderBy", "name");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("2")){
					map.put("orderBy", "name");
					map.put("ascOrdesc", "DESC");
				}else if(ascOrdesc.equals("3")){
					map.put("orderBy", "area");
					map.put("ascOrdesc", "ASC");
				}else if(ascOrdesc.equals("4")){
					map.put("orderBy", "area");
					map.put("ascOrdesc", "DESC");
				}
		}
		List<Student> stuList = studentService.selectListByCondition(map);
		Integer total = studentService.selectCountByCondition(map);
		Result.gridData(stuList, total, json);
		Result.success(json,"数据加载成功", null);
		return json;
	}
	@ResponseBody
	@RequestMapping(value = "/getStudentArray", method=RequestMethod.POST)
	public JSONArray getStudentArray(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mainStatus", 1);
			List<Student> stuList = studentService.selectListByCondition(map);
			for(Student h:stuList){
				JSONObject json = new JSONObject();
				json.put("id",h.getId());
				json.put("name",h.getName());
				array.add(json);
			}
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/back/getUserArray-加载用户数组列表出现异常", array);
		}
		return array;
	}
	

	@ResponseBody
	@RequestMapping(value = "/addOrUpdateStudent", method = RequestMethod.POST)
	public JSONObject addOrUpdateStudent(@ModelAttribute Student student,HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			Admin admin = ActionUtil.checkSession4Admin(request.getSession());//验证session中的user，存在即返回
			if(null == admin)return Result.adminSessionInvalid(json);
			if(Check.isNull(student.getAvatar())){
				student.setAvatar(AssetsPath.defaultUserAvatar);
			}
			if(null == student.getId()){//添加
				studentService.insert(student);
			}else{//修改
				studentService.updateByPrimaryKeySelective(student);
			}
			json.put("id", student.getId());
			Result.success(json, "添加或修改用户成功", null);
		}catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/back/addOrUpdateStudent-新增或修改用户出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateStudentDelete",method=RequestMethod.POST)
	public JSONObject updateStudentDelete(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			studentService.updateDeletedNowByIds(map);
			Result.success(json, "数据删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/back/updateUserDelete-删除用户出现异常", json);
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateStudentRecover",method=RequestMethod.POST)
	public JSONObject updateStudentRecover(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			studentService.updateDeletedNullByIds(ids);
			Result.success(json, "数据恢复成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/back/updateUserRecover-恢复用户出现异常", json);
		}
		return json;
	}

	
	@ResponseBody
	@RequestMapping(value = "/deleteStudentThorough",method=RequestMethod.POST)
	public JSONObject deleteStudentThorough(HttpServletRequest request, @RequestParam(value="ids") String ids) {
		JSONObject json = new JSONObject();
		try {
			studentService.deleteByIds(ids);
			Result.success(json, "数据彻底删除成功", null);
		} catch (Exception e) {
			Result.catchError(e, logger, "LH_ERROR-User-AJAX-/back/deleteUserThorough-彻底删除用户出现异常", json);
		}
		return json;
	}
	

}
