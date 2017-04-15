package com.eyun.framework.angular.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.eyun.framework.angular.baseview.WindowPop;
import com.eyun.framework.util.android.ActivityUtil;

import static com.eyun.framework.angular.core.Scope.activity;
import static com.eyun.framework.util.android.KeyBoardUtils.isShouldHideInput;


/**
 * Created by administrator on 2016-12-26.
 */

public class AngularActivity extends Activity {
    private boolean index=false;
    public Scope scope;
    protected EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scope = Scope.init(AppContext.getInstance().getScope(), this);
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
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        scope.inflateScope=scope;
       super.setContentView(layoutResID);
    }
    //点击EditText文本框之外任何地方隐藏键盘的解决办法
    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v =getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public void back(View view) {
        this.onBackPressed();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(editText!=null&&keyCode == 120) {
            editText.requestFocus();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
