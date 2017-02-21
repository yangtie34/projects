package com.eyun.framework.angular.core;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.view.LayoutInflater;
import android.view.View;

import com.eyun.framework.util.FastJsonTools;
import com.eyun.framework.util.xmlUtil.PullXmlUtil;

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
         Resources res = Scope.activity.getResources();
         XmlResourceParser parser = res.getLayout(loyout);
        View view=null;
        try {
            view= inflateXml(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }
        return view;
    }
    private static View inflateXml(XmlPullParser parser) throws XmlPullParserException, IOException {


        return LayoutInflater.from(Scope.activity).inflate(PullXmlUtil.getXmlPullParser(parser),null);
    }
}
