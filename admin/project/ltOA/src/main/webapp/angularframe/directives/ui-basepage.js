(function() {
	"use strict"
	angular.module("ui-basepage", []).directive("page", function() {
		return {
			template: "<div class='converse_page1' ng-show='iTotalDisplayRecords>0'>" +
				"<span class='date_num'>共<span>{{iTotalDisplayRecords}}</span>条数据</span>" +
				"<div class='page_num'>" +
				"<span class='lt' ng-click='prev();'><em></em></span>" +
				"<a ng-class='(p.fix?\"now_num\":\"now_ellipsis\")+\" \"+(p.pageIndex==iPageIndex?\"active\":\"\")'   ng-click='page(p.pageIndex)' ng-repeat='p in list'>{{p.pageIndex}}</a>" +
				"<span class='gt' ng-click='back();'><em></em></span>" +
				"</div>" +
				"</div>",
			restrict: "E",
			scope: {
				iTotalDisplayRecords: "=",
				pageSearch: "&",
				iDisplayStart: "=",
				iDisplayLength: "="
			},
			link: function(scope, ele, attrs) {
				scope.iDisplayLength = scope.iDisplayLength ? scope.iDisplayLength : 15; //默认每页15条
				scope.iDisplayStart = 0; //默认从第0条记录开始
				scope.iPageIndex = 1; //默认页码索引是1
				scope.showlist = new Array();
				scope.$watch("iTotalDisplayRecords", function(newVal) {
					scope.totalPages = getTotalPages(scope.iTotalDisplayRecords, scope.iDisplayLength);
					flushPage();
				});
				scope.$watch("iDisplayStart", function(newVal, oldVal) {
					scope.iPageIndex = Math.floor(scope.iDisplayStart / scope.iDisplayLength) + 1;
					scope.pageSearch();
					flushPage();
				});

				scope.prev = function() {
					if (scope.iPageIndex > 1) {
						reset(scope.iPageIndex - 1);
					}
				}
				scope.back = function() {
					if (scope.totalPages > scope.iPageIndex) {
						reset(scope.iPageIndex + 1);
					}
				}
				scope.page = function(index) {
					reset(index);
				}

				function reset(pageIndex) {
					if(pageIndex=="..."){
						return;
					}
					scope.iPageIndex = pageIndex;
					scope.iDisplayStart = (scope.iPageIndex - 1) * scope.iDisplayLength;
				}

				function flushPage() {
					if (scope.totalPages - scope.iPageIndex >= 6) {
						var _list = generatelist(scope.iPageIndex, scope.iPageIndex + 3, true);
						_list = _list.concat({
							pageIndex: "...",
							fix: false
						});
						_list = _list.concat(generatelist(scope.totalPages - 1, scope.totalPages, true));
						scope.list = _list;
					} else {
						if (scope.totalPages >= 6) {
							scope.list = generatelist(scope.totalPages - 5, scope.totalPages, true);
						} else {
							scope.list = generatelist(1, scope.totalPages, true);
						}
					}
				}

				function getTotalPages(total, displaylength) {
					if (!total || total <= 0) {
						return 0;
					}
					var count = Math.floor(total / displaylength);

					if (total % displaylength > 0) {
						count++;
					}
					return count;
				}

				function generatelist(start, end, fix) {
					var _list = new Array();
					for (var i = start; i <= end; i++) {
						_list.push({
							pageIndex: i,
							fix: fix
						});
					}
					return _list;
				}
			}
		}
	});
})();