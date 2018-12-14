<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../../common/ydzjtaglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>通知公告-公告列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/ydzjhead.jsp" />
<script>
	var downPath = "${session.downPath}";
</script>
<script type="text/javascript" src="${ctx}js/logined/notice/user/issueList.js?v=${jsversion}"></script>
<style type="text/css">
	table.pretty tbody td{padding:5px;}
</style>
</head>
<body>
<div class="list">
        <div class="doArea">
           <ul class="searchkey">
               <li>
                 <label>标题：</label><input type="text" id="title" class="formText"/>&nbsp;&nbsp;&nbsp;
                 <label>栏目名称：</label><select id="lm"><option value="-1">全部</option></select>&nbsp;&nbsp;&nbsp;
                 <label>发布状态：</label>
                 <select id="type">
                 	<option value="0">全部</option>
                 	<option value="1">已发布</option>
                 	<option value="2">不通过</option>
                 	<option value="3">审批中</option>
                 </select>
                 &nbsp;&nbsp;&nbsp;
                 <label>创建时间：</label><input type="text" onclick="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" readonly="readonly"  id="startTime" class="formText Wdate"/> 到 
                 <input type="text" id="endTime" onclick="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" readonly="readonly"  class="formText Wdate"/>
               </li>
               <li>
                     <div class="fbutton">
                       <span><a id="search" class="search">查询</a></span>
                  </div>
                    <img src="${ctx}images/public/fg.png" class="cut"/>
                     <div class="fbutton"><span><a href="javascript:void(0);" id="delete" class="del">删除</a></span></div>
                </li>
            </ul>  
         </div>
        <div class="prettyList">
		<table cellpadding="0" cellspacing="1"  class="pretty dataTable" id="table">
            <thead>
			</thead>
        </table>
        <div class="clear"></div>    
       </div>
</div>
</body>
</html>
