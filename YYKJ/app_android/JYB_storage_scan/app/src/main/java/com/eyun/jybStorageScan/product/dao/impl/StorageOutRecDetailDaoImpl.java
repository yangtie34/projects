package com.eyun.jybStorageScan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybStorageScan.product.dao.StorageOutRecDetailDao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public class StorageOutRecDetailDaoImpl implements StorageOutRecDetailDao {

    private static StorageOutRecDetailDaoImpl storageOutRecDetailDao = null;

    public static StorageOutRecDetailDaoImpl getInstance() {
        if (storageOutRecDetailDao == null) {
            synchronized (new StorageOutRecDetailDaoImpl()) {
                if (storageOutRecDetailDao == null) {
                    storageOutRecDetailDao = new StorageOutRecDetailDaoImpl();
                }
            }
        }
        return storageOutRecDetailDao;
    }

    @Override
    public int getCount(String RecNumber, long PorID) {

        String sqlSelect="Select ProNumber from storage_StorageOutRecDetail where RecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber)
                +" and ProID="+PorID;

        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toInteger(list.get(0).get("ProNumber"));
        }
        return -1;
    }

    @Override
    public boolean isThisDetailProBarCode(String RecNumber, long ProID) {
        String sqlSelect="Select 1 from storage_StorageOutRecDetail where RecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber)
                +" and ProID="+ProID;

        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> getStorageOutRecDetailByRecNumber(String RecNumber) {
        String sqlSelect="Select * from storage_StorageOutRecDetail where RecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber);
        return BaseDao.getInstance().queryForList(sqlSelect);
    }

    @Override
    public int getSumCount(String RecNumber) {
        String sqlSelect="Select Sum(ProNumber) ProNumber from storage_StorageOutRecDetail where RecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber);

        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toInteger(list.get(0).get("ProNumber"));
        }
        return 0;
    }

    @Override
    public boolean initStorageOutRecDetail(String RecNumber) {
            String sql="insert into storage_StorageOutRecDetail(" +
                    "RecNumber," +
                    "ProID," +
                    "ProBarCode," +
                    "ProName," +
                    "ProCategory," +
                    "ProCategoryName," +
                    "ProSpec," +
                    "ProSpecName," +
                    "ProMeasureUnitName," +
                    "ProNumber)" +
                    "select '"+RecNumber+"' RecNumber," +
                    "t.proId,pro.ProBarCode,pro.proName,pro.ProCategory,pc.name ProCategoryName," +
                    " pro.ProSpec,ps.name ProSpecName,pro.ProMeasureUnitName,sum(t.ProNumber) ProNumber" +
                    " from storage_StorageOutRecScan t" +
                    " left join product pro on pro.proId=t.proId" +
                    " left join ProductCategory pc on pro.ProCategory=pc.code" +
                    " left join ProductSpec ps on pro.ProSpec=ps.code" +
                    " where t.RecNumber="+SqlStringUtils.GetQuotedString(RecNumber)+
                    " group by t.proId,pro.ProBarCode,pro.proName,pro.ProCategory,pc.name ," +
                    " pro.ProSpec,ps.name,pro.ProMeasureUnitName";
            return BaseDao.getInstance().excute(sql);
     }

    @Override
    public int getProNumber(String RecNumber, long proId) {
        String sqlSelect="Select Sum(ProNumber) ProNumber from storage_StorageOutRecDetail where RecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber) +" and proId="+proId;

        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toInteger(list.get(0).get("ProNumber"));
        }
        return 0;
    }

}
