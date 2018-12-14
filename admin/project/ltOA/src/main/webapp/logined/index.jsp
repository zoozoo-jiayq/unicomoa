<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"  %>
<%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path+"/" ;
/* String menuStyle="defalut"; //左侧菜单
Cookie cookies[]=request.getCookies();
Cookie menuCookie = null;
String location="";
String sysStyle="";
if(cookies!=null&&cookies.length>0){
	 for(int i=0;i<cookies.length-1;i++){    
		 Cookie sCookie=cookies[i];   //取出数组中的一个Cookie对象
		 String sname=sCookie.getName(); //取得这个Cookie的名字
		 if("menuStyle".equals(sname)){
			   menuCookie = sCookie;
			   menuStyle=sCookie.getValue()!=null?sCookie.getValue().toString():"default"; //取得这个Cookie的内容
		 }
		 if("sysStyle".equals(sname)){
			   menuCookie = sCookie;
			     sysStyle=sCookie.getValue()!=null?sCookie.getValue().toString():"default"; //取得这个Cookie的内容
		 }
		
    }  
}
if(menuCookie==null){
	Cookie menuStyleCookie =new Cookie("menuStyle",menuStyle); 
	response.addCookie(menuStyleCookie);		
} 
if("left".equals(menuStyle)){
	location=basePath+"logined/indexLeft.jsp";
}else{
	location=basePath+"logined/indexDefault.jsp";
} */

UserInfo userInfo = (UserInfo) session.getAttribute("adminUser");
Integer tempSkinLogo = userInfo.getSkinLogo();
String menuStyle = "";
String location="";
if(tempSkinLogo != null){
	String str = (tempSkinLogo+"");
	if(str.length() == 1){
		menuStyle = "default";
	}else{
		Integer tempStyle = Integer.parseInt(str.substring(1, 2));
		if(tempStyle==2){
			menuStyle = "left";
		}
		/* if(tempStyle==3){
			menuStyle = "fix";
		} */
		if(tempStyle==3){
			Integer first = Integer.parseInt(str.substring(0,1));
			Integer f = Integer.parseInt(first+"1");
			userInfo.setSkinLogo(f);
			menuStyle = "default";
		}
		if(tempStyle==1){
			menuStyle = "default";
		}
	}
}
if("left".equals(menuStyle)){
	location=basePath+"logined/indexLeft.jsp";
}
/* if("fix".equals(menuStyle)){
	location=basePath+"logined/indexFix.jsp";
} */
if("default".equals(menuStyle)){
	location=basePath+"logined/indexDefault.jsp";
}  
//session.setAttribute("sysStyle", sysStyle);//样式
response.sendRedirect(location);
%>
 