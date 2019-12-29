package com.bigguy.server.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author bigguy_hc
 * @create 2019-12-28 22:22
 * @description：
 */
public class MyServer {

    public static void main(String[] args) throws IOException {

        int port = 8888;
        java.net.ServerSocket serverSocket = new java.net.ServerSocket(port);

        System.out.println("serverSocket listener init...");

        // 监听 socket
        Socket clientSocekt = serverSocket.accept();

        // 从 socket 获得输入流，从流中读取数据
        InputStream inputStream = clientSocekt.getInputStream();

        byte[] bytes = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int len;
        while ((len = inputStream.read(bytes)) != -1){
            // 从接收方中去读取数据
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }

        System.out.println("get msg from client："+ sb.toString());

        OutputStream outputStream = clientSocekt.getOutputStream();

        String retMsg = "接受到消息了，可以关闭连接了...";
        outputStream.write(retMsg.getBytes());

        // 关闭流
        inputStream.close();
        outputStream.close();
        clientSocekt.close();
        serverSocket.close();
    }
}
