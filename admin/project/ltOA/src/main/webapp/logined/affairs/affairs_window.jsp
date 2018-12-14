<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择人员</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/Reminder.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/logined/affairs/affairs_window.js"></script>
</head>
<body class="bg_white">
 <div class="work_details">
     <h2><a href="javascript:void(0);" onclick="showAffairHistory()">查看历史记录</a>
     <span>共<font color="#ff6600" id="affairsSize"> <s:property value='#request.affairsSize' /> </font>条提醒</span></h2>
     <div id="popCont" >
			<s:iterator value="#request.affairsMap.keySet()" id="affairsMapId" var="affairsMapId">
			<dl>
				<dt class="iconText" onclick='updateModuleReaded("<s:property value="#affairsMapId" />")' title="分类全部已阅" >
					<s:if test='affairsMapId=="通讯薄"' ><em class="icon_addrBook"></em></s:if>
					<s:elseif test='#affairsMapId=="工作日志"' ><em class="icon_logBook"></em></s:elseif>
					<s:elseif test='#affairsMapId=="电子邮件"' ><em class="icon_mailBox"></em></s:elseif>
					<s:elseif test='#affairsMapId=="通知公告"' ><em class="icon_notice"></em></s:elseif>
					<s:elseif test='#affairsMapId=="日程安排"' ><em class="icon_schedule"></em></s:elseif>
					<s:elseif test='#affairsMapId=="日程安排-周期性事务"' ><em class="icon_schedule"></em></s:elseif>
					<s:elseif test='#affairsMapId=="文件柜"' ><em class="icon_fileCabinet"></em></s:elseif>
					<s:elseif test='#affairsMapId=="工作流:提醒下一步经办人"' ><em class="icon_workflow"></em></s:elseif>
					<s:elseif test='#affairsMapId=="工作流:提醒流程发起人"' ><em class="icon_workflow"></em></s:elseif>
					<s:elseif test='#affairsMapId=="工作流:提醒流程所有人员"' ><em class="icon_workflow"></em></s:elseif>
					<s:elseif test='#affairsMapId=="公文管理:发文核稿"' ><em class="icon_officialDoc"></em></s:elseif>
					<s:elseif test='#affairsMapId=="公文管理:收文登记"' ><em class="icon_officialDoc"></em></s:elseif>
					<s:elseif test='#affairsMapId=="公文管理:领导批阅"' ><em class="icon_officialDoc"></em></s:elseif>
					<s:elseif test='#affairsMapId=="公文管理:收文分发"' ><em class="icon_officialDoc"></em></s:elseif>
					<s:elseif test='#affairsMapId=="公文管理:收文阅读"' ><em class="icon_officialDoc"></em></s:elseif>
					<s:else><em class="icon_common"></em></s:else>
					&nbsp;<s:property value="#affairsMapId" />
				</dt>
					<s:iterator value="#request.affairsMap.get(#affairsMapId)" var="affairs">
						<dd style="cursor:pointer"
							onclick="openRemaidUrl('<s:property value='#affairs.affairsBody.remindUrl' />', '<s:property value='#affairs.id' />', '<s:property value='#affairs.affairsBody.smsType' />')">
					
							<p>
								<s:property value="#affairs.affairsBody.contentInfo" />
							</p>
							<p>
								<em class="day"><s:property value='#affairs.limitTime' /></em>
								来自：<s:property value="#affairs.affairsBody.fromUserInfo.userName" />
							</p>
						</dd>
					</s:iterator>
			</dl>
			</s:iterator>
		</div>
		 
 </div>
 <div id="noAffairs">
		 		<dl class="no_data">
			       <dt>暂无新提醒</dt>
			       <dd>本窗口<em id="lastTime" class="time">3</em>秒后自动关闭</dd>
			    </dl>
</div>
</body>
</html>
