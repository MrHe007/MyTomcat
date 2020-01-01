package com.bigguy.server.server1;

import com.bigguy.server.cst.SystemCst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author bigguy_hc
 * @create 2019-12-29 15:57
 * @description：
 */
public class MyHttpServer {

    private Logger logger = LoggerFactory.getLogger(MyHttpServer.class);

    /**
     * 服务器的根目录
     */
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator  + "webroot";
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
        int port = 8081;
        MyHttpServer httpServer = new MyHttpServer(port);

        // 运行服务器
        httpServer.start();



    }

    /**
     * 初始化容器资源
     */
    public void init(int port) {
        logger.info("server init.... port：{}", port);
        logger.info("server root path:{} ", WEB_ROOT);
        try {
            // 不能写成下面这个形式
//            this.serverSocket = new ServerSocket(port);
            this.serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
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

            WebProcessor processor = null;
            // 如果以 servlet 开头表示处理 servlet
            if(StringUtils.isNotBlank(request.getRequestUri()) && request.getRequestUri().startsWith("/servlet/")){
                processor = new ServletProcessor();

            }else {
                // 否则处理静态数据
                processor = new StaticProcessor();
            }


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
