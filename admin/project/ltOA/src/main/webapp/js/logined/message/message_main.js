$(document).ready(function() {	
		/** 初始化选择树 **/
		qytx.app.tree.user({
			id	:	"groupUserTree",
			click	:	callback,
			loadComplete: function() {
					var treeObj = $.fn.zTree.getZTreeObj("groupUserTree");
					var nodes = treeObj.transformToArray(treeObj.getNodes()[0]);
					for (var i=0;i<nodes.length;i++){
						var node = nodes[i];
						if(node.id.substr(0,4) == "uid_"){
							var data = {
									id	:	node.id.substr(4),
									name:   node.name
							}
							zTreeOnCheckResult(data);
							treeObj.selectNode(node);
							break;
						}else{
							continue;
						}
					}
			}
		});
});


function callback(nodes){
	if(nodes&&nodes.length>0){
		if(nodes[0].id.substr(0,4) == "uid_"){
			var data = {
					id	:	nodes[0].id.substr(4),
					name:   nodes[0].name
			}
			zTreeOnCheckResult(data);
		}else{
			return;
		}
	}
}
function zTreeOnCheckResult(data)
{
    var name  = encodeURI(data.name);
    $("#message_main").attr("src", basePath + "logined/message/list_user_message.jsp?userId="+data.id+"&userName="+name); 
}

function openIframe(url){
	$("#message_main").attr("src", url); 
}