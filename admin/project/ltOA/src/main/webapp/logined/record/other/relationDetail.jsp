<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>关系详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript"src="${ctx}/js/logined/record/commonUpload.js"></script>
<style type="text/css">
.inputTable th {
    width: 125px;
}
</style>
<script>
function downFileById(id) {
	window.open(basePath + 'filemanager/downfile.action?attachmentId=' + id);
}
</script>
</head>
<body>
<div class="formPage" style="width: 700px">
  <div class="formbg">
	 <div class="big_title">关系详情</div>
	 <div class="content_form">
	    <div class="small_title">关系基本信息</div>
        <input type="hidden" id="attment" value="${recordRelation.attachmentList}">
      	  <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
                <tr>
                  <th><label>姓名：</label></th>
                  <td>${recordRelation.userInfo.userName}</td>
                  <th><label>成员姓名：</label></th>
                  <td>${recordRelation.memberName}</td>
                </tr>
      			<tr>
                    <th><label>与本人关系：</label></th>
                    <td>${recordRelation.relationStr}</td>
                    <th><label>出生日期：</label></th>
                    <td><fmt:formatDate value="${recordRelation.birthDate}" pattern="yyyy-MM-dd"/></td>
                </tr>
                <tr>
                    <th><label>政治面貌：</label></th>
                    <td >${empty recordRelation.politicalStatusStr?"--":recordRelation.politicalStatusStr}</td>
                    <th><label>职业：</label></th>
                    <td>${empty recordRelation.occupation?"--":recordRelation.occupation}</td>
                </tr>
                <tr>
                    <th><label>工作单位：</label></th>
                    <td >${empty recordRelation.workUnit?"--":recordRelation.workUnit}</td>
                    
                    <th><label>联系电话（单位）：</label></th>
                    <td>${empty recordRelation.workTelephone?"--":recordRelation.workTelephone}</td>
                </tr>
                 <tr>
                    <th><label>联系电话（个人）：</label></th>
                    <td>${recordRelation.personalPhone}</td>
                    <th><label>联系电话（家庭）：</label></th>
                    <td >${recordRelation.homePhone}</td>
                </tr>
                <tr>
                    <th><label>单位地址：</label></th>
                    <td colspan="3">${empty recordRelation.unitAddress?"--":recordRelation.unitAddress}</td>
                </tr>
                <tr>
                    <th><label>家庭地址：</label></th>
                    <td colspan="3">${empty recordRelation.homeAddress?"--":recordRelation.homeAddress}</td>
                </tr>
                <tr>
                    <th><label>备注：</label></th>
                    <td colspan="3">${empty recordRelation.remark?"--":recordRelation.remark}</td>
                </tr>
                <tr>
                    <th><label>登记时间：</label></th>
                    <td><fmt:formatDate value="${recordRelation.createTime}" pattern="yyyy-MM-dd"/></td>
                </tr>
             </table>
             <div class="small_title">附件</div>
					<table width="100%" cellspacing="0" cellpadding="0" border="0"
						class="inputTable">
						<tbody>
							<tr>
								<td colspan="3">
								<div class="annex">
					                <ul id="attachmentList">
					                    <c:forEach items="${recordRelation.attachmentList}" var="attachment" >
					                        <li><div class="icon"><em class="${attachment.attacthSuffix }"></em></div><div class="txt"><p>${attachment.attachName }</p><p><a style="cursor:pointer"  onclick="viewFile_record(${attachment.id})">预览</a> <a  href="javascript:void(0);"  onclick="downFileById(${attachment.id},this)">下载</a></p></li>
					                    </c:forEach>
					                     <c:if test="${empty recordRelation.attachmentList }">无</c:if>
				                    </ul>
					            </div>
							</td>
							</tr>
						</tbody>
					</table>
  	    </div>
  	  </div>                       			
</div>
</body>
</html>