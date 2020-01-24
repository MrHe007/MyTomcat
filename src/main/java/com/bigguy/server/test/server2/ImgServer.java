package com.bigguy.server.test.server2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author bigguy_hc
 * @create 2019-12-29 20:53
 * @description：
 */
public class ImgServer {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(9898);
        while (true) {
            Socket socket = server.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int len;
            StringBuffer sb = new StringBuffer();
            int contentLength = 0;
            boolean first = true;

            len = inputStream.read(buffer);
            String headerStr = new String(buffer, 0, len);

            len = inputStream.read(buffer);
            System.out.println(len);

            String imgPath = "D:\\IDEAPro\\MyTomcat\\webroot\\cat.png";
            File file = new File(imgPath);

            FileInputStream fis = new FileInputStream(file);
            byte[] imgBuffer = new byte[1024*8];

            int imgLength = fis.read(imgBuffer);

            System.out.println(sb.toString());
            String html = "http/1.1 200 ok\n"
                    +"\n\n";

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(html.getBytes());
            outputStream.close();
        }
    }

}
