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
package com.bigguy.server.util;

import com.bigguy.server.cst.SystemCst;
import com.bigguy.server.server1.MyHttpServer;

import java.io.File;

/**
 * @author ：hechen
 * @data ：2019/12/30
 * @description ：系统工具
 */
public class SystemUtils {

    /**
     * 获得页面目录文件夹路径
     * @return
     */
    public static String getPagesDirPath(){
        String path = MyHttpServer.WEB_ROOT + SystemCst.PAGE_DIR + File.separator;
        return path;
    }

}
