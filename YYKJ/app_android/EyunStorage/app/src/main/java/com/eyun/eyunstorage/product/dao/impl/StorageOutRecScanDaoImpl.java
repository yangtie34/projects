package com.eyun.eyunstorage.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.eyunstorage.AppPublic;
import com.eyun.eyunstorage.AppUser;
import com.eyun.eyunstorage.product.dao.StorageOutRecScanDao;
import com.eyun.eyunstorage.product.entity.StorageLocation;
import com.eyun.eyunstorage.product.entity.StorageOutRec;
import com.eyun.eyunstorage.product.entity.StorageOutRecScan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public class StorageOutRecScanDaoImpl implements StorageOutRecScanDao {

    private static StorageOutRecScanDaoImpl storageOutRecScanDao = null;

    public static StorageOutRecScanDaoImpl getInstance() {
        if (storageOutRecScanDao == null) {
            synchronized (new StorageOutRecScanDaoImpl()) {
                if (storageOutRecScanDao == null) {
                    storageOutRecScanDao = new StorageOutRecScanDaoImpl();
                }
            }
        }
        return storageOutRecScanDao;
    }
    @Override
    public boolean isExist(StorageOutRecScan mo) {
        String sqlSelect="Select ScanNumber from storage_StorageOutRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(mo.getRecNumber())
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
    @Override
    public int getCount(String RecNumber, long ProID, int ScanType) {

        String sqlSelect="Select ScanNumber from storage_StorageOutRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(RecNumber)
                +" and ProID="+ProID+" and ScanType="+ScanType;
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toInteger(list.get(0).get("ScanNumber"));
        }
        return -1;
    }

    @Override
    public boolean addScanInfo(StorageOutRecScan mo) {

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("CreateComBrId", AppUser.comBrId);
        //map.put("CreateComID", AppUser.comId);
        map.put("CreateIp",SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateUserId",AppUser.userId);
        map.put("ProId",mo.getProId());
        map.put("ProNumber",mo.getProNumber());
        map.put("RecNumber",SqlStringUtils.GetQuotedString(mo.getRecNumber()));
        map.put("ScanNumber",mo.getScanNumber());
        map.put("ScanType",mo.getScanType());
        if(mo.getStorLocaCode()!=null){
            map.put("StorLocaCode",mo.getStorLocaCode());
            map.put("StorLocaComBrID",mo.getStorLocaComBrID());
        }
        String sqlInsert=SqlStringUtils.GetConstructionInsert("storage_StorageOutRecScan",map);
        return BaseDao.getInstance().excute(sqlInsert);
    }

    @Override
    public boolean createScansByStorageOutRecDetails(StorageOutRec storageInRec, int ScanType) {
        return false;
    }

    @Override
    public boolean updateScanInfo(StorageOutRecScan mo) {

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("ScanNumber","ScanNumber+"+mo.getScanNumber());
        String where=" where RecNumber="+SqlStringUtils.GetQuotedString(mo.getRecNumber())
                +" and ProID="+mo.getProId()+" and ScanType="+mo.getScanType();
        String sqlUpdate=SqlStringUtils.GetConstructionUpdate("storage_StorageOutRecScan",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }

    @Override
    public StorageOutRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType, StorageLocation storageLocation) {

        String sqlSelect="Select t.* ,pro.proName from storage_StorageOutRecScan t "+
                " left join product pro on pro.proId=t.ProID " +
                " where t.RecNumber="+SqlStringUtils.GetQuotedString(RecNumber)
                +" and t.ProID="+ProID+" and t.ScanType="+ScanType;
        if(storageLocation!=null){
            sqlSelect+=" and t.StorLocaCode="+storageLocation.getCode();
        }
        List<StorageOutRecScan> list= BaseDao.getInstance().query(sqlSelect,StorageOutRecScan.class);
        if(list!=null&&list.size()>0)
            return list.get(0);
        return null;
    }
    @Override
    public int getOutRecScanNumber(String recNumber) {
        String sqlSelect="Select sum(ScanNumber) number from storage_StorageOutRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber)
                +" and ScanType="+ AppPublic.ScanType.CRC;
        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlSelect);
        if(list.size()>0){
            return TypeConvert.toInteger(list.get(0).get("number"));
        }
        return 0;
    }
    @Override
    public int getRecOutStorageNumber(String recNumber) {
        String sqlSelect="Select sum(ScanNumber) number from storage_StorageOutRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber)
                +" and ScanType="+ AppPublic.ScanType.FJ;
        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlSelect);
        if(list.size()>0){
            return TypeConvert.toInteger(list.get(0).get("number"));
        }
        return 0;
    }
    @Override
    public int getRecOutStorageLocalNumber(String RecNumber,Long localCode) {
        String sqlSelect="Select sum(ScanNumber) number from storage_StorageOutRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(RecNumber)
                +" and StorLocaCode="+localCode+" and ScanType="+ AppPublic.ScanType.FJ;
        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlSelect);
        if(list.size()>0){
            return TypeConvert.toInteger(list.get(0).get("number"));
        }
        return 0;
    }
    public List<StorageOutRecScan> getStorageOutRecScan(String recNumber, int scanType) {
        String sql="select t.*,pro.proName from storage_StorageOutRecScan t " +
                " left join product pro on pro.proId=t.ProID " +
                "where t.RecNumber="+SqlStringUtils.GetQuotedString(recNumber) +" and t.ScanType="+scanType ;
        List<StorageOutRecScan> list= BaseDao.getInstance().query(sql,StorageOutRecScan.class);
        return list;
    }

    @Override
    public boolean isFullNumber(String RecNumber, String ProID, int ScanType,int number) {
        String sqlSelect="Select avg(proNumber)-sum(ScanNumber)-"+number+" number from storage_StorageOutRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(RecNumber)
                +" and ProID="+ProID+" and ScanType="+ScanType;
        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlSelect);
        if(list.size()==0){
            return false;
        }else if(list.get(0).get("number")==null){
            return false;
        }else{
            return TypeConvert.toInteger(list.get(0).get("number"))<0;
        }
    }

    public List<Map<String,Object>> getScanbyRecNumberAndScanType(String recNumber, int scanType) {
            String sql="select t.ProID ProID,pro.ProName,t.ScanNumber ProNumber,pro.ProMeasureUnitName,pro.ProCategory ProCategoryName from storage_StorageOutRecScan t " +
                    " left join product pro on pro.proId=t.ProID " +
                    "where t.RecNumber="+SqlStringUtils.GetQuotedString(recNumber) +" and t.ScanType="+scanType ;
            List<Map<String, Object>> list= BaseDao.getInstance().queryForList(sql);
            return list;
    }
}
