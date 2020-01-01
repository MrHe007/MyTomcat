package com.bigguy.server.server1;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * @author bigguy_hc
 * @create 2019-12-29 16:03
 * @description：
 */
public class MyHttpRequest{


    private Logger logger = LoggerFactory.getLogger(MyHttpRequest.class);

    private InputStream inputStream;

    /**
     * 缓冲区
     */
    private StringBuffer buffer = new StringBuffer(1024*2);

    /**
     * 请求链接
     */
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

        if(len <= 0){
            return;
        }
        buffer.append(new String(bufferArr, 0, len));

        logger.info("request content：\n" + buffer.toString());

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

        // 防止后面空指针
        return StringUtils.EMPTY;
    }

    public String getRequestUri() {
        return requestUri;
    }

}
