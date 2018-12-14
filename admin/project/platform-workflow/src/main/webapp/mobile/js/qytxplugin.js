document.addEventListener( "plusready",  function()
{
    var _BARCODE = 'qytxplugin',
		B = window.plus.bridge;
    var qytxplugin =
    {
    	selectUsers : function (userIds, simpleCheck, successCallback)
		{
			var success = typeof successCallback !== 'function' ? null : function(args)
            {
                successCallback(args);
            };
            callbackID = B.callbackId(success);
			return B.exec(_BARCODE, "selectUsers", [callbackID, userIds, simpleCheck,false]);
		},
		selectDateTime : function (Args, successCallback)
        {
        	var success = typeof successCallback !== 'function' ? null : function(args)
            {
            	successCallback(args);
            };
            callbackID = B.callbackId(success);
            return B.exec(_BARCODE, "selectDateTime", [callbackID, Args]);
        },
        showDateTimeHalf : function (Args, successCallback)
        {
            var success = typeof successCallback !== 'function' ? null : function(args)
            {
                successCallback(args);
            };
            callbackID = B.callbackId(success);
            return B.exec(_BARCODE, "showDateTimeHalf", [callbackID, Args]);
        },
        selectGroups : function (groupIds, simpleCheck, successCallback)
        {
        	var success = typeof successCallback !== 'function' ? null : function(args)
            {
                successCallback(args);
            };
            callbackID = B.callbackId(success);
            return B.exec(_BARCODE, "selectGroup", [callbackID, groupIds, simpleCheck]);
        },
        localMessage : function (Args, successCallback)
        {
            var success = typeof successCallback !== 'function' ? null : function(args)
            {
                successCallback(args);
            };
            callbackID = B.callbackId(success);
            return B.exec(_BARCODE, "localMessage", [callbackID, Args]);
        },
        getLoginUserInfo : function ()
        {
            return B.execSync(_BARCODE, "getLoginUserInfo", []);
        },
        getBaseUrlPath : function (moduleName,methodName)
        {
            return B.execSync(_BARCODE, "getBaseUrlPath", [moduleName,methodName]);
        },
     	goBackDesk : function ()
        {
            return B.execSync(_BARCODE, "goBackDesk", []);
        },
        appReady : function ()
        {
            return B.execSync(_BARCODE, "appReady", []);
        },
        log : function (msg)
        {
            return B.execSync(_BARCODE, "log", [msg]);
        },
		sendDidi : function (didiJson, successCallback)
		{
			var success = typeof successCallback !== 'function' ? null : function(args)
            	{
                       successCallback(args);
                };
                callbackID = B.callbackId(success);
			return B.exec(_BARCODE, "sendDidi", [callbackID, didiJson]);
		}
    };
    window.plus.qytxplugin = qytxplugin;
}, true );