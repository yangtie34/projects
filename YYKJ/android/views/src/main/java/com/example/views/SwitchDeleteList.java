package com.example.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.angular.core.ViewParent;
import com.chengyi.android.util.CSS;
import com.example.baseView.swipeDelete.SwipeAdapter;
import com.example.baseView.swipeDelete.SwipeListView;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/2/15.
 */

public class SwitchDeleteList extends ViewParent {
        private OnRightItemClickListener onRightItemClickListener;
            SwipeAdapter mAdapter;
        public SwitchDeleteList(Scope parent, String Data) {
            super(parent);
            setData(Data);

            init();
        }
        public SwitchDeleteList(Context context, AttributeSet attr) {
            super(context, attr);
        }
        @Override
        protected void init() {
            SwipeListView swipeListView=new SwipeListView(scope.activity);
            swipeListView.setLayoutParams(CSS.LinearLayoutParams.matchAll());
            swipeListView.setDivider(new ColorDrawable(Color.parseColor("#dddbdb")));
            swipeListView.setDividerHeight(1);
            this.addView(swipeListView);
            this.setBackgroundColor(getResources().getColor(R.color.black));
            mAdapter = new SwipeAdapter(scope.activity,(List<View>) scope.key(getData()).val(),swipeListView.getRightViewWidth());
            swipeListView.setAdapter(mAdapter);

        }

    public void setOnRightItemClickListener(OnRightItemClickListener onRightItemClickListener) {
        this.onRightItemClickListener = onRightItemClickListener;
        mAdapter.setOnRightItemClickListener(new SwipeAdapter.onRightItemClickListener() {

            @Override
            public void onRightItemClick(View v, int position) {

                SwitchDeleteList.this.onRightItemClickListener.onRightItemClick(v,position);
            }
        });
    }

    public interface OnRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}
