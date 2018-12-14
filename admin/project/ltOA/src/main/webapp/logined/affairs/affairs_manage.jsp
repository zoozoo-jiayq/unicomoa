<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>事务提醒设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/css/myworks.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/flat/plugins/smallTab/skins/smallTab.js"></script>
<script type="text/javascript"	src="${ctx}js/logined/affairs/affairs_manage.js"></script>
<style type="text/css">
  .con_explain ul li{ color:#4d4d4d;}
</style>
</head>
<body>
<div class="formPage">
  <div class="formbg">
      <div class="big_title">提醒设置</div>
             <div class="my_jdsz">
           <div class="left_mj">
               <div class="tab">
                  <ul>
                      <li class="current"><a href="javascript:void(0);">提醒模块设置</a></li>
                      <li><a href="javascript:void(0);">重复提醒设置</a></li>
                  </ul>
               </div>
           </div>
           <div class="right_mj">
                <div class="tabContent" id="tab1">
					<table  cellpadding="0" cellspacing="0" class="pretty mt10" id="myTable">
						<thead>
							<tr>
							    <th class="num" id="no">序号</th>
								<th id="moduleName" class="longTxt">模块名称</th>
								<th style="width:100px;"><label class="radio"><input name="" type="checkbox" id="affairAll" onclick="selectAffairAll()" value="" />在线消息</label></th>
								<th style="width:100px;"><label class="radio"><input name="" type="checkbox" id="smsAll" onclick="selectSmsAll()" value="" />短信</label></th>
								<th style="width:100px;" class="right_bdr0"><label class="radio"><input name="" type="checkbox" id="pushAll" onclick="selectPushAll()" value="" />手机推送</label></th>
							</tr>
						</thead>
					</table>
                </div>
                <div class="tabContent" style="display:none;" id="tab2">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
                        <tr>
                          <th style="width:70px;"><label>重复提醒：</label></th>
                          <td><label class="radio"><input type="checkbox" id="status" />启用重复提醒</label></td>
                        </tr>
                         <tr>
                          <th style="width:70px;"><label>提醒方式：</label></th>
                          <td><select id="times"><option value="1">1次</option><option value="2">2次</option><option value="4">4次</option></select></td>
                        </tr>
                      </table>
                       <div class="con_explain">
                          <ul>
                              <li>注：重复提醒设置，是指除了在接收到待办事项时的提醒，另外进行的提醒。</li>
                              <li>• 设置为1次，在每天 <span class="blue">08:45</span>，发送待办事项提醒；</li>
                              <li>• 设置为2次，在每天 <span class="blue">08:45、14:45</span>，发送待办事项提醒；</li>
                              <li>• 设置为4次，在每天 <span class="blue">08:45、11:30、14:45、17:30</span>，发送待办事项提醒；</li>
                          </ul>
                       </div>  
                </div>
           </div>       
      </div>     
   
       
 </div>   
 <div class="buttonArea">
    <input type="button" class="formButton_green" value="保存" hidefocus="" onclick="sub();"><span class="blue">点击保存，提交提醒设置。</span>
 </div>
</div>
</body>
</html>
