/**
 * @class MainPage
 *  页面主类，提供一些全局性的方法和属性
 */
/**
 * 获取当前用户名
 * @method getUserName
 * return {String}
 */
/**
 * 获取当前登录名
 * @method getLoginName
 * @return {String}
 */
/***
 * 获取用户的部门信息
 * @method getBmxx
 * @return ｛Object｝{id: *, mc: *}
 */
/**
 * 获取班级信息
 * @method getBjxx
 * @return {Object} {id: *, mc: *}
 */
/**
 * 获取职工id
 * @method getZgId
 * @return {String}
 */
/**
 * 获取学生id
 * @method getXsId
 * @return {String}
 */
/**
 * 获取教学组织结构权限ids,id以逗号作为分隔符
 * @method getJxzzjgQx
 * @return {String}
 */
/**
 * 获取行政组织结构权限ids,id以逗号作为分隔符
 * @method getXzzzjgQx
 * @return {String}
 */
/**
 * 获取角色Id字符串集合
 * @method getRoleIds
 * @return {String}
 */
/**
 * 获取当前学年ID
 * @method getXnId
 * @return {String}
 */
/**
 *获取当前学期ID
 * @method getXqId
 * @return {String}
 */
/**
 * 获取当前学年名称
 * @method getXnMc
 * @return {String}
 */
/**
 *获取当前学期名称
 * @method getXqMc
 * @return {String}
 */
/**
 * 获取当前学年代码
 * @method getXnDm
 * @return {String}
 */
/**
 * 获取当前学期代码
 * @method getXqDm
 * @return {String}
 */
/**
 * 提供一个在框架上打开新页面的功能
 * @method openPage
 * @param {String} classname 需要打开的页面对应的类名
 * @param {Object} classInitConfig 类初始化参数
 * @param {Boolean} closeIfExist 如果存在是否关闭,默认为false
 */
/**
 * 提供一个在框架上打开新页面的功能
 /**

 *      var params = {
 *          action : 'loadFileAction',
 *          params : {
 *              name : '张三',
 *              age : 51
 *          }
 *      };
 *      MainPage.downLoad(params);
 * 该方法不支持参数的多层嵌套，只支持一层
 * @method downLoad
 * @param {Object} config 配置参数对象
 */