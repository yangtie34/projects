package com.eyunsoft.app_wasteoilCostomer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.eyunsoft.app_wasteoilCostomer.Model.NameToValue;
import com.eyunsoft.app_wasteoilCostomer.Model.ProduceRec_Model;
import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;
import com.eyunsoft.app_wasteoilCostomer.Publics.MsgBox;
import com.eyunsoft.app_wasteoilCostomer.Publics.TitleSet;
import com.eyunsoft.app_wasteoilCostomer.bll.Category_BLL;
import com.eyunsoft.app_wasteoilCostomer.bll.ProdRec_BLL;
import com.eyunsoft.app_wasteoilCostomer.bll.SysPublic_BLL;
import com.eyunsoft.app_wasteoilCostomer.utils.CustomCheckBox.CustomCheckBox;
import com.eyunsoft.app_wasteoilCostomer.utils.FormCatchUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.media.CamcorderProfile.get;

public class ProdRecAdd extends AppCompatActivity {

    private Button btnSave;
    private Button btnDelete;
    private Button btnBack;

    private EditText editRecNo;


    private EditText editProNumber;
    private EditText editProDangerComponent;
    private EditText editRemark;

    private EditText editUseProName;
    private EditText editUseProNumber;
    private EditText editUseProSpec;
    private EditText editUseProMode;

    public Map<String,List<NameToValue>> MapCategory;
    private Spinner dropCategorydl;
    private Spinner dropCategoryzl;
    private String  dropCategorydlid;

    //private Spinner dropCategory;
    private Spinner dropProShape;
    private Spinner dropProMeasureUnit;
    private Spinner dropUseProMeasureUnit;
    private Spinner dropProPack;



    public ArrayList<NameToValue> listCategory;
    public ArrayList<NameToValue> listProShape;
    public ArrayList<NameToValue> listProMeasureUnit;
    public ArrayList<NameToValue> listProHazardNature;
    public ArrayList<NameToValue> listProPack;

    public CustomCheckBox customCheckBox;

    private boolean isFirstLoad = true;//首次加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_rec_add);

        editRecNo = (EditText) findViewById(R.id.edit_RecNo);

        editProNumber = (EditText) findViewById(R.id.edit_ProNumber);

        editProDangerComponent = (EditText) findViewById(R.id.edit_ProDangerComponent);
        editRemark = (EditText) findViewById(R.id.edit_Remark);

        dropCategorydl = (Spinner) findViewById(R.id.drop_Categorydl);
        dropCategoryzl = (Spinner) findViewById(R.id.drop_Categoryzl);

        dropProMeasureUnit = (Spinner) findViewById(R.id.drop_ProMeasureUnit);
        dropProShape = (Spinner) findViewById(R.id.drop_ProShape);
        dropProPack = (Spinner) findViewById(R.id.dropProPack);
        dropUseProMeasureUnit=(Spinner)findViewById(R.id.dropUseProMeasureUnit);

        editUseProMode=(EditText)findViewById(R.id.editProModel);
        editUseProName=(EditText)findViewById(R.id.editProName);
        editUseProNumber=(EditText)findViewById(R.id.editProNumber);
        editUseProSpec=(EditText)findViewById(R.id.editProSpec);



        InitForm();

        //返回
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //重置
        btnDelete = (Button) findViewById(R.id.btnDeleteAll);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitForm();
            }
        });

        //保存
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long comId = ((App) getApplication()).getSysComID();
                long userId = ((App) getApplication()).getCompanyUserID();
                long comBrId = ((App) getApplication()).getSysComBrID();

                ProduceRec_Model mo = new ProduceRec_Model();
                mo.setRecNumber(editRecNo.getText().toString());
                mo.setCreateComCusID(userId);
                mo.setCreateComBrID(comBrId);
                mo.setCreateComID(comId);
                mo.setCreateIp("");

                mo.setUseProSpec(editUseProSpec.getText().toString());
                mo.setUseProModel(editUseProMode.getText().toString());
                mo.setUseProName(editUseProName.getText().toString());
                mo.setUseProNumber(editUseProNumber.getText().toString());


                NameToValue mapProCategory = (NameToValue) dropCategoryzl.getSelectedItem();
                mo.setProCategory(Convert.ToInt64(mapProCategory.InfoValue.toString()));
                mo.setProCategoryName(mapProCategory.InfoName.toString().trim().replace(">>", ""));
                mo.setProDangerComponent(editProDangerComponent.getText().toString().trim());


                // HashMap<String, Object> mapProHazardNature=(HashMap<String, Object>)drop.getSelectedItem();
                String natureStr = "";
                List<String> listSelect = customCheckBox.getSelectedBoxContents();
                for (int i = 0; i < listSelect.size(); i++) {
                    natureStr += listSelect.get(i) + ",";
                }
                if (!TextUtils.isEmpty(natureStr)) {
                    natureStr = natureStr.substring(0, natureStr.length() - 1);
                }
                mo.setProHazardNature(natureStr.trim());

                NameToValue mapProMeasureUnit = (NameToValue) dropProMeasureUnit.getSelectedItem();
                mo.setProMeasureUnitName(mapProMeasureUnit.InfoName.toString());


                mo.setProNumber(editProNumber.getText().toString());

                NameToValue mapProPack = (NameToValue) dropProPack.getSelectedItem();
                mo.setProPackName(mapProPack.InfoName);


                NameToValue mapProShape = (NameToValue) dropProShape.getSelectedItem();

                mo.setProShape(Convert.ToInt64(mapProShape.InfoValue.toString()));
                mo.setProShapeName(mapProShape.InfoName.toString());
                mo.setSysComID(comId);
                mo.setRemark(editRemark.getText().toString());
                mo.setRecSource(3);
                NameToValue mapUseUnit = (NameToValue) dropUseProMeasureUnit.getSelectedItem();
                mo.setUseProMeasureUnitName(mapUseUnit.InfoName.toString());


                String mess = "";
                String successStr = "添加成功";
                if (TextUtils.isEmpty(editRecNo.getText())) {
                    mess = ProdRec_BLL.ProdRec_Add(mo);
                    FormCatchUtil.setObjectToShare(ProdRecAdd.this,mo,mo.getProCategory()+"");
                } else {
                    successStr = "修改成功";
                    mess = ProdRec_BLL.ProdRec_Update(mo);
                }

                if (TextUtils.isEmpty(mess)) {
                    MsgBox.Show(ProdRecAdd.this, successStr);
                    InitForm();
                } else {
                    MsgBox.Show(ProdRecAdd.this, mess);
                }


            }
        });


    }

    /**
     * 初始化窗体
     */
    public void InitForm() {
        TitleSet.SetTitle(this,11);
        long comId = ((App) getApplication()).getSysComID();
        listProHazardNature = SysPublic_BLL.GetProduct_HazardNature();
        if (isFirstLoad) {

            if (listProHazardNature != null && listProHazardNature.size() > 0) {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < listProHazardNature.size(); i++) {
                    list.add(listProHazardNature.get(i).InfoName);
                }

                customCheckBox = new CustomCheckBox(this);
                customCheckBox.setCheckBoxs(list);

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layNature);
                linearLayout.addView(customCheckBox);
            }
        } else {
            customCheckBox.UnCheck();
        }

        //计量单位
        listProMeasureUnit = SysPublic_BLL.GetProduct_MeasureUnit();
        ArrayAdapter<NameToValue> arrayAdapterUnit = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listProMeasureUnit);
        dropProMeasureUnit.setAdapter(arrayAdapterUnit);


        ArrayAdapter<NameToValue> arrayAdapterUseUnit = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listProMeasureUnit);
        dropUseProMeasureUnit.setAdapter(arrayAdapterUseUnit);

        //包装
        listProPack=SysPublic_BLL.GetProduct_Pack();
        ArrayAdapter<NameToValue> arrayAdapterProPack = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listProPack);
        dropProPack.setAdapter(arrayAdapterProPack);

        //危废形态
        listProShape = SysPublic_BLL.GetProduct_Shape();
        ArrayAdapter<NameToValue> arrayAdapterShape = new ArrayAdapter<NameToValue>(this, android.R.layout.simple_spinner_item, listProShape);
        dropProShape.setAdapter(arrayAdapterShape);

        //危废种类
        listCategory = Category_BLL.GetProductCategory(comId);
        MapCategory=SysPublic_BLL.formatCategory(listCategory);
        final ArrayAdapter<NameToValue> arrayAdapterCategory=new ArrayAdapter<NameToValue>(this,android.R.layout.simple_spinner_item,MapCategory.get("0"));

        dropCategorydl.setAdapter(arrayAdapterCategory);
        dropCategorydl.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                ;//文本说明
                dropCategorydlid=arrayAdapterCategory.getItem(arg2).InfoValue;
                ArrayAdapter<NameToValue> arrayAdapterCategoryzl=new ArrayAdapter<NameToValue>(ProdRecAdd.this,android.R.layout.simple_spinner_item,MapCategory.get(dropCategorydlid));
                dropCategoryzl.setAdapter(arrayAdapterCategoryzl);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        dropCategoryzl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String zlid=MapCategory.get(dropCategorydlid).get(position).InfoValue;
                    long code=Convert.ToInt64(zlid);
                    String natrueStr=Category_BLL.GetHazardNature(code);
                    if(!TextUtils.isEmpty(natrueStr)) {
                        customCheckBox.Check(natrueStr.split(","));
                    }
                if(formatForm((ProduceRec_Model) FormCatchUtil.getObjectFromShare(ProdRecAdd.this,zlid))){
                    MsgBox.Show(ProdRecAdd.this, "已加载最后一次录入信息！请修改！");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editRemark.setText("");
        editProDangerComponent.setText("");

        editProNumber.setText("");
        editRecNo.setText("");

        editRecNo.setEnabled(false);

        Intent intent = getIntent();
        String recNo = intent.getStringExtra("RecNo");
        System.out.println("RecNo");
        //System.out.println(recNo);
        if (!TextUtils.isEmpty(recNo)&&isFirstLoad) {

            TitleSet.SetTitle(this,12);
            System.out.println(recNo);
            ProduceRec_Model model = ProdRec_BLL.LoadData(recNo);
            if(model.IsExist()) {
                formatForm(model);
            }

        }

        isFirstLoad = false;
    }

    private boolean formatForm(ProduceRec_Model model){
        if(model==null)return false;
        editRecNo.setText(model.getRecNumber());
        editProDangerComponent.setText(model.getProDangerComponent());
        editProNumber.setText(model.getProNumber());

        editUseProSpec.setText(model.getUseProSpec());
        editUseProNumber.setText(model.getUseProNumber());
        editUseProMode.setText(model.getUseProModel());
        editUseProName.setText(model.getUseProName());

        editRemark.setText(model.getRemark());


        customCheckBox.Check(model.getProHazardNature().split(","));


        String dlid="";
        for (int i = 0; i < MapCategory.get("0").size(); i++) {
            if (Convert.ToString(model.getProCategory()).startsWith( MapCategory.get("0").get(i).InfoValue)) {
                dlid=MapCategory.get("0").get(i).InfoValue;
                dropCategorydl.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < MapCategory.get(dlid).size(); i++) {
            if (MapCategory.get(dlid).get(i).InfoValue.equals(Convert.ToString(model.getProCategory()))) {
                dlid=MapCategory.get(dlid).get(i).InfoValue;
                dropCategoryzl.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < listProPack.size(); i++) {
            if (listProPack.get(i).InfoName.equals(model.getProPackName())) {
                dropProPack.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < listProMeasureUnit.size(); i++) {
            if (listProMeasureUnit.get(i).InfoName.equals(model.getProMeasureUnitName())) {
                dropProMeasureUnit.setSelection(i);
                break;
            }
        }

        ArrayAdapter<NameToValue> adaterUseUnit = (ArrayAdapter<NameToValue>) dropUseProMeasureUnit.getAdapter();
        for (int i = 0; i < adaterUseUnit.getCount(); i++) {
            if (adaterUseUnit.getItem(i).InfoName.equals(model.getUseProMeasureUnitName())) {
                dropUseProMeasureUnit.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < listProShape.size(); i++) {
            if (listProShape.get(i).InfoValue.equals(Convert.ToString(model.getProShape()))) {
                dropProShape.setSelection(i);
                break;
            }
        }
        return true;
    }
}

