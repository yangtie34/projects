package com.eyun.jybStorageScan.product.service.impl;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybStorageScan.AppPublic;
import com.eyun.jybStorageScan.AppUser;
import com.eyun.jybStorageScan.R;
import com.eyun.jybStorageScan.product.dao.impl.ProductStockChangeDaoImpl;
import com.eyun.jybStorageScan.product.dao.impl.ProductStockDaoImpl;
import com.eyun.jybStorageScan.product.dao.impl.StorageInRecDaoImpl;
import com.eyun.jybStorageScan.product.dao.impl.StorageInRecDetailDaoImpl;
import com.eyun.jybStorageScan.product.dao.impl.StorageInRecScanDaoImpl;
import com.eyun.jybStorageScan.product.dao.impl.StorageLocationChangeDaoImpl;
import com.eyun.jybStorageScan.product.dao.impl.StorageLocationProductDaoImpl;
import com.eyun.jybStorageScan.product.dao.impl.StorageOutRecDaoImpl;
import com.eyun.jybStorageScan.product.dao.impl.StorageOutRecDetailDaoImpl;
import com.eyun.jybStorageScan.product.dao.impl.StorageOutRecScanDaoImpl;
import com.eyun.jybStorageScan.product.entity.Product;
import com.eyun.jybStorageScan.product.entity.StorageInRec;
import com.eyun.jybStorageScan.product.entity.StorageInRecScan;
import com.eyun.jybStorageScan.product.entity.StorageLocation;
import com.eyun.jybStorageScan.product.entity.StorageOutRec;
import com.eyun.jybStorageScan.product.entity.StorageOutRecScan;
import com.eyun.jybStorageScan.product.service.StorageOutRecScanService;
import com.eyun.jybStorageScan.support.ReceiptUtil;
import com.eyun.jybStorageScan.support.SoundSupport;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public class StorageOutRecScanServiceImpl implements StorageOutRecScanService {

    private static StorageOutRecScanServiceImpl storageOutRecScanService = null;

    public static StorageOutRecScanServiceImpl getInstance() {

        if (storageOutRecScanService == null) {
            synchronized (new String()) {
                if (storageOutRecScanService == null) {
                    storageOutRecScanService = new StorageOutRecScanServiceImpl();
                }
            }
        }
        return storageOutRecScanService;
    }


    @Override
    public ResultMsg getScanbyRecNumberAndProBarCode(String RecNumber, String ProBarCode, int ScanType,StorageLocation storageLocation,int number) {
        Product product = ProductServiceImpl.getInstance().LoadData(ProBarCode);
        if (product==null) {
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("商品信息不存在");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }else{
            Long proId=product.getProId();
            StorageOutRecScan mo=new StorageOutRecScan();
            mo.setScanNumber(number);
            mo.setRecNumber(RecNumber);
            mo.setProId(proId);
            mo.setScanType(ScanType);
            if(storageLocation!=null){
                mo.setStorLocaCode(storageLocation.getCode());
                mo.setStorLocaComBrID(storageLocation.getComBrId());
                mo.setStorLocaComID(storageLocation.getComId());
            }
            return Scan(mo);
        }
    }
    public ResultMsg getScanForScanOutRec(String RecNumber, String ProBarCode, int ScanType,StorageLocation storageLocation,int number) {
        Product product = ProductServiceImpl.getInstance().LoadData(ProBarCode);
        ResultMsg resultMsg = new ResultMsg();
        if (product==null) {
            resultMsg.setMsg("商品信息不存在");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }else{
            Long proId=product.getProId();
            StorageOutRecScan mo=new StorageOutRecScan();
            mo.setScanNumber(number);
            mo.setRecNumber(RecNumber);
            mo.setProId(proId);
            mo.setScanType(ScanType);
            if(storageLocation!=null){
                mo.setStorLocaCode(storageLocation.getCode());
                mo.setStorLocaComBrID(storageLocation.getComBrId());
                mo.setStorLocaComID(storageLocation.getComId());
            }
            boolean isSucc = StorageOutRecScanDaoImpl.getInstance().isExist(mo);

            if (!isSucc) {
                mo.setProNumber(StorageOutRecDetailDaoImpl.getInstance().getProNumber(mo.getRecNumber(),mo.getProId()));
                isSucc = StorageOutRecScanDaoImpl.getInstance().addScanInfo(mo);
            } else {
                isSucc = StorageOutRecScanDaoImpl.getInstance().updateScanInfo(mo);
            }
            if (isSucc) {
                resultMsg.setMsg("");
                resultMsg.setTure(true);
                resultMsg.setObject(getScanbyRecNumberAndProID(mo.getRecNumber(),mo.getProId().toString(),mo.getScanType(),storageLocation));
                SoundSupport.play(R.raw.ok);
                return resultMsg;
            } else {
                resultMsg.setMsg("数据保存失败，请重试！");

                SoundSupport.play(R.raw.fail);
                resultMsg.setTure(false);
                return resultMsg;
            }
        }
    }
    @Override
    public ResultMsg getCCScanProBarCode(String ProBarCode,StorageLocation storageLocation) {
        String recNumber= ReceiptUtil.getReceiptID();
        Product product = ProductServiceImpl.getInstance().LoadData(ProBarCode);
        if (product==null) {
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("商品信息不存在");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }else{
            StorageOutRecScan mo=new StorageOutRecScan();
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
            mo.setRecNumber(recNumber);
            if(storageLocation!=null){
                mo.setStorLocaCode(storageLocation.getCode());
                mo.setStorLocaComBrID(storageLocation.getComBrId());
                mo.setStorLocaComID(storageLocation.getComId());
            }
            StorageOutRecScanDaoImpl.getInstance().addScanInfo(mo);
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("扫描成功");
            StorageOutRec mor=new StorageOutRec();
            mor.setRecNumber(recNumber);
            mor.setRecState(AppPublic.ScanType.SAVE);
            StorageOutRecDaoImpl.getInstance().addStorageOutRec(mor);
            resultMsg.setObject(new Object[]{getScanbyRecNumberAndProID(recNumber, TypeConvert.toString(product.getProId()),AppPublic.ScanType.FJ,storageLocation),recNumber});
            resultMsg.setTure(true);
            SoundSupport.play(R.raw.ok);
            return resultMsg;
        }
    }

    @Override
    public List<Map<String, Object>> getScanbyRecNumberAndScanType(String RecNumber, int ScanType) {
        return StorageOutRecScanDaoImpl.getInstance().getScanbyRecNumberAndScanType(RecNumber,ScanType);
    }

    @Override
    public int getRecOutStorageNumber(String recNumber) {
            return StorageOutRecScanDaoImpl.getInstance().getRecOutStorageNumber(recNumber);
    }

    @Override
    public ResultMsg Scan(StorageOutRecScan mo) {
        ResultMsg resultMsg = new ResultMsg();
        /*//判断商品信息
        boolean IsExists = ProductDaoImpl.getInstance().IsExist(mo.getProId());
        if (!IsExists) {
            resultMsg.setMsg("商品信息不存在");
            resultMsg.setTure(false);
            return resultMsg;
        }*/
        //判断入库单信息
        if(!StorageOutRecDetailDaoImpl.getInstance().isThisDetailProBarCode(mo.getRecNumber(), mo.getProId())) {
            resultMsg = new ResultMsg();
            resultMsg.setMsg("出库单无此商品信息");
            SoundSupport.play(R.raw.wcxx);
            resultMsg.setTure(false);
            return resultMsg;
        }
        if(StorageOutRecScanDaoImpl.getInstance().isFullNumber(mo.getRecNumber(),TypeConvert.toString(mo.getProId()),mo.getScanType(),mo.getScanNumber())){
             resultMsg = new ResultMsg();
            resultMsg.setMsg("此商品扫描数量扫描数量大于单据商品数！");
            SoundSupport.play(R.raw.cosign_number_full);
            resultMsg.setTure(false);
            return resultMsg;
        }
/*            boolean IsExists = StorageOutRecDaoImpl.getInstance().IsExist(mo.getRecNumber());
        if (!IsExists) {
            resultMsg.setMsg("入库单信息不存在");
            resultMsg.setTure(false);
            SoundSupport.play(R.raw.wcxx);
            return resultMsg;
        }*/

        boolean isSucc = StorageOutRecScanDaoImpl.getInstance().isExist(mo);

        if (!isSucc) {
            mo.setProNumber(StorageOutRecDetailDaoImpl.getInstance().getProNumber(mo.getRecNumber(),mo.getProId()));
            isSucc = StorageOutRecScanDaoImpl.getInstance().addScanInfo(mo);
        } else {
            isSucc = StorageOutRecScanDaoImpl.getInstance().updateScanInfo(mo);
        }
        if (isSucc) {
            resultMsg.setMsg("");
            resultMsg.setTure(true);
            StorageLocation storageLocation=null;
            if (mo.getStorLocaCode() != null) {
                storageLocation=new StorageLocation();
                storageLocation.setCode(mo.getStorLocaCode());
            }
            resultMsg.setObject(getScanbyRecNumberAndProID(mo.getRecNumber(),mo.getProId().toString(),mo.getScanType(),storageLocation));
            SoundSupport.play(R.raw.ok);
            return resultMsg;
        } else {
            resultMsg.setMsg("数据保存失败，请重试！");

            SoundSupport.play(R.raw.fail);
            resultMsg.setTure(false);
            return resultMsg;
        }
    }

    @Override
    public StorageOutRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType, StorageLocation storageLocation) {
        return StorageOutRecScanDaoImpl.getInstance().getScanbyRecNumberAndProID(RecNumber, ProID, ScanType,storageLocation);
    }

    @Override
    public int getRecOutStorageLocalNumber(String RecNumber,StorageLocation storageLocation) {
        return StorageOutRecScanDaoImpl.getInstance().getRecOutStorageLocalNumber(RecNumber,storageLocation.getCode());
    }
    @Override
    public int getOutRecScanNumber(String RecNumber) {
        return StorageOutRecScanDaoImpl.getInstance().getOutRecScanNumber(RecNumber);
    }

    @Override
    public ResultMsg outRecScanOk(String recNumber) {
        ResultMsg resultMsg=new ResultMsg();
        StorageOutRecStateServiceImpl.getInstance().addStorageOutRecState(recNumber,AppPublic.ScanType.FJ);
        resultMsg.setTure(true);
        return resultMsg;
    }

    @Override
    public ResultMsg outStorageScanOk(String recNumber) {
        ResultMsg resultMsg=new ResultMsg();
        List<StorageOutRecScan> storageOutRecScanList=StorageOutRecScanDaoImpl.getInstance().getStorageOutRecScan(recNumber,AppPublic.ScanType.FJ);
        for (int i = 0; i < storageOutRecScanList.size(); i++) {
            StorageOutRecScan sirs=storageOutRecScanList.get(i);
            ProductStockDaoImpl.getInstance().updateProductStock(sirs.getProId(),0-sirs.getScanNumber());
            ProductStockChangeDaoImpl.getInstance().addProductStockChange(AppPublic.ChangeType.out,sirs.getRecNumber(),sirs.getCreateTime(),
                    sirs.getProId(),sirs.getScanNumber());
            StorageLocation storageLocation=new StorageLocation();
            storageLocation.setCode(sirs.getStorLocaCode());
            storageLocation.setComBrId(sirs.getStorLocaComBrID());
            storageLocation.setComId(sirs.getStorLocaComID());
            StorageLocationProductDaoImpl.getInstance().addStorageLocationProduct(storageLocation,sirs.getProId(),0-sirs.getScanNumber());
            StorageLocationChangeDaoImpl.getInstance().addStorageLocationChange(AppPublic.ChangeType.out,storageLocation,sirs.getProId(),sirs.getScanNumber());
        }
        StorageOutRecStateServiceImpl.getInstance().addStorageOutRecState(recNumber,AppPublic.ScanType.FJ);
        resultMsg.setTure(true);
        return resultMsg;
    }

    @Override
    public ResultMsg scanOutRecOk(String recNumber) {
        StorageOutRecDaoImpl.getInstance().updateState(recNumber, AppPublic.ScanType.FJ);
        List<StorageOutRecScan> storageOutRecScanList=StorageOutRecScanDaoImpl.getInstance().getStorageOutRecScan(recNumber,AppPublic.ScanType.FJ);

       // StorageOutRecDetailDaoImpl.getInstance().initStorageOutRecDetail();
        for (int i = 0; i < storageOutRecScanList.size(); i++) {
            StorageOutRecScan sirs=storageOutRecScanList.get(i);
            ProductStockDaoImpl.getInstance().updateProductStock(sirs.getProId(),0-sirs.getScanNumber());
            ProductStockChangeDaoImpl.getInstance().addProductStockChange(AppPublic.ChangeType.out,sirs.getRecNumber(),sirs.getCreateTime(),
                    sirs.getProId(),sirs.getScanNumber());
            StorageLocation storageLocation=new StorageLocation();
            storageLocation.setCode(sirs.getStorLocaCode());
            storageLocation.setComBrId(sirs.getStorLocaComBrID());
            storageLocation.setComId(sirs.getStorLocaComID());
            StorageLocationProductDaoImpl.getInstance().addStorageLocationProduct(storageLocation,sirs.getProId(),0-sirs.getScanNumber());
            StorageLocationChangeDaoImpl.getInstance().addStorageLocationChange(AppPublic.ChangeType.out,storageLocation,sirs.getProId(),sirs.getScanNumber());
        }
        StorageOutRecStateServiceImpl.getInstance().addStorageOutRecState(recNumber,AppPublic.ScanType.FJ);
        //出库单详情
        StorageOutRecDetailDaoImpl.getInstance().initStorageOutRecDetail(recNumber);
        ResultMsg resultMsg=new ResultMsg();
        resultMsg.setTure(true);
        return resultMsg;
    }
}
