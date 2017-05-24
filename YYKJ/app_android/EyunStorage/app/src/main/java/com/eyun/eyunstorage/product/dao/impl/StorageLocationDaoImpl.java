package com.eyun.eyunstorage.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.eyunstorage.product.dao.StorageLocationDao;
import com.eyun.eyunstorage.product.entity.StorageLocation;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public class StorageLocationDaoImpl implements StorageLocationDao {

    private static StorageLocationDaoImpl storageLocationDao = null;

    public static StorageLocationDaoImpl getInstance() {
        if (storageLocationDao == null) {
            synchronized (new StorageLocationDaoImpl()) {
                if (storageLocationDao == null) {
                    storageLocationDao = new StorageLocationDaoImpl();
                }
            }
        }
        return storageLocationDao;
    }


    @Override
    public List<Map<String, Object>> getStorageLocation(long ComID, long ComBrID) {
        String sqlSelect="Select * from storage_StorageLocation where ComID="+ ComID;
        if(ComBrID>0)
        {
            sqlSelect+=" and ComBrID="+ComBrID;
        }
        return  BaseDao.getInstance().queryForList(sqlSelect);
    }

    @Override
    public StorageLocation getStorageLocatioin(String LocaBarCode) {
        String sqlSelect="Select * from storage_StorageLocation where LocaBarCode="+ SqlStringUtils.GetQuotedString(LocaBarCode);
        List<StorageLocation> list= BaseDao.getInstance().query(sqlSelect,StorageLocation.class);
        if(list!=null&&list.size()==1)
            return list.get(0);
        return  null;
    }
}
