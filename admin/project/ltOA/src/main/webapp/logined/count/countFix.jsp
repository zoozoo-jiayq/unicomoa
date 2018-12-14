<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../common/taglibs.jsp" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>桌面_管理中心</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/desktop_manage.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/logined/count/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/js/logined/count/countHighChart.js"></script>
</head>
<body>
  <div class="manageList">
      <table cellspacing="0" cellpadding="0" border="0" style="width:100%">
         <tbody>
           <tr>
               <td>
                  <div class="manageBox col_1">
                       <h2 class="title">流程使用排名</h2>
                       <div class="cont" id="flowUseCounts"></div>
                  </div>
               </td>
               <td style="width:15px;"></td>
               <td>
                  <div class="manageBox col_2">
                       <h2 class="title">申请数量前十</h2>
                       <div class="cont" id="applyCounts"></div>
                  </div>
               </td>
               
           </tr>
           <tr><td colspan="3" style=" height:15px;"></td></tr>
           <tr>
               <td>
                  <div class="manageBox col_3">
                       <h2 class="title">流程使用频率</h2>
                       <div class="cont" id="flowUseFrequency"></div>
                  </div>
               </td>
               <td style="width:15px;"></td>
               <td>
                  <div class="manageBox col_4">
                       <h2 class="title">流程总量统计</h2>
                       <div class="cont" id="flowTotalCounts"></div>
                  </div>
               </td>
               
           </tr>
            <tr><td colspan="3" style=" height:15px;"></td></tr>  
           <tr>
               <td>
                  <div class="manageBox col_5">
                       <h2 class="title">手机端使用次数</h2>
                       <div class="cont" id="wapUseCounts"></div>
                  </div>
               </td>
               <td style="width:15px;"></td>
               <td>
                  <div class="manageBox col_6">
                       <h2 class="title">PC系统使用次数</h2>
                       <div class="cont" id="pcUseCounts"></div>
                  </div>
               </td>
              <!--  <td>
                  <div class="manageBox col_5">
                       <h2 class="title">手机端使用频度</h2>
                       <div class="cont" ></div>
                  </div>
               </td>
               <td style="width:15px;"></td> -->
               <!-- <td>
                  <div class="manageBox col_6">
                       <h2 class="title">请假统计</h2>
                       <div class="cont"></div>
                  </div>
               </td> -->
           </tr>
           <tr><td colspan="3" style=" height:15px;"></td></tr>
           <tr>
           	   <td>
                  <div class="manageBox col_4">
                       <h2 class="title">考勤打卡统计</h2>
                       <div class="cont" id="attCounts"></div>
                  </div>
               </td>
               <td style="width:15px;"></td>
               <td></td>
           </tr>       
         </tbody>
      </table>
  </div>
</body>
</html>
