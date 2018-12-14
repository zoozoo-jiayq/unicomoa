    //绑定事件
    jQuery(document).ready(function($){
        //全选/全取消
        $("#selectAll").click(function(){
            if($(this).attr("checked")){
                var cs = document.getElementsByName("taskId");
                 
                for(var i=0; i<cs.length; i++){
                    if(!$(cs[i]).attr("disabled")){
                        cs[i].checked = "checked";
                    }
                }
            }else{
                var cs = document.getElementsByName("taskId");
                for(var i=0; i<cs.length; i++){
                    cs[i].checked = "";
                }
            }
        });

        //批量删除
        $("#deleteBatch").click(function(){
            domListBatchOption.doDeleteBatch();
        });
    });