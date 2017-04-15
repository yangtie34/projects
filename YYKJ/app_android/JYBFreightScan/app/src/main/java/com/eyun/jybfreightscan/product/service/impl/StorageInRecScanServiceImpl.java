package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.AppPublic;
import com.eyun.jybfreightscan.AppUser;
import com.eyun.jybfreightscan.R;
import com.eyun.jybfreightscan.product.dao.impl.ProductStockChangeDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.ProductStockDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.StorageInRecDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.StorageInRecDetailDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.StorageInRecScanDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.StorageLocationChangeDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.StorageLocationProductDaoImpl;
import com.eyun.jybfreightscan.product.entity.Product;
import com.eyun.jybfreightscan.product.entity.StorageInRec;
import com.eyun.jybfreightscan.product.entity.StorageInRecScan;
import com.eyun.jybfreightscan.product.entity.StorageLocation;
import com.eyun.jybfreightscan.product.service.StorageInRecScanService;
import com.eyun.jybfreightscan.support.ReceiptUtil;
import com.eyun.jybfreightscan.support.SoundSupport;

import java.util.List;
import java.util.Map;

import static com.eyun.jybfreightscan.R.raw.cosign_number_full;

/**
 * Created by Administrator on 2017/3/6.
 */

public class StorageInRecScanServiceImpl implements StorageInRecScanService {
    private static StorageInRecScanServiceImpl storageInRecScanService = null;

    public static StorageInRecScanServiceImpl getInstance() {
        if (storageInRecScanService == null) {
            synchronized (new StorageInRecScanServiceImpl()) {
                if (storageInRecScanService == null) {
                    storageInRecScanService = new StorageInRecScanServiceImpl();
                }
            }
        }
        return storageInRecScanService;
    }

    @Override
    public boolean initScansByStorageInRecDetails(String RecNumber, int ScanType) {
        return StorageInRecScanDaoImpl.getInstance().initScansByStorageInRecDetails(RecNumber, ScanType);
    }

    @Override
    public StorageInRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType, StorageLocation storageLocation) {
        return StorageInRecScanDaoImpl.getInstance().getScanbyRecNumberAndProID(RecNumber, ProID, ScanType,storageLocation);
    }
    /**
     * 根据入库单编号和商品id和扫描类型累加扫描信息
     * @return
     */
    @Override
    public ResultMsg Scan(StorageInRecScan mo) {
        ResultMsg resultMsg = new ResultMsg();
        /*//判断商品信息
        boolean IsExists = ProductDaoImpl.getInstance().IsExist(mo.getProId());
        if (!IsExists) {
            resultMsg.setMsg("商品信息不存在");
            resultMsg.setTure(false);
            return resultMsg;
        }*/
        //判断入库单信息
        boolean IsExists = StorageInRecDaoImpl.getInstance().IsExist(mo.getRecNumber());
        if (!IsExists) {
            resultMsg.setMsg("入库单信息不存在");
            resultMsg.setTure(false);
            return resultMsg;
        }

        int count = StorageInRecScanDaoImpl.getInstance().getCount(mo.getRecNumber(), mo.getProId(), mo.getScanType());
        boolean isSucc = false;

        if (count == -1) {
            isSucc = StorageInRecScanDaoImpl.getInstance().addScanInfo(mo);
        } else {
            isSucc = StorageInRecScanDaoImpl.getInstance().updateScanInfo(mo);
        }
        if (isSucc) {
            resultMsg.setMsg("");
            resultMsg.setTure(true);
            resultMsg.setObject(getScanbyRecNumberAndProID(mo.getRecNumber(),mo.getProId().toString(),mo.getScanType(),null));
            return resultMsg;
        } else {
            resultMsg.setMsg("数据保存失败，请重试！");
            resultMsg.setTure(false);
            return resultMsg;
        }
    }

    @Override
    public ResultMsg getScanbyRecNumberAndProBarCode(String RecNumber, String ProBarCode, int ScanType, int number) {
        Product product = ProductServiceImpl.getInstance().LoadData(ProBarCode);
        if (product==null) {
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("商品信息不存在");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }else if(!StorageInRecDetailServiceImpl.getInstance().hasThisProduct(RecNumber, TypeConvert.toString(product.getProId()))){
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("入库单无此商品信息");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }else if(StorageInRecScanDaoImpl.getInstance().isFullNumber(RecNumber, TypeConvert.toString(product.getProId()),ScanType,number)){
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("此商品扫描数量扫描数量大于单据商品数！");
            SoundSupport.play(R.raw.cosign_number_full);
            resultMsg.setTure(false);
            return resultMsg;
        }else{
            StorageInRecScanDaoImpl.getInstance().addScanNumber(RecNumber, TypeConvert.toString(product.getProId()),ScanType,null,number);
                ResultMsg resultMsg = new ResultMsg();
                resultMsg.setMsg("扫描成功");
            SoundSupport.play(R.raw.ok);
                resultMsg.setTure(true);
                resultMsg.setObject(getScanbyRecNumberAndProID(RecNumber, TypeConvert.toString(product.getProId()),ScanType,null));
                return resultMsg;
        }
    }

    @Override
    public ResultMsg getScanbyRecInStorage(String RecNumber, String ProBarCode, StorageLocation storageLocation, int number) {
        Product product = ProductServiceImpl.getInstance().LoadData(ProBarCode);
        if (product==null) {
            ResultMsg resultMsg = new ResultMsg();
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setMsg("商品信息不存在");
            resultMsg.setTure(false);
            return resultMsg;
        }else if(!StorageInRecDetailServiceImpl.getInstance().hasThisProduct(RecNumber, TypeConvert.toString(product.getProId()))){
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("入库单无此商品信息");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }else if(StorageInRecScanDaoImpl.getInstance().isFullNumber(RecNumber, TypeConvert.toString(product.getProId()), AppPublic.ScanType.CRC,number)){
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("此商品扫描数量扫描数量大于单据商品数！");
            SoundSupport.play(R.raw.cosign_number_full);
            resultMsg.setTure(false);
            return resultMsg;
        }else{
            StorageInRecScan storageInRecScan=new StorageInRecScan();
            storageInRecScan.setScanType(AppPublic.ScanType.CRC);
            storageInRecScan.setRecNumber(RecNumber);
            storageInRecScan.setProId(product.getProId());
            storageInRecScan.setStorLocaCode(storageLocation.getCode());
            if(StorageInRecScanDaoImpl.getInstance().isExist(storageInRecScan)){
                    StorageInRecScanDaoImpl.getInstance().addScanNumber(RecNumber, TypeConvert.toString(product.getProId()),AppPublic.ScanType.CRC,storageLocation,number);
           }else{
                StorageInRecScanDaoImpl.getInstance().addScanInfo(RecNumber, TypeConvert.toString(product.getProId()),AppPublic.ScanType.CRC,storageLocation,number);
            }
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("扫描成功");
            SoundSupport.play(R.raw.ok);
            resultMsg.setTure(true);
            resultMsg.setObject(getScanbyRecNumberAndProID(RecNumber, TypeConvert.toString(product.getProId()),AppPublic.ScanType.CRC,storageLocation));
            return resultMsg;
        }
    }

    @Override
    public ResultMsg getScanbyRecNumberAndProBarCodeForScanInRec(String RecNumber, String ProBarCode, int ScanType, long ComSuppID, int number) {
        Product product = ProductServiceImpl.getInstance().LoadData(ProBarCode);
        if (product==null) {
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("商品信息不存在");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }
        if(ComSuppID!=product.getVestingComSuppId()){
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("非同一供应商商品！");
            SoundSupport.play(R.raw.gysbt);
            resultMsg.setTure(false);
            return resultMsg;
        }
        StorageInRecScan storageInRecScan=getScanbyRecNumberAndProID(RecNumber, TypeConvert.toString(product.getProId()),ScanType,null);
        if(storageInRecScan==null){
            StorageInRecScan mo=new StorageInRecScan();
            mo.setScanNumber(number);
            mo.setScanType(ScanType);
            mo.setProNumber(0);
            mo.setCreateUserId(AppUser.userId);
            mo.setCreateComBrId(AppUser.comBrId);
            mo.setCreateComId(AppUser.comId);
            mo.setCreateIp(NetWorkUtil.getHostIP());
            mo.setProId(product.getProId());
            mo.setProName(product.getProName());
            mo.setCreateTime(DateUtils.getCurrentTime());
            mo.setRecNumber(RecNumber);
            StorageInRecScanDaoImpl.getInstance().addScanInfo(mo);
        }else{
            StorageInRecScanDaoImpl.getInstance().addScanNumber(RecNumber, TypeConvert.toString(product.getProId()),ScanType,null,number);
        }
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setMsg("扫描成功");
        SoundSupport.play(R.raw.ok);
        resultMsg.setObject(getScanbyRecNumberAndProID(RecNumber, TypeConvert.toString(product.getProId()),ScanType,null));
        resultMsg.setTure(true);
        return resultMsg;
    }
    @Override
    public ResultMsg getFjScanProBarCode(String ProBarCode) {
        String recNumber= ReceiptUtil.getReceiptID();
        Product product = ProductServiceImpl.getInstance().LoadData(ProBarCode);
        if (product==null) {
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("商品信息不存在");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }else{
            StorageInRecScan mo=new StorageInRecScan();
            mo.setScanNumber(1);
            mo.setScanType(AppPublic.ScanType.FJ);
            mo.setProNumber(1);
            mo.setCreateUserId(AppUser.userId);
            mo.setCreateComBrId(AppUser.comBrId);
            mo.setCreateComId(AppUser.comId);
            mo.setCreateIp(NetWorkUtil.getHostIP());
            mo.setProId(product.getProId());
            mo.setProName(product.getProName());
            mo.setCreateTime(DateUtils.getCurrentTime());
            mo.setComSuppID(product.getVestingComSuppId());
            mo.setRecNumber(recNumber);
            StorageInRecScanDaoImpl.getInstance().addScanInfo(mo);
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("扫描成功");
            resultMsg.setObject(new Object[]{getScanbyRecNumberAndProID(recNumber, TypeConvert.toString(product.getProId()),AppPublic.ScanType.FJ,null),recNumber});
            resultMsg.setTure(true);
            SoundSupport.play(R.raw.ok);
            return resultMsg;
        }
    }

    @Override
    public List<Map<String, Object>> getScanbyRecNumberAndScanType(String RecNumber, int ScanType) {
        return StorageInRecScanDaoImpl.getInstance().getScanbyRecNumberAndScanType(RecNumber,ScanType);
    }

    @Override
    public int getInRecScanNumber(String RecNumber) {
        return StorageInRecScanDaoImpl.getInstance().getInRecScanNumber(RecNumber);
    }

    @Override
    public int getRecInStorageNumber(String RecNumber) {
        return StorageInRecScanDaoImpl.getInstance().getRecInStorageNumber(RecNumber);
    }

    @Override
    public int getRecInStorageLocalNumber(String RecNumber,StorageLocation storageLocation) {
        return StorageInRecScanDaoImpl.getInstance().getRecInStorageLocalNumber(RecNumber,storageLocation.getCode());
    }

    @Override
    public ResultMsg inRecScanOk(String recNumber) {
        ResultMsg resultMsg=new ResultMsg();
        StorageInRecStateServiceImpl.getInstance().addStorageInRecState(recNumber,AppPublic.ScanType.FJ);
        resultMsg.setTure(true);
        return resultMsg;
    }

    @Override
    public ResultMsg inStorageScanOk(String recNumber) {
        ResultMsg resultMsg=new ResultMsg();
        List<StorageInRecScan> storageInRecScanList=StorageInRecScanDaoImpl.getInstance().getStorageInRecScan(recNumber,AppPublic.ScanType.CRC);
        for (int i = 0; i < storageInRecScanList.size(); i++) {
            StorageInRecScan sirs=storageInRecScanList.get(i);
            ProductStockDaoImpl.getInstance().updateProductStock(sirs.getProId(),sirs.getScanNumber());
            ProductStockChangeDaoImpl.getInstance().addProductStockChange(AppPublic.ChangeType.in,sirs.getRecNumber(),sirs.getCreateTime(),
                    sirs.getProId(),sirs.getScanNumber());
            StorageLocation storageLocation=new StorageLocation();
            storageLocation.setCode(sirs.getStorLocaCode());
            storageLocation.setComBrId(sirs.getStorLocaComBrID());
            storageLocation.setComId(sirs.getStorLocaComID());
            StorageLocationProductDaoImpl.getInstance().addStorageLocationProduct(storageLocation,sirs.getProId(),sirs.getScanNumber());
            StorageLocationChangeDaoImpl.getInstance().addStorageLocationChange(AppPublic.ChangeType.in,storageLocation,sirs.getProId(),sirs.getScanNumber());
        }
        StorageInRecStateServiceImpl.getInstance().addStorageInRecState(recNumber,AppPublic.ScanType.CRC);
        resultMsg.setTure(true);
        return resultMsg;
    }

    @Override
    public ResultMsg scanInRecOk(String recNumber) {
        StorageInRec mo=new StorageInRec();
        mo.setRecNumber(recNumber);
        StorageInRecDaoImpl.getInstance().addStorageInRec(mo);
        StorageInRecDetailDaoImpl.getInstance().initStorageInRecDetail(recNumber);
        StorageInRecStateServiceImpl.getInstance().addStorageInRecState(recNumber,AppPublic.ScanType.FJ);
        ResultMsg resultMsg=new ResultMsg();
        resultMsg.setTure(true);
        return resultMsg;
    }
}
