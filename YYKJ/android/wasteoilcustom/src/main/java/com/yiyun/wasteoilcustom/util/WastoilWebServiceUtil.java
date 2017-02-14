package com.yiyun.wasteoilcustom.util;

import com.example.fornet.NetWorkUtil;
import com.example.fornet.WebServiceUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.util.HashMap;

import static com.chengyi.android.util.ActivityUtil.alert;

/**
 * Created by Administrator on 2017/2/14.
 */

public class WastoilWebServiceUtil {
    private static final String NAMESPACE   = "http://tempuri.org/";
    static {
        WebServiceUtils.setNAMESPACE(NAMESPACE);
    }

    public static void callWebService(String url, final String methodName,
                                      HashMap<String, Object> properties,
                                      String addMappingKey,
                                      Class addMappingClass,
                                      final WebServiceUtils.WebServiceCallBack webServiceCallBack){

        if(!NetWorkUtil.IsNetWorkEnable()){
            alert("无法连接网络!");
            return;
        }

        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);


        WebServiceUtils.callWebService(url, methodName, properties, header,addMappingKey,addMappingClass, webServiceCallBack);

    }
}
