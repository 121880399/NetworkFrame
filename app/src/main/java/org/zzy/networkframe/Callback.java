package org.zzy.networkframe;

import org.zzy.networkframe.intf.Call;
import org.zzy.networkframe.response.Response;

import java.io.IOException;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/21
 */

public interface Callback {

    /**
     * 失败回调这个
     * */
    void onFailure(Call call, IOException e);


    /**
     * 成功回调这个
     * */
    void onResponse(Call call, Response response)throws IOException;
}
