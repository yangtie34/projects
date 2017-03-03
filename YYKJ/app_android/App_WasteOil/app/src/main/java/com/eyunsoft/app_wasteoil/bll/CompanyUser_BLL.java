package com.eyunsoft.app_wasteoil.bll;

import com.eyunsoft.app_wasteoil.Model.CompanyCustomerApply_Model;
import com.eyunsoft.app_wasteoil.Model.CompanyCustomer_Model;
import com.eyunsoft.app_wasteoil.Model.CompanyUser_Model;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.util.Vector;

/**
 * Created by Administrator on 2016/12/22.
 */

public class CompanyUser_BLL {
    // 命名空间
    private static final String NAMESPACE   = "http://tempuri.org/";
    // SOAPACTION
    private static String   SOAP_ACTION = "http://tempuri.org";

    public static String Login(CompanyUser_Model model,long comID)
    {
        String methodName = "SysLogin";
        String webUrl = "http://125.46.79.254:8211/Login/Login.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

        soapObject.addProperty("mo", model);
        soapObject.addProperty("comID",comID);
        soapObject.addProperty("frameType","3");
        soapObject.addProperty("mess","");


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
        envelope.addMapping(NAMESPACE, "CompanyUser_Model", CompanyUser_Model.class);
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }
    public static String SysLogin_CompanyCustomer(CompanyCustomer_Model model, long comID)
    {
        String methodName = "SysLogin_CompanyCustomer";
        String webUrl = "http://125.46.79.254:8211/Login/Login.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

        soapObject.addProperty("mo", model);
        soapObject.addProperty("comID",comID);
        soapObject.addProperty("frameType","-1");
        soapObject.addProperty("mess","");


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
        envelope.addMapping(NAMESPACE, "CompanyCustomer_Model", CompanyCustomer_Model.class);
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }
    /**
     *
     * @param imageBuffer
     * @return
     */
    public static String UpLoadHeader(String imageBuffer,String path)
    {

        String methodName="GetUploadFile_CustomerAuthent";
        String webUrl = "http://125.46.79.254:8211/FileUpload/UploadFile.asmx";


        //以下就是 调用过程了，不明白的话 请看相关webservice文档
        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("imageBuffer", imageBuffer);   //参数2  图片字符串
        soapObject.addProperty("oFilePath", path);   //参数2  图片字符串

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

        String resatult = "";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object result = envelope.getResponse();
            resatult= result.toString().replace("[","").replace("]","").split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  resatult;
    }
    public static String register(CompanyCustomerApply_Model model)
    {
        String methodName = "Add";
        String webUrl = "http://125.46.79.254:8211/Customer/Apply.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

        soapObject.addProperty("mo", model);


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
        envelope.addMapping(NAMESPACE, "CompanyCustomerApply_Model", CompanyCustomerApply_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = null;
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;
            Object o=envelope.getResponse();
            Vector<SoapPrimitive> object = (Vector) envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult =  object.get(1).toString()+","+ object.get(0).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }
}
