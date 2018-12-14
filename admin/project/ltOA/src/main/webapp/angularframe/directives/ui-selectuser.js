/*
 * <select-txl show-dialog="flag" config="set" default-select-data="defaultData" select-data="data"></select-txl>
 * scope.set = {
				showTab:2,//1，角色树 2显示部门树,3都显示，默认3
				showUser:true,//true显示人员，false不显示人员，默认true
				isSingle:false,//true单选，false复选,默认true
			};
 */
(function(){
	"use strict"
angular.module("ui-selectuser", ['ngAnimate', 'ui.bootstrap'])
	.directive("selectTxl", ["$uibModal", function(uibModal) {
		return {
			template: "<script type='text/ng-template' id='selectuserdialog'>" +
				//"<div style='width:450px'>" +
				"<div class='modal-header'><h3 class='modal-title'>选择人员</h3></div>" +
				"<div style='margin:5px 10px'>" +
				"<form style='margin-bottom:5px'><input ng-model='search' placeholder='搜索人员' class='form-control'/></form>" +
				"<ul class='nav nav-tabs'><li role='presentation' ng-class='tab==2?active:1'><a href='#'' ng-click = 'groupTree(config);tab=2' ng-show='showTab>=2'>按部门选择</a></li>" +
				"<li role='presentation'  ng-class='tab==1?active:1'><a href='#'' ng-click='roleTree(config);tab=1' ng-show='showTab!=2'>按角色选择</a></li></ul>" +
				"<div><ul id='_sfsfscvdklkg' basetree tree-data='data' tree-set='set' class='ztree'></ul></div>" +
				"<div class='modal-footer'><button ng-click = '_sure(config)' class='btn btn-primary btn-large'>确定</button><button class='btn btn-large' ng-click = '_cancel()'>取消</button></div></div></script>",
			scope: {
				showDialog: "=",
				selectData: "=",
				config: "=",
				defaultSelectData: "="
			},
			link: function(scope, ele, attrs) {
				var defaultconfig = {
					showTab: 3,
					showUser: true,
					isSingle: true
				};
				var set = angular.merge(defaultconfig, scope.config);
				scope.showDialog = 0;
				scope.$watch("showDialog", function(newV) {
					if (newV) {
						var modalInstance = uibModal.open({
							animation: true, //是否需要动画
							templateUrl: "selectuserdialog",
							controller: "dialogCtrl", //弹出页面的控制器
							scope: scope,
							resolve: {
								txlset: function() {
									return {
										config: set,
										defaultSelectData: scope.defaultSelectData
									};
								}
							}
						});
						modalInstance.result.then(function(r) {
							scope.selectData = r;
						});
					}
				});
			}
		}
	}])
	.controller("dialogCtrl", ["$scope", "$uibModalInstance", "treeService", "txlService", "txlset", function(scope, moduleInstance, treeService, txlService, txlset) {
		scope.set = {
			isRadio: txlset.config.isSingle,
			enable: true
		}
		scope.tab = 2;
		scope.active = "active";
		scope.showTab = txlset.config.showTab;

		scope.groupTree = function() {
			txlService.groupTreeData(scope.search, txlset.config).success(function(data){
				scope.data = data;
			});
		}

		scope.roleTree = function() {
			txlService.roleTreeData(scope.search, txlset.config).success(function(data){
				scope.data = data;
			});
		}

		scope.$watch("search", function(newVal) {
			switch (scope.tab) {
				case 1:
					scope.roleTree();
					break;
				case 2:
					scope.groupTree();
					break;
			}
		});

		scope._sure = function() {
			var data = treeService("_sfsfscvdklkg");
			var selectList = new Array();
			if(txlset.config.showUser){
				for(var i=0; i<data.length; i++){
					if(data[i].id.indexOf("uid")==0){
						selectList.push(data[i]);
					}
				}
			}else{
				for(var i=0; i<data.length; i++){
					if(data[i].id.indexOf("gid")==0){
						selectList.push(data[i]);
					}
				}
			}
			moduleInstance.close(selectList);
			scope.showDialog = false;
		}

		scope._cancel = function() {
			moduleInstance.dismiss();
			scope.showDialog = false;
		}
	}]).factory("txlService", ["$http",function(http) {
		return {
			groupTreeData: function(search,config) {
				var showType = config.showUser?3:2;
				var type = search?5:1;
				return http({
					url:basePath+"user/selectUser.action?searchName="+search+"&showType="+showType+"&type="+type,
					method:"GET"
				});
				
			},
			roleTreeData: function(options) {
				return http({
					url:basePath+"user/selectUser.action?searchName=&showType=3&type=1",
					method:"GET"
				});
			}
		}
	}]);
})();