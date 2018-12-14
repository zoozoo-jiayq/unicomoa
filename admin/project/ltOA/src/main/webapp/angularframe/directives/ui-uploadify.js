angular.module("ui-uploadify", []).directive("uploadfs", function() {
	var defaultConfig = {
		fileSize: "10MB",
		queueSize: 25,
		fileObjName: "fileupload",
		fileTypeExts: "*"
	};
	return {
		scope: {
			config: "=",
			outfile: "="
		},
		template: "<input type='file' id='_uf' multiple  />" +
			"<div id='_queueid' style='display:none'></div>" +
			"<div class='annex'>" +
			"<ul>" +
			"<li ng-repeat='f in fs'>" +
			"<div class='icon'><em class='wb'></em></div>" +
			"<div class='txt'><p>{{f.name}}</p><p class='gray_9'><a class='ml10' ng-click='dele(f);''>删除</a></p></div>" +
			"<p class='clear'></p>" +
			"</li>" +
			"</ul>" +
			"</div>",
		link: function(scope, element, attrs) {
			var options = angular.extend(scope.config, defaultConfig);
			var fs = new Array();
			scope.fs = fs;
			scope.outfile = new Array();
			scope.dele = function(f) {
				scope.fs = _delf(f,scope.fs);
				scope.outfile = _delf(f,scope.outfile);
			}
			
			function _delf(f,flist){
				var index = -1;
				for (var i = 0; i < flist.length; i++) {
					if (f.id == flist[i].fileId) {
						index = i;
					}
				}
				if (index >= 0) {
					var source = flist;
					source = source.slice(0, index).concat(source.slice(index + 1));
					return source;
				}
				return [];
			}
			
			$("#_uf").uploadify({
				'fileObjName': options.fileObjName,
				'queueID': "_queueid",
				'uploader': options.url,
				'buttonText': '上传图片...',
				'buttonImage': options.buttonImg,
				'width': '77',
				'height': '25',
				'fileTypeDesc': '支持的格式:',
				'fileTypeExts': options.fileTypeExts,
				'fileSizeLimit': options.fileSize,
				'queueSizeLimit': options.queueSize,
				'debug': false,
				'auto': true,
				'removeComplete': false,
				'removeTimeout': 0,
				'successTimeout': 99999,
				'swf': options.swf,
				'onUploadProgress': function(file, bytesUploaded, bytesTotal,
					totalBytesUploaded, totalBytesTotal) {
					//options.upload_progress(file,bytesUploaded,bytesTotal);
				},
				'onSelect': function(file) {
					scope.$apply(function() {
						scope.fs.push(file);
					});
					return true;
				},
				'onSelectError': function(file, errorCode, errorMsg) {},
				'onFallback': function() {
					alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
				},
				'onUploadSuccess': function(file, data, response) {
					scope.$apply(function() {
						scope.outfile.push({
							fileId: file.id,
							fileName: file.name,
							fileSize: file.size,
							fileType: file.type,
							fileData: eval('('+data+')')
						});
					});
				}
			});
		}
	};
});