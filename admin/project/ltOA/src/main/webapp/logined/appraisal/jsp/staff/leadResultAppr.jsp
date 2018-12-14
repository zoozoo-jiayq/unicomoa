<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>绩效考核</title>

	<link href="${ctx }/logined/appraisal/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/logined/appraisal/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${ctx}/logined/appraisal/js/staff/leadResultAppr.js?version=${version}"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/datatable/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/qytx-cbb-v1.0.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/selectGroup.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/peopleTree/skins/jquery.ztree.all-3.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/treeNode.js"></script>
	  <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
	  <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
  </head>
<body>
	<input type="hidden" id="tid" value="${param.tid}">
	<input type="hidden" id="view_type" value="${param.view_type}">
		<!-- 右侧内容模块 -->
		<div class="iframe_right_content">
			<div class="navigation_bar">
				<p>当前位置：
					<a href="#">首页</a>
					<span id="dh"> </span>
				</p>
			</div>
			<div class="kh_score_box">
				<div class="pr">
					<p class="kh_score_title"  id="khmc">绩效考核</p>
					<div class="kh_btn_box "  id="shenhe">
							<span id="span_sptg" class="btn_inline btn_blue" onclick="appr(6)">审批通过</span>
							<span id="span_spbtg" class="btn_inline btn_red_solid ml15" onclick="appr(5)">审批不过</span>
						<span id="span_dc" class="btn_inline btn_white ml15 fl" onclick="toExcel()">导出</span>
					</div>					
				</div>
				
				<div class="kh_score_detail">
					<ul class="kh_detail_list">
						<li>
							<span class="kh_detail_list_title">部门：</span>
							
							<input id="groupSel" type="text" readonly="readonly" class="kh_select" />
                      	    <span class="selectdot" id="groupSel_div"></span>
							<div id="menuContent" style="position: absolute;">
								<ul id="groupTree" class="ztree"
									style="position: absolute; margin-top: 3px; width: 170px;height:150px;overflow:auto; background: #ffffff;  border: 1px solid #e3e3e3;z-index:1000"></ul>
							</div>
							<input type="hidden" id="groupId" value=""/>
						</li>
						<li>
							<span class="kh_detail_list_title">评级：</span>
							<select class="kh_select" id="pj" onchange="gradeChange()">
								<option value="">请选择</option>
							</select>
						</li>
						<li>
							<span class="kh_detail_list_title">平均分：</span>
							<span class="kh_detail_list_content" id="avg">0</span>分
						</li>
					</ul>
					<table id="myTable" cellpadding="0" cellspacing="0" class="score_table mt15">
			            <thead>
			            <tr>
							<th id="no" class="wd80">排名</th>
							<th id="user_name" class="wd100">姓名</th>
							<th id="group_name" class="wd200">部门</th>
							<th id="job" class="wd200">职务</th>
							<th id="jb" class="wd120">级别</th>
							<th id="fz" class="wd80">得分</th>
							<th class="wd200">查看</th>
							<th class="wd200">操作</th>
			            </tr>
			            </thead>   
			        </table>

				</div>
			</div>
		</div>
	<!-- <script src="../js/jquery-1.8.3.min.js"></script>
	<script src="../js/jxkh.js"></script> -->
</body>
</html>
