/**
 * 日期指令
 * <input type="text" maxValue="endDate" ng-model="startDate" datepicker/>
 * <input type="text" minValue="startDate" ng-model="endDate" datepicker/>
 */
system.directive('csDatepicker',['$parse','$timeout',function($parse,$timeout){
    return {
        restrict : 'A',
        scope : {
            maxValue : '=maxvalue',
            minValue : '=minvalue',
            onChange : '&onChange'
        },
        link : function(scope, element, attrs){
            var onChange = scope.onChange,
                maxValue = scope.maxValue,
                minValue = scope.minValue,
                clickId = attrs.clickId,
                getter = $parse(attrs.ngModel),
                setter = getter.assign;
            var fn = function(dp){
                setter(scope.$parent,dp.cal.getDateStr());
                if(onChange){
                   onChange();
                }
                $timeout(function(){
                	scope.$apply(function(){});
                });
            };
            var config = {
                onpicked:fn,dateFmt:'yyyy-MM-dd',readOnly:true,
                doubleCalendar : true,autoUpdateOnChanged : true,
                oncleared : fn
            };
            if(maxValue)config.maxDate = maxValue;
            if(minValue)config.minDate = minValue;
            element.on('focus',function(){
            	if(scope.maxValue)config.maxDate = scope.maxValue;
                if(scope.minValue)config.minDate = scope.minValue;
                WdatePicker(config);
            });
            if(clickId)
                $("#"+clickId).on('click',function(){
                    if(scope.maxValue)config.maxDate = scope.maxValue;
                    if(scope.minValue)config.minDate = scope.minValue;
                    element.focus();
                });
        }
    }
}]);