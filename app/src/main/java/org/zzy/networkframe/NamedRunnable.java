package org.zzy.networkframe;

import org.zzy.networkframe.util.Util;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/22
 */

public abstract class NamedRunnable implements Runnable{
    protected final String name;

    public NamedRunnable(String format,Object... args){
        this.name= Util.format(format,args);
    }

    @Override
    public void run() {
        String oldName=Thread.currentThread().getName();
        Thread.currentThread().setName(name);
        try{
            execute();
        }finally {
            Thread.currentThread().setName(oldName);
        }
    }

    protected abstract void execute();
}
