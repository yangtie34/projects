package com.chengyi.android.angular.UI;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chengyi.android.angular.R;
import com.chengyi.android.angular.core.AngularActivity;
import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.util.ActivityUtil;
import com.chengyi.android.util.AppContext;

import org.w3c.dom.Text;

import static com.chengyi.android.angular.core.Scope.activity;

/**
 * Created by Administrator on 2017/2/14.
 */

public class Loadding {
    private static AngularActivity angularActivity;
    private static View view;

    public static void show() {
            show("");
    }

    public static void show(String text){
       if(angularActivity==null||angularActivity!= activity){

           angularActivity=activity;

           LayoutInflater inflater = LayoutInflater.from(angularActivity);

           view = inflater.inflate(R.layout.loadding, null);

           ActivityUtil.getRootView().addView(view);
       }
        ((TextView)view.findViewById(R.id.loaddingMsg)).setText(text);
        view.setVisibility(View.VISIBLE);
    }

    public static void hide(){
        view.setVisibility(View.GONE);
    }

}
