package com.lhfeiyu.config;
/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 路径类 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2015年7月4日上午11:39:11 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class PagePath {
	
	
	/** 公共页面 开始 */
	public static final String test = "/test";//测试页面
    public static final String error = "/front/index";//错误页面
    public static final String jumpToLogin = "/front/toLogin";//跳转到前台登陆页面
    public static final String jumpToBackLogin = "/back/base/login/jumpToBackLogin";//跳转到后台登陆页面
    /** 公共页面 开始 */
    
    
    /**前台页面 开始*/
    public static final String frontIndex = "/front/index";//首页
    public static final String index = "/front/index";//首页
    public static final String frontStudent = "/back/base/student/student";//
    public static final String Stuadd = "/back/base/student/Stuadd";//
    
    public static final String frontDoctorHome = "/front/domain/doctor/doctorHome";//医生首页
    public static final String frontDoctor = "/front/domain/doctor/doctor";//医生
    public static final String diagnoseList = "/front/domain/doctor/diagnoseList";//病历夹列表
    public static final String diagnoseTemplateList = "/front/domain/doctor/diagnoseTemplateList";//病历夹模板列表
    public static final String diagnoseListForApply = "/front/domain/doctor/diagnoseListForApply";//可供申请查看的病历列表
    public static final String diagnose = "/front/domain/doctor/diagnose";//病历详情
    public static final String diagnoseTemplate = "/front/domain/doctor/diagnoseTemplate";//病历模板详情
    public static final String diagnoseRead = "/front/domain/doctor/diagnoseRead";//病历查看
    public static final String patientLibrary = "/front/domain/doctor/patientLibrary";//患者库
    public static final String doctorArticle = "/front/domain/doctor/article";//文章
    public static final String doctorArticleDetails = "/front/domain/doctor/articleDetails";//文章详情
    public static final String doctorInformation = "/front/domain/doctor/information";//资讯
    public static final String doctorBespeak = "/front/domain/doctor/bespeak";//预约
    public static final String doctorMessage = "/front/domain/doctor/message";//留言
    public static final String doctorChat = "/front/domain/doctor/chat";//消息
    public static final String doctorConsult = "/front/domain/doctor/consult";//咨询
    public static final String doctorBaseInformation = "/front/domain/doctor/doctorBaseInformation";//医生基本信息
    public static final String doctorDiagnoseApply = "/front/domain/doctor/diagnoseApply";//医生授权
    public static final String addOrUpdatePatient = "/front/domain/doctor/addOrUpdatePatient";//添加患者
    public static final String doctorConsultDetail = "/front/domain/doctor/consultDetail";//咨询详情
    public static final String messageDetail = "/front/domain/doctor/messageDetail";//留言详情
    public static final String doctorList = "/front/domain/doctor/doctorList";//留言详情
    public static final String bespeakDeatil = "/front/domain/doctor/bespeakDeatil";//预约详情
    public static final String doctorUserReportDetail = "/front/domain/doctor/doctorUserReportDetail";//预约详情
    public static final String doctorUserReportList = "/front/domain/doctor/doctorUserReportList";//预约详情
    
    public static final String treatment = "/front/domain/treatment/treatment";//就诊
    public static final String doctorBloodTest = "/front/domain/treatment/doctorBloodTest";//血液检测
    
    public static final String frontHospital = "/front/domain/hospital/hospital";//诊所
    public static final String frontHospitalHome = "/front/domain/hospital/hospitalHome";//诊所展示首页
    public static final String updateHospital = "/front/domain/hospital/updateHospital";//诊所修改信息
    public static final String addOrUpdateAnnouncementForHospital = "/front/domain/hospital/addOrUpdateAnnouncement";//诊所文章、活动等发布
    public static final String addOrUpdateDoctorForHospital = "/front/domain/hospital/addOrUpdateDoctor";//诊所添加修改医生
    public static final String hospitalDoctor = "/front/domain/hospital/doctor";//诊所医生
    public static final String hospitalBespeak = "/front/domain/hospital/bespeak";//诊所预约
    public static final String medication = "/front/domain/hospital/medication";//诊所药品
    public static final String medicationLog = "/front/domain/hospital/medicationLog";//诊所药品记录
    public static final String addOrUpdateMedicationLog = "/front/domain/hospital/addOrUpdateMedicationLog";//诊所药品记录更新
    public static final String addOrUpdateMedication = "/front/domain/hospital/addOrUpdateMedication";//诊所添加修改药品信息
    public static final String addOrUpdateBespeakForHospital = "/front/domain/hospital/addOrUpdateBespeak";//诊所添加修改预约
    public static final String hospitalPatient = "/front/domain/hospital/patient";//诊所患者
    public static final String hospitalDiagnoseApply = "/front/domain/hospital/diagnoseApply";//授权
    public static final String hospitalDiagnoseTemplate = "/front/domain/hospital/diagnoseTemplate";//病历模板
    public static final String addOrUpdateDiagnoseTemplateForHospital = "/front/domain/hospital/addOrUpdateDiagnoseTemplate";//添加病历模板
    public static final String hospitalDiagnoseRead = "/front/domain/hospital/diagnoseRead";//
    public static final String hospitalDiagnoseList = "/front/domain/hospital/diagnoseList";//
    public static final String hospitalDiagnoseSumList = "/front/domain/hospital/diagnoseSumList";//
    public static final String hospitalArticle = "/front/domain/hospital/article";//
    public static final String hospitalArticleDetails = "/front/domain/hospital/articleDetails";//
    public static final String hospitalUserReportList = "/front/domain/hospital/hospitalUserReportList";//
    public static final String hospitalUserReportDetail = "/front/domain/hospital/hospitalUserReportDetail";//
    public static final String hospitalNurseDetail = "/front/domain/hospital/hospitalNurseDetail";//
    public static final String hospitalNurseList = "/front/domain/hospital/hospitalNurseList";//
    
    
    
    public static final String hospitalVolunteerDetail = "/front/domain/hospital/volunteer/hospitalVolunteerDetail";
    public static final String hospitalVolunteerList = "/front/domain/hospital/volunteer/hospitalVolunteerList";//
    
    
    
    public static final String article = "/front/base/article/article";//文章新闻详情页
    public static final String articleList = "/front/base/article/articleList";//文章新闻列表
    public static final String addOrUpdateArticle = "/front/base/article/addOrUpdateArticle";//添加或修改文章
    public static final String commonBaseArtilce = "/front/base/common/baseArticle";//共用 - 基本文章
    
    public static final String frontUser = "/front/domain/user/user";//用户中心
    public static final String consult = "/front/domain/user/consult";//咨询医生
    public static final String userNotice = "/front/domain/user/notice";//消息
    public static final String internalMessage = "/front/domain/user/internalMessage";//站内信
    public static final String consultRecord = "/front/domain/user/consultRecord";//咨询记录
    public static final String userBaseInformation = "/front/domain/user/userBaseInformation";//患者基本信息
    public static final String userDiagnoseList = "/front/domain/user/userDiagnoseList";//病历列表
    public static final String updateDiagnose = "/front/domain/user/updateDiagnose";//病历列表
    public static final String myDoctor = "/front/domain/user/myDoctor";//我的医生
    public static final String bespeakRecord = "/front/domain/user/bespeakRecord";//预约记录
    public static final String consultDetail = "/front/domain/user/consultDetail";//咨询详情
    public static final String relationPatient = "/front/domain/user/relationPatient";//关联患者
    public static final String relationPatientList = "/front/domain/user/relationPatientList";//关联患者列表
    public static final String regularRemindList = "/front/domain/user/regularRemindList";//提醒消息列表
    public static final String addOrUpdateRegularRemind = "/front/domain/user/addOrUpdateRegularRemind";//添加或修改定时提醒
    public static final String hospitalBloodTest = "/front/domain/user/hospitalBloodTest";//
    public static final String consultDoctorList = "/front/domain/user/consultDoctorList";//咨询医生列表
    public static final String userReportList = "/front/domain/user/userReportList";//检测报告
    public static final String userReportDetail = "/front/domain/user/userReportDetail";//检测报告
    
    public static final String searchDoctorOrHospitalList = "/front/domain/user/searchDoctorOrHospitalList";//查询医生或诊所
    public static final String bespeakRecordDetail = "/front/domain/user/bespeakRecordDetail";//预约详情
    
    public static final String userBespeak = "/front/domain/bespeak/bespeak";//预约
    public static final String bespeakChoose = "/front/domain/bespeak/choose";//预约-选择医院或医生
    public static final String bespeakChooseProvinceCity = "/front/domain/bespeak/chooseProvinceCity";//预约-选择省市
    
    
    public static final String personInformation = "/front/user/personInformation";//个人信息
    public static final String accountInformation = "/front/user/accountInformation";//账户信息
    public static final String loginPasswordUpdate = "/front/user/loginPasswordUpdate";//修改用户密码
    public static final String loginPasswordFind = "/front/user/loginPasswordFind";//找回用户密码
    public static final String addReceiveAddress = "/front/user/addReceiveAddress";//添加用户收货地址
    public static final String receiveAddressList = "/front/user/receiveAddressList";//用户收货全部地址
    public static final String realName = "/front/user/realName";//用户认证
    public static final String payPasswordUpdate = "/front/user/payPasswordUpdate";//用户修改支付密码
    public static final String payPasswordFind = "/front/user/payPasswordFind";//用户找回支付密码
    public static final String editUser = "/front/user/editUser";//修改用户信息
    public static final String bindUserPhone = "/front/user/bindUserPhone";//绑定用户的手机号码
    
    
    public static final String addAnnouncement = "/front/addAnnouncement";
    public static final String announcement = "/front/announcement";
    
    public static final String login = "/front/base/login/login";//登录页面
    public static final String doDctorLogin = "/front/base/login/doctorLogin";//医生登录页面
    public static final String doHspitalLogin = "/front/base/login/hospitalLogin";//医疗机构登录页面
    public static final String register = "/front/base/login/register";//注册页面
    public static final String mailCheck = "/front/mailCheck";//激活邮箱验证页面
    public static final String mailReCheck = "/front/mailReCheck";//重新激活邮箱验证页面
    public static final String loginPasswordMailUpdate = "/front/loginPasswordMailUpdate";//找回密码的邮箱
    
    public static final String chat = "/front/chat/chat";//聊天页面
    
    public static final String fans = "/front/fans/fans";//师友页面
    public static final String fansRanking = "/front/fans/fansRanking";//粉丝排名

    /**前台页面 结束*/
    
    /** 后台页面 开始 */
    public static final String backArticle= "/back/base/article/article";//文章
    public static final String backAddOrUpdateArticle = "/back/base/article/addOrUpdateArticle";//新增修改文章
	public static final String backLogin = "/back/base/login/login";//后台主页面
    public static final String backMain = "/back/base/main";//后台主页面
    public static final String backUser = "/back/base/user/user";//用户信息
    
    public static final String backStudent = "/back/base/student/student";
    
    
    public static final String backUserControl = "/back/base/user/userControl";//用户控制
    public static final String userFund = "/back/base/user/userFund";//用户资金
    public static final String backUserRelation = "/back/base/user/userRelation";//用户关联
    
    
    public static final String backMessage = "/back/base/message/message";//消息
    public static final String backNotice = "/back/base/notice/notice";//通知
    public static final String backInternalMessage = "/back/base/internalMessage/internalMessage";//站内信
    public static final String backChat = "/back/base/chat/chat";//聊天
    public static final String backComment = "/back/base/comment/comment";//评论
    public static final String backFans = "/back/base/fans/fans";//粉丝好友
    
    public static final String backPicture = "/back/base/picture/picture";//图片管理
    public static final String backAddOrUpdatePicture = "/back/base/picture/addOrUpdatePicture";//图片管理
    public static final String backAlbum= "/back/base/picture/album";//相册
    
    public static final String backDict = "/back/base/dict/dict";//数据字典
    
    public static final String backAdvertisement = "/back/base/advertisement/advertisement";//数据字典
    public static final String backAddOrUpdateAdvertisement = "/back/base/advertisement/addOrUpdateAdvertisement";//数据字典
    
    public static final String backRegularRemind = "/back/domain/user/regularRemind";//定时提醒
    
    public static final String backPatient = "/back/domain/user/patient";//患者信息
    public static final String backPatientReport = "/back/domain/user/patientReport";//患者报告
    public static final String backPatientReportDetail = "/back/domain/user/patientReportDetail";//患者报告详情
    public static final String backPrdPrint= "/back/domain/user/prdPrint";//患者报告详情打印
    
    public static final String backPhraseRecord = "/back/domain/phraseRecord/phraseRecord";//常用短语
   
    public static final String backCancer = "/back/domain/cancer/cancer";//常用短语
    
    public static final String backBespeak = "/back/domain/bespeak/bespeak";//预约
    
    public static final String backAnnouncement = "/back/base/announcement/announcement";//公告
    public static final String backAddOrUpdateAnnouncement = "/back/base/announcement/addOrUpdateAnnouncement";//
    
    public static final String backHospital = "/back/domain/hospital/hospital";//诊所
    public static final String backDoctor = "/back/domain/hospital/doctor";//医生
    public static final String backDepartment = "/back/domain/hospital/department";//科室
    public static final String backDiagnose = "/back/domain/hospital/diagnose";//病历
    public static final String backDiagnosePrint = "/back/domain/hospital/diagnosePrint";//病历
    public static final String backDiagnoseTemplate = "/back/domain/hospital/diagnoseTemplate";//病历模板
    public static final String backConsult = "/back/domain/hospital/consult";//咨询
    public static final String backNurse = "/back/domain/hospital/nurse";//咨询
    
    public static final String backDataHospital = "/back/domain/data/dataHospital";//诊所数据
    public static final String dataHospitalDetail = "/back/domain/data/dataHospitalDetail";//诊所数据详情
    
    public static final String backMedicationType = "/back/domain/medication/medicationType";//药物类型
    public static final String backMedication = "/back/domain/medication/medication";//药物
    public static final String backMedicationLog = "/back/domain/medication/medicationLog";//药物记录
    public static final String backMedicationRepertory = "/back/domain/medication/medicationRepertory";//
    
    public static final String backApply = "/back/base/apply/apply";//申请
    
    public static final String backOperationLog = "/back/base/sys/operationLog";//操作记录
    
    public static final String backLoginLog = "/back/base/sys/loginLog";//登录日志
    
    public static final String backRoleMenu = "/back/base/sys/roleMenu";//角色菜单
    
    public static final String backQuickMenu = "/back/base/sys/quickMenu";//快捷菜单
    
    public static final String backAdmin = "/back/base/sys/admin";//管理员
    
    public static final String backRole = "/back/base/sys/role";//管理员
    
    
    
    public static final String backProvinceCityArea = "/back/base/provinceCityArea/provinceCityArea";//省市区信息
    
    public static final String backSysArticle = "/back/base/sys/sysArticle";
    public static final String backAddOrUpdateSysArticle = "/back/base/sys/addOrUpdateSysArticle";
    
    /** 后台页面 结束 */
	
	
	
    
	
    
}
