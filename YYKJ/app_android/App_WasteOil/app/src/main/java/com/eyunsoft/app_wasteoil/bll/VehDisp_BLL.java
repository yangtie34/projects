package com.eyunsoft.app_wasteoil.bll;

import com.eyunsoft.app_wasteoil.Model.TransferRecState_Model;
import com.eyunsoft.app_wasteoil.Model.TransferRec_Model;
import com.eyunsoft.app_wasteoil.Model.VehicleDispathForwardedState_Model;
import com.eyunsoft.app_wasteoil.Model.VehicleDispathState_Model;
import com.eyunsoft.app_wasteoil.Model.VehicleDispath_Model;
import com.eyunsoft.app_wasteoil.Publics.Convert;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class VehDisp_BLL {
    // 命名空间
    private static final String NAMESPACE   = "http://tempuri.org/";
    // SOAPACTION
    private static String   SOAP_ACTION = "http://tempuri.org";

    /**
     * 调度-加载数据
     * @param recNumber
     * @return
     */
    public static VehicleDispath_Model VehDisp_LoadData(String recNumber)
    {
        String methodName = "VehDisp_LoadData";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("recNo",recNumber);

        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"TransferRec_Model",TransferRec_Model.class);
        envelope.setOutputSoapObject(soapObject);

        VehicleDispath_Model mo=new VehicleDispath_Model();
        String resatult = null;
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            if(object!=null){
                System.out.println(object.toString());
                SoapObject response=(SoapObject) envelope.bodyIn;
                SoapObject regSopa=(SoapObject)response.getProperty(0);

                int cout = regSopa.getPropertyCount();
                if (cout > 0) {
                    for (int i = 0; i < regSopa.getPropertyCount(); i++) {
                        String value = regSopa.getProperty(i).toString();
                        if (value.contentEquals("anyType{}")) {
                            value = "";
                        }

                        mo.setProperty(i, value);
                    }
                    mo.setExist(true);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mo;
    }

    /**
     * 中转确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispForwardedState_ZhongZhuan(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispForwardedState_ZhongZhuan";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moForwardedState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathForwardedState_Model",VehicleDispathForwardedState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 中转卸车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispForwardedState_XieChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispForwardedState_XieChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moForwardedState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathForwardedState_Model",VehicleDispathForwardedState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 中转到车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispDispForwardedState_DaoChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispDispForwardedState_DaoChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moForwardedState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathForwardedState_Model",VehicleDispathForwardedState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 中转发车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispForwardedState_FaChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispForwardedState_FaChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moForwardedState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathForwardedState_Model",VehicleDispathForwardedState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 中转装车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispForwardedState_ZhuangChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispForwardedState_ZhuangChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moForwardedState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathForwardedState_Model",VehicleDispathForwardedState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 中转封车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispForwardedState_FengChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispForwardedState_FengChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moForwardedState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathForwardedState_Model",VehicleDispathForwardedState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }



    /**
     * 启程确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispState_QiCheng(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispState_QiCheng";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathState_Model",VehicleDispathState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 受理确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispState_ShouLi(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispState_ShouLi";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathState_Model",VehicleDispathState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 到车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispState_DaoChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispState_DaoChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathState_Model",VehicleDispathState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 装车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispState_ZhuangChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispState_ZhuangChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathState_Model",VehicleDispathState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 封车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispState_FengChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispState_FengChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathState_Model",VehicleDispathState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 发车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispState_FaChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispState_FaChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathState_Model",VehicleDispathState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 卸车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispState_XieChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispState_XieChe";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathState_Model",VehicleDispathState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 完成确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static String ConfirmProcess_VehDispState_Ok(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispState_Ok";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moState",model);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathState_Model",VehicleDispathState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }


    /**
     *  订单查询
     * @param Condition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static List<HashMap<String,Object>> VehDisp_Select(String Condition, int pageIndex, int pageSize)
    {


        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();

        String methodName = "VehDisp_Select";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

        soapObject.addProperty("pageIndex",pageIndex);
        soapObject.addProperty("pageSize",pageSize);
        soapObject.addProperty("Condition","["+Condition+"]");
        soapObject.addProperty("orderField","");
        soapObject.addProperty("orderType","");

        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");


        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);
        envelope.implicitTypes = true;

        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("查询条件:" + Condition);
            System.out.println("获取到数据:" + object.toString());
            String jsonStr=object.toString();

            if(!jsonStr.equals("anyType{}"))
            {

                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();

                    if (jsonObject.has("VehDispNumber")) {
                        hashMap.put("VehDispNumber", jsonObject.get("VehDispNumber"));
                    } else {
                        continue;

                    }
                    if (jsonObject.has("VehDispTime")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String t=format.format(Convert.ToDate(jsonObject.get("VehDispTime").toString()));
                        hashMap.put("VehDispTime",t);
                    } else {
                        hashMap.put("VehDispTime", "");
                    }

                    if (jsonObject.has("VehDispState")) {

                        hashMap.put("VehDispState", jsonObject.get("VehDispStateName"));
                    } else {
                        continue;
                    }

                    if (jsonObject.has("VehDispForwardedState")) {
                        hashMap.put("VehDispForwardedState", jsonObject.get("VehDispForwardedStateName"));
                    } else {
                        hashMap.put("VehDispForwardedState", "--");
                    }


                    if (jsonObject.has("CarriageMode")) {
                        hashMap.put("CarriageMode",jsonObject.get("CarriageModeName").toString());
                    } else {
                        hashMap.put("CarriageMode", "--");
                    }

                    if (jsonObject.has("CarriageMoney")) {
                        hashMap.put("CarriageMoney",jsonObject.get("CarriageMoney").toString());
                    } else {
                        hashMap.put("CarriageMoney", "--");
                    }

                    if (jsonObject.has("Remark")) {
                        hashMap.put("Remark", jsonObject.get("Remark"));
                    } else {
                        hashMap.put("Remark", "");
                    }

                    if (jsonObject.has("CreateComBrName")) {
                        hashMap.put("CreateComBrName", jsonObject.get("CreateComBrName"));
                    } else {
                        hashMap.put("CreateComBrName", "");
                    }

                    if (jsonObject.has("CreateUserFullnameCreateUserFullname")) {
                        hashMap.put("CreateUserFullname", jsonObject.get("CreateUserFullname"));
                    } else {
                        hashMap.put("CreateUserFullname", "");
                    }

                    if (jsonObject.has("VehLicensePlateNumber")) {
                        hashMap.put("VehLicensePlateNumber", jsonObject.get("VehLicensePlateNumber"));
                    } else {
                        hashMap.put("VehLicensePlateNumber", "");
                    }

                    if (jsonObject.has("VehDrName")) {
                        hashMap.put("VehDrName", jsonObject.get("VehDrName"));
                    } else {
                        hashMap.put("VehDrName", "");
                    }

                    if (jsonObject.has("VehDrPhone")) {
                        hashMap.put("VehDrPhone", jsonObject.get("VehDrPhone"));
                    } else {
                        hashMap.put("VehDrPhone", "");
                    }
                    list.add(hashMap);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     *  查询调度明细
     * @return
     */
    public static List<HashMap<String,Object>> VehDis_LoadInfo(String vehDisNumber)
    {


        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();

        String methodName = "VehDis_LoadInfo";
        String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

        soapObject.addProperty("recNo",vehDisNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");


        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);
        envelope.implicitTypes = true;

        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            String jsonStr=object.toString();

            if(!jsonStr.equals("anyType{}"))
            {

                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("Table");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();

                    if (jsonObject.has("VehDispState")) {
                        hashMap.put("VehDispState", jsonObject.get("VehDispState"));
                    } else {
                        continue;

                    }
                    if (jsonObject.has("VehDispStateName")) {

                        hashMap.put("VehDispStateName", jsonObject.get("VehDispStateName"));
                    } else {
                        continue;
                    }

                    if (jsonObject.has("VehDispTime")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String t=format.format(Convert.ToDate(jsonObject.get("VehDispTime").toString()));
                        hashMap.put("VehDispTime",t);
                    } else {
                        hashMap.put("VehDispTime", "");
                    }

                    if (jsonObject.has("VehDispForwardedState")) {
                        hashMap.put("VehDispForwardedState", jsonObject.get("VehDispForwardedState"));
                    } else {
                        continue;
                    }

                    if (jsonObject.has("VehDispForwardedStateName")) {
                        hashMap.put("VehDispForwardedStateName", jsonObject.get("VehDispForwardedStateName"));
                    } else {
                        hashMap.put("VehDispForwardedStateName", "--");
                    }


                   if (jsonObject.has("Address")) {
                        hashMap.put("Address",jsonObject.get("Address").toString());
                    } else {
                        hashMap.put("Address", "--");
                    }


                    if (jsonObject.has("Remark")) {
                        hashMap.put("Remark", jsonObject.get("Remark"));
                    } else {
                        hashMap.put("Remark", "");
                    }

                    if (jsonObject.has("LicensePlateNumber")) {
                        hashMap.put("LicensePlateNumber", jsonObject.get("LicensePlateNumber"));
                    } else {
                        hashMap.put("LicensePlateNumber", "");
                    }

                    if (jsonObject.has("VehDrName")) {
                        hashMap.put("VehDrName", jsonObject.get("VehDrName"));
                    } else {
                        hashMap.put("VehDrName", "");
                    }

                    if (jsonObject.has("VehDrPhone")) {
                        hashMap.put("VehDrPhone", jsonObject.get("VehDrPhone"));
                    } else {
                        hashMap.put("VehDrPhone", "");
                    }
                    list.add(hashMap);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
