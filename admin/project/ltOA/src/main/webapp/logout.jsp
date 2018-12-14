
<%@page import="cn.com.qytx.platform.utils.spring.SpringUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cn.com.qytx.platform.log.domain.Log"%>
<%@page import="cn.com.qytx.platform.log.service.impl.LogImpl"%>
<%@page import="cn.com.qytx.platform.log.service.LogType"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@ page import="cn.com.qytx.platform.log.service.ILog" %>
<%@page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%@ page import="cn.com.qytx.platform.org.service.IUser" %>
<%
   ILog logService = (ILog)SpringUtil.getBean("logService");
   IUser userService = (IUser)SpringUtil.getBean("userService");
   UserInfo userInfo = (UserInfo)session.getAttribute("adminUser");
   userInfo.setLastLoginTime(new Date());
   userService.saveOrUpdate(userInfo);
   session.invalidate(); //清空Session
   if(userInfo !=null ){
        Log log = new Log();
		log.setCompanyId(userInfo.getCompanyId());
		log.setInsertTime(new Timestamp(new Date().getTime()));
		log.setModuleName("user");
		log.setSysName("xmjcyOA1.0");
		log.setIp(request.getRemoteAddr());
		log.setIsDelete(0);
		log.setLogType(LogType.LOG_LOGIN_OUT);
		log.setRefId(userInfo.getUserId());
		log.setRemark("退出登录成功");
		log.setUserId(userInfo.getUserId());
		log.setUserName(userInfo.getUserName());
		log.setType(0);//手动添加系统日志
		logService.saveOrUpdate(log);
   }
   response.sendRedirect("login.jsp");
%>