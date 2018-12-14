<%@ page language="java" pageEncoding="utf-8"%>
<%
	String type=request.getParameter("type");
	if("moduleCode".equals(type)){
		session.removeAttribute("moduleCode");
		session.removeAttribute("instanceId");
		session.removeAttribute("processId");
		session.removeAttribute("executionId");
		session.removeAttribute("type");
	}else if("detail".equals(type)){
		session.removeAttribute("etoId");
		session.removeAttribute("ebodyId");
		session.removeAttribute("receiverWorkNo");
	}
	
%>