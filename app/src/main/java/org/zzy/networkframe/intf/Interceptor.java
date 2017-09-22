package org.zzy.networkframe.intf;

import org.zzy.networkframe.Connection;
import org.zzy.networkframe.request.Request;
import org.zzy.networkframe.response.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/22
 */

public interface Interceptor {

    Response intercept(Chain chain)throws IOException;

    interface Chain{
        Request request();

        Response proceed(Request request)throws IOException;

        Connection connection();

        Call call();

        int connectTimeoutMillis();

        Chain withConnectTimeout(int timeout,TimeUnit unit);

        int readTimeoutMillis();

        Chain withReadTimeout(int timeout,TimeUnit unit);

        int writeTimeoutMillis();

        Chain withWriteTimeout(int timeout,TimeUnit unit);
    }

}
