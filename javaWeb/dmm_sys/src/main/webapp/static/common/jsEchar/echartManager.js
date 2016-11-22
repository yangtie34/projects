(function($) {
    $.echartManager = {};
    $.echartManager.charMap = {};

    $.echartManager.put =function(chart){
        $.echartManager.charMap[chart.dom.id] = chart;
	};
	$.echartManager.get =function(id){
		return $.echartManager.charMap[id];
	};
	$.echartManager.del =function(id){
		var obj = this.charMap
		delete obj[id];
	};
	$.echartManager.resize = function(){
		for(var key in $.echartManager.charMap){

            $.echartManager.charMap[key].resize();
		}
	};
})(jQuery);