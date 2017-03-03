package com.eyunsoft.app_wasteoil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.eyunsoft.app_wasteoil.Model.NameToValue;
import com.eyunsoft.app_wasteoil.bll.Category_BLL;
import com.eyunsoft.app_wasteoil.bll.SysPublic_BLL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/1.
 */

public class CategoryAdd  extends AppCompatActivity {

    private Button btnAdd;
    private Button btnBack;
    public ArrayList<NameToValue> listCategory;
    public Map<String,List<NameToValue>> MapCategory;
    private Spinner dropCategorydl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
        btnAdd= (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        btnBack= (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        init();
    }

    private void init(){
        //危废种类
        long comId = App.getInstance().getSysComID();
        listCategory = Category_BLL.GetProductCategory(comId);
        MapCategory= SysPublic_BLL.formatCategory(listCategory);
        final ArrayAdapter<NameToValue> arrayAdapterCategory=new ArrayAdapter<NameToValue>(this,android.R.layout.simple_spinner_item,MapCategory.get("0"));

        dropCategorydl.setAdapter(arrayAdapterCategory);
        dropCategorydl.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
             /*   ;//文本说明
                dropCategorydlid=arrayAdapterCategory.getItem(arg2).InfoValue;
                ArrayAdapter<NameToValue> arrayAdapterCategoryzl=new ArrayAdapter<NameToValue>(CategoryAdd.this,android.R.layout.simple_spinner_item,MapCategory.get(dropCategorydlid));
                dropCategoryzl.setAdapter(arrayAdapterCategoryzl);*/
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void add(){

    }

}
