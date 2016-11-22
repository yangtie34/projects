package com.jhkj.mosdc.framework.util;

import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;
import com.jhkj.mosdc.permiss.domain.User;

import java.util.List;

/**
 * 业务模块权限工具类。
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-3
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class BusinessPermisUtil {
    public static final String PERMISS_ALL="ALL";

    /**
     * 指定具有最大权限的角色代码数组。
     * @param dbIds
     * @return
     */
    public static String getDataPermissionByIds(List<Long> dbIds){
        User user = UserPermiss.getUser();

        String roleIds = user.getCurrentRoleLxIds();/*获取当前用户所在的角色id*/
        /*如果当前登录用户的角色属于最大数据权限角色那么返回ALL*/
        String[] roles = roleIds.split(",");
        for(Long id : dbIds){
            for(int i=0;i<roles.length;i++){
                long temp = Long.parseLong(roles[i]);
                if(id.longValue()==temp){
                     return PERMISS_ALL;
                }
            }
        }

        /*如果当前登录用户不是最大数据权限角色,那么获取其分配的用户数据权限*/
        String jxzzjgIds = user.getCurrentJxzzjgIds();
        
        return jxzzjgIds;
    }
    /**
     * 指定具有最大权限的角色代码数组。
     * @return
     */
    public static String getDataPermissionByIds(){
        UserInfo userInfo = UserPermissionUtil.getUserInfo();
        /*如果当前登录用户不是最大数据权限角色那么请求其分配的用户数据权限*/
        String userDataIds ="";
        String jxzzjgIds = userInfo.getPermissJxzzIds();
        String xzzzjgIds = userInfo.getPermissXzzzIds();
        if(jxzzjgIds==null&&xzzzjgIds!=null){
            userDataIds = xzzzjgIds;
        }else if(jxzzjgIds!=null&&xzzzjgIds==null){
            userDataIds = jxzzjgIds;
        }else if(jxzzjgIds!=null&&xzzzjgIds!=null){
            userDataIds = jxzzjgIds.concat(",").concat(xzzzjgIds);
        }else{
            userDataIds = "";
        }
        if(userDataIds==""){
            return "";
        }else{
            return userDataIds;
        }

    }
}
