
package com.eyun.framework.moduleview.listViw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.eyun.customXghScan.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeDropDownAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    private List<View> data = new ArrayList<>();
    private List<View> convertViews = new ArrayList<>();
    private int mRightWidth = 0;

    /**
     * @param
     */
    public SwipeDropDownAdapter(Context ctx, List<View> data, int rightWidth) {
        mContext = ctx;
        if (data != null) this.data = data;
        mRightWidth = rightWidth;
    }

    public void addListData(List<View> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void setListData(List<View> list) {
        data = list;
        convertViews.clear();
        notifyDataSetChanged();
    }

    public void removeByIndex(int index) {
        data.remove(index);
        convertViews.remove(index);
        notifyDataSetChanged();
    }
    public void clearListData() {
        data.clear();
        convertViews.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertViews.size()<=position) {
            View view = data.get(position);

            convertView = LayoutInflater.from(mContext).inflate(R.layout.base_list_delete_item, parent, false);
            holder = new ViewHolder();
            holder.item_left = (RelativeLayout) convertView.findViewById(R.id.item_left);
            holder.item_right = (RelativeLayout) convertView.findViewById(R.id.item_right);


            holder.item_right_txt = (TextView) convertView.findViewById(R.id.item_right_txt);
            convertView.setTag(holder);


            LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            holder.item_left.setLayoutParams(lp1);
            LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
            holder.item_right.setLayoutParams(lp2);


            holder.item_left.addView(view);

            holder.setRightClick(new RightOnclick(position));
            convertViews.add(convertView);
            return convertView;
        } else {// 有直接获得ViewHolder
           holder = (ViewHolder) convertViews.get(position).getTag();
            holder.getRightOnclick().setPosition(position);
            return convertViews.get(position);
        }
    }

    class RightOnclick implements OnClickListener {
        private int position;

        public RightOnclick(int position) {
            this.position=position;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onRightItemClick(v, position);
            }
        }
        void  setPosition(int position){
            this.position=position;
        }
    }

    static class ViewHolder {
        RelativeLayout item_left;
        RelativeLayout item_right;
        TextView item_right_txt;
        private RightOnclick rightOnclick;
        void setRightClick(RightOnclick rightOnclick){
            this.rightOnclick=rightOnclick;
            item_right.setOnClickListener(rightOnclick);
        }

        public RightOnclick getRightOnclick() {
            return rightOnclick;
        }
    }

    /**
     * 单击事件监听器
     */
    private onRightItemClickListener mListener = null;

    public void setOnRightItemClickListener(onRightItemClickListener listener) {
        mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}
