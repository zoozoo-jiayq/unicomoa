<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../common/flatHead.jsp"></jsp:include>
<title>分类设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/flat/js/placeholder.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}js/logined/docTemplate/templateCategoryList.js" ></script>
</head>

<body>
<div class="list">

  		<div class="searchArea">
    		<table cellspacing="0" cellpadding="0">
      		<tbody>
        		<tr>
          		<td class="right">&nbsp;</td>
            		<td style="width:92px;"><div class="fButton greenBtn"><span class="add" onclick="addTemplateCategory();">新增</span></div></td>
        		</tr>
      		</tbody>
   			</table>
  		</div>
		<table  cellspacing="0" cellpadding="0" class="pretty" id="myTable">
		</table>
</div>
</body>
</html>
