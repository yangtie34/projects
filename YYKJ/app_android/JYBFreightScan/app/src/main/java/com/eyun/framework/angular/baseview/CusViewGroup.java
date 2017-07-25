package com.eyun.framework.angular.baseview;


import com.eyun.framework.angular.core.DirectiveViewControl;

import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */

public interface CusViewGroup extends CusBaseView{
    List<DirectiveViewControl> getChildrens();
    void addChildren(DirectiveViewControl directiveViewControl);
    void setChildrens(List<DirectiveViewControl> directiveViewControls);
}
