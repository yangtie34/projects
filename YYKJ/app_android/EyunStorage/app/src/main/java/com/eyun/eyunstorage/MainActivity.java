package com.eyun.eyunstorage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.eyun.framework.angular.core.AngularActivity;
import com.eyun.framework.angular.core.AppContext;
import com.eyun.eyunstorage.activitys.Login;
import com.eyun.eyunstorage.cusviews.CusSDDListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eyun.framework.util.android.ViewUtil.alert;

public class MainActivity extends AngularActivity {
    private CusSDDListView dlistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* AppContext.intent(InRecScan.class);
*//*        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()

                .detectDiskReads().detectDiskWrites().detectNetwork()

                .penaltyLog().build());*//*
        setContentView(R.layout.activity_main);
        dlistView= (CusSDDListView) findViewById(R.id.droplistview);
        List<String> list=new LinkedList<String>();
       *//* scope.key("list").push("str0");
        scope.key("list").push("str1");
        scope.key("list").push("str2");
        scope.key("list").push("str3");
        scope.key("list").push("str4");*//*
        //scope.key("list").val(list);
        scope.key("str123").val("44464654654564");
        scope.key("str124").val("44464654654564");
        initListView();*/
        AppContext.intent(Login.class);
     /*  new Thread(new Runnable() {
           @Override
           public void run() {
               final Object aaa= BaseDao.getInstance().queryForString(ServerConfig.JDBC.validationQuery,new Object[]{});
               alert(aaa.toString());

           }
       }).start();*/


    }

    private void initListView() {
        CusSDDListView.QueryForList queryForList=new CusSDDListView.QueryForList() {
            @Override
            public List<Map<String, Object>> query(String Condition, int currentPage, int numPerPage) {
                return getData(Condition,currentPage,numPerPage);
            }

            @Override
            public View data2view(Map<String, Object> map) {
                return getView(map);
            }

            @Override
            public String getId(Map<String, Object> map) {
                return map.get("id").toString();
            }

            @Override
            public String getCondition() {
                return null;
            }
        };
        dlistView.setQueryForList(queryForList);
        dlistView.setOnRightItemClickListener(new CusSDDListView.onRightItemClickListener() {
            @Override
            public boolean onRightItemClick(View v, String dataId) {
                alert(dataId);
                return true;
            }
        });
    }

    private TextView getView(Map<String, Object> map){
        TextView textview= (TextView) LayoutInflater.from(this).inflate(android.R.layout.simple_expandable_list_item_1,null);
        textview.setText(map.get("name").toString());
        return textview;
    };

    public void onclick(View view) {
       // scope.key("list").push("str"+scope.key("list").size());
        scope.key("str124").val("4446465464564");

        alert(scope.key("str").toJson());
    }

    private List<Map<String, Object>> getData(String Condition, int currentPage, int numPerPage){
        List<Map<String, Object>> list=new ArrayList<>();
        if(currentPage>3)return list;
        for (int i = 1; i <=numPerPage ; i++) {
            Map<String, Object> map=new HashMap<>();
            map.put("id","第"+currentPage+"页-第"+i+"条");
            map.put("name","第"+currentPage+"页-第"+i+"条");
            list.add(map);
        }

        return list;
    }

}
