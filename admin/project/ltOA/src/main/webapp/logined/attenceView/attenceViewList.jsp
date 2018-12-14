<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>考勤详情</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<!-- tree -->
<script type="text/javascript" src="${ctx}plugins/tree/ztree/jquery.ztree.all-3.5.min.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
<script type="text/javascript" src="${ctx}js/logined/attenceView/attenceViewList.js"></script>
<script type="text/javascript">
    //点击导入
    jQuery(document).ready(function($){
	    $("#importUser").click(function(){
	        uploadUser();
	        return false;
	    });
    });
</script>

<style>
   
   
</style>
<script type="text/javascript" src="${ctx}js/logined/user/userList.js?version=${version}"></script>

</head>
<body>
  <div class="list" style="overflow:visible;">
    <div class="searchArea" style="overflow:visible;">
      <table cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
             <td class="right">
            <div class="left" style="display:inline-block;float: left;">
            <label>在职人数：<span id="jobNum"></span></label>
	          <label>到岗人数人数：<span id="inPosition"></span></label>
	          <label>出勤率：<span id="attendancePer"></span></label>
	          </div>	
              <label>科室部门：</label>
	            <div class="divselect12  divselect13" onmouseleave="javascript:$('#handleUserTree').hide()" style="vertical-align:middle;float:none;display:inline-block;">
			      	<cite class="cite12 text_ellipsis cite13"  onClick="toggleSelectGroup()"  id="groupText"></cite>
					<ul id="groupTree" class="ztree leibie"style="display: none;height:200px !important;width:200px !important;"></ul>
		  	   </div>
              <label>到岗状态：</label>
              <select name="state" id="state">
                <option value="">全部</option>
                <option value="0">到岗</option>
                <option value="1">未到岗</option>
              </select>
              <input id="serch" type="button" value="查询" class="searchButton"/></td>
          </tr>
        </tbody>
      </table>
    </div>
    <table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="attenceTable">
     <thead>
      <tr>
       <th  class="tdCenter" style="width:20px;">序号</th>
       <th  class="tdCenter" style="width:120px;">人员名称</th>
       <th  class="tdCenter"  style="width:120px;">科室部门</th>
       <th  class="tdCenter" style="width:10%;">到岗状态</th>
       </tr>	
      </thead>
    </table>
  </div>
<script>funPlaceholder(document.getElementById("searchName"));</script>
</body>
</html>
