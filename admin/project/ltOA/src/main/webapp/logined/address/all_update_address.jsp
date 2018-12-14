<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>外部通讯录-修改</title>
<jsp:include page="../../common/flatHead.jsp" />

<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}flat/plugins/upload/uploadify.css" />
<script type="text/javascript" src="${ctx }js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/address/upload.js"></script>
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript" src="${ctx}js/logined/address/update_all_address.js"></script>

<style type="text/css">
.inputTable th{width:75px;}
</style>
<script type="text/javascript">
$(document).ready(function(){
   $(".myphoto dt").hover(function() {
		$(this).find(".close").fadeToggle();
	});
});
</script>


</head>
<body>
<input id="addressId" type="hidden"
		value='<s:property value="#request.address.id" />' />
	<input id="publicSign" type="hidden"
		value='<s:property value="#request.publicSign" />' />
<div class="formPage">
<form id="form1">
  <div class="formbg">
  <div class="big_title">修改人员信息</div>
    <div class="content_form">
      <h2 class="small_title">分组</h2>
      <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
        <tbody>
          <tr>
            <th><label>分组：</label></th>
            <td><input id="groupId" type="hidden"
					value='<s:property value="#request.address.addressGroupId" />' />
					<select name="addressGroupId" id="addressGroupId"></select></td>
          </tr>
        </tbody>
      </table>
      <h2 class="small_title">个人信息</h2>
      <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
        <tbody>
          <tr>
            <th>排序号：</th>
            <td ><input id="orderNo" name="orderNo" type="text"
					value='<s:property value="#request.address.orderNo" />'
					onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
					maxlength="9" class="formText"  /></td>
            <th>头像：</th>
            <td  rowspan="3">
				<dl class="myphoto">
                <dt><em  id="deletePhoto"  class="close"></em>
                	<c:if test="${address.photo == ''}">
			    			<img src="${ctx}flat/plugins/form/skins/default/meeting.png" id="photoImg"/>
		             </c:if>
		             <c:if test="${address.photo != ''}">
			    			<img src="${ctx}filemanager/prevViewByPath.action?filePath=${requestScope.address.photo}" id="photoImg"/>
		                </c:if>
                </dt>
                <dd>
                  <h3><input id="file_upload" name="file_upload" type="file" multiple="true" /></h3>
                </dd>
                <dd>
                  <p>支持 .jpg .png格式图片，200K以内。</p>
                </dd>
                <div id="img_queue"></div>
			    			<input type="hidden" id="photo" value="${address.photo}" />
              </dl>
					</td>
          </tr>
          <tr>
            <th ><span class="requireField">*</span>姓名：</th>
            <td><input id="name" type="text" class="formText" 
					valid="required" errmsg="addressbook.msg_name_not_null"
					maxlength="25" value="<s:property value="#request.address.name" />" /></td>
          </tr>
          <tr>
            <th>性别：</th>
            <td><label class="radio">
                       <input type="radio" value="1" name="sex"
                         <s:if test="#request.address.sex==1">checked="checked"</s:if>  id="sex_1" />男
                    </label>
              <label class="radio">
                        <input type="radio" value="0" name="sex" 
                          <s:if test="#request.address.sex==0">checked="checked"</s:if>  id="sex_0"/>女
               </label></td>
          </tr>
          <tr>
            <th>生日：</th>
            <td><input id="birthday" name="birthday" type="text" class="Wdate formText" 
				onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" value="<s:date name="#request.address.birthday" format="yyyy-MM-dd"/>" /></td>
            <th>昵称：</th>
            <td><input id="nickname" name="" type="text" class="formText"
					 maxlength="25"
					value='<s:property value="#request.address.nickname" />' /></td></td>
          </tr>
          <tr>
            <th>职务：</th>
            <td><input id="postInfo" type="text" class="formText" 
					maxlength="25"
					value="<s:property value="#request.address.postInfo" />" /></td>
            <th>配偶：</th>
            <td><input id="wifeName" name="wifeName" type="text"
					class="formText" maxlength="25" 
					value="<s:property value="#request.address.wifeName" />" /></td>
          </tr>
          <tr>
            <th>子女：</th>
            <td ><input id="children" type="text" class="formText" 
					maxlength="25"
					value='<s:property value="#request.address.children" />' /></td>
            
            <td></td>
          </tr>
        </tbody>
      </table>
      <h2 class="small_title">联系方式</h2>
      <table width="100%" class="inputTable" border="0" cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
            <th ><label>单位名称：</label></th>
            <td ><input id="companyName" name="companyName"
					value='<s:property value="#request.address.companyName" />'
					maxlength="200" type="text" class="formText"  /></td>
			<th><label>单位邮编：</label></th>
            <td><input id="companyPostCode" name="companyPostCode"
					value='<s:property value="#request.address.companyPostCode" />'
					maxlength="6"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						valid="limit" errmsg="addressbook.msg_post_limit" min="6"
					type="text" class="formText"  /></td>
		  </tr>
          <tr>		
            <th ><label>单位地址：</label></th>
            <td><input id="companyAddress" name="companyAddress"
					value='<s:property value="#request.address.companyAddress" />'
					maxlength="200" 
					type="text" class="formText"  /></td>
          </tr>
          <tr>
            
            <th><label>工作电话：</label></th>
            <td><input id="officePhone" name="officePhone"
					value='<s:property value="#request.address.officePhone" />'
					onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						maxlength="12" valid="limit" errmsg="addressbook.msg_phone_limit"
						type="text" class="formText" /></td>
			<th><label>工作传真：</label></th>
            <td><input id="officeFax" name="officeFax"
					value='<s:property value="#request.address.officeFax" />'
					onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						valid="limit" errmsg="addressbook.msg_fax_limit" min="7" maxlength="12" 
					type="text" class="formText"   /></td>
          </tr>
          <tr>
           
            <th><span class="requireField">*</span>
              <label>手机：</label>
            </th>
            <td><input id="familyMobileNo" name="familyMobileNo"
						valid="required|isMobile" errmsg="addressbook.msg_phone_not_null|addressbook.msg_familyismobile_error"
						value='<s:property value="#request.address.familyMobileNo" />'
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						maxlength="11" type="text" class="formText"  /></td>
			<th><label>电子邮件：</label></th>
            <td>
            	<input name="input" type="text" class="formText" maxlength="50" valid="isEmail"
                           errmsg="addressbook.msg_email_error" id="familyEmail" value="${address.familyEmail}"/>
            </td>
          </tr>
        </tbody>
      </table>
      <h2 class="small_title">备注</h2>
      <table width="100%" class="inputTable" border="0" cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
            <th>备注：</th>
            <td><textarea id="remark" class="formTextarea"
						 rows="6" onkeyup="this.value = this.value.slice(0, 500)"
							onblur="this.value = this.value.slice(0, 500)"><s:property value="#request.address.remark" /></textarea></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="buttonArea"><input id="updateAddress" class="formButton_green"
				 value="保 存" type="submit" />&nbsp;&nbsp;<input  value="返 回"
				type="button" onclick="javascript: window.location.href = document.referrer;" class="formButton_grey" />
		</div>
 </form>
</div>
</body>
</html>
