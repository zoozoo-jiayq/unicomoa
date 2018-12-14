<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:include page="../../../common/ydzjtaglibs.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../../../common/ydzjhead.jsp" />
<script>
	var downPath = "${session.downPath}";
</script>
<title>应用设置-公告管理设置</title>
<script type="text/javascript" src="${ctx}js/placeholder.js?v=${jsversion}"></script>
<script type="text/javascript" src="${ctx}/js/logined/notice/manage/list.js?v=${jsversion}"></script>
<style type="text/css">
	table.pretty tbody td{padding:5px;}
</style>
</head>
<body>
<div class="list">
        <div class="doArea">
           <ul class="searchkey">
              <li>
                    <div class="fbutton"><span><a href="javascript:void(0)" onclick="add();" class="add">新增栏目</a></span></div>
                    <div class="fbutton"><span><a href="javascript:void(0)" onclick="del();" class="del">删除栏目</a></span></div>
               </li>
            </ul>  
          </div>
        
        <div class="prettyList">
		<table id="contentList" cellpadding="0" cellspacing="0"  class="pretty dataTable">
				<thead>
						<tr>
                   		   <th class="chk"><input id="allCheckbox" type="checkbox"/></th>
                           <th class="num">序号</th>
                           <th style="width:150px">栏目名称</th>
                           <th>发布范围</th>
                           <th style="width:200px;">发布人</th>
                           <th style="width:80px;">是否审批</th>
                           <th style="width:80px;">审批人</th>
                           <th style="width:160px;">创建时间</th>
                           <th style="width:80px;" class="right_bdr0">操作</th>
						</tr>
				</thead>
				<tbody>
				</tbody>
		</table>
        <div class="clear"></div>    
       </div>
</div>
</body>
</html>