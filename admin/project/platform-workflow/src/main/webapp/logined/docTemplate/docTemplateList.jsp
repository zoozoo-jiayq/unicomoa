<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>分类设置</title>
<jsp:include page="../../common/flatHead.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/datatable_checkbox.js"></script>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/flat/js/placeholder.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/docTemplate/templateList.js" ></script>
</head>
<body>
<div class="list">
		<div class="searchArea">
		<table cellspacing="0" cellpadding="0">
      		<tbody>
        	<tr>
        	<td class="right">
	   		 <label>类型：</label>
                     <select id="docTemplateType"></select>
                     <label>关键字：</label>
                     <span class="position:relative;">
                     <input type="text" style="width:250px" class="formText searchkey" id="title" name="" placeholder="模版名称" maxlength="50"/>
                     </span>
                     <input type="button"  onclick="getDataTable();" id="searchButton" class="searchButton" value="查询"/>
 			</td>
 			<td style="width:184px;">
 				<div class="fButton greenBtn"><span class="upload" onclick="addDocTempalte(0);">上传</span></div>
                <div class="fButton orangeBtn"><span class="delete" onclick="deleteMore();">删除</span></div>
 			</td>
 			</tr>
       </tbody>
       </table>
	</div>
		<table cellpadding="0" cellspacing="0"  class="pretty" id="myTable">
				<thead>
				</thead>
		</table>
</div>
<script>funPlaceholder(document.getElementById("title"));</script>
</body>
</html>
