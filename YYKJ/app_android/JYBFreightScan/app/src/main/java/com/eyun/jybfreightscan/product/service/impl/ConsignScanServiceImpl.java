package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.jybfreightscan.R;
import com.eyun.jybfreightscan.product.dao.impl.ConsignDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.ConsignProBarCodeDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.ConsignScanDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.ConsignStateDaoImpl;
import com.eyun.jybfreightscan.product.entity.Consign;
import com.eyun.jybfreightscan.product.entity.Vehicle;
import com.eyun.jybfreightscan.product.service.ConsignScanService;
import com.eyun.jybfreightscan.support.SoundSupport;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ConsignScanServiceImpl implements ConsignScanService {
    private static ConsignScanServiceImpl ConsignScanService = null;

    public static ConsignScanServiceImpl getInstance() {
        if (ConsignScanService == null) {
            synchronized (new ConsignScanServiceImpl()) {
                if (ConsignScanService == null) {
                    ConsignScanService = new ConsignScanServiceImpl();
                }
            }
        }
        return ConsignScanService;
    }

    @Override
    public int getConsignScanCounts(Consign consign, int scanType) {
        return ConsignScanDaoImpl.getInstance().getConsignScanCounts(consign.getRecNumber(), scanType,null);
    }
    @Override
    public int getConsignScanCounts(Consign consign, int scanType, Vehicle vehicle) {
        ConsignScanDaoImpl.getInstance().initConsignScan(consign.getRecNumber(),consign.getProNumber(),scanType,vehicle.getVehNumber());
        return ConsignScanDaoImpl.getInstance().getConsignScanCounts(consign.getRecNumber(), scanType,vehicle.getVehNumber());
    }

    @Override
    public ResultMsg getScanProduct(Consign consign, int scanType, Vehicle vehicle, String code, int number) {
        ResultMsg resultMsg = new ResultMsg();
       /* Product product = ProductServiceImpl.getInstance().LoadData(code);
        if (product==null) {
            resultMsg.setMsg("商品信息不存在");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }*/
        if(!ConsignProBarCodeDaoImpl.getInstance().hasThisProduct(consign.getRecNumber(),code)){
            resultMsg.setMsg("托运单无此商品");
            resultMsg.setTure(false);
            SoundSupport.play(R.raw.wcxx);
            return resultMsg;
        }
        int scanNumber=ConsignScanDaoImpl.getInstance().getConsignScanCounts(consign.getRecNumber(), scanType,null);
        if(scanNumber+number>consign.getProNumber()){
            resultMsg.setMsg("扫描数量大于托运单商品数量！");
            resultMsg.setTure(false);
            SoundSupport.play(R.raw.cosign_number_full);
            return resultMsg;
        }
        boolean bool=ConsignScanDaoImpl.getInstance().updateConsignScan(consign.getRecNumber(),number,scanType,vehicle.getVehNumber());
        if(bool){
            resultMsg.setMsg("扫描成功");
            SoundSupport.play(R.raw.ok);
            resultMsg.setTure(true);
            //resultMsg.setObject(product);
            return resultMsg;
        }else{
            resultMsg.setMsg("扫描失败");
            SoundSupport.play(R.raw.fail);
            resultMsg.setTure(false);
            return resultMsg;
        }
    }

    @Override
    public ResultMsg consignScanOk(String recNumber, int scanType) {
        ConsignStateDaoImpl.getInstance().add(recNumber,scanType);
        int RecState=0;
        int RecForwardedState=0;
        switch (scanType){
            case 1:RecState=3;
                break;
            case 2:RecState=3;RecForwardedState=2;
                break;
            case 3:RecState=3;RecForwardedState=3;
                break;
            case 4:RecState=4;
                break;
        }
        boolean bool=ConsignDaoImpl.getInstance().updateConsign(recNumber,RecState,RecForwardedState);
        ResultMsg resultMsg = new ResultMsg();
        if(bool){
            resultMsg.setMsg("提交成功");
            SoundSupport.play(R.raw.ok);
            resultMsg.setTure(true);
            return resultMsg;
        }else{
            resultMsg.setMsg("提交失败");
            SoundSupport.play(R.raw.fail);
            resultMsg.setTure(false);
            return resultMsg;
        }
    }
}
