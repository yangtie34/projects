package com.eyun.jybStorageScan.cusviews;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.eyun.framework.angular.core.DataListener;
import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.angular.core.AngularViewParent;
import com.eyun.jybStorageScan.R;

import java.util.List;
import java.util.Map;



/**
 * data:map{title,view}
 */

public class CusTabView extends AngularViewParent {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public CusTabView(Scope parent, String Data) {
        super(parent);
        setData(Data);
        init();
    }
    public CusTabView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    protected void init() {
      this.addView(scope.inflate(R.layout.cus_view_tab));
        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

      scope.key(getData()).watch(new DataListener<Map<String,View>>() {
          @Override
          public void hasChange(Map<String,View> map) {
            final String[] titles=map.keySet().toArray(new String[]{});
              MyViewPagerAdapter myViewPagerAdapter=new MyViewPagerAdapter((List) map.values());
              mViewPager.setAdapter(myViewPagerAdapter);
          }
      });
        mTabLayout.setupWithViewPager(mViewPager);
    }
    class MyViewPagerAdapter extends PagerAdapter{


        private List<View> mListViews;

        public MyViewPagerAdapter(List mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {
            container.removeView(mListViews.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return  mListViews.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;//官方提示这样写
        }
    }


}
