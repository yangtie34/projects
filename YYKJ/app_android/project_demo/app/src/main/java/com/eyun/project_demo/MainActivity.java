package com.eyun.project_demo;

import android.os.Bundle;

import com.eyun.framework.angular.core.AngularActivity;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AngularActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        List<String> list=new LinkedList<String>();
        list.add("str0");
        list.add("str1");
        list.add("str2");
        list.add("str3");
       // list.add("str4");
        scope.key("list").val(list);
        scope.key("str123").val("44464654654564");
    }
}
