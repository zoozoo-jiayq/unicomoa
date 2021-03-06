<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
 <head>
    
  <title>表单设计器</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="leipi.org">
    <link href="${ctx }/logined/customForm/html_drag_editor/Public/css/bootstrap/css/bootstrap.css?2024" rel="stylesheet" type="text/css" />
    <link href="${ctx }/logined/customForm/html_drag_editor/Public/css/site.css?2024" rel="stylesheet" type="text/css" />
    

    
<style>
	body{overflow:hidden;}
      #target{
        padding: 5px;
      }
      #target .component{
        border: 1px solid #fff;
      }
      #temp{
        width: 500px;
        background: white;
        border: 1px dotted #ccc;
        border-radius: 10px;
      }

      .popover-content form {
        margin: 0 auto;
        width: 213px;
      }
      .popover-content form .btn{
        margin-right: 10px
      }
      #source{
        min-height: 500px;
      }

      .plugDesign{
        float:left;
        border:1px dashed #fff;
        margin-left: 0px;
        margin-right: 10px;
      }
      /*石鑫修改*/
      .bg{display:block;width: 100%;height: 100%;}
      .popover{top:60px !important;}
      .fl{float: left;}
      .hide{display: none;}
      .ml20{margin-left: 20px;}
      .ml200{margin-left: 200px;}
      .container{position: absolute;top: 0px;left: 50%;margin-left: -600px;}
      .nav{margin-bottom: 0px;height: 60px;}
      .nav_left{height: 60px;line-height: 60px;text-align: center;margin-left: 0px;}
      .nav_left img{float: left;margin-top: 10px;}
      .nav_left span{display: block;float: left;font-size: 20px;color: #fff;margin-left: 10px;}
      .nav_left span:nth-child(4){font-size: 16px;}
      .nav_right{width: 220px;height: 60px;float: right;}
      .nav_right span{display: block;padding: 0 25px;margin-left: 10px;line-height: 30px;text-align: center;float: right;font-size: 14px;background: #aaa;color: #fff;border-radius: 2px;cursor: pointer;margin-top: 15px;}
      	/*控件区域*/
      .control_title{display: block;width: 100px;height: 37px;background: url(${ctx }/logined/customForm/html_drag_editor/Public/js/formbuild/images/title.png);line-height: 30px;text-align: center;color: #fff;margin-top: 104px;}
	  .control_title1{display: block;width: 100px;height: 37px;line-height: 30px;text-align: center;color: #fff;margin-top: 104px;}
      .tabbable{margin-top: 40px;}	
      .tab-pane .control_tip{width: 138px !important;height: 46px;padding: 0px !important;margin:0px !important;text-align: left !important;line-height: 46px;text-indent: 10px;color: #fff;background: #416785;}
      #build{background: url(${ctx }/logined/customForm/html_drag_editor/Public/js/formbuild/images/iPhone.png) no-repeat top;width: 524px;overflow: hidden;height:1060px !important;padding-top: 153px;}
      #build form{width: 460px;margin-left: 32px;background: #ebebeb!important;overflow-y:scroll;}
      form{padding: 0px !important;}
          /*明细控件样式*/
      #build .details{width: 100% !important;height: 48px;}
      #build .details input{width: 100%;line-height:40px;text-align: center;border:none;background: #fff;color: #06aeed;font-size: 14px;}    
      	/*审批设置*/
      .setting{width: 336px;overflow: hidden;float: right;}
      .setting_title{overflow: hidden;}
      .setting_title span{cursor: pointer;}	
      .setting_tip{overflow: hidden;margin-top: 40px;}
      .setting_tip .tip_one{overflow: hidden;}
      .setting_tip .tip_two{overflow: hidden;}
      .tiptwo_area{overflow: hidden;}
      .tiptwo_area .area_title{display: block;width: 100%;text-align: left;font-size: 14px;color:#fff;}
      .tiptwo_area .area_title span{font-size: 12px;color: #999;margin-left: 10px;}
      .tiptwo_area input{margin-top: 10px;width: 100%;height: 30px;border:1px solid #fff;border-radius: 2px;background: #305169;color: #fff;box-sizing:border-box;font-size: 12px;}
      .tiptwo_area textarea{margin-top: 10px;width: 100%;line-height: 20px;border:1px solid #fff;border-radius: 2px;background: #305169;color: #fff;box-sizing:border-box;resize:none;height: 80px;font-size: 12px;}
      	/*控件拖拽后的样式*/
      #target .component{background: #fff;min-height: 46px;overflow: hidden;margin-bottom: 0px;margin-top: 12px;}
      #target .component label{width:30%;color: #333;line-height: 46px;text-align: left;text-indent: 12px;padding: 0px;margin: 0px;font-size: 16px;}
      #target .component .controls{width: 70%;margin: 0px;float: left;}
      #target .component .entry{width:100%;height: 46px;line-height: 46px;text-align: left;font-size: 14px;color: #333;padding: 0px;margin: 0px;border:none;}
      #target .component textarea{width:100%;line-height: 20px;text-align: left;font-size: 14px;color: #333;padding: 0px;margin: 0px;border:none;resizing:none;min-height: 80px;}
      #target .component select{width:100%;height: 46px;text-align: left;font-size: 14px;color: #333;padding: 0px;margin: 0px;border:none;}
      #target .component .single_sel{margin-top: 16px;}
      #target .component .more_sel{margin-top: 16px;}
    </style>
    <script type="text/javascript">
        function showPlugRange(obj){
           $(obj).css({border:"1px dashed red"});
        }
        function hidePlugRange(obj){
           	$(obj).css({border:""}); 
        }
      
    </script>
 </head>
<body>
<input type="hidden" name="FORM_ID" id="FORM_ID"  value="${formId }" />
<img class="bg" src="${ctx }/logined/customForm/html_drag_editor/Public/js/formbuild/images/bg_change.png" alt="">
<div class="container">
	<div class="nav">
		<div class="nav_left span3">
			<img src="${ctx }/logined/customForm/html_drag_editor/Public/js/formbuild/images/logoo.png" alt="">
			<span>审批管理后台</span>
			<span>|</span>
			<span>设计器</span>
		</div>
		<div class="nav_right">
			<!--<span>保存并启用</span>-->
			<span id="save">保存</span>
		</div>
	</div>
  <div class="row clearfix">
   
    <div class="span3 ml200">
        <span class="control_title">控件</span>
      <div class="tabbable">
        <form class="form-horizontal" id="components">
          <fieldset>
            <div class="tab-content">

              <div class="tab-pane active" id="1" >


<!-- Text start -->
<div class="control-group component span2.8 plugDesign"  rel="popover" title="文本框控件" trigger="manual" data-content="
  <form class='form'>
    <div class='controls'>
      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
      <label class='control-label'>默认值</label> <input type='text' id='orgvalue' placeholder='默认值'>
	  <label class='control-label'>数据类型</label>
	  <select id='contenttype' title='数据类型' >
		<option value='string'>字符串</option>
		<option value='number'>数字</option>
	  </select>
	  <label class='control-label'>手机端是否显示</label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
	      否
	  </label>	  
	  <label class='control-label'>是否必填</label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
	      否
	  </label>
      <hr/>
      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
      <button class='btn btn-warning' type='button'>删除</button>
    </div>
  </form>"
  >
  <!-- Text -->
  <label class="control_tip control-label leipiplugins-orgname ">文本框</label>
  <div class="controls" style="display:none">
    <input type="text" placeholder="默认值" cnname="%文本框%" name="_WenBenKuang_" contenttype="string" title="文本框" value="" class="leipiplugins entry" clientshow="yes" leipiplugins="text"/>
  </div>

</div>
<!-- Text end -->


<!-- Textarea start -->           
<div class="control-group component span2.8 plugDesign"  rel="popover" title="多行文本控件" trigger="manual" data-content="
  <form class='form'>
    <div class='controls'>
      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
      <label class='control-label'>默认值</label> <input type='text' id='orgvalue' placeholder='默认值'>
      <label class='control-label'>手机端是否显示</label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
	      否
	  </label>	
	  <label class='control-label'>是否必填</label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
	      否
	  </label>
      <hr/>
      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
      <button class='btn btn-warning' type='button'>删除</button>
    </div>
  </form>"
  >
  <!-- Textarea -->
  <label class="control_tip control-label leipiplugins-orgname">多行文本</label>
  <div class="controls" style="display:none">
    <div class="textarea">
          <textarea title="多行文本" cnname="%多行文本%" clientshow="yes" name="_DuoXingWenBen_" class="leipiplugins" leipiplugins="textarea"/></textarea>
    </div>
  </div>
</div>
<!-- Textarea end -->

<!-- Select start -->
<div class="control-group component span2.8 plugDesign"  rel="popover" title="下拉控件" trigger="manual" data-content="
  <form class='form'>
    <div class='controls'>
      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
      <label class='control-label'>下拉选项</label>
      <textarea style='min-height: 100px' id='orgvalue'></textarea>
      <p class='help-block'>一行一个选项</p>
      <label class='control-label'>手机端是否显示</label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
	      否
	  </label>
	  <label class='control-label'>是否必填</label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
	      否
	  </label>
      <hr/>
      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
      <button class='btn btn-warning' type='button'>删除</button>
    </div>
  </form>"
  >
  <!-- Select -->
  <label class="control_tip control-label leipiplugins-orgname">下拉菜单</label>
  <div class="controls" style="display:none">
    <select name="_XiaLaCaiDan_" title="下拉菜单" cnname="%下拉菜单%" clientshow="yes" class="leipiplugins" leipiplugins="select">
      <option>选项一</option>
      <option>选项二</option>
      <option>选项三</option>
    </select>
  </div>

</div>
<!-- Select end -->

<!-- Multiple radios start -->
<div class="control-group component span2.8 plugDesign"  rel="popover" title="单选控件" trigger="manual" data-content="
  <form class='form'>
    <div class='controls'>
      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
      <label class='control-label'>单选框</label>
      <textarea style='min-height: 100px' id='orgvalue'></textarea>
      <p class='help-block'>一行一个选项</p>
      <label class='control-label'>手机端是否显示</label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
	      否
	  </label>
      <label class='control-label'>是否必填</label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
	      否
	  </label>
      <hr/>
      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
      <button class='btn btn-warning' type='button'>删除</button>
    </div>
  </form>"
  >
  <label class="control_tip control-label leipiplugins-orgname">单选框</label>
  <div class="controls leipiplugins-orgvalue" style="display:none">
    <!-- Multiple Checkboxes -->
    <label class="radio inline">
      <input type="radio" name="_DanXuanKuang_" cnname="%单选框%" clientshow="yes" title="单选框" value="选项1" class="leipiplugins single_sel" leipiplugins="radio" orginline="inline">
      选项1
    </label>
    <label class="radio inline">
      <input type="radio" name="_DanXuanKuang_" cnname="%单选框%" clientshow="yes" title="单选框" value="选项2" class="leipiplugins single_sel" leipiplugins="radio" orginline="inline">
      选项2
    </label>
  </div>
</div>

<!-- Multiple radios end -->


<!-- Multiple Checkboxes start -->

<div class="control-group component span2.8 plugDesign"  rel="popover" title="复选控件" trigger="manual" data-content="
  <form class='form'>
    <div class='controls'>
      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
      <label class='control-label'>复选框</label>
      <textarea style='min-height: 100px' id='orgvalue'></textarea>
      <p class='help-block'>一行一个选项</p>
      <label class='control-label'>手机端是否显示</label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
	      否
	  </label>
      <label class='control-label'>是否必填</label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' value='1'>
		  是
	  </label>
	  <label class='radio inline'>
		  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
	      否
	  </label>
      <hr/>
      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
      <button class='btn btn-warning' type='button'>删除</button>
    </div>
  </form>"
  >
  <label class="control_tip control-label leipiplugins-orgname">复选框</label>
  <div class="controls leipiplugins-orgvalue" style="display:none">
    <!-- Multiple Checkboxes -->
    <label class="checkbox inline">
      <input type="checkbox" name="_FuXuanKuang_" cnname="%复选框%" clientshow="yes" title="复选框" value="选项1" class="leipiplugins more_sel" leipiplugins="checkbox" orginline="inline">
      选项1
    </label>
    <label class="checkbox inline">
      <input type="checkbox" name="_FuXuanKuang_" cnname="%复选框%" clientshow="yes" title="复选框" value="选项2" class="leipiplugins more_sel" leipiplugins="checkbox" orginline="inline">
      选项2
    </label>
  </div>

</div>

	<!-- 日历控件 start -->
	<div class="control-group component span2.8 plugDesign"   rel="popover" title="日历控件"  trigger="manual" data-content="
	  <form class='form'>
	    <div class='controls'>
	      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
		  <label class='control-label'>格式类型</label>
		  <select id='dateformat' title='格式类型' >
			<option value='yyyy-MM'>日期，形如：2010-09</option>
			<option value='yyyy-MM-dd'>日期，形如：2010-09-09</option>
			<option value='yyyy-MM-dd HH:mm'>日期，形如：2010-09-09 09:25</option>
			<option value='yyyy-MM-dd HH:mm:ss'>日期，形如：2010-09-09 09:25:00</option>
			<option value='yyyy-MM-dd AM/PM'>日期，形如：2010-09-09 上午</option>
		  </select>
		  <label class='control-label'>手机端是否显示</label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
		      否
		  </label>
		  <label class='control-label'>是否必填</label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
		      否
		  </label>
	      <hr/>
	      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
        <button class='btn btn-warning' type='button'>删除</button>
	    </div>
	  </form>"
	  >
	  <!-- Text -->
	  <label class="control_tip control-label leipiplugins-orgname">日历控件</label>
	  <div class="controls" style="display:none">
	    <input type="text" cnname="%日历控件%" name="_RiLiKongJian_" clientshow="yes" title="日历控件" value="" class="leipiplugins Wdate entry"  date_format="yyyy-MM" datewidget="dateWidget" leipiplugins="date"/>
	  </div>

	</div>
	<!-- 日历控件 end -->

	<!-- 人员控件 start -->
	<div class="control-group component span2.8 plugDesign" rel="popover"  title="人员控件" trigger="manual" data-content="
	  <form class='form'>
	    <div class='controls'>
	      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
	      <label class='control-label'>手机端是否显示</label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
		      否
		  </label>
		  <label class='control-label'>是否必填</label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
		      否
		  </label>
	      <hr/>
	      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
        <button class='btn btn-warning' type='button'>删除</button>
	    </div>
	  </form>"
	  >
	  <!-- Text -->
	  <label class="control_tip control-label leipiplugins-orgname">人员控件</label>
	  <div class="controls" style="display:none">
	    <input type="text" cnname="%人员控件%" name="_RenYuanKongJian_" clientshow="yes" selectuser="selectuser" style="cursor: hand" title="人员控件" value="" class="leipiplugins USER entry" leipiplugins="selectuser"/>
	  </div>

	</div>
	<!-- 人员控件 end -->

	<!-- 部门控件 start -->
	<div class="control-group component span2.8 plugDesign" rel="popover"  title="部门控件" trigger="manual" data-content="
	  <form class='form'>
	    <div class='controls'>
	      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
	      <label class='control-label'>手机端是否显示</label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
		      否
		  </label>
		  <label class='control-label'>是否必填</label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
		      否
		  </label>
	      <hr/>
	      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
        <button class='btn btn-warning' type='button'>删除</button>
	    </div>
	  </form>"
	  >
	  <!-- Text -->
	  <label class="control_tip control-label leipiplugins-orgname">部门控件</label>
	  <div class="controls" style="display:none">
	    <input type="text" cnname="%部门控件%" name="_BuMenKongJian_" selectgroup="selectgroup" clientshow="yes" style="cursor: hand" title="部门控件" value="" class="leipiplugins USER entry" leipiplugins="selectgroup"/>
	  </div>

	</div>
	<!-- 部门控件 end -->

	<!-- 审批意见控件 start -->           
	<div class="control-group component span2.8 plugDesign" rel="popover"  title="审批意见控件" trigger="manual" data-content="
	  <form class='form'>
	    <div class='controls'>
	      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
	      <label class='control-label'>默认值</label> <input type='text' id='orgvalue' placeholder='默认值'>
	      <label class='control-label'>手机端是否显示</label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
		      否
		  </label>
	      <hr/>
	      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
        <button class='btn btn-warning' type='button'>删除</button>
	    </div>
	  </form>"
	  >
	  <!-- Textarea -->
	  <label class="control_tip control-label leipiplugins-orgname">审批意见控件</label>
	  <div class="controls" style="display:none">
	    <div class="textarea">
	          <textarea title="审批意见控件" cnname="%审批意见控件%" clientshow="yes" name="_ShenPiYiJianKongJian_" adviceflag="advice" class="leipiplugins" leipiplugins="advice"/></textarea>
	    </div>
	  </div>
	</div>
	<!-- 审批意见控件 end -->

	<!-- 计算控件 start -->
	<div class="control-group component span2.8 plugDesign" rel="popover"  title="计算控件" trigger="manual" data-content="
	  <form class='form'>
	    <div class='controls'>
	      <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
		  <label class='control-label'>编辑表达式</label>
		  <textarea style='min-height: 80px' id='expr'></textarea>
      	  <p class='help-block' style='color:blue;'>说明：本控件支持“+”,“-”,“*”,“/”计算，例如：控件1 + 控件2 - 控件3 * 控件4，其中控件1,2,3,4代表用户输入的控件名称。</p>
      	  <label class='control-label'>手机端是否显示</label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
		      否
		  </label>
      	  <label class='control-label'>是否必填</label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
		      否
		  </label>
	      <hr/>
	      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
        <button class='btn btn-warning' type='button'>删除</button>
	    </div>
	  </form>"
	  >
	  <!-- Text -->
	  <label class="control_tip control-label leipiplugins-orgname">计算控件</label>
	  <div class="controls" style="display:none">
	    <input type="text" cnname="%计算控件%" name="_JiSuanKongJian_" title="计算控件" clientshow="yes" value="" class="leipiplugins USER entry" calu="1" leipiplugins="calculate"/>
	  </div>

	</div>
	<!-- 计算控件 end -->

	<!-- 日期范围控件 start -->
	<div class="control-group component span2.8 plugDesign"  rel="popover" title="日期范围控件" trigger="manual" data-content="
	  <form class='form'>
	    <div class='controls'>
	      <label class='control-label'>开始日期控件名称</label> <input type='text' id='orgname1' placeholder='开始日期'>
	      <label class='control-label'>结束日期控件名称</label> <input type='text' id='orgname2' placeholder='结束日期'>
	      <label class='control-label'>日期范围控件名称</label> <input type='text' id='orgname3' placeholder='日期范围'>
		  <label class='control-label'>格式类型</label>
		  <select id='dateformat' title='格式类型' >
			<option value='yyyy-MM'>日期，形如：2010-09</option>
			<option value='yyyy-MM-dd'>日期，形如：2010-09-09</option>
			<option value='yyyy-MM-dd HH:mm'>日期，形如：2010-09-09 09:25</option>
			<option value='yyyy-MM-dd HH:mm:ss'>日期，形如：2010-09-09 09:25:00</option>
			<option value='yyyy-MM-dd AM/PM'>日期，形如：2010-09-09 上午</option>
		  </select>
		  <label class='control-label'>手机端是否显示</label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
		      否
		  </label>
		  <label class='control-label'>是否必填</label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' value='1'>
			  是
		  </label>
		  <label class='radio inline'>
			  <input type='radio' name='required' id='required' title='单选框' checked='checked' value='0' >
		      否
		  </label>
	      <hr/>
	      <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
        <button class='btn btn-warning' type='button'>删除</button>
	    </div>
	  </form>"
	  >
	  <!-- Text -->
	  <label class="control_tip control-label leipiplugins-orgname">日期范围控件</label>
	  <div class="controls" style="display:none" >
	  </div>
	  <label class="control-label leipiplugins-orgname1" style="display:none">开始日期</label>
	  <div class="controls" style="display:none" >
	  		<input type="text" cnname="%开始日期%" name="_KaiShiRiQi_" clientshow="yes" title="开始日期" other="结束日期" calendarrangeflag="start" value="" class="leipiplugins Wdate entry"  date_format="yyyy-MM" datewidget="dateWidget" leipiplugins="date_range"/>
      </div>
	  <label class="control-label leipiplugins-orgname2" style="display:none">结束日期</label>
	  <div class="controls" style="display:none" >
	  		<input type="text" cnname="%结束日期%" name="_JieShuRiQi_" clientshow="yes" title="结束日期" other="开始日期" calendarrangeflag="end" value="" class="leipiplugins Wdate entry"  date_format="yyyy-MM" datewidget="dateWidget" leipiplugins="date_range"/>
	  </div>
	  <label class="control-label leipiplugins-orgname3" style="display:none">日期范围</label>
	  <div class="controls" style="display:none" >
	  		<input type="text" cnname="%日期范围%" name="_RiQiFanWei_" clientshow="yes" title="日期范围" calendarrange_expr="结束日期-开始日期" leipiplugins="date_range" value="" class="leipiplugins entry" />
	  </div>
	</div>

  <!-- 明细控件 start -->
  <div class="control-group component span2.8 plugDesign " rel="popover"  title="明细控件" trigger="manual"  data-content="
    <form class='form'>
      <div class='controls'>
        <label class='control-label'>控件名称</label> <input type='text' id='orgname' placeholder='必填项'>
      
          <label class='control-label'>手机端是否显示</label>
      <label class='radio inline'>
        <input type='radio' name='clientshow' id='clientshow' title='单选框' checked='checked' value='1'>
        是
      </label>
      <label class='radio inline'>
        <input type='radio' name='clientshow' id='clientshow' title='单选框' value='0' >
          否
      </label>
          
        <hr/>
        <button class='btn btn-info' type='button'>确定</button><button class='btn btn-danger' type='button'>取消</button>
        <button class='btn btn-warning' type='button'>删除</button>
      </div>
    </form>"
    >
  	<label class="control_tip control-label detaillabel leipiplugins-orgname">明细控件</label>
    <div class="controls detailcontrols details"  style="display:none;">
      <input type="button" class="btn btn-default btn-lg leipiplugins" clientshow="yes" title="明细" expr="" leipiplugins="detailWidget" value="+  明细" name="_MingXi_" cnname="%明细%" detail="detail"/>
    </div>
  </div>

              </div>
              <div class="tab-pane" id="5">
                <textarea id="source" class="span6"></textarea>
              </div>              
            </fieldset>
          </form>
        </div><!--tab-content-->
        </div><!---tabbable-->
        <div class="span4">
	      <div class="clearfix">
	        <div id="build">
	          <form id="target" class="form-horizontal">
	            <fieldset id="sourceContainer">
	            ${fa.formSource }
	            </fieldset>
	          </form>
	        </div>
	      </div>
	    </div>
	    <div class="setting" style="display:none">
	    	<div class="setting_title">
	    		<span class="control_title1 fl">控件设置</span>
	    		<span class="control_title fl ml20">审批设置</span>		
	    	</div>
	    	<div class="setting_tip">
	    		<div class="tip_one hide">
	    				
	    		</div>
	    		<div class="tip_two">
	    			<div class="tiptwo_area">
	    				<span class="area_title">审批名称<span>最多10个字</span></span>
	    				<input type="text" name="" value="" placeholder="" />
	    			</div>
	    			<div class="tiptwo_area">
	    				<span class="area_title">审批说明<span>最多100个字</span></span>
	    				<textarea name=""></textarea>
	    			</div>	
	    		</div>	
	    	</div>	
	    </div>
      </div> <!-- row -->     
    </div> <!-- /container -->
    
<script type="text/javascript" charset="utf-8" src="${ctx }/logined/customForm/html_drag_editor/Public/js/jquery-1.8.1.min.js?2024"></script>
<script type="text/javascript" charset="utf-8" src="${ctx }/logined/customForm/html_drag_editor/Public/js/pinyin.js?2024"></script>
<script type="text/javascript"  src="${ctx }/logined/customForm/html_drag_editor/Public/js/formbuild/bootstrap/js/bootstrap.min.js?2024"></script>
<script type="text/javascript" charset="utf-8" src="${ctx }/logined/customForm/html_drag_editor/Public/js/formbuild/leipi.form.build.core.js?2024"></script>
<script type="text/javascript" charset="utf-8" src="${ctx }/logined/customForm/html_drag_editor/Public/js/formbuild/leipi.form.build.plugins.js?2024"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog_alert.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}js/common/artClose.js"></script>
<script type="text/javascript">
	  $(function(){
		  
		  if(event.keyCode == 13){
			  alert(1);
			  return false;
		  }
		  
		  var basePath ="${ctx}";
        	var H = $(window).height();
          var BH = H-60;
          if(BH<1060){
            $("#build").css("height",BH+"px");
          }
          else{
            $("#build").css("height","1060px")
          }
        	$("body").css("height",H+"px")
          if(BH<910){
            $("#build form").css("height",(BH-170)+"px")
          }
          else{
            $("#build form").css("height",(BH-370)+"px")
          }
        	
        	$(".setting_title span").click(function(){
        		var index = $(this).index();
        		$(this).removeClass("control_title1").addClass("control_title");
        		$(this).siblings("span").removeClass("control_title").addClass("control_title1");
        		$(".setting_tip div").eq(index).removeClass("hide");
        		$(".setting_tip div").eq(index).siblings("div").addClass("hide")
        	})

          $("#save").click(function(){
        	  checkTitleIsRepeat();
            //保存表单控件
              var paramData={
          			'formContent':$("#sourceContainer").html(),
          			'formId':$("#FORM_ID").val()
          	};
          	$.ajax({
          	      url:basePath+"workflowForm/saveFormProperties.action",
          	      type:"post",
          	      dataType: "json",
          	      data:paramData,
          	      success: function(data){
          				if(data!=null&&data.marked!=null&&data.marked==1){
          					art.dialog.alert("表单保存成功！");
          				}else if(data!=null&&data.marked!=null&&data.marked==0){
          					art.dialog.alert("表单已经被使用，不能修改。");
          				}else{
          					art.dialog.alert("表单保存失败！");
          				}
          	    	}
          	 }); 
          });
        	
         function checkTitleIsRepeat(){
			 var widgetNameList = new Array();	        	 
        	 //处理飞明细控件
			 var widget = $("#sourceContainer").find(".component").find(".control-label");
        	 for(var i=0; i<widget.length; i++){
        		 var wn = $(widget[i]).text();
        		 if(widgetNameList.length>0){
	        		 for(var j=0; j<widgetNameList.length; j++){
	        			 if(wn == widgetNameList[j]){
	        				 art.dialog.alert("表单控件名称【"+wn+"】不能重复");
	        				 return ;
	        			 }
	        		 }
        		 } 
	        	widgetNameList.push(wn);
        	 }
        	 
        	 var detailWidget = $("#sourceContainer").find(".detailcontrols");
        	 if(detailWidget){
        		 var details = $(detailWidget).find(".leipiplugins");
        		 if(details.length>0){
        			 for(var i=0; i<details.length; i++){
        				 var temp = $(details[i]).attr("title");
        				 if(widgetNameList.length>0){
        	        		 for(var j=0; j<widgetNameList.length; j++){
        	        			 if(temp == widgetNameList[j]){
        	        				 art.dialog.alert("表单控件名称【"+temp+"】不能重复");
        	        				 return ;
        	        			 }
        	        		 }
                		 } 
        	        	widgetNameList.push(temp);
        			 }
        		 }
        	 }
        	 
         }
         
        });
</script>
</body>
</html>