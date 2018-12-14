<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../common/taglibs.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员列表</title>
<style type="text/css">
.inputTable th {
	width: 150px;
}
</style>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }flat/plugins/peopleTree/skins/tree_default.css" type="text/css" />
<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
	//点击导入
	jQuery(document).ready(function($) {
		$("#importUser").click(function() {
			uploadUser();
			return false;
		});
	});
</script>
<script type="text/javascript" src="${ctx}js/common/placeholder.js"></script>
<script type="text/javascript"  src="${ctx}js/common/treeNode.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/logined/user/userTree.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}js/common/ajaxfileupload.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}flat/js/base.js?version=${version}"></script>

<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
<!-- 人员选择  start-->
<script type="text/javascript" src="${ctx}/js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript"  src="${ctx}js/logined/group_ext/userList.js?version=${version}"></script>
</head>
<body>
	<form id="userSelectFrom">
		<input type="hidden" id="type" value="${param.type}" /> 
		<input name="groupId" type="hidden" id="groupId" value="${param.groupId}" /> 
		<input type="hidden" id="loginName" value="${param.loginName}" /> 
		<input type="hidden" id="alterName" value="${param.alterName}" /> 
		<input name="roleId" type="hidden" id="roleId" value="${param.roleId}" /> 
		<input name="loginOrder" type="hidden" id="loginOrder" value="${param.loginOrder}" />
		<input type="hidden" id="userIds" />
		<input type="hidden" id="userNames"/>
		<input id="mobileViewState" type="hidden" value="-1"/>
	</form>
	
 
	<div class="list">
    <div class="searchArea">
      <table cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
            <td class="right"><label>性别：</label>
              <select name="sex" id="sex">
                <option value="">全部</option>
                <option value="1">男</option>
                <option value="0">女</option>
              </select>
              <label>关键字：</label>
              <span class="position:relative;">
                   <input type="text" class="formText  searchkey" maxlength="30" placeholder="姓名/手机号码" id="searchName" />
              </span>
              <input type="button" value="查询" class="searchButton" id="search"/></td>
              <c:if test="${param.edit == 1}">
               	<td style="width:184px">
              </c:if>
              <c:if test="${param.edit != 1}">
               	<td style="width:184px;display:none;">
              </c:if>
	               <div class="fButton greenBtn">
	               <span class="add" id="btnAddUser" >新增</span> </div>
	               <div class="fButton orangeBtn"> <span  id="btnDeleteUser" class="delete">删除</span> </div>
	               <div class="fButton blueBtn" style="display: none;"> <span class="move" id="moveToGroup">移动到群组</span> </div>
               </td>
          </tr>
        </tbody>
      </table>
    </div>
 <table cellpadding="0" cellspacing="0" id="userTable"  class="pretty dataTable">
      <thead>
        <tr>
          <th  class="chk"><input type='checkbox' id='total'/></th>
          <th  id="no" class="num">序号</th>
          <th  id="userName" class="tdCenter" style="width:20%;">姓名</th>
          <th  class="tdCenter" style="width:100px;">性别</th>
          <th  id="phone" class="tdCenter"style="width:20%;">手机号码</th>
          <th  class="tdCenter" style="width:20%;">登录用户</th>
          <th  id="createTime" class="tdCenter"style="width:20%;">加入日期</th>
          <th  id="lastLoginTime" class="tdCenter"style="width:20%;" class="right_bdr0">最后登录</th>
         </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
  </div>
 
  
	 <script>funPlaceholder(document.getElementById("searchName"));</script>
</body>
</html>