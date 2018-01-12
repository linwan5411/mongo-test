package com.mymongo.filter;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数过滤器
 * @author lyj
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns  = "/*")
public class ParamWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /**
         * 重新构造request中的参数
         */
        Map<String, String[]> m = new HashMap<String, String[]>(request.getParameterMap());
        request = new ParameterRequestWrapper((HttpServletRequest) request, m);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
