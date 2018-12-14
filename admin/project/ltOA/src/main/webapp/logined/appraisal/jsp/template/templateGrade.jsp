<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>Document</title>
	<jsp:include page="../../common/taglibs.jsp"/>
	<link rel="stylesheet" href="${ctx}logined/appraisal/css/jxkh_style.css">
	<script src="${ctx}logined/appraisal/js/template/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	<script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
	<script src="${ctx}logined/appraisal/js/template/templateGrade.js?version=${version}"></script>
    <style>
    	/* 2016-12-28 dialog弹框文字居中  */
    	.aui_content{display:block;}
    </style>
</head>
<body style="overflow:hidden;">
		<!-- 右侧内容模块 -->
		<div class="iframe_right_content" >
			<div class="navigation_bar">
				<p>当前位置：
					<a href="#">首页</a>
					<a href="#"> > 模板设置</a>
					<span> > 分级模板</span>
				</p>
			</div>
			<div class="kh_score_box pd_muban" style="padding-right:0px;padding-bottom:20px;">
				<div class="muban_left_list">
					<p class="muban_title">分级模板
						<span class="btn2_inline btn_add ml10" id="addTemplate">增加模板</span>
					</p>
					<ul class="fenji_list" id="fenji_list">
						
					</ul>
				</div>
				<div class="ml230" id="templateBox">
					<ul class="creat_list">
						<li>
							<span class="creat_list_title">分级名称：</span>
							<input id="fenjiName" class="creat_list_input wd200" type="text" placeholder="必填" maxlength="20">
						</li>
						<li>
							<span class="creat_list_title">分级描述：</span>
							<textarea id="fenjiDescrible" class="creat_list_txt" placeholder="必填" maxlength="300"></textarea>
						</li>
						<li>
							<span class="creat_list_title">分级标准：</span>
							<p>
								<span class="btn2_inline btn_add_blue" id="addStandard">新增标准</span>
								<span class="btn2_inline btn_del_red ml10" id="delStandard">删除标准</span>
								<!-- <span class="ml10"><span class="color_red">*</span>级别值小的级别高</span> -->
							</p>
							<table id="templateTable" class="score_table td_addpd50 mt20">
								<thead>
									<tr>
										<th>选择</th>
										<th>名称</th>
										<th>分值区间</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</li>
					</ul>
					<div class="btn_container2">
						<span id="tiJiao" class="btn_inline btn_white">提交</span>
						<span id="qvxiao" class="btn_inline btn_gray ml15">取消</span>
						<span id="delete" class="btn_inline btn_red ml15">删除</span>
					</div>
				</div>
			</div>
		</div>
</body>
</html>