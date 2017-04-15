package com.eyun.framework.util.android;

import com.eyun.framework.angular.core.AppContext;

import java.util.Timer;
import java.util.TimerTask;

import static com.eyun.framework.util.android.ViewUtil.alert;

/**
 * Created by administrator on 2016-10-20.
 */

public class ActivityUtil {
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    public static void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            alert("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            AppContext.getInstance().exit();
        }
    }
}
