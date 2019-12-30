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
     * 响应头部
     */
    interface ResponseHeader{
        String Content_Type = "content-type: ";
    }

    /**
     * 响应状态
     */
    interface ResonseStatus{

        /**
         * 响应成功
         */
        String RESP_SUCCESS = "HTTP/1.1 200 ok" + CRLF;

    }

    /**
     * 响应类型
     */
    interface Content_Type{

        /**
         * 图片类型
         */
        String IMG_Content_Type = "image/jpeg";

    }


}
