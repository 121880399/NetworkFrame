package org.zzy.networkframe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/12
 */

public class Connection {
    private void createSocket(){
        // BufferedSink buffer=null;
        Sink sink=null;
        Source source=null;

        InputStream inputStream=null;
        OutputStream outputStream=null;
        Socket socket=null;
        try {
            socket=new Socket("www.baidu.com",80);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            source= Okio.source(inputStream);
            sink= Okio.sink(outputStream);
            BufferedSource readBuffer = Okio.buffer(source);
            BufferedSink writeBuffer = Okio.buffer(sink);

            StringBuffer sb = new StringBuffer();
            sb.append("GET / HTTP/1.1 \r\n");// 注意\r\n为回车换行
            sb.append("Host: www.baidu.com\r\n");// 注意\r\n为回车换行
            sb.append("Accept-Language: zh-cn\r\n");
            sb.append("Connection: Keep-Alive\r\n");
            sb.append("\r\n");
            //向服务器写数据
            writeBuffer.write(sb.toString().getBytes());
            writeBuffer.flush();

            String info=null;
            while((info=readBuffer.readUtf8Line())!=null) {
                System.out.println("response:" + info);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                sink.close();
                source.close();
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
