package com.eyun.eyunstorage.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.eyunstorage.AppPublic;
import com.eyun.eyunstorage.AppUser;
import com.eyun.eyunstorage.product.dao.StorageInRecStateDao;
import com.eyun.eyunstorage.product.entity.StorageInRec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */

public class StorageInRecStateDaoImpl implements StorageInRecStateDao {
    private static StorageInRecStateDaoImpl storageInRecStateDao = null;

    public static StorageInRecStateDaoImpl getInstance() {

        if (storageInRecStateDao == null) {
            synchronized (new StorageInRecStateDaoImpl()) {
                if (storageInRecStateDao == null) {
                    storageInRecStateDao = new StorageInRecStateDaoImpl();
                }
            }
        }
        return storageInRecStateDao;
    }
    @Override
    public int getStorageInRecState(String recNumber) {
        String sqlSelect="Select RecState from storage_StorageInRecState where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()>0){
            return TypeConvert.toInteger(list.get(0).get("RecState"));
        }
        return 0;
    }

    @Override
    public boolean addStorageInRecState(String recNumber, int recState) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecNumber",SqlStringUtils.GetQuotedString(recNumber));
        map.put("RecState",recState);
        map.put("ScanTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        map.put("CreateUserID", AppUser.userId);
        map.put("CreateComBrID",AppUser.comBrId);
        //map.put("CreateComID", AppUser.comId);
        map.put("CreateIp",SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String sqlInsert=SqlStringUtils.GetConstructionInsert("storage_StorageInRecState",map);

        StorageInRecDaoImpl.getInstance().updateState(recNumber,recState);
        return BaseDao.getInstance().excute(sqlInsert);
    }

    @Override
    public boolean updateStorageInRecState(String recNumber, int recState) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecState",recState);
        map.put("ScanTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String where=" where RecNumber="+SqlStringUtils.GetQuotedString(recNumber);
        String sqlUpdate=SqlStringUtils.GetConstructionUpdate("storage_StorageInRecState",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }

    @Override
    public StorageInRec getFjNoRCRec() {
        String sqlSelect="Select t.* from storage_StorageInRec t where t.RecState="+ AppPublic.ScanType.FJ
                +" and CreateComBrID="+AppUser.comBrId;
        List<StorageInRec> lists=BaseDao.getInstance().query(sqlSelect,StorageInRec.class);
        if(lists.size()>0){
            return lists.get(0);
        }else{
            return null;
        }

    }
}
