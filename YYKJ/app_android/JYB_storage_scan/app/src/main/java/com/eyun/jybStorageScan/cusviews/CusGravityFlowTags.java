package com.eyun.jybStorageScan.cusviews;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.moduleview.flowLayout.FlowLayout;
import com.eyun.framework.util.common.DensityUtils;

import java.util.List;


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
