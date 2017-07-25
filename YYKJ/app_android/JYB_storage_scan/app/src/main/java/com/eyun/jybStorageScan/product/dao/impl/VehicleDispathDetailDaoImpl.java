package com.eyun.jybStorageScan.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.jybStorageScan.product.dao.VehicleDispathDetailDao;
import com.eyun.jybStorageScan.product.entity.VehicleDispathDetail;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class VehicleDispathDetailDaoImpl implements VehicleDispathDetailDao {
    private static VehicleDispathDetailDaoImpl vehicleDispathDetailDao = null;

    public static VehicleDispathDetailDaoImpl getInstance() {
        if (vehicleDispathDetailDao == null) {
            synchronized (new VehicleDispathDetailDaoImpl()) {
                if (vehicleDispathDetailDao == null) {
                    vehicleDispathDetailDao = new VehicleDispathDetailDaoImpl();
                }
            }
        }
        return vehicleDispathDetailDao;
    }

    @Override
    public VehicleDispathDetail getVehicleDispathDetailByConsignRecNumber(String RecNumber) {
        String sqlSelect="Select * from  VehicleDispathDetail where ConsignRecNumber="
                + SqlStringUtils.GetQuotedString(RecNumber);

                List<VehicleDispathDetail> list=BaseDao.getInstance().query(sqlSelect,VehicleDispathDetail.class);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
