package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.jybfreightscan.AppUser;
import com.eyun.jybfreightscan.product.dao.StorageOutRecDao;
import com.eyun.jybfreightscan.product.entity.StorageOutRec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public class StorageOutRecDaoImpl implements StorageOutRecDao {

    private static StorageOutRecDaoImpl storageOutRecDao = null;

    public static StorageOutRecDaoImpl getInstance() {
        if (storageOutRecDao == null) {
            synchronized (new StorageOutRecDaoImpl()) {
                if (storageOutRecDao == null) {
                    storageOutRecDao = new StorageOutRecDaoImpl();
                }
            }
        }
        return storageOutRecDao;
    }


    @Override
    public boolean IsExist(String recNumber) {

        String sqlExists="Select 1 from StorageOutRec where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlExists);
        if(list!=null&&list.size()==1)
            return  true;
        return  false;
    }

    @Override
    public StorageOutRec getStorageOutRecByRecNumber(String RecNumber) {
        String sqlSelect="Select * from StorageOutRec where RecNumber="+ SqlStringUtils.GetQuotedString(RecNumber);
        List<StorageOutRec> list= BaseDao.getInstance().query(sqlSelect,StorageOutRec.class);
        if(list!=null&&list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public String addStorageOutRec(StorageOutRec mo) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecState",mo.getRecState());
        map.put("RecNumber", SqlStringUtils.GetQuotedString(mo.getRecNumber()));
        map.put("RecTime", SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        map.put("CreateIp", SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime", SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        map.put("CreateUserID", AppUser.userId);
        map.put("CreateComBrID",AppUser.comBrId);
        map.put("CreateComID",AppUser.comId);

        String sqlInsert= SqlStringUtils.GetConstructionInsert("StorageOutRec",map);
        BaseDao.getInstance().excute(sqlInsert);
        return mo.getRecNumber();
    }

    @Override
    public boolean updateState(String RecNumber, int state) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecState",state);
        String where=" where RecNumber="+ SqlStringUtils.GetQuotedString(RecNumber);
        String sqlUpdate= SqlStringUtils.GetConstructionUpdate("StorageOutRec",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }
}
