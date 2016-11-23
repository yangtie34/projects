/**
 * 经典模板。
 * 
 * @argument jsonData = { templateType :'classicalTemplate', compDatas :[ {
 *           compId:1818181 compData:{ 存放该组件对应的数据格式。 } }, { compId:1818182
 *           compData:{ 存放该组件对应的数据格式。 } } ], positionData : [ { compIds:[
 *           1818181,1818182 ] },{ compIds:[ 1818181,1818182 ] },{ compIds:[
 *           1818181,1818182 ] } ] }
 * @return
 *           @example
 */
NS.define('Output.ClassicalTemplate', {
			constructor : function(config) {
			   config.autoScroll = true;
			   config.style = {
							backgroundColor : 'white'
						};
			   config.baseCls = 'classTemplate';
			   var container = new Ext.container.Container(config);
			   this.component = Ext.create('Ext.container.Container',{
			   			items:container
			   });
			},
		    /***
			  获取封装组件
			**/
            getLibComponent : function(){
			   return this.component;
			},
			render : function(id){
			   this.component.render(id);
			}
		});