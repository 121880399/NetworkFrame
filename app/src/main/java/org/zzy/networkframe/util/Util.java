package org.zzy.networkframe.util;

import java.util.Locale;
import java.util.concurrent.ThreadFactory;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/22
 */

public class Util {

    public static ThreadFactory threadFactory(final String name,final boolean daemon){
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread result=new Thread(r,name);
                result.setDaemon(daemon);
                return result;
            }
        }
    }
    public static String format(String format,Object... args){
        return String.format(Locale.US,format,args);
    }
}
