
/**
 * 日期指令
 * <input type="text" maxValue="endDate" ng-model="startDate" datepicker/>
 * <input type="text" minValue="startDate" ng-model="endDate" datepicker/>
 */
directives.directive('csDatepicker',['model',function(model){
    return {
        restrict : 'A',
        link : function(scope, element, attrs){
            var modelPath = attrs.ngModel,
                maxPath = attrs.maxvalue,
                minPath = attrs.minvalue,
                onchange = attrs.onchange;
            var fn = function(dp){
                model.putObject(scope,modelPath,dp.cal.getDateStr());
                scope.$apply(function(){
                    if(onchange){
                        var cFn = model.getObject(scope,onchange.replace("(","").replace(")",""));
                        cFn();
                    }
                });
            }
            element.on('focus',function(){
                var config = {
                    onpicked:fn,dchanging:fn, Mchanging:fn, ychanging:fn, dchanged:fn, Mchanged:fn, ychanged:fn,dateFmt:'yyyy-MM-dd',doubleCalendar : true,
                    readOnly:true
                },
                maxValue = model.getObject(scope,maxPath),
                minValue = model.getObject(scope,minPath);
                
                if(maxValue)config.maxDate = maxValue;
                if(minValue)config.minDate = minValue;
                WdatePicker(config);
            });
        }
    }
}]);