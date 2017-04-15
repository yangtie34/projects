package com.eyun.jybStorageScan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.jybStorageScan.AppUser;
import com.eyun.jybStorageScan.product.dao.StorageInRecDao;
import com.eyun.jybStorageScan.product.entity.StorageInRec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/4.
 */

public class StorageInRecDaoImpl implements StorageInRecDao {

    private static StorageInRecDaoImpl storageInRecDao = null;

    public static StorageInRecDaoImpl getInstance() {
        if (storageInRecDao == null) {
            synchronized (new StorageInRecDaoImpl()) {
                if (storageInRecDao == null) {
                    storageInRecDao = new StorageInRecDaoImpl();
                }
            }
        }
        return storageInRecDao;
    }

    @Override
    public StorageInRec getStorageInRecByRecNumber(String recBarrCode) {
        String sqlSelect="Select * from StorageInRec where RecNumber="+SqlStringUtils.GetQuotedString(recBarrCode);
        List<StorageInRec> list= BaseDao.getInstance().query(sqlSelect,StorageInRec.class);
        if(list!=null&&list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public  boolean IsExist(String recNumber)
    {
        String sqlExists="Select 1 from StorageInRec where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlExists);
        if(list!=null&&list.size()==1)
            return  true;
        return  false;
    }

    @Override
    public String addStorageInRec(StorageInRec mo) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecNumber",SqlStringUtils.GetQuotedString(mo.getRecNumber()));
        map.put("RecTime", SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        map.put("CreateIp", SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        map.put("CreateUserID", AppUser.userId);
        map.put("CreateComBrID",AppUser.comBrId);
        map.put("CreateComID",AppUser.comId);

        String sqlInsert=SqlStringUtils.GetConstructionInsert("StorageInRec",map);
        BaseDao.getInstance().excute(sqlInsert);
        return mo.getRecNumber();
    }

    @Override
    public boolean updateState(String RecNumber, int state) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecState",state);
        String where=" where RecNumber="+SqlStringUtils.GetQuotedString(RecNumber);
        String sqlUpdate=SqlStringUtils.GetConstructionUpdate("StorageInRec",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }
}
