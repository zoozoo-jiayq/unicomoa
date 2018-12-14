<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增联系人</title>
<jsp:include page="../../common/flatHead.jsp" />

<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var basePath = "${ctx}";
</script>
<script type="text/javascript"
	src="${ctx}js/logined/address/add_address.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}plugins/upload/uploadify.css" />
<script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}plugins/upload/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${ctx}js/logined/address/upload.js"></script>
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
<form id="form1">
<input id="groupId" type="hidden" value="${paramValues.groupId[0]}" />
<input id="groupType" type="hidden" value="${paramValues.groupType[0]}" />
<div class="formPage">
  <div class="formbg">
  <div class="big_title">新增人员信息</div>
    <div class="content_form">
      <h2 class="small_title">分组</h2>
      <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
        <tbody>
          <tr>
            <th><label>分组：</label></th>
            <td><select name="addressGroupId" id="addressGroupId"></select></td>
          </tr>
        </tbody>
      </table>
      <h2 class="small_title">个人信息</h2>
      <table width="100%" cellspacing="0" cellpadding="0" border="0" class="inputTable">
        <tbody>
          <tr>
            <th>排序号：</th>
            <td ><input id="orderNo" name="orderNo" type="text"
						class="formText"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						maxlength="9" /></td>
            <th>头像：</th>
            <td  rowspan="3">
			<dl class="myphoto" >
			<dt><em  id="deletePhoto"  class="close"></em><img id="photoImg" src="${ctx }flat/plugins/form/skins/default/meeting.png" /></dt>
			 <dd>
                  <h3><input id="file_upload" name="fileupload" type="file" multiple="true" /></h3>
                </dd>
                <dd>
                  <p>支持 .jpg .png格式图片，200K以内。</p>
                </dd>
					<input id="photo" type="hidden" />
						<div style="display: none;" id="queue"></div>
						</dl>		
		    </td>
          </tr>
          <tr>
            <th ><span class="requireField">*</span>姓名：</th>
            <td><input id="name" name="name" valid="required"
						errmsg="addressbook.msg_name_not_null" type="text"
						class="formText" maxlength="25" /> 
					</td>
          </tr>
          <tr>
            <th>性别：</th>
            <td><label class="radio">
                <input type="radio" value="1" name="sex" id="sex_1" checked="checked" />男
            </label>&nbsp;
            <label class="radio">
                <input type="radio" value="0" name="sex" id="sex_0" />女
            </label></td>
          </tr>
          <tr>
            <th>生日：</th>
            <td><input id="birthday" name="birthday" type="text"
						class="Wdate formText" 
						onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" /></td>
            <th>昵称：</th>
            <td><input id="nickname" name="nickname" type="text"
						maxlength="25" class="formText"  />
					</td>
          </tr>
          <tr>
            <th>职务：</th>
            <td><input id="postInfo" name="postInfo" type="text"
						maxlength="25" class="formText"  />
					</td>
            <th>配偶：</th>
            <td><input id="wifeName" name="wifeName" type="text"
						maxlength="25" class="formText"  />
					</td>
          </tr>
          <tr>
            <th>子女：</th>
            <td ><input id="children" name="children" type="text"
						maxlength="25" class="formText"  />
					</td>
            <td></td>
          </tr>
        </tbody>
      </table>
      <h2 class="small_title">联系方式</h2>
      <table width="100%" class="inputTable" border="0" cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
            <th ><label>单位名称：</label></th>
            <td ><input id="companyName" name="companyName" type="text"
						maxlength="200" class="formText"  />
					</td>
			<th><label>单位邮编：</label></th>
            <td><input id="companyPostCode" name="companyPostCode"
						maxlength="6"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						valid="limit" errmsg="addressbook.msg_post_limit" min="6"
						type="text" class="formText"  />
					</td>
          </tr>
          <tr>
            <th ><label>单位地址：</label></th>
            <td><input id="companyAddress" name="companyAddress"
						maxlength="200" type="text" class="formText" />
					</td>
          </tr>
          <tr>
          	<th><label>工作电话：</label></th>
            <td><input id="officePhone" name="officePhone" type="text"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						maxlength="12" valid="limit" errmsg="addressbook.msg_phone_limit"
						min="7" class="formText" />
					</td>
            <th><label>工作传真：</label></th>
            <td><input id="officeFax" name="officeFax"
						onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
						valid="limit" errmsg="addressbook.msg_fax_limit" min="7"
						maxlength="12" 
						type="text" class="formText" />
					</td>
			</tr>
			<tr>
								<th><span class="requireField">*</span> <label>手机：</label>
								</th>
								<td><input id="familyMobileNo" name="familyMobileNo"
									valid="required|isMobile"
									errmsg="addressbook.msg_phone_not_null|addressbook.msg_familyismobile_error"
									onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
									maxlength="11" type="text" class="formText" />
								</td>

								<th><label>电子邮件：</label>
								</th>
								<td><input name="input" type="text" class="formText"
									maxlength="50" valid="isEmail"
									errmsg="addressbook.msg_email_error" id="familyEmail"
									 /></td>
							</tr>
        </tbody>
      </table>
      <h2 class="small_title">备注</h2>
      <table width="100%" class="inputTable" border="0" cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
            <th>备注：</th>
            <td><textarea id="remark" name="remark"
							class="formTextarea"  rows="6"
							onkeyup="this.value = this.value.slice(0, 500)"
							onblur="this.value = this.value.slice(0, 500)"></textarea>
					</textarea></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
<div class="buttonArea"><input hideFocus="" id="add"
					value="保 存" type="button" class="formButton_green" /> &nbsp;&nbsp;<input hideFocus=""
					value="返 回" type="button"  class="formButton_grey" onclick="javascript: window.history.go(-1);" />
			</div>
</div>
</form>
</body>
</html>
