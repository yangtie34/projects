package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.product.dao.StorageLocationProductDao;
import com.eyun.jybfreightscan.product.entity.StorageLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */

public class StorageLocationProductDaoImpl implements StorageLocationProductDao {
    private static StorageLocationProductDaoImpl storageLocationProductDao = null;

    public static StorageLocationProductDaoImpl getInstance() {
        if (storageLocationProductDao == null) {
            synchronized (new StorageLocationProductDaoImpl()) {
                if (storageLocationProductDao == null) {
                    storageLocationProductDao = new StorageLocationProductDaoImpl();
                }
            }
        }
        return storageLocationProductDao;
    }

    @Override
    public boolean addStorageLocationProduct(StorageLocation storageLocation, long ProID, int ProNumber) {
        String sql = "Select * from StorageLocationProduct where ProID=" + ProID + " and StorLocaCode=" + storageLocation.getCode();
        List<Map<String, Object>> list = BaseDao.getInstance().queryForList(sql);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (list.size() > 0) {
            map.put("ProNumber", "ProNumber+"+ProNumber);
            String where = " where ProID=" + ProID + " and StorLocaCode=" + storageLocation.getCode();
            sql = SqlStringUtils.GetConstructionUpdate("StorageLocationProduct", map, where);

        } else {
            map.put("StorLocaCode", storageLocation.getCode());
            map.put("StorLocaComBrID", storageLocation.getComBrId());
            map.put("StorLocaComID", storageLocation.getComId());
            map.put("ProID", ProID);
            map.put("ProNumber", ProNumber);
            sql = SqlStringUtils.GetConstructionInsert("StorageLocationProduct", map);
        }
        return BaseDao.getInstance().excute(sql);
    }
    @Override
    public boolean updateStorageLocationProduct(StorageLocation storageLocation, long ProID, int ProNumber) {
        if(ProNumber==0){
            return BaseDao.getInstance().excute("delete StorageLocationProduct where ProID=" + ProID + " and StorLocaCode=" + storageLocation.getCode());
        }
        String sql = "Select * from StorageLocationProduct where ProID=" + ProID + " and StorLocaCode=" + storageLocation.getCode();
        List<Map<String, Object>> list = BaseDao.getInstance().queryForList(sql);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (list.size() > 0) {
            map.put("ProNumber", ProNumber);
            String where = " where ProID=" + ProID + " and StorLocaCode=" + storageLocation.getCode();
            sql = SqlStringUtils.GetConstructionUpdate("StorageLocationProduct", map, where);

        } else {
            map.put("StorLocaCode", storageLocation.getCode());
            map.put("StorLocaComBrID", storageLocation.getComBrId());
            map.put("StorLocaComID", storageLocation.getComId());
            map.put("ProID", ProID);
            map.put("ProNumber", ProNumber);
            sql = SqlStringUtils.GetConstructionInsert("StorageLocationProduct", map);
        }
        return BaseDao.getInstance().excute(sql);
    }
    @Override
    public int getLocationProNumber(long StorLocaCode) {
        String sql = "Select sum(ProNumber) number from StorageLocationProduct where StorLocaCode=" + StorLocaCode;
        List<Map<String, Object>> list = BaseDao.getInstance().queryForList(sql);
        if(list!=null&&list.size()>0){
            return TypeConvert.toInteger(list.get(0).get("number"));
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> getStorageLocationProducts(long StorLocaCode) {
        String sql = "Select t.*,pro.proName,pro.ProMeasureUnitName,pc.name ProCategoryName from StorageLocationProduct t " +
                " left join product pro on pro.proId=t.proId " +
                "left join ProductCategory pc on pro.ProCategory=pc.code " +
                " where t.StorLocaCode=" + StorLocaCode;
        List<Map<String, Object>> list = BaseDao.getInstance().queryForList(sql);
        return list;
    }
}
