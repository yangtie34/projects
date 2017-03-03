package com.eyunsoft.app_wasteoil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.eyunsoft.app_wasteoil.Model.CompanyCustomerApply_Model;
import com.eyunsoft.app_wasteoil.Publics.MsgBox;
import com.eyunsoft.app_wasteoil.bll.CompanyUser_BLL;
import com.eyunsoft.app_wasteoil.utils.ImageDeal;
import com.eyunsoft.app_wasteoil.utils.LoadDialog.LoadDialog;

import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/2/24.
 */

public class Register3 extends Activity {
    public static CompanyCustomerApply_Model model=new CompanyCustomerApply_Model();
    private TextView next;
    private ImageView thisimg;
    private Map<Integer,Uri> dataBitmap=new HashMap<>();
    private ImageView BusinessLicenseImg;
    private ImageView OrganizationCodeImg;
    private ImageView DangerWasteImg;
    private ImageView BusinessLicenseImgAdd ;
    private ImageView OrganizationCodeImgAdd ;
    private ImageView DangerWasteImgAdd ;
    //    private EditText AuditState;
//    private EditText CreateIp;
//    private EditText CreateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        BusinessLicenseImg = (ImageView) findViewById(R.id.BusinessLicenseImg);
        OrganizationCodeImg = (ImageView) findViewById(R.id.OrganizationCodeImg);
        DangerWasteImg = (ImageView) findViewById(R.id.DangerWasteImg);

        BusinessLicenseImgAdd = (ImageView) findViewById(R.id.BusinessLicenseImgAdd);
        OrganizationCodeImgAdd = (ImageView) findViewById(R.id.OrganizationCodeImgAdd);
        DangerWasteImgAdd = (ImageView) findViewById(R.id.DangerWasteImgAdd);
        if(model.getBusinessLicenseType()!=1){
            findViewById(R.id.OrganizationCodeLine).setVisibility(View.GONE);
        }
        next = (TextView) findViewById(R.id.next);
        next.setText("注册");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register3.this.next();
            }
        });
    }


    public void add(View view){
        Intent local = new Intent();
        local.setType("image/*");
        local.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(local, 2);

        switch (view.getId()){
            case R.id.BusinessLicenseImgAdd:
                thisimg=BusinessLicenseImg;
                break;
            case R.id.OrganizationCodeImgAdd:
                thisimg=OrganizationCodeImg;
                break;
            case R.id.DangerWasteImgAdd:
                thisimg=DangerWasteImg;
                break;
        }
    }
    public void Pai(View view){
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it, 1);
        switch (view.getId()){
            case R.id.BusinessLicenseImgPai:
                thisimg=BusinessLicenseImg;
                break;
            case R.id.OrganizationCodeImgPai:
                thisimg=OrganizationCodeImg;
                break;
            case R.id.DangerWasteImgPai:
                thisimg=DangerWasteImg;
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String url="";
        if (resultCode == Activity.RESULT_OK) {
            Bitmap image=null;
            switch(requestCode) {
                case 1:
                    /*Bundle extras = data.getExtras();
                    image = (Bitmap) extras.get("data");
                    String imagePath = ImageDeal.savePhoto(image, Environment
                            .getExternalStorageDirectory().getAbsolutePath(), String
                            .valueOf(System.currentTimeMillis()));
                  thisimg.setImageBitmap(GetLocalOrNetBitmap(imagePath));
                    dataBitmap.put(thisimg.getId(),null);
                    break;*/
                case 2:
                    Uri uri = data.getData();
                    thisimg.setImageBitmap(GetLocalOrNetBitmap(uri));
                    //thisimg.setImageURI(uri);
                        dataBitmap.put(thisimg.getId(),uri);
                    break;
                default:
                    break;
            };

        }
    }
      private void next() {
          final Uri BusinessLicenseImge=dataBitmap.get(BusinessLicenseImg.getId());
          final Uri OrganizationCodeImge=dataBitmap.get(OrganizationCodeImg.getId());
          final Uri DangerWasteImge=dataBitmap.get(DangerWasteImg.getId());
          if(BusinessLicenseImge==null){
              MsgBox.Show(Register3.this, "请选择企业工商营业执照！");
              return;
          }
          if(OrganizationCodeImge==null&& model.getBusinessLicenseType()==1){
              MsgBox.Show(Register3.this, "请选择组织机构代码证！");
              return;
          }
          if(DangerWasteImge==null){
              MsgBox.Show(Register3.this, "请选择危险废物经营许可证！");
              return;
          }
          PackageManager pm = getPackageManager();
          boolean permission = (PackageManager.PERMISSION_GRANTED ==
                  pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", "com.eyunsoft.app_wasteoilCostomer"));
          if (!permission) {
              MsgBox.Show(Register3.this,"请放开存储权限");
              return;
          }
          LoadDialog.show(Register3.this,"注册中...");
          new Thread() {
              public void run() {
                  String strBusinessLicenseImge=uploadPic(BusinessLicenseImge);
                  if(strBusinessLicenseImge==null){
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                      LoadDialog.dismiss(Register3.this);
                      LoadDialog.show(Register3.this,"上传失败！未知错误！");
                      return;
                          }
                      });
                  }
                  model.setBusinessLicenseImg(strBusinessLicenseImge);
                 if(model.getBusinessLicenseType()==1){
                     model.setOrganizationCodeImg(uploadPic(OrganizationCodeImge));}
                  model.setDangerWasteImg(uploadPic(DangerWasteImge));
                  final String result= CompanyUser_BLL.register(model);
                  LoadDialog.dismiss(Register3.this);
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         oknext(result);
                     }
                 });
              }
          }.start();

    }
    private void oknext(  String result){
        if(result==null){
            MsgBox.Show(Register3.this, "服务器访问失败！");
            return;
        }
        String results[]=result.split(",");
        if(results[0].equalsIgnoreCase("false")){
            MsgBox.Show(Register3.this, "注册失败！"+results[1]);
        }else{
            MsgBox.Show(Register3.this, "注册成功！");
            Intent intent=new Intent(Register3.this,Login.class);
            startActivity(intent);
        }

    }
    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     *
     * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
     *
     * B.本地路径:url="file://mnt/sdcard/photo/image.png";
     *
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param url
     * @return
     */
    public  Bitmap GetLocalOrNetBitmap(Uri url)  {
        try {
            InputStream is=this.getContentResolver().openInputStream(url);
            Bitmap bitmap=BitmapFactory.decodeStream(is);
            is.close();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            // 设置想要的大小
            int newWidth = 320;
            int newHeight = 480;
            // 计算缩放比例
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
            bitmap.recycle();
            return newbm;
        }catch (IOException e){
            e.printStackTrace();
        }
      return null;
    }
    private String  uploadPic(Uri imagePath) {
        if(imagePath != null){
            Bitmap bm=GetLocalOrNetBitmap(imagePath);
            //int size=500;
            InputStream is= null;
         /*   try {
                is = this.getContentResolver().openInputStream(imagePath);
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            Bitmap bm=BitmapFactory.decodeStream(is);
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // int ysl=50;
            //do{
            // baos.reset();

           bm.compress(Bitmap.CompressFormat.PNG, 20, baos);
            //  ysl-=10;
            //}while (baos.toByteArray().length / 1024 > size);
            //bm.compress(Bitmap.CompressFormat.JPEG, size, baos);
            //Log.e("哈哈","最终大小" + baos.toByteArray().length);
            byte[] bytes = baos.toByteArray();
            try {
               // bm.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(
                    bytes, 0, bytes.length);

            String namePath = ImageDeal.savePhoto(compressedBitmap, Environment
                    .getExternalStorageDirectory().getAbsolutePath(), String
                    .valueOf(System.currentTimeMillis()));
           // compressedBitmap.recycle();
           // Log.e("sd:",bytes.length+"");
                String uploadBuffer = new String(Base64.encode(bytes));//进行Base64编码

                String msg=CompanyUser_BLL.UpLoadHeader(uploadBuffer,namePath);
                if(!TextUtils.isEmpty(msg))
                {
                    return msg;
                }
        }
        return null;
    }
    public Bitmap compressImage(Uri url)  {
        int size=500;
        InputStream is= null;
        try {
            is = this.getContentResolver().openInputStream(url);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        Bitmap bm=BitmapFactory.decodeStream(is);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
       // int ysl=50;
        //do{
           // baos.reset();
            bm.compress(Bitmap.CompressFormat.PNG, 50, baos);
          //  ysl-=10;
        //}while (baos.toByteArray().length / 1024 > size);
            //bm.compress(Bitmap.CompressFormat.JPEG, size, baos);
        //Log.e("哈哈","最终大小" + baos.toByteArray().length);
        byte[] bytes = baos.toByteArray();

        try {
            bm.recycle();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(
                bytes, 0, bytes.length);
        return compressedBitmap;
    }

    // 把Bitmap转换成Base64
    public static String getBitmapStrBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        try {
            bitmap.recycle();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encode(bytes));
    }
}
