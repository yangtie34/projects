package com.eyunsoft.app_wasteoilCostomer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eyunsoft.app_wasteoilCostomer.Model.CompanyCustomerApply_Model;
import com.eyunsoft.app_wasteoilCostomer.Publics.MsgBox;

import static com.eyunsoft.app_wasteoilCostomer.Register3.model;

/**
 * Created by Administrator on 2017/2/24.
 */

public class Register1 extends Activity {

    private EditText ContactName;
    private EditText ContactMobile;
    private EditText ContactEmail;
    //private EditText ContactArea;
    private EditText ContactAddress;
    //private EditText WeiXinNumber;
    private EditText ComFullName;
    private RadioGroup BusinessLicenseTypeRadioGroup;
    private RadioButton BusinessLicenseType;
    private EditText BusinessLicenseNumber;
    private LinearLayout OrganizationCodeNumberloyout;
    //    private EditText BusinessLicenseImg;
    private EditText BusinessLicenseAddress;
    private EditText BusinessScope;
    private EditText LegalRepresentative;
    private EditText OrganizationCodeNumber;
//    private EditText OrganizationCodeImg;
//    private EditText DangerWasteName;
//    private EditText DangerWasteNumber;
//    private EditText DangerWasteLegalPerson;
//    private EditText DangerWasteAddress;
//    private EditText DangerWasteImg;
//    private EditText DangerWasteBusinessCategory;
//    private EditText DangerWasteBusinessMode;
//    private EditText DangerWasteBusinessScale;
//    private EditText DangerWasteValidityTimeStart;
//    private EditText DangerWasteValidityTimeEnd;
//    private EditText AuditState;
//    private EditText CreateIp;
//    private EditText CreateTime;

    private TextView next;
    /**
     * 是否应该隐藏输入法
     * @param v
     * @param event
     * @return
     */
    public static  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        ContactName = (EditText) findViewById(R.id.ContactName);
        ContactMobile = (EditText) findViewById(R.id.ContactMobile);
        ContactEmail = (EditText) findViewById(R.id.ContactEmail);
        ContactAddress = (EditText) findViewById(R.id.ContactAddress);
        ComFullName = (EditText) findViewById(R.id.ComFullName);
        BusinessLicenseTypeRadioGroup = (RadioGroup) findViewById(R.id.BusinessLicenseType);
        BusinessLicenseNumber = (EditText) findViewById(R.id.BusinessLicenseNumber);
        OrganizationCodeNumberloyout= (LinearLayout) findViewById(R.id.OrganizationCodeNumberloyout);
        BusinessLicenseAddress = (EditText) findViewById(R.id.BusinessLicenseAddress);
        BusinessScope = (EditText) findViewById(R.id.BusinessScope);
        LegalRepresentative = (EditText) findViewById(R.id.LegalRepresentative);
        OrganizationCodeNumber = (EditText) findViewById(R.id.OrganizationCodeNumber);

        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register1.this.next();
            }
        });
        BusinessLicenseType = (RadioButton) Register1.this.findViewById(R.id.BusinessLicenseType1);
        //绑定一个匿名监听器
        BusinessLicenseTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                BusinessLicenseType = (RadioButton) Register1.this.findViewById(radioButtonId);
                if(radioButtonId!= R.id.BusinessLicenseType1){
                    OrganizationCodeNumberloyout.setVisibility(View.GONE);
                }else{
                    OrganizationCodeNumberloyout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void next() {
        CompanyCustomerApply_Model model = Register3.model;
        String strContactName = ContactName.getText().toString();
        String strContactMobile = ContactMobile.getText().toString();
        String strContactEmail = ContactEmail.getText().toString();
        String strContactAddress = ContactAddress.getText().toString();
        String strComFullName = ComFullName.getText().toString();
        //String strBusinessLicenseType = BusinessLicenseType.getText().toString();
        String strBusinessLicenseNumber = BusinessLicenseNumber.getText().toString();
        String strBusinessLicenseAddress = BusinessLicenseAddress.getText().toString();
        String strBusinessScope = BusinessScope.getText().toString();
        String strLegalRepresentative = LegalRepresentative.getText().toString();
        String strOrganizationCodeNumber = OrganizationCodeNumber.getText().toString();

        if (strContactName.length() == 0) {
            MsgBox.Show(Register1.this, "申请人不能为空！");
        } else {
            model.setContactName(strContactName);
        }
        if (strContactMobile.length() == 0) {
            MsgBox.Show(Register1.this, "联系手机不能为空！");
        } else {
            model.setContactMobile(strContactMobile);
        }
        if (strContactEmail.length() == 0) {
            MsgBox.Show(Register1.this, "电子邮箱不能为空！");
        } else {
            model.setContactEmail(strContactEmail);
        }
        if (strContactAddress.length() == 0) {
            MsgBox.Show(Register1.this, "联系地址不能为空！");
        } else {
            model.setContactAddress(strContactAddress);
        }
        if (strComFullName.length() == 0) {
            MsgBox.Show(Register1.this, "企业全称不能为空！");
        } else {
            model.setComFullName(strComFullName);
        }
        if (BusinessLicenseType.getId() == R.id.BusinessLicenseType1) {
            model.setBusinessLicenseType(1);
        } else if (BusinessLicenseType.getId() == R.id.BusinessLicenseType2) {
            model.setBusinessLicenseType(2);
        } else if (BusinessLicenseType.getId() == R.id.BusinessLicenseType3) {
            model.setBusinessLicenseType(3);
        }
        if (strBusinessLicenseNumber.length() == 0) {
            MsgBox.Show(Register1.this, "工商执照注册号不能为空！");
        } else {
            model.setBusinessLicenseNumber(strBusinessLicenseNumber);
        }
        if (strBusinessLicenseAddress.length() == 0) {
            MsgBox.Show(Register1.this, "工商营业执照地址不能为空！");
        } else {
            model.setBusinessLicenseAddress(strBusinessLicenseAddress);
        }
        if (strBusinessScope.length() == 0) {
            MsgBox.Show(Register1.this, "经营范围不能为空！");
        } else {
            model.setBusinessScope(strBusinessScope);
        }
        if (strLegalRepresentative.length() == 0) {
            MsgBox.Show(Register1.this, "法定代表人不能为空！");
        } else {
            model.setLegalRepresentative(strLegalRepresentative);
        }
        if (strOrganizationCodeNumber.length() == 0&&BusinessLicenseType.getId() == R.id.BusinessLicenseType1) {
            MsgBox.Show(Register1.this, "组织机构代码不能为空！");
        } else {
            model.setOrganizationCodeNumber(strOrganizationCodeNumber);
        }
        Intent intent=new Intent(this,Register2.class);
        startActivity(intent);
    }


}
