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

import com.bigguy.server.util.SystemUtils;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * @author ：hechen
 * @data ：2019/12/31
 * @description ：
 */
public class ServletProcessor implements WebProcessor{


    public static void main(String[] args) throws Exception {

        URLClassLoader loader = null;
        File classPath = new File(MyHttpServer.WEB_ROOT);
        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();

        urls[0] = new URL(null, repository, streamHandler);
        loader = new URLClassLoader(urls);

        Class objClass = null;
        String servletName = "SystemUtils";
        objClass = loader.loadClass(servletName);

        SystemUtils systemUtils = (SystemUtils)objClass.newInstance();

        systemUtils.test();

        System.out.println("fdf");


    }


    /**
     * 参考博客：https://www.cnblogs.com/rogge7/p/7766522.html
     * @param request
     * @param response
     */


    public void process(MyHttpRequest request, MyHttpResponse response){

        String requestUri = request.getRequestUri();

        String servletName = requestUri.substring(requestUri.indexOf("/") + 1);


        URLClassLoader loader = null;

        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;

        File classPath = new File(MyHttpServer.WEB_ROOT);

        try {
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();

            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Class myClass = null;
        Servlet servlet = null;
        try {

            //  servlet 为类名
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            servlet = (Servlet)myClass.newInstance();

            // todo： 调用 servlet 服务类
//            servlet.service();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}
