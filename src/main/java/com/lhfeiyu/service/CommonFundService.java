package com.lhfeiyu.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lhfeiyu.config.ConstField;
import com.lhfeiyu.dao.AccountLogMapper;
import com.lhfeiyu.dao.NoticeMapper;
import com.lhfeiyu.dao.UserFundMapper;
import com.lhfeiyu.dao.UserMapper;
import com.lhfeiyu.po.AccountLog;
import com.lhfeiyu.po.Notice;
import com.lhfeiyu.po.User;
import com.lhfeiyu.po.UserFund;
import com.lhfeiyu.thirdparty.wx.business.Message;
import com.lhfeiyu.tools.Check;
import com.lhfeiyu.tools.CommonGenerator;
import com.lhfeiyu.tools.DateFormat;
import com.lhfeiyu.util.Md5Util;

/**
* <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 业务层：通用-资金相关操作 <p>
* <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
* <strong> 编写时间：</strong> 2016年3月1日20:32:42 <p>
* <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
* <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 2.0 <p>
 */
@Service
public class CommonFundService extends CommonService<UserFund> {
	@Autowired
	UserFundMapper userFundMapper;
	@Autowired
	AccountLogMapper accountLogMapper;
	@Autowired
	NoticeMapper noticeMapper;
	@Autowired
	UserMapper userMapper;
	
	public UserFund selectUserFundByUserId(Integer userId){
		return userFundMapper.selectUserFundByUserId(userId);
	}

	/**
	 * 私用方法-用户账户资金变更
	 * @param totalMoney 支付密码
	 * @param totalMoney 资金金额
	 * @param userId 用户ID
	 * @param username 用户名
	 * @param msg 消息字符串
	 * @return JSONObject msg,code
	 */
	private JSONObject updateUserMoney(String payPass, String optType, BigDecimal totalMoney, int userId, String username, JSONObject json, boolean noPassword,String theOtherName){
		UserFund uf = userFundMapper.selectUserFundByUserId(userId);
		if(null == uf){ 
			json.put("msg", "用户资金存在异常");
			json.put("error_desc", "userFund_null");
			return json; 
		}
		if(null == totalMoney || totalMoney.doubleValue() <= 0){ 
			json.put("msg", "金额不正确，无法进行操作");
			json.put("error_desc", "money_nullOrNagetive");
			return json; 
		}
		if(noPassword){
			// 系统操作，不用输入密码
		}else{
			if(null != optType &&  !optType.equals("incomeMoney") && !optType.equals("outcomeFrozenMoney")){//收入资金和从交结资金中支出不验证密码
				String pass = uf.getPayPassword();
				if(null == pass || "".equals(pass)){ 
					json.put("msg", "您目前尚未设置支付密码，为了您的账户安全，请先设置支付密码");
					json.put("error_desc", "payPass_null");
					return json; 
				}
				payPass = Md5Util.encrypt(payPass);
				if("".equals(payPass) || !payPass.equals(pass)){ 
					json.put("msg", "您输入的支付密码不正确");
					json.put("error_desc", "payPass_error");
					return json; 
				}
			}
		}
		double avaliableMoney = uf.getAvaliableMoney().doubleValue();
		double frozenMoney = uf.getFrozenMoney().doubleValue();
		double money = totalMoney.doubleValue();
		uf.setAvaliableMoney(null);//先清空资金，下面跟据情况更新
		uf.setFrozenMoney(null);
		String tip_type = "";
		if(optType.equals("freezeMoney")){//冻结资金
			tip_type = "冻结资金";
			if(avaliableMoney <= 0 || avaliableMoney < money){
				json.put("msg", "账户可用余额不足，请先充值");
				json.put("error_desc", "avaliableMoney_lack");
				return json; 
			}
			avaliableMoney = avaliableMoney - money;
			frozenMoney = frozenMoney + money;
			uf.setFrozenMoney(new BigDecimal(frozenMoney));
			uf.setAvaliableMoney(new BigDecimal(avaliableMoney));
		}else if(optType.equals("activateMoney")){//解冻资金
			tip_type = "解冻资金";
			if(frozenMoney <= 0 || frozenMoney < money){
				json.put("msg", "账户冻结资金小于解冻金额，操作失败");
				json.put("error_desc", "frozenMoney_lessThan_activate");
				return json; 
			}
			avaliableMoney = avaliableMoney + money;
			frozenMoney = frozenMoney - money;
			uf.setFrozenMoney(new BigDecimal(frozenMoney));
			uf.setAvaliableMoney(new BigDecimal(avaliableMoney));
		}else if(optType.equals("incomeMoney")){//增加资金
			tip_type = "增加可用资金";
			avaliableMoney = avaliableMoney + money;
			uf.setAvaliableMoney(new BigDecimal(avaliableMoney));
		}else if(optType.equals("outcomeMoney")){//减少资金
			tip_type = "扣除可用资金";
			if(avaliableMoney <= 0 || avaliableMoney < money){
				json.put("msg", "账户可用余额不足，请先充值");
				json.put("error_desc", "avaliableMoney_lack");
				return json; 
			}
			avaliableMoney = avaliableMoney - money;
			uf.setAvaliableMoney(new BigDecimal(avaliableMoney));
		}else if(optType.equals("outcomeFrozenMoney")){//减少冻结资金
			tip_type = "扣除冻结资金";
			if(frozenMoney <= 0 || frozenMoney < money){
				json.put("msg", "账户冻结资金小于支出金额，操作失败");
				json.put("error_desc", "frozenMoney_lessThan_outcome");
				return json; 
			}
			frozenMoney = frozenMoney - money;
			uf.setFrozenMoney(new BigDecimal(frozenMoney));
		}else{
			json.put("msg", "无法视别操作类型");
			json.put("error_desc", "type_error");
			return json; 
		}
		uf.setUpdatedBy(username);
		uf.setUpdatedAt(new Date());
		userFundMapper.updateMoneyById(uf);
		Notice notice = new Notice();//账户资金变动通知
		notice.setTypeId(13);//dict-13:账户资金变动通知
		notice.setReceiverId(userId);
		//notice.setReadStatus(1);
		String serial = CommonGenerator.getSerialByDate("n");
		notice.setSerial(serial);
		notice.setTitle("账户资金变动通知");
		String content = "账户资金变动通知:操作:"+tip_type+"，金额:"+totalMoney+"，对方名称："+theOtherName;
		notice.setContent(content);
		notice.setCreatedAt(new Date());
		if(null != username){
			notice.setCreatedBy(username);
		}else{
			notice.setCreatedBy("admin");
		}
		noticeMapper.insertSelective(notice);
		User user = userMapper.selectByPrimaryKey(userId);
		
		String openId = user.getThirdName();
		//微信消息通知
		if(Check.isNotNull(openId)){
			Date d = new Date();
			String dateStr = DateFormat.simple.format(d);
			String firstValue = "账户资金变动通知";
			String attr1Value = tip_type;
			String attr2Value = totalMoney.toString()+"元";
			JSONObject messageJson = Message.buildMsg(openId, ConstField.wx_moban_2, ConstField.page_user_center, 
							 "first", firstValue, "tradeType", attr1Value, "curAmount", attr2Value, 
							 "tradeDateTime", dateStr, null, null, "remark", ConstField.wx_message_remark);
			Message.sendMessage(ConstField.wx_moban_2, messageJson);//JSONObject resultJson = Message.sendMessage(ConstField.wx_moban_10, messageJson);
		}
		return json;
	}
	
	/**
	 * 冻结用户账户资金（同时新增账户资金变动记录）
	 * @param totalMoney 支付密码
	 * @param totalMoney 资金金额
	 * @param userId 用户ID
	 * @param username 用户名
	 * @param tradeTypeId 交易类型ID（dict:fatherId-140）
	 * @param theOtherId 对方ID
	 * @param theOtherName 对方名称
	 * @return JSONObject msg,code
	 */
	public JSONObject freezeMoney(String payPass, BigDecimal totalMoney, int userId, String username,
			Integer tradeTypeId, Integer theOtherId, String theOtherName, JSONObject json, boolean noPassword){
		//inOrOut 收入或支出(1，收入2，支出)
		//tradeStatus 交易状态(1冻结，2解冻，3实际支出，4实际收入)
		json = updateUserMoney(payPass, "freezeMoney", totalMoney, userId, username, json, noPassword,theOtherName);
		if(json.containsKey("error_desc")){ return json; }
		json = addAccountLog(totalMoney, userId, username, 2, 1, tradeTypeId, theOtherId, theOtherName, json);
		return json;
	}
	
	/**
	 * 激活用户账户资金-解冻（同时新增账户资金变动记录）
	 * @param totalMoney 支付密码
	 * @param totalMoney 资金金额
	 * @param userId 用户ID
	 * @param username 用户名
	 * @param tradeTypeId 交易类型ID（dict:fatherId-140）
	 * @param theOtherId 对方ID
	 * @param theOtherName 对方名称
	 * @return JSONObject msg,code
	 */
	public JSONObject activateMoney(String payPass, BigDecimal totalMoney, int userId, String username, 
			Integer tradeTypeId, Integer theOtherId, String theOtherName, JSONObject json, boolean noPassword){
		json = updateUserMoney(payPass, "activateMoney", totalMoney, userId, username, json, noPassword,theOtherName);
		if(json.containsKey("error_desc")){ return json; }
		json = addAccountLog(totalMoney, userId, username, 1, 2, tradeTypeId, theOtherId, theOtherName, json);
		return json;
	}
	
	/**
	 * 增加用户账户资金-收入（同时新增账户资金变动记录）
	 * @param totalMoney 支付密码
	 * @param totalMoney 资金金额
	 * @param userId 用户ID
	 * @param username 用户名
	 * @param tradeTypeId 交易类型ID（dict:fatherId-140）
	 * @param theOtherId 对方ID
	 * @param theOtherName 对方名称
	 * @return JSONObject msg,code
	 */
	public JSONObject incomeMoney(String payPass, BigDecimal totalMoney, int userId, String username, 
			Integer tradeTypeId, Integer theOtherId, String theOtherName, JSONObject json, boolean noPassword){
		json = updateUserMoney(payPass, "incomeMoney", totalMoney, userId, username, json, noPassword,theOtherName);
		if(json.containsKey("error_desc")){ return json; }
		json = addAccountLog(totalMoney, userId, username, 1, 4, tradeTypeId, theOtherId, theOtherName, json);
		return json;
	}
	
	/**
	 * 减少用户账户资金-支出（同时新增账户资金变动记录）
	 * @param totalMoney 支付密码
	 * @param totalMoney 资金金额
	 * @param userId 用户ID
	 * @param username 用户名
	 * @param tradeTypeId 交易类型ID（dict:fatherId-140）
	 * @param theOtherId 对方ID
	 * @param theOtherName 对方名称
	 * @return JSONObject msg,code
	 */
	public JSONObject outcomeMoney(String payPass, BigDecimal totalMoney, int userId, String username,
			Integer tradeTypeId, Integer theOtherId, String theOtherName, JSONObject json, boolean noPassword){
		json = updateUserMoney(payPass, "outcomeMoney", totalMoney, userId, username, json, noPassword,theOtherName);
		if(json.containsKey("error_desc")){ return json; }
		json = addAccountLog(totalMoney, userId, username, 2, 3, tradeTypeId, theOtherId, theOtherName, json);
		return json;
	}
	
	/**
	 * 从用户账户冻解资金中扣除（同时新增账户资金变动记录）
	 * @param totalMoney 支付密码
	 * @param totalMoney 资金金额
	 * @param userId 用户ID
	 * @param username 用户名
	 * @param tradeTypeId 交易类型ID（dict:fatherId-140）
	 * @param theOtherId 对方ID
	 * @param theOtherName 对方名称
	 * @return JSONObject msg,code
	 */
	public JSONObject outcomeFrozenMoney(String payPass, BigDecimal totalMoney, int userId, String username,
			Integer tradeTypeId, Integer theOtherId, String theOtherName, JSONObject json, boolean noPassword){
		json = updateUserMoney(payPass, "outcomeFrozenMoney", totalMoney, userId, username, json, noPassword,theOtherName);
		if(json.containsKey("error_desc")){ return json; }
		json = addAccountLog(totalMoney, userId, username, 2, 5, tradeTypeId, theOtherId, theOtherName, json);
		return json;
	}
	
	/**
	 * 从用户账户可用资金中转移资金到指定用户可用资金中（同时新增账户资金变动记录）
	 * @param totalMoney 支付密码（发起人）
	 * @param totalMoney 资金金额
	 * @param userId 用户ID（发起人）
	 * @param username 用户名（发起人）
	 * @param tradeTypeId 交易类型ID（dict:fatherId-140）
	 * @param theOtherId 对方ID（接起人）
	 * @param theOtherName 对方名称（接起人）
	 * @return JSONObject msg,code
	 */
	public JSONObject outcomeMoneyToUserAvaliable(String payPass, BigDecimal totalMoney, int userId, String username,
			Integer tradeTypeId, int receiverId, String receiverName, JSONObject json, boolean noPassword){
		json = updateUserMoney(payPass, "outcomeMoney", totalMoney, userId, username, json, noPassword,receiverName);
		if(json.containsKey("error_desc")){ return json; }
		json = addAccountLog(totalMoney, userId, username, 2, 3, tradeTypeId, receiverId, receiverName, json);
		if(json.containsKey("error_desc")){ return json; }
		json = updateUserMoney(null, "incomeMoney", totalMoney, receiverId, receiverName, json, noPassword,receiverName);
		if(json.containsKey("error_desc")){ return json; }
		json = addAccountLog(totalMoney, receiverId, receiverName, 1, 4, tradeTypeId, userId, username, json);
		return json;
	}
	
	/**
	 * 从用户账户交结资金中转移资金到指定用户可用资金中（同时新增账户资金变动记录）
	 * @param totalMoney 支付密码（发起人）
	 * @param totalMoney 资金金额
	 * @param userId 用户ID（发起人）
	 * @param username 用户名（发起人）
	 * @param tradeTypeId 交易类型ID（dict:fatherId-140）
	 * @param theOtherId 对方ID（接起人）
	 * @param theOtherName 对方名称（接起人）
	 * @return JSONObject msg,code
	 */
	public JSONObject outcomeFrozenMoneyToUserAvaliable(String payPass, BigDecimal totalMoney, int userId, String username,
			Integer tradeTypeId, int receiverId, String receiverName, JSONObject json, boolean noPassword){
		json = updateUserMoney(payPass, "outcomeFrozenMoney", totalMoney, userId, username, json, noPassword,receiverName);
		if(json.containsKey("error_desc")){ return json; }
		json = addAccountLog(totalMoney, userId, username, 2, 5, tradeTypeId, receiverId, receiverName, json);
		if(json.containsKey("error_desc")){ return json; }
		json = updateUserMoney(null, "incomeMoney", totalMoney, receiverId, receiverName, json, noPassword,receiverName);
		if(json.containsKey("error_desc")){ return json; }
		json = addAccountLog(totalMoney, receiverId, receiverName, 1, 4, tradeTypeId, userId, username, json);
		return json;
	}
	
	/**
	 * 新增账户资金变动记录
	 * @param totalMoney	资金金额
	 * @param userId 用户ID
	 * @param username 用户名
	 * @param inOrOut 收入或支出(1，收入2，支出)
	 * @param tradeStatus 交易状态(1冻结，2解冻，3实际支出，4实际收入，5冻结资金中支出)
	 * @param tradeTypeId 交易类型ID（dict:fatherId-140）141:普通商品交易142：批发
	 * @param theOtherId 对方ID
	 * @param theOtherName 对方名称
	 * @param msg 消息字符串
	 * @return JSONObject msg,code
	 */
	public JSONObject addAccountLog(BigDecimal totalMoney, int userId, String username, Integer inOrOut, 
			Integer tradeStatus, Integer tradeTypeId, Integer theOtherId, String theOtherName, JSONObject json){
		UserFund uf = userFundMapper.selectUserFundByUserId(userId);
		if(null == uf){
			json.put("msg", "用户资金存在异常");
			json.put("error_desc", "userFund_null");
			return json; 
		}
		Date date = new Date();
		AccountLog  accountLog = new AccountLog();
		String serial = CommonGenerator.getSerialByDate("al");
		accountLog.setSerial(serial);
		accountLog.setUserId(userId);
		accountLog.setUsername(username);
		accountLog.setMoney(totalMoney);
		accountLog.setInOrOut(inOrOut);
		accountLog.setPayTime(date);
		accountLog.setTheOtherId(theOtherId);
		accountLog.setTheOtherName(theOtherName);;
		accountLog.setTradeTypeId(tradeTypeId);
		accountLog.setTradeStatus(tradeStatus);
		accountLog.setAvaliableMoneyLog(uf.getAvaliableMoney());
		accountLog.setFrozenMoneyLog(uf.getFrozenMoney());
		accountLog.setCreatedAt(date);
		accountLog.setCreatedBy(username);
		accountLogMapper.insert(accountLog);//资金变动记录
		return json;
	}
	
}