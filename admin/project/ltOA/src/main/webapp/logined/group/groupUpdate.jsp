<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>人员列表</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group/groupUpdate.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group/userTree.js"></script>
<!-- 人员选择  start-->
<script type="text/javascript" src="${ctx}js/logined/group/selectUser.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}js/logined/group/selectGroup.js"></script>
<style type="text/css"> 
.inputTable th{width:108px;}
</style>
</head>
<body>
<input type="hidden" id="groupId" value="${requestScope.groupId}"/>
<input type="hidden" id="directorId" value="${requestScope.groupInfoVO.directorId}"/>
<input type="hidden" id="assistantId" value="${requestScope.groupInfoVO.assistantId}"/>
<input type="hidden" id="topDirectorId" value="${requestScope.groupInfoVO.topDirectorId}"/>
<input type="hidden" id="topChangeId" value="${requestScope.groupInfoVO.topChangeId}"/>
<input type="hidden" id="parentId" value="${requestScope.groupInfoVO.parentId}"/>
<input type="hidden" id="isHasChild" value="${requestScope.isHasChild}"/>
<input type="hidden" id="isHasGroupUser" value="${requestScope.isHasGroupUser}"/>
<div class="formPage">
  <div class="formbg">
    <div class="big_title">修改部门</div>
    <div class="content_form">
    <form action="#" id="groupForm">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
        <tr>
          <th><em class="requireField">*</em><label>部门名称：</label></th>
          <td><input name="" type="text" class="formText"  id="groupName" maxlength="25"
						      valid="required" errmsg="group.group_name_not_null" value="${requestScope.groupInfoVO.groupName}"/>
						</td>
          <th><em class="requireField">*</em><label>电话：</label></th>
        <td><input name="" type="text" class="formText" maxlength="12" id="groupPhone" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"  valid="required|isPhone" errmsg="group.group_phone_not_null|group.group_phone_format_error" value="${requestScope.groupInfoVO.phone}"/>
						</td>
        </tr>
        <tr>
          <th><label>上级部门：</label></th>
        <td>
							<div id="treeContent" style="z-index:66;position:relative">
								<input id="groupSel" type="text" readonly="readonly" class="formText iconTree"   />
								<!-- <a class="icon_clear" href="#" id="parentRemove">清空</a>
								<span class="selectdot" id="groupSel_div"></span> -->
								<div id="menuContent" style="position: absolute;display: none;">
									<ul id="groupTree" class="ztree" style="position: absolute; margin-top: 0; width: 357px;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #8a9ba5"></ul>
								</div>
							</div>
						</td>
          <th><em class="requireField">*</em><label>部门排序号：</label></th>
        <td><input name="input2" type="text" class="formText"  maxlength="3" id="groupOrder" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" valid="required|isNumber" errmsg="group.group_order_not_null|group.group_order_format_error" value="${requestScope.groupInfoVO.orderIndex}"/></td>
        </tr>
        <tr>
          <th><label>部门主管：</label></th>
          <td><input type="text" class="readOnlyText blur-not-disabled" id="director" style="width:245px;" value="${requestScope.directorName}"/><span class="addMember"><a
							class="icon_add" href="#" id="directorSelect">选择</a></span>
						</td>
          <th><label>部门助理：</label></th>
          <td><input type="text" class="readOnlyText blur-not-disabled"  id="assistant" style="width:245px;" value="${requestScope.assistantName}"/><span class="addMember"><a
							class="icon_add" href="#" id="assistantSelect">选择</a></span>
						</td>
        </tr>
        <tr>
          <th><label>上级主管领导：</label></th>
          <td><input type="text" class="readOnlyText blur-not-disabled" id="topDirector" style="width:245px;" value="${requestScope.topDirectorName}"/><span  class="addMember"><a
							class="icon_add" href="#" id="topDirectorSelect">选择</a></span>
						</td>
          <th><label>上级分管领导：</label></th>
          <td><input name="" class="readOnlyText blur-not-disabled" type="text" style="width:245px;" id="topChange" value="${requestScope.topChangeName }" /><span  class="addMember">
          	<a
							class="icon_add" href="#" id="topChangeSelect">选择</a></span>
          </td>
        </tr>
        <tr style="display:none">
          <th><label>是否分支机构：</label></th>
          <td colspan="3"><label class="radio">
                       			<input type="radio" value="0" <c:if test="${empty groupInfoVO.isForkGroup || groupInfoVO.isForkGroup == 0}">checked</c:if> name="isForkGroup"/>否
                    		</label>
                    		<label class="radio">
                       			<input type="radio" value="1" <c:if test="${groupInfoVO.isForkGroup == 1}">checked</c:if> name="isForkGroup"/>是
                    		</label>
          </td>
        </tr>
        <tr>
          <th><label>部门职能：</label></th>
          <td colspan="3"><textarea  class="formTextarea" id="functions" >${requestScope.groupInfoVO.functions}</textarea></textarea></td>
        </tr>
      </table>
      </form>
    </div>
  </div>
  <div class="buttonArea">
    <input type="button" class="formButton_green" value="更新"  id="groupUpdate"/><span class="blue">点击更新，提交部门信息，*标记为必填项</span>
  </div>
</div>
</body>
</html>