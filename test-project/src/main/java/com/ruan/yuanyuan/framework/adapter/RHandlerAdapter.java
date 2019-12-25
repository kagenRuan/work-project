package com.ruan.yuanyuan.framework.adapter;

import com.ruan.yuanyuan.framework.handler.RHander;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 15:01
 * version:
 * Description:
 */
public class RHandlerAdapter {

    private Map<String, Object> paramMapping;

    public RHandlerAdapter(Map<String, Object> paramMapping) {
        this.paramMapping = paramMapping;
    }


    public void handle(HttpServletRequest request, HttpServletResponse response, RHander handler) throws Exception {

        Class<?>[] clazz = handler.method.getParameterTypes();

        //获取请求中的参数列表
        Map<String, String[]> params = request.getParameterMap();
        //参数的值
        Object[] paramValues = new Object[clazz.length];

        for (Map.Entry<String, String[]> param : params.entrySet()) {

            //替换掉参数上面的[]符号
            String paramValue = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");

            if (!paramMapping.containsKey(param.getKey())) {
                continue;
            }

            int index = (Integer) paramMapping.get(param.getKey());
            paramValues[index] = castStringValue(paramValue, clazz[index]);

        }


        //把request和response进行赋值
        int requestIndex = (Integer) paramMapping.get(HttpServletRequest.class.getName());
        int responseIndex = (Integer) paramMapping.get(HttpServletResponse.class.getName());

        paramValues[requestIndex] = request;
        paramValues[responseIndex] = response;


        handler.method.invoke(handler.controller, paramValues);


    }


    private Object castStringValue(String value, Class clazz) {

        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == int.class) {
            return Integer.valueOf(value).intValue();
        } else {
            return null;
        }

    }
}
