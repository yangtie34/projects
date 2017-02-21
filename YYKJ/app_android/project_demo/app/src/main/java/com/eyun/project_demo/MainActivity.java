package com.eyun.project_demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.ScopeInflater;

public class MainActivity extends AngularActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean bool=true;
        for (;bool==true;){
            /*switch (eventType) {
                case XmlPullParser.START_TAG:
                    if(pullXmlEntity.getTag()==null){
                        String tag = parser.getName();
                        pullXmlEntity.setTag(tag);
                        pullXmlEntity.setNamespace(parser.getNamespace());
                        tagAttrList = new LinkedList<>();
                        for (int j = 0; j < parser.getAttributeCount(); j++) {
                            tagAttrList.add(new TagAttr(parser.getAttributeNamespace(j), parser.getAttributeName(j), parser.getAttributeValue(j)));//添加属性的值，如tag="item1"
                        }
                        pullXmlEntity.setTagAttrList(tagAttrList);
                    }else{

                        pullXmlEntities.add(readXml(parser));

                    }

                    break;
                case XmlPullParser.END_TAG:
                     pullXmlEntity.setPullXmlEntities(pullXmlEntities);
                    loop=false;
                    break;
            }*/
            bool=false;
            System.out.print(bool);
           int  eventType=1;

        }
        this.addContentView(ScopeInflater.inflate(R.layout.activity_main),null);
       // setContentView(R.layout.activity_main);
    }
}
