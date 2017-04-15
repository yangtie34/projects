package com.eyun.jybStorageScan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.jybStorageScan.AppPublic;
import com.eyun.jybStorageScan.AppUser;
import com.eyun.jybStorageScan.product.dao.StorageTakeRecDao;
import com.eyun.jybStorageScan.product.dao.StorageTakeRecDao;
import com.eyun.jybStorageScan.product.entity.StorageLocation;
import com.eyun.jybStorageScan.product.entity.StorageTakeRec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public class StorageTakeRecDaoImpl implements StorageTakeRecDao {

    private static StorageTakeRecDaoImpl storageTakeRecDao = null;

    public static StorageTakeRecDaoImpl getInstance() {
        if (storageTakeRecDao == null) {
            synchronized (new StorageTakeRecDaoImpl()) {
                if (storageTakeRecDao == null) {
                    storageTakeRecDao = new StorageTakeRecDaoImpl();
                }
            }
        }
        return storageTakeRecDao;
    }


    @Override
    public StorageTakeRec getStorageTakeRecByStorLocaCode(long StorLocaCode) {
        String sql=" select * from StorageTakeRec where StorLocaCode="+StorLocaCode+
                " and RecState<3 and CreateTime between '"+ DateUtils.getDateBefore(2) +"' and '"+DateUtils.getCurrentTime()+"'";
        List<StorageTakeRec> storageTakeRecs= BaseDao.getInstance().query(sql,StorageTakeRec.class);
        if(storageTakeRecs!=null&&storageTakeRecs.size()>0){
            return storageTakeRecs.get(0);
        }else{
            StorageTakeRec storageTakeRec=new StorageTakeRec();
            storageTakeRec.setStorLocaCode(StorLocaCode);
            return storageTakeRec;
        }
    }

    @Override
    public boolean addStorageTakeRec(StorageTakeRec storageTakeRec) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecNumber",SqlStringUtils.GetQuotedString(storageTakeRec.getRecNumber()));
        map.put("RecState",storageTakeRec.getRecState());
        map.put("StorLocaCode",storageTakeRec.getStorLocaCode());
        map.put("CreateUserID", AppUser.userId);
        map.put("CreateComBrID",AppUser.comBrId);
        map.put("CreateComID",AppUser.comId);
        map.put("CreateIp",SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String sqlInsert=SqlStringUtils.GetConstructionInsert("StorageTakeRec",map);
        return BaseDao.getInstance().excute(sqlInsert);
    }

    @Override
    public boolean updateStorageTakeRec(StorageTakeRec storageTakeRec) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecState",storageTakeRec.getRecState());
        String where = " where RecNumber=" + SqlStringUtils.GetQuotedString(storageTakeRec.getRecNumber());
        String sql = SqlStringUtils.GetConstructionUpdate("StorageTakeRec", map, where);
        return BaseDao.getInstance().excute(sql);
    }
    @Override
    public boolean clearByStorageTakeRec(String RecNumber,int RecState) {
        String sql=" delete StorageTakeRec where RecNumber="+SqlStringUtils.GetQuotedString(RecNumber)+
                " and RecState="+ RecState;
        return BaseDao.getInstance().excute(sql);
    }
}
