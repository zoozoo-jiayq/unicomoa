<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>更新联系人信息</title>
<%@ include file="../../common/taglibs.jsp"%>
<jsp:include page="../../common/head.jsp" />
<script type="text/javascript">
	var basePath = "${ctx}";
	$(document).ready(function(){
		   $(".myphoto dt").hover(function() {
				$(this).find(".close").fadeToggle();
			});
		});
</script>
<script type="text/javascript"
	src="${ctx}/js/logined/address/update_address.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}plugins/upload/uploadify.css" />
<script type="text/javascript"
		src="${ctx}plugins/upload/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/address/upload.js"></script>
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>

</head>

<body>
	<input id="addressId" type="hidden"
		value='<s:property value="#request.address.id" />' />
	<input id="photo" type="hidden"
		value='<s:property value="#request.address.photo" />' />
	
	<div class="list" style="margin-top: 3px;">
		<table cellpadding="0" cellspacing="0" class="BlockTop">
			<tr>
				<td class="left"></td>
				<td class="center">详情</td>
				<td class="right"></td>
			</tr>
		</table>
		<div class="TableBox">
		  <form id="form1">
			<div class="info_details gril" style="height: auto;">
			<div class="touxiang"><a href="javascript:void(0)" class="canclePic" id="deletePhoto"></a><img id="photoImg" /></div>				
				<div style="padding-left: 137px">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="inputTable1">
						<tr>
							<th style="width: 70px"><span class="requireField">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</th>
							<td><input id="name" type="text" class="formText" size="30" maxlength="25" 
							    valid="required"
					   	        errmsg="addressbook.msg_name_not_null" 
								value="<s:property value="#request.address.name" />" />
								</td>
							<th style="width: 40px">昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</th>
							<td><input type="text" id="nickname" maxlength="25"  class="formText" size="30" value="<s:property value="#request.address.nickname" />" />
							</td>
						</tr>
						<tr>
							<th>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</th>
							<td><select id="sex" name="sex">
							<option  value="">--请选择--</option>
							<option <s:if test="#request.address.sex==1">selected="selected"</s:if> value='1'>
								男
							</option>
							<option <s:if test="#request.address.sex==0">selected="selected"</s:if> value='0'>
								女
							</option>
						</select>
							</td>
							<th>生&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</th>
							<td><input id="birthday" name="birthday" type="text" class="Wdate formText"
								onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})"  size="30"
								value="<s:date name="#request.address.birthday" format="yyyy-MM-dd"/>" />
							</td>
						</tr>
						<tr>
							<th>工作单位</th>
							<td><input id="companyName" name="companyName" value='<s:property value="#request.address.companyName" />'
							     maxlength="200"  type="text" class="formText" size="30" />
							</td>
							<th>职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务</th>
							<td><input id="postInfo" name="postInfo"  type="text" value='<s:property value="#request.address.postInfo" />' 
							    maxlength="25" class="formText" size="30" />
							</td>
						</tr>
						<tr>
							<th>照片上传</th>
							<td colspan="3"><input id="file_upload" name="fileupload"  style="display: none;"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<ul class="tab">
				<li class="current"><a href="#">个人信息</a></li>
				<li><a href="#">其他信息</a></li>
				<li><a href="#">备注</a>
				</li>
			</ul>
			<div class="tabContent" style="display: block;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="inputTable mt5">
					<tr>
						<th>单位地址</th>
						<td><input id="companyAddress" name="companyAddress" value='<s:property value="#request.address.companyAddress" />' 
						     maxlength="200" type="text" class="formText" size="30" />
						</td>
						<th>单位邮编</th>
						<td><input id="companyPostCode" name="companyPostCode" value='<s:property value="#request.address.companyPostCode" />' 
						type="text" class="formText" size="30" 
						maxlength="6"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						valid="limit" errmsg="addressbook.msg_post_limit" min="6"/>
						</td>
					</tr>
					<tr>
						<th>家庭地址</th>
						<td><input id="familyAddress" name="familyAddress" value='<s:property value="#request.address.familyAddress" />' 
						     maxlength="200"  type="text" class="formText" size="30" />
						</td>
						<th>家庭邮编</th>
						<td><input id="familyPostCode" name="familyPostCode" value='<s:property value="#request.address.familyPostCode" />' 
						    type="text" class="formText" size="30" 
						    maxlength="6"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						valid="limit" errmsg="addressbook.msg_familyPostCode_limit"
						min="6" />
						</td>
					</tr>
					<tr>
						<th>配&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;偶</th>
						<td><input id="wifeName" name="wifeName"  type="text" value='<s:property value="#request.address.wifeName" />' 
						maxlength="25" class="formText" size="30" />
						</td>
						<th>子&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;女</th>
						<td><input id="children" name="children"  type="text" value='<s:property value="#request.address.children" />' 
						maxlength="25" class="formText" size="30" />
						</td>
					</tr>
					<tr>
						<th>电子邮件</th>
						<td><input id="familyEmail" name="familyEmail" value='<s:property value="#request.address.familyEmail" />' 
						type="text" class="formText" size="30" 
						valid="isEmail"
						errmsg="addressbook.msg_email_error" 
						maxlength="200"/>
						</td>
						<th>手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机</th>
						<td><input id="familyMobileNo" name="familyMobileNo" value='<s:property value="#request.address.familyMobileNo" />' 
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
					valid="isMobile" errmsg="addressbook.msg_familyismobile_error"
						maxlength="11" type="text" class="formText" size="30" />
						</td>
					</tr>
					<tr>
						<th>工作电话</th>
						<td><input id="officePhone" name="officePhone" value='<s:property value="#request.address.officePhone" />' 
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						maxlength="12" valid="limit" errmsg="addressbook.msg_phone_limit"
						min="7" type="text" class="formText" size="30" />
						</td>
						<th>工作传真</th>
						<td><input id="officeFax" name="officeFax" value='<s:property value="#request.address.officeFax" />' 
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						valid="limit" errmsg="addressbook.msg_fax_limit" min="7" maxlength="12"  type="text" class="formText" size="30" />
						</td>
					</tr>
					<tr>
						<th>家庭电话</th>
						<td colspan="3"><input id="familyPhoneNo" name="familyPhoneNo" value='<s:property value="#request.address.familyPhoneNo" />'
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						maxlength="12" valid="limit"
						errmsg="addressbook.msg_familyphone_limit" min="7" type="text" class="formText" size="30" />
						</td>
					</tr>
				</table>
			</div>
			<div class="tabContent">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="inputTable1">
					<tr>
						<th>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;组</th>
						<input id="groupId" type="hidden" value='<s:property value="#request.address.addressGroupId" />'   />
						<td><select name="addressGroupId" id="addressGroupId">
						</select>
						</td>
					</tr>
					<tr>
						<th>排序号</th>
						<td><input id="orderNo" name="orderNo" type="text" value='<s:property value="#request.address.orderNo" />' 
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
					      maxlength="9"  class="formText" size="30" />
						</td>
					</tr>
				</table>
			</div>
			<div class="tabContent">
			<div class="m_all_10">	<textarea id="remark" class="formTextarea" cols="100" rows="6" onkeyup="this.value = this.value.slice(0, 500)"
							onblur="this.value = this.value.slice(0, 500)"><s:property value="#request.address.remark" /></textarea>
			</div>
			</div>
		</form>
			<div class="buttonArea">
				<input id="updateAddress"  hideFocus="" value="保 存" class="formButton_green"
					type="button" />&nbsp;&nbsp;<input hideFocus=""
					id="backBtn" value="返 回" type="button" class="formButton_grey" />
			</div>
		</div>
	</div>
	
</body>
</html>