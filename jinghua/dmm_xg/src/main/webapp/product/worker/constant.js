app.constant('setting', {
	
	// itemStyle
	labelTop : {
	    normal : {
	        label : {
	            show : true,
	            position : 'center',
	            formatter : '{b}',
	            textStyle: {
	                baseline : 'bottom'
	            }
	        },
	        labelLine : { show : false }
	    }
	},
	
	// itemStyle
	labelBottom : {
	    normal : {
	        color: '#ccc',
	        label : {
	            show : true,
	            position : 'center'
	        },
	        labelLine : { show : false }
	    },
	    emphasis: { color: 'rgba(0,0,0,0)' }
	},
	
	// itemStyle
	labelFromatterFn : function(count){
		return {
			normal : {
		        label : {
		            formatter : function (params){
		                return count-params.value + '人';
		            },
		            textStyle: {baseline : 'top'}
		        }
		    }
		}
	}
	
});