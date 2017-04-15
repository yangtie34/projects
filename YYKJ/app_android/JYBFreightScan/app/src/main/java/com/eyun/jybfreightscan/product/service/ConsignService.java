package com.eyun.jybfreightscan.product.service;

import com.eyun.jybfreightscan.product.entity.Consign;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface ConsignService {

    /**
     * 根据条码获取托运单信息
     * @return
     */
    Consign getConsign(String consignCode);

}
