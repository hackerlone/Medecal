package com.lhfeiyu.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 权限过滤器 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2013-12-28上午10:20:57 <p>
 * <strong> 修  改  人：</strong>  <p>
 * <strong> 修改时间：</strong>  <p>
 * <strong> 修改描述：</strong>  <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class AuthorityFilter implements Filter {

	@Override
	public void destroy() {}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		  	HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;
	        String requestType = request.getHeader("X-Requested-With");
	        try {
	        	Object userId = request.getSession().getAttribute("userId");
	        	if (userId != null) {
					chain.doFilter(request, response);
				}else{
					String uri = request.getRequestURI();
					if(uri.startsWith("/css") || uri.startsWith("/js")|| uri.startsWith("/third-party") || 
					   uri.startsWith("/images")|| uri.startsWith("/file") || uri.startsWith("/doc")  ||
					   uri.startsWith("/lib")|| uri.startsWith("/temp")  || uri.startsWith("/util")){
						response.setHeader("Cache-Control", "max-age=86400");//1天  
						chain.doFilter(request, response);
					}else{
						if(uri.contains("login") || uri.contains("Login")){
			        		chain.doFilter(request, response);
			        	}else{
			        		if(null != requestType && !"".equals(requestType)){
		    					response.setContentType("application/json;charset=UTF-8");
								response.setContentLength(7);
								PrintWriter out = response.getWriter();
							    out.print("toLogin");
							    out.flush();
							    out.close();
							}else{
								//chain.doFilter(request, response);
								response.sendRedirect("/jumpToLogin");
							}
			        	}
					}
				}
	        	
	        	/*String uri = request.getRequestURI();
	        	if("/login".equals(uri) || "/".equals(uri) || "".equals(uri)){
	        		chain.doFilter(request, response);return;
	        	}
	        	Object userId = request.getSession().getAttribute("userId");
				if (userId != null) {
					chain.doFilter(request, response);
				}else{
					if(null != requestType && !"".equals(requestType)){
						response.sendRedirect("/login");
					}else{
						response.setContentType("application/json;charset=UTF-8");
						response.setContentLength(7);
						PrintWriter out = response.getWriter();
					    out.print("toLogin");
					    out.flush();
					    out.close();
					}
				}*/
	        } catch(Exception e) {
	        	e.printStackTrace();
	        	if(null != requestType && !"".equals(requestType)){
	        		response.sendRedirect("/jumpToLogin");
				}else{
					response.setContentType("application/json;charset=UTF-8");
					response.setContentLength(7);
					PrintWriter out = response.getWriter();
				    out.print("toLogin");
				    out.flush();
				    out.close();
				}
	        }
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
}
