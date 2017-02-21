package com.eyunsoft.app_wasteoilCostomer;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */

public class ListViewTestAdapter extends BaseAdapter {

    public List<HashMap<String, Object>> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    private int index = -1;

    public ListViewTestAdapter(Context context, List<HashMap<String, Object>> data) {
        this.mContext = context;
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////getView()方法是重点!!!!!!///////////////////////
    //////////////////////////////////////////////////////////////////////////
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listviewlayout_handover, null);
            holder.txtProName = (TextView) convertView.findViewById(R.id.txtProCategoryName);
            holder.txtProNumber = (TextView) convertView.findViewById(R.id.txtProNumber);
            holder.txtUnit = (TextView) convertView.findViewById(R.id.txtProMeasureUnitName);
            holder.editAcutalNumber = (EditText) convertView.findViewById(R.id.editActualProNumber);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        EditText editText = holder.editAcutalNumber;

        //使listview中的edittext获取焦点
        editText.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // 在TOUCH的UP事件中，要保存当前的行下标，因为弹出软键盘后，整个画面会被重画
                // 在getView方法的最后，要根据index和当前的行下标手动为EditText设置焦点
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index = position;
                }
                return false;
            }
        });

        //以下方法为edittext进行数据的保存
        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence text, int start, int before, int count) {
                //如果该edittext有默认内容，还要在if那里进行过滤
                if (index>=0 && text.length() > 0 && index == position && !text.toString().equals("默认内容")) {
                    mData.get(index).put("ActualProNumber", text.toString());
                }
            }

        });
        holder.txtProName.setText(mData.get(position).get("ProCategoryName").toString());
        holder.txtUnit.setText(mData.get(position).get("ProMeasureUnitName").toString());
        holder.txtProNumber.setText(mData.get(position).get("ProNumber").toString());
       // holder.editText.setText(mData.get(position).get("ProNumber").toString());

        editText.clearFocus();

        if (index != -1 && index == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            editText.requestFocus();
        }

        final ViewHolder finalHolder = holder;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, finalHolder.txtProName.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public final class ViewHolder {
        public TextView txtProName;
        public TextView txtProNumber;
        public TextView txtUnit;
        public EditText editAcutalNumber;
    }
}