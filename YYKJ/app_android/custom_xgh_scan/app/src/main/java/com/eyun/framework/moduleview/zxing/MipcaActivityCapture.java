package com.eyun.framework.moduleview.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.SurfaceHolder.Callback;

import com.eyun.framework.moduleview.zxing.view.ViewfinderView;
import com.google.zxing.Result;

public interface MipcaActivityCapture extends Callback {

    ViewfinderView getViewfinderView();

    void handleDecode(Result obj, Bitmap barcode);

    void setResult(int resultOk, Intent obj);

    void finish();

    void startActivity(Intent intent);

    void drawViewfinder();

    Handler getHandler();
}