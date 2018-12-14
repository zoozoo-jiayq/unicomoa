<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

--%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据字典设置</title>
<jsp:include page="../../../../common/flatHead.jsp"/>
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/flat/plugins/tree/skins/tree_default.css" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}logined/appraisal/js/dict/dict.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script> 
<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}flat/js/base.js"></script>
</head>

<body>
<input type="hidden" id="sysTag" value='${paramValues.sysTag[0]}'/>
<input type="hidden" id="sysTagItem" value='${paramValues.sysTag[0]}'/>
<input type="hidden" id="infoType" />
<input type="hidden" id="SysTypeId" />
<input type="hidden" id="SystypeValue" />
<input type="hidden" id="sid" />

<div class="mainpage">
  <div class="leftMenu">
    <div class="service-menu">
      <h1>类型列表</h1>
        <div class="menu-cont"  id="sysTypeList">
        
       </div>
    </div>
  </div>
  <div class="list">
  <div class="searchArea">
      <table cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
           
            <td class="right">&nbsp;</td>
              <td style="width:184px;">
                  <div class="fButton greenBtn">
                   <span  class="add"  id="addType" >新增</span>
                  </div>
                  <div class="fButton orangeBtn">
                    <span class="delete"   id="deleteType" >删除</span>
                  </div>
              </td>
          </tr>
        </tbody>
      </table>
    </div>
    <table cellpadding="0" cellspacing="0"  class="pretty dataTable">
      <thead>
        <tr>
          <th class="chk"><input id="total"  type="checkbox" /></th>
          <th class="num">序号</th>
          <th>名称</th>
          <th class="right_bdr0" style="width:70px;">操作</th>
        </tr>
      </thead>
      <tbody  id="types">
      </tbody>
    </table>
  </div>
</div>


<!--  
<div class="dictionary" >
	<h1>数据字典设置</h1>
	<div class="dataArea">
      <div class="data_r">
         <div class="workes">
            <ul>
               <%--<li><a href="javascript:void(0)"  id="addSysType" onclick="addTypeOpen(-1)"><em class="em1"></em>新增</a></li>
               <li><a href="javascript:void(0)"  id="updateSysType" onclick="updateSysTypeOpen()"><em class="em2"></em>修改</a></li>
               <li><a href="javascript:void(0)"  id="deleteSysType" onclick="deleteSysType()"><em class="em3"></em>删除</a></li>
            --%>
               <li><a href="javascript:void(0)"  id="addType" ><em class="em1"></em>新增</a></li>
               <li><a href="javascript:void(0)"  id="updateType" ><em class="em2"></em>修改</a></li>
               <li><a href="javascript:void(0)"  id="deleteType" ><em class="em3"></em>删除</a></li>
            </ul>
          </div>
    	<div id="sysTypeList" class="d_list">    	
    	</div>
  	  </div>
  <div class="data_l">
    <h2>
     <label class="radio"><input type="checkbox"  id="total"/>全选</label>&nbsp;&nbsp;
     <input style="display: none" type="button" id="addType" class="formButton" value="新增"/>
     <input style="display: none" type="button" class="formButton" id="updateType" value="修改"/>
     <input style="display: none" type="button" class="formButton" id="deleteType" value="删除"/>
    </h2>
      <div id="types">
      </div>
  </div>
 </div>
</div>
-->
</body>
<script type="text/javascript">
		if(menuStyle != "default"){
			var mainpage = $("div.mainpage");
			mainpage.removeClass("mainpage").addClass("mainpage_r");
		}
</script>
</html>