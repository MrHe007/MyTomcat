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

/**
 * @author ：hechen
 * @data ：2020/1/1
 * @description ：
 */
public class Bootstrap {

    public static void main(String[] args) {

        int port = 8080;

        HttpConnector httpConnector = new HttpConnector(port);

        // 启动连接器
        httpConnector.start();
    }

}
