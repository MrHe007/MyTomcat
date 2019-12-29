package com.bigguy.server.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author bigguy_hc
 * @create 2019-12-28 22:29
 * @description：
 */
public class ClientSocket {

    public static void main(String[] args) throws IOException {

        String host = "127.0.0.1";
        int port = 8888;

        // 设置服务端的 主机+端口
        Socket clientSocket = new Socket(host, port);
        OutputStream outputStream = clientSocket.getOutputStream();

        String msg = "hello, world";

        // 将数据写到输出流中
        outputStream.write(msg.getBytes());
        // 关闭输出流
        clientSocket.shutdownOutput();

        InputStream inputStream = clientSocket.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        StringBuilder msgBuilder = new StringBuilder();
        while ((len = inputStream.read(buffer)) != -1){
            msgBuilder.append(new String(buffer, 0, len));
        }

        System.out.println("receive from sever："+ msgBuilder.toString());

        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }
}
