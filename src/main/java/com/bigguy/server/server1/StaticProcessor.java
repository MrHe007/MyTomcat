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
package com.bigguy.server.server1;

import com.bigguy.server.cst.HttpCst;
import com.bigguy.server.util.HttpUtils;
import com.bigguy.server.util.ResponseHandleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author ：hechen
 * @data ：2020/1/1
 * @description ：静态资源处理器
 */
public class StaticProcessor implements WebProcessor {

    private static final int BUFFER_SIZE = 1024;
    private Logger logger = LoggerFactory.getLogger(StaticProcessor.class);

    @Override
    public void process(MyHttpRequest request, MyHttpResponse response) throws IOException {
        // 处理静态资源，直接将静态资源从文件流中读出， 写到浏览器输出流中

        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        String path = request.getRequestUri();
        FileInputStream fis = null;

        // 防止请求路径为空报异常
        if(StringUtils.isBlank(path)){
            return;
        }

        File file = new File(MyHttpServer.WEB_ROOT, path);
        try {
            if(file.exists()){
                fis = new FileInputStream(file);

                // 写成功
                ResponseHandleUtils.responseSuccess(outputStream);

                if(HttpUtils.isImgUri(request.getRequestUri())){
                    ResponseHandleUtils.setContentType(outputStream, HttpCst.ContentType.IMG_CONTENT_TYPE);
                }

                // 头写完成
                ResponseHandleUtils.finishResponseHeader(outputStream);
                int len ;
                while ((len = fis.read(buffer)) != -1){
                    // 写到输出流中
                    outputStream.write(buffer, 0, len);
                }
            }else{
                logger.error("没有这个链接...");
                // 处理404错误
                ResponseHandleUtils.handle404Error(outputStream);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != fis){
                fis.close();
            }
        }
    }

}
