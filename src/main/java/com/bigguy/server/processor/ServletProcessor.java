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
package com.bigguy.server.processor;

import com.bigguy.server.http.HttpRequest;
import com.bigguy.server.http.HttpResponse;
import com.bigguy.server.http.facade.HttpRequestFacade;
import com.bigguy.server.http.facade.HttpResponseFacade;
import com.bigguy.server.server1.MyHttpServer;
import com.bigguy.server.util.ResponseHandleUtils;
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


    @Override
    public void process(HttpRequest request, HttpResponse response){
        String requestUri = request.getRequestURI();

        // /servlet/LoginServlet
        String servletName = requestUri.substring(requestUri.indexOf("/") + 1);

        // 提取真正的类名：LoginServlet
        servletName = servletName.substring(servletName.indexOf("servlet")+ "servlet".length()+1);

        // todo: 加载类需要指定包名,下载假设所有的 servlet 都在这个包下面
        servletName = "com.bigguy.server.server1." + servletName;

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

            //  servlet 为类名（如果有包名，需要指定包名：com.bigguy.servlet.LoginServlet）
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            // 通过字节码反射得到 Servlet 的实体类
            servlet = (Servlet)myClass.newInstance();

            // 写成功
            ResponseHandleUtils.responseHtmlOk(response.getOutputStream());

            // 向下转型
            HttpRequestFacade requestFacade = new HttpRequestFacade(request);
            HttpResponseFacade responseFacade = new HttpResponseFacade(response);

            // 调用 servlet 的服务类
            servlet.service(requestFacade, responseFacade);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
