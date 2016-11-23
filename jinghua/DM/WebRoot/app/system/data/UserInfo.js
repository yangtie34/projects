/**
 * 用户信息工具类
 */
NS.define('System.data.UserInfo', {
    constructor: function (userInfo) {
        this.userInfo = userInfo;
    },
    /**
     * 获取当前用户名
     * @return {String}
     */
    getUserName: function () {
        return this.userInfo.username;
    },
    /**
     * 获取当前登录名
     */
    getLoginName : function(){
        return this.userInfo.loginName;
    },
    /***
     * 获取用户的部门信息
     * @return ｛Object｝{id: *, mc: *}
     */
    getBmxx : function(){
        var user = this.userInfo;
        return {id : user.bmId,mc : user.bmmc};
    },
    /**
     * 获取班级信息
     * @return {Object} {id: *, mc: *}
     */
    getBjxx : function(){
        var user = this.userInfo;
        return {id : user.bjId,mc : user.bmmc};
    },
    /**
     * 获取职工id
     * @return {String}
     */
    getZgId : function(){
        return this.userInfo.zgId;
    },
    /**
     * 获取学生id
     * @return {String}
     */
    getXsId : function(){
        return this.userInfo.zgId;
    },
    /**
     * 获取教学组织结构权限ids,id以逗号作为分隔符
     * @return {String}
     */
    getJxzzjgQx: function () {
        return this.userInfo.permissJxzzIds;
    },
    /**
     * 获取行政组织结构权限ids,id以逗号作为分隔符
     * @return {String}
     */
    getXzzzjgQx: function () {
        return this.userInfo.permissXzzzIds;
    },
    /**
     * 获取角色Id字符串集合
     * @return {String}
     */
    getRoleIds : function(){
        return this.userInfo.roleIds;
    }
});