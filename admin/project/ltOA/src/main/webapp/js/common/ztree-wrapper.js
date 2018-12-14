 /*
			   * 原型：$.selectTree(url,inputId,divId,ulId,setting);
			   * url:数据源地址
			   * inputId:输入框的地址,显示数据的name属性，如果需要记录数据的ID属性，需要在页面中添加隐藏域:<input id="inputId_hidden" type="hidden" readonly  />
			   * divId:树的DIVid
			   * ulId:树的ULid
			   * setting:树的设置,默认设置是：
					setting =  {
						data: {
						simpleData: {
							enable: true
						}
						},
						callback: {
						onClick: selectNodes
						}
					}
*/
(function($){
	$.extend({

		selectTree:function(url,inputId,divId,ulId,setting){
			$.ajax({
			  type: "GET",
			  url: url,
			  dataType: "text",
			  success:function(data){
				if(!setting){
					setting =  {
						data: {
						simpleData: {
							enable: true
						}
						},
						callback: {
						onClick: selectNodes
						}
					}
				}
				var temp = eval('('+data+')');
		
				$.fn.zTree.init($("#"+ulId), setting, temp);
				$("#"+inputId).click(function(){
					showMenu();
				});
			  }
			});

			function showMenu() {
				var nameObj = $("#"+inputId);
				var nameOffset = $("#"+inputId).offset();
				$("#"+divId).css({left:nameOffset.left + "px", top:nameOffset.top + nameObj.outerHeight() + "px"}).slideDown("fast");
				$("#"+divId).css({border: "1px solid #e4e4e4"});
				$("body").bind("mousedown", onBodyDown);
			}
			function hideMenu() {
				$("#"+divId).fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
			function onBodyDown(event) {
				if (!(event.target.id == "menuBtn" || event.target.id == inputId || event.target.id == divId || $(event.target).parents("#"+divId).length>0)) {
					hideMenu();
				}
			}
			function selectNodes(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(ulId);
				var nodes = zTree.getSelectedNodes();
				if (nodes.length > 0) {
					var name = nodes[nodes.length - 1].name
					var id = nodes[nodes.length - 1].id;
					if(nodes[nodes.length-1].pId == 0 || !nodes[nodes.length-1].pId || id.indexOf("gid")>=0){
						return;
					}
					$("#"+inputId).val(name);
					if($("#"+inputId+"_hidden")){
						if(id.split("_").length == 2){
							id= id.split("_")[1];
						}
						$("#"+inputId+"_hidden").val(id);
					}
				}
				hideMenu();
			}				
		}
	});
})(jQuery)