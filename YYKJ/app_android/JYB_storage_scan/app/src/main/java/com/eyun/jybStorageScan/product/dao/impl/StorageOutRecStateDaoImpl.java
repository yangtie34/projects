package com.eyun.jybStorageScan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybStorageScan.AppPublic;
import com.eyun.jybStorageScan.AppUser;
import com.eyun.jybStorageScan.product.dao.StorageOutRecStateDao;
import com.eyun.jybStorageScan.product.entity.StorageInRec;
import com.eyun.jybStorageScan.product.entity.StorageOutRec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */

public class StorageOutRecStateDaoImpl implements StorageOutRecStateDao {
    private static StorageOutRecStateDaoImpl storageOutRecStateDao = null;

    public static StorageOutRecStateDaoImpl getInstance() {

        if (storageOutRecStateDao == null) {
            synchronized (new StorageOutRecStateDaoImpl()) {
                if (storageOutRecStateDao == null) {
                    storageOutRecStateDao = new StorageOutRecStateDaoImpl();
                }
            }
        }
        return storageOutRecStateDao;
    }

    @Override
    public int getStorageOutRecState(String recNumber) {
        String sqlSelect="Select RecState from storage_StorageOutRecState where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()>0){
            return TypeConvert.toInteger(list.get(0).get("RecState"));
        }
        return 0;
    }

    @Override
    public boolean addStorageOutRecState(String recNumber, int recState) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecNumber",SqlStringUtils.GetQuotedString(recNumber));
        map.put("RecState",recState);
        map.put("ScanTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        map.put("CreateUserID", AppUser.userId);
        map.put("CreateComBrID",AppUser.comBrId);
        map.put("CreateComID",AppUser.comId);
        map.put("CreateIp",SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String sqlInsert=SqlStringUtils.GetConstructionInsert("storage_StorageOutRecState",map);
        StorageOutRecDaoImpl.getInstance().updateState(recNumber,recState);
        return BaseDao.getInstance().excute(sqlInsert);
    }

    @Override
    public boolean updateStorageOutRecState(String recNumber, int recState) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecState",recState);
        map.put("ScanTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String where=" where RecNumber="+SqlStringUtils.GetQuotedString(recNumber);
        String sqlUpdate=SqlStringUtils.GetConstructionUpdate("storage_StorageOutRecState",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }

    @Override
    public StorageOutRec getCCNoFJRec() {
        String sqlSelect="Select t.* from storage_StorageOutRec t where t.RecState="+ AppPublic.ScanType.CRC
                +" and CreateComBrID="+AppUser.comBrId;
        List<StorageOutRec> lists=BaseDao.getInstance().query(sqlSelect,StorageOutRec.class);
        if(lists.size()>0){
            return lists.get(0);
        }else{
            return null;
        }
    }
}
