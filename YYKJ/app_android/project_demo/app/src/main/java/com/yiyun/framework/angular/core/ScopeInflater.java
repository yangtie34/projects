package com.yiyun.framework.angular.core;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


/**
 * Created by Administrator on 2017/2/20.
 */

public class ScopeInflater {
    /**
     * 解析包含复杂执行的xml
     */
    public static View inflate(int loyout){
        final Resources res = Scope.activity.getResources();
        final XmlResourceParser parser = res.getLayout(loyout);
        try {
            return inflateXml(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }
        return null;
    }
    private static View inflateXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        View view=null;
        int eventCode = parser.getEventType();//获取事件类型

        while(eventCode != XmlPullParser.END_DOCUMENT)  {
            switch (eventCode){
                case XmlPullParser.START_DOCUMENT: //开始读取XML文档
                    //实例化集合类
                    break;
                case XmlPullParser.START_TAG://开始读取某个标签
                    if("person".equals(parser.getName())) {
                        //通过getName判断读到哪个标签，然后通过nextText()获取文本节点值，或通过getAttributeValue(i)获取属性节点值
                    }
                    break;
                case XmlPullParser.END_TAG://读完一个Person，可以将其添加到集合类中
                    break;
            }
            parser.next();
        }

        return view;
    }
}
