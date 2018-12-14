<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>邮件搜索</title>
    <jsp:include page="../../common/head.jsp"/>
    <style type="text/css">
        .inputTable th {
            width: 120px;
        }

        .starsPop0 {
            position: relative;
        }

        .starsPop {
            position: absolute;
            top: 16px;
            left: 30px;
        }
    </style>

</head>
<body>
<div class="input" style="width:auto">
    <div class="pageTitle"><em class="iconSearch">查询邮件</em></div>
    <form action="${ctx}logined/email/searchList.action" method="post">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" class="inputTable">
            <tr>
                <th>选择邮箱：</th>
                <td class="mailBox_sel">
                    <select name="emailSearchVo.emailBoxId" id="emailBoxId">
                        <s:iterator value="#boxList" var="emailBox">
                            <option value="${emailBox.id}" boxType="${emailBox.boxType}">${emailBox.boxName}</option>
                        </s:iterator>
                    </select>
                    <input type="hidden" class="selectHidden" id="emailBoxId_hidden"
                           value="${emailSearchVo.emailBoxId}"/>
                </td>
            </tr>
            <tr>
                <th><label>邮件状态：</label></th>
                <td>
                    <select name="emailSearchVo.readStatus" id="readStatus">
                        <option value="">全部</option>
                        <option value="0">未读</option>
                        <option value="1">已读</option>
                    </select>
                    <input type="hidden" class="selectHidden" id="readStatus_hidden"
                           value="${emailSearchVo.readStatus}"/>
                </td>
            </tr>
            <tr class="showHide">
                <th><label>邮件星标：</label></th>
                <td>
                    <div class="starsPop0">
                    <span class="wauto">
                        <em id="markLevelEm" class="all_color"></em>
                        <a class="selectStar" href="javascript:void(0);">选择</a>
                    </span>

                    <div class="starsPop">
                        <ul id="markLevelUL">
                            <li><span style="width: 30px;" class="all_color" level="">所有</span></li>
                            <li><span class="red" level="4">红</span></li>
                            <li><span class="yellow" level="3">黄</span></li>
                            <li><span class="green" level="2">绿</span></li>
                            <li><span class="gray" level="1">灰</span></li>
                            <li><span class="none" level="0">无</span></li>
                        </ul>
                    </div>
                    </div>
                    <input type="hidden" name="emailSearchVo.markLevel" id="markLevel"
                           value="${emailSearchVo.markLevel}"/>
                </td>
            </tr>
            <tr>
                <th><label>日期：</label></th>
                <td>
                    <input type="text" class="formText Wdate"  name="emailSearchVo.timeStart" id="timeStart" onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'timeEnd\')}',skin: 'default', dateFmt: 'yyyy-MM-dd'});"
                           value="${emailSearchVo.timeStart}"/>
                    至 <input type="text" class="formText Wdate"  name="emailSearchVo.timeEnd" id="timeEnd" onFocus="WdatePicker({minDate: '#F{$dp.$D(\'timeStart\')}',skin: 'default', dateFmt: 'yyyy-MM-dd'});"
                             value="${emailSearchVo.timeEnd}"/>
                </td>
            </tr>
            <tr>
                <th><label class="mailPerson">发件人：</label></th>
                <td>
                    <input type="hidden" name="emailSearchVo.userIds" id="userIds" value="${emailSearchVo.userIds}"/>
                    <input type="text" readonly="readonly" class="formText" size="30" name="emailSearchVo.userNames"
                           id="userNames"
                           value="${emailSearchVo.userNames}"/>
                    <a class="icon_add" href="javascript:selectUser('userNames','userIds')">选择</a>
                    <a class="icon_clear" href="javascript:cleanUser('userNames','userIds')">清空</a>
                </td>
            </tr>
            <tr>
                <th><label>邮件主题包含文字：</label></th>
                <td>
                    <input type="text" class="formText" size="30" name="emailSearchVo.subject" id="subject"
                           value="${emailSearchVo.subject}"/>
                </td>
            </tr>
            <tr>
                <th><label>邮件内容[查询词1]：</label></th>
                <td>
                    <input type="text" class="formText" size="30" name="emailSearchVo.contentInfo1" id="contentInfo1"
                           value="${emailSearchVo.contentInfo1}"/>
                </td>
            </tr>
            <tr>
                <th><label>邮件内容[查询词2]：</label></th>
                <td>
                    <input type="text" class="formText" size="30" name="emailSearchVo.contentInfo2" id="contentInfo2"
                           value="${emailSearchVo.contentInfo2}"/>
                </td>
            </tr>
            <tr>
                <th><label>邮件内容[查询词3]：</label></th>
                <td>
                    <input type="text" class="formText" size="30" name="emailSearchVo.contentInfo3" id="contentInfo3"
                           value="${emailSearchVo.contentInfo3}"/>
                </td>
            </tr>
            <tr>
                <th><label>附件文件名包含文字：</label></th>
                <td>
                    <input type="text" class="formText" size="30" name="emailSearchVo.attachmentName"
                           id="attachmentName"
                           value="${emailSearchVo.attachmentName}"/>
                </td>
            </tr>
        </table>
        <div class="buttonArea">
        <span class="mainButton">
            <input value="查 询" type="submit"/>
        </span>
        </div>
    </form>
</div>
<script type="text/javascript" src="${ctx}/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/user/selectuser.js"></script>
<script type="text/javascript" src="${ctx}/js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}/js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx}/js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}/js/common/showError.js"></script>
<script type="text/javascript" src="${ctx}/js/logined/email/email_common_select_user.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        setSelectDefaultValue();
        setMarkLevelDefaultValue();
        initFormDif();
        initMarkLevelPop();
     //   initMy97DatePicker();
    });

    //对于存在 class为date的输入框，进行My97DatePicker初始化
  /*   function initMy97DatePicker() {
        $(".date").addClass("Wdate").click(function () {
            WdatePicker();
        });
    } */

    function initMarkLevelPop() {
        /* $(".starsPop0").mouseover(function (e) {
            $(".starsPop").show();
            $(".starsPop").mouseout(function () {
                $(this).hide();
                return false;
            });
            return false;
        }); */
        $(".starsPop0").mouseover(function (e) {
            $(".starsPop").show();
            return false;
        });
        $(".starsPop0").mouseout(function () {
        	$(".starsPop").hide();
            return false;
        });
        
        $("#markLevelUL li").click(function () {
            var cls = $(this).find("span").attr("class");
            $("#markLevelEm").attr("class", cls);
            $("#markLevel").val($(this).find("span").attr("level"));
            $(".starsPop").hide();
        });
    }

    function initFormDif() {
        $(".mailBox_sel select").click(function () {
            var boxType = $(this).find('option:selected').attr("boxType");
            if (boxType == "2" || boxType == "3") {
                $(this).closest(".inputTable").find(".showHide").hide().val("");
                $(this).closest(".inputTable").find(".mailPerson").text("收件人");
            } else {
                $(this).closest(".inputTable").find(".showHide").show();
                $(this).closest(".inputTable").find(".mailPerson").text("发件人");
            }
        });
    }

    /**
     *修改时初始化 select值
     */
    function setSelectDefaultValue() {
        $(".selectHidden").each(function () {
            if ($(this).val() != "") {
                var theId = $(this).attr("id").toString();
                var selectId = theId.substring(0, theId.lastIndexOf("_"));
                $("#" + selectId).val($(this).val());
            }
        });
    }

    function setMarkLevelDefaultValue() {
        var markLevelVal = $("#markLevel").val();
        if (markLevelVal != "") {
            var cls = $("#markLevelUL li span[level='" + markLevelVal + "']").attr("class");
            $("#markLevelEm").attr("class", cls);
        }
    }

</script>
</body>
</html>
