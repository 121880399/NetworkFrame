package org.zzy.networkframe.interceptor;

import org.zzy.networkframe.intf.Interceptor;
import org.zzy.networkframe.response.Response;

import java.io.IOException;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/22
 */

public class RetryAndFollowUpInterceptor implements Interceptor{


    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
