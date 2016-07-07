package com.lhfeiyu.task;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 任务调度：触发器 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2013-12-28上午10:24:48 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class InitializingCronTrigger{
  		/*extends CronTriggerBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public InitializingCronTrigger() {
			String cronExpression = getCronExpressionFromDB();
			try {
				super.setCronExpression(cronExpression);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		private String getCronExpressionFromDB(){
	    	//这里应该是从数据库读取触发时间规则，目前暂时直接返回指定字符串。
			return "0 15 10 15 * ?";//每月15号10点15
	        //return "0,10,20,30,40,50 * * * * ?";//每隔10秒调用一次
	}*/
}
