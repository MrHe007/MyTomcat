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
package com.bigguy.server.exception;

import lombok.Data;

/**
 * @author ：hechen
 * @data ：2020/1/1
 * @description ：系统错误
 */
@Data
public class SystemException extends RuntimeException{

    private String errorCode;
    private String errorDesc;

    public SystemException() {
        String errorDesc = "系统错误";
        this.errorDesc = errorDesc;
    }

    public SystemException(String errorDesc) {
        super(errorDesc);
        this.errorDesc = errorDesc;
    }
}
