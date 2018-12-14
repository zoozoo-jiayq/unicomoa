<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员列表</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/js/base.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group_ext/groupUpdate.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<!-- 人员选择  start-->
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<style type="text/css">
.inputTable th{width:75px;}
</style>
</head>
<body class="bg_white">
<input type="hidden" id="groupId" value="${requestScope.groupId}"/>
<input type="hidden" id="directorId" value="${requestScope.groupInfoVO.directorId}"/>
<input type="hidden" id="assistantId" value="${requestScope.groupInfoVO.assistantId}"/>
<input type="hidden" id="topDirectorId" value="${requestScope.groupInfoVO.topDirectorId}"/>
<input type="hidden" id="topChangeId" value="${requestScope.groupInfoVO.topChangeId}"/>
<input type="hidden" id="parentId" value="${requestScope.groupInfoVO.parentId}"/>
<input type="hidden" id="isHasChild" value="${requestScope.isHasChild}"/>
<input type="hidden" id="isHasGroupUser" value="${requestScope.isHasGroupUser}"/>
    <div class="elasticFrame formPage">
<form action="#" id="groupForm">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
               <tbody>
                          <tr>
                            <th><label>群组名称：</label></th>
                            <td><input name="" type="text" class="formText" style="width:350px;" maxlength="30" id="groupName" 
						      onkeyup="this.value=this.value.replace(/[\\]/g,'')" valid="required" errmsg="groupExt.group_name_not_null" value="${requestScope.groupInfoVO.groupName}"/></td>
                          </tr>
                          <tr>
                              <th><label>排序字段：</label></th>
                              <td><input name="input2" type="text" style="width:150px;" class="formText" maxlength="3" id="groupOrder" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" valid="required|isNumber" errmsg="groupExt.group_order_not_null|groupExt.group_order_format_error" value="${requestScope.groupInfoVO.orderIndex}"/>
                              <font class="tip">正序排列，值越小排序越靠前</font></td>
                          </tr>
              </tbody>
           </table>
<input value="更 新" style="display:none" type="submit" class="formButton_green" id="groupUpdate"/>
</form>
</div>
</body>
</html>