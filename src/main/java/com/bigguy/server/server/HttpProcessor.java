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

import com.bigguy.server.cst.HttpCst;
import com.bigguy.server.exception.SystemException;
import com.bigguy.server.http.HttpRequest;
import com.bigguy.server.http.HttpResponse;
import com.bigguy.server.http.SocketInputStream;
import com.bigguy.server.http.base.HttpHeader;
import com.bigguy.server.http.base.HttpRequestLine;
import com.bigguy.server.processor.ServletProcessor;
import com.bigguy.server.processor.StaticProcessor;
import com.bigguy.server.processor.WebProcessor;
import com.bigguy.server.util.RequestUtil;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author ：hechen
 * @data ：2020/1/1
 * @description ：
 *  1、创建 HttpRequest、HttpResponse
 *  2、从 socket 解析数据填充 HttpRequest 的请求行 和 请求头
 *  3、根据静态资源、 servlet 请求进行不同的处理
 */
public class HttpProcessor{

    private HttpRequest request;
    private HttpResponse response;
    private HttpRequestLine requestLine = new HttpRequestLine();
    protected String method = null;
    protected String queryString = null;


    public void process(Socket socket){
        try {
            SocketInputStream inputStream = new SocketInputStream(socket.getInputStream(), 2048);

            OutputStream outputStream = socket.getOutputStream();

            request = new HttpRequest(inputStream);
            response = new HttpResponse(outputStream);

            response.setHeader(HttpCst.ResponseHeader.SERVER, "hc's http server" );

            /**
             * 解析请求
             */
            parseRequest(inputStream);

            // 解析请求头
            parseRequestHeader(inputStream);

            // 根据不同的请求前缀调用不同的处理器进行处理
            WebProcessor processor;
            if(RequestUtil.isServletRequest(request.getRequestURI())){
                processor = new ServletProcessor();
            }else {
                processor = new StaticProcessor();
            }

            // 处理器进行处理
            processor.process(request, response);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析请求头
     * 1、请求行
     *  1、请求方法
     *  2、请求uri：检查 uri 是否满足标准格式
     *  3、请求方法
     * @param inputStream
     */
    private void parseRequest(SocketInputStream inputStream) {
        try {
            inputStream.readRequestLine(requestLine);

            String method = new String(requestLine.method, 0, requestLine.methodEnd);
            String uri = null;
            String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

            if (method.length() < 1) {
                throw new SystemException("Missing HTTP request method");
            }
            else if (requestLine.uriEnd < 1) {
                throw new SystemException("Missing HTTP request URI");
            }

            // Parse any query parameters out of the request URI
            int question = requestLine.indexOf("?");
            if (question >= 0) {

                // 解析请求参数
                request.setQueryString(new String(requestLine.uri, question + 1,
                        requestLine.uriEnd - question - 1));
                uri = new String(requestLine.uri, 0, question);
            }
            else {
                request.setQueryString(null);
                uri = new String(requestLine.uri, 0, requestLine.uriEnd);
            }

            // Normalize URI (using String operations at the moment)
            String normalizedUri = normalize(uri);

            // Set the corresponding request properties
            request.setMethod(method);
            request.setProtocol(protocol);
            if (normalizedUri != null) {
                request.setRequestURI(normalizedUri);
            }
            else {
                request.setRequestURI(normalizedUri);
            }

            if (normalizedUri == null) {
                throw new SystemException("Invalid URI: " + uri + "'");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 检查请求 uri 是否满足标准格式
     * @param path
     * @return
     */
    private String normalize(String path) {

        if (path == null) {
            return null;
        }
        // Create a place for the normalized path
        String normalized = path;

        // Normalize "/%7E" and "/%7e" at the beginning to "/~"
        if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
            normalized = "/~" + normalized.substring(4);

        // Prevent encoding '%', '/', '.' and '\', which are special reserved
        // characters
        if ((normalized.indexOf("%25") >= 0)
                || (normalized.indexOf("%2F") >= 0)
                || (normalized.indexOf("%2E") >= 0)
                || (normalized.indexOf("%5C") >= 0)
                || (normalized.indexOf("%2f") >= 0)
                || (normalized.indexOf("%2e") >= 0)
                || (normalized.indexOf("%5c") >= 0)) {
            return null;
        }

        if (normalized.equals("/.")) {
            return "/";
        }

        // Normalize the slashes and add leading slash if necessary
        if (normalized.indexOf('\\') >= 0) {
            normalized = normalized.replace('\\', '/');
        }
        if (!normalized.startsWith("/")) {
            normalized = "/" + normalized;
        }

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0) {
                break;
            }
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0) {
                break;
            }
            if (index == 0) {
                return (null);  // Trying to go outside our context
            }
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // Declare occurrences of "/..." (three or more dots) to be invalid
        // (on some Windows platforms this walks the directory tree!!!)
        if (normalized.indexOf("/...") >= 0) {
            return (null);
        }

        // Return the normalized path that we have completed
        return (normalized);
    }

    /**
     * 解析请求
     * @param inputStream
     */
    private void parseRequestHeader(SocketInputStream inputStream) throws IOException {

        while (true) {
            HttpHeader header = new HttpHeader();;

            // Read the next header
            inputStream.readHeader(header);
            if (header.nameEnd == 0) {
                if (header.valueEnd == 0) {
                    return;
                }
                else {
                    throw new SystemException();
                }
            }

            String name = new String(header.name, 0, header.nameEnd);
            String value = new String(header.value, 0, header.valueEnd);

            request.addHeader(name, value);
            // do something for some headers, ignore others.
            if (name.equals("cookie")) {
                Cookie cookies[] = RequestUtil.parseCookieHeader(value);
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("jsessionid")) {
                        // Override anything requested in the URL
                        if (!request.isRequestedSessionIdFromCookie()) {
                            // Accept only the first session id cookie
                            request.setRequestedSessionId(cookies[i].getValue());
                            request.setRequestedSessionCookie(true);
                            request.setRequestedSessionURL(false);
                        }
                    }
                    request.addCookie(cookies[i]);
                }
            }else if (name.equals("content-length")) {
                int n = -1;
                try {
                    n = Integer.parseInt(value);
                }
                catch (Exception e) {
                    throw new SystemException();
                }
                request.setContentLength(n);
            }
            else if (name.equals("content-type")) {
                request.setContentType(value);
            }
        } //end while

    }

}
