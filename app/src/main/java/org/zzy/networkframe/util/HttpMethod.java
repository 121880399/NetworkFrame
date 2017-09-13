package org.zzy.networkframe.util;

/**
 * 工具类，对Http请求的方法做一些验证
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/12
 */

public class HttpMethod {

    /**
     * 如果是get请求，则方法false
     * get请求没有请求体
     * */
    public static boolean requiresRequestBody(String method){
        return method.equals("GET")? false:true;
    }
}
