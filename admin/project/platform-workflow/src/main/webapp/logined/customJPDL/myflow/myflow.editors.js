(function($) {
	var myflow = $.myflow;

	$.extend(true, myflow.editors, {
		inputEditor : function() {
			var _props, _k, _div, _src, _r;
			this.init = function(props, k, div, src, r) {
				_props = props;
				_k = k;
				_div = div;
				_src = src;
				_r = r;

				$('<input class="formText" maxlength="20"  />')
						.val(props[_k].value).change(function() {
									props[_k].value = $(this).val();
								}).appendTo('#' + _div);

				$('#' + _div).data('editor', this);
			}
			this.destroy = function() {
				$('#' + _div + ' input').each(function() {
							_props[_k].value = $(this).val();
						});
			}
		},
		inputEditor_readonly : function() {
			var _props, _k, _div, _src, _r;
			this.init = function(props, k, div, src, r) {
				_props = props;
				_k = k;
				_div = div;
				_src = src;
				_r = r;

				$('<input class="formText"  readonly="readonly"/>')
						.val(props[_k].value).change(function() {
									props[_k].value = $(this).val();
								}).appendTo('#' + _div);

				$('#' + _div).data('editor', this);
			}
			this.destroy = function() {
				$('#' + _div + ' input').each(function() {
							_props[_k].value = $(this).val();
						});
			}
		},

		textAreaEditor_readonly : function() {
			var _props, _k, _div, _src, _r;
			this.init = function(props, k, div, src, r) {
				_props = props;
				_k = k;
				_div = div;
				_src = src;
				_r = r;

				$('<textarea rows="3" cols="22"  class="formTextarea" readonly="readonly"></textarea>')
						.val(props[_k].value).change(function() {
									props[_k].value = $(this).val();
								}).appendTo('#' + _div);

				$('#' + _div).data('editor', this);
			}
			this.destroy = function() {
				$('#' + _div + ' input').each(function() {
							_props[_k].value = $(this).val();
						});
			}
		},

		textAreaEditor : function() {
			var _props, _k, _div, _src, _r;
			this.init = function(props, k, div, src, r) {
				_props = props;
				_k = k;
				_div = div;
				_src = src;
				_r = r;
				$('<textarea rows="3" cols="22"  onKeyUp="if(this.value.length > 30) this.value=this.value.substr(0,30)"  class="formTextarea"></textarea>')
						.val(props[_k].value).change(function() {
									props[_k].value = $(this).val();
								}).appendTo('#' + _div);

				$('#' + _div).data('editor', this);
			}
			this.destroy = function() {
				$('#' + _div + ' textarea').each(function() {
							_props[_k].value = $(this).val();
						});
			}
		},

		// 条件表达式编辑框
		exprTextAreaEditor : function() {
			var _props, _k, _div, _src, _r;
			this.init = function(props, k, div, src, r) {
				_props = props;
				_k = k;
				_div = div;
				_src = src;
				_r = r;

				var exprEditor = $('<textarea rows="3" cols="22" class="formTextarea" readonly></textarea>');

				exprEditor.val(props[_k].value).change(function() {

							props[_k].value = $(this).val();
						}).appendTo('#' + _div);

				exprEditor.click(function() {
					/*
					 * 这里目前暂时只能通过编程触发事件，来生成JSON数据，以后把myflow.js破解后再寻找更好的方法,
					 * 因为事件触发是异步行为，为了保证能获取到数据，需要延迟一定时间再获取。
					 */
					$("#hiddenpaths").focus();
					exprEditor.focus();
					setTimeout(function() {

						// 获取全部的数据
						var allData = eval('(' + $("#hiddenpaths").val() + ')');

						// 获取判断节点数据
						var decisonRect;
						var routers = "[";
						var pathRouter = new Array();
						var rects = allData.states;

						// 首先验证，如果有多个判断节点，则节点的名称不能重复
						for (var rect in rects) {
							if (rects[rect].type == "decision"
									&& rects[rect].props.text.value == _props.text.value) {
								if (decisonRect) {
									art.dialog.alert("<<判断>>节点名称不能重复,请修改!");
									return;
								} else {
									decisonRect = rect;
								}

							}
						}
						// 获取该节点的转出分支路径,要自动过滤掉重复的路径
						var paths = allData.paths;
						var pathIsExist = false;
						for (var path in paths) {
							if (paths[path].from == decisonRect) {
								var flag = true;
								for (var i = 0; i < pathRouter.length; i++) {
									if (pathRouter[i].from == paths[path].from
											&& pathRouter[i].to == paths[path].to) {
										flag = false;
									}
								}
								if (flag) {
									pathIsExist = true;
									pathRouter.push(paths[path]);
									routers += "{id:\"" + path + "\",name:\""
											+ paths[path].text.text + "\"},";
								}
							}
						}

						// 验证任务名称是否重复
						var nodeNames = {};
						var nodes = allData.states;
						for (var key in nodes) {
							var temp = nodes[key].text.text;
							if (!nodeNames[temp]) {
								nodeNames[temp] = 1;
							} else {
								art.dialog.alert("任务节点名称不能重复,请重新命名!");
								return null;
							}
						}

						// //验证PATH是否重名
						// var paths = allData.paths;
						// var pathNames = {};
						// for(var key in paths){
						// var pathname = paths[key].text.text;
						// if(!pathNames[pathname]){
						// pathNames[pathname] = 1;
						// }else{
						// art.dialog.alert("连线名不能重复,请删除连线后重连!");
						// return null;
						// }
						// }

						if (!pathIsExist) {
							art.dialog.alert("请先指定判断节点的转出路由!");
							return;
						}
						routers = routers.substring(0, routers.length - 1);
						routers += "]";

						art.dialog
								.open(
										$("#proName").val()
												+ "/workflow/jpdl!exprEditor.action?router="
												+ routers + "&formId="
												+ $("#formId").val(), {
											title : "编辑条件表达式",
											 width : 800,
											 height : 450,
											ok : function() {
												var ifr = this.iframe;
												var subWin = ifr.contentWindow;
												var expr = subWin["buildExpr"]();
												exprEditor.val(expr);
												exprEditor.change();
												exprEditor.focus();
											},
											cancel : true
										});
					}, 500);

				});

				$('#' + _div).data('editor', this);
			}
			this.destroy = function() {
				$('#' + _div + ' input').each(function() {
							_props[_k].value = $(this).val();
						});
			}
		},

		selectEditor : function(arg) {
			var _props, _k, _div, _src, _r;
			this.init = function(props, k, div, src, r) {
				_props = props;
				_k = k;
				_div = div;
				_src = src;
				_r = r;

				var sle = $('<select  style="width:100%;"/>')
						.val(props[_k].value).change(function() {
									props[_k].value = $(this).val();
								}).appendTo('#' + _div);

				if (typeof arg === 'string') {
					$.ajax({
								type : "GET",
								url : arg,
								success : function(data) {
									var opts = eval(data);
									if (opts && opts.length) {
										for (var idx = 0; idx < opts.length; idx++) {
											sle.append('<option value="'
													+ opts[idx].value + '">'
													+ opts[idx].name
													+ '</option>');
										}
										sle.val(_props[_k].value);
									}
								}
							});
				} else {
					for (var idx = 0; idx < arg.length; idx++) {
						sle.append('<option value="' + arg[idx].value + '">'
								+ arg[idx].name + '</option>');
					}
					sle.val(_props[_k].value);
				}

				$('#' + _div).data('editor', this);
			};
			this.destroy = function() {
				$('#' + _div + ' input').each(function() {
							_props[_k].value = $(this).val();
						});
			};
		}
	});

})(jQuery);