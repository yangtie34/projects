package com.eyunsoft.app_wasteoilCostomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eyunsoft.app_wasteoilCostomer.Model.SysNotice_Model;
import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;
import com.eyunsoft.app_wasteoilCostomer.bll.Notice_BLL;

public class NoticeDetail extends AppCompatActivity {

    public Button btnBack;

    public TextView txtTitle;
    public TextView txtNoticeTime;
    public TextView txtNoticeContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        btnBack=(Button)findViewById(R.id.btnBack);

        txtNoticeContent=(TextView)findViewById(R.id.txtContent);
        txtNoticeTime=(TextView)findViewById(R.id.txtNoticeTime);
        txtTitle=(TextView)findViewById(R.id.txtTitle);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       InitForm();


    }

   public void InitForm()
   {
       Intent myIntent=getIntent();
       String notice=myIntent.getStringExtra("NoticeID");
       if(!TextUtils.isEmpty(notice))
       {
           SysNotice_Model mo= Notice_BLL.Notice_LoadData(notice);
           txtTitle.setText(mo.getNoticeTitle());
           txtNoticeTime.setText(mo.getNoticeTime().replace("T"," "));
           txtNoticeContent.setText(mo.getNoticeContent());
       }
       else
       {
           long   noticeId=myIntent.getLongExtra("NoticeId",0);
           if(noticeId>0) {
               SysNotice_Model model = Notice_BLL.Notice_LoadData(Convert.ToString(noticeId));
               txtTitle.setText(model.getNoticeTitle());
               txtNoticeTime.setText(model.getNoticeTime().replace("T", " "));
                txtNoticeContent.setText(model.getNoticeContent());
           }
       }
   }


}


