(function() {
	"use strict"
	/*
	    树的实现
	 */
	var _basetree = angular.module("ui-basetree", [])
		/*
		* <div>
		  <ul id="sfs" basetree tree-data="data" select-node="node"  class="ztree"></ul>
		</div>
		*/
	_basetree.directive("basetree", function() {
		return {
			restrict: "A",
			scope: {
				treeData: "=",
				treeSet: "=",
				selectNode: "=",
				defaultSelect: "=",
				checkNode: "=",
				treeClick: "&"
			},
			link: function(scope, ele, attrs) {
				var defaultTreeSet = {
					isRadio: true, //默认单选
					enable: false // 默认不显示
				};
				var setting = {
					check: {
						enable: false,
						chkboxType: {
							"Y": "ps",
							"N": "ps"
						},
						chkStyle: "radio",
						radioType: "all"
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
						onClick: function(event, treeId, treeNode) {
							if(scope.treeClick){
								scope.$apply(function() {
									scope.selectNode = treeNode;
								});
								scope.treeClick();
							}else{
								scope.$apply(function() {
									scope.selectNode = treeNode;
								});
							}
						},
						onCheck: function(event, treeId, treeNode) {
							scope.$apply(function() {
								var treeObj=$.fn.zTree.getZTreeObj(attrs.id);
							    var nodes=treeObj.getCheckedNodes(true);
								scope.checkNode = nodes;
							});
						}
					}
				};
				angular.merge(defaultTreeSet, scope.treeSet);
				if (!defaultTreeSet.isRadio) {
					setting.check.chkStyle = "checkbox";
				}
				setting.check.enable = defaultTreeSet.enable;

				scope.$watch("treeData", function(data) {
					var zTreeObj = $.fn.zTree.init($("#" + attrs.id), setting, data);
					zTreeObj.expandAll(true);
					/*下面是默认选中*/
					if (null != scope.defaultSelect && undefined != scope.defaultSelect || "" != scope.defaultSelect ){
						var zTree_Menu = $.fn.zTree.getZTreeObj("" + attrs.id);
						var nodes = zTreeObj.transformToArray(zTreeObj.getNodes());
						// 修改时默认需要刷新左边的树		
						for(var i=0;i<nodes.length;i++){
							if (nodes[i].id==(scope.defaultSelect)){
								if(setting.check.chkStyle=="radio"){
									zTree_Menu.checkNode(nodes[i],true,true);
								}else{
									zTree_Menu.selectNode(nodes[i]);
								}
								zTreeObj.expandNode(nodes[i], true);
								break;              	
							}
						}
					}
				})
			}
		};
	});

	/*
	 	服务:获取选中的数据
	 	调用方式:treeService(treeId);
	*/
	_basetree.factory("treeService", function() {
		return function(treeId) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			return treeObj.getCheckedNodes(true);
		}
	});

	/*
		<input type="text" ng-model="node.name"  select-tree treecontainer="ssss"  ng-click="showtree=true"/>
			<input type="hidden" ng-model="node.id"  />
			<div class="menuContent" id="ssss" style='position: absolute;display:none'  >
				<ul id="sdf" basetree tree-data="data" select-node="node" class="ztree" ></ul>
			</div>
	*/
	_basetree.directive("selectTree", function() {
		return {
			link: function(scope, ele, attrs) {
				var flag = false;
				ele.attr("readonly", "readonly");
				ele.bind("click", function() {
					flag = !flag;
					if (flag) {
						$("#" + attrs["treecontainer"]).show();
					} else {
						$("#" + attrs["treecontainer"]).hide();
					}
				});
				var node = $("#" + attrs["treecontainer"]).find("ul").attr("select-node");
				scope.$watch(node, function(newv, oldv) {
					if (newv) {
						flag = !flag;
						$("#" + attrs["treecontainer"]).hide();
					}
				});
			}
		}
	});
})();