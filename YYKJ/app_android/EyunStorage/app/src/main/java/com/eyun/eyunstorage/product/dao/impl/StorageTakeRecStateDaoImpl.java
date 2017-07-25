package com.eyun.eyunstorage.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.eyunstorage.AppUser;
import com.eyun.eyunstorage.product.dao.StorageTakeRecStateDao;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/15.
 */

public class StorageTakeRecStateDaoImpl implements StorageTakeRecStateDao {
    private static StorageTakeRecStateDaoImpl storageTakeRecStateDao = null;

    public static StorageTakeRecStateDaoImpl getInstance() {

        if (storageTakeRecStateDao == null) {
            synchronized (new StorageTakeRecStateDaoImpl()) {
                if (storageTakeRecStateDao == null) {
                    storageTakeRecStateDao = new StorageTakeRecStateDaoImpl();
                }
            }
        }
        return storageTakeRecStateDao;
    }

    @Override
    public boolean addStorageTakeRecState(String recNumber, int recState) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecNumber",SqlStringUtils.GetQuotedString(recNumber));
        map.put("RecState",recState);
        map.put("ScanTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        map.put("CreateUserID", AppUser.userId);
        map.put("CreateComBrID",AppUser.comBrId);
        //map.put("CreateComID", AppUser.comId);
        map.put("CreateIp",SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String sqlInsert=SqlStringUtils.GetConstructionInsert("storage_StorageTakeRecState",map);
        return BaseDao.getInstance().excute(sqlInsert);
    }


}
