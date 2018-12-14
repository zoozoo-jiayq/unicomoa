<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<jsp:include page="../../common/taglibs.jsp"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>绩效考核</title>
    <link rel="stylesheet" type="text/css"
          href="${ctx}/logined/appraisal/plugins/uploadify.css" />
    <script type="text/javascript" src="${ctx}/logined/appraisal/plugins/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/logined/appraisal/plugins/attachHelp.js"></script>
    <script type="text/javascript" src="${ctx}/logined/appraisal/plugins/attachment.js"></script>
    <script type="text/javascript" src="${ctx}/logined/appraisal/common/commonUpload.js"></script>
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
    <script type="text/javascript"
            src="${ctx}/logined/appraisal/js/staff/self-assessment.js?version=${version}"></script>
    <script type="text/javascript">
    function test(){
    	parent.changeIframe("http://www.baidu.com");
    }
    </script>
</head>
<body>
<input type="hidden" id="tid" value="${param.tid}">
<input type="hidden" id="zt" value="${param.zt}">
<!-- 右侧内容模块 -->
<div class="iframe_right_content">
    <div class="navigation_bar">
        <p>当前位置：
            <a href="#">首页</a>
            <span> > 自述自评</span>
        </p>
    </div>
    <div class="kh_score_box">
        <div class="pr">
            <p class="kh_score_title" id="khmc"></p>
        </div>
        <div class="kh_score_detail">
            <textarea id="wdsz" class="creat_list_txt" maxlength="1000" placeholder="请输入您对自己的意见或建议"></textarea>
            <table width="100%" cellspacing="0" cellpadding="0" border="0"
                   class="inputTable">
                <tbody>
                <tr>
                    <td colspan="3" id="fileAttachTd">
                        <input type="hidden"  id="attachmentId"/>
                        <input id="file_upload" name="fileupload" type="file" multiple="true" />
                        <!-- 上传队列 -->
                        <div id="queue"  style="display:none;"></div>
                        <div class="annex">
                            <ul id="attachmentList">

                            </ul>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <ul class="khinfo_list">
                <li>
                    <span class="khinfo_list_title">考核项：</span>
                    <table class="score_table">
                        <thead>
                        <tr>
                            <th class="wd115">指标分类</th>
                            <th class="wd165">指标名称</th>
                            <th class="wd400">指标描述</th>
                            <th class="wd100">分值</th>
                            <th class="wd100">打分</th>
                        </tr>
                        </thead>
                        <tbody id="table">
                        </tbody>
                    </table>
                </li>
            </ul>
            <p class="kh_total">总计：<span id="total">0</span>分</p>
            <div class="btn_container2">
                <span class="btn_inline btn_white" onclick="post()">预览</span>
                <span class="btn_inline btn_gray ml15" onclick="reset()">重置</span>
            </div>

        </div>
    </div>
</div>
</body>
</html>
