/***
 * @class NS.util.ComponentInstance
 * @extends NS.Base
 * @singleton
 *    用以获取包装对象
 * @private
 */
NS.define('NS.util.ComponentInstance',{
    singleton : true,
    CM : NS.ClassManager,
    /**
     * 构造函数
     */
    constructor : function(){
        this.callParent(arguments);
        this.instanceMap = {
            'Ext.Component' : 'NS.Component',
            'Ext.container.Container' : 'NS.container.Container',
            'Ext.tab.Panel' : 'NS.container.TabPanel',
            'Ext.panel.Panel' : 'NS.container.Panel',
            'Ext.form.Panel' : 'NS.form.BasicForm',

            'Ext.toolbar.ToolBar' : 'NS.toolbar.Toolbar',
            'Ext.button.Button' : 'NS.button.Button',
            'Ext.grid.Panel' : 'NS.grid.Grid',
            'Ext.menu.Menu' : 'NS.menu.Menu',
            'Ext.window.Window' : 'NS.window.Window',
            /**
             * grid
             */
            'Ext.grid.column.Column' : 'NS.grid.Column',
            'Ext.grid.column.ButtonColumn' : 'NS.grid.Column',
            'Ext.grid.column.LinkColumn' : 'NS.grid.Column',
            'Ext.grid.column.ProgressColumn' : 'NS.grid.Column',
            'Ext.grid.RowNumberer' : 'NS.grid.Column',






            /**
             * FieldInstance Field组件的映射关系
             */
            'Ext.form.field.Base' : 'NS.form.field.BaseField',
            'Ext.form.field.Text' : 'NS.form.field.Text',
            'Ext.form.field.Checkbox' : 'NS.form.field.Checkbox',
            'Ext.form.field.ComboBox' : 'NS.form.field.ComboBox',
            'Ext.form.field.Date' : 'NS.form.field.Date',
            'Ext.form.field.Display' : 'NS.form.field.Display',
            'Ext.form.field.File' : 'NS.form.field.File',
            'Ext.form.field.Number' : 'NS.form.field.Number',
            'Ext.form.field.Radio' : 'NS.form.field.Radio',
            'Ext.form.field.TextArea' : 'NS.form.field.TextArea',
            'Ext.ux.comboBoxTree' : 'NS.form.field.ComboBoxTree',
            'Ext.form.field.Time' : 'NS.form.field.Time',
            'Ext.form.CheckboxGroup' : 'NS.form.field.CheckboxGroup',
            'Ext.form.RadioGroup' : 'NS.form.field.RadioGroup',
            'Ext.form.field.ForumSearch':'NS.form.field.ForumSearch',
            'Ext.form.FieldSet':'NS.form.FieldSet',
            'Ext.form.field.HtmlEditor':'NS.form.field.HtmlEditor'
        };
        this.fieldInstanceMap = {

        };
    },
    /**
     * 获取组件的实例
     * @param {Ext.Component} component
     * @return {NS.Component}
     * @private
     */
    getInstance : function(component){
//       var NSCom = this.instanceMap[component.$className];
       if(component.NSContainer){
          return component.NSContainer;
       }else{
          var NSCom = this.CM.create(this.instanceMap[component.$className],{});
          NSCom.setComponent(component);
          component.NSContainer = NSCom;
          return NSCom;
       }
    }
});
