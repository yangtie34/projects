package com.eyun.jybfreightscan.product.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public interface ConsignProBarCodeService {

    /**
     * 根据条码获取简单信息
     * @param barCode
     * @return
     */
    List<Map<String, Object>> GetSimpleInfo(String barCode);

    /**
     * 返回条码个数
     * @param recNumber
     * @return
     */
    int GetSumCount(String recNumber);
}
