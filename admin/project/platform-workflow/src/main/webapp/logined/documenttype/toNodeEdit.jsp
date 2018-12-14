<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/myworks.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/plugins/smallTab/skins/smallTab.js"></script>
<script type="text/javascript" src="${ctx }flat/plugins/smallTab/skins/smallTab.js"></script>
<script type="text/javascript" src="${ctx }/js/logined/documentType/toNodeEdit.js"></script>
<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
<style type="text/css">
.inputTable th{width:75px;}
</style>
</head>
<body>
<form id='from' action="${ctx}/documentType/updateNode.action" method="post">
<input type="hidden" name='doctypeId' value="${doctypeId }"/>
    <input type="hidden" id="nodeId" name="nodef.id" value="${nodeId}"/>
    <input type="hidden" id="userIds" name="nodef.candidate" value="${node.candidate}"/>
    <input type="hidden" id="groupIds" name="nodef.depts" value="${node.depts}"/>
    <input type="hidden" id="roleIds" name="nodef.roles" value="${node.roles}"/>
    <input type="hidden" name="nodef.nodeName" value="${node.nodeName}" />
    <input type="hidden" name="nodef.writeableProperties" value="${node.writeableProperties}" id="writeableProperties"/>
    <input type="hidden" name="nodef.secretProperties" value="${node.secretProperties}" id="secretProperties"/>
    <input type="hidden" name="nodef.nodeType" value="${node.nodeType}"/>
    <input type="hidden" name="nodef.descri" value="${node.descri}"/>
    <input type="hidden" name="nodef.nodeOrder" value="${node.nodeOrder}"/>
    <input type="hidden" name="typef" value="${type}"/>  
    <input type="hidden" name="nodef.isMydepCanAccept" value="${node.isMydepCanAccept}"/>
<div class="formPage">
  <div class="formbg">
      <div class="big_title">步骤：属性设置</div>
      <div class="my_jdsz">
           <div class="left_mj">
               <div class="tab">
                    <ul>
                      <li id='candidate' class="current"><a href="javascript:void(0);">经办权限</a></li>
                      <li id='write'><a href="javascript:void(0);">可写字段</a></li>
                      <li id='secret'><a href="javascript:void(0);">保密字段</a></li>
                      <!-- <li id="editDoc"><a href="javascript:void(0);">其它设置</a></li> -->
                  </ul>
                  </div>
           </div>
           <div class="right_mj">
                  <div class="tabContent">
                       <p class="tip">设置经办权限（经办权限为“部门”、“角色”、“人员”三者的合集）</p>
                       <table border="0" class="inputTable" style="width:100%">
                            <tr>
				            <th ><label>授权范围：<br />（人员）</label></th>
				            <td><textarea name="textarea" cols="40" rows="3"    readonly="readonly" class="readOnlyTextarea" id="users"><c:forEach items="${userInfos}" var="u">${u.userName},</c:forEach></textarea><span class="addMember"><a class="icon_add" href="#" id="addUser">添加</a><a  class="icon_clear" href="#" id="removeUser" >清空</a></span></td>
				        </tr>
				        <tr>
				            <th><label>授权范围：<br />（部门）</label></th>
				            <td><textarea name="textarea" cols="40" rows="3"    readonly="readonly"  class="readOnlyTextarea" id="groups"><c:forEach items="${groupInfos}" var="g">${g.groupName},</c:forEach></textarea><span class="addMember"><a class="icon_add" href="#" id="addGroup">添加</a><a  class="icon_clear" href="#" id="removeGroup" >清空</a></span></td>
				        </tr>
				        <tr>
				            <th><label>授权范围：<br />（角色）</label></th>
				            <td><textarea name="textarea" cols="40" rows="3"     readonly="readonly"  class="readOnlyTextarea" id="roles"><c:forEach items="${roleInfos}" var="r">${r.roleName},</c:forEach></textarea><span class="addMember"><a class="icon_add" href="#" id="addRole">添加</a><a  class="icon_clear" href="#" id="removeRole" >清空</a></span></td>
				        </tr>
                       </table>
                  </div>
                  <div class="tabContent" style="display:none">
                         <p class="tip">编辑可写字段(可写字段对于本步骤主办人、经办人均为可写)</p>
                          <div class="bmzd ">
                               <div class="listbox fl">
                                 <h2>本步骤可写字段</h2>
                                     <select  multiple="multiple" class="selelist" id="writeAbles" >
										<c:forEach items="${writeAbleProperties}" var="formp">
											<option value="${formp.propertyId}">${formp.propertyNameCh}</option>
										</c:forEach>
				  					</select>
                                  <p>
                                     <input type="button" id="allW" value="全选" />
                                  </p>   
                              </div>
                                <div class="btn_box fl">
						             <a style="cursor:pointer;"  id="wleftSelect" ><img src="${ctx }flat/images/icon/l_jt.png"></a>
						             <a style="cursor:pointer;"    id="wrightSelect" ><img src="${ctx }flat/images/icon/r_jt.png"></a>
						        </div>
                              <div class="listbox fl">
                                  <h2>备选字段</h2>
                    				 <select multiple="multiple" id="wprops" class="selelist"  >
										<c:forEach items="${formProperties}" var="fp">
											<option value="${fp.propertyId}">${fp.propertyNameCh}</option>
										</c:forEach>
									</select>
					              <p>
					                <input type="button" id="allWriteProps"   value="全选" />
					              </p>   
                              </div>
                              <div class="clear"></div>
                              <div class="explain">注：点击条目时，可以组合CTRL或SHIFT键进行多选</div>
                        </div>
                  </div>
                   <div class="tabContent" style="display:none">
                        <p class="tip">编辑保密字段(保密字段对于本步骤主办人、经办人均为不可见)</p>
                          <div class="bmzd ">
                               <div class="listbox fl">
                                 <h2>本步骤可写字段</h2>
                                     <select  multiple="multiple" class="selelist" id="secretAbles" >
										<c:forEach items="${secretProperties}" var="formp">
											<option value="${formp.propertyId}">${formp.propertyNameCh}</option>
										</c:forEach>
							  		</select>
                                  <p>
                                   <input type="button" id="allS" value="全选" />
                                  </p>   
                              </div>
                                <div class="btn_box fl">
					             <a style="cursor:pointer;"  id="sleftSelect" ><img src="${ctx }flat/images/icon/l_jt.png"></a>
					             <a style="cursor:pointer;"    id="srightSelect" ><img src="${ctx }flat/images/icon/r_jt.png"></a>
					          </div>
                              <div class="listbox fl">
                                 <h2>备选字段</h2>
                                       <select multiple="multiple" id="sprops" class="selelist"  >
											<c:forEach items="${formProperties}" var="fp">
												<option value="${fp.propertyId}">${fp.propertyNameCh}</option>
											</c:forEach>
									</select>
                                  <p>
                                     <input type="button" id="allSecretProps"   value="全选" />
                                  </p>   
                              </div>
                              <div class="clear"></div>
                              <div class="explain">注：点击条目时，可以组合CTRL或SHIFT键进行多选</div>
                        </div>
                  </div>
                  <div class="tabContent" style="display:none">
                  		<table>
                  			<tr>
                  				<th>编辑正文</th>
                  				<td><label><input type="radio" name="nodef.editDoc" value="1" <c:if test="${node.editDoc==1 }">checked</c:if> />允许</label><label><input type="radio" name="nodef.editDoc" value="2" <c:if test="${node.editDoc==2 }">checked</c:if>/>禁止</label></td>
                  			</tr>
                  		</table>
                  </div>
           </div>
       </div>
          
  </div>
  <div class="buttonArea">
    <input type="button" class="formButton_green" value="确定"  id="sure" hidefocus=""/>
    <input type="button" value="返回" class="formButton_grey"  hidefocus="" onclick="javascript:history.back();"/>
  </div>
</div>
</form>
</body>
</html>
