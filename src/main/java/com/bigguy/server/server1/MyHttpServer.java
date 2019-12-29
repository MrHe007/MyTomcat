package com.bigguy.server.server1;

import com.bigguy.server.cst.SystemCst;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author bigguy_hc
 * @create 2019-12-29 15:57
 * @description：
 */
public class MyHttpServer {

    private boolean shutdown = false;

    private ServerSocket serverSocket;
    private int port;
    private String host;

    public MyHttpServer(int port) {
        this.port = port;

        // 初始化容器
        this.init(port);
    }

    public static void main(String[] args) throws IOException {
        int port = 8888;
        MyHttpServer httpServer = new MyHttpServer(port);

        // 运行服务器
        httpServer.start();



    }

    /**
     * 初始化容器资源
     */
    public void init(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 容器开始运行
     * 1、监听 请求
     * 2、处理响应
     */
    private void start() throws IOException {

        while (!shutdown){

            Socket clientSocket = serverSocket.accept();

            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            MyHttpRequest request = new MyHttpRequest(inputStream);

            // 解析请求数据
            request.handleRequest();

            // 处理响应
            MyHttpResponse response = new MyHttpResponse(outputStream);
            response.setRequest(request);

            // 处理响应
            response.handleResponse();
            // 关闭客户端连接
            clientSocket.close();

            shutdown = isShutdown(request.getRequestUri());
        }

    }


    /**
     * 1、根据请求链接判断是否关闭服务器
     * 2、请求链接：/hello（需要包含 /）
     * @param requestUri
     * @return
     */
    public boolean isShutdown(String requestUri){
        if(StringUtils.isNotBlank(requestUri) && SystemCst.SHUTDOWN_COMMAND.equals(requestUri)){
            return true;
        }
        return false;
    }

}
