package org.zzy.networkframe.request;

import java.io.IOException;

import okio.BufferedSink;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/12
 */

public abstract class RequestBody {

    /**
     * 返回Content-Type头
     * */
   // public abstract MediaType contentType();

    /**
     * 讲请求体中的内容写入sink中
     * */
    public abstract void writeTo(BufferedSink sink)throws IOException;

}
