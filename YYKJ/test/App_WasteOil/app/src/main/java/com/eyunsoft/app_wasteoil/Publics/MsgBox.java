package com.eyunsoft.app_wasteoil.Publics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/26.
 */

public class MsgBox {


    /**
     * 显示提示信息
     * @param context
     * @param str
     */
    public static void Show(Context context, String str)
    {
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }


}
