package com.eyun.jybStorageScan.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybStorageScan.AppUser;
import com.eyun.jybStorageScan.product.dao.ConsignScanDao;
import com.eyun.jybStorageScan.product.entity.Consign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ConsignScanDaoImpl implements ConsignScanDao {
    private static ConsignScanDaoImpl ConsignScanDao = null;

    public static ConsignScanDaoImpl getInstance() {
        if (ConsignScanDao == null) {
            synchronized (new ConsignScanDaoImpl()) {
                if (ConsignScanDao == null) {
                    ConsignScanDao = new ConsignScanDaoImpl();
                }
            }
        }
        return ConsignScanDao;
    }

    @Override
    public boolean initConsignScan(String recNumber,int proNumber, int scanType,Long vehNumber) {
        String sql="Select *  from storage_ConsignScan where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber)
                +" and ScanType="+scanType+" and VehNumber="+vehNumber;
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list.size()==0) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ScanType", scanType);
            map.put("RecNumber", SqlStringUtils.GetQuotedString(recNumber));
            map.put("VehNumber", vehNumber);
            map.put("ProNumber", proNumber);

            map.put("CreateUserID", AppUser.userId);
            map.put("CreateComID", AppUser.comId);
            map.put("CreateComBrID", AppUser.comBrId);
            map.put("CreateIp", SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
            map.put("CreateTime", SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
            sql = SqlStringUtils.GetConstructionInsert("ConsignScan", map);
            return BaseDao.getInstance().excute(sql);
        }else {
            return true;
        }
    }

    @Override
    public int getConsignScanCounts(String recNumber, int scanType, Long vehNumber) {
        String sqlSelect="Select sum(ScanNumber) ScanNumber from storage_ConsignScan where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber)
                +" and ScanType="+scanType;
        if(vehNumber!=null){

            sqlSelect+=" and VehNumber="+vehNumber;
        }
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlSelect);
        if(list!=null&& list.size()>0)
        {
            return TypeConvert.toInteger(list.get(0).get("ScanNumber"));
        }
        return 0;
    }

    @Override
    public boolean updateConsignScan(String recNumber, int number, int scanType, Long vehNumber) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("ScanNumber","ScanNumber+"+number);
        String where=" where RecNumber="+SqlStringUtils.GetQuotedString(recNumber)
                +" and scanType="+scanType+" and vehNumber="+vehNumber;
        String sqlUpdate=SqlStringUtils.GetConstructionUpdate("storage_ConsignScan",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }

}
