<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"  %>
<%
  String styleType = request.getParameter("styleType");
  %>
<li><a href="${ctx }logined/changeStyle.jsp?sysStyle=1&styleType=<%=styleType %>"><img src="${ctx }flat/images/city/pic1.png"></img><h2>默认</h2> </a></li>
<li><a href="${ctx }logined/changeStyle.jsp?sysStyle=3&styleType=<%=styleType %>"><img src="${ctx }flat/images/city/pic2.png"></img><h2>蓝色</h2></a></li>
<li><a href="${ctx }logined/changeStyle.jsp?sysStyle=2&styleType=<%=styleType %>"><img src="${ctx }flat/images/city/pic3.png"></img><h2>绿色</h2></a></li>
<li><a href="${ctx }logined/changeStyle.jsp?sysStyle=4&styleType=<%=styleType %>"><img src="${ctx }flat/images/city/pic4.png"></img><h2>大海色</h2></a></li>
 