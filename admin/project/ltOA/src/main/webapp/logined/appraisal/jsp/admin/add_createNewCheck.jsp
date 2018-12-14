<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../../common/taglibs.jsp"/>
    <title>创建新考核</title>
	<script type="text/javascript" src="${ctx}/logined/appraisal/js/admin/add_createNewCheck.js?version=${version}"></script>	
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script> 
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
  </head>
  
<body>
<jsp:include page="../header.jsp"/>
<input type="hidden" id="vid" value="${param.vid}">
<input type="hidden" id="ck_vid" value="${param.ck_vid}">
<input type="hidden" id="type" value="${param.type}">
	<div class="creat_container">
		<div class="crumbs">
			<ul>
				<li>
					<a href="#">1、填写考核信息</a>
					<a href="#" class="has_done">1、填写考核信息</a>
				</li>
				<li>
					<a href="#">2、考核表设置</a>
					<a href="#" class="not_done">2、考核表设置</a>
				</li>
				<li>
					<a href="#">3、设定考核关系</a>
					<a href="#" class="not_done">3、设定考核关系</a>
				</li>
				<li>
					<a href="#">4、设定分级标准</a>
					<a href="#" class="not_done">4、设定分级标准</a>
				</li>
			</ul>
		</div>
		<ul class="creat_list">
			<li>
				<span class="creat_list_title">考核名称：</span>
				<input class="creat_list_input wd270" type="text" placeholder="请输入考核名称"  maxlength="50" id="khmc">
				<p class="mt5" id="khmcts" style="display:none"><span class="color_red">*&nbsp;请输入考核名称</span></p>
			</li>
			<li>
				<span class="creat_list_title">考核类型：</span>
				<select class="creat_list_select"  id="khlx" onchange="gradeChange()">
					<option value="1">年度考核</option>
					<option value="2">季度考核</option>
					<option value="3">月度考核</option>
					<option value="4">不定期考核</option>
				</select>
			</li>
			<li>
				<span class="creat_list_title">参考：</span>
				<select class="creat_list_select"  id="ck">
					<option value="">请选择</option>
				</select>
			</li>
			<li>
				<span class="creat_list_title">时间：</span>
				<select class="time_select" id="year">
				</select>
				<select class="time_select" id="jd">
					<option value="1">第1季度</option>
					<option value="2">第2季度</option>
					<option value="3">第3季度</option>
					<option value="4">第4季度</option>
				</select>
				<select class="time_select" id="month">
				</select>
				<div  id="date">
				<input id="startDate" style="width:200px" type="text"
								value='<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>' class="Wdate formText time_select"
								onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" />
								<input type="hidden" id="startDateHidden" />
								<span class="text_zhi">至</span>
				<input id="endDate" style="width:200px" type="text"
								value='<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>' class="Wdate formText time_select"
								onFocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate\')}',skin:'default',dateFmt:'yyyy-MM-dd'})" />
								<input type="hidden" id="endDateHidden" />
				</div>
			</li>
			<li>
				<span class="creat_list_title">描述：</span>
				<textarea class="creat_list_txt"  maxlength="300" id="ms"></textarea>
			</li>
			<li>
				<span class="creat_list_title">是否自述自评：</span>
				<label class="creat_list_radio">
					<input type="radio" name="is_zishu" checked="checked" value="1">是
				</label>
				<label class="creat_list_radio">
					<input type="radio" name="is_zishu" value="0">否
				</label>
			</li>
			<li>
				<span class="creat_list_title">考核是否匿名：</span>
				<label class="creat_list_radio">
					<input type="radio" name="is_noname" checked="checked"  value="1">是
				</label>
				<label class="creat_list_radio">
					<input type="radio" name="is_noname"  value="0">否
				</label>
			</li>
		</ul>
		<div class="btn_container2">  
			<span class="btn_inline btn_white" onclick="save()">下一步</span>
			<span class="btn_inline btn_gray ml15" onclick="toClear()">重置</span>
			<span class="btn_inline btn_gray ml15" onclick="goHome()">取消</span>
		</div>
	</div>
</body>
</html>
