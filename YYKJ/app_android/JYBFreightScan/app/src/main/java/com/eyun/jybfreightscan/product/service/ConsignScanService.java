package com.eyun.jybfreightscan.product.service;

import com.eyun.jybfreightscan.product.entity.ConsignScan;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public interface ConsignScanService {

    /**
     * 获取扫描件数
     * @param recNumber
     * @param scanType
     * @return
     */
    int GetScanNumber(String recNumber,int scanType);

    /**
     * 获取扫描件数
     * @param recNumber
     * @param scanType
     * @param barCode
     * @return
     */
    int GetScanNumber(String barCode, String recNumber,int scanType);

    /**
     * 添加扫描记录
     * @param mo
     * @return
     */
    String Add(ConsignScan mo);


    /**
     * 获取未上传记录
     * @param comBrID
     * @param comID
     * @param userId
     * @return
     */
    List<Map<String,Object>> GetNoUpLoad(long comBrID, long comID, long userId);


    /**
     * 上传扫描记录
     * @param recNumber
     * @return
     */
    String UpLoad(String recNumber);

}


