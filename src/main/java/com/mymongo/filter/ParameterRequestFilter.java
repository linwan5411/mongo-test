package com.mymongo.filter;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * request参数转换,用于敏感字符的处理
 * @author lyj
 */
public class ParameterRequestFilter extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public ParameterRequestFilter(HttpServletRequest request) {
        super(request);
    }

    /**
     * 获取request 上面的值(然后进行业务操作)
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        // 返回值之前 先进行过滤
        String[] values = super.getParameterValues(name);
        if(values==null){
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = filterDangerString(values[i]);
        }
        return values;
    }

    /**
     * 用于参数过滤
     * @param value
     * @return
     */
    public String filterDangerString(String value) {
        if(!StringUtils.isEmpty(value) && value instanceof String){
            //修改后面的参数
            return "123456";
        }
        return value;
    }

}
