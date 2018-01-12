package com.mymongo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 配置一个filter，敏感字符的过滤
 * @author lyj
 */
//@Component
//@ServletComponentScan
//@WebFilter(urlPatterns  = "/*")
public class ParamFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new ParameterRequestFilter((HttpServletRequest)servletRequest), servletResponse);
    }

    @Override
    public void destroy() {

    }
}
