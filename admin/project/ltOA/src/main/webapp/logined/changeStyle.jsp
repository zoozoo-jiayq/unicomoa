<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%
//获取登录人
UserInfo userInfo = (UserInfo) session.getAttribute("adminUser");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//换肤跳转
Integer sysStyle = Integer.parseInt(request.getParameter("sysStyle"));
String styleType = request.getParameter("styleType");
if(userInfo != null){
	Integer tempSkin = userInfo.getSkinLogo();
	if(tempSkin != null){
		String str[] = (tempSkin+"").split("");
		if(sysStyle != null){
			switch(sysStyle){
				case 1:
					userInfo.setSkinLogo(Integer.parseInt("1"+str[2]));
					break;//1默认
				case 2: 
					userInfo.setSkinLogo(Integer.parseInt("2"+str[2]));
					break;//2绿色
				case 3:
					userInfo.setSkinLogo(Integer.parseInt("3"+str[2]));
					break;//3蓝色
				case 4:
					userInfo.setSkinLogo(Integer.parseInt("4"+str[2]));
					break;//4品牌色
				default: 
					userInfo.setSkinLogo(Integer.parseInt("1"+str[2]));
					break;
			}
		}
	}
	
}

//String sysStyle = request.getParameter("sysStyle")!=null?request.getParameter("sysStyle").toString():"default";
/* session.setAttribute("sysStyle", sysStyle);

Cookie sysStyleCookie =new Cookie("sysStyle",sysStyle); 
sysStyleCookie.setMaxAge(999999999);
response.addCookie(sysStyleCookie);	
session.setAttribute("sysStyle", sysStyle); */
String location = "";
if(styleType != null && styleType != ""){
	if("indexHrLeft".equals(styleType)){
		location = basePath+"logined/indexHrLeft.jsp";
	}else if("indexFix".equals(styleType)){
		location = basePath+"logined/indexFix.jsp";
	}else if("indexLeft".equals(styleType)){
		location = basePath+"logined/indexLeft.jsp";
	}else{
		location = basePath+"logined/index.jsp";
	}
}else{
	location = basePath+"logined/index.jsp";
}
response.sendRedirect(location);
%>

 