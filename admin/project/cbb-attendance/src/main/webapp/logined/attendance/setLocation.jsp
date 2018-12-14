<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源设定</title>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=T72A6h5INhfXpKA5ZBsrDcj3fbQpeaWs"></script>
<script type="text/javascript"
        src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css"/>
<jsp:include page="../../common/flatHead.jsp"></jsp:include>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.inputTable th{width:75px;}
</style>
</head>
<body class="bg_white">
<div class="elasticFrame formPage pt20">
  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
    <tbody>
	   <tr>

        <th><label style="line-height: 23px;">搜索：</label></th>
        <td>
        	<input type="text" id="location" value="百度" style="width:300px;"/>
        	<input type="hidden" id="longitude" value="${param.longitude }"/>
        	<input type="hidden" id="latitude" value="${param.latitude }"/>
        </td>
      </tr>
	   <tr>
        <th></th>
        <td><div id="l-map" style="height:300px;width:100%;"></div></td>
      </tr>
      <tr>
        <th><label>考勤地点：</label></th>
        <td>
        	<span id="locationStr">${param.location }</span>
        </td>
      </tr>
     </tbody>
  </table>
</div>
	<script type="text/javascript">
		$(function () {
			function G(id) {
				return document.getElementById(id);
			}
			var map = new BMap.Map("l-map");//创建地图实例
			var searchInfoWindow = new BMapLib.SearchInfoWindow(map);
		    var myGeo = new BMap.Geocoder();
			
		    var point = new BMap.Point(116.404, 39.915);;//初始设置地图中心点
		    var longitude = $("#longitude").val();
		    var latitude = $("#latitude").val();
		    if(longitude!=null && longitude!='' && latitude!=null && latitude!=''){
		    	point = new BMap.Point(longitude, latitude);;//初始设置地图中心点
		    }
		    map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
		    map.centerAndZoom(point,15);                   // 初始化地图,设置城市和地图级别。
		    creatMarker();
		    
		    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
				{"input" : "location"
			});
		    
		    var myValue;
			ac.addEventListener("onconfirm", function(e,m) {    //鼠标点击下拉列表后的事件
				var _value = e.item.value;
				myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				$("#locationStr").html(myValue);
				setPlace();
			});
		
			function setPlace(){
				map.clearOverlays();    //清除地图上所有覆盖物
				function myFun(){
					var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
				    point = new BMap.Point(pp.lng, pp.lat);
				    map.centerAndZoom(pp, 15);
				    creatMarker();
					$("#longitude").val(pp.lng);
					$("#latitude").val(pp.lat);
				}
				var local = new BMap.LocalSearch(map, { //智能搜索
				  onSearchComplete: myFun
				});
				local.search(myValue);
			}
			
		    //创建地图遮盖物
		    function creatMarker() {
		        var marker = new BMap.Marker(point);        // 创建标注
		        map.addOverlay(marker);                     // 将标注添加到地图中
		        marker.enableDragging();                    //标注可拖拽
		        marker.addEventListener("click", function (e) {
		            searchInfoWindow.open(marker);
		        })
		        marker.addEventListener("dragend", function (e) { //监听标注的dragend事件，获取拖拽后地理坐标
		            $("#longitude").val(e.point.lng);
		            $("#latitude").val(e.point.lat);
		            var pt = e.point;
		            myGeo.getLocation(pt,function(result){
		            	$("#locationStr").html(result.address);	            	
		            });
		        })
		        var label = new BMap.Label("拖动图标可以定位哦~", {offset: new BMap.Size(20, -10)});
		        marker.setLabel(label);
		        map.addOverlay(marker); //在地图中添加marker
		        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		    };
		});
	
	</script>
</body>
</html>