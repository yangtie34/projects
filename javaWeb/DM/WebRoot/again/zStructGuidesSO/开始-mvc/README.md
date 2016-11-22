# MVC使用索引

## 1 MVC结构简介

#### mvc目前分为Model层，参照{@link NS.mvc.Model}类，View层，参照{@link NS.mvc.View}以及Controller层,参照{@link NS.mvc.Controller}

## 2 Model层

### Model层由后台service请求配置，以及通过请求配置获取数据的方法构成。
         
### 2.1 生成Model对象实例

		     var model = new NS.mvc.Model({
                  serviceConfig : {
                      'xsHeader_data' : {
                          service : "base_Service"
                      },
                      'xs_show_data' : {
                          service : "xsxx_showData",
                          params : {
                              age : 12,
                              sex : '12121212'
                          }
                      }
                  }
              });
  这样就获得了一个Model对象的实例

### 2.2 调用model对象和后台进行交互

### 然后如果你想和后台数据进行交互，那么需要通过获取以下方法获取数据
  
			  var data1 = model.getData('xsHeader_data',function(data){
							 //data 就是你想要的JSON数据
					  });
					  var data2 = model.getData([{
							 key : 'xsHeader_data'
					  },{
							 key : 'xs_show_data',
							 params : {
								  age : 12,
								  sex : '12121212'
							 }
					  }],function(data){
						//data 就是你想要的JSON数据,注意 data.[你所配置的serrvice的键] 就可以获取你对应的请求的数据啦
					  });

## 3  View层

### 3.1 生成View层对象实例

### 以下方法就生成一个view实例

           var view = new NS.mvc.View();

### 3.2 调用view层的方法
### 将组件注册进入view的管理中

			view.register('grid',new NS.component.Component());//注册组件

			var component = view.get('grid');//获取组件

#### 设置要渲染的顶层组件

             view.setComponent(component);//component 即为将要渲染到tab页上的组件\

## 4 Controller

###	      这里需要重点介绍Controller组件，因为在目前的结构划分中，虽然我们将前端结构层次划分为3层，但是因为为了整体撰写考虑，
	  我们把Model和View层交由Controller层代理。

### 4.1 生成controller实例

### 通常情况下每个人需要通过继承来实现自己的页面，而目前，需要继承的类即为{@link NS.mvc.Controller}

      NS.define('App.com.MyController',{
                      extend : 'NS.mvc.Controller',
                      modelConfig : {
                          serviceConfig : {
                              'xsHeader_data' : {
                                  service : "base_Service",
                                  params : {}
                              },
                              'xs_show_data' : {
                                  service : "xsxx_showData",
                                  params : {
                                      age : 12,
                                      sex : '12121212'
                                  }
                              }
                          }
                      }，
                      init : function(){
                          this.initData();
                          this.initComponent();
                      },
                      initData : function(){
                          this.getData('xsHeader_data',function(data){//代理实现了model的getData 方法
                              this.initComponent(data);
                          });
                      },
                      initComponent : function(data){
                          var component = this.createPage(data);
                          this.registerComponent('grid',component);//代理实现view的register 方法
                          this.getComponent('grid');//component 代理实现了view的get 方法
                          this.setPageComponent(component);//代理实现了view的setComponent 方法
                      }
                  });

##  5 MVC 小结
		用户在实现自己页面的时候需要继承{@link NS.mvc.Controller} 来实现自己的页面类，同时其中的操作步骤参照1.3.1 例子






