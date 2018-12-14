<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>分类设置</title>
 <jsp:include page="../../common/flatHeadNoChrome.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="javascript" src="${ctx}js/logined/customForm/formCategoryList.js" ></script>

</head>
<body>
<jsp:include page="shareFormCategory.jsp" />
<div class="list">
<div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
        	
          <td class="right">&nbsp;
		  
            </td>
            <td style="width:93px;"><div class="fButton greenBtn"><span class="add" href="javascript:void(0)" onclick="addFormCategory();">新增</span></div></td>
        </tr>
      </tbody>
    </table>
  </div>
		<table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="myTable">
				<thead>
						<tr>
								<th class="num" id="no">序号</th>
								<th id="name" class="longTxt">表单分类</th>
								<th style="width:100px;" class="data_r" id="count">表单数量</th>
								<th style="width:100px;" class="right_bdr0">操作</th>
						</tr>
				</thead>
		</table>
</div>
</body>
</html>
