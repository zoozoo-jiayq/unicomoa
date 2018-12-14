<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% 
  String formId = request.getParameter("formId")!=null?request.getParameter("formId"):"";
 %>
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
 
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}js/logined/formAuthority/propertyList.js"></script>
</head>

  <body>
 
   <div class="list">
     <div class="title_gw">
        <span class="name">权限设置</span>
        <input type="button"  onclick="goFomrListPage();" value="返回" class="formButton_grey"  />
     </div>
     <table class="pretty"  id="myTable"  cellspacing="0" cellpadding="0">
    <thead>
      <tr >
        <th class="num">序号</th>
        <th style="width:60%">字段名称</th>
        <th style="width:40%;">可编辑人员列表</th>
        <th class="right_bdr0" style="width:100px;">操作</th>
      </tr>
    </thead>
    <tbody>
    </tbody>
  </table>                       
                                               
 </div>
 <input type="hidden"  id="firstPage" value="<%=request.getParameter("firstPage")!=null?request.getParameter("firstPage"):"" %>" />
<input type="hidden" id="formId"  value="<%=formId %>"    />
<input type="hidden" id="range_all_user_id"     />
<input type="hidden" id="range_all_user"      />
  
   
  </body>
</html>
