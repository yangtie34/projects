package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.jybfreightscan.product.dao.impl.ConsignProBarCodeDaoImpl;
import com.eyun.jybfreightscan.product.service.ConsignProBarCodeService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ConsignProBarCodeServiceImpl implements ConsignProBarCodeService {


    private static ConsignProBarCodeServiceImpl consignProBarCodeService = null;

    public static ConsignProBarCodeServiceImpl getInstance() {
        if (consignProBarCodeService == null) {
            synchronized (new ConsignProBarCodeServiceImpl()) {
                if (consignProBarCodeService == null) {
                    consignProBarCodeService = new ConsignProBarCodeServiceImpl();
                }
            }
        }
        return consignProBarCodeService;
    }

    @Override
    public List<Map<String, Object>> GetSimpleInfo(String barCode) {
        return ConsignProBarCodeDaoImpl.getInstance().GetSimpleInfo(barCode);
    }

    @Override
    public int GetSumCount(String recNumber) {
        return ConsignProBarCodeDaoImpl.getInstance().GetSumCount(recNumber);
    }
}
