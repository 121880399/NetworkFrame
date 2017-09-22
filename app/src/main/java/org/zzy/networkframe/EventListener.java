package org.zzy.networkframe;

import org.zzy.networkframe.common.Protocol;
import org.zzy.networkframe.intf.Call;
import org.zzy.networkframe.request.Request;
import org.zzy.networkframe.response.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/21
 */

public abstract class EventListener {

    public void callStart(Call call){

    }

    public void dnsStart(Call call,String domainName){

    }

    public void dsnEnd(Call call, String domainName, List<InetAddress> inetAddressList){

    }

    public void connectStart(Call call, InetSocketAddress inetSocketAddress,Proxy proxy){

    }

    public void secureConnectStart(Call call){

    }

    public void secureConnectEnd(Call call,Handshake handshake){

    }

    public void connectEnd(Call call,InetSocketAddress inetSocketAddress,Proxy proxy,Protocol protocal){

    }


    public void connnectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocal, IOException ioe){

    }

    public void connectionAcquired(Call call,Connection connection){

    }

    public void connectionReleased(Call call,Connection connection){

    }

    public void requestHeadersStart(Call call){

    }

    public void requestHeadersEnd(Call call,Request request){

    }

    public void requestBodyStart(Call call){

    }

    public void requestBodyEnd(Call call,long byteCount){

    }

    public void responseHeadersStart(Call call){

    }

    public void responseHeaderEnd(Call call,Response response){

    }

    public void responseBodyStart(Call call){}

    public void responseBodyEnd(Call call,long byteCount){}

    public void callEnd(Call call){}

    public void callFailed(Call call,IOException iod){}


}

