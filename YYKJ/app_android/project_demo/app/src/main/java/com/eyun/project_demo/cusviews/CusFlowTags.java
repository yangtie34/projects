package com.eyun.project_demo.cusviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.entity.NodeEntity;
import com.eyun.framework.moduleview.flowLayout.CommonAdapter;
import com.eyun.framework.moduleview.flowLayout.FlowLayout;
import com.eyun.framework.moduleview.flowLayout.TagAdapter;
import com.eyun.framework.moduleview.flowLayout.TagFlowLayout;
import com.eyun.framework.moduleview.flowLayout.ViewHolder;
import com.eyun.project_demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * data:List<NodeEntity> or List<List<NodeEntity>>
 *  params:最多选择几个
 */

public class CusFlowTags extends AngularViewParent {
    private TagFlowLayout mFlowLayout;
    private TagAdapter<String> mAdapter ;
    private int maxSelect=-1;
    private boolean isListView=false;
    private Set<Integer> selectedList= new HashSet<Integer>();
    private Map<Integer, Set<Integer>> selectedMap = new HashMap<Integer, Set<Integer>>();

    private ListView mListView;
    public CusFlowTags(Scope parent, String Data, String return_) {
        super(parent);
        setData(Data);
        setParams(String.valueOf(maxSelect));//默认多选
        setReturn(return_);
        init();
    }
    public CusFlowTags(Scope parent, String Data, int maxSelect, String return_) {
        super(parent);
        this.maxSelect=maxSelect;
        setData(Data);
        setParams(String.valueOf(maxSelect));
        setReturn(return_);
        init();
    }
    public CusFlowTags(Context context, AttributeSet attr) {
        super(context, attr);
    }
    @Override
    protected void init() {
        final LayoutInflater mInflater = LayoutInflater.from(scope.activity);
        scope.key(getData()).watch(new DataListener<List<Object>>() {
            @Override
            public void hasChange(final List<Object> l) {
                if(l.size()>0&&l.get(0)instanceof List){//listView
                    if(CusFlowTags.this.getChildCount()==0){
                        View flowView=scope.inflate(R.layout.base_listview);
                        mListView = (ListView) flowView.findViewById(R.id.id_listview);

                        CusFlowTags.this.addView(flowView);
                    }
                    isListView=true;
                     List<List<String>> mDatas = new ArrayList<List<String>>();
                    for (int i = 0; i <l.size() ; i++) {
                        List<NodeEntity> list= (List<NodeEntity>) l.get(i);
                        for(int j=0;j<list.size(); j++){
                            NodeEntity ne= (NodeEntity) l.get(i);
                            mDatas.get(i).add(ne.getName());
                            if(ne.isCheck()){
                                selectedMap.get(i).add(j);
                            }
                        }
                    }
                    mListView.setAdapter(new CommonAdapter<List<String>>(scope.activity, R.layout.base_flowtag_for_listview, mDatas)
                    {

                        @Override
                        public void convert(final ViewHolder viewHolder, List<String> strings)
                        {
                            TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.id_tagflowlayout);

                            TagAdapter<String> tagAdapter = new TagAdapter<String>(strings)
                            {
                                @Override
                                public View getView(FlowLayout parent, int position, String o)
                                {
                                    //can use viewholder
                                    TextView tv = (TextView) mInflater.inflate(R.layout.base_tag,
                                            parent, false);
                                    tv.setText(o);
                                    return tv;
                                }
                            };
                            tagFlowLayout.setAdapter(tagAdapter);
                            //重置状态
                            tagAdapter.setSelectedList(selectedMap.get(viewHolder.getItemPosition()));

                            tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener()
                            {
                                @Override
                                public void onSelected(Set<Integer> selectPosSet)
                                {
                                    List<List<NodeEntity>> lists = (List<List<NodeEntity>>) scope.key(getData()).val();
                                    List<NodeEntity> list =lists.get(viewHolder.getItemPosition());
                                    List<List<NodeEntity>> listsResult =(List<List<NodeEntity>>) scope.key(getReturn()).val();
                                      List<NodeEntity> listResult = new ArrayList<NodeEntity>();
                                    for (Integer i : selectPosSet) {
                                        NodeEntity ne = list.get(i);
                                        ne.setCheck(true);
                                        listResult.add(ne);
                                    }
                                    for (int i = 0; i < list.size(); i++) {
                                        NodeEntity ne = list.get(i);
                                        if (!listResult.contains(ne)) {
                                            ne.setCheck(false);
                                        }
                                    }
                                    if (maxSelect == 1) {
                                        setReturn(listResult.get(0));
                                    } else {
                                        listsResult.set(viewHolder.getItemPosition(),listResult);
                                        setReturn(listsResult);
                                    }
                                    //selectedMap.put(viewHolder.getItemPosition(), selectPosSet);
                                }
                            });
                        }
                    });
                }else{
                    if(CusFlowTags.this.getChildCount()==0){
                        View flowView =scope.inflate(R.layout.cus_flow_tag);
                        mFlowLayout = (TagFlowLayout) flowView.findViewById(R.id.id_flowlayout);
                        mFlowLayout.setMaxSelectCount(Integer.parseInt(getParams()));
                        CusFlowTags.this.addView(flowView);
                    }
                    isListView=false;
                    String mVals="";
                    for (int i = 0; i <l.size() ; i++) {
                        NodeEntity ne= (NodeEntity) l.get(i);
                        mVals+=ne.getName()+",";
                    }
                    String []mValArray=mVals.split(",");
                    mFlowLayout.setAdapter(mAdapter = new TagAdapter<String>(mValArray)
                    {

                        @Override
                        public View getView(FlowLayout parent, int position, String s)
                        {
                            TextView base_tag = (TextView) mInflater.inflate(R.layout.base_tag,
                                    mFlowLayout, false);
                            base_tag.setText(s);
                            if(((NodeEntity) l.get(position)).isCheck()){
                                selectedList.add(position);
                            }
                            return base_tag;
                        }
                    });
                }
                if(selectedList.size()==0&&maxSelect==1){//如果是单选则默认选择第一个
                    mAdapter.setSelectedList(1);
                }else{
                    mAdapter.setSelectedList(selectedList);
                }

            }
        });


        /*mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener(){//点击事件
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                //Toast.makeText(getActivity(), mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });*/

        if(!isListView) {
            mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {//选中事件
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    List<NodeEntity> list = (List<NodeEntity>) scope.key(getData()).val();
                    List<NodeEntity> listResult = new ArrayList<NodeEntity>();
                    for (Integer i : selectPosSet) {
                        NodeEntity ne = list.get(i);
                        ne.setCheck(true);
                        listResult.add(ne);
                    }
                    for (int i = 0; i < list.size(); i++) {
                        NodeEntity ne = list.get(i);
                        if (!listResult.contains(ne)) {
                            ne.setCheck(false);
                        }
                    }
                    if (maxSelect == 1) {
                        setReturn(listResult.get(0));
                    } else {
                        setReturn(listResult);
                    }
                }
                //getActivity().setTitle("choose:" + selectPosSet.toString());
            });
        }
    }

}
