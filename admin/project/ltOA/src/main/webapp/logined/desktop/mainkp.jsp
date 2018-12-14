<%@ page contentType="text/html;charset=UTF-8" import="java.util.*" language="java" %>
<jsp:include page="../../common/flatHead.jsp"/>
<%@ page import=" cn.com.qytx.platform.org.domain.ModuleInfo" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%

 
String _moduleList="";
List<ModuleInfo> modulelist = (List<ModuleInfo>)request.getSession().getAttribute("moduleList"); 
for(int i=0; i<modulelist.size(); i++){
	String moduleName = modulelist.get(i).getModuleName();
	_moduleList+=moduleName+",";
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>桌面_卡片版</title>
<jsp:include page="../../common/flatHead.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/css/desktop_kp.css" rel="stylesheet" type="text/css" />
<link href="${ctx}flat/plugins/datatable/skins/filesIcon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}flat/js/jquery.masonry.js"></script>
<script type="text/javascript" src="${ctx}js/logined/desktop/mainkp.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $('.item_list').masonry({ 
			itemSelector: '.item',
			columnWidth:400,
			gutterWidth:20								
		});		
	});
</script>
</head>
<body>
<input type="hidden" id="_moduleList"  value="<%=_moduleList%>"/>
<input type="hidden" id="userId" value="${sessionScope.adminUser.userId }"/>


<div class="item_list">
   <div class="item">
        <div class="title"> <em class="wdsp"></em>我的审批</div>
        <div class="cont">
             <ul class="approval">
                <li id ="dsp"   >待审批<span class="num"><em style="cursor:pointer;" id="jbpmWaitSize">0</em></span>件</li>
                <li id ="ysp"   >已审批<span class="num"><em style="cursor:pointer;" id="jbpmApproveSize">0</em></span>件</li>
             </ul>
        </div>
   </div>
   
   <!-- 公告查看  -->
   <div class="item" id="notifyItem"   >
       <div class="title"><a class="more" href="javascript:void(0);" id="notifyMore"  >更多</a><em class="ggck"></em>公告查看</div>
        <div class="cont" id="notifyViewDiv">
        暂无数据
        </div>
   </div>
    
     <!-- 公司理念  -->
    <div class="item">
     <div class="title"><em class="gsln"></em>公司理念</div>
     <div class="cont">
       <p class="idea"><span id="philosophySpan"></span> </p>
     </div>
   </div>

    <!-- 工作日志   -->
   <div class="item" id="dailyItem"   >
       <div class="title"><a class="more"  href="javascript:void(0);" id="dailyMore"  >更多</a><em class="gzrz"></em>工作日志</div>
        <div class="cont">
             <ul class="news" id="dailyViewDiv" >
             暂无数据
             </ul>
        </div>
   </div>
   
   
   <div class="item">
    <div class="title"><a class="more" href="#" id="affairsMore" >更多</a><em class="swtx"></em>事务提醒</div>
     <div class="cont">
      <ul class="warn">
         <li class="box1">
           <h2>事项</h2>
           <p>工作流<a class="num" href="#" onclick="openAffairs(this);" ><span id="gzlSpan">0</span></a></p>
           <p>任务管理<a class="num" href="#" onclick="openAffairs(this);" ><span id="rwglSpan">0</span></a></p>
           <p>日程管理<a class="num" href="#" onclick="openAffairs(this);" ><span id="rcglSpan">0</span></a></p>
         </li>
         <li class="box2">
           <h2>信息</h2>
           <p>通知公告<a class="num" href="#" onclick="openAffairs(this);" ><span id="tzglSpan">0</span></a></p>
           <p>工作日志<a class="num" href="#" onclick="openAffairs(this);" ><span id="gzrzSpan">0</span></a></p>
           <p>内部邮件<a class="num" href="#"><span id="nbyjSpan"  onclick="openAffairs(this);" >0</span></a></p>
         </li>
      </ul>
     </div> 
   </div>
   
   <div class="item">
   <input type="hidden" id="day" value=""/>
   <input type="hidden" id="lastTime" />
       <div class="title"><a class="more" href="javascript:void(0);" id="attendanceMore"    >更多</a><em class="kqjl"></em>考勤记录</div>
       <div class="cont">
          <p class="punch_time"><span class="dk_btn" id="click">打卡</span><span class="while"  ><em id="nowTime" ></em></span></p>
          <dl class="punch">
             <dt><em id="week"></em></dt>
             <div id="punchReportDiv" ></div>
          </dl>
       </div>
   </div>
   
   
   <!-- 
    <div class="item">
       <div class="title"><a class="more" href="#">更多</a><em class="xwck"></em>新闻查看</div>
        <div class="cont">
             <ul class="news">
                <li><a href="#">节放假通知2014年中秋节放假通知<span class="time">2014-02-06</span></a></li>
             </ul>
        </div>
   </div>
    -->
   <!-- 问卷列表 -->
   <div class="item" id="questionItem"  >
       <div class="title"><a class="more" href="javascript:void(0);"  id="questionMore"     >更多</a><em class="wjlb"></em>问卷列表</div>
        <div class="cont" >
             <ul class="news" id="questionViewDiv">
             	暂无数据
             </ul>
        </div>
   </div>
   <!-- 我的任务 -->
   <div class="item" id="taskItem"  >
       <div class="title"><a class="more" href="javascript:void(0);"  id="taskMore" >更多</a><em class="wdrw"></em>我的任务</div>
        <div class="cont" id="taskViewDiv">
        暂无数据
        </div>
   </div>
   
   <!-- 资料查阅 -->
   <div class="item" id="filesItem">
       <div class="title"><a class="more" href="javascript:void(0);"  id="filesMore"  >更多</a><em class="zlcy"></em>资料查阅</div>
        <div class="cont">
             <ul class="news" id="filesViewDiv">
             暂无数据
             </ul>
        </div>
   </div>
 
</div>

</body>
</html>

