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
package com.bigguy.server.server;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ：hechen
 * @data ：2020/1/1
 * @description ：http 连接器
 *  1、实例化一个 SocketServer
 *  2、监听 socket
 *  3、将 socket 交给 HttpProcessor 处理
 */
@Data
public class HttpConnector implements Runnable {

    private boolean stopped;
    private String scheme = "http";
    private int port;
    private ServerSocket serverSocket;

    private Logger logger = LoggerFactory.getLogger(HttpConnector.class);


    public HttpConnector(int port) {
        this.port = port;
    }

    /**
     * 启动服务
     */
    public void initServer(){
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void await(){
        initServer();

        while (!stopped){

            Socket socket = null;

            try {
                socket = serverSocket.accept();

                HttpProcessor httpProcessor = new HttpProcessor();

                // 处理器负责处理套接字
                httpProcessor.process(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void run() {
        await();
    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }

}
