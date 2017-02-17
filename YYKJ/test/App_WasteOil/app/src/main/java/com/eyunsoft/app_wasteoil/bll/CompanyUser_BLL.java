package com.eyunsoft.app_wasteoil.bll;

import com.eyunsoft.app_wasteoil.Model.CompanyUser_Model;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

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

}