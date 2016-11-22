(EG.define("Sys",{
	config:{

	},
	constructor:function(){

	},
	statics:{

		/**
		 * 显示状态字
		 * @param array
		 * @param value
		 * @param {Boolean?} ignoreEmpty
		 */
		showType:function (array,value,ignoreEmpty){
			if(value==null&&ignoreEmpty) return "";
			for(var i=0;i<array.length;i++){
				if(value==null) value="";
				if(value==array[i][1]) return array[i].length>2?("<span style='color:"+array[i][2]+"'>"+array[i][0]+"</span>"):array[i][0];
			}
			return "";
		},

		/**
		 *
		 * @param ks
		 * @param fn
		 */
		call:function(ks,fn){
			var ss={};
			for(var i=0;i<ks.length;i++){
				var k=ks[i]["key"];
				var o={};
				ss[k]={
					service:k,
					params:{}
				};
			}
			//alert(EG.toJSON(ss));
			var model = new NS.mvc.Model({serviceConfig:ss});
			model.callService(ks,fn);
		}
	}

}))();
