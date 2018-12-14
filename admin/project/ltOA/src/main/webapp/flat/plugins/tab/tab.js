// JavaScript Document, 
/*
 * showCloseBtn ：判断是否显示关闭连接，默认显示，如果showCloseBtn值为1则不显示
 */
var currentUrl;
var _tabArr =[]; //保存所有的菜单是否有子菜单
var _currentTabState="close";
var _clsHeadState="展开";  //  展开  收起
function addTab(tabid, url, name, showCloseBtn, mothodList) {
    currentUrl = url;
    //遍历并清除开始存在的tab当前效果并隐藏其显示的div
    var tablist = document.getElementById("div_tab").getElementsByTagName('li');
    var pannellist = document.getElementById("div_pannel").getElementsByTagName('iframe');
    if (tablist.length > 0) {
        for (var i = 0; i < tablist.length; i++) {

            if(("div_"+tabid)==pannellist[i].id)
            {
                pannellist[i].style.display = "block";
                tablist[i].className = 'crent';
                clearFunTabStyle(tabid);
            }
            else
            {
                pannellist[i].style.display = "none";
                tablist[i].className = "";
            }
        }
    }

    ///如果当前tabid存在直接显示已经打开的tab
    if (document.getElementById("div_" + tabid) == null) {
        //创建iframe
        var box = document.createElement("iframe");
        box.id = "div_" + tabid;
        box.name = "div_" + tabid;
        box.height = "100%";
        box.frameBorder = 0;
        box.width = "100%";
        document.getElementById("div_pannel").appendChild(box);
        //创建li菜单
        var tab = document.createElement("li");
        tab.className = "crent";
        tab.id = tabid;
        var litxt = "";
        if(showCloseBtn==undefined||showCloseBtn)
        {
            litxt = "<a href=\"javascript:void(0);\" title=" + name + " class=\"menua\"  onclick=\"switchTab('"+tabid+"')\" >" + name + "</a><a onclick=\"closeTab('" + tabid + "')\" class=\"win_close\" title=\"关闭当前窗口\"><a>";
        }
        else {
            litxt = "<span><a href=\"javascript:void(0);\"  title=" + name + " class=\"menua\" onclick=\"switchTab('"+tabid+"')\" >" + name + "</a></span>";
        }

        tab.innerHTML = litxt;
        document.getElementById("div_" + tabid).style.display = 'block';
        document.getElementById("div_tab").appendChild(tab);

        //添加导航
        var s_tab = document.createElement("div");
       // var s_left = tabid.style.left;
        s_tab.id = "func_tab_" + tabid;
        s_tab.className = "lastMenu";
     //   s_tab.style.left = s_left + 50;
        var s_tabTxt = "";
        if (mothodList != undefined && mothodList != "") {
            var data = eval("(" + mothodList + ")");
           for (var i = 0; i < data.length; i++) {
                if (i == 0) {
                    s_tabTxt += " <li class=\"current\"><a href=\"javascript:void(0);\" onclick=\"flushIframeUrl(this,'" + tabid + "','"+basePath + data[i].url + "')\">" + data[i].moduleName + "</a></li>";
                }
                else {
                    s_tabTxt += "<li>|</li><li><a href=\"javascript:void(0);\" onclick=\"flushIframeUrl(this,'" + tabid + "','"+basePath + data[i].url + "')\">" + data[i].moduleName + "</a></li>";
                }
            }
            _tabArr[tabid]=1;
            showIndexHeader();
			 
        }
		else
		{
		     //如果不存在二级菜单，则不显示菜单div
		   _tabArr[tabid]=0;  
		   hideIndexHeader();
		}
        //移除最后一个
        if (endsWith(s_tabTxt, "|")) {
            s_tabTxt = s_tabTxt.substr(0, s_tabTxt.length - 1);
        }
        s_tab.innerHTML = s_tabTxt;
        document.getElementById("s_tab").appendChild(s_tab);
        clearFunTabStyle(tabid);

        //设置tal的总宽度
        resizeLayout();//调整tab所占用的宽度
       setUlWidth();
        //设置二级菜单的位置   
       var window_w = $(window).width();/*当前屏幕的宽度*/
       var tl_w = $("#div_tab li.crent").position().left + $("#div_tab li.crent").width();/*当前选项卡右边距离屏幕左边的距离*/
       if(window_w - tl_w < 80){
    	   $(".s_tab .lastMenu:visible").css("right",window_w - tl_w);
       }else{
    	   $(".s_tab .lastMenu:visible").css("left",$("#div_tab li.crent").position().left-($("#s_tab .lastMenu").last().width()-$("#div_tab li.crent").width())/2);
       }
      // 解决IE6 初次不加载问题
      document.getElementById("div_" + tabid).src = url;
    }
}

/**
 * 显示三级菜单栏
 */
function showIndexHeader(){
	 $(".indexHeader").css("height","122px");
	  $(".s_tab").show();
	  if(_clsHeadState=="收起"){
		   $(".iframeFather").css("top","68px");
	  }else{
		   $(".iframeFather").css("top","122px");
	  }
	  _currentTabState="open";
}
/**
 * 隐藏三级菜单栏
 */
function hideIndexHeader(){
	  $(".indexHeader").css("height","89px");
	   $(".s_tab").hide();
	  if(_clsHeadState=="收起"){
		   $(".iframeFather").css("top","35px");
	  }else{
		   $(".iframeFather").css("top","89px");
	  }
	   _currentTabState="close";
}

/**
 * 切换tab
 */
function switchTab(tabid)
{
    //遍历并清除开始存在的tab当前效果并隐藏其显示的div
	if(tabid=="shouye"){
		hideIndexHeader();
	}else{
		var isopenTab = _tabArr[tabid];
		if(isopenTab==0){
			hideIndexHeader();
		}else{
			showIndexHeader();
		}
	}
	
    var tablist = document.getElementById("div_tab").getElementsByTagName('li');
    var pannellist = document.getElementById("div_pannel").getElementsByTagName('iframe');
    if (tablist.length > 0) {
        for (var i = 0; i < tablist.length; i++) {
            if(("div_"+tabid)==pannellist[i].id)
            {
                pannellist[i].style.display = "block";
                tablist[i].className = 'crent';
                clearFunTabStyle(tabid);
            }
            else
            {
                pannellist[i].style.display = "none";
                tablist[i].className = '';
            }
        }
    }
    //document.getElementById("div_" + tabid).src =  document.getElementById("div_" + tabid).src;

}
function closeTab(obj) {
    var ob = document.getElementById(obj);
    if (ob == undefined || ob == null) {
        return;
    }
    
    ob.parentNode.removeChild(ob);
    var obdiv = document.getElementById("div_" + obj);
    
    // 说明:添加这3行代码是因为,通过在线人员发送邮件时,关闭此tab时,报错
    // 报错  消息: “__flash__removeCallback”未定义 原因是flash关闭时引起的 上传图片引起的
    if(obdiv.contentWindow){
    	try{
    		var mailAttachmentTd = obdiv.contentWindow.document.getElementById('mailAttachmentTd');
            if(null != mailAttachmentTd)
            mailAttachmentTd.parentNode.removeChild(mailAttachmentTd);
    	}catch(e){
    	}
    }
    
    
    obdiv.parentNode.removeChild(obdiv);

    var tablist = document.getElementById("div_tab").getElementsByTagName('li');
    var pannellist = document.getElementById("div_pannel").getElementsByTagName('iframe');
    if (tablist.length > 0) {
        //显示上一个tab页
        tablist[tablist.length - 1].className = 'crent';
        pannellist[tablist.length - 1].style.display = 'block';
    }
    //删除功能菜单项
    var funcTab = document.getElementById("func_tab_" + obj);
    funcTab.parentNode.removeChild(funcTab);
    var funcTabList = getElementsByClassName("s_tab", "lastMenu");
    if (funcTabList.length > 0) {
        //显示上一个功能tab页
    	var tabAttrId = funcTabList[funcTabList.length - 1].id;
    	var tabAttrIdArr = tabAttrId.split("_");
    	var tId = tabAttrIdArr[2];
    	if("shouye"==tId){
    		hideIndexHeader();
    	}else{
    		var isopenTab = _tabArr[tId];
    		if(isopenTab==0){
    			hideIndexHeader();
    		}else{
    			showIndexHeader();
    		}
    	}
    	 
        funcTabList[funcTabList.length - 1].style.display = 'block';
       
        
        
    }
     //设置tal的总宽度
        resizeLayout();//调整tab所占用的宽度
       setUlWidth();
       //设置二级菜单的位置   
       $(".s_tab .lastMenu:visible").css("left",$("#div_tab li.crent").position().left-($("#s_tab .lastMenu").last().width()-$("#div_tab li.crent").width())/2);
}



function flushIframeUrl(obj,tabid, url) {
   document.getElementById("div_" + tabid).src = url;
    //首先清除其他样式
   $(obj).parent().parent().find(".current").removeClass("current"); 
   $(obj).parent().addClass("current");	

}
/**
 * 清除其他功能tab的样式
 * @param tabid
 */
function clearFunTabStyle(tabid) {

    var tablist = getElementsByClassName("s_tab", "lastMenu");
    for (var i = 0; i < tablist.length; i++) {
        if (tablist[i].id == "func_tab_" + tabid) {
            tablist[i].style.display = "block";

           // tablist[i].children()[0].className="current" ;
        }
        else {
            tablist[i].style.display = "none";
           // tablist[i].children()[0].className="" ;
        }
    }
}
/**
 * 根据className获取元素
 */
function getElementsByClassName(parentId, className) {
    var el = [],
        _el = document.getElementById(parentId).getElementsByTagName('*');
    for (var i = 0; i < _el.length; i++) {
        if (_el[i].className == className) {
            el[el.length] = _el[i];
        }
    }
    return el;
}
function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

//关闭当前的TAB
function closeCurrentTab(){
	var lis = document.getElementsByTagName("li");
	for(var i=0; i<lis.length; i++){
		if(lis[i].getAttribute("class")=='crent'){
			var id = lis[i].getAttribute("id");
			closeTab(id);
		}
	}
}