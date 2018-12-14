<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>单位信息</title>
<jsp:include page="../../common/flatHead.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}plugins/upload/uploadify.css" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/annex/skins/annex_default.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeToolsTop.js"></script>
<script type="text/javascript" src="${ctx }js/common.js"></script>
<script type="text/javascript" src="${ctx}js/logined/systemset/companySet.js"></script>
<style type="text/css">
.inputTable th {
	width: 85px;
}

.uploadify {
	padding-top: 5px;
}
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
	<div class="formPage">
		<div class="formbg">
			<div class="big_title">单位信息</div>
			<div class="content_form">
			<form id="form1">
				<input type="hidden" name="company.companyId" id="companyId"
					value="${companyInfo.companyId }" /> <input type="hidden"
					name="company.logUrl" id="logUrl" value="${companyInfo.logUrl }" />
				<table width="100%" cellspacing="0" cellpadding="0" border="0"
					class="inputTable">
					<tbody>
						<tr>
							<th><em class="requireField">*</em><label>单位名称：</label>
							</th>
							<td><input type="text"
									maxlength="50" id="companyName" name="company.companyName"
									valid="required" errmsg="company.company_companyName_not_null"
									onkeyup="cleanSpelChar(this)" class="formText"
									value="${companyInfo.companyName }"/>
							</td>
								<th>单位LOGO：</th>
								<td rowspan="3"><dl class="myphoto">
										<dt><em class="close"></em>
											<img src="${ctx}/flat/images/hy.png" width="107" height="107" id="view"/>
										</dt>
										<dd>
											<h3><input id="file_upload" name="file_upload" type="file" multiple="true" /></h3>
										</dd>
										<dd><p>支持 .jpg .png格式图片，200KB以内</p></dd>
									</dl>
							</td>
						</tr>
						<tr>
							<th><em class="requireField">*</em><label>单位简称：</label>
							</th>
							<td><input name="company.shortName" type="text"
								maxlength="6" id="shortName" valid="required"
								errmsg="company.company_shortName_not_null"
								value="${companyInfo.shortName }" class="formText"/>
							</td>

						</tr>
						<tr>
							<th><em class="requireField">*</em><label>系统名称：</label>
							</th>
							<td><input name="company.sysName" type="text" maxlength="15"
								id="sysName" valid="required"
								errmsg="company.company_sysName_not_null"
								value="${companyInfo.sysName }" class="formText"/>
							</td>
						</tr>
						<tr>
							<th><label>电子邮件：</label>
							</th>
							<td><input name="company.email" onkeyup="value=value.replace(/[\u4E00-\u9FA5]/g,'')" type="text" valid="isEmail"
								maxlength="50" errmsg="record.must_be_right_email" id="email"
								value="${companyInfo.email }" class="formText" />
							</td>
							<th><label>联系人：</label>
							</th>
							<td><input name="company.linkMan" type="text" maxlength="50"
								id="linkMan" value="${companyInfo.linkMan }" class="formText"
								/>
							</td>
						</tr>
						<tr>
							<th><label>联系电话：</label>
							</th>
							<td><input name="company.tel" type="text"
								valid="isPhoneSimple"   onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" errmsg="record.must_be_right_phone_no"
								maxlength="12" id="tel" value="${companyInfo.tel }"
								class="formText"/>
							</td>
							<th><label>单位地址：</label>
							</th>
							<td><input name="company.address" type="text" maxlength="50"
								id="address" value="${companyInfo.address }" class="formText"
								/>
							</td>
						</tr>
						<tr>
							<th><label>单位简介：</label>
							</th>
							<td colspan="3"><textarea   onkeyup="this.value = this.value.slice(0, 200)"  onblur="this.value = this.value.slice(0, 200)"
									class="formTextarea" name="company.introduction"
									id="introduction">${companyInfo.introduction }</textarea>
							</td>
						</tr>

						<tr>
							<th><label>公司理念：</label>
							</th>
							<td colspan="3"><textarea   onkeyup="this.value = this.value.slice(0, 200)"  onblur="this.value = this.value.slice(0, 200)"
									class="formTextarea" name="company.philosophy"
									id="philosophy">${companyInfo.philosophy }</textarea>
							</td>
						</tr>

					</tbody>
				</table>
				</form>
			</div>
			
		</div>
		<div class="buttonArea">
			<input type="button" id="submitButton" class="formButton_green" value="保存" hidefocus="" /><span 
				class="blue">点击保存，提交单位信息，*标记为必填项</span>
		</div>
	</div>
</body>
</html>