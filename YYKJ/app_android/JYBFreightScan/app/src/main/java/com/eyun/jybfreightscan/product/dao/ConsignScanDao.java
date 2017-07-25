package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.ConsignScan;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public interface ConsignScanDao {


    /**
     * 加载扫描数据
     * @param recNumber
     * @return
     */
    List<ConsignScan> LoadData(String recNumber);

    /**
     * 判断扫描表数据是否存在
     * @param barCode
     * @param scanType
     * @return
     */
    public boolean IsExists(String barCode,int scanType);

    /**
     * 返回扫描数量-所有
     * @param recNumber
     * @param scanType
     * @return
     */
    public int GetScanNumber(String recNumber,int scanType);

    /**
     * 返回扫描数量-一货一码
     * @param barCode
     * @param recNumber
     * @param scanType
     * @return
     */
    public int GetScanNumber(String barCode,String recNumber,int scanType);
    /**
     * 获取添加语句
     * @param mo
     * @return
     */
    public String GetSqlAdd(ConsignScan mo);

    /**
     * 添加扫描记录
     * @param mo
     * @return
     */
    public String Add(ConsignScan mo);

    /**
     * 上传
     * @param list
     * @return
     */
    public boolean UpLoad(List<String> list);

    /**
     * 获取未上传记录
     * @param comBrID
     * @param comID
     * @param userId
     * @return
     */
    List<Map<String,Object>> GetNoUpLoad(long comBrID, long comID, long userId);
}
