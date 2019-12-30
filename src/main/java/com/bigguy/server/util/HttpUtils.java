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

import com.bigguy.server.cst.HttpCst;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：hechen
 * @data ：2019/12/30
 * @description ：
 */
public class HttpUtils {

    private final static List<String> imgTypeList = Arrays.asList("jpg", "png", "icon", "jpeg");


    /**
     * 判断是否是请求图片，通过请求连接的后缀名
     * @param requestUri
     * @return
     */
    public static boolean isImgUri(String requestUri){
        if(StringUtils.isNotBlank(requestUri)){

            for (String imgSuffix : imgTypeList) {
                if(requestUri.indexOf(imgSuffix) > 0){
                    return true;
                }
            }
        }
        return false;
    }



}
