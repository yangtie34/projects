package com.eyun.eyunstorage.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.eyunstorage.AppUser;
import com.eyun.eyunstorage.product.dao.StorageTakeRecScanDao;
import com.eyun.eyunstorage.product.entity.StorageTakeRec;
import com.eyun.eyunstorage.product.entity.StorageTakeRecScan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public class StorageTakeRecScanDaoImpl implements StorageTakeRecScanDao {

    private static StorageTakeRecScanDaoImpl storageTakeRecScanDao = null;

    public static StorageTakeRecScanDaoImpl getInstance() {
        if (storageTakeRecScanDao == null) {
            synchronized (new StorageTakeRecScanDaoImpl()) {
                if (storageTakeRecScanDao == null) {
                    storageTakeRecScanDao = new StorageTakeRecScanDaoImpl();
                }
            }
        }
        return storageTakeRecScanDao;
    }


    @Override
    public boolean addScanInfo(StorageTakeRecScan mo) {

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("CreateComBrId",AppUser.comBrId);
        //map.put("CreateComID", AppUser.comId);
        map.put("CreateIp",SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateUserId", AppUser.userId);
        map.put("ProId",mo.getProId());
       // map.put("ProNumber",mo.getProNumber());
        map.put("StorLocaCode",mo.getStorLocaCode());
        map.put("StorLocaComBrID",mo.getStorLocaComBrId());
        map.put("RecNumber",SqlStringUtils.GetQuotedString(mo.getRecNumber()));
        map.put("ScanNumber",mo.getScanNumber());
        map.put("ScanType",mo.getScanType());

        String sqlInsert=SqlStringUtils.GetConstructionInsert("storage_StorageTakeRecScan",map);
        return BaseDao.getInstance().excute(sqlInsert);
    }

    @Override
    public boolean createScansByStorageTakeRecDetails(StorageTakeRec storageInRec, int ScanType) {
        return false;
    }

    @Override
    public boolean updateScanInfo(StorageTakeRecScan mo) {

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("ScanNumber","ScanNumber+"+mo.getScanNumber());
        String where=" where RecNumber="+SqlStringUtils.GetQuotedString(mo.getRecNumber())
                +" and ProID="+mo.getProId()+" and ScanType="+mo.getScanType();
        String sqlUpdate=SqlStringUtils.GetConstructionUpdate("storage_StorageTakeRecScan",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }

    @Override
    public StorageTakeRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType) {

        String sqlSelect="Select t.*,pro.proName from storage_StorageTakeRecScan t " +
        " left join product pro on pro.proId=t.ProID " +
                "where t.RecNumber="+SqlStringUtils.GetQuotedString(RecNumber) +" and t.ProID="+ProID+" and t.ScanType="+ScanType ;
        List<StorageTakeRecScan> list= BaseDao.getInstance().query(sqlSelect,StorageTakeRecScan.class);
        if(list!=null&&list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public List<StorageTakeRecScan> getStorageTakeRecScans(String RecNumber, int ScanType) {
        String sqlSelect="Select * from storage_StorageTakeRecScan where RecNumber="+SqlStringUtils.GetQuotedString(RecNumber)
                +"  and ScanType="+ScanType;
        List<StorageTakeRecScan> list= BaseDao.getInstance().query(sqlSelect,StorageTakeRecScan.class);
        if(list!=null&&list.size()>0)
            return list;
        return new ArrayList<>();
    }
    public boolean isExist(StorageTakeRecScan mo) {
        String sqlSelect="Select ScanNumber from storage_StorageTakeRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(mo.getRecNumber())
                +" and ProID="+mo.getProId()+" and ScanType="+mo.getScanType();
        if(mo.getStorLocaCode()!=null){
            sqlSelect+=" and StorLocaCode="+mo.getStorLocaCode();
        }
        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlSelect);
        if(list.size()>0){
            return true;
        }
        return false;
    }
}
