/*
Copyright (C) 2011-$today.year. ShenZhen IBOXCHAIN Information Technology Co.,Ltd.

All right reserved.

This software is the confidential and proprietary
information of IBOXCHAIN Company of China.
("Confidential Information"). You shall not disclose
such Confidential Information and shall use it only
in accordance with the terms of the contract agreement
you entered into with IBOXCHAIN inc.

*/
package com.bigguy.server.util;

import com.bigguy.server.cst.HttpCst;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：hechen
 * @data ：2019/12/30
 * @description ：
 */
public class HttpUtils {

    private final static List<String> imgTypeList = Arrays.asList("jpg", "png", "icon", "jpeg");


    /**
     * 判断是否是请求图片，通过请求连接的后缀名
     * @param requestUri
     * @return
     */
    public static boolean isImgUri(String requestUri){
        if(StringUtils.isNotBlank(requestUri)){

            for (String imgSuffix : imgTypeList) {
                if(requestUri.indexOf(imgSuffix) > 0){
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * 响应成功
     * @param outputStream
     */
    public static void responseSuccess(OutputStream outputStream){
        try {
            outputStream.write(HttpCst.ResonseStatus.RESP_SUCCESS.getBytes());
        } catch (IOException e) {
            System.out.println("响应失败");
            e.printStackTrace();
        }
    }


    /**
     * 设置 content-type
     * @param outputStream
     * @param contentType
     */
    public static void setContentType(OutputStream outputStream, String contentType){
        StringBuffer sb = new StringBuffer(HttpCst.ResponseHeader.Content_Type );
        sb.append(contentType);
        sb.append(HttpCst.CRLF);
        try {
            outputStream.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 响应头与响应体之间空格隔开
     * @param outputStream
     */
    public static void finishResponseHeader(OutputStream outputStream){
        try {
            outputStream.write(HttpCst.CRLF.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
