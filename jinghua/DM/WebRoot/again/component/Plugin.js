/***
 * @class NS.Plugin
 * @extends NS.Base
 * 所有组件的插件类的基类
 *      var component = new NS.Component({
 *          plugins : [new NS.Plugin]
 *      });
 */
NS.define('NS.Plugin',{
    /**
     * 插件的宿主对象初始化方法
     * @param {NS.Component} component 宿主对象
     */
    init : NS.emptyFn
});
