<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"  %>
  
  <!-- 换首页 -->
  <div class="change hideDiv popo" id="changeIndex" style="display:none" >
     <ul>
        <li style="cursor:pointer;" onclick=" $('.hideDiv').hide(); gotoUrl('shouye', '&nbsp;桌&nbsp;&nbsp;&nbsp;面', '${ctx}logined/desktop/mainPage.action', '')"   > <img src="${ctx }flat/images/main/zy_icon.png"></img><h2>桌面</h2> </li>
        <li style="cursor:pointer;" onclick=" $('.hideDiv').hide();addTab('dwmh', 'http://www.haxinmi.jcy.gov.cn/', '门户首页', true, ''); "   > <img src="${ctx }flat/images/main/dwmh_icon.png"></img><h2>门户首页</h2> </li>
        <%-- <li style="cursor:pointer;" onclick=" $('.hideDiv').hide();addTab('glpt', '${ctx}/logined/count/countFix.jsp', '管理平台', true, '');"><img src="${ctx }flat/images/main/zxmh_icon.png"></img><h2>管理平台</h2></li> --%>
        <li style="cursor:pointer;" onclick=" $('.hideDiv').hide();addTab('gzpt', '${ctx}/logined/desktop/mainkp.jsp', '工作平台', true, '');" ><img src="${ctx }flat/images/main/rwmh_icon.png"></img><h2>工作平台</h2></li>
     </ul>
  </div>
  <%
  String styleType = request.getParameter("styleType");
  %>
   <!-- 换颜色 -->
  <div class="change hideDiv popo" id="hcolor" style="display:none">
     <ul>
       <jsp:include page="style.jsp" >
       	<jsp:param value="<%=styleType %>" name="styleType"/>
       </jsp:include>
     </ul>
  </div>
  
  