
package com.example.baseView.swipeDelete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.views.R;

import java.util.List;

public class SwipeAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext = null;
    private List<View> data;
    
    private int mRightWidth = 0;

    /**
     * @param
     */
    public SwipeAdapter(Context ctx, List<View> data, int rightWidth) {
        mContext = ctx;
        this.data = data;
        mRightWidth = rightWidth;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_delete_item, parent, false);
            holder = new ViewHolder();
            holder.item_left = (RelativeLayout)convertView.findViewById(R.id.item_left);
            holder.item_right = (RelativeLayout)convertView.findViewById(R.id.item_right);


            holder.item_right_txt = (TextView)convertView.findViewById(R.id.item_right_txt);
            convertView.setTag(holder);
        } else {// 有直接获得ViewHolder

            holder = (ViewHolder)convertView.getTag(); return convertView;
        }

        LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        holder.item_left.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
        holder.item_right.setLayoutParams(lp2);

        View view = data.get(position);
        holder.item_left.addView(view);

        holder.item_right.setOnClickListener(new RightOnclick(position));
        return convertView;
    }
    class RightOnclick implements View.OnClickListener{
        private int position;
        public  RightOnclick(int position){
            this.position=position;
        }
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onRightItemClick(v, position);
            }
        }
    }
    static class ViewHolder {
    	RelativeLayout item_left;
    	RelativeLayout item_right;

        TextView item_right_txt;
    }
    
    /**
     * 单击事件监听器
     */
    private onRightItemClickListener mListener = null;
    
    public void setOnRightItemClickListener(onRightItemClickListener listener){
    	mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}
