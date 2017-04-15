package com.eyun.jybStorageScan.cusviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.eyun.framework.Constant;
import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.moduleview.listViw.DropDownListView;
import com.eyun.framework.moduleview.listViw.SwipeDropDownAdapter;
import com.eyun.framework.moduleview.listViw.SwipeDropDownListView;
import com.eyun.framework.util.CallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *    列表数据有分页删除加载使用
 */

public class CusSDDListView extends AngularViewParent {

    private SwipeDropDownListView swipeDropDownListView;

    private SwipeDropDownAdapter swipeDropDownAdapter;

    private QueryForList queryForList;
    private List<Map<String,Object>> listData;
    private List<View> listResult;

    private int currentPage=1;//当前页
    private int numPerPage=10;//每页显示几条
    private int totalRows=0;  //总条数

    public CusSDDListView(Scope parent) {
        super(parent);
        init();
    }

    public CusSDDListView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    protected void init() {
        swipeDropDownListView =new SwipeDropDownListView(Scope.activity);
        swipeDropDownListViewOption();
        swipeDropDownAdapter = new SwipeDropDownAdapter(scope.activity,null,swipeDropDownListView.getRightViewWidth());
        swipeDropDownListView.setAdapter(swipeDropDownAdapter);
        this.addView(swipeDropDownListView);
        swipeDropDownListView.setVisibility(INVISIBLE);
    }

    public void setQueryForList(QueryForList queryForList) {
            scope.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    swipeDropDownListView.setVisibility(VISIBLE);
                }
            });
        this.queryForList = queryForList;
        initListData();
    }

    private List<View> getViews(List<Map<String,Object>> list){
        List<View> listViews=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            listViews.add(queryForList.data2view(list.get(i)));
        }
        return listViews;
    }
    private void initPageInfo(){
        currentPage=1;
        numPerPage=10;
    }
    /**
     * 外部调用查询方法
     */
    public void  initListData(){
        initPageInfo();
        scope.forThread(new CallBack<List<Map<String,Object>>>() {
            @Override
            public List<Map<String,Object>> run() {
                return queryForList.query(queryForList.getCondition(),currentPage,numPerPage);
            }
        }, new DataListener<List<Map<String,Object>>>() {
            @Override
            public void hasChange(List<Map<String, Object>> l) {
                setData(l);
                swipeDropDownListView.onDropDownComplete();
                swipeDropDownListView.onBottomComplete();
            }
        });
    }
    private void  addNextPageData(){
        currentPage++;
        scope.forThread(new CallBack<List<Map<String,Object>>>() {
            @Override
            public List<Map<String,Object>> run() {
                return queryForList.query(queryForList.getCondition(),currentPage,numPerPage);
            }
        }, new DataListener<List<Map<String,Object>>>() {
            @Override
            public void hasChange(List<Map<String, Object>> l) {
                addData(l);
                swipeDropDownListView.onBottomComplete();
            }
        });
    }
    private void setData(List<Map<String,Object>> listData){
        if(listData.size()<numPerPage){
            swipeDropDownListView.setHasMore(false);
        }else{
            swipeDropDownListView.setHasMore(true);
        }
        this.listData=listData;
        listResult=getViews(listData);
        swipeDropDownAdapter.setListData(listResult);
//        swipeDropDownAdapter = new SwipeDropDownAdapter(scope.activity,listResult,swipeDropDownListView.getRightViewWidth());
//        swipeDropDownListView.setAdapter(swipeDropDownAdapter);

    }
    private void addData(List<Map<String,Object>> listData){
        if(listData.size()<numPerPage){
            swipeDropDownListView.setHasMore(false);
        }else{
            swipeDropDownListView.setHasMore(true);
        }
        this.listData.addAll(listData);
        List<View> listResultAdd=getViews(listData);
        //listResult.addAll(listResultAdd);
        swipeDropDownAdapter.addListData(listResultAdd);
    }
    private void removeByIndex(int index){
        swipeDropDownAdapter.removeByIndex(index);
        listData.remove(index);
    }
    public interface QueryForList{
        /**
         * 查询数据方法
         * @param Condition
         * @param currentPage
         * @param numPerPage
         * @return
         */
        List<Map<String,Object>> query(String Condition, int currentPage, int numPerPage);

        /**
         * 数据转成view
         * @param map
         * @return
         */
        View data2view(Map<String,Object> map);

        /**
         * 获取一条数据的id
         * @param map
         * @return
         */
        String getId(Map<String,Object> map);

        /**
         * 获取查询数据
         * @return
         */
        String getCondition();
    }

    private void swipeDropDownListViewOption() {

        swipeDropDownListView.setLayoutParams(Constant.layoutParamsMATCH);
        swipeDropDownListView.setDivider(new ColorDrawable(Color.parseColor("#dddbdb")));
        swipeDropDownListView.setDividerHeight(1);
        //是否允许左滑删除
        swipeDropDownListView.setMoveEnable(false);
        //允许使用下拉样式
        swipeDropDownListView.setDropDownStyle(false);
        //允许使用底部样式
        swipeDropDownListView.setOnBottomStyle(false);
        //是否允许滚动到底部之后自动执行底部的事件
        swipeDropDownListView.setAutoLoadOnBottom(true);
        //初次加载时,隐藏底部
        swipeDropDownListView.setAutoLoadOnBottom(false);//hideBottom();
        //swipeDropDownListView.isAutoLoadOnBottom();
        //设置下拉的比例与实际下拉的比例
        swipeDropDownListView.setHeaderPaddingTopRate(2);
        //设置头部的默认文字
        swipeDropDownListView.setHeaderDefaultText("下拉可以刷新");
        //头部释放过后的文字
        swipeDropDownListView.setHeaderReleaseText("释放");
        //头部加载过程中的文字
        swipeDropDownListView.setHeaderLoadingText("我正在努力加载");

        swipeDropDownListView.setHeaderPullText("我正在下拉.");

        //设置底部的默认文字
        swipeDropDownListView.setFooterDefaultText("上拉加载数据");

        swipeDropDownListView.setFooterNoMoreText("没有更多的数据了");
        setOnDropDownListener();
        setOnBottomListener();
    }
    public interface onRightItemClickListener{
        /**
         * 左滑右边点击事件
         * @param v
         * @param dataId 数据的id
         */
        boolean onRightItemClick(View v , String dataId);
    }
/*    public void removeView(int index){
        swipeDropDownAdapter.removeByIndex(index);
    }*/
    public void setOnRightItemClickListener(final onRightItemClickListener listener){
        //是否允许左滑事件
        swipeDropDownListView.setMoveEnable(true);
        swipeDropDownAdapter.setOnRightItemClickListener(new SwipeDropDownAdapter.onRightItemClickListener() {
            @Override
            public void onRightItemClick(View v, int position) {
                if(listener.onRightItemClick(v,queryForList.getId(listData.get(position)))){
                   removeByIndex(position);
                };
            }
        });
    }
    //设置下拉的处理事件
    private void setOnDropDownListener(){
        //允许使用下拉样式
        swipeDropDownListView.setDropDownStyle(true);
        //设置下拉的处理事件
        swipeDropDownListView.setOnDropDownListener(new DropDownListView.OnDropDownListener() {

            @Override
            public void onDropDown() {
                swipeDropDownListView.onDropDown();//显示加载中

                 initListData();


            }
        });
    }
    public void noDropDownStyle(){
        scope.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeDropDownListView.setDropDownStyle(false);
            }
        });
    };
    public void noOnBottomStyle(){
        scope.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeDropDownListView.setOnBottomStyle(false);
            }
        });
    };
    //设置底部的处理事件
    private void setOnBottomListener(){
        //允许使用底部样式
        swipeDropDownListView.setOnBottomStyle(true);
       //swipeDropDownListView.setAutoLoadOnBottom(true);
        swipeDropDownListView.setOnBottomListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置底部显现
                swipeDropDownListView.onBottomBegin();//显示加载中
               // SystemClock.sleep(2000);
                addNextPageData();


            }
        });
    }
}
