package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.jybfreightscan.product.dao.impl.VehicleDispathDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.VehicleDispathDetailDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.VehicleDispathStateDaoImpl;
import com.eyun.jybfreightscan.product.entity.Consign;
import com.eyun.jybfreightscan.product.entity.Vehicle;
import com.eyun.jybfreightscan.product.entity.VehicleDispathDetail;
import com.eyun.jybfreightscan.product.service.VehicleDispathService;

/**
 * Created by Administrator on 2017/3/20.
 */

public class VehicleDispathServiceImpl implements VehicleDispathService {
    private static VehicleDispathServiceImpl VehicleDispathService = null;

    public static VehicleDispathServiceImpl getInstance() {
        if (VehicleDispathService == null) {
            synchronized (new VehicleDispathServiceImpl()) {
                if (VehicleDispathService == null) {
                    VehicleDispathService = new VehicleDispathServiceImpl();
                }
            }
        }
        return VehicleDispathService;
    }

    @Override
    public ResultMsg okVehicleDisp(Vehicle vehicle, Consign consign, int dispathType) {
        ResultMsg resultMsg =new ResultMsg();
        VehicleDispathDetail vehicleDispathDetail= VehicleDispathDetailDaoImpl.getInstance().getVehicleDispathDetailByConsignRecNumber(consign.getRecNumber());
        boolean bool=VehicleDispathDaoImpl.getInstance().hasThisDispath(vehicleDispathDetail.getVehDispNumber(),vehicle.getVehNumber());
        if(!bool){
            resultMsg.setMsg("托运单无此车辆调度信息！");
        }
        VehicleDispathStateDaoImpl.getInstance().add(vehicleDispathDetail.getVehDispNumber(),dispathType);
        //1-已装车 2-已发车， 2-中转已到车，3-中转已卸车，4-中转已装车，5-中转已发车，3-已完成
        int RecState=0;
        int RecForwardedState =0;
        switch (dispathType){
            case 1:RecState=1;
                break;
            case 2:RecState=2;
                break;
            case 3:RecState=2;RecForwardedState=2;
                break;
            case 4:RecState=2;RecForwardedState=3;
                break;
            case 5:RecState=2;RecForwardedState=4;
                break;
            case 6:RecState=2;RecForwardedState=5;
                break;
            case 7:RecState=3;RecForwardedState=6;
                break;

        }
        VehicleDispathDaoImpl.getInstance().updateVehicleDispath(vehicleDispathDetail.getVehDispNumber(),RecState,RecForwardedState);
        resultMsg.setTure(true);
        return null;
    }
}
