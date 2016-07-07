package com.lhfeiyu.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * <strong> 描&nbsp;&nbsp;&nbsp;&nbsp;述：</strong> 编码过滤器 <p>
 * <strong> 作&nbsp;&nbsp;&nbsp;&nbsp;者：</strong> 虞荣华 <p>
 * <strong> 编写时间：</strong> 2015年1月28日上午8:58:49 <p>
 * <strong> 公&nbsp;&nbsp;&nbsp;&nbsp;司：</strong> 成都蓝海飞鱼科技有限公司 <p>
 * <strong> 版&nbsp;&nbsp;&nbsp;&nbsp;本：</strong> 1.0 <p>
 */
public class EncodeFilter implements Filter {

	private FilterConfig config;  
    // 初始化配置参数（从web.xml配置中获取） 
    private static final String INIT_PARAM_ENCODE = "encode";  
  
    @Override  
    public void init(FilterConfig config) throws ServletException {  
        this.config = config;  
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
        String encode = config.getInitParameter(INIT_PARAM_ENCODE);  
        if (encode != null && !encode.isEmpty()) {  
            request.setCharacterEncoding(encode);  
        }  
        chain.doFilter(request, response);  
        if (encode != null && !encode.isEmpty()) {  
            response.setCharacterEncoding(encode); 
        }  
    }  
  
    @Override  
    public void destroy() {  
    	
    }  

}
