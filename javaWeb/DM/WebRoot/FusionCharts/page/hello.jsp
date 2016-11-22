<%--
  Created by IntelliJ IDEA.
  User: LJH
  Date: 2012-2-23
--%>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<head>
    <title>Simple jsp page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">

    <script type="text/javascript" src="../fusionJS/FusionCharts.js"></script>
    <script type="text/javascript" src="../fusionJS/ComplexLine1.js"></script>
</head>
<body onload="selectOption('')">

<div id="chart1"></div>

<div>
    <span id="hiddenSel"></span> <span id="hiddenCheckBox"></span>
</div>
<script type="text/javascript">
var myChart = new FusionCharts("../charts/MSLine.swf",
        "myChartId", 400, 240, 0, 1);

var jsonData = {
    /*"chart":{
     "caption":"Business Results 2005 v 2006",
     "xaxisname":"Month",
     "yaxisname":"Revenue",
     "showvalues":"0",
     "numberprefix":"$"
     },*/
    "categories":[{
        "category":[{
            "label":"Jan"
        },
            {
                "label":"Feb"
            },
            {
                "label":"Mar"
            },
            {
                "label":"Apr"
            },
            {
                "label":"May"
            },
            {
                "label":"Jun"
            },
            /*{
                "vline":"true",
                "color":"FF5904",
                "thickness":"12"
            },*/
            {
                "label":"Jul"
            },
            {
                "label":"Aug"
            },
            {
                "label":"Sep"
            },
            {
                "label":"Oct"
            },
            {
                "label":"Nov"
            },
            {
                "label":"Dec"
            }
        ]
    }
    ],
    "dataset":[{
        "seriesname":"2006",
        "data":[{
            "value":"27400"
        },
            {
                "value":"29800"
            },
            {
                "value":"25800"
            },
            {
                "value":"26800"
            },
            {
                "value":"29600"
            },
            {
                "value":"32600"
            },
            {
                "value":"31800"
            },
            {
                "value":"36700"
            },
            {
                "value":"29700"
            },
            {
                "value":"31900"
            },
            {
                "value":"34800"
            },
            {
                "value":"24800"
            }
        ]
    },
        {
            "seriesname":"2005",
            "data":[{
                "value":"10000"
            },
                {
                    "value":"11500"
                },
                {
                    "value":"12500"
                },
                {
                    "value":"15000"
                },
                {
                    "value":"11000"
                },
                {
                    "value":"9800"
                },
                {
                    "value":"11800"
                },
                {
                    "value":"19700"
                },
                {
                    "value":"21700"
                },
                {
                    "value":"21900"
                },
                {
                    "value":"22900"
                },
                {
                    "value":"20800"
                }
            ]
        }
    ]/*,
 "trendlines":{
 "line":[{
 "startvalue":"26000",
 "color":"91C728",
 "displayvalue":"Target",
 "showontop":"1"
 }
 ]
 },
 "styles": {
 "definition": [
 {
 "name": "CanvasAnim",
 "type": "animation",
 "param": "_xScale",
 "start": "0",
 "duration": "1"
 }
 ],
 "application": [
 {
 "toobject": "Canvas",
 "styles": "CanvasAnim"
 }
 ]
 }*/
};
var jsonObj = eval(Charts.getData('test', 'month', 'sdfs', '', '', jsonData));
//jsonObj.chart.caption = "34dtged343";


myChart.setJSONData(jsonObj);
myChart.setChartAttribute( "legendPosition",'right' );
myChart.render("chart1");

/* function changeAttribute()
 {
 // get chart reference
 var chartReference = FusionCharts("myChartId");
 // change palette, palette colors and set glass effect to chart
 //chartReference.setChartAttribute( "caption",'dfdfd' );
 chartReference.setJSONData(jsonData2);

 }*/

</script>
<input type="button" onclick="reDraw('',jsonObj,'myChartId')" value="对比"/>
</body>
</html>