package org.zzy.networkframe;

import org.zzy.networkframe.util.Util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/22
 */

public class Dispatcher {
    //最大请求
    private int maxRequest=64;
    //每个host最多5个请求
    private int maxRequestPerHost=5;

    private ExecutorService executorService;

    //准备异步的call
    private final Deque<AsyncCall> readyAsyncCalls=new ArrayDeque<>();

    //正在异步运行的call
    private final Deque<AsyncCall> runningAsyncCalls=new ArrayDeque<>();

    //正在同步运行的call
    private final Deque<RealCall> runningSyncCalls=new ArrayDeque<>();

    public Dispatcher(ExecutorService executorService){
        this.executorService=executorService;
    }

    public Dispatcher(){}

    /**
     * 创建线程池
     * */
    public synchronized  ExecutorService executorService(){
        if(executorService==null){
            executorService=new ThreadPoolExecutor(0,Integer.MAX_VALUE,60, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), Util.threadFactory("http Disathcer",false));
        }
        return executorService;
    }

    /**
     * 设置最大请求
     * */
    public synchronized  void setMaxRequest(int maxRequest){
        if(maxRequest<1){
            throw new IllegalArgumentException("max < 1 :" + maxRequest);
        }
        this.maxRequest=maxRequest;
        promoteCalls();
    }

    /**
     * 得到最大请求
     * */
    public synchronized int getMaxRequest(){
        return maxRequest;
    }

    /**
     * 设置对于每一个host的最大请求
     * */
    public synchronized  void setMaxRequestPerHost(int maxRequestPerHost){
        if(maxRequestPerHost <1){
            throw new IllegalArgumentException("max < 1: " + maxRequestPerHost);
        }
        this.maxRequestPerHost=maxRequestPerHost;
        promoteCalls();
    }

    /**
     * 得到对于每一个host的最大请求
     * */
    public synchronized  int getMaxRequestPerHost(){
        return maxRequestPerHost;
    }

    /**
     * 只针对异步call
     * 把准备中的异步call 提升到正在运行状态
     * */
    private void promoteCalls(){
        //正在运行的异步call已经超过了最大容量
        if(runningAsyncCalls.size()>=maxRequest)return;
        //准备运行的异步call为空,没有call可以提升
        if(readyAsyncCalls.isEmpty())return;

        for(Iterator<AsyncCall> i=readyAsyncCalls.iterator();i.hasNext();){
            AsyncCall call=i.next();

            if(runningCallsForHost(call)<maxRequestPerHost){
                i.remove();
                runningAsyncCalls.add(call);
                executorService().execute(call);
            }

            //正在运行的异步call数量已经超过最大数量则停止
            if(runningAsyncCalls.size() >= maxRequest ) return;
        }
    }

    /**
     * 对于同一个host，有几个在正在运行的队列中
     * */
    private int runningCallsForHost(AsyncCall call){
        int result=0;
        for(AsyncCall c:runningAsyncCalls){
            if(c.host().equals(call.host())) result++;
        }
        return result;
    }


    synchronized void executed(RealCall call){
        runningSyncCalls.add(call);
    }

    /**
     * 返回正在运行的异步call和同步call的数量
     * */
    public synchronized int runningCallsCount(){
        return runningAsyncCalls.size()+runningSyncCalls.size();
    }
}
