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
package com.bigguy.server.test.server1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ：hechen
 * @data ：2020/1/1
 * @description ：
 */
public class LoginServlet implements Servlet {

    private Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init...");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        logger.info("service ....");

        PrintWriter pw = servletResponse.getWriter();

        pw.println("hello world");
        pw.println("hello world");

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
