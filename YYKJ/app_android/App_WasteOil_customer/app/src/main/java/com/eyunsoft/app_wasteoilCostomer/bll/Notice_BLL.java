package com.eyunsoft.app_wasteoilCostomer.bll;

import com.eyunsoft.app_wasteoilCostomer.Model.SysNoticeRead_Model;
import com.eyunsoft.app_wasteoilCostomer.Model.SysNotice_Model;
import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;

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
 * Created by Administrator on 2017/1/11.
 */

public class Notice_BLL {


    // 命名空间
    private static final String NAMESPACE   = "http://tempuri.org/";
    // SOAPACTION
    private static String   SOAP_ACTION = "http://tempuri.org";

    /**
     * 系统通知-加载数据
     * @param noticeId
     * @return
     */
    public static SysNotice_Model Notice_LoadData(String noticeId)
    {
        String methodName = "LoadData";
        String webUrl = "http://125.46.79.254:8211/Offer/Notice.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("noticeId",noticeId);

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
        envelope.addMapping(NAMESPACE,"SysNotice_Model",SysNotice_Model.class);
        envelope.setOutputSoapObject(soapObject);

        SysNotice_Model mo=new SysNotice_Model();
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
     * 读通知
     * @param model
     * @param
     * @return
     */
    public static String ReadNotice(SysNoticeRead_Model model)
    {
        String methodName = "ReadNotice";
        String webUrl = "http://125.46.79.254:8211/Offer/Notice.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("mo",model);


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
        envelope.addMapping(NAMESPACE,"SysNoticeRead_Model",SysNoticeRead_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "读取失败";
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
     *  通知查询
     * @param Condition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static List<HashMap<String,Object>> GetNoticeList(String Condition, int pageIndex, int pageSize)
    {


        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();

        String methodName = "GetNoticeList";
        String webUrl = "http://125.46.79.254:8211/Offer/Notice.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

        soapObject.addProperty("condition","["+Condition+"]");
        soapObject.addProperty("pageIndex",pageIndex);
        soapObject.addProperty("pageSize",pageSize);



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

                    if (jsonObject.has("NoticeID")) {
                        hashMap.put("NoticeID", jsonObject.get("NoticeID"));
                    } else {
                        continue;
                    }
                    if (jsonObject.has("NoticeTime")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String t=format.format(Convert.ToDate(jsonObject.get("NoticeTime").toString()));
                        hashMap.put("NoticeTime",t);
                    } else {
                        hashMap.put("NoticeTime", "");
                    }

                    if (jsonObject.has("NoticeTitle")) {

                        hashMap.put("NoticeTitle", jsonObject.get("NoticeTitle"));
                    } else {
                        hashMap.put("NoticeTitle", "");
                    }

                    if (jsonObject.has("NoticeType")) {

                        hashMap.put("NoticeType", jsonObject.get("NoticeType"));
                    } else {
                        hashMap.put("NoticeType", "");
                    }

                    if (jsonObject.has("NoticeRecNumber")) {

                        hashMap.put("NoticeRecNumber", jsonObject.get("NoticeRecNumber"));
                    } else {
                        hashMap.put("NoticeRecNumber", "");
                    }

                    if (jsonObject.has("ProShapeName")) {
                        hashMap.put("ProShapeName", jsonObject.get("ProShapeName"));
                    } else {
                        hashMap.put("ProShapeName", "--");
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

