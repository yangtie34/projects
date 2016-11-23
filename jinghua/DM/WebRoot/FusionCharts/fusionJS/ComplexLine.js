/**
 * 根据指定的URL,渲染出下拉框
 * @param url
 */
function selectOption(url) {
     Connection.request({
     async:false,
     method: "POST",
     url:url,
     params:{name:value}
     });
     var json = Connection.getData();
    //var json = {fieldName:'zhangsan',fields:[{value:1,label:111},{value:2,label:222},{value:3,label:333},{value:4,label:444}]};   //数据格式
    var jsonObj = eval(json);
    var selStart = "<select onchange=changeSel('" + url + "','" + jsonObj.fieldName + "',this)>";
    var selEnd = "</select>"
    var fields = jsonObj.fields;
    var len = fields.length;
    var option = "<option>----</option>";
    for (var i = 0; i < len; i++) {
        var value = fields[i]['value'];
        var label = fields[i]['label'];
        option += "<option value='" + value + "' >" + label + "</option>"
    }
    var hiddenDiv = document.getElementById("hiddenSel");
    hiddenDiv.innerHTML = selStart + option + selEnd;

}
/**
 * 外部无需调用此方法
 * @param url
 * @param name
 * @param obj
 */
function changeSel(url, name, obj) {
    with (obj) {
         Connection.request({
         async:false,
         method: "POST",
         url:url,
         params:{name:value}
         });
         var json = Connection.getData();
       // var json = {data:[{name:'glass',value:'2005',label:'二年级'},{name:'glass',value:'2006',label:'三年级'}]};     //数据格式
        var checkName = [];
        var jsonObj = eval(json);
        var data = jsonObj.data;
        var len = data.length;
        var checkbox = '';
        var flag = 0;
        for (var i = 0; i < len; i++) {
            var name = data[i]['name'];
            for (var i = 0; i < checkName.length; i++) {
                if (checkName[i] == name)
                    flag = 1;
            }
            if (flag == 0) {
                checkName.push(name);
            }
            var val = data[i]['value'];
            var label = data[i]['label'];
            checkbox += "<input type='checkbox' name='" + name + "' value='" + val + "' onclick='func(this)'/>" + label;
        }

        var span = document.getElementById("hiddenCheckBox");
        span.innerHTML = checkbox;
    }
}

var __param = [];
/**
 * 外部无需调用此方法
 * @param obj
 */
function func(obj) {
    var name1 = obj.name;
    var value1 = obj.value;
    var paramObj = {name:name1,value:value1}

    if (obj.checked) {
        var flag = 0;
        for (var i = 0; i < __param.length; i++) {
            if (__param[i]['name'] == name1)
                flag = 1;
        }
        if (flag == 0)
            __param.push(paramObj);
    } else {
        var flag = 0;

        for (var i = 0; i < __param.length; i++) {

            var checked = document.getElementsByName(name1);
            var count = 0;
            for (var i = 0; i < checked.length; i++) {
                if (checked[i].type == "checkbox" && checked[i].checked)
                    count++;
            }
        }
        if (count == 0) {
            for (var i = 0; i < __param.length; i++) {
                if (__param[i]['name'] == name1) {
                    flag = 1;
                    break;
                }
            }
        }
        if (flag == 1)
            __param.splice(i, 1);
    }

   /* for (var i = 0; i < __param.length; i++) {
        var name = __param[i]['name'];
        alert(name)
        
    }*/
}
/**
 * 对多个指标进行对比时，调用此方法(按钮的单击事件)
 * @param url
 * @param chartId
 */
function reDraw(url, chartId) {

    var param = [];

    for (var i = 0; i < __param.length; i++) {
        var name = __param[i]['name'];
        var checked = document.getElementsByName(name);
        for (var j = 0; j < checked.length; j++) {

            if (checked[j].type == "checkbox" && checked[j].checked) {
                param.push({name:checked[j]['value']})
            }
        }
    }
  
     Connection.request({
     async:false,
     method: "POST",
     url:url,
     params:param
     });
     var jsonData = Connection.getData();


    var chartReference = FusionCharts(chartId);

    //chartReference.setChartAttribute( "caption",'dfdfd' );

    chartReference.setJSONData(jsonData);

}
var Charts = {};
Charts.getData=function (caption,xName,yName,legendPosition,numberPrefix,dataBody){
    var jsonObj = eval(dataBody);
    var jsonData = {"chart":{
          "caption":caption,
        "legendPosition":legendPosition,
          "xaxisname":xName,
          "yaxisname":yName,
          "showvalues":"0",
          "numberprefix":numberPrefix||"$"

      },categories:jsonObj.categories,dataset:jsonObj.dataset};

    return jsonData;

}
