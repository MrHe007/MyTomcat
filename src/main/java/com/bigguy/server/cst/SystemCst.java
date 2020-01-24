package com.bigguy.server.cst;

import java.io.File;

/**
 * @author bigguy_hc
 * @create 2019-12-29 15:58
 * @description：
 */
public interface SystemCst {

    /**
     * 资源路径
     */
    String WEB_ROOT = System.getProperty("user.dir") + File.separator  + "webroot";

    /**
     * 页面存放目录
     */
    String PAGE_DIR = File.separator + "pages";

    /**
     * 关闭服务器的命令
     */
    String SHUTDOWN_COMMAND = "/close";

    /**
     * 字符集
     */
    String CHAR_SET = "utf-8";


}
