<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>人事档案奖惩-新增或修改</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <jsp:include page="../../../common/flatHead.jsp" />
    <script>
        window.UEDITOR_HOME_URL = basePath+"plugins/ueditor/";
    </script>
    <link rel="stylesheet" type="text/css"
          href="${ctx}plugins/upload/uploadify.css" />
    <link href="${ctx}/flat/css/reset.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/flat/plugins/form/skins/form_default.css"
          rel="stylesheet" type="text/css" />
    <link href="${ctx }flat/plugins/tree/skins/tree_default.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/flat/plugins/annex/skins/annex_default.css"
          rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
    <script type="text/javascript"
            src="${ctx}/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/flat/plugins/org/org.js"></script>
    <script type="text/javascript" src="${ctx}/js/logined/record/common.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/validate_form.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/showError.js"></script>
    <script type="text/javascript" src="${ctx }flat/plugins/maxlen_data/behaviour_min.js"></script>
    <script type="text/javascript" src="${ctx }flat/plugins/maxlen_data/textarea_maxlen_min.js"></script>

    <!-- 上传 start -->
    <script type="text/javascript" src="${ctx}js/logined/upload/attachHelp.js"></script>
    <script type="text/javascript" src="${ctx}js/logined/cbb/attachment/attachment.js"></script>
    <script type="text/javascript"
            src="${ctx}/js/logined/record/commonUpload.js"></script>
    <script type="text/javascript"
            src="${ctx}/js/logined/record/other/panaltiesAddOrUpdate.js"></script>
    <script type="text/javascript">
        var temp_explain = '${recordPenalties.explain}';
        var temp_nature = '${recordPenalties.nature}';
        var temp_remark ='${recordPenalties.remark}';
    </script>
    <style type="text/css">
        .inputTable th {
            width: 100px;
        }
        .annex ul li{float: left}
    </style>
</head>
<body>
<div class="formPage" style="width: 700px">
    <form action="#" name="userRecordForm">
        <div class="formbg">
            <input type="hidden" id="penaltiesId" value="${recordPenalties.id}" />
            <input type="hidden" value="${recordPenalties.userInfo.userId}" id="userId"/>
            <div class="big_title">${empty recordPenalties.id?"新增奖惩信息":"修改奖惩信息"}</div>
            <div class="content_form">
                <div class="small_title">基本信息</div>
                <table width="100%" border="0" cellpadding="0" cellspacing="0"
                       class="inputTable">
                    <tr>
                        <th><label>单位员工：</label></th>
                        <td><input type="text"   class="readOnlyText" style="width: 190px" readonly="readonly" value="${recordPenalties.userInfo.userName}"/></td>
                        <th><em class="requireField">*</em><label>奖惩项目：</label></th>
                        <td><input id="project" type="text" style="width: 192px" class="formText" value="${recordPenalties.project}"
                                   maxlength="25" /></td>
                    </tr>
                    <tr>
                        <th><em class="requireField">*</em><label>奖惩日期：</label></th>
                        <td><input id="penaltiesDate" type="text" style="width: 192px"
                                   value='<fmt:formatDate value="${recordPenalties.penaltiesDate}" pattern="yyyy-MM-dd"/>'
                                   class="Wdate formText"
                                   onFocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})"/>
                            <input type="hidden" id="penaltiesDateHidden"  value="${recordPenalties.penaltiesDate}"/>
                        </td>
                        <th><label>工资月份：</label></th>
                        <td><input id="wagesMonth" type="text" style="width: 192px"
                                   value="${recordPenalties.wagesMonth}"  class="Wdate formText"
                                   onFocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM'})"/>
                            <input type="hidden" id="wagesMonthHidden"  value="${recordPenalties.wagesMonth}"/ >
                        </td>
                    </tr>
                    <tr>
                        <th><em class="requireField">*</em><label>奖惩属性：</label></th>
                        <td><select style="width: 193px" id="type">
                            <option value="">请选择</option>
                            <option value="0">奖励</option>
                            <option value="1">惩罚</option>
                        </select></td>
                        <th><label>奖惩金额(元)：</label></th>
                        <td><input id="penalties_money" type="text" style="width: 193px" class="formText" style="width: 192px" 
                        onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-.]+/,'');}).call(this)" onblur="this.v();"   
                        value="${recordPenalties.penalties_money}" maxlength="6"
                        /></td>
                    </tr>
                    <tr>
                        <th>备注：</th>
                        <td colspan="3"><textarea id="remark" rows="2" style="width: 95%"
                                                  class="formTextarea" maxlength="255" showremain="limitRemark">${recordPenalties.remark}</textarea>
                            <br/>您还可以输入：<span id="limitRemark">255</span>字
                        </td>
                    </tr>
                    <tr>
                        <th>奖惩说明：</th>
                        <td colspan="3">
                            <script id="contentInfo" maxlength="255" type="text/plain" style="width:100%"></script>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="small_title">附件</div>
                <table width="100%" cellspacing="0" cellpadding="0" border="0"
                       class="inputTable">
                    <tbody>
                    <tr>
                        <td colspan="3" >
                            <input type="hidden" id="attachmentId" value="${recordPenalties.attment}"/>
                            <input id="file_upload" name="fileupload" type="file" multiple="true" />
                            <!-- 上传队列 -->
                            <div id="queue"  style="display:none;"></div>
                            <div class="annex">
                                <ul id="attachmentList">
                                    <c:forEach items="${recordPenalties.attachmentList}" var="attachment" >
                                        <li><div class="icon"><em class="${attachment.attacthSuffix}"></em></div><div class="txt"><p>${attachment.attachName }</p><p><a style="cursor:pointer"  onclick="viewfileOpen(${attachment.id})">预览</a> <a style="cursor:pointer"  onclick="deleteAttachment_daily(${attachment.id},this)">删除</a></p></li>
                                    </c:forEach>
                                </ul>
                        </td>
                    </tr>
                    </tbody>
                </table>

            </div>
            <!-- input div结束 -->
        </div>
        <div class="buttonArea" style="text-align: center">
            <input hideFocus="" value="保 存" type="button"
                   class="formButton_green" onclick="addOrUpdate(this)" />
        </div>
    </form>
</div>
</body>
</html>