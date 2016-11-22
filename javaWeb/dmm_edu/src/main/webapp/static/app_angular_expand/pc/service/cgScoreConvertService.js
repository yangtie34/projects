
/**
 * 分数到等级的转换：
 * score>=4分：优,
 * 4>score>=3分：良,
 * 3>score>=2分：中,
 * score<2分：一般,
 * 
*/
jxpg.factory("cgScoreConvertService",function(){
	return {
		getConvert:function(score){
			var result;
			if(score>=4){
				result="优";
	  		}else if(score>=3 && score<4){
	  			result="良";
	  		}else if(score>=2 && score<3){
	  			result="中";
	  		}else if(score<2){
	  			result="一般";
	  		}
			return result;
		}
	}
});