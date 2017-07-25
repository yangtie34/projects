package com.eyun.jybfreightscan.product.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public interface ConsignProBarCodeDao {

    /**
     * 根据条码获取单据号
     * @param barCode
     * @return
     */
    String GetRecNumber(String barCode);

    /**
     * 根据条码获取货物种类（批量货或者一货一码判断）
     * @param barCode
     * @return
     */
    int GetCount(String barCode);

    /**
     * 根据条码获取托运单简单信息
     * @param barCode
     * @return
     */
    List<Map<String,Object>> GetSimpleInfo(String barCode);

    /**
     * 返回条码个数
     * @param recNumber
     * @return
     */
    int GetSumCount(String recNumber);

}
