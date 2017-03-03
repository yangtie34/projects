package com.eyun.project_demo.cusviews;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.moduleview.flowLayout.FlowLayout;
import com.eyun.framework.util.common.DensityUtils;
import com.eyun.project_demo.R;

import java.util.List;
import java.util.Map;


/**
 *         <attr name="gravity">
 <enum name="left" value="-1" />
 <enum name="center" value="0" />
 <enum name="right" value="1" />
 </attr>
 */

public class CusGravityFlowTags extends AngularViewParent {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private int gravity=-1;
    public CusGravityFlowTags(Scope parent, String Data,int gravity) {
        super(parent);
        setData(Data);
        gravity=gravity;
        init();
    }
    public CusGravityFlowTags(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    protected void init() {
        int gravy=getParams()!=null?Integer.parseInt(getParams()):gravity;
        final FlowLayout flowLayout =new FlowLayout(Scope.activity,gravy);
        LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp.setMargins(0, DensityUtils.dp2px(6),0,0);
        flowLayout.setLayoutParams(lp);
        this.addView(flowLayout);
        scope.key(getData()).watch(new DataListener<List<View>>() {
            @Override
            public void hasChange(List<View> list) {
                for (int i = 0; i <list.size() ; i++) {
                    flowLayout.addView(list.get(i));
                }
            }
        });
    }
}
