
(function() {
	angular.module("ui-datepicker", []).directive("datePicker", function() {
		return {
			require: "ngModel",
			scope: {
				config: "="
			},
			link: function(scope, element, attrs, ngModel) {
				var defaultConfig = {
					onpicking: onpicking,
					oncleared: oncleared
				};
				var dst = angular.merge(defaultConfig, scope.config);
				element.val(ngModel.$viewValue);

				function onpicking(dp) {
					var date = dp.cal.getNewDateStr();
					scope.$apply(function() {
						ngModel.$setViewValue(date);
					});
				}

				function oncleared() {
					scope.$apply(function() {
						ngModel.$setViewValue("");
					});
				}
				element.bind('click', function() {
					WdatePicker(dst);
				});
			}
		}
	});
})();