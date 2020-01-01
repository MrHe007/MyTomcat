package com.bigguy.server.server1;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * @author bigguy_hc
 * @create 2019-12-29 16:03
 * @description：
 */
public class MyHttpRequest implements ServletRequest {


    private Logger logger = LoggerFactory.getLogger(MyHttpRequest.class);

    private InputStream inputStream;

    /**
     * 缓冲区
     */
    private StringBuffer buffer = new StringBuffer(1024*2);

    /**
     * 请求链接
     */
    private String requestUri;

    public MyHttpRequest(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 处理响应
     */
    public void handleRequest(){
        byte[] bufferArr = new byte[1024*2];
        int len = 0;
        try {
            len = inputStream.read(bufferArr);
        } catch (IOException e) {
            e.printStackTrace();
            len = -1;
        }

        if(len <= 0){
            return;
        }
        buffer.append(new String(bufferArr, 0, len));

        logger.info("request content：\n" + buffer.toString());

        // 解析出 requestUri
        this.requestUri = parseUri(buffer.toString());
    }

    /**
     * 1、从传输的数据中解析出请求链接（请求头）
     * 2、在请求中搜索第一个和第二个空格
     * @param requestData
     * @return
     */
    public String parseUri(String requestData){

        int index1, index2;
        index1 = requestData.indexOf(' ');
        if(index1 != -1) {
            index2 = requestData.indexOf(' ', index1 + 1);
            return requestData.substring(index1 + 1, index2);
        }

        // 防止后面空指针
        return StringUtils.EMPTY;
    }

    public String getRequestUri() {
        return requestUri;
    }


    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }
}
