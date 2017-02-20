package com.yiyun.framework.angular.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yiyun.framework.angular.baseview.WindowPop;
import com.yiyun.framework.util.ActivityUtil;

import static com.yiyun.framework.angular.core.Scope.activity;


/**
 * Created by administrator on 2016-12-26.
 */

public class AngularActivity extends Activity {
    private boolean index=false;
    public Scope scope;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scope = Scope.init(AppContext.getScope(), this);
        AppContext.addActivity(this);
    }

    public boolean isIndex() {
        return index;
    }

    public void setIndex() {
        this.index = true;
    }

    /**
     * 监听Back键按下事件,方法1:

     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        if(WindowPop.hasShow()){
            WindowPop.hideTop();
        }else if(index){
            ActivityUtil.exitBy2Click(); //调用双击退出函数
        }else{
            AppContext.intentPrev();
        }
        //super.onBackPressed();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        activity=this;
        AppContext.getInstance().newIntentLast(activity);
         }

}
