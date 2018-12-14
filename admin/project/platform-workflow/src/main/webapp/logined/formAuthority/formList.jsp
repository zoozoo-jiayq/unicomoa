<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
<script type="text/javascript" src="${ctx}js/logined/formAuthority/formList.js"></script>
</head>
 <body>
  <input type="hidden"  id="firstPage" value="<%=request.getParameter("firstPage")!=null?request.getParameter("firstPage"):"" %>" />
<div class="list">
  <div class="searchArea">
    <table cellspacing="0" cellpadding="0">
      <tbody>
        <tr>
        	<td class="right">
            <label>关键字：</label>
            <span class="position:relative;">
            <input type="text" id="title"  maxlength="10"    class="formText searchkey" placeholder="表单名称/表单类别"/>
            </span>
            <input type="button"   id="searchButton"  class="searchButton" value="查询"/>
            </td>
        </tr>
      </tbody>
    </table>
  </div>
         <table width="100%"   id="myTable"  class="pretty"  cellspacing="0" cellpadding="0"  >
			        <thead>
				         <tr >
					        <th class="num">序号</th>
					        <th style="width:100%">表单名称</th>
					        <th style="width:150px;" >表单类别</th>
					        <th class="right_bdr0" style="width:70px;">操作</th>
				        </tr>
			        </thead>
			        <tfoot>
			        </tfoot>
			    </table>
</div>
<script>funPlaceholder(document.getElementById("title"));</script>


  </body>
</html>
