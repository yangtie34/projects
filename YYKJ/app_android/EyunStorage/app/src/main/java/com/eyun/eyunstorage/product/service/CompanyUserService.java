package com.eyun.eyunstorage.product.service;

import com.eyun.framework.entity.ResultMsg;

/**
 * Created by Administrator on 2017/3/6.
 */
public interface CompanyUserService {
    /**
     * 根据用户名和密码登录登录
     */
    ResultMsg login(String userName,String pwd);

}
