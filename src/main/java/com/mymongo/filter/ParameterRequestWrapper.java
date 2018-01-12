package com.mymongo.filter;

import com.mymongo.util.Base64Util;
import com.mymongo.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

/**
 * 用来转换参数,此处用来添加，删除，修改request中传递过程中的参数集
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params;

    public ParameterRequestWrapper(HttpServletRequest request, Map<String, String[]> newParams) {
        super(request);
        this.params = newParams;
        // RequestDispatcher.forward parameter
        renewParameterMap(request);
    }

    @Override
    public String getParameter(String name) {
        String result = "";
        Object v = params.get(name);
        if (v == null) {
            result = null;
        } else if (v instanceof String[]) {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                result = strArr[0];
            } else {
                result = null;
            }
        } else if (v instanceof String) {
            result = (String) v;
        } else {
            result = v.toString();
        }
        return result;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector<String>(params.keySet()).elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] result = null;

        Object v = params.get(name);
        if (v == null) {
            result = null;
        } else if (v instanceof String[]) {
            result = (String[]) v;
        } else if (v instanceof String) {
            result = new String[] { (String) v };
        } else {
            result = new String[] { v.toString() };
        }

        return result;
    }

    /**
     * 用来构造参数
     * @param req
     */
    private void renewParameterMap(HttpServletRequest req) {

        String queryString = req.getQueryString();

        if (queryString != null && queryString.trim().length() > 0) {
            String[] params = queryString.split("&");

            for (int i = 0; i < params.length; i++) {
                int splitIndex = params[i].indexOf("=");
                if (splitIndex == -1) {
                    continue;
                }

                //得到对应的key
                String key = params[i].substring(0, splitIndex);

                //处理得到value
                String val = params[i].substring(splitIndex+1);


                /**
                 * 如果key 没有在其中，那么久添加key
                 */
                if (!this.params.containsKey(key)) {
                    if (splitIndex < params[i].length()) {
                        String value = params[i].substring(splitIndex + 1);
                        this.params.put(key, new String[] { value });
                    }
                }

                /**
                 * 如果是json 那么直接转码
                 */
                if("json".equals(key)){

                    String json = Base64Util.decodeBase64String(val);

                    Map<String,Object> map = JsonUtils.json2Map(json);

                    map.forEach((k,v)->{
                        this.params.put(k, new String[] {(String) v});
                    });
                }

            }
        }
    }

}
