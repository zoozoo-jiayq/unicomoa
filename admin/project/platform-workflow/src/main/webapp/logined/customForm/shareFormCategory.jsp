<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String type = request.getParameter("type");
if(type==null || type.equals("")){
    type="1";
}
%>
<input type="hidden"  id="type" value="<%=type%>"/>