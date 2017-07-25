package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.DigestUtils;
import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.product.dao.CompanyUserDao;
import com.eyun.jybfreightscan.product.entity.CompanyUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public class CompanyUserDaoImpl implements CompanyUserDao{

    private static CompanyUserDaoImpl companyUserDao = null;

    public static CompanyUserDaoImpl getInstance() {
        if (companyUserDao == null) {
            synchronized (new CompanyUserDaoImpl()) {
                if (companyUserDao == null) {
                    companyUserDao = new CompanyUserDaoImpl();
                }
            }
        }
        return companyUserDao;
    }

    @Override
    public boolean login(String userName, String userPwd) {
        String sql = "select a.UserID,a.UserName,a.UserNumber,a.Fullname," +
                "a.IsMainUser,b.ComBrID,b.ComBrName,b.ComBrNumber,b.ComBrJianC,b.ComBrArea," +
                "  b.BusinessTypeZB as ComBrBusinessTypeZB," +
                "b.BusinessTypeZD as ComBrBusinessTypeZD," +
                "b.BusinessTypeCC as ComBrBusinessTypeCC," +
                " c.ComID,c.ComNumber,c.ComName,c.ComGrade,c.SysOperateMode from CompanyUser a" +
                "  left join CompanyBranch b on b.ComBrID=a.ComBrID" +
                " left join Company c on c.ComID=b.ComID" +
                " where a.FlagDelete=0 and a.Disabled=0 and b.FlagDelete=0 and b.Disabled=0" +
                " and c.FlagDelete=0 and c.Disabled=0 and (c.ExpireTime is null or datediff(day,c.ExpireTime,getdate())<=0)" +
                " and a.UserName=" + SqlStringUtils.GetQuotedString(userName) + " and a.Pwd=" + SqlStringUtils.GetQuotedString(DigestUtils.md5(userPwd)) + " order by a.UserID";
        List<Map<String, Object>> resault = BaseDao.getInstance().queryForList(sql);
        if (resault != null && resault.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CompanyUser getUserInfo(String userName) {
        String sqlQuery="Select * from CompanyUser where UserName="+ SqlStringUtils.GetQuotedString(userName);
        List<CompanyUser> list= BaseDao.getInstance().query(sqlQuery,CompanyUser.class);
        if(list!=null&&list.size()>0)
            return list.get(0);
        return  null;
    }

    public String getReceiptSignStr(long ComBrID){
        String sqlSelect="Select ReceiptSign from CompanyBranch where ComBrID="+ComBrID;

        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toString(list.get(0).get("ReceiptSign"));
        }
        return "";
    };
}
