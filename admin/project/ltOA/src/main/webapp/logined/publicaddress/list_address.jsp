<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>外部通讯录</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/publicaddress/list_address.js"></script>
<script type="text/javascript" src="${ctx}js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/datatable/selecedForDatatablePagination.js"></script>
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
</head>
<body>
<input id="groupId" type="hidden"  />
	<input id="currentUserId" type="hidden"
		value='<s:property value="#session.adminUser.userId"/>' />
	<input id="groupName" type="hidden" />
	<input id="maintainPriv" type="hidden" />
<div class="mainpage">
  <div class="leftMenu">
    <div class="service-menu" id="groupListMenu">
      
    </div>
  </div>
  <div class="list" id="dataArea">
    <div class="searchArea">
      <table  cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
            
            <td class="right"><label>性别：</label>
              <select id="sex">
                <option value="-1">全部</option>
                <option value="1">男</option>
                <option value="0">女</option>
              </select>
              <label>关键字：</label>
              <input type="text" id="searchkey"  placeholder="姓名/手机号码" class="formText searchkey" maxlength="30"/>
              <input type="button" value="查询" class="searchButton" id="" onclick="queryAddress();"/></td>
            <td style="width:368px;display: none;"   id="addAddressTd"  >
            <div class="listbtn"  id="addAddressDiv"   style="display:none;"  >
              <div class="fButton greenBtn"> <span class="add" id="addAddressBtn">新增</span> </div>
              <div class="fButton orangeBtn"> <span id="deleteBtn" class="delete">删除</span> </div>
              <div class="fButton blueBtn"> <span class="import" id="importBtn">导入</span> </div>
              <div class="fButton blueBtn"> <span class="export" id="exportBtn">导出</span> </div>
            </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <table id="myTable" cellpadding="0" cellspacing="0"  class="pretty dataTable">
      <thead>
        <tr>
          <th class="num"> <input type="checkbox" name="checkbox"  id="total"/></th>
          <th style="width:20%;" id="name">姓名</th>
          <th style="width:50px;" id="sex">性别</th>
          <th style="width:150px;" class="longTxt" id="companyName">单位名称</th>
          <th style="width:150px;" id="postInfo">职务</th>
          <th style="width:20%;" id="familyMobileNo">手机号码</th>
          <th style="width:20%;" id="officePhone">工作电话</th>
          <th style="width:20%;" class="right_bdr0">操作</th>
        </tr>
      </thead>
     
    </table>
  </div>
</div>
<script>funPlaceholder(document.getElementById("searchkey"));</script>
</body>
   <script type="text/javascript">
		if(menuStyle == "left" ){
			var mainpage = $("div.mainpage");
			mainpage.removeClass("mainpage").addClass("mainpage_r");
		}
   </script>
</html>
