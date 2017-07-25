package com.eyun.jybStorageScan.product.service;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.jybStorageScan.product.entity.CompanyUser;

/**
 * Created by Administrator on 2017/3/6.
 */
public interface CompanyUserService {
    /**
     * 根据用户名和密码登录登录
     */
    ResultMsg login(String userName,String pwd);

}
