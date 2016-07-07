package com.lhfeiyu.interceptor;

/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 权限拦截器     <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2015-1-26下午1:17:26 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class AuthorityInterceptor {// extends AbstractInterceptor
	
	/*private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		Map<String,Object> session = ctx.getSession();
		Object userId =  session.get("userId");
		if(userId!=null){
			return invocation.invoke();
		}
		else{
		   ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		   ServletActionContext.getResponse().setContentLength(7);
		   PrintWriter out = ServletActionContext.getResponse().getWriter();
		   out.print("toLogin");
		   out.flush();
		   out.close();
		   return Action.SUCCESS;
		}
	}*/
}
