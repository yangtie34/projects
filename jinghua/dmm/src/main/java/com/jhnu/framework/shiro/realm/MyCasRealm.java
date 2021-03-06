package com.jhnu.framework.shiro.realm;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jhnu.system.permiss.service.UserService;

public class MyCasRealm extends CasRealm {  
    private UserService userService;  
    public void setUserService(UserService userService) {  
        this.userService = userService;  
    }  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
        String username = (String)principals.getPrimaryPrincipal();  
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();  
        authorizationInfo.setRoles(userService.findRoles(username));  
        authorizationInfo.setStringPermissions(userService.findPermissions(username));  
        return authorizationInfo;  
    }  
    
} 
