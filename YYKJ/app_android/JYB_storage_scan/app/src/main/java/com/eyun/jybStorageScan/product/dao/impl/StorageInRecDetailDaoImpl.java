package com.eyun.jybStorageScan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybStorageScan.product.dao.StorageInRecDetailDao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/4.
 */

public class StorageInRecDetailDaoImpl implements StorageInRecDetailDao {

    private static StorageInRecDetailDaoImpl storageInRecDetailDao = null;

    public static StorageInRecDetailDaoImpl getInstance() {
        if (storageInRecDetailDao == null) {
            synchronized (new StorageInRecDetailDaoImpl()) {
                if (storageInRecDetailDao == null) {
                    storageInRecDetailDao = new StorageInRecDetailDaoImpl();
                }
            }
        }
        return storageInRecDetailDao;
    }

    @Override
    public List<Map<String, Object>> getStorageInRecDetailByRecNumber(String RecNumber) {
        String sqlSelect="Select * from StorageInRecDetail where RecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber);
        return BaseDao.getInstance().queryForList(sqlSelect);
    }


    @Override
    public boolean isThisDetailProBarCode(String RecNumber, long ProID) {

        String sqlSelect="Select 1 from StorageInRecDetail where RecNumber="
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
    public int getCount(String RecNumber, long ProID) {
        String sqlSelect="Select ProNumber from StorageInRecDetail where RecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber)
                +" and ProID="+ProID;

        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toInteger(list.get(0).get("ProNumber"));
        }
        return -1;
    }

    @Override
    public int getSumCount(String RecNumber) {
        String sqlSelect="Select Sum(ProNumber) ProNumber from StorageInRecDetail where RecNumber="
                +SqlStringUtils.GetQuotedString(RecNumber);

        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toInteger(list.get(0).get("ProNumber"));
        }
        return 0;
    }

    @Override
    public boolean hasThisProduct(String RecNumber, String ProID) {
        String sqlSelect="Select * from StorageInRecDetail where RecNumber="+SqlStringUtils.GetQuotedString(RecNumber)+
                " and proId="+ProID;
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list.size()>0){
            return  true;
        }
        return false;
    }

    @Override
    public boolean initStorageInRecDetail(String RecNumber) {
        String sql="insert into StorageInRecDetail(" +
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
                "pro.ProSpec,ps.name ProSpecName,pro.ProMeasureUnitName,t.ProNumber" +
                " from StorageInRecScan t " +
                "left join product pro on pro.proId=t.proId " +
                "left join ProductCategory pc on pro.ProCategory=pc.code " +
                "left join ProductSpec ps on pro.ProSpec=ps.code " +
                "where t.RecNumber="+SqlStringUtils.GetQuotedString(RecNumber);
        return BaseDao.getInstance().excute(sql);
    }
}
