package com.eyun.jybfreightscan.product.dao.impl;

import android.text.TextUtils;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.jdbc.jdbcUtil.BaseDaoServer;
import com.eyun.framework.jdbc.jdbcUtil.BaseDaoUtil;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.L;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.product.entity.Consign;
import com.eyun.jybfreightscan.product.entity.ConsignScan;
import com.eyun.jybfreightscan.product.dao.ConsignScanDao;
import com.eyun.jybfreightscan.product.entity.ConsignState;
import com.eyun.jybfreightscan.product.entity.VehicleDispathDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ConsignScanDaoImpl implements ConsignScanDao {

    private static ConsignScanDaoImpl consignScanDao = null;

    public static ConsignScanDaoImpl getInstance() {
        if (consignScanDao == null) {
            synchronized (new ConsignScanDaoImpl()) {
                if (consignScanDao == null) {
                    consignScanDao = new ConsignScanDaoImpl();
                }
            }
        }
        return consignScanDao;
    }

    @Override
    public List<ConsignScan>  LoadData(String recNumber)
    {
        String sql="Select * from ConsignScan where IsUpLoad=0 and RecNumber="+SqlStringUtils.GetQuotedString(recNumber);
        List<ConsignScan> list=BaseDao.getInstance().query(sql,ConsignScan.class);
        return list;
    }

    @Override
    public boolean UpLoad(List<String> list)
    {
        return BaseDaoServer.getInstance().excuteTransaction(list);
    }

    @Override
    public boolean IsExists(String barCode, int scanType) {

        String sql="Select 1 from ConsignScan where ScanProBarCode="
                + SqlStringUtils.GetQuotedString(barCode)+" and ScanType="+scanType;
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list!=null&&list.size()>0)
            return true;
        return false;
    }

    @Override
    public  List<Map<String,Object>> GetNoUpLoad(long comBrID, long comID, long userId)
    {
        String sql="Select distinct RecNumber from ConsignScan where CreateUserId="
                +userId+" and CreateComBrId="+comBrID+" and CreateComId="+comID;
        return BaseDao.getInstance().queryForList(sql);
    }

    @Override
    public String GetSqlAdd(ConsignScan mo) {

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("CreateUserId",mo.getCreateUserId());
        map.put("CreateComBrId",mo.getCreateComBrId());
        map.put("CreateComId",mo.getCreateComId());
        map.put("CreateIp",SqlStringUtils.GetQuotedString(mo.getCreateIp()));
        map.put("ProNumber",mo.getProNumber());
        map.put("RecNumber",SqlStringUtils.GetQuotedString(mo.getRecNumber()));
        map.put("ScanErrorType",mo.getScanErrorType());
        map.put("ScanNumber",mo.getScanNumber());
        map.put("ScanProBarCode",SqlStringUtils.GetQuotedString(mo.getScanProBarCode()));
        map.put("ScanType",mo.getScanType());
        map.put("VehDispNumber",SqlStringUtils.GetQuotedString(mo.getVehDispNumber()));
        map.put("VehNumber",mo.getVehNumber());
        if(mo.getIsUpload()) {
            map.put("IsUpLoad",1);
        }
        else
        {
            map.put("IsUpLoad",0);
        }
        return SqlStringUtils.GetConstructionInsert("ConsignScan",map);

    }

    @Override
    public int GetScanNumber(String recNumber, int scanType) {

        String sql="Select sum(ScanNumber) ScanNumber from ConsignScan where RecNumber="
                + SqlStringUtils.GetQuotedString(recNumber)+" and ScanType="+scanType;
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list!=null&&list.size()==1)
            return TypeConvert.toInteger(list.get(0).get("ScanNumber"));
        return 0;

    }

    @Override
    public int GetScanNumber(String barCode,String recNumber, int scanType) {

        String sql="Select sum(ScanNumber) ScanNumber from ConsignScan where RecNumber="
                + SqlStringUtils.GetQuotedString(recNumber)+" and ScanProBarCode="+SqlStringUtils.GetQuotedString(barCode)
                +" and ScanType="+scanType;
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list!=null&&list.size()==1)
            return TypeConvert.toInteger(list.get(0).get("ScanNumber"));
        return 0;

    }

    @Override
    public String Add(ConsignScan mo) {

        if(TextUtils.isEmpty(mo.getRecNumber()))
        {
            return "单号不能为空";
        }
        if(mo.getVehNumber()==0)
        {
            return "车辆不能为空";
        }
        if(TextUtils.isEmpty(mo.getVehDispNumber()))
        {
            return "调度不能为空";
        }
        //件数判断
        int scanNumber=this.GetScanNumber(mo.getRecNumber(),mo.getScanType());
        int proNumber=mo.getProNumber();
        if(scanNumber>=proNumber)
        {
            return "扫描重复（已扫描完成）";
        }
        if(scanNumber>(proNumber+mo.getScanNumber()))
        {
            return "扫描重复（扫描件数不能超过货物总件数）";
        }

        List<String> list=new ArrayList<String>();
        String sqlConsignScan=GetSqlAdd(mo);

        list.add(sqlConsignScan);

        if(mo.getScanType()==1||mo.getScanType()==3) {
            if (!VehicleDispathDetailDaoImpl.getInstance().IsExists(mo.getRecNumber(), mo.getVehDispNumber())) {
                VehicleDispathDetail moVehDetail = new VehicleDispathDetail();
                moVehDetail.setCreateUserId(mo.getCreateUserId());
                moVehDetail.setCreateIp(mo.getCreateIp());
                moVehDetail.setCreateComBrId(mo.getCreateComBrId());
                moVehDetail.setCreateComId(mo.getCreateComId());
                moVehDetail.setConsignRecNumber(mo.getRecNumber());
                moVehDetail.setVehDispNumber(mo.getVehDispNumber());

                String sqlVehicleDispath=VehicleDispathDetailDaoImpl.getInstance().GetSqlAdd(moVehDetail);
                L.d(sqlVehicleDispath);
                list.add(sqlVehicleDispath);
            }
        }

        if(BaseDao.getInstance().excuteTransaction(list))
            return  "";
        return "保存失败";

    }
}
