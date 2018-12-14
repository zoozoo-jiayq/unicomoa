/**
 * input 设置默认值
 * 需要先引用jquery
 * 示例：
 * 姓名：<input width="120px" type="text"  fs="请输入姓名"/>
 */

$(function(){
    var formTips = $('input:text[fs],textarea[fs]'); //选择需要添加提示文字的表单
    formTips.css('color','#999');
    formTips.each(function(){
        var currentEle=$(this);
        //属性值
        var tipContent=currentEle.attr("fs");
        //设置表单值
        currentEle.val(tipContent);
        //获得焦点
        currentEle.focus(function(){   
            if(currentEle.val()==tipContent){
                currentEle.val('');
                currentEle.css('color','#333');
            }
        });
        //失去焦点
        currentEle.blur(function(){    
            if(currentEle.val()==''){
                currentEle.val(tipContent);
                currentEle.css('color','#999');
            }
        });
    });
});