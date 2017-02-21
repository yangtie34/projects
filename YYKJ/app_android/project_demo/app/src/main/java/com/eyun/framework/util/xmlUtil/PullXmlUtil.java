package com.eyun.framework.util.xmlUtil;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.eyun.framework.util.FastJsonTools;


public class PullXmlUtil {

    public static XmlPullParser getXmlPullParser(XmlPullParser parser) {

        XmlPullParser xmlPullParser=null;
        try {
            xmlPullParser= writeXml(readXml(parser));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return xmlPullParser;
    }

    private static PullXmlEntity readXml(XmlPullParser parser) throws XmlPullParserException, IOException {

        //声明返回值
        PullXmlEntity pullXmlEntity = new PullXmlEntity();
        List<TagAttr> tagAttrList = new LinkedList<>();
        List<PullXmlEntity> pullXmlEntities=new LinkedList<PullXmlEntity>();
        //首先利用Xml.newPullParser()获取解析对象
        //获取解析的事件类型
        boolean bool=true;
        int eventType =  parser.getEventType();
        //判断文件解析的是否完毕
        while (bool){
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if(pullXmlEntity.getTag()==null){
                        String tag = parser.getName();
                        pullXmlEntity.setTag(tag);
                        pullXmlEntity.setNamespace(parser.getNamespace());
                        for (int j = 0; j < parser.getAttributeCount(); j++) {

                            tagAttrList.add(new TagAttr(parser.getAttributeNamespace(j),
                                    parser.getAttributeName(j),
                                   null,// parser.getAttributePrefix(j),
                                    parser.getAttributeType(j),
                                    parser.getAttributeValue(j)));//添加属性的值，如tag="item1"
                        }
                        pullXmlEntity.setTagAttrList(tagAttrList);
                    }else{
                        PullXmlEntity pullXmlEntityc=readXml(parser);
                        pullXmlEntities.add(pullXmlEntityc);
                    }
                    break;
                case XmlPullParser.END_TAG:
                     pullXmlEntity.setPullXmlEntities(pullXmlEntities);
                    bool=false;
                    break;
                default:break;
            }
            eventType=parser.next();
        }
        return pullXmlEntity;
    }

    // 写入
    public  static XmlPullParser writeXml(PullXmlEntity pullXmlEntity) throws IOException, XmlPullParserException {
        // 采用pull解析进行实现
        XmlPullParser parser = Xml.newPullParser();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ;

        XmlSerializer serializer = Xml.newSerializer();
        // 设置输出的流及编码
        serializer.setOutput(out, "UTF-8");
        // 设置文件的开始
        serializer.startDocument("UTF-8", true);


        writeXml(pullXmlEntity, serializer);

        // 文件的结束
        serializer.endDocument();
        parser.setInput(new ByteArrayInputStream(out.toByteArray()), "UTF-8");
        return parser;
    }

    // 写入
    public  static void writeXml(PullXmlEntity pullXmlEntity, XmlSerializer serializer) throws IOException, XmlPullParserException {


        // persons标签开始
        serializer.startTag(pullXmlEntity.getNamespace(), pullXmlEntity.getTag());
        for (int j = 0; j < pullXmlEntity.getTagAttrList().size(); j++) {
            TagAttr tagAttr = pullXmlEntity.getTagAttrList().get(j);
            serializer.attribute(tagAttr.getNamespace(), tagAttr.getName(), tagAttr.getValue());
            serializer.setPrefix(tagAttr.getPrefix(),tagAttr.getNamespace());
        }
        for (int j = 0; j < pullXmlEntity.getPullXmlEntities().size(); j++) {
            PullXmlEntity pullXmlEntities = pullXmlEntity.getPullXmlEntities().get(j);
            writeXml(pullXmlEntities, serializer);
        }
        // persons标签的结束
        serializer.endTag(pullXmlEntity.getNamespace(), pullXmlEntity.getTag());


    }

}
