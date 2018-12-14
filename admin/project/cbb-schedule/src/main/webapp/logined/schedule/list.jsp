<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="../../common/taglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导出日程</title>
<jsp:include page="../../common/flatHead.jsp"></jsp:include>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.formButton_green{min-width:100px; _width:100px;height:30px;line-height:25px; text-align:center;outline:0;cursor:pointer;color:#fff;font-size:16px;border:0; background:#61b6c5; border-radius:3px; margin-right:20px;padding:0 10px;}
</style>
</head>
<body>
	<div class="formbg" style="padding-top:20px;min-height: 600px;">
	   <div class="content_form">
	      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
	        <tbody>
		        <tr>
		       		 <th style="float:left;width:500px;text-align: left;"><span>该功能正在开发中，敬请期待。</span>
	  				 </th>
	  				 <td></td>
		        </tr>
		        <tr>
		       		 <th style="float:left;width:500px;text-align: left;"><span>目前您可以选择“导出日程”，进行查看。</span>
	  				 </th>
	  				 <td></td>
		        </tr>
	        </tbody>
          </table>
        </div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#export").click(function(){
				var startTime=$("#startTime").val();
				var endTime=$("#endTime").val();
				if(!startTime || !endTime){
					art.dialog.alert("请选择日期");
					return false;
				}
		    	var url=basePath+"schedule/export.action?startTime="+startTime+"&endTime="+endTime;
		    	window.location.href=url;
			});
		});
	</script>
</body>
</html>

