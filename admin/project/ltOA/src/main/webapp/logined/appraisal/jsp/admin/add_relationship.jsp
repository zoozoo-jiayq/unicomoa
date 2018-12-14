<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../../common/taglibs.jsp"/>
	<title>设定考核关系</title>
	<script type="text/javascript" src="${ctx}js/user/selectuser.js"></script>
	<script src="${ctx}/logined/appraisal/js/admin/add_relationship.js?version=${version}"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
</head>
<body>
<input type="hidden" id="vid" value="${param.vid}">
<input type="hidden" id="ck_vid" value="${param.ck_vid}">
<input type="hidden" id="type" value="${param.type}">
<jsp:include page="../header.jsp"/>
<div class="creat_container">
	<div class="crumbs">
		<ul>
			<li>
				<a href="#">1、填写考核信息</a>
				<a href="#" class="has_done">1、填写考核信息</a>
			</li>
			<li>
				<a href="#">2、考核表设置</a>
				<a href="#" class="has_done">2、考核表设置</a>
			</li>
			<li>
				<a href="#">3、设定考核关系</a>
				<a href="#" class="has_done">3、设定考核关系</a>
			</li>
			<li>
				<a href="#">4、设定分级标准</a>
				<a href="#" class="not_done">4、设定分级标准</a>
			</li>
		</ul>
	</div>
	<ul class="khgx_list">
		<li>
			<p class="khgx_title mt5">被考核人员：</p>
			<input type="hidden" id="bkhryUserIds" value=""/>
			<textarea class="creat_list_txt fl wd765" readonly="readonly" id="bkhryUserNames"></textarea>
			<div class="kh_btn_box b10 right37">
				<span class="btn2_inline btn_add" onclick="selecBkhry();">添加</span>
				<br>
				<span class="btn2_inline btn_del mt5" onclick="clearBkhry();">清空</span>
			</div>
		</li>
		<li>
			<p class="khgx_title mt5">评分人员：</p>
			<input type="hidden" id="pfryUserIds" value=""/>
			<textarea class="creat_list_txt fl wd765" readonly="readonly" id="pfryUserNames"></textarea>
			<div class="kh_btn_box b38 right37">
				<span class="btn2_inline btn_add" onclick="selecPfry();">添加</span>
				<br>
				<span class="btn2_inline btn_del mt5" onclick="clearPfry();">清空</span>
			</div>
			<p class="khgx_tishi"><span class="color_red">*</span>自己不能给自己评分</p>
		</li>
		<li>
			<p class="khgx_title mt5">督查领导：</p>
			<input type="hidden" id="dcldUserIds" value=""/>
			<textarea class="creat_list_txt fl wd765" readonly="readonly" id="dcldUserNames"></textarea>
			<div class="kh_btn_box b10 right37">
				<span class="btn2_inline btn_add" onclick="selecDcld();">添加</span>
				<br>
				<span class="btn2_inline btn_del mt5" onclick="clearDcld();">清空</span>
			</div>
		</li>

	</ul>
	<div class="btn_container2">
		<span class="btn_inline btn_white" onclick="backStep();">上一步</span>
		<span class="btn_inline btn_gray ml15" onclick="clearBkhry();clearPfry();clearDcld();">重置</span>
		<span class="btn_inline btn_white ml15" onclick="nextStep();">下一步</span>
	</div>
</div>
</body>