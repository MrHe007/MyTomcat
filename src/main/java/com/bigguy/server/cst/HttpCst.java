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
package com.bigguy.server.cst;

/**
 * @author ：hechen
 * @data ：2019/12/30
 * @description ：
 */
public interface HttpCst {

    /**
     * http 协议换行
     */
    String CRLF = "\r\n";

    /**
     * 响应头部，注意: 后面有一个空格
     */
    interface ResponseHeader{
        String CONTENT_TYPE = "content-type: ";

        String CONTENT_LENGTH = "Content_length: ";

        /**
         * 服务器
         */
        String SERVER = "Server";

    }


    /**
     * 响应状态
     */
    interface ResonseStatus{

        /**
         * 响应成功
         */
        String RESP_SUCCESS = "HTTP/1.1 200 ok" + CRLF;

        /**
         * 404页面找不到
         */
        String ERROR_404 = "HTTP/1.1 404 File Not Found" + CRLF;

        /**
         * 405
         */
        String ERROR_405 = "HTTP/1.1 405 Method Not Allowed" + CRLF;


    }


    /**
     * 响应类型
     */
    interface ContentType{

        /**
         * 图片类型
         */
        String IMG_CONTENT_TYPE = "image/jpeg";

        /**
         * 文本响应类型
         */
        String HTML_TEXT = " text/html";



    }


}
