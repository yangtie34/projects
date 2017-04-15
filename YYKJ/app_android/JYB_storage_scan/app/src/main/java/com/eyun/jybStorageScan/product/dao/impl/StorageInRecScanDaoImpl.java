package com.eyun.jybStorageScan.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.StringUtils;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybStorageScan.AppPublic;
import com.eyun.jybStorageScan.AppUser;
import com.eyun.jybStorageScan.product.dao.StorageInRecScanDao;
import com.eyun.jybStorageScan.product.entity.StorageInRec;
import com.eyun.jybStorageScan.product.entity.StorageInRecScan;
import com.eyun.jybStorageScan.product.entity.StorageLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/3/4.
 */

public class StorageInRecScanDaoImpl implements StorageInRecScanDao {

    private static StorageInRecScanDaoImpl storageInRecScanDao = null;

    public static StorageInRecScanDaoImpl getInstance() {
        if (storageInRecScanDao == null) {
            synchronized (new StorageInRecScanDaoImpl()) {
                if (storageInRecScanDao == null) {
                    storageInRecScanDao = new StorageInRecScanDaoImpl();
                }
            }
        }
        return storageInRecScanDao;
    }

    @Override
    public boolean initScansByStorageInRecDetails(String RecNumber,int ScanType){
        String sql="select * from StorageInRecScan where RecNumber="+SqlStringUtils.GetQuotedString(RecNumber) +" and ScanType="+ScanType ;
        List<Map<String, Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list.size()==0){
            sql="insert into storageInRecScan(scanType,scanErrorType,scanNumber,recNumber,proId,proNumber," +
                    " createUserID,createComBrId,createComId,createIp,createTime) " +
                    " select "+ScanType+" scanType,0 scanErrorType,0 scanNumber,recNumber,proId,proNumber," +
                    " "+ AppUser.userId+" createUserID,"+AppUser.comBrId+" createComBrId,"+AppUser.comId+" createComId,'"
                    + NetWorkUtil.getHostIP()+"' createIp,'"+ DateUtils.getCurrentTime()+"' createTime " +
                    " from storageInRecDetail where recNumber="+SqlStringUtils.GetQuotedString(RecNumber);
            return BaseDao.getInstance().excute(sql);
        }else{
            return true;
        }
    }
    @Override
    public boolean addScanInfo(String RecNumber,String ProID,int ScanType, StorageLocation storageLocation,int number){
        String sql="insert into storageInRecScan(scanType,scanErrorType,scanNumber," ;
        if(storageLocation!=null){
            sql+= "StorLocaCode," +
                "StorLocaComBrID," +
                "StorLocaComID," ;
        }
        sql+= "recNumber,proId,proNumber," +
                " createUserID,createComBrId,createComId,createIp,createTime) " +
                " select "+ScanType+" scanType,0 scanErrorType,"+number+" scanNumber,";
        if(storageLocation!=null){
            sql+= storageLocation.getCode()+" StorLocaCode," +
                    storageLocation.getComBrId()+" StorLocaComBrID,"+
                    storageLocation.getComId()+"StorLocaComID," ;
        }
        sql+= " recNumber,proId,proNumber," +
                " "+ AppUser.userId+" createUserID,"+AppUser.comBrId+" createComBrId,"+AppUser.comId+" createComId,'"
                + NetWorkUtil.getHostIP()+"' createIp,'"+ DateUtils.getCurrentTime()+"' createTime " +
                " from storageInRecDetail where recNumber="+SqlStringUtils.GetQuotedString(RecNumber)+" and  proId="+ProID;
        return BaseDao.getInstance().excute(sql);
    };

    @Override
    public StorageInRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType, StorageLocation storageLocation) {
        String sqlSelect="Select t.* ,pro.proName from  StorageInRecScan t " +
                "left join product pro on pro.proId=t.ProID " +
                " where t.RecNumber="+SqlStringUtils.GetQuotedString(RecNumber)
                +" and t.ProID="+ProID+" and t.ScanType="+ScanType;
        if(storageLocation!=null){
            sqlSelect+=" and t.StorLocaCode="+storageLocation.getCode();
        }
        List<StorageInRecScan> list= BaseDao.getInstance().query(sqlSelect,StorageInRecScan.class);
        if(list!=null&&list.size()>0)
            return list.get(0);
        return null;
    }


    @Override
    public boolean addScanInfo(StorageInRecScan mo) {

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("CreateComBrId",mo.getCreateComBrId());
        map.put("CreateComId",mo.getCreateComId());
        map.put("CreateIp",SqlStringUtils.GetQuotedString(mo.getCreateIp()));
        map.put("CreateUserId",mo.getCreateUserId());
        map.put("ProId",mo.getProId());
        map.put("ComSuppID",mo.getComSuppID());
        map.put("ProNumber",mo.getProNumber());
        map.put("RecNumber",SqlStringUtils.GetQuotedString(mo.getRecNumber()));
        map.put("ScanNumber",mo.getScanNumber());
        map.put("ScanType",mo.getScanType());
        if(mo.getStorLocaCode()!=null){
            map.put("StorLocaCode",mo.getStorLocaCode());
            map.put("StorLocaComBrID",mo.getStorLocaComBrID());
            map.put("StorStorLocaComID",mo.getStorLocaComID());
        }
        String sqlInsert=SqlStringUtils.GetConstructionInsert("StorageInRecScan",map);
        return BaseDao.getInstance().excute(sqlInsert);
    }

    @Override
    public boolean updateScanInfo(StorageInRecScan mo) {

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("ScanNumber","ScanNumber+"+mo.getScanNumber());
        String where=" where RecNumber="+SqlStringUtils.GetQuotedString(mo.getRecNumber())
                +" and ProID="+mo.getProId()+" and ScanType="+mo.getScanType();
        String sqlUpdate=SqlStringUtils.GetConstructionUpdate("StorageInRecScan",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }

    @Override
    public boolean addScanNumber(String RecNumber, String ProID, int ScanType, StorageLocation storageLocation,int number) {
        String sql="update StorageInRecScan set ScanNumber=ScanNumber+ "+number ;
        if(storageLocation!=null){
            sql+= ",StorLocaCode=" +storageLocation.getCode()+
                    ",StorLocaComBrID=" +storageLocation.getComBrId()+
                    ",StorLocaComID="+storageLocation.getComId();
        }
        sql+=" where RecNumber="+SqlStringUtils.GetQuotedString(RecNumber)+
                " and proID ="+ProID+
                " and scanType="+ScanType;

        return BaseDao.getInstance().excute(sql);
    }

    @Override
    public boolean isFullNumber(String RecNumber, String ProID, int ScanType,int number) {
        String sqlSelect="Select avg(proNumber)-sum(ScanNumber)-"+number+" number from StorageInRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(RecNumber)
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

    @Override
    public int getCount(String RecNumber, long ProID, int ScanType) {

        String sqlSelect="Select ScanNumber from StorageInRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(RecNumber)
                +" and ProID="+ProID+" and ScanType="+ScanType;

        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
           return TypeConvert.toInteger(list.get(0).get("ScanNumber"));
        }
        return -1;
    }

    @Override
    public boolean isExist(StorageInRecScan mo) {
        String sqlSelect="Select ScanNumber from StorageInRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(mo.getRecNumber())
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
    public int getRecInStorageNumber(String RecNumber) {
        String sqlSelect="Select sum(ScanNumber) number from StorageInRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(RecNumber)
                +" and ScanType="+ AppPublic.ScanType.CRC;
        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlSelect);
        if(list.size()>0){
            return TypeConvert.toInteger(list.get(0).get("number"));
        }
        return 0;
    }

    @Override
    public int getRecInStorageLocalNumber(String RecNumber,Long localCode) {
        String sqlSelect="Select sum(ScanNumber) number from StorageInRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(RecNumber)
                +" and StorLocaCode="+localCode+" and ScanType="+ AppPublic.ScanType.CRC;
        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlSelect);
        if(list.size()>0){
            return TypeConvert.toInteger(list.get(0).get("number"));
        }
        return 0;
    }

    @Override
    public int getInRecScanNumber(String recNumber) {
        String sqlSelect="Select sum(ScanNumber) number from StorageInRecScan where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber)
                +" and ScanType="+ AppPublic.ScanType.FJ;
        List<Map<String,Object>> list=BaseDao.getInstance().queryForList(sqlSelect);
        if(list.size()>0){
            return TypeConvert.toInteger(list.get(0).get("number"));
        }
        return 0;
    }

    public List<Map<String,Object>> getScanbyRecNumberAndScanType(String recNumber, int scanType) {
        String sql="select t.ProID ProID,pro.ProName,t.ScanNumber ProNumber,pro.ProMeasureUnitName,pro.ProCategory ProCategoryName from StorageInRecScan t " +
                " left join product pro on pro.proId=t.ProID " +
                "where t.RecNumber="+SqlStringUtils.GetQuotedString(recNumber) +" and t.ScanType="+scanType ;
        List<Map<String, Object>> list= BaseDao.getInstance().queryForList(sql);
        return list;
    }
    public List<StorageInRecScan> getStorageInRecScan(String recNumber, int scanType) {
        String sql="select * from StorageInRecScan t " +
                "where t.RecNumber="+SqlStringUtils.GetQuotedString(recNumber) +" and t.ScanType="+scanType ;
        List<StorageInRecScan> list= BaseDao.getInstance().query(sql,StorageInRecScan.class);
        return list;
    }
}
