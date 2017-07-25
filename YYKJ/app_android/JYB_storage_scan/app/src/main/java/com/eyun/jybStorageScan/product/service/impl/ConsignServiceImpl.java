package com.eyun.jybStorageScan.product.service.impl;

import com.eyun.jybStorageScan.product.dao.impl.ConsignDaoImpl;
import com.eyun.jybStorageScan.product.entity.Consign;
import com.eyun.jybStorageScan.product.service.ConsignService;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ConsignServiceImpl implements ConsignService {
    private static ConsignServiceImpl ConsignService = null;

    public static ConsignServiceImpl getInstance() {
        if (ConsignService == null) {
            synchronized (new ConsignServiceImpl()) {
                if (ConsignService == null) {
                    ConsignService = new ConsignServiceImpl();
                }
            }
        }
        return ConsignService;
    }
    @Override
    public Consign getConsign(String ConsignCode) {
        return ConsignDaoImpl.getInstance().getConsign(ConsignCode);
    }

}
