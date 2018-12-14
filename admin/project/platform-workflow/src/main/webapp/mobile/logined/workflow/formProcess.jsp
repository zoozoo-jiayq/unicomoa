<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>填写表单</title>
    <jsp:include page="workflowHead.jsp" />
    <link rel="stylesheet" type="text/css" href="${ctx}mobile/css/style.css"/>
    
</head>
<body>
<div class="container-fluid">
	<input type="hidden" value="<%request.getParameter("processId") %>" id="processId">
    <form class="form-horizontal">
        <div class="input-group">
            <p>表单名称表单名称表单名称表单名称表单名称表单名称表单名称表单名称表单名称表单名称表单名称表单名称可以折行显示</p>
        </div>
        <div class="input-group">
            <span class="input-group-addon bdnone" style="width:50%;text-align:left">内容</span>
            <p style="font-size:14px">内容多了可以换行内容多了可以换行内容多了可以换行内容多了可以换行内容多了可以换行内容多了可以</p>
        </div>
        <div class="input-group">
            <span class="input-group-addon bdnone">人员选择</span>
            <span class="form-control bdnone rtext">张三</span>
        </div>
        <div class="input-group">
            <span class="input-group-addon bdnone">人员复选</span>
            <span class="form-control bdnone rtext">张三、李四</span>
        </div>
        <div class="input-group">
            <span class="input-group-addon bdnone">单选项</span>
            <span class="form-control bdnone rtext">选项名称1</span>
        </div>
        <div class="input-group">
            <span class="input-group-addon bdnone">复选项</span>
            <span class="form-control bdnone rtext">选项名称1、选项名称3</span>
        </div>
        <div id="hiddenBody" style="display:none">
            <div class="input-group">
                <span class="input-group-addon bdnone">选择部门</span>
                <span class="form-control bdnone rtext"></span>
            </div>
            <div class="input-group">
                <span class="input-group-addon bdnone">审批意见</span>
                <span class="form-control bdnone rtext"></span>
            </div>
        </div>
        <div class="push">
            <div class="input-group" style="background-color:rgb(217,217,217)" id="push">
                <span class="input-group-addon bdnone" style="text-align:left" id="pushinner">展开所有表单项</span>
            </div>
        </div>
        <div class="block-item bt10 addExaPer">
            <span class="gray correct">审批人<span>(长按头像可删除)</span></span>
            <div class="swiper-container-header">
                            <div class="head headImg">
                                <div class="round"><img src="../images/tx1.jpg"></div>
                                <p class="center">乔佳</p>
                            </div>
                            <div class="head headImg">
                                <div class="round ico-1">乔佳</div>
                                <p class="center">乔佳</p>
                            </div>
                            <div class="head headImg">
                                <div class="round"><img src="../images/tx1.jpg"></div>
                                <p class="center">欧阳乔峰</p>
                            </div>
                            <div class="head">
                               <div class="round"><img src="../images/round_plus.png"/></div>
                                <p class="center">请选择</p>
                            </div>
            </div>
        </div>
        <div class="buttonArea">
            <button class="btn btn-primary">提交</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        var num = $(".swiper-slide .head").length;
        var tie = (num+1) * 45;
        $(".swiper-slide").css("width",tie);
        var mySwiper = new Swiper('.swiper-container',{
            scrollContainer: true
        });
        $('#push').click(function(){
            $('#hiddenBody').slideToggle();
            if($('#pushinner').html()=="展开所有表单项"){
                $('#pushinner').html('收起多余选项')
                $('#push').addClass('active')
            }else{
                $('#pushinner').html('展开所有表单项')
                $('#push').removeClass('active')
            }
        });
    });
</script>
<script type="text/javascript" src="${ctx}mobile/js/logined/workflow/formProcess.js"></script>
</body>
</html>