package org.zzy.networkframe.response;

import org.zzy.networkframe.Headers;
import org.zzy.networkframe.request.Request;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/20
 */

public class Response {
    Request request;
    int code;
    String message;
    ResponseBody body;

    Headers header;
    //来自网络放回的response
    Response networkResponse;
    //从缓存中取出的respones
    Response cacheResponse;
    Response priorResponse;

    Response(Builder builder){
        this.request=builder.request;
        this.code=builder.code;
        this.message=builder.message;
        this.body=builder.body;
        this.networkResponse=builder.networkResponse;
        this.cacheResponse=builder.cacheResponse;
        this.priorResponse=builder.priorResponse;
        this.header=builder.header.build();
    }

    public Request getRequest(){
        return this.request;
    }

    public int getCode(){return code;}

    public String getMessage(){return message;}

    public ResponseBody getBody(){
        return body;
    }

    public Response getNetworkResponse(){
        return networkResponse;
    }

    public Response getCacheResponse(){
        return cacheResponse;
    }

    public Response getPriorResponse(){
        return priorResponse;
    }

    public Headers getHeader(){
        return header;
    }

    public static class Builder{
        Request request;
        int code=-1;
        String message;
        Headers.Builder header;

        ResponseBody body;
        //来自网络放回的response
        Response networkResponse;
        //从缓存中取出的respones
        Response cacheResponse;
        Response priorResponse;

        public Builder(){ header=new Headers.Builder();}

        public Builder request(Request request){
            this.request=request;
            return this;
        }

        public Builder message(String message){
            this.message=message;
            return this;
        }

        public Builder code(int code){
            this.code=code;
            return this;
        }

        public Builder addHeader(String name,String value){
            header.add(name,value);
            return this;
        }

        public Builder body(ResponseBody body){
            this.body=body;
            return this;
        }

        public Builder networkResponse(Response networkResponse){
            this.networkResponse=networkResponse;
            return this;
        }

        public Builder cacheResponse(Response cacheResponse){
            this.cacheResponse=cacheResponse;
            return this;
        }

        public Builder priorResponse(Response priorResponse){
            this.priorResponse=priorResponse;
            return this;
        }

        public Response build(){
            if(request==null) throw new IllegalStateException("reqeust==null");
            if(code<0) throw new IllegalStateException("code < 0: " + code);
            if(message==null) throw new IllegalStateException("message == null");
            return new Response(this);
        }

    }




}
