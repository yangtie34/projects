package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.jybfreightscan.AppUser;
import com.eyun.jybfreightscan.product.dao.StorageLocationChangeDao;
import com.eyun.jybfreightscan.product.entity.StorageLocation;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/15.
 */

public class StorageLocationChangeDaoImpl implements StorageLocationChangeDao {
    private static StorageLocationChangeDaoImpl storageLocationChangeDao = null;

    public static StorageLocationChangeDaoImpl getInstance() {
        if (storageLocationChangeDao == null) {
            synchronized (new StorageLocationChangeDaoImpl()) {
                if (storageLocationChangeDao == null) {
                    storageLocationChangeDao = new StorageLocationChangeDaoImpl();
                }
            }
        }
        return storageLocationChangeDao;
    }
    @Override
    public boolean addStorageLocationChange(int ChangeType, StorageLocation storageLocation, Long ProID, int ProNumber) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("ChangeType", ChangeType);
        map.put("StorLocaCode", storageLocation.getCode());
        map.put("StorLocaComBrID", storageLocation.getComBrId());
        map.put("StorLocaComID", storageLocation.getComId());
        map.put("ProID", ProID);
        map.put("ProNumber", ProNumber);
        map.put("CreateUserID", AppUser.userId);
        map.put("CreateComID", AppUser.comId);
        map.put("CreateComBrID", AppUser.comBrId);
        map.put("CreateIp", SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime", SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String sql= SqlStringUtils.GetConstructionInsert("StorageLocationChange",map);
        return BaseDao.getInstance().excute(sql);
    }
}
