<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>百度地图测试</title>
<script src="http://api.map.baidu.com/api?v=2.0&ak=CSgs5pvAdsprP9WSG3pSY868PxE50VOx&s=1" type="text/javascript"></script>
</head>
<body>
<%-- 这是页面的整体架子 --%>
  <div id="map" class="col-xs-12" style="height:100%;"></div>  
 <script type="text/javascript">  
  var map = new BMap.Map('map');  
  var point = new BMap.Point(116.404, 39.915);    
  map.centerAndZoom(point, 15);
  window.setTimeout(function(){  
      map.panTo(new BMap.Point(116.409, 39.918));    
  }, 2000);
  map.addControl(new BMap.GeolocationControl());    
  map.addControl(new BMap.MapTypeControl());    
  map.addControl(new BMap.CopyrightControl());
  map.addControl(new BMap.NavigationControl());
  
</script>  
</body>
</html>