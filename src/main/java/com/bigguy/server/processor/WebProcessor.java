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

import java.io.IOException;

/**
 * @author ：hechen
 * @data ：2020/1/1
 * @description ：
 */
public interface WebProcessor {

    /**
     * 处理器处理方法
     * @param request
     * @param response
     */
    public void process(HttpRequest request, HttpResponse response) throws IOException;

}
