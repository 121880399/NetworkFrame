package org.zzy.networkframe;

import org.zzy.networkframe.intf.Call;
import org.zzy.networkframe.intf.Interceptor;
import org.zzy.networkframe.request.Request;
import org.zzy.networkframe.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/20
 */

public class RealCall implements Call {

    RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;

    private EventListener eventListener;

    Request originalRequest;

    private boolean executed;

    private Dispatcher dispatcher;


    @Override
    public Request request() {
        return originalRequest;
    }

    @Override
    public Response execute() throws IOException {
        synchronized (this){
            if (executed) throw new IllegalStateException("Already Executed");
            executed=true;
        }
        //captureCallStackTrace();
        eventListener.callStart(this);
        try{
            dispatcher.executed(this);
            Response result=getResponseWithInterceptorChain();
            if(result==null) throw new IOException("Canceled");
            return result;
        }catch (IOException e){
            eventListener.callFailed(this,e);
        }finally {
            dispatcher.finished(this);
        }
        return null;
    }



    @Override
    public void enqueue(Callback responseCallback) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    class AsyncCall extends NamedRunnable {

        private final Callback responseCallback;

        AsyncCall(Callback responseCallback){
            super("http %s","zzy");
            this.responseCallback=responseCallback;
        }

        @Override
        protected void execute() {
            boolean signalledCallback=false;
            try{
                Response response=getResponseWithInterceptorChain();
                if(retryAndFollowUpInterceptor.isCanceled()){
                    signalledCallback=true;
                    responseCallback.onFailure(RealCall.this,new IOException("Canceled"));
                }else{
                    signalledCallback=true;
                    responseCallback.onResponse(RealCall.this,response);
                }

            }catch (IOException e){
                if(signalledCallback){

                }else{
                    eventListener.callFailed(RealCall.this,e);
                    responseCallback.onFailure(RealCall.this,e);
                }
            }
            finally {
                dispatcher.finished(this);
            }
        }
    }

    Response getResponseWithInterceptorChain()throws IOException{
        List<Interceptor> interceptors=new ArrayList<>();
        interceptors.add(retryAndFollowUpInterceptor);

    }
}
