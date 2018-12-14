<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>邮件箱管理</title>
        <jsp:include page="../../common/flatHead.jsp"/>
        <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
</head>
<body>
 <div class="list">
    <div class="searchArea">
      <table cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
           
            <td class="right">&nbsp;</td>
              <td style="width:134px;">
               <div class="fButton greenBtn">
                              <span class="add"  onclick="toAddEmailBox();">新增文件夹</span>
                        </div>
              </td>
          </tr>
        </tbody>
      </table>
    </div>

    <table id="dataTable" cellpadding="0" cellspacing="0" class="pretty dataTable">
        <thead>
        <tr>
            <!--  <th class="num">序号</th>-->
            <th id="boxName" class="data_l">名称</th>
            <th id="boxSize" class="longTxt data_r">占用空间</th>
            <th id="pageMax">每页显示邮件数</th>
            <th class="right_bdr0 oper"  style="width: 120px;">操作</th>
        </tr>
        </thead>
    </table>
</div>
</div>
<script type="text/javascript" src="${ctx}/js/logined/email/email_box_common.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}/js/logined/email/email_box_list.js?version=${version}"></script>
</body>
</html>
