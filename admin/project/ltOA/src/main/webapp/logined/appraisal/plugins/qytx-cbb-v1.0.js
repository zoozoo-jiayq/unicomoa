(function(win){
	var _util = {};
	var _app = {};
	var _frame = {};
	var _qytx = {
		frame:_frame,
		util:_util,
		app:_app
	};
	win.qytx = _qytx;

	//----------框架信息的实现放这里 begin----------------//
	_frame.version = "v1.0";
	//----------框架信息的实现放这里 end----------------//

	//----------应用的实现放这里 begin--------------------//
	/*
	* 弹出实现
	*/
	_app.dialog = {
		alert:function(info,callback){
			if(callback){
				art.dialog.alert(info,callback);
			}else{
				art.dialog.alert(info);
			}
		},
		/**
		 * through true/false 默认false
		 */
		success:function(info,callback,through){
			var _cb = callback;
			if(!callback){
				_cb = function(){
					return true;
				}
			}
			if(through){
				art.dialog.through({
					title : '消息',
					content : info,
					icon : 'succeed',
					height : 110,
					width : 340,
					ok:_cb
				});
			}else{
				art.dialog({
					title : '消息',
					content : info,
					icon : 'succeed',
					height : 110,
					width : 340,
					ok:_cb
				});
			}
		},
		confirm:function(info,callback){
			art.dialog.confirm(info,callback);
		},
		/*
		* params={
				url:[页面地址][必填]
				title:[弹出框标题][必填]
				id:[弹出框的ID][非必填]
				size:[必填][S/M/L:S:400*200;M:600*300;L:800*450]
				ok:[确定按钮][回调函数][非必填][默认返回true]
				cancel:[取消按钮][回掉函数][非必填]
				close:[关闭][回掉函数][非必填]
				customButton:[自定义按钮列表][非必填，默认是两个按钮”确定“”取消“]	
				through: true/false [非必填] 默认false	
		}
		*/
		openUrl:function(params){
			if(!params.url){
				alert("URL地址不能为空!");
				return;
			}
			if(!params.title){
				alert("标题不能为空!");
				return;
			}
			if(!params.size){
				alert("尺寸不能为空!");
				return;
			}
			var _config = {};
			if(params.size == 'S'){
				_config.width = 400;
				_config.height = 200;
			}
			if(params.size == 'M'){
				_config.width = 600;
				_config.height = 300;
			}
			if(params.size == 'L'){
				_config.width = 800;
				_config.height = 450;
			}
			if(params.id){
				_config.id = params.id
			}

			_config.lock = true;
			_config.opacity = 0.08;
			_config.title = params.title;
			
			if(params.customButton){
				_config.button = params.customButton;
			}else{
				if(params.ok){
					_config.ok = params.ok;
				}
				if(params.cancel){
					_config.cancel = params.cancel;
				}
				if(params.close){
					_config.button = [{
						name:"关闭",
						callback:params.close
					}];
				}
			}
			if(params.through){
				art.dialog.open.through(params.url,_config);
			}else{
				art.dialog.open(params.url,_config);
			}
		},
		/*
		*params={
			content:[HTML内容][必填]
			title:[弹出框标题][必填]
			size:[必填][S/M/L:S:400*200;M:600*300;L:800*450]
			id:[弹出框的ID][非必填]
			ok:[确认按钮][回调函数][非必填][默认返回true]
			cancel:[取消按钮][非必填]
			colose:[关闭按钮][非必填]
			customButton:[按钮列表][非必填，默认两个按钮“确定”“取消”]
			through: true/false [非必填] 默认false	
		}
		*/
		openHtmlContent:function(params){
			//实现过程
			if(!params.content){
				alert("内容不能为空!");
				return;
			}
			if(!params.title){
				alert("标题不能为空!");
				return;
			}
			if(!params.size){
				alert("尺寸不能为空!");
				return ;
			}
			var _config = {};
			if(params.size == 'S'){
				_config.width = 400;
				_config.height = 200;
			}
			if(params.size == 'M'){
				_config.width = 600;
				_config.height = 300;
			}
			if(params.size == 'L'){
				_config.width = 800;
				_config.height = 450;
			}
			if(params.id){
				_config.id = params.id;
			}
			_config.lock = true;
			_config.opacity = 0.08;
			_config.title = params.title;
			_config.content = params.content;
			if(params.customButton){
				_config.button = params.customButton;
			}else{
				if(params.ok){
					_config.ok = params.ok;
				}
				if(params.cancel){
					_config.cancel = params.cancel;
				}
				if(params.close){
					_config.button = [{
						name:"关闭",
						callback:params.close
					}];
				}
			}
			if(params.through){
				art.dialog.through(_config);
			}else{
				art.dialog(_config);
			}
		},
		/*
		 * 短暂提示(同时只允许一个tips)
     	 * content 内容、
     	 * callback 回调函数 
     	 * time 显示时间(单位秒, 默认1.5)
		 */
		tips:function(content,callback,time){
			if(!time){
				time=1.5;
			}
			async.parallel([
			                function(callback){
			                	try{
			                		window.parent.art.dialog.through({
			                			id: 'Tips',
			                			title: false,
			                			cancel: false,
			                			fixed: true,
			                			lock: false
			                		})
			                		.content('<div style="padding: 0 1em;">' + content + '</div>')
			                		.time(time || 1.5);
			                	}catch(e){
			                		art.dialog.through({
			                			id: 'Tips',
			                			title: false,
			                			cancel: false,
			                			fixed: true,
			                			lock: false
			                		})
			                		.content('<div style="padding: 0 1em;">' + content + '</div>')
			                		.time(time || 1.5);
			                	}

			                },
			                function(){
			                	setTimeout(function(){
			                		if(callback){
				                		callback();
				                	}
			                	},50);
			                }
			                ],function(err,result){});
		}
	};
	
	/*
	 * form表单验证
	 * config={
			id:""							[必填，form的id]
			type:"add/edit"					[非必填，新增或修改，默认add （暂未启用）]
			defaultParam:{}					[非必填，表单提交时附带的参数，是对象格式]
			url:""							[必填，提交的方法]
			validFn:[param,param...]		[非必填，单元素异步验证方法（例：验证手机号码是否重复）]
					param={
						url:""				[必填，验证的后台方法路径]
						param:{}			[非必填，请求的参数]
						type:"post/get"		[非必填，请求的类型，默认post]
						dataType:"json/text"[非必填，返回的数据类型，默认text]
						callback:function(data){return false;}[必填，回调函数，必须返回true或者false]
					}
			success:function(data){}		[必填，回调函数 data是返回的数据]
		}
	 */
	_app.SimpleForm = function(config){
		this.conf = config;
		var that = this;
		/**
		 * 设置同步方法验证
		 */
		this._synchAjax = function(config){
			var r = false;
			var conf = config;
			var data = {};
			if(typeof(conf.param)=="function"){
				data = conf.param();
			}else if(conf.param!=null){
				data =  conf.param;
			}
			$.ajax({
				type:conf.post||"post",
				url:conf.url,
				cache: false,
				async:false,
				dataType:conf.dataType||"text",
				data:data,
				success:function(result){
					r = conf.callback.call(conf.callback,result);
					return r;
				}
			});
			return r;
		};
		
		/**
		 * 循环读取配置中的异步验证元素，并加以验证
		 */
		this.checkColumn = function(){
			var validFn = that.conf.validFn;
			if(validFn && validFn.length > 0){
				for(var i = 0;i< validFn.length;i++){
					if(validFn[i].url){
						if(that._synchAjax({
							url:validFn[i].url,
							param:validFn[i].param||{},
							type:validFn[i].type||"post",
							dataType:validFn[i].dataType||"text",
							callback:validFn[i].callback
						})){
							continue;
						}else{
							return false;
							break;
						}
					}
				}
			}
			return true;
		}
		
		/**
		 * 正则验证表单元素，并将错误信息展示出来
		 */
		this.checkReg = function(inputObj, reg, msg) {
	        inputObj.value = inputObj.value.trim();

	        if (inputObj.value == '') {
	            return false;
	        } else {
	            if (!reg.test(inputObj.value)) {
					qytx.app.valid.showObjError($(inputObj),msg);
					return false;
				}
	        }
	        return true;
	    }
		
		/**
		 * false 表示表单项有错误。true表示表单项正确
		 */
		this._baseValidForm = function(){
			var $inputs = jQuery("#"+that.conf.id).find("input[type='text']");
			
			for(var i = 0;i<$inputs.length;i++){    //绑定提示事件
				var $valid = jQuery($inputs[i]).attr("valid");
				var $errmsg = jQuery($inputs[i]).attr("errmsg");
				if(($valid && !$errmsg) || (!$valid && $errmsg)){  //验证标准格式是否正确
					return false;
				}else if($valid && $errmsg && $valid.split("|").length != $errmsg.split("|").length){
					return false;
				}
			}
			
			var $textAreas = jQuery("#"+that.conf.id).find("textarea");  //文本域添加提示
			for(var i = 0 ; i < $textAreas.length;i++){
				if(jQuery($textAreas[i]).attr("placeholder")){
					funPlaceholder($textAreas[i]);
				}
				var $valid = jQuery($textAreas[i]).attr("valid"); //验证标准格式是否正确
				var $errmsg = jQuery($textAreas[i]).attr("errmsg");
				if($valid!=null && $valid.split("|").length != $errmsg.split("|").length){ 
					return false;
				}
			}
			return true;
		};
		/**
		 * 验证基本表单
		 */
		if(!that._baseValidForm()){
			alert("部分表单项设置有错误，请重新设置！");
			return;
		}
		/**
		 * 验证表单项
		 */
		this._validFormItem=function($item){
			var $valid = $item.attr("valid");
			var $errmsg = $item.attr("errmsg"); 
			if($valid!=null && $valid!=""){
				var valids = $valid.split("|");
				var errmsgs = $errmsg.split("|");
				for(var i = 0 ; i < valids.length ; i++){
					var errmsg = errmsgs[i];
					switch (valids[i]) {
					case "required":
						if(!$item.val() || !$.trim(!$item.val())){
							qytx.app.valid.showObjError($item, errmsg);
							return false;
						}
						break;
					case "username":
						if(!that.checkReg($item[0],/^^[0-9a-zA-Z_]+$/,errmsg)){
							return false;
						}
						break;
					case  "telphone":
						if(!that.checkReg($item[0],/^\d{7,12}$/,"telphone.telphone_length_out_of_range") || !that.checkReg($item[0],/(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)/,"telphone.telphone_length_out_of_range")){
							return false;
						}
						break;
					default:
						break;
					}
				}
			}
			return true;
		};
		/**
		 * 验证整个表单
		 */
		this._validForm = function(){
			var $inputs = jQuery("#"+that.conf.id).find("input[type='text']");
			for(var i = 0;i<$inputs.length;i++){
				if(!that._validFormItem(jQuery($inputs[i]))){
					return false;
				}
			}
			return true;
		};
		/**
		 * 得到表单参数
		 */
		this.QueryStringTojson = function(e) {
			var rtn = {};
			var qs = e;
			if (qs) {
				var ps = qs.split('&');
				for ( var i = 0; i < ps.length; i++) {
					var tmp = ps[i].split('=');
					rtn[tmp[0]] = tmp[1];
				}
			}
			return rtn;
		},
		/**
		 * 表单默认参数
		 */
		this._formDefaultPram = function(){
			if(that.conf.defaultParam && typeof(that.conf.defaultParam) =="function"){
				return that.conf.defaultParam();
			}else{
				return that.conf.defaultParam||{};
			}
		};
		/**
		 * 表单参数序列化
		 */
		this._getParamData=function(){
			var confParam = that.QueryStringTojson(jQuery("#"+that.conf.id).serialize());
			jQuery.extend(confParam,that._formDefaultPram());//
			return confParam;
		};
		/**
		 * 提交表单
		 */
		this.subForm = function(){
			if(that._validForm() && that.checkColumn()){
				var config = {};
				config.url = that.conf.url;
				config.data = that._getParamData();
				config.async = true;
				config.dataType="text";
				config.success = that.conf.success;
				qytx.app.ajax(config);
				
			}
		};
		
		that.subForm();
	};
	/*
		功能：分页参数调用
		param={

			   id:xxx,//分页绑定的table 的ID  必填项
 
			   url:分页提交的url           必填项
			  
			  iDisplayLength:15          分页显示条数  非必填项 默认15
			  
			  valuesFn ：[{"aTargets":[0],"fnRender":function(oObj){
				
				}}]               //重写值的方法     非必填项
				
			selectParam：    传递参数Map类型  非必填项  传递参数的id    
			
			bInfo: true/false 页脚信息，默认true
			sPage: true/false 是否支持跨页选中 默认true
		前台JSP页面页面实例     id和重写函数   （必须有一个，id绑定的是json返回的返回的key值）
		class 列表包含的class样式，width宽度	
	   <table cellpadding="0" cellspacing="0"  class="pretty dataTable" id="table1">
          <thead>
          	<tr>
	        	<th id="title" class="data_l longTxt">标题</th>
	       		<th id="tnum" style="width:70px;">问题数</th>
	        	<th id="cname" style="width:80px;">创建人</th>
	        	<th id="publishDate" style="width:130px;">发布时间</th>
	       	 	<th id="endDate" style="width:130px;">答题截止时间</th>
	       	 	<th id="answerPeople" style="width:70px;" class="data_r">答题人数</th>
	       	 	<th id="state" style="width:70px;">状态</th>
	        	<th style="width:115px;" class="oper right_bdr0">操作</th>
	      	</tr>	
          </thead>
      </table>
		
		}

	*/
	_app.grid = function(conf){
		conf.selectParam = conf.selectParam||{};
		this.conf = conf;
		var that = this;
		this.header = function(){  //获得头部信息 
			var array = new Array();
			jQuery("#"+that.conf.id).find("th").each(function(i){
				var headerParam = {};
				headerParam.mDataProp = jQuery(this).attr("id")||null;
				if(jQuery(this).attr("class")){
					headerParam.sClass = jQuery(this).attr("class");
				}
        		array.push(headerParam);
			});
			return array;
		};
		that.header = that.header();
		this.query = function(){ //布局分页
			this._layGrid();
		};
		this.queryParam = function(){
			var config = {};
			jQuery("div.searchArea input[type='text']").each(function(){
				var $this = jQuery(this);
				config[$this.attr("name")] = $this.val();
			});
			jQuery("div.searchArea select").each(function(){
				var $this = jQuery(this);
				config[$this.attr("name")] = $this.val();
			});
			jQuery.extend(that.conf.selectParam,config);//
			return that.conf.selectParam;
		};
		var bInfo = that.conf.bInfo==null?true:that.conf.bInfo;
		var sPage = that.conf.sPage==null?true:that.conf.sPage;
		this._layGrid = function(){
			jQuery("#"+that.conf.id).dataTable({
				"bDestroy" : true,
				"bProcessing" : true,
				'bServerSide' : true,
				'fnServerParams' : function(aoData) {
					var queryParam = that.queryParam();
					$.each(queryParam,function(key,val){
						aoData.push({"name":key,"value":val});
					});
				},
				"sAjaxSource" : that.conf.url,
				"sServerMethod" : "POST",
				"sPaginationType" : "full_numbers",
				"bPaginate" : bInfo, // 翻页功能
				"bLengthChange" : false, // 改变每页显示数据数量
				"bFilter" : false, // 过滤功能
				"bSort" : false, // 排序功能
				//"aaSorting": [['4','desc']],
				"bInfo" : bInfo,// 页脚信息
				"bAutoWidth" : false,// 自动宽度
				"iDisplayLength" :that.conf.iDisplayLength||15, // 每页显示多少行
				"fnDrawCallback" : function(oSettings) {
					 $('.pretty tbody  tr td[class="longTxt"]').each(function() {
		 				this.setAttribute('title', $(this).text());
		 			});
					 if(sPage){
						 try{
							 _getChecked();
						 }catch(e){
							 
						 }
					 }
				},
				"aoColumns":that.header||that.header(),
				"oLanguage" : {
					"sUrl" : basePath + "plugins/datatable/cn.txt" // 中文包
				},
				"aoColumnDefs":that.conf.valuesFn||[]
			});
		};
		that.query();
		return this;
	};
	/*
	* 树的实现
	*/
	_app.tree = {
		base:function(params){
			return tree_base(params);
		},
		user:function(params){
			return tree_user(params);
		},
		userCheckOrRadio:function(params){
			return userCheckOrRadio(params);
		},
		alertUserTree:function(params){
			return alertUserTree(params);
		}
	};
	
	/*
		功能：ztree插件封装
		param={
	
			   id:xxx,//UL标签的ID
	
			   type:check/radio,//check复选框，radio单选框，非必填，默认是没有单选框或者复选框
	
			   data:xxx,//必填，数据
	
			   defaultSelectId:xxx,//已选中节点id,非必填
	
			   async:true/false;非必选，默认值为false,不启用异步；true启用异步加载
	
			   url:"",//非必填，如果async值为true,则该值为必填，异步请求的地址
	
			   autoParam:[],//非必填，如果async为true,表示url请求的参数
	
			   click:function(nodes,node){
					//nodes数组里面放的是node对象 node指点击的节点对象
			   }//节点被选中事件
			   loadComplete:function()//初始完毕后执行的操作
	
		}
	
	*/
	function tree_base(param){
		if(param!=null){
//			param.defaultSelectId = null;
			if(!param.id){
				alert('id不能为空！');
				return false;
			}
	
			if(!param.data){
				alert('data不能为空!');
				return false;
			}
	
			//定义树类型
			var _check={};
				_check.enable = false;//默认是没有单选框或者复选框
			//定义回调函数
			var _callback = {};
				_callback.onClick = function(){
					return true;
				}
			// 实现回调函数
			var _zTreeOnCheck = function(event, treeId, treeNode){
				var treeObj = $.fn.zTree.getZTreeObj(param.id);
				var nodes = treeObj.getCheckedNodes(true); //获取选中的节点
				var nodesArr = [];
				for ( var i = 0; i < nodes.length; i++) {
					var node = nodes[i];
					nodesArr.push(node);
				}
				if(param.click){
					param.click(nodesArr,treeNode);
				}
			}
	
			var _zTreeOnClick = function(event, treeId, treeNode){
				var nodesArr = [];
				nodesArr.push(treeNode);
				param.click(nodesArr,treeNode);
			}
	

			// 根据type设定ztree初始化设定
			if(param.type){
				if(param.type=='check'){//复选框
					_check.enable = true;
					_check.chkboxType = {
						"Y" : "ps",
						"N" : "ps"
					},
					_check.chkStyle = 'checkbox';
	
					if(param.click){
						_callback.onCheck = _zTreeOnCheck;
					}
				}else if(param.type == 'radio'){//单选框
					_check.enable = true;
					_check.chkStyle = 'radio';
					_check.radioType = 'all';
	
					if(param.click){
						_callback.onCheck = _zTreeOnCheck;
					}
				}else{
					if(param.click){
						_callback.onClick = _zTreeOnClick;
					}
				}
			}else {
				if(param.click){
					_callback.onClick = _zTreeOnClick;
				}
			}
	
			// 初始化ztree异步加载配置
			var _async = {};
				_async.enable = false;
			if(param.async){
				_async.enable = true;
				_async.url = param.url == null?'':param.url;
				_async.autoParam = param.autoParam == null?[]:param.autoParam;
			}
			// setting
			var _setting = {
				check : _check,
				view: {
					showIcon: true
				},
				async: _async,
				data : {
					simpleData : {
						enable : true
					},
					showTitle : true, //是否显示节点title信息提示 默认为true
					key : {
						title : "title" //设置title提示信息对应的属性名称 也就是节点相关的某个属性
					}
				},
				callback : _callback
			};
			//生成树
			var _treeObj = $.fn.zTree.init($("#"+param.id), _setting, param.data);
			// 获得所有节点并默认展开第一个子节点
			var _rootNodesArr = _treeObj.getNodes();	      
			for (var j=0;j<_rootNodesArr.length;j++){
				var _nodes = _treeObj.transformToArray(_rootNodesArr[j]);
				var _isExpand =false;
				for(var i=0;i<_nodes.length;i++){
					
					if (!_nodes[i].isParent){                    	
						var _nodesArr = _treeObj.getNodesByParam("id", _nodes[i].id, null);
						
						var _tempNode = _nodesArr[0];   
						while(_tempNode.getParentNode() != null){
							_treeObj.expandNode(_tempNode.getParentNode(),true,false,false);
							_tempNode=_tempNode.getParentNode();
						}   
						_isExpand = true;
						break;
					}
				}
				if (_isExpand){
					break;
				}
			}
			if(param.loadComplete){
				param.loadComplete();
			}
			
			//获得传递过来的已选中的节点的id，并将这些节点选中
			if(param.defaultSelectId){
				var ids = param.defaultSelectId.split(",");
				for ( var i = 0; i < ids.length; i++) {
					var node = _treeObj.getNodeByParam("id", ids[i], null);
					
					if (null != node) {
						_treeObj.checkNode(node, true, true);
						_treeObj.selectNode(node);
					}
				}
			}
	
	
	
		}else{
			alert('参数不能为空！');
			return false;
		}
	}
	
		/*
		功能：ztree 人员部门树插件封装
		param={

			   id:xxx,//UL标签的ID 必填
			   
			   click:function(nodes,node){
					//nodes数组里面放的是node对象
			   }//节点被选中事件
			   loadComplete:function()//初始完毕后执行的操作

		}

	*/
	
	function tree_user(param){
		if(param!=null){
			//param.type = '';
			param.async = false;


			// var url = 'http://10.200.10.165:8083/qytx-oa/user/selectTreeUser.action?type=1&showType=3';
			var url = basePath+'/user/selectTreeUser.action?type=1&showType=3';
			$.ajax({
						url : url,
						type : 'post',
						dataType : 'json',
						success: function(data){
							param.data = data;
							qytx.app.tree.base(param);
						}
					});

		}else{
			alert('参数不能为空！');
			return false;
		}
	}
	
	/*
		功能：ztree 人员弹出选择树
		param={

			   id:xxx,//UL标签的ID 必填
			   
			   type:check/radio,//check复选框，radio单选框，非必填，默认是复选框
			   
			   defaultSelectId:xxx,//已选中节点id
			   
			   showType:xxx	// 非必填 1 部门 2 角色 3 人员(默认)

			   dataParam:{} //参数
			   
			   url:""							[非必填，初始化请求的接口地址]
			   
			   asyncUrl:"",						[非必填，asyncUrl 有值则代表异步请求]
			
			   click:function(nodes,node){
					//nodes数组里面放的是node对象 当前点击的节点对象
			   }//节点被选中事件
			   loadComplete:function()//初始完毕后执行的操作

		}

	*/
	function userCheckOrRadio(param){
		if(param!=null){
			param.async = false;

			// 获得选中类型
			if(!param.type){
				param.type = 'check';
			}
			var showType = param.showType||3;// 非必填 1 部门 2 角色 3 人员(默认)

			// 获得已选中的节点id
			var _defaultSelectId = '';
			if(param.defaultSelectId){
				var ids = param.defaultSelectId.split(",");
				for ( var i = 0; i < ids.length; i++) {
					if(showType == 1){//部门 
						_defaultSelectId+='gid_'+ids[i]+',';
					}else if(showType == 2){//角色
						_defaultSelectId+='rid_'+ids[i]+',';
					}else if(showType == 3){// 人员
						_defaultSelectId+='uid_'+ids[i]+',';
					}else{
						_defaultSelectId = param.defaultSelectId;
						break;
					}
				}
			}

			param.defaultSelectId = _defaultSelectId;


			//var url = 'http://10.200.10.165:8083/qytx-oa/user/selectTreeUser.action?type=1&showType=3';
			var url = basePath+'/user/selectUser.action';
			if(param.url){
				url = param.url;
			}
			if(param.asyncUrl){
				param.async=true;
				param.autoParam = ["id=nodeId"];
				param.url = param.asyncUrl;
			}
			$.ajax({
						url : url,
						type : 'post',
						data :	param.dataParam||{},
						dataType : 'json',
						success: function(data){
							param.data = data;
							qytx.app.tree.base(param);
						}
					});

		}else{
			alert('参数不能为空！');
			return false;
		}
	}
	
	/*
		功能：ztree 人员弹出选择树
		param={
			showType:"3"					[非必填，查找类型  1 部门 2 角色 3 人员 	默认3（人员）]
			type:"check/radio"				[非必填，check代表多选，radio代表单选   默认多选]
			selTab:"1_1_1"                  [非必填，选择树的标签显示选项，默认全部显示 部门_角色_群组]
			defaultSelectIds:""				[非必填，已选中的ids]
			async:true/false				[非必填，是否异步请求 默认非异步请求]
			callback:function(data){}		[必填，回调函数 data的数据类型是数组对象 例：'[{"id":"1","name":"管理员"}...]']
		}
	
	 */
	function alertUserTree(param){
		var showType = param.showType||3;
		var defaultSelectId=param.defaultSelectIds||"";
		var checkType = "check";
		if(param.type && param.type == "radio"){
			var checkType = "radio";
		}
		var selTab = param.selTab||"1_1_1";
		
		var url = basePath + "/flat/plugins/org/org2.jsp?showType=" + showType + "&defaultSelectId=" + defaultSelectId + "&checkType=" 
							+ checkType+"&selTab="+selTab;
		
		if(param.async){
			url+="&async=async";
		}
		
		var title = "选择人员";
		if (showType == 1) {
			title = "选择部门";
		} else if (showType == 2) {
			title = "选择角色";
		}
		art.dialog.open(url, {
			title : title,
			width : 360,
			height : 400,
			lock : true,
		    opacity:0.08,
			button : [{
						name : '确定',
						focus:true,
						callback : function() {
							var result = art.dialog.data("result");
							param.callback(result);
							return true;
						}
					}, {
						name : '取消',
						callback : function() {
							return true;
						}
					}]
		}, false);
	}
	/*
	* 上传的实现
	*/
	_app.upload = {};

	/*
	*下拉树的实现
	* params = {
			id:[input控件的ID][必填],
			url:[数据源的URL地址][必填],
			width:[下拉树的宽度][非必填，默认值是100px]
			height:[下拉树的高度][非必填，默认值是300px]
			callback:[非必填][选中节点的回调函数，回调函数原型：function(node);node选中节点]
	}
	* 
	*/
	_app.selecttree = function(params){
		if(!params.id){
			alert("INPUT控件的ID不能为空!");
			return ;
		}
		if(!params.url){
			alert("url属性不能为空!");
			return ;
		}
		var _width = 100;
		var _height = 300;
		if(params.width){
			_width = params.width;
		}
		if(params.height){
			_height = params.height;
		}

		//获取数据
		$.ajax({
		  type: "GET",
		  url: params.url,
		  dataType: "text",
		  success:function(data){

		  	//创建存放树的容器
			var tree_divId = div_+Math.random();
			var tree_ulId = ul_+Math.random();
			var tree_div = $("<div>",{
				id:tree_divId,
				"class":"menuContent",
				style:"display:none; position: absolute;"
			});

			var tree_ul = $("<ul>",{
				id:tree_ulId,
				"class":"ztree",
				style:"margin-top:0; width:"+_width+"px; height:"+_height+"px;"
			});
			$(tree_div).append($(tree_ulId));

			var	setting =  {
				data: {
				simpleData: {
					enable: true
				}
				},
				callback: {
					onClick: function(e, treeId, treeNode){
						var zTree = $.fn.zTree.getZTreeObj(ulId);
						var nodes = zTree.getSelectedNodes();
						if (nodes.length > 0) {
							var _selected = nodes[nodes.length - 1];
							if(params.callback){
								callback(_selected);
							}
						}
						hideMenu();
					}
				}
			}
			var _data = eval('('+data+')');
			$.fn.zTree.init($(tree_ul), setting, _data);

			//INPUT框绑定点击事件,点击显示树
			$("#"+params.id).click(function(){
				var nameObj = $("#"+params.id);
				var nameOffset = $("#"+params.id).offset();
				$(tree_div).css({left:nameOffset.left + "px", top:nameOffset.top + nameObj.outerHeight() + "px"}).slideDown("fast");
				$(tree_div).css({border: "1px solid #e4e4e4"});
				$("body").bind("mousedown", onBodyDown);
			});

			function onBodyDown(event) {
				if (!(event.target.id == params.id || event.target.id == tree_divId || $(event.target).parents("#"+tree_divId).length>0)) {
					hideMenu();
				}
			}
			function hideMenu() {
				$(tree_div).fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
		  }
		});
	}

	/*
	*自动补全的实现
	* params = {
			id:[input控件的ID][必填],
			data:[url地址或者json数据][必填]
			max:[显示的最大数量][非必填][默认值10]
			minChars：[自动完成前输入的最小字符数][非必填][默认值0]
			width：[提示框的宽度][非必填][默认150]
			scrollHeight：[提示框的高度，超过指定高度，添加滚动条][非必填][默认值300]
			formatItem: function(row, i, max) {
             	return i + '/' + max + ':' +row.cateType+' '+ row.name ;
        	},[显示的格式][row:节点对象；i:索引值;max:显示的最大数量][必填]
        	formatMatch: function(row, i, max) {
             return row.name+row.cateType;
        	},[做匹配的数据格式][必填]
        	formatResult: function(row) {
             	return row.name;                  
        	}[选中某行，返回的结果]
        	callback:function(row){
				
        	}[选中行后触发的事件][非必填]

	}
	* 
	*/
	_app.autocompleted = function(params){
		if(!params.id){
			alert("ID属性不能为空!");
			return ;
		}
		if(!params.data){
			alert("数据源地址不能为空!");
			return ;
		}
		if(!params.formatItem){
			alert("显示格式定义不能为空!");
			return ;
		}
		if(!params.formatMatch){
			alert("匹配规则不能为空!");
			return ;
		}
		if(!params.formatResult){
			alert("返回结果定义不能为空!");
			return ;
		}
		var _max = 10;
		if(params.max){
			_max = params.max;
		}
		var _minChars = 0;
		if(params.minChars){
			_minChars = params.minChars;
		}
		var _width = 150;
		if(params.width){
			_width = params.width;
		}
		var _scrollHeight =300;
		if(params.scrollHeight){
			_scrollHeight = params.scrollHeight;
		}
		var _matchContains = true;
		var _autoFill = false;
		var _callbac = function(){};
		if(params.callback){
			_callbac = params.callback;
		}
		$('#'+params.id).autocomplete(params.data, { 
			max: _max,    //列表里的条目数
	        minChars: _minChars,    //自动完成激活之前填入的最小字符
	        width: _width,     //提示的宽度溢出隐藏
	        scrollHeight: _scrollHeight,   //提示，的高度，溢出显示滚动条
	        matchContains: _matchContains,    //包含匹配，就是data参数里的数 据，是否只要包含文本框里的数据就显示
	        autoFill: _autoFill,    //自动填充
	        formatItem: function(row, i, max){
	        	return params.formatItem(row,i,max);
	        },
	        formatMatch: function(row,i,max){
	        	return params.formatMatch(row,i,max);
	        },
	        formatResult: function(row){
	        	return params.formatResult(row);
	        }
	     	}).result(function(event, row, formatted){
	     		_callbac(event,row,formatted);
	     	});
	}
	/*
	*ajax的实现
	*params={
			type:[post/get][非必填，默认get]
			url:[url地址][必填，如果携带参数须对中文转义]
			async:[false/ture][非必填,默认为true]
			data:[json数据][非必填]
			dataType:[返回值类型][非必填默认text]
			shade:[显示遮罩层true/false][默认不显示]
			success:function(data){}[回调函数]
			}
	* 
	*/
    _app.ajax=function(params){
		var that = this;
		that.params = params;
		var _async = true;
		if(!params.async){
			_async = false;
		}
    	$.ajax({
		    type:params.type||'get',
			async: _async,
			cache: false,
			url  : params.url,
			data : params.data||{},
			dataType : params.dataType||'text',
			beforeSend:function(){
				if(that.params.shade){
					$("body").lock();
				}
			},
			complete:function(){
				if(that.params.shade){
					$("body").unlock();
				}
			},
			success : function(data) {
				params.success.call(params.success,data);
			},
			error:function(msg){
//			    alert(msg.responseText);
			}
		})
	};
    /*
     *表单验证 
	 *params={
				form:[form]
		}
     */
    _app.valid={
    		check:function(params){
    			return validator(params.form);
    		},
    		showObjError:function(obj,msg){
    			return showObjError(obj,msg);
    		},
    		hideError:function(obj){
    			return hideError(obj);
    		}
    		
    };
    
	//----------应用的实现放这里 end--------------------//
    /*
     * 上传文件
	*params={
			id:"file_upload",      [必填，绑定的上传按钮ID]
			queueID:"queue",	   [非必填，绑定的上传动画的文本区域，默认：queue]
			hiddenID:"attachmentId" [必填项，上传文件值保存到该隐藏域文本上]
			moduleName:"",          [非必填，所属模块名称]
			buttonText:"",          [非必填，按钮文本]
			fileTypeExts:"",        [非必填，填写格式".doc,.docx" ，默认所有都可以]
			fileSizeLimit:"",       [非必填，文件大小数字类型，模式20M]
			queueSizeLimit:""       [非必填，上传文件个数限制，默认5个]
			ulName:""				[非必填，上传之后展示附件的ul位置]
			width:""				[非必填，按钮宽度 默认48]
			height:""				[非必填，按钮度 默认15]
			buttonImage:""			[非必填，按钮图片 默认是附件列表的上传图标]
			callback:function(data){}   [非必填，上传成功后的回调函数]
			deleteFun:function(id,path){} [非必填，删除方法实现 id:附件id path:附件路径  建议添加]
	}
	* 
	*/
    _app.fileupload = function(params){
    	if(!params.id){
    		alert("id不正确！");
    		return false;
    	}
    	
    	this.params = params;
    	this._init = function(){
    		this.layout();
    	};
    	
    	/**
    	 * 选中上传后初始化页面
    	 * @param file 文件内容
    	 * @param fileListUl 附件上传展示的ul
    	 */
    	this.onSelectHtml = function(file, fileListUl, fileUpload){
    		var creatAttachHtml = '<li id="'+file.id+'_li"><div class="icon"><em class="'+this.getClassByFileName(file.name)+'"></em></div>';
    		creatAttachHtml += '<div class="txt"><p>'+file.name+'</p>';
    		creatAttachHtml += '<p class="gray_9" id="'+file.id+'_p"><span class="progress_bar mr5"><i id="'+file.id+'_i" style="width:0%"></i></span><font id="'
    		    +file.id+'_font"></font></p>';
    		creatAttachHtml += '</div><p class="clear"></p></li>';
    		$("#"+fileListUl).append(creatAttachHtml);
    	}
    	
    	/**
    	 * 变更上传进度
    	 * @param file 文件内容
    	 * @param totalBytesUploaded 总上传大小
    	 * @param totalBytesTotal 总文件大小
    	 */
    	this.onUploadProgressHelp = function(file, totalBytesUploaded, totalBytesTotal){
//    		var percentage = Math.floor(totalBytesUploaded*100/totalBytesTotal);
//    		$("#"+file.id+"_i").attr("style", "width:"+percentage+"%");
//    		$("#"+file.id+"_font").html(percentage+"%");
    	}
    	
    	/**
    	 *根据文件类型获取对应class
    	 * @param type 文件类型
    	 * @return {string} class名称
    	 */
    	this.getClassByFileName = function(fileName) {
    		
    	    if (fileName.lastIndexOf(".") != -1) {
    	    	fileName = fileName.substr(fileName.lastIndexOf(".")+1, fileName.length);
    	    }else{
    	    	fileName = 'unknow';
    	    }
    	    fileName = fileName.toLocaleLowerCase();
    	    var defaultType = {txt: "wb", doc: "doc", ppt: "ppt", excel: "excel", img: "img", rar: "rar",unknown:"unknown"};
    	    switch (fileName) {
    	        case "txt":
    	            return defaultType.txt;
    	        case "doc":
    	        case "docx":
    	            return defaultType.doc;
    	        case "ppt":
    	        case "pptx":
    	            return defaultType.ppt;
    	        case "xls":
    	        case "xlsx":
    	            return defaultType.excel;
    	        case "gif":
    	        case "jpg":
    	        case "jpeg":
    	        case "png":
    	            return defaultType.img;
    	        case "rar":
    	        case "zip":
    	        case "7z":
    	            return defaultType.rar;
    	        default :
    	            return defaultType.unknown;
    	    }
    	}
    	
    	/**
    	 * 上传完成后操作
    	 * @param file 文件内容
    	 */
    	this.onUploadSuccess = function(file, filePath, fileUpload, attachId){
    		$("#"+file.id+"_i").parent().remove();
    		$("#"+file.id+"_font").remove();
			var pArea = $("#"+file.id+"_p");
			var aArea = $("<a>",{
    				"id":file.id+"_delete",
    				"class":"ml10",
    				"href":"javascript:void(0);",
    				"text":"删除",
    				"click":function(){
    					$('#'+fileUpload).uploadify('cancel', file.id);
    		    		$('#'+file.id+"_li").remove();
    		    		if(params.deleteFun){
    		    			params.deleteFun(attachId,filePath);
    		    		}
    				}
    			});
			pArea.append(aArea);
    	};
    	
    	
    	
    	this.layout = function(){
    		var that = this;
    		jQuery("#"+that.params.id).uploadify({
    			"queueID" : that.params.queueID||"queue",
    			"fileObjName" : "fileupload",
    			"uploader" : basePath +  "filemanager/uploadfile.action?module="+that.params.moduleName||"",
    			"buttonText" : that.params.buttonText||"上传附件...",
    			"formData" : {
    				"userid" : "用户id",
    				"username" : "用户名",
    				"rnd" : "加密密文"
    			},
    			"buttonImage" : that.params.buttonImage||(basePath + "flat/images/upload.png"),
    			"cancel" : basePath + "plugins/upload/upbutton.png",
    			"width" : that.params.width||"48",
    			"height" : that.params.height||"15",
    			"fileTypeDesc" : "支持的格式:",
    			"fileTypeExts" : that.params.fileTypeExts||"*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.txt;*.jpg;*.jpeg;*.png;*.gif;*.bmp;*.zip;*.rar;*.7z;*.html;*.css;*.rm;*.rmvb;*.mp3;*.mp4;*.avi;*.wmv;*.flv;*.mpg;*.mpeg;*.mkv;*.pdf;*.ios;*.mid;*.chm;*.ico",
    			"fileSizeLimit" : that.params.fileSizeLimit||"20MB",
    			"queueSizeLimit" : that.params.queueSizeLimit||5,
    			"debug" : false,
    			"auto" : true,
    			"removeComplete" : false,
    			"removeTimeout" : 0,
    			"langFile" : basePath + "plugins/upload/ZH_cn.js",
    			"successTimeout" : 99999,
    			"swf" : basePath + "plugins/upload/uploadify.swf",
    			"overrideEvents" : ["onDialogClose"],
    			"onUploadProgress" : function(file, bytesUploaded, bytesTotal,totalBytesUploaded, totalBytesTotal) {
    				that.onUploadProgressHelp(file, totalBytesUploaded, totalBytesTotal);
    			},
    			"onSelect" : function(file) {
    				if(file.name.length>30){
    					art.dialog.alert("文件名长度不能大于30个字符!");
						return false;
    				}
    				if(params.ulName){
    					that.onSelectHtml(file, params.ulName, params.id);
    				}
    				return true;
    			},
    			"onSelectError" : function(file, errorCode, errorMsg) {
    				switch (errorCode) {
    					case -100 :
    						art.dialog.alert("上传的文件数量已经超出系统限制的"
    								+ $("#"+params.id).uploadify("settings",
    										"queueSizeLimit") + "个文件！");
    						break;
    					case -110 :
    						art.dialog.alert("文件 ["
    								+ file.name
    								+ "] 大小超出系统限制的"
    								+ $("#"+params.id).uploadify("settings",
    										"fileSizeLimit") + "大小！");
    						break;
    					case -120 :
    						art.dialog.alert("文件 [" + file.name + "] 大小异常！");
    						break;
    					case -130 :
    						art.dialog.alert("文件 [" + file.name + "] 类型不正确！");
    						break;
    				}
    			},
    			"onFallback" : function() {
    				art.dialog.alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
    			},
    			"onUploadSuccess" : function(file, data, response) {
    				if(data){
    					if(data=="filenametoolong"){
    						art.dialog.alert("文件名长度不能大于30个字符!");
    						return;
    					} 
    				}
    				
    				data = eval("(" + data + ")");
    				/** 将附件id赋值到隐藏域 **/
    				if(params.hiddenID){
    					var attachmentId = $("#"+params.hiddenID).val();
    					if (attachmentId) {
    						attachmentId = attachmentId + data.id + ",";
    					} else {
    						attachmentId = "," + data.id + ",";
    					}
    					$("#"+params.hiddenID).val(attachmentId);
    				}
    				
    				/** 附件后缀 **/
    				if(params.ulName){
    					that.onUploadSuccess(file,data.attachFile,params.id,data.id);
    				}
    				
    				/** 回调函数 **/
    				if(params.callback){
    					params.callback(data);
    				}
    			},
    			// 上传前取消文件
    			"onCancel" : function(file) {
    				 art.dialog.alert("The file " + file.name + " was cancelled.");
    			}
    		});
    	};
    	this._init();
    	return this;
    };

})(window);