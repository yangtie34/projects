package com.eyunsoft.app_wasteoil.bll;

import com.eyunsoft.app_wasteoil.Model.ProduceRec_Model;
import com.eyunsoft.app_wasteoil.Publics.Convert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ProdRec_BLL {

    // 命名空间
    private static final String NAMESPACE   = "http://tempuri.org/";
    // SOAPACTION
    private static String   SOAP_ACTION = "http://tempuri.org";

    /**
     * 生产单-加载数据
     * @param recNumber
     * @return
     */
    public static ProduceRec_Model LoadData(String recNumber)
    {
        String methodName = "ProdRec_LoadData";
        String webUrl = "http://125.46.79.254:8211/Offer/Produce.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("recNumber",recNumber);

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
        envelope.addMapping(NAMESPACE,"ProduceRec_Model",ProduceRec_Model.class);
        envelope.setOutputSoapObject(soapObject);

        ProduceRec_Model mo=new ProduceRec_Model();
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
                        System.out.println(i + "--" + value);
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
     * 添加生产单
     * @param model
     * @return
     */
    public static String ProdRec_Add(ProduceRec_Model model)
    {
        String methodName = "ProdRec_Add";
        String webUrl = "http://125.46.79.254:8211/Offer/Produce.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("mo",model);
      //  soapObject.addProperty("recNumber","");

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
        envelope.addMapping(NAMESPACE,"ProduceRec_Model",ProduceRec_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = null;
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.startsWith("[anyType{}"))//执行成功
            {
                return  "";
            }
            else if(resatult.endsWith("anyType{}]"))
            {
                resatult=resatult.replace("[","").replace("anyType{}]","");
            }
            else
                return "未知错误";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "未知错误";
        }

        return resatult;
    }

    /**
     * 修改生产单
     * @param model
     * @return
     */
    public static String ProdRec_Update(ProduceRec_Model model)
    {
        String methodName = "ProdRec_Update";
        String webUrl = "http://125.46.79.254:8211/Offer/Produce.asmx";

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
        envelope.addMapping(NAMESPACE,"ProduceRec_Model",ProduceRec_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "修改失败";
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
                resatult="修改成功";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 删除生产单
     * @param recNumber
     * @return
     */
    public static String ProdRec_Delete(String recNumber)
    {
        String methodName = "ProdRec_Delete";
        String webUrl = "http://125.46.79.254:8211/Offer/Produce.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("recNumber",recNumber);

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

        String resatult = "删除失败";
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
    public static List<HashMap<String,Object>> ProdRec_Select(String Condition, int pageIndex, int pageSize)
    {

        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();

        String methodName = "ProdRec_Select";
        String webUrl = "http://125.46.79.254:8211/Offer/Produce.asmx";

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
                    list.add(toHashMap(jsonObject));
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
    public static HashMap<String, Object> toHashMap(JSONObject jsonObject)
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        // 将json字符串转换成jsonObject
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            Object value = null;
            try {
                value =  jsonObject.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            data.put(key, value);
        }
        return data;
    }
}
