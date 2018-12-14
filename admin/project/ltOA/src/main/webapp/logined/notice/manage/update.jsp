<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../../../common/ydzjtaglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告管理设置-编辑栏目</title>
<jsp:include page="../../../common/ydzjhead.jsp" />
<link href="${ctx}/ydzj/css/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/ydzj/css/new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var currentSessionId="<%=session.getId()%>";
</script>
<script type="text/javascript">
        var basePath = '${ctx}';
        var downPath = '${session.downPath}';
        var url = document.referrer;
</script>
<script type="text/javascript" src="${ctx}js/placeholder.js?v=${jsversion}"></script>
<script type="text/javascript" src="${ctx}/js/logined/notice/manage/update.js?v=${jsversion}"></script>
<script type="text/javascript" src="${ctx}/js/logined/notice/manage/openSelectUser.js?v=${jsversion}"></script>
<script type="text/javascript" src="${ctx}/js/logined/notice/manage/selectUser.js?v=${jsversion}"></script>
<script type="text/javascript" src="${ctx}/js/logined/notice/manage/uploadPhoto.js?v=${jsversion}"></script>
<script type="text/javascript" src="${ctx}/plugins/upload/jquery.uploadify.min.js?v=${jsversion}"></script>
</head>
<body>
<input type="hidden" id="userIds" value="${column.issuer}"/>
<input type="hidden" id="fwIds" value="${column.distribution}"/>
<input type="hidden" id="dxUserIds" value="${column.approverId}"/>
<input type="hidden" id="VID" value="${column.vid}"/>
<input type="hidden" id="CreatedDatetime" value="${column.createdDatetime}"/>
<input type="hidden" id="photoPath" value="${column.titleIcon}"/>
<input type="hidden" id="firstTitle"  value="${column.title}"/>
<input type="hidden" id="category" value="${column.category }"/>
<div class="input">
     <table class="listTitle">
	    	<tr>
	        	<td class="left"></td>
                <td class="center">新增栏目</td>
                <td class="right"></td>
	        </tr>
	</table>
    <table border="0" class="inputTable">
        <tr>
            <th><span class="requireField">*</span><label>栏目名称：</label></th>
            <td><input id="lmName" type="text" maxlength="8" class="formText" value="${column.title}" /></td>
            <th><span class="requireField"></span><label>栏目序号：</label></th>
            <td><input id="lmNum" type="text" value="${column.orderIndex}" class="formText" maxlength="3" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/></td>
        </tr>
        <tr>
            <th><span class="requireField">*</span><label>栏目图标：</label></th>
            <td colspan="3">
              <div class="lmtb_c">
                 <ul id="firstUL">
                    <li><em></em><img tit="/ydzj/images/content/dshy.png" src="${ctx}/ydzj/images/content/dshy.png"/></li>
                    <li><em></em><img tit="/ydzj/images/content/gg.png" src="${ctx}/ydzj/images/content/gg.png"/></li>
                    <li><em></em><img tit="/ydzj/images/content/news.png" src="${ctx}/ydzj/images/content/news.png"/></li>
                    <li><em></em><img tit="/ydzj/images/content/qywh.png" src="${ctx}/ydzj/images/content/qywh.png"/></li>
                    <li><em></em><img tit="/ydzj/images/content/qz.png" src="${ctx}/ydzj/images/content/qz.png"/></li>
                    <li><em></em><img tit="/ydzj/images/content/scsz.png" src="${ctx}/ydzj/images/content/scsz.png"/></li>
                    <li><em></em><img tit="/ydzj/images/content/xxyl.png" src="${ctx}/ydzj/images/content/xxyl.png"/></li>
                    <li><em></em><img tit="/ydzj/images/content/xw.png" src="${ctx}/ydzj/images/content/xw.png"/></li>
                 </ul>
                 <ul id="secondUL">
                    <li id="addLI"><img id="addImg" src="${ctx}/ydzj/images/content/add_lmtb.png"/></li>
                 </ul>
                 
              </div>
              <!-- <p class="gray_9 pl15">上传的本地图片需小于200k</p> -->
            </td>
        </tr>
        <tr>
           <th><span class="requireField">*</span><label>发布人员：</label></th>
           <td colspan="3"><textarea id="userNames" class="readOnlyText" style="float:left; width:80%;" readonly="readonly">${column.issuerName}</textarea><div class="addMember"><a id="addNames" href="javascript:void(0)" class="icon_add">选择</a><a href="javascript:void(0)" id="userEmpty" class="icon_clear">清空</a></div></td>
        </tr>
        <tr>
           <th><span class="requireField">*</span><label>发布范围：</label></th>
           <td colspan="3"><textarea id="fwNames" class="readOnlyText" style="float:left; width:80%;" readonly="readonly">${column.distributionName}</textarea><div class="addMember"><a id="addfw" href="javascript:void(0)" class="icon_add">选择</a><a href="javascript:void(0)" id="fwEmpty" class="icon_clear">清空</a></div></td>
        </tr>
        <tr>
            <th><label>是否审批：</label></th>
            <td>
            <s:if test="column.approve == 'N'.toString()">
            	<div id="isApp">否</div>
            </s:if>
            <s:else>
            	<div id="isApp">是</div>
            </s:else>
            	
            </td>
           <th><span id="showTH" ><span class="requireField">*</span><label>审批人员：</label></span></th>
            <td><span id="showTD" ><input value="${column.approver}" id="dxUserName" type="text" class="readOnlyText"   readonly="readonly"/>&nbsp;<a id="userName" href="javascript:void(0)" class="icon_add">选择</a></span></td>
        </tr>
  </table>
  <div class="buttonArea">
          <input type="button" id="savelm" value="保 存" class="formButton"/>
          &nbsp;&nbsp;
          <input type="button" value="返 回" class="formButton" onclick="javascript:window.location.href = document.referrer;"/>
    </div>
</div>
</body>
</html>