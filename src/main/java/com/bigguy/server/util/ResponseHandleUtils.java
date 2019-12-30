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

import com.bigguy.server.cst.Constants;
import com.bigguy.server.cst.HttpCode;
import com.bigguy.server.cst.HttpCst;

import java.io.*;

/**
 * @author ：hechen
 * @data ：2019/12/30
 * @description ：响应处理类
 */
public class ResponseHandleUtils {


    /***
     * @Description  处理文件找不到错误
     * @Author  hechen
     * @Date   2019/12/30
     * @Param  [outputStream]
     * @Return void
     *
     */
    public static void handle404Error(OutputStream outputStream){

        // 加载pages 下面的 404 页面到浏览器页面
        // 加载pages 下面的 405 页面到浏览器页面
        try {
            outputStream.write(HttpCst.ResonseStatus.ERROR_404.getBytes());
            handleHttpCodePage(outputStream, HttpCode.ErrorCode.CODE_404);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * @Description  处理文件找不到错误
     * @Author  hechen
     * @Date   2019/12/30
     * @Param  [outputStream]
     * @Return void
     *
     */
    public static void handle405Error(OutputStream outputStream){

        // 加载pages 下面的 405 页面到浏览器页面
        try {
            outputStream.write(HttpCst.ResonseStatus.ERROR_405.getBytes());
            handleHttpCodePage(outputStream, HttpCode.ErrorCode.CODE_405);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将 pages 下对应的状态码输出到浏览器页面中
     * @param outputStream
     * @param httpCode
     */
    private static void handleHttpCodePage(OutputStream outputStream, String httpCode){


        String path = SystemUtils.getPagesDirPath();

        path += httpCode + Constants.FileSuffix.HTML;
        File file = new File(path);

        FileInputStream fis = null;
        int len;
        if(file.exists()){
            try {

                // 写头
                outputStream.write((HttpCst.ResponseHeader.CONTENT_TYPE + HttpCst.ContentType.HTML_TEXT).getBytes());

                // 完成头部的写
                finishResponseHeader(outputStream);

                fis = new FileInputStream(file);
                byte[] buffer = new byte[2048];
                while ((len = fis.read(buffer)) != -1){
                    outputStream.write(buffer, 0, len);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(null != fis){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
        StringBuffer sb = new StringBuffer(HttpCst.ResponseHeader.CONTENT_TYPE );
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
