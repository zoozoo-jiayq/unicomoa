<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../../../common/ydzjtaglibs.jsp" />
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发布内容-公告发布</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/ydzjhead.jsp" />
<link href="${ctx}css/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var basePath = "${ctx}";
var downPath = "${downPath}";
window.UEDITOR_HOME_URL = basePath+"plugins/ueditor/";
</script>
<script type="text/javascript" src="${ctx}plugins/ueditor/editor_config.js?version=${jsversion}"></script>
<script type="text/javascript" src="${ctx}plugins/ueditor/editor_all.js?version=${jsversion}"></script>
<script type="text/javascript" src="${ctx}js/logined/notice/user/issueAdd.js?v=${jsversion}"></script>
<script type="text/javascript">
var currentSessionId="<%=session.getId()%>";
</script>
<link href="${ctx}/ydzjcss/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/news/user/uploadPhoto.js?v=${jsversion}"></script>
<script type="text/javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js?v=${jsversion}"></script>
</head>
<body>
<input type="hidden" id="oldDistributionPhone" value="${userPhones}"  alt=""/>
<input type="hidden" id="title" value="${column.title}"  alt="栏目标题"/>
<input type="hidden" id="userIds" value="${column.distribution}"   alt="发布范围"/>
<input type="hidden" id="managerID" value="${column.vid}"   alt="栏目Id"/>
<input type="hidden" id="Approve" value="${column.approve}" alt="审批状态"/>
<input type="hidden" id="Approver" value="${column.approver}"  alt="审批人" />
<input type="hidden" id="ApproverID" value="${column.approverId}"   alt="审批人id"/>
<input type="hidden" id="IssuerID" value="${column.issuer}"   alt="发布人"/>
<input type="hidden" id="MaterialID"/><!-- 素材id -->
<div class="input">
     <table class="listTitle">
	    	<tr>
	        	<td class="left"></td>
                <td class="center">发布通知公告</td>
                <td class="right"></td>
	        </tr>
	</table>
    <table border="0" class="inputTable">
        <tr>
            <th><span class="requireField">*</span><label>标题：</label></th>
            <td><input type="text" id="noticeTitle" class="formText" maxlength="30" placeholder="不超过30个字"/></td>
        </tr>
         <tr>
            <th><span class="requireField">*</span><label>内容：</label></th>
            <td>
            <script  id="editor" type="text/plain"></script>
            </td>
        </tr>
        <tr>
           <th><span class="requireField">*</span><label>发布范围：</label></th>
           <td>
			<textarea class="readOnlyText" style="float:left; width:80%;" readonly="readonly" id="userNames">${column.distributionName}</textarea>
	           <div class="addMember">
		           <a href="javascript:void(0)" class="icon_add"  id="addUsers">选择</a>
		           <a href="javascript:void(0)" class="icon_clear" id="clearUsers">清空</a>
	           </div>
		   </td>
        </tr>
         <tr style="display:none">
            <th><label>审批意见：</label></th>
            <td>发布对象有误</td>
        </tr>
      </table>      
  <div class="buttonArea">
		  <c:choose>
		   <c:when test="${column.approve=='N'}">
			            <input type="button" onclick="publish(1)" value="发 布" class="formButton"/>
			            &nbsp;&nbsp;
		   </c:when>
		   <c:otherwise>
			            <input type="button" onclick="publish(2)" value="提交审批" class="formButton"/>
			            &nbsp;&nbsp;
		   </c:otherwise>
		   </c:choose>
            <input type="button" onclick="dispPreview()" value="预 览" class="formButton"/>
            &nbsp;&nbsp;
            <input type="button" onclick="publish(3)" value="存草稿" class="formButton"/>
            &nbsp;&nbsp;
            <input type="button" value="取 消" class="formButton" onclick="javascript:window.location.href=document.referrer"/>
  </div>
</div>
</body>
</html>