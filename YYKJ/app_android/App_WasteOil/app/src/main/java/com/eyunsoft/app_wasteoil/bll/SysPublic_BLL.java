package com.eyunsoft.app_wasteoil.bll;

import com.eyunsoft.app_wasteoil.Model.NameToValue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/26.
 */

public class SysPublic_BLL {


    // 命名空间
    private static final String NAMESPACE   = "http://tempuri.org/";
    // SOAPACTION
    private static String   SOAP_ACTION = "http://tempuri.org";
    private static ArrayList<NameToValue> Product_Shape;
    /**
     * 获取危险废物形态
     * @return
     */
    public static ArrayList<NameToValue> GetProduct_Shape()
    {
        if(Product_Shape!=null)return new ArrayList<>(Product_Shape);
        ArrayList<NameToValue> list=new ArrayList<NameToValue>();

        String methodName = "GetProduct_Shape";
        String webUrl = "http://125.46.79.254:8211/Offer/SysPublic.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);



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
            String jsonStr=object.toString();

            if(!jsonStr.trim().equals(""))
             {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("infoname")) {
                        value.InfoName=jsonObject.get("infoname").toString();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("infovalue")) {
                        value.InfoValue=jsonObject.get("infovalue").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
                 Product_Shape=list;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
    private static ArrayList<NameToValue> Product_HazardNature;
    /**
     * 获取危险废物特性
     * @return
     */
    public static ArrayList<NameToValue> GetProduct_HazardNature()
    {
        if(Product_HazardNature!=null)return Product_HazardNature;
        ArrayList<NameToValue> list=new ArrayList<NameToValue>();

        String methodName = "GetProduct_HazardNature";
        String webUrl = "http://125.46.79.254:8211/Offer/SysPublic.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

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

            System.out.println("获取到数据GetProduct_HazardNature:" + object.toString());
            String jsonStr=object.toString();

            if(!jsonStr.trim().equals(""))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(i);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("infoname")) {
                        value.InfoName=jsonObject.get("infoname").toString();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("infovalue")) {
                        value.InfoValue=jsonObject.get("infovalue").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
                Product_HazardNature=list;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
    private static ArrayList<NameToValue> Product_MeasureUnit;
    /**
     * 获取计量单位
     * @return
     */
    public static ArrayList<NameToValue> GetProduct_MeasureUnit()
    {
        if(Product_MeasureUnit!=null)return Product_MeasureUnit;
        ArrayList<NameToValue> list=new ArrayList<NameToValue>();

        String methodName = "GetProduct_MeasureUnit";
        String webUrl = "http://125.46.79.254:8211/Offer/SysPublic.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);



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

            if(!jsonStr.trim().equals(""))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("infoname")) {
                        value.InfoName=jsonObject.get("infoname").toString();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("infovalue")) {
                        value.InfoValue=jsonObject.get("infovalue").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
                Product_MeasureUnit=list;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * 获取车辆调度状态
     * @return
     */
    public static ArrayList<NameToValue> GetVehicleDispath_VehDispState()
    {

        ArrayList<NameToValue> list=new ArrayList<NameToValue>();

        String methodName = "GetVehicleDispath_VehDispState";
        String webUrl = "http://125.46.79.254:8211/Offer/SysPublic.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

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

            if(!jsonStr.trim().equals(""))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(i);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("infoname")) {
                        value.InfoName=jsonObject.get("infoname").toString();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("infovalue")) {
                        value.InfoValue=jsonObject.get("infovalue").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * 获取车辆中转状态
     * @return
     */
    public static ArrayList<NameToValue> GetVehicleDispath_VehDispForwardedState()
    {

        ArrayList<NameToValue> list=new ArrayList<NameToValue>();

        String methodName = "GetVehicleDispath_VehDispForwardedState";
        String webUrl = "http://125.46.79.254:8211/Offer/SysPublic.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

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

            if(!jsonStr.trim().equals(""))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(i);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("infoname")) {
                        value.InfoName=jsonObject.get("infoname").toString();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("infovalue")) {
                        value.InfoValue=jsonObject.get("infovalue").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * 获取交接支付方式
     * @return
     */
    public static ArrayList<NameToValue> GetVehicleDispathHandover_PaymentMode()
    {

        ArrayList<NameToValue> list=new ArrayList<NameToValue>();

        String methodName = "GetVehicleDispathHandover_PaymentMode";
        String webUrl = "http://125.46.79.254:8211/Offer/SysPublic.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

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

            if(!jsonStr.trim().equals(""))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(i);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("infoname")) {
                        value.InfoName=jsonObject.get("infoname").toString();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("infovalue")) {
                        value.InfoValue=jsonObject.get("infovalue").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * 获取交接结算方式
     * @return
     */
    public static ArrayList<NameToValue> GetVehicleDispathHandover_SettlementMode()
    {

        ArrayList<NameToValue> list=new ArrayList<NameToValue>();

        String methodName = "GetVehicleDispathHandover_SettlementMode";
        String webUrl = "http://125.46.79.254:8211/Offer/SysPublic.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

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

            if(!jsonStr.trim().equals(""))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(i);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("infoname")) {
                        value.InfoName=jsonObject.get("infoname").toString();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("infovalue")) {
                        value.InfoValue=jsonObject.get("infovalue").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    private static ArrayList<NameToValue> Product_Pack;
    /**
     * 获取危废生产单包装方式
     * @return
     */
    public static ArrayList<NameToValue> GetProduct_Pack()
    {
        if(Product_Pack!=null)return Product_Pack;

        ArrayList<NameToValue> list=new ArrayList<NameToValue>();

        String methodName = "GetProduct_Pack";
        String webUrl = "http://125.46.79.254:8211/Offer/SysPublic.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

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

            if(!jsonStr.trim().equals(""))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(i);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("infoname")) {
                        value.InfoName=jsonObject.get("infoname").toString();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("infovalue")) {
                        value.InfoValue=jsonObject.get("infovalue").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
                Product_Pack=list;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
    private static Map<String,List<NameToValue>> formatCategory;
    public static Map<String,List<NameToValue>> formatCategory(ArrayList<NameToValue> listCategory){
        if(formatCategory!=null)return formatCategory;
        Map<String,List<NameToValue>> MapCategory=new HashMap<>();
        List<NameToValue> dl=new ArrayList<>();
        for (int i = 0; i <listCategory.size() ; i++) {
            NameToValue Category=listCategory.get(i);
            if(!Category.InfoValue.equalsIgnoreCase("")&&Integer.parseInt(Category.InfoValue)<100){
                dl.add(Category) ;

                List<NameToValue> zl=new ArrayList<>();
                for (int j = 0; j <listCategory.size() ; j++) {
                    NameToValue Categoryzl=listCategory.get(j);
                    if(Categoryzl!=Category&&Categoryzl.InfoValue.startsWith(Category.InfoValue)){
                        zl.add(Categoryzl) ;
                    }
                }
                MapCategory.put(Category.InfoValue,zl);
            }
        }
        MapCategory.put("0",dl);
        formatCategory=MapCategory;
        return MapCategory;
    }
    private static Map<String,List<NameToValue>> formatSelectCategory;
    public static Map<String,List<NameToValue>> formatSelectCategory(ArrayList<NameToValue> listCategory){
        if(formatSelectCategory!=null)return formatSelectCategory;
        Map<String,List<NameToValue>> MapCategory=new HashMap<>();
        List<NameToValue> dl=new ArrayList<>();
        NameToValue nameAll=new NameToValue();
        nameAll.InfoValue="all";
        nameAll.InfoName="==所有==";
        dl.add(nameAll);
        NameToValue nameAll1=new NameToValue();
        nameAll1.InfoValue="";
        nameAll1.InfoName="==所有==";
        List<NameToValue> all=new ArrayList<>();
        all.add(nameAll1);
        MapCategory.put("all",all);
        for (int i = 0; i <listCategory.size() ; i++) {
            NameToValue Category=listCategory.get(i);
            if(Integer.parseInt(Category.InfoValue)<100){
                dl.add(Category) ;

                List<NameToValue> zl=new ArrayList<>();
                zl.add(Category);
                for (int j = 0; j <listCategory.size() ; j++) {
                    NameToValue Categoryzl=listCategory.get(j);
                    if(Categoryzl!=Category&&Categoryzl.InfoValue.startsWith(Category.InfoValue)){
                        zl.add(Categoryzl) ;
                    }
                }
                MapCategory.put(Category.InfoValue,zl);
            }
        }
        MapCategory.put("0",dl);
        formatSelectCategory=MapCategory;
        return MapCategory;
    }
    public static void init(){
        formatSelectCategory=null;
        formatCategory=null;
        Product_Pack=null;
        Product_MeasureUnit=null;
        Product_HazardNature=null;
        Product_Shape=null;


    }
}
