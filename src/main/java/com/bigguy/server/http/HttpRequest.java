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
package com.bigguy.server.http;

import com.bigguy.server.http.base.HttpRequestLine;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：hechen
 * @data ：2020/1/1
 * @description ：
 */
@Data
@NoArgsConstructor
public class HttpRequest {

    /**
     * 请求行
     */
    private HttpRequestLine requestLine;

    /**
     * cookie
     */
    private List<Cookie> cookies;

    /**
     * 请求参数
     */
    private Map<String, Object> parameters;

    /**
     * 请求 uri
     */
    private String requestURI;

    /**
     * socket 输入流
     */
    private InputStream inputStream;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求协议
     */
    private String protocol;

    /**
     * 请求参数
     */
    private String queryString;

    /**
     * 请求类型
     */
    private String contentType;

    /**
     * 请求长度
     */

    /**
     * 数据长度
     */
    private int contentLength;

    /**
     * 请求地址
     */
    private InetAddress inetAddress;

    /**
     * 请求头键值对
     */
    protected HashMap headers = new HashMap(16);


    private boolean requestedSessionCookie;

    /**
     * 请求 session id
     */
    private String requestedSessionId;

    private boolean requestedSessionURL;



    public HttpRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void addHeader(String name, String value) {
        name = name.toLowerCase();
        synchronized (headers) {
            ArrayList values = (ArrayList) headers.get(name);
            if (values == null) {
                values = new ArrayList();
                headers.put(name, values);
            }
            values.add(value);
        }
    }


    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    /**
     * 增加 cookie
     * @param cookie
     */
    public void addCookie(Cookie cookie) {
        synchronized (cookies) {
            cookies.add(cookie);
        }
    }
}
