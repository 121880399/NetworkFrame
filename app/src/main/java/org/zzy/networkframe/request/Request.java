package org.zzy.networkframe.request;

import org.zzy.networkframe.Headers;
import org.zzy.networkframe.util.HttpMethod;

/**
 * Http request请求封装，使用建造者模式构建
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/12
 */

public class Request {
    //url
    String url;
    //方法
    String method;
    //请求头
    Headers headers;
    //请求体
    RequestBody body;

    Request(Builder builder) {
        this.url=builder.url;
        this.method=builder.method;
        this.headers=builder.headers.build();
        this.body=builder.body;
    }

    /**
     * 为了防止内存泄漏，使用静态内部类
     * */
    public static class Builder{
        String url;
        String method;
        Headers.Builder headers;
        RequestBody body;

        public Builder(){
            this.method="GET";
            this.headers=new Headers().Builder();
        }

        public Builder url(String url){
            if(url==null) throw new NullPointerException("url==null");
            this.url=url;
            return this;
        }

        /**
         * 通过Header对象创建Header.Builder对象
         * */
        public Builder header(Headers header){
            this.headers=header.newBuilder();
            return this;
        }

        /**
         * 添加请求头参数
         * */
        public Builder addHeader(String name,String value){
            this.headers.add(name.value);
            return this;
        }

        /**
         * 删除请求头参数
         * */
        public Builder removeHeader(String name){
            headers.removeAll(name);
            return this;
        }


        public Builder method(String method,RequestBody body){
            if(method==null) throw new NullPointerException("method == null");
            if(method.length()==0) throw new IllegalArgumentException("method.length()==0");

            //如果方法不需要有方法体，但是body不为空
            if(body!=null && !HttpMethod.requiresRequestBody(method)){
                throw new IllegalArgumentException("method "+method + "must not have a request body");
            }

            //如果方法需要有方法体，但是body却为空
            if(body == null && HttpMethod.requiresRequestBody(method)){
                throw new IllegalArgumentException("method "+method + "must have a request body");
            }

            this.method=method;
            this.body=body;
            return this;
        }

        public Request build(){
            if(url==null) throw new IllegalArgumentException("url==null");
            return new Request(this);
        }

    }
}
