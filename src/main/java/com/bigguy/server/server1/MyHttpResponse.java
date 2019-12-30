package com.bigguy.server.server1;

import com.bigguy.server.cst.HttpCst;
import com.bigguy.server.util.HttpUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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
                HttpUtils.responseSuccess(outputStream);

                if(HttpUtils.isImgUri(request.getRequestUri())){
                    HttpUtils.setContentType(outputStream, HttpCst.Content_Type.IMG_Content_Type);
                }

                // 头写完成
                HttpUtils.finishResponseHeader(outputStream);
                int len ;
                while ((len = fis.read(buffer)) != -1){
                    // 写到输出流中
                    outputStream.write(buffer, 0, len);
                }
            }else{
                // 文件找不到，提示错误
                String errorMsg = "HTTP/1.1 404 File Not Found\r\n"+
                        "Content_type: text/html\r\n" +
                        "Content_length: 23\r\n" +
                        "\r\n" +
                        "<h1>file not found</h1>";
                outputStream.write(errorMsg.getBytes());
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
