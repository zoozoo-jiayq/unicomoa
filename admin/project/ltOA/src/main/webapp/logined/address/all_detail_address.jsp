<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>联系人信息</title>
<jsp:include page="../../common/taglibs.jsp" />
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
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
<body class="bg_white">
<div class="elasticFrame formPage" style="padding:0px;">
          <div class="content_form">
          <div class="small_title">联系人-<s:property
					value="#request.address.name" /></div>
            <table width="100%" class="inputTable" border="0" cellspacing="0" cellpadding="0">
			<tbody><tr>
				<th>性别：</th>
				<td><s:if test="#request.address.sex==1">男</s:if>
					<s:if test="#request.address.sex==0">女</s:if></td>
				<th>头像：</th>
				<td  rowspan="3"><dl class="myphoto">
                <dt><s:if
						test="#request.address.photo==null||#request.address.photo==''">
						<s:if test="#request.address.sex==0">
							<img width='122' height='122' src="${ctx}flat/plugins/form/skins/default/meeting.png" />
						</s:if>
						<s:else>
							<img width='122' height='122' src="${ctx}flat/plugins/form/skins/default/meeting.png" />
						</s:else>
					</s:if> <s:else>
			    			<img src="${ctx}filemanager/prevViewByPath.action?filePath=${requestScope.address.photo}" id="photoImg"/>
					</s:else></dt>
                  </dl>
                 </td>
               
			</tr>
            <tr>
            	<th>生日：</th>
				<td><s:date name="#request.address.birthday"
						format="yyyy.MM.dd" /></td>
            </tr>
            <tr>
            	<th>昵称：</th>
				<td><s:property value="#request.address.nickname" /></td>
            </tr>
			<tr>
				<th>职务：</th>
				<td><s:property value="#request.address.postInfo" /></td>
                <th>配偶：</th>
				<td><s:property value="#request.address.wifeName" /></td>
			</tr>
			<tr>
				<th>子女：</th>
				<td colspan="3"><s:property value="#request.address.children" /></td>
			</tr>
		</tbody></table>


		<div class="small_title">联系方式</div>
		<table width="100%" class="inputTable" border="0" cellspacing="0" cellpadding="0">
			<tbody><tr>
				<th><label>单位名称：</label></th>
				<td><s:property value="#request.address.companyName" /></td>
				<th><label>单位邮编：</label></th>
				<td><s:property value="#request.address.companyPostCode" /></td>
				
			</tr>
			<tr>
				<th><label>单位地址：</label></th>
				<td><s:property value="#request.address.companyAddress" /></td>
			</tr>
			<tr>
				<th><label>工作电话：</label></th>
				<td><s:property value="#request.address.officePhone" /></td>
				<th><label>工作传真：</label></th>
				<td><s:property value="#request.address.officeFax" />
				</td>
			</tr>
			<tr>
				<th><label>手机：</label></th>
				<td><s:property value="#request.address.familyMobileNo" /></td>
				<th><label>电子邮件：</label></th>
				<td><s:property value="#request.address.familyEmail" /></td>
			</tr>
		</tbody></table>
		
		 <div class="small_title">备注</div>
        <table width="100%" class="inputTable" border="0" cellspacing="0" cellpadding="0">
			<tbody><tr>
				<th><label>备注：</label></th>
				<td><s:property value="#request.address.remark" /></td>
			</tr>
		</tbody></table>
		</div>
		
		
		</div>
</div>
</body>
</html>