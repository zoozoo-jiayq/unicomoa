<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人员添加</title>
    <jsp:include page="../../common/flatHead.jsp"/>
    <link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${ctx}flat/plugins/tree/skins/tree_default.css" type="text/css"/>
   <script type="text/javascript" src="${ctx}plugins/tree/ztree/jquery.ztree.all-3.5.min.js"></script>
   <link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css"/>
    <script type="text/javascript" src="${ctx}js/logined/user/otherGroup.js"></script>
    <script type="text/javascript" src="${ctx}js/logined/user/userSign.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/user/userUpdate.js"></script>
    <script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
    <script type="text/javascript" src="${ctx}js/logined/user/userTree.js"></script>

    <script type="text/javascript" src="${ctx}/js/logined/user/selectUser.js"></script>
    <script type="text/javascript" src="${ctx}/js/user/selectuser.js"></script>
    <script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
    <script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}js/user/selectGroup.js"></script>
    <script type="text/javascript" language="javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js" ></script>
    <script type="text/javascript" src="${ctx}js/logined/user/upload.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	   $(".myphoto dt").hover(function() {
			$(this).find(".close").fadeToggle();
		});
	});
	</script>
</head>
<body>
 <form action="#" id="userForm">
<input type="hidden" id="userId" value="${requestScope.user.userId}"/>
<input type="hidden" id="roleId" value="${requestScope.roleIds}"/>
<input type="hidden" id="assistId" value="${requestScope.assistIds}"/>
<input type="hidden" id="groupId" value="${requestScope.groupId}"/>
<input type="hidden" id="defaultgroupId" value="${requestScope.groupId}"/>
<div class="formPage">
  <div class="formbg">
    <div class="big_title">编辑人员</div>
     
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
        <tr>
          <th><em class="requireField">*</em><label>姓名：</label></th>
          <td><input type="text" class="formText" maxlength="16" valid="required"
                           errmsg="user.user_userName_not_null" id="userName" value="${requestScope.user.userName}"/></td>
          <th rowspan="3">头像：</th>
                            <td rowspan="3">
                                 <dl class="myphoto">
                                       <%--<a href="javascript:void(0)" class="canclePic" id="deletePhoto" style="display:none"></a>
			    		<c:if test="${requestScope.user.photo == ''}">
			    			<img src="${ctx}/images/default_photo.png" id="photoImg"/>
		                </c:if>
			    		<c:if test="${requestScope.user.photo != ''}">
			    			<img src="${ctx}filemanager/prevViewByPath.action?filePath=${requestScope.user.photo}" id="photoImg"/>
		                </c:if>
			
			    		<div class="center top5">
			        		<input id="file_upload" name="file_upload" type="file" multiple="true" />
			
			    		<div id="img_queue"></div>
			    			<input type="hidden" id="photo" value="${requestScope.user.photo}" />
			        	</div>
                                 --%>
                                <dt><em class="close"></em>
                                	<c:if test="${requestScope.user.photo == '' || requestScope.user.photo == null }">
                                		<img src="${ctx }flat/plugins/form/skins/default/meeting.png" width="107" height="107" id="photoImg"  />
                                	</c:if>
                                	<c:if test="${requestScope.user.photo != '' && requestScope.user.photo != null}">
						    			<img src="${ctx}filemanager/prevViewByPath.action?filePath=${requestScope.user.photo}" id="photoImg" width="107" height="107"/>
					                </c:if>
                                </dt>
                                       <dd><h3><input id="file_upload" name="file_upload" type="file" multiple="true" /></h3></dd>
                                       <dd><p>支持 .jpg .png格式图片，200KB以内</p></dd>
                                       <input type="hidden" id="photo" value="${requestScope.user.photo}" />
                                 </dl>
          </td>
        </tr>
 <tr style="display: none;">
                <th><em class="requireField">*</em><label>主&nbsp;&nbsp;角&nbsp;&nbsp;色</label></th>
                <td><input type="text" class="readOnlyText blur-not-disabled" size="30" id="role" maxlength="20"
                          value="${requestScope.roleNames}"/>
                        <c:if test="${requestScope.user.isDefault!=0}">
                        <a  class="icon_add" href="#" id="roleSelect">添加</a><a
                        class="icon_clear" href="#" id="roleRemove">清空</a><a href="javascript:void(0)" id="showAssist">指定辅助角色</a>
                        </c:if>
                </td>
            </tr>
            <tr style="display: none;" id="assistContent">
                <th><label>辅助角色</label></th>
                <td><input type="text" class="readOnlyText" size="30" id="assist" value="${requestScope.assistNames}"
                           disabled="disabled"/><a
                        class="icon_add" href="#" id="assistSelect">添加</a><a
                        class="icon_clear" href="#" id="assistRemove">清空</a>
                </td>
            </tr>
        <tr>
        	<th><em class="requireField">*</em><label>单位/部门：</label></th>
            <td> <c:if test="${requestScope.user.isDefault!=0}"><input id="groupSel" type="text" readonly="readonly" class="formText iconTree" />
                        <span class="selectdot" id="groupSel_div"></span>
							<div id="menuContent" style="position: absolute;">
								<ul id="groupTree" class="ztree"
									style="position: absolute; margin-top: 0; width: 375px;height:150px;overflow:auto; 
background: #ffffff;  border: 1px solid #8a9ba5;z-index:1000"></ul>
							</div>
	 </c:if>
	  <c:if test="${requestScope.user.isDefault==0}">
                     <input id="groupSel" type="text" readonly="readonly"  
                        class="formText" value="${requestScope.groupName}" />
                </c:if>

</td>
        </tr>
        <tr>
        	<th><em class="requireField">*</em><label>手机号码：</label></th>
            <td><input name="input" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" class="formText" maxlength="11" id="phone"
                           valid="required|isMobile" errmsg="user.user_phone_not_null|user.user_phone_format_error"
                           value="${requestScope.user.phone}"
                           /></td>
        </tr>
        <tr>
        	<th><label>性别：</label></th>
            <td><label class="radio"><input type="radio" value="1" name="sex"
                         <s:if test="#request.user.sex==1">checked="checked"</s:if>  id="sex_1" />男</label>
          	  <label class="radio"> <input type="radio" value="0" name="sex" 
                          <s:if test="#request.user.sex==0">checked="checked"</s:if>  id="sex_0"/>女</label>
          </td>
          <th><label>别名：</label></th>
          <td><input name="input" type="text" class="formText" maxlength="16" id="alterName"
                           value="${requestScope.user.alterName}"/></td>
        </tr>
        <tr>
          <th><label>生日：<fmt:parseDate value="${requestScope.user.birthDay}" var="date"
                                             pattern="yyyy-MM-dd HH:mm:ss.S"/></label></th>
          <td><input name="input" type="text" class="formText Wdate" 
                           onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" id="birthDay"
                           value='<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>'/></td>
          <th><label>电子邮件：</label></th>
          <td><input name="input" onkeyup="value=value.replace(/[\u4E00-\u9FA5]/g,'')" type="text" class="formText" maxlength="50" valid="isEmail"
                           errmsg="user.user_email_format_error" id="email" value="${requestScope.user.email}"/></td>
        </tr>
        <tr>
          <th><label>办公电话：</label></th>
          <td><input name="input" type="text" class="formText"  maxlength="12" valid="isPhone"
                           errmsg="user.user_officeTel_format_error" id="officeTel" value="${requestScope.user.officeTel}"
                           /></td>
          <th><label>排序号：</label></th>
          <td><input name="input" type="text" class="formText" maxlength="5"
                           onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" id="userOrder"
                           value="${requestScope.user.orderIndex}"/></td>
        </tr>
        <tr>
           <th><label>职务：</label></th>
          <td><input name="input" type="text" class="formText" size="30" maxlength="10" id="job" value="${user.job }"/></td> 
          <th><label>个性签名：</label></th>
          <td colspan="1"><select id="userSign" style="float:left">
                        <option value="0" <c:if test="${user.signType == 0}">selected</c:if>>不启用</option>
                        <option value="1" <c:if test="${user.signType == 1}">selected</c:if>>文字签名</option>
                        <option value="2" <c:if test="${user.signType == 2}">selected</c:if>>图片签名</option>
                    </select>
                     <table id="contentSign" style="display:none;margin-top: -10px"  class="up_tb fl">
                        <tr>
                        	<td align="left">
                        	<label>姓名+时间(月日)</label>
                        	</td>
                        </tr>
                    </table>
         <table id="signContent" style="display:none;margin-top: -5px"  class="up_tb fl">
                        <tr>
                            <td align="left"><input type="file" id="userSign_upload" style="float:left"/></td>
                            <td align="left">
                                <img id="imgContent" border="0" width="120px" height="40px"   src="${ctx}images/qm.jpg"/>
                                <input type="hidden" id="imgSignUrl" value="${user.signUrl}"/>
                            </td>
                        </tr>
                    </table>
      		</td>
      		</tr>
      		<tr>
	          <th><label>集团短号：</label></th>
	          <td><input name="input" type="text" class="formText"  maxlength="12" id="workNo" value="${requestScope.user.workNo}"
	                           /></td>
	          <th><label>号码隐藏：</label></th>
	          <td><select id="hidePhone" style="float:left">
                        <option value="0" <c:if test="${user.hidePhone == 0}">selected</c:if>>关闭</option>
                        <option value="1" <c:if test="${user.hidePhone == 1}">selected</c:if>>开启</option>
                    </select></td>
	        </tr>
	        <tr>
	          <th><label>旧集团短号：</label></th>
	          <td><input name="input" type="text" class="formText"  maxlength="12" id="oldWorkNo" value="${requestScope.user.homeTel}"
	                            onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/></td>
	          <th><label>旧电话号：</label></th>
	          <td><input name="input" type="text" class="formText"  maxlength="12" id="oldPhone" value="${requestScope.user.phone2}"
	                            onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/></td>

	        </tr>
	        
	        
	        
      		<tr>
	          <th><label>高管模式：</label></th>
	          <td><select id="isLeader" style="float:left">
                        <option value="0" <c:if test="${user.isLeader == 0}">selected</c:if>>关闭</option>
                        <option value="1" <c:if test="${user.isLeader == 1}">selected</c:if>>开启</option>
                    </select></td>
	        </tr>
      		</table>
    </div>
    
    <div class="big_title" style="padding-bottom:5px">
		从属部门设置<div class="fButton blueBtn" style=" display:inline-block;float:none; vertical-align: middle;">
			<div> <span class="add" onclick="addOtherGroup('add')">新增</span> </div>
		
	</div></div>
	
	<p class="clear"></p>
	  <div class="content_form">
	<table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="otherGroupTable">
		<thead>
			<tr>
			<th style="width:100px;">部门名称</th>
			<th style="width:60%;">职务</th>
			<th style="width:40%;">办公电话</th>
			<th style="width:120px;">排序号</th>
			<th class="right_bdr0" style="width:110px;">操作</th>
			</tr>
		</thead>
		<tbody>
			<tr class="odd">
				<td colspan="5">暂无数据</td>
			</tr>
		</tbody>
	</table>
   </div> 
    
    
    
    <div class="big_title" style="display:none;">控件设置</div>
    <div class="content_form" style="display:none;">
    	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
          <tr>
            <th>印章控件：</th>
            <td><select name="sign" id="sign"><option value="0" <c:if test="${requestScope.user.sinWidget == 0}">selected</c:if>>不启用</option><option value="1" <c:if test="${requestScope.user.sinWidget == 1}">selected</c:if>>启用</option></select>
            </td>
            <th>OFFICE控件：</th>
            <td><select name="office" id="office"><option value="0" <c:if test="${requestScope.user.officeWidget == 0}">selected</c:if>>不启用</option><option value="1" <c:if test="${requestScope.user.officeWidget == 1}">selected</c:if>>启用</option></select>
            </td>
            </tr>
          <tr>
            <th>套打控件：</th>
            <td colspan="3"><select name="print" id="print"><option value="0" <c:if test="${requestScope.user.taoDa == 0}">selected</c:if>>不启用</option><option value="1" <c:if test="${requestScope.user.taoDa == 1}">selected</c:if>>启用</option></select>
            </td>
          </tr>
        </table>
    </div>
    
  </div>
  
  <div class="buttonArea">
    <input type="button" class="formButton_green" value="确定"  id="userInfoUpdate"/>
   <c:if test="${requestScope.source!='tree'}">
						<input value="返回" class="formButton_grey" type="button" id="back"/>
             			</c:if>
  </div>
</div>
</form>
</body>
</html>