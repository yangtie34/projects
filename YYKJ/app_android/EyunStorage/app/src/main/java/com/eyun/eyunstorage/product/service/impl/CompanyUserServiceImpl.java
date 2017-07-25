package com.eyun.eyunstorage.product.service.impl;


import com.eyun.framework.entity.ResultMsg;
import com.eyun.eyunstorage.AppUser;
import com.eyun.eyunstorage.product.dao.impl.CompanyUserDaoImpl;
import com.eyun.eyunstorage.product.entity.CompanyUser;
import com.eyun.eyunstorage.product.service.CompanyUserService;


/**
 * Created by Administrator on 2017/3/6.
 */

public class CompanyUserServiceImpl implements CompanyUserService {
    private static CompanyUserServiceImpl companyUserService = null;

    public static CompanyUserServiceImpl getInstance() {
        if (companyUserService == null) {
            synchronized (new CompanyUserServiceImpl()) {
                if (companyUserService == null) {
                    companyUserService = new CompanyUserServiceImpl();
                }
            }
        }
        return companyUserService;
    }

    @Override
    public ResultMsg login(String userName, String userPwd) {
        boolean isSucc= CompanyUserDaoImpl.getInstance().login(userName,userPwd);
        ResultMsg resultMsg=new ResultMsg();
        if(isSucc)
        {
            resultMsg.setTure(true);
            resultMsg.setMsg("");
            CompanyUser mo=CompanyUserDaoImpl.getInstance().getUserInfo(userName);
            resultMsg.setObject(mo);
            AppUser.ReceiptSign=CompanyUserDaoImpl.getInstance().getReceiptSignStr(mo.getComBrId());
        }
        else
        {
            resultMsg.setTure(false);
            resultMsg.setMsg("登录失败，用户名或密码错误，请重试");
        }
        return resultMsg;
    }


}
