<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>学习经历</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../../common/flatHead.jsp"/>
    <link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/datatable_checkbox.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/other/learnList.js"></script>
    <script type="text/javascript">
        var basePath = "${ctx}";
    	var userId = "${param.userId}";
    </script>
</head>
<body>
<input type="hidden" value="${param.userId}" id="userId"/>
<div class="list">
		<div class="searchArea">
      <table cellspacing="0" cellpadding="0">
        <tbody>
          <tr>            
                <td class="right">&nbsp;</td>
               	<td style="width:185px;">
	               <div class="fButton greenBtn" id="btnAddLearn" >
	              	 <span class="add"  id="btnAddUser">新增</span> 
	               </div>
	               <div class="fButton greenBtn" id="btnRefreshLearn" >
	              	 <span class="refresh">刷新</span> 
	               </div>
               </td>
          </tr>
        </tbody>
      </table>
    </div>

        <table id="myTable" cellpadding="0" cellspacing="0" class="pretty dataTable">
            <thead>
            <tr>
            	  <th class="num" id="no">序号</th>
                  <th style="width:100px;" id="userName" >员工姓名</th>
                  <th style="width:20%;" id="major" class="longTxt">所学专业</th>
                  <th style="width:100px;" id="education">所获学历</th>
                  <th style="width:20%;" id="school" class="longTxt">所在院校</th>
                  <th style="width:90px;" id="reterence" class="longTxt">证明人</th>
                  <th class="right_bdr0" style="width:100px;">操作</th>
            </tr>
            </thead>
     
        </table>
    <div class="clear"></div>
</div>
</body>
</html>