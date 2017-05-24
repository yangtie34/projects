package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.ConsignState;

/**
 * Created by Administrator on 2017/4/6.
 */

public interface ConsignStateDao {

    /**
     * 状态添加
     * @param mo
     */
    public String GetSqlAdd(ConsignState mo);


    /**
     * 判断单据状态是否存在
     * @param recNumber
     * @param state
     * @param comBrID
     * @return
     */
    public boolean IsExists(String recNumber,int state,long comBrID);
}
