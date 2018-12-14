<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>工作经历详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript"src="${ctx}/js/logined/record/commonUpload.js"></script>
<style type="text/css">
.inputTable th{ width:100px;}
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
	 <div class="big_title">工作详情</div>
	 <div class="content_form">
	    <div class="small_title">基本信息</div>
        <input type="hidden" id="attment" value="${recordWork.attachmentList}">
      	  <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
                <tr>
                  <th><label>姓名：</label></th>
                  <td>${recordWork.userInfo.userName}</td>
                  <th><label>工作单位：</label></th>
                  <td>${recordWork.workUnit}</td>
                </tr>
      			<tr>
                    <th><label>开始时间：</label></th>
                    <td><fmt:formatDate value="${recordWork.startDate}" pattern="yyyy-MM-dd"/></td>
                    <th><label>结束时间：</label></th>
                    <td><fmt:formatDate value="${recordWork.endDate}" pattern="yyyy-MM-dd"/></td>
                </tr>
                <tr>
                    <th><label>行业类别：</label></th>
                    <td>${empty recordWork.industryCategory?"--":recordWork.industryCategory}</td>
                    <th><label>所在部门：</label></th>
                    <td>${recordWork.department}</td>
                </tr>
                <tr>
                    
                    <th><label>担任职务：</label></th>
                    <td>${recordWork.position}</td>
                    <th><label>证明人：</label></th>
                    <td>${empty recordWork.reterence?"--":recordWork.reterence}</td>
                </tr>
                 <tr>
                    <th><label>工作内容：</label></th>
                    <td colspan="3" >${empty recordWork.jobContent?"--":recordWork.jobContent}</td>
                    
                </tr>
                 <tr>
                    <th><label>主要业绩：</label></th>
                    <td colspan="3">${empty recordWork.achievement?"--":recordWork.achievement}</td>
                </tr>
                <tr>
                    <th><label>离职原因：</label></th>
                    <td colspan="3">${empty recordWork.leavingReasons?"--":recordWork.leavingReasons}</td>
                </tr>
                <tr>
                    <th><label>备注：</label></th>
                    <td colspan="3">${empty recordWork.remark?"--":recordWork.remark}</td>
                </tr>
                <tr>
                    <th><label>登记时间：</label></th>
                    <td><fmt:formatDate value="${recordWork.createTime}" pattern="yyyy-MM-dd"/></td>
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
					                     <c:forEach items="${recordWork.attachmentList}" var="attachment" >
					                        <li><div class="icon"><em class="${attachment.attacthSuffix }"></em></div><div class="txt"><p>${attachment.attachName }</p><p><a style="cursor:pointer"  onclick="viewFile_record(${attachment.id})">预览</a> <a  href="javascript:void(0);"  onclick="downFileById(${attachment.id},this)">下载</a></p></li>
					                    </c:forEach>
					                    <c:if test="${empty recordWork.attachmentList }">无</c:if>
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