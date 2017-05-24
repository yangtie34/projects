package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.jybfreightscan.product.dao.impl.ConsignScanDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.ConsignStateDaoImpl;
import com.eyun.jybfreightscan.product.entity.ConsignScan;
import com.eyun.jybfreightscan.product.service.ConsignScanService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ConsignScanServiceImpl implements ConsignScanService {

    private static ConsignScanServiceImpl consignScanService = null;

    public static ConsignScanServiceImpl getInstance() {
        if (consignScanService == null) {
            synchronized (new ConsignScanServiceImpl()) {
                if (consignScanService == null) {
                    consignScanService = new ConsignScanServiceImpl();
                }
            }
        }
        return consignScanService;
    }

    @Override
    public int GetScanNumber(String recNumber, int scanType) {
        return ConsignScanDaoImpl.getInstance().GetScanNumber(recNumber,scanType);
    }

    @Override
    public int GetScanNumber(String barCode,String recNumber, int scanType) {
        return ConsignScanDaoImpl.getInstance().GetScanNumber(barCode,recNumber,scanType);
    }

    @Override
    public String Add(ConsignScan mo) {
        return ConsignScanDaoImpl.getInstance().Add(mo);
    }

    @Override
    public List<Map<String, Object>> GetNoUpLoad(long comBrID, long comID, long userId) {
        return ConsignScanDaoImpl.getInstance().GetNoUpLoad(comBrID,comID,userId);
    }

    @Override
    public String UpLoad(String recNumber) {
        List<ConsignScan> list=ConsignScanDaoImpl.getInstance().LoadData(recNumber);
        if(list!=null&&list.size()>0)
        {
            List<String> listSql=new ArrayList<>();
           for(int i=0;i<list.size();i++)
           {
               ConsignScan mo=list.get(i);
               String sql= ConsignScanDaoImpl.getInstance().GetSqlAdd(mo);
               listSql.add(sql);
           }
            if(ConsignScanDaoImpl.getInstance().UpLoad(listSql))
            {
                return "单据"+recNumber+"上传成功";
            }
            else
            {
                return "单据"+recNumber+"上传失败：添加失败";
            }
        }
        else
        {
            return "单据"+recNumber+"上传失败：扫描信息不存在";
        }
    }
}
