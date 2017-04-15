package com.eyun.jybStorageScan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybStorageScan.product.dao.StorageTakeRecDetailDao;
import com.eyun.jybStorageScan.product.dao.StorageTakeRecDetailDao;
import com.eyun.jybStorageScan.product.entity.StorageTakeRec;
import com.eyun.jybStorageScan.product.entity.StorageTakeRecScan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public class StorageTakeRecDetailDaoImpl implements StorageTakeRecDetailDao {

    private static StorageTakeRecDetailDaoImpl storageTakeRecDetailDao = null;

    public static StorageTakeRecDetailDaoImpl getInstance() {
        if (storageTakeRecDetailDao == null) {
            synchronized (new StorageTakeRecDetailDaoImpl()) {
                if (storageTakeRecDetailDao == null) {
                    storageTakeRecDetailDao = new StorageTakeRecDetailDaoImpl();
                }
            }
        }
        return storageTakeRecDetailDao;
    }


    @Override
    public boolean initStorageTakeRecDetail(StorageTakeRec storageTakeRec) {
        String sql="insert into StorageTakeRecDetail(" +
                "RecNumber," +
                "ProID," +
                "ProName," +
                "ProCategory," +
                "ProCategoryName," +
                "ProSpec," +
                "ProSpecName," +
                "ProMeasureUnitName," +
                "ProNumber," +
                "ProBookNumber)" +
                "select '"+storageTakeRec.getRecNumber()+"' RecNumber," +
                "t.proId,pro.proName,pro.ProCategory,pc.name ProCategoryName," +
                "pro.ProSpec,ps.name ProSpecName,pro.ProMeasureUnitName,t.ProNumber," +
                "t.ProNumber" +
                " from StorageLocationProduct t " +
                "left join product pro on pro.proId=t.proId " +
                "left join ProductCategory pc on pro.ProCategory=pc.code " +
                "left join ProductSpec ps on pro.ProSpec=ps.code " +
                "where t.StorLocaCode="+storageTakeRec.getStorLocaCode();
        return BaseDao.getInstance().excute(sql);
    }

    @Override
    public boolean updateStorageTakeRecDetail(StorageTakeRecScan mo) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("ProNumber",mo.getScanNumber());
        map.put("ProBookNumber","ProNumber");
        map.put("ProDifferNumber","ProNumber-ProBookNumber");
        String where=" where RecNumber="+SqlStringUtils.GetQuotedString(mo.getRecNumber())
                +" and ProID="+mo.getProId();
        String sqlUpdate=SqlStringUtils.GetConstructionUpdate("StorageTakeRecDetail",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }
    @Override
    public List<Map<String, Object>> getStorageTakeRecDetailByRecNumber(String RecNumber) {
        String sqlSelect="Select * from StorageTakeRecDetail where RecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber);
        return BaseDao.getInstance().queryForList(sqlSelect);
    }

    @Override
    public int getSumCount(String RecNumber) {
        String sqlSelect="Select Sum(ProNumber) ProNumber from StorageTakeRecDetail where RecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toInteger(list.get(0).get("ProNumber"));
        }
        return 0;
    }
}
