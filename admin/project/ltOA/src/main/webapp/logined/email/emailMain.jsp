<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>OA办公系统电子邮件中心</title>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../common/flatHead.jsp"/>
        <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/css/mail.css" rel="stylesheet" type="text/css" />    
    <link href="${ctx}/plugins/datatable/table_style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctx}flat/js/base.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/email/email_common.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/email/email_main.js"></script>
</head>

<body>
<div class="mainpage">
    <div class="leftMenu">
        <div class="meilTitle">
           <ul>
              <li><a href="javascript:void(0);" onclick="window.open('${ctx}logined/email/emailToListPage.action?notRead=1','emailContent')" target="emailContent"><img src="${ctx}flat/images/icon/wd_mail.png">收信</a></li>
              <li><a href="javascript:void(0);" onclick="window.open('${ctx}logined/email/emailBodyCreate.action','emailContent')" target="emailContent"><img src="${ctx}flat/images/icon/xx_mail.png">写信</a></li>
           </ul>
        </div>
		<div class="meil_wrap">
			<ul>
				<li id="inbox" class="current"><a href="${ctx}logined/email/emailToListPage.action" target="emailContent">收件箱<span id="box_inBox">${defaultBoxEmailNotReadCountMap["notRead"]}</span></a></li>
				<li id="outbox"><a href="${ctx}logined/email/emailSendedListPage.action" target="emailContent">已发送</a></li>
				<li><a href="${ctx}logined/email/emailDraftListPage.action" target="emailContent">草稿箱</a></li>
				<li><a href="${ctx}logined/email/emailWastebasketListPage.action" target="emailContent">垃圾箱</a></li>
			</ul>
			<h2 <s:if test="#emailBoxList==null||#emailBoxList.size==0"> style="display:none;" </s:if> id="myBoxTag" style="cursor: pointer;">我的文件夹</h2>
		  	<ul class="wj_ul" id="wj_ul">
               <s:iterator value="#emailBoxList" var="emailBox">
                    <li id="emailBox_${emailBox.id}">
                    	<a href="${ctx}logined/email/emailToListPage.action?emailBoxId=${emailBox.id}" target="emailContent">
                    		<s:property value="#emailBox.boxName"/><span id="box_my_${emailBox.id}"><s:if test="#emailBox.emailCount!=0">（<s:property value="#emailBox.emailCount"/>）</s:if></span>
                    	</a>
                    </li>
                </s:iterator>   
           	</ul>
			<li><a href="${ctx}logined/email/emailBoxList.jsp" target="emailContent">邮箱设置</a></li>
        </div>
    </div>

	<iframe frameborder="0" src="${ctx}${redirectURL}" name="emailContent" scrolling="auto" style="width: 100%; height:100%;" class="iframeresize"></iframe>
</div>
</body>
<script type="text/javascript">
		if(menuStyle != "default"){
			var mainpage = $("div.mainpage");
			mainpage.removeClass("mainpage").addClass("mainpage_r");
		}
</script>
</html>
