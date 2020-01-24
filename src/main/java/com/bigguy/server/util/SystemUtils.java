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

import java.io.File;

/**
 * @author ：hechen
 * @data ：2019/12/30
 * @description ：系统工具
 */
public class SystemUtils {

    public static void main(String[] args) {

        SystemUtils systemUtils = new SystemUtils();
        systemUtils.test();
    }

    /**
     * 获得页面目录文件夹路径
     * @return
     */
    public static String getPagesDirPath(){
        String path = SystemCst.WEB_ROOT + SystemCst.PAGE_DIR + File.separator;
        return path;
    }

    public void test(){
        System.out.println("test init");
    }

}
