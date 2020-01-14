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

import com.bigguy.server.http.base.HttpHeader;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：hechen
 * @data ：2020/1/1
 * @description ：
 */
@Data
@NoArgsConstructor
public class HttpResponse {

    private HttpRequest request;

    private List<HttpHeader> httpHeaders = new ArrayList<>(2);

    /**
     * socket 输出流
     */
    private OutputStream outputStream;


    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * 添加 http header
     * @param headerName
     * @param headerValue
     */
    public void setHeader(String headerName, String headerValue){
        httpHeaders.add(new HttpHeader(headerName, headerValue));
    }

}
