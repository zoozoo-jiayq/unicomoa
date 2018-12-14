(function($) {
	var myflow = $.myflow;

	$.extend(true, myflow.config.rect, {
				attr : {
					r : 8,
					fill : '#F6F7FF',
					stroke : '#03689A',
					"stroke-width" : 2
				}
			});

	$.extend(true, myflow.config.props.props, {
		name : {
			name : 'name',
			label : '流程名称',
			value : $("#processAttributeName").val(),
			editor : function() {
				return new myflow.editors.inputEditor_readonly();
			}
		}
			// desc : {name:'', label:'描述',
			// value:$("#processAttributeDirections").val(),
			// editor:function(){return new
			// myflow.editors.textAreaEditor_readonly();}}
		});

	$.extend(true, myflow.config.tools.states, {
		start : {
			showType : 'image',
			type : 'start',
			name : {
				text : '<<start>>'
			},
			text : {
				text : '发起流程'
			},
			img : {
				src : $("#proName").val()
						+ '/logined/customJPDL/myflow/img/48/start_event_empty.png',
				width : 48,
				height : 48
			},
			// attr : {width:50 ,heigth:50 },
			props : {
				text : {
					name : 'text',
					label : '名称',
					value : '发起流程',
					editor : function() {
						return new myflow.editors.inputEditor_readonly();
					}
				},
				desc : {
					name : 'desc',
					label : '描述',
					value : "",
					editor : function() {
						return new myflow.editors.textAreaEditor();
					}
				}
			}
		},
		end : {
			showType : 'image',
			type : 'end',
			name : {
				text : '<<end>>'
			},
			text : {
				text : '结束'
			},
			img : {
				src : $("#proName").val()
						+ '/logined/customJPDL/myflow/img/48/end_event_terminate.png',
				width : 48,
				height : 48
			},
			attr : {
				width : 50,
				heigth : 50
			},
			props : {
				text : {
					name : 'text',
					label : '显示',
					value : '结束',
					editor : function() {
						return new myflow.editors.inputEditor_readonly();
					}
				}

			}
		},

		fork : {
			showType : 'image',
			type : 'fork',
			name : {
				text : '<<fork>>'
			},
			text : {
				text : '分支'
			},
			img : {
				src : $("#proName").val()
						+ '/logined/customJPDL/myflow/img/48/gateway_parallel.png',
				width : 48,
				height : 48
			},
			attr : {
				width : 50,
				heigth : 50
			},
			props : {
				text : {
					name : 'text',
					label : '名称',
					value : '',
					editor : function() {
						return new myflow.editors.textEditor();
					},
					value : '分支'
				},
				desc : {
					name : 'desc',
					label : '描述',
					value : '',
					editor : function() {
						return new myflow.editors.textAreaEditor();
					}
				}
			}
		},
		join : {
			showType : 'image',
			type : 'join',
			name : {
				text : '<<join>>'
			},
			text : {
				text : '合并'
			},
			img : {
				src : $("#proName").val()
						+ '/logined/customJPDL/myflow/img/48/gateway_parallel.png',
				width : 48,
				height : 48
			},
			attr : {
				width : 50,
				heigth : 50
			},
			props : {
				text : {
					name : 'text',
					label : '名称',
					value : '',
					editor : function() {
						return new myflow.editors.textEditor();
					},
					value : '合并'
				},
				desc : {
					name : 'desc',
					label : '描述',
					value : '',
					editor : function() {
						return new myflow.editors.textAreaEditor();
					}
				}
			}
		},
		task : {
			showType : 'text',
			type : 'task',
			name : {
				text : '<<task>>'
			},
			text : {
				text : '任务'
			},
			img : {
				src : $("#proName").val()
						+ '/logined/customJPDL/myflow/img/48/task_empty.png',
				width : 48,
				height : 48
			},
			props : {
				text : {
					name : 'text',
					label : '名称',
					value : '',
					editor : function() {
						return new myflow.editors.textEditor();
					},
					value : '任务'
				},

				desc : {
					name : 'desc',
					label : '描述',
					value : '',
					editor : function() {
						return new myflow.editors.textAreaEditor();
						// return new myflow.editors.inputEditor();
					}
				}
			}
		},
		mutilSign : {
			showType : 'text',
			type : 'mutilSign',
			name : {
				text : '<<mutilSign>>'
			},
			text : {
				text : '会签'
			},
			img : {
				src : $("#proName").val()
						+ '/logined/customJPDL/myflow/img/48/task_empty.png',
				width : 48,
				height : 48
			},
			props : {
				text : {
					name : 'text',
					label : '名称',
					value : '会签',
					editor : function() {
						return new myflow.editors.textEditor();
					}
				},

				desc : {
					name : 'desc',
					label : '描述',
					value : '',
					editor : function() {
						return new myflow.editors.textAreaEditor();
					}
				}
			}
		},
		decision : {
			showType : 'image',
			type : 'decision',
			name : {
				text : '<<decision>>'
			},
			text : {
				text : '判断'
			},
			img : {
				src : $("#proName").val()
						+ '/logined/customJPDL/myflow/img/48/gateway_exclusive.png',
				width : 48,
				height : 48
			},
			props : {
				text : {
					name : 'text',
					label : '名称',
					value : '判断',
					editor : function() {
						return new myflow.editors.textEditor();
					}
				},
				expr : {
					name : 'expr',
					label : '判断表达式',
					value : '',
					editor : function() {
						return new myflow.editors.exprTextAreaEditor();
					}
				},
				desc : {
					name : 'desc',
					label : '描述',
					value : '',
					editor : function() {
						return new myflow.editors.textAreaEditor();
					}
				}
			}
		}
	});
})(jQuery);