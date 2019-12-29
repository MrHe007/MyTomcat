package com.bigguy.server.server1;

import com.bigguy.server.cst.SystemCst;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author bigguy_hc
 * @create 2019-12-29 16:03
 * @description：
 */
@Data
public class MyHttpRequest {

    private InputStream inputStream;

    /**
     * 缓冲区
     */
    private StringBuffer buffer = new StringBuffer(1024*2);

    // 请求链接
    private String requestUri;

    public MyHttpRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 处理响应
     */
    public void handleRequest(){
        byte[] bufferArr = new byte[1024*2];
        int len = 0;
        try {
            len = inputStream.read(bufferArr);
        } catch (IOException e) {
            e.printStackTrace();
            len = -1;
        }
        for (int j=0; j<len; j++){
            buffer.append((char)bufferArr[j]);
        }

        System.out.println("request content：" + buffer.toString());

        // 解析出 requestUri
        this.requestUri = parseUri(buffer.toString());
    }

    /**
     * 1、从传输的数据中解析出请求链接（请求头）
     * 2、在请求中搜索第一个和第二个空格
     * @param requestData
     * @return
     */
    public String parseUri(String requestData){

        int index1, index2;
        index1 = requestData.indexOf(' ');
        if(index1 != -1) {
            index2 = requestData.indexOf(' ', index1 + 1);
            return requestData.substring(index1 + 1, index2);
        }
        return null;
    }
}
