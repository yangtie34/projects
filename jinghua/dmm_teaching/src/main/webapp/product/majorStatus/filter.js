app.filter('positive', function(){
	return function(value){
		var val = null;
		if(value!=null && value != ''){
//			val = (Number)value;
			val = parseInt(value);
			if(val < 0){
				val = -val;
			}
		}else
			val = '--';
		return val;
	}
})