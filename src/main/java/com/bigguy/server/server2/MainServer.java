package com.bigguy.server.server2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author bigguy_hc
 * @create 2019-12-29 20:46
 * @description：
 */
public class MainServer {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(9899);
        while (true) {
            Socket socket = server.accept();
            InputStreamReader r = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(r);
            String readLine = br.readLine();
            while (readLine != null && !readLine.equals("")) {
                System.out.println("获取到数据：" + readLine);
                readLine = br.readLine();
            }

            String html = "http/1.1 200 ok\n"
                    +"\n\n";

            String content = "hello world";

            html += content;

            OutputStream outputStream = socket.getOutputStream();

            outputStream.write(html.getBytes());

            outputStream.close();

        }

    }

}
