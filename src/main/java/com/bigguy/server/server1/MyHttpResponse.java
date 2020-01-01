package com.bigguy.server.server1;

import com.bigguy.server.cst.HttpCst;
import com.bigguy.server.util.HttpUtils;
import com.bigguy.server.util.ResponseHandleUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author bigguy_hc
 * @create 2019-12-29 16:03
 * @description：
 */
@Data
public class MyHttpResponse {

    private static final int BUFFER_SIZE = 1024;
    private OutputStream outputStream;
    private MyHttpRequest request;

    private Logger logger = LoggerFactory.getLogger(MyHttpResponse.class);

    public MyHttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    // 处理响应
    public void handleResponse() throws IOException {

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

    public MyHttpRequest getRequest() {
        return request;
    }

    public void setRequest(MyHttpRequest request) {
        this.request = request;
    }

}
