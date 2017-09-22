package org.zzy.networkframe.intf;

import org.zzy.networkframe.Callback;
import org.zzy.networkframe.request.Request;
import org.zzy.networkframe.response.Response;

import java.io.IOException;

/**
 *
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/20
 */

public interface Call {

    /**
     * 初始化这个call,返回原始的Request
     * */
    Request request();

    /**
     * 同步执行
     * */
    Response execute()throws IOException;


    /**
     * 异步执行
     * */
    void enqueue(Callback responseCallback);

    /**
     * 如果还没有完成就取消请求
     * */
    void cancel();


    /**
     * 如果call被execute或者enqueue执行返回true
     * */
    boolean isExecuted();

    /**
     * 是否被取消
     * */
    boolean isCanceled();



}
