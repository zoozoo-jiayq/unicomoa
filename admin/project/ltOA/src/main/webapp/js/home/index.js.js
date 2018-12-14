
var MENU_ITEM_HEIGHT = 30;
var MIN_PNAEL_HEIGHT = 8 * MENU_ITEM_HEIGHT;
var MAX_PNAEL_HEIGHT = 20 * MENU_ITEM_HEIGHT;
var SCROLL_HEIGHT = 4 * MENU_ITEM_HEIGHT;
var bigMenuIcons = ['@crs','reportshop','comm','erp','exam_manage','hrms','info','mytable','project','roll_manage','sale_manage','sys','system',
                    'training','workflow','address','netdisk','picture','wiki','wf_entrust','wf_destory','wf_log','wf_mine','wf_new',
                    'wf_query','wf_stat','sms','person_info','todo','notify','notify_auditing','email','calendar','diary','bbs','meeting',
                    'attendance','attendance_manage','work_plan','vehicle','score','vote','fax','file_folder','news','itask'];

var timer_sms_mon = null;
var timer_online_tree_ref = null;
//΢Ѷ���Զ��ر�ʱ�䣬��
var smsbox_close_timeout = 3;
var smsbox_close_countdown = null;
var smsbox_close_timer = null;
var closeNocPanel = null;
var nocbox_close_timeout = 3;
var timeLastLoadOnline = 0;
var nextTabId = 0;

jQuery.noConflict();

(function($){
   $.fn.addTab = function(id, title, url, closable, selected){
      $('.over-mask-layer').hide();   //������Ż��л���������������򿪣�������֮
      $('#overlay_panel').hide();

      if(!id) return;
      closable = (typeof(closable) == 'undefined') ? true : closable;
      selected = (typeof(selected) == 'undefined') ? true : selected;
      var height = isTouchDevice() ? 'auto' : '100%';
      $('#tabs_container').tabs('add', {
         id: id,
         title: title,
         closable: closable,
         selected: selected,
         style: 'height:' + height + ';',
         content: '<iframe id="tabs_' + id + '_iframe" name="tabs_' + id + '_iframe" allowTransparency= "true" src="' + url + '" onload="IframeLoaded(\'' + id + '\');" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:' + height + ';"></iframe>'
      });
   };
   $.fn.selectTab = function(id){
      $('#tabs_container').tabs('select', id);
   };
   $.fn.closeTab = function(id){
      $('#tabs_container').tabs('close', id);
   };
   $.fn.getSelected = function(){
      return $('#tabs_container').tabs('selected');
   };
   
   //�ӳ�ͼƬ����
   function LoadImage(selector)
   {
      $(selector).each(function(){
         this.src = this.getAttribute("_src");
         this.removeAttribute("_src");
      });
   }
   
   function checkActive(id){
      if($('#'+id+'_panel:hidden').length > 0)
         $('#'+id).removeClass('active');
      else
         window.setTimeout(checkActive, 300, id);
   };

   function getSecondMenuHTML(id){
      var html = '';
      for(var i=0; i < second_array[id].length; i++)
      {
         var func_id = 'f' + second_array[id][i];
         if(!func_array[func_id])
            continue;
         
         var func_name = func_array[func_id][0];
         var func_code = func_array[func_id][1];
         var open_window = func_array[func_id][3] ? func_array[func_id][3] : '';
         var bExpand = func_code.substr(0,1) == "@" && third_array[func_id];
         func_code = func_code.replace("LOGIN_USER_ID", cur_login_user_id)
         var onclick = bExpand ? "" : "createTab(" + func_id.substr(1) + ",'" + func_name.replace("'", "\'") + "','" + func_code.replace("'", "\'") + "','" + open_window + "');";

         html += '<li><a id="' + func_id + '" href="javascript:;" onclick="' + onclick + '"' + (bExpand ? ' class="expand"' : '') + ' hidefocus="hidefocus"><span>' + func_name + '</span></a>';
         if(bExpand)
         {
            html += '<ul>';
            for(var j=0; j < third_array[func_id].length; j++)
            {
               var func_id1 = 'f' + third_array[func_id][j];
               var func_name1 = func_array[func_id1][0];
               var func_code1 = func_array[func_id1][1];
               var open_window1 = func_array[func_id1][3] ? func_array[func_id1][3] : '';
               var onclick1 = "createTab(" + func_id1.substr(1) + ",'" + func_name1.replace("'", "\'") + "','" + func_code1.replace("'", "\'") + "','" + open_window1 + "');";
               html += '<li><a id="' + func_id1 + '" href="javascript:;" onclick="' + onclick1 + '" hidefocus="hidefocus"><span>' + func_name1 + '</span></a></li>';
            }
            html += '</ul>';
         }
         html += '</li>';
      }

      return '<ul id="second_menu">' + html + '</ul>';
   };

   function resizeLayout()
   {
      // ����������߶�
      var wWidth = (window.document.documentElement.clientWidth || window.document.body.clientWidth || window.innerHeight);
      var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
      var nHeight = $('#north').is(':visible') ? $('#north').outerHeight() : 0;
      var fHeight = $('#funcbar').is(':visible') ? $('#funcbar').outerHeight() : 0;
      var cHeight = wHeight - nHeight - fHeight - $('#south').outerHeight() - $('#hero_bar').outerHeight() - $('#taskbar').outerHeight();
      $('#center').height(cHeight);
      
      $("#center iframe").css({height: cHeight});

/*
      if(isTouchDevice())
      {
         $('.tabs-panel:visible').height(cHeight);
         if($('.tabs-panel > iframe:visible').height() > cHeight)
            $('.tabs-panel:visible').height($('.tabs-panel > iframe:visible').height());
      }
*/
      //һ����ǩ���
      var width = wWidth - $('#taskbar_left').outerWidth() - $('#taskbar_right').outerWidth();
      $('#tabs_container').width(width - $('#tabs_left_scroll').outerWidth() - $('#tabs_right_scroll').outerWidth() - 2);
      $('#taskbar_center').width(width-1);   //-1��Ϊ�˼���iPad

      $('#tabs_container').triggerHandler('_resize');
   };

   //�˵�������ͷ�¼�,idΪfirst_menu
   function initMenuScroll(id)
   {
      //�˵����Ϲ�����ͷ�¼�
      $('#' + id + ' > .scroll-up:first').hover(
         function(){
            $(this).addClass('scroll-up-hover');
            if(id == 'first_panel')
            {
               $("#first_menu > li > a.active").removeClass('active');   //�ָ�һ��active�Ĳ˵�Ϊ��
            }
         },
         function(){
            $(this).removeClass('scroll-up-hover');
         }
      );

      //������ϼ�ͷ
      $('#' + id + ' > .scroll-up:first').click(
         function(){
            var ul = $('#' + id + ' > ul:first');
            ul.animate({'scrollTop':(ul.scrollTop()-SCROLL_HEIGHT)}, 600);
         }
      );

      //���¹�����ͷ�¼�
      $('#' + id + ' > .scroll-down:first').hover(
         function(){
            $(this).addClass('scroll-down-hover');
            if(id == 'first_panel')
            {
              $("#first_menu > li > a.active").removeClass('active');   //�ָ�һ����active�Ĳ˵�Ϊ��
            }
         },
         function(){
            $(this).removeClass('scroll-down-hover');
         }
      );

      //������¼�ͷ
      $('#' + id + ' > .scroll-down:first').click(
         function(){
            var ul = $('#' + id + ' > ul:first');
            ul.animate({'scrollTop':(ul.scrollTop()+SCROLL_HEIGHT)}, 600);
         }
      );
   };

   function initStartMenu()
   {
      //���ҳ�棬���ظ����˵���壬������������˵���active״̬
      $('#overlay_startmenu').click(function(){
         if($('#start_menu_panel:visible').length)
         {
            $('#overlay_startmenu').hide();
            $('#start_menu_panel').slideUp(300);
            $('#start_menu').removeClass('active');
         }
      });

      //���������ͼ�갴ť�����˵����
      $('#start_menu').bind('click', function(){
         if($('#start_menu_panel:visible').length)
         {
            $('#overlay_startmenu').hide();
            $('#start_menu_panel').slideUp(300);
            $(this).removeClass('active');
         }
         
         //�ӳټ���ͼƬ
         LoadImage("#first_menu img[_src]");
         
         //���õ���ͼ��Ϊactive״̬
         $(this).addClass('active');

         //���ֲ�λ�ú���ʾ
         $('#overlay_startmenu').show();

         //�˵����λ��
         var top = $('#start_menu').offset().top + $('#start_menu').outerHeight() - 6;
         $('#start_menu_panel').css({top: top});
         $('#start_menu_panel').slideDown('fast');

         ////���㲢���ò˵����ĸ߶�,�Ƿ���ʾ������ͷ
         var scrollHeight = $("#first_menu").attr('scrollHeight');
         if($("#first_menu").height() < scrollHeight)
         {
            var height = ($('#south').offset().top - $('#start_menu').offset().top)*0.7;   //���ø߶�Ϊ��ʼ�˵���״̬���߲��70%
            height = height - height % MENU_ITEM_HEIGHT;   //���ø߶�Ϊ MENU_ITEM_HEIGHT ������
            //�����ø߶ȴ����������߸߶ȣ�������
            height = height <= MAX_PNAEL_HEIGHT ? height : MAX_PNAEL_HEIGHT;
            //�����ø߶ȳ���scrollHeight�������ø߶�ΪscrollHeight
            height = height > scrollHeight ? scrollHeight : height;
            $('#first_menu').height(height);
         }
         else
         {
            var height = scrollHeight > MIN_PNAEL_HEIGHT ? scrollHeight : MIN_PNAEL_HEIGHT;
            $('#first_menu').height(height);
         }

         if($("#first_menu").height() >= $("#first_menu").attr('scrollHeight'))
         {
            $('#first_panel > .scroll-up:first').hide();
            $('#first_panel > .scroll-down:first').hide();
         }

         //���㲢���ö����˵�����λ��
         //var top = $('#first_menu').offset().top - $("#start_menu_panel").offset().top;
         //$('#second_panel').css('top', top - 5);
         $('#second_panel > .second-panel-menu').css('height', $('#first_menu').height());

         //��һ�δ�ʱ���ö����˵������¼�
         if($('#second_panel > .second-panel-menu > .jscroll-c').length <= 0)
            $('#second_panel > .second-panel-menu').jscroll();
      });
      
      //���һ���˵�
      var html = "";
      for(var i=0; i<first_array.length; i++)
      {
         var menu_id = first_array[i];
         if(typeof(func_array['m'+menu_id]) != "object")
            continue;
         
         var image = !func_array['m'+menu_id][1] ? 'icon_default' : func_array['m'+menu_id][1];
         html += '<li><a id="m' + menu_id + '" href="javascript:;" hidefocus="hidefocus"><img src="/images/transparent.gif" align="absMiddle" _src="/images/menu/' + image + '.png"/> ' + func_array['m'+menu_id][0] + '</a></li>';
      }
      $("#first_menu").html(html);
      $("#first_menu").mousewheel(function(){
         $('#first_menu').stop().animate({'scrollTop':($('#first_menu').scrollTop() - this.D)}, 300);
      });

      //һ���˵�������ͷ�¼�
      initMenuScroll('first_panel');

      //һ���˵�hover��click�¼�
      $("#first_menu > li > a").click(function(){
         //���ǰһ���˵�Ϊactive���򷵻�
         if(this.className.indexOf('active') >= 0)
            return;

         $("#second_menu > li > a.expand").removeClass('active');   //�ָ�����expand�˵�Ϊ��
         $("#first_menu > li > a.active").removeClass('active');   //�ָ�һ����active�Ĳ˵�Ϊ��

         //��ȡ��ǰһ���˵����������˵���HTML���룬�����¶����˵����
         $('#second_panel > .second-panel-menu').html(getSecondMenuHTML(this.id));
         $("#" + this.id).addClass('active');   //����ǰһ���˵���Ϊactive

         //�������˵������¼�
         $('#second_panel > .second-panel-menu').jscroll();

         //�����˵����չ����˵�
         $('#second_menu > li > a.expand').click(function(){
            $(this).toggleClass('active');
            $(this).parent().children('ul').toggle();
            $('#second_panel > .second-panel-menu').jscroll();
         });
      });

      if(menuExpand !="" && typeof(second_array['m'+menuExpand]) == "object")
      {
         //չ������Ķ����˵�
         $('#m'+menuExpand).addClass('active');
         $('#second_panel > .second-panel-menu').html(getSecondMenuHTML('m'+menuExpand));
         
         //�����˵����չ����˵�
         $('#second_menu > li > a.expand').click(function(){
            $(this).toggleClass('active');
            $(this).parent().children('ul').toggle();
            $('#second_panel > .second-panel-menu').jscroll();
         });
      }
      else
      {
         //��¼ʱ�ѳ�������˵�����Ϊ�����˵�������
         var html = "";
         for(var i=0; i<shortcutArray.length; i++)
         {
            if(typeof(func_array['f'+shortcutArray[i]]) != "object")
               continue;
         
            var func_id = 'f'+shortcutArray[i];
            var func_name = func_array[func_id][0];
            var func_code = func_array[func_id][1];
            var open_window = func_array[func_id][3] ? func_array[func_id][3] : "";
         
            if(func_code.substr(0, 1) == "@")
               continue;
         
            var onclick = "createTab(" + func_id.substr(1) + ",'" + func_name.replace("'", "\'") + "','" + func_code.replace("'", "\'") + "','" + open_window + "');";
            html += '<li><a id="' + func_id + '" href="javascript:;" onclick="' + onclick + '" hidefocus="hidefocus"><span>' + func_name + '</span></a></li>';
         }
         html = '<ul id="second_menu">' + html + '</ul>';
         $('#second_panel > .second-panel-menu').html(html);
      }
      
      $('#second_panel, #second_menu').bind('selectstart', function(){
         return false;
      });
      
      //����״̬����¼�
      $('#start_menu_panel > .panel-user > .avatar').hover(
         function(){},
         function(){window.setTimeout(function(){$('#on_status').fadeOut();}, 300);}
      );
      $('#start_menu_panel > .panel-user > .avatar').click(function(){
         $('#on_status').fadeIn(300, function(){
            $(this).css('filter', 'progid:DXImageTransform.Microsoft.shadow(strength=5, direction=135, color=#a3a4a8);');
         });
      });
      
      $('#on_status > a').click(function(){
         var status = $(this).attr('status');
         if(status < "1" || status > "4") return;
         
         $.get("ipanel/pheader.php", {ON_STATUS_SET: status});
         $('#start_menu_panel > .panel-user > .avatar > .status_icon').attr('class', 'status_icon status_icon_'+status);
         $('#on_status').fadeOut(300);
      });
   }

   function initTabs()
   {
      //���ñ�ǩ������
      $('#tabs_container').tabs({
         tabsLeftScroll:'tabs_left_scroll',
         tabsRightScroll:'tabs_right_scroll',
         panelsContainer:'center',
         secondTabsContainer: 'funcbar_left'
      });
      
      //�ر����б�ǩ����ʾ�Ż��л�
      $('#tabs_container').bind('_close', function(){
         if($('a.tab', this).length <= 0 && $('#portal_panel:visible').length <= 0)
            $('#portal').trigger('click');
      });
   }

   function initTaskCenter()
   {
      //�Ż��л��¼�
      $('#task_center').bind('click', function(){
         if($('#'+this.id+'_panel:visible').length)
         {
            $('#'+this.id+'_panel').animate({top:-$('#'+this.id+'_panel').outerHeight()}, 600, function(){$(this).hide()});
            $('#overlay_panel').hide();
            return;
         }
         
         //��һ�μ���
         if($('#'+this.id+'_panel').html() == "")
         {
            $('#'+this.id+'_panel').html('<iframe id="task_center_iframe" name="task_center_iframe" allowTransparency= "true" src="" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:100%;"></iframe>');
            $('#'+this.id+'_panel iframe').attr('src', '/general/task_center/');
         }
         else
         {
            if(typeof(frames['task_center_iframe'].init) == 'function')
               frames['task_center_iframe'].init();
         }

         //���λ��
         $('.over-mask-layer').hide();
         $('#overlay_panel').show();
         $('#'+this.id+'_panel').css('left', ($(document).width()-$('#'+this.id+'_panel').width())/2);
         var top = $('#'+this.id+'_panel').outerHeight() > $('#center').outerHeight() ? 0 : 20;
         $('#'+this.id+'_panel').css({top: -$('#'+this.id+'_panel').outerHeight()});
         $('#'+this.id+'_panel').show().animate({top:top}, 600);

         //��������ͼ����Ϊactive״̬
         $(this).addClass('active');
         window.setTimeout(checkActive, 300, this.id);
      });
   }
   
   function initIE6Tips()
   {
      $('#ie6_tips :button.hide-for-ever').click(function(){
         $.cookie('IE6_NO_TIPS', "1", {expires:1000, path:'/'});
         $('#ie6_tips').hide();
      });
      
      $('#ie6_tips :button.hide-this-time').click(function(){
         $('#ie6_tips').hide();
      });
   }
   
   function initPortal()
   {
      //�Ż��л��¼�
      $('#portal').bind('click', function(){
         if($('#'+this.id+'_panel:visible').length)
         {
            $('#'+this.id+'_panel').animate({top:-$('#'+this.id+'_panel').outerHeight()}, 600, function(){$(this).hide()});
            $('#overlay_panel').hide();
            return;
         }
         
         //��һ�μ���
         if($('#'+this.id+'_panel').html() == "")
         {
            $('#'+this.id+'_panel').html('<iframe id="portal_iframe" name="portal_iframe" allowTransparency= "true" src="" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:100%;"></iframe>');
            $('#'+this.id+'_panel iframe').attr('src', '/general/portal/');
         }

         //���λ��
         $('.over-mask-layer').hide();
         $('#overlay_panel').show();
         $('#'+this.id+'_panel').css('left', ($(document).width()-$('#'+this.id+'_panel').width())/2);
         var top = $('#'+this.id+'_panel').outerHeight() > $('#center').outerHeight() ? 0 : 20;
         $('#'+this.id+'_panel').css({top: -$('#'+this.id+'_panel').outerHeight()});
         $('#'+this.id+'_panel').show().animate({top:top}, 600);

         //��������ͼ����Ϊactive״̬
         $(this).addClass('active');
         window.setTimeout(checkActive, 300, this.id);
      });
   }
   
   function initTheme()
   {
      //���������ʾ
      $('#theme').bind('click', function(){
         if($('#'+this.id+'_panel:visible').length)
         {
            $('#'+this.id+'_panel').animate({top:-$('#'+this.id+'_panel').outerHeight()}, 600, function(){$(this).hide()});
            $('#overlay_panel').hide();
            return;
         }
         
         if($('#'+this.id+'_panel').text() == "")
         {
            var html = '';
            html += '<div class="center" id="theme_slider"></div>';
            html += '<div class="close">';
            html += '<a class="btn-black-a" href="javascript:;" onClick="jQuery(\'#theme\').click();" hidefocus="hidefocus">' + td_lang.global.close + '</a>';
            html += '</div>';
            html += '<div class="bottom"></div>';
            $('#'+this.id+'_panel').html(html);
            
            //��������ͼƬ
            for(var id in themeArray)
            {
               if(themeArray[id].src=="") return; 
               var aobj =   $('<a class="theme_thumb" hidefocus="hidefocus"><img src="'+themeArray[id].src+'" width="107" height="54" /></a>');
               aobj.attr("index",id);
               
               aobj.append("<span>"+themeArray[id].title+"</span>")
               $('#theme_slider').append(aobj);
            }
            $('#theme_slider').append('<div style="clear:both;"></div>');
            
            //�����л�
            $('#theme_slider a.theme_thumb').live("click",function(){
               var index = $(this).attr("index");
               if(ostheme == index){return;}
               setTheme(index);
            });
            
            $("#theme_slider a").hover(
               function(){$(this).addClass("current");},
               function(){$(this).removeClass("current");}
            );
            
            CheckBkImg('#'+this.id+'_panel div, #'+this.id+'_panel a');
         }

         //���λ��
         $('.over-mask-layer').hide();
         $('#overlay_panel').show();
         //$('#'+this.id+'_panel').css('left', ($(document).width()-$('#'+this.id+'_panel').width())/2);
         var top = $('#'+this.id+'_panel').outerHeight() > $('#center').outerHeight() ? -10 : 0;
         $('#'+this.id+'_panel').css({top: -$('#'+this.id+'_panel').outerHeight()});
         $('#'+this.id+'_panel').show().animate({top:top}, 600);
         
         //��������ͼ����Ϊactive״̬
         $(this).addClass('active');
         window.setTimeout(checkActive, 300, this.id);
         
      });
   }

   function initPersonInfo()
   {
      //����������¼�
      $('#person_info').bind('click', function(){
         window.setTimeout(function(){
            $().addTab('11', func_array["f11"][0], func_array["f11"][1], func_array["f11"][3]);
         }, 1);   //�ӳ�1������ܴ�OA���鴰�ڴ򿪿�������ǩ����ֵ�����
         
      });
   }
   
   function initLogout()
   {
      //ע��
       $('#logout').bind('click', function(){
         logout();
         return false;
      });
   }
   
   function initHideTopbar()
   {
      //����topbar�¼�
      $('#hide_topbar').bind('click', function(){
         $('#north').slideToggle(300, function(){resizeLayout();});
         $(this).toggleClass('up');

         var hidden = $(this).attr('class').indexOf('up') >= 0;
         $.cookie('hideTopbar', (hidden ? '1' : null), {expires:1000, path:'/'});
      });

      if($.cookie('hideTopbar') == '1')
         $('#hide_topbar').triggerHandler('click');
   }

   function initSearch()
   {
      //������Ļس��¼�
      $('#keyword').keyup(function(event){
         if(this.value != "" && $('#search_clear:visible').length <= 0)
            $('#search_clear').show();
         else if(this.value == "")
            $('#search_clear').hide();
      });
      
      $("#keyword").autocomplete({
         source:'search.php',
         select:function(event, ui){
			//������ʱ,�����´򿪴��ڱ�ԭ���ڸ��ǵ����
			setTimeout(function() {
				view_user(ui.item.id)
			}, 0);
         }
      });
   }

   function initSmsbox()
   {
      //���΢Ѷ�䵯���������Ӧ���
      $('#smsbox').click(function(){
         if($(this).attr('class').indexOf('active') >= 0)
         {
            window.clearInterval(smsbox_close_countdown);
            window.clearTimeout(smsbox_close_timer);
            $('#smsbox_panel').fadeOut(($.browser.msie ? 1 : 300));
            $(this).removeClass('active');
         }
         else
         {
            $('#org_panel').hide();
            $('#smsbox_panel').fadeIn(($.browser.msie ? 1 : 600), function(){$(this).css('top', $('#south').offset().top-$(this).height()+3+'px');});
            $(this).addClass('active');
            window.setTimeout(checkActive, 300, this.id);
            
            if(bSmsPriv)
               LoadSms();
         }
      });

      //�رհ�ť
      var closeBtn = $('#smsbox_panel > .head > .head-center > .head-close');
      closeBtn.hover(
         function(){$(this).addClass('head-close-active');},
         function(){$(this).removeClass('head-close-active');}
      );
      closeBtn.click(function(){
         $('#smsbox').triggerHandler('click');
      });

      if(!bSmsPriv)
         return;
         
      //����΢Ѷ��ʾ
      $('#smsbox_tips').html($('#no_sms').html()).show();
      $('#smsbox_tips').bind('_show', function(){
         window.clearInterval(smsbox_close_countdown);
         window.clearTimeout(smsbox_close_timer);
         
         smsbox_close_countdown = window.setInterval(function(){
               var seconds = parseInt($('#smsbox_close_countdown').text());
               if(seconds > 0)
               {
                  $('#smsbox_close_countdown').text(seconds-1);
               }
               else
               {
                  window.clearInterval(smsbox_close_countdown);
                  $('#smsbox_close_countdown').text(smsbox_close_timeout);
               }
            }, 1000);
         
         smsbox_close_timer = window.setTimeout(function(){
               if($('#smsbox_tips:visible').length > 0)
                  $('#smsbox').triggerHandler('click');
            }, smsbox_close_timeout*1000);
      });

      //������ť
      $('#smsbox_scroll_up,#smsbox_scroll_down').hover(
         function(){$(this).addClass('active');},
         function(){$(this).removeClass('active');}
      );
      $('#smsbox_scroll_up').click(function(){
         var listContainer = $('#smsbox_list_container');
         var blockHeight = $('div.list-block:first', listContainer).outerHeight();
         listContainer.animate({scrollTop: listContainer.scrollTop()-blockHeight*3}, 300);
      });
      $('#smsbox_scroll_down').click(function(){
         var listContainer = $('#smsbox_list_container');
         var blockHeight = $('div.list-block:first', listContainer).outerHeight();
         listContainer.animate({scrollTop: listContainer.scrollTop()+blockHeight*3}, 300);
      });

      //�б��������¼�
      $("#smsbox_list_container").mousewheel(function(){
         //$('#smsbox_list_container').scrollTop($('#smsbox_list_container').scrollTop() - this.D);
         $('#smsbox_list_container').stop().animate({'scrollTop':($('#smsbox_list_container').scrollTop() - this.D)}, 300);
      });
      
      //�б����ݱ仯
      $('#smsbox_list_container').bind('_change', function(){
         $('#smsbox_scroll_up,#smsbox_scroll_down').toggle($(this).outerHeight() < $(this).attr('scrollHeight'));
         if(newSmsArray.length > 0)
         {
            $('#smsbox_tips').hide();
            $('#no_sms').hide()
            $('#smsbox_list_container > div.list-block:first').trigger('click');
         }
         else
         {
            $('#smsbox_tips').html($('#no_sms').html()).show(0, function(){$(this).triggerHandler('_show');});
         }
      });

      //�б�hoverЧ��
      var listBlocks = $('#smsbox_list_container > div.list-block');
      listBlocks.live('mouseenter', function(){$(this).addClass('list-block-hover');});
      listBlocks.live('mouseleave', function(){$(this).removeClass('list-block-hover');});
		   
      //�б�click�¼�
      listBlocks.live('click', function(){
         if($(this).attr('class').indexOf('list-block-active') > 0) return;
         $('#smsbox_list_container > div.list-block').removeClass('list-block-active');
         $(this).addClass('list-block-active');
         $('table', this).removeClass('unread');
			
         var id = $(this).attr('group_id');
         var html = '';
         selectedRecvSmsIdStr = selectedSendSmsIdStr = '';
         for(var i=0; i< newSmsArray.length; i++)
         {
            if(!((newSmsArray[i].from_id == id && newSmsArray[i].to_id == loginUser.uid) || (newSmsArray[i].from_id == loginUser.uid && newSmsArray[i].to_id == id)))
               continue;

            //���յ�΢Ѷ
            if(newSmsArray[i].receive)
               selectedRecvSmsIdStr += newSmsArray[i].sms_id + ',';
            else
               selectedSendSmsIdStr += newSmsArray[i].sms_id + ',';
            
            newSmsArray[i].unread = 0;
            var name = newSmsArray[i].from_name;
            var time = newSmsArray[i].send_time.indexOf(' ') > 0 ? newSmsArray[i].send_time : newSmsArray[i].send_time;
            var from_type_name = newSmsArray[i].from_type_name;
            
            html += CreateMsgBlock({"sms_id":newSmsArray[i].sms_id, "class":(newSmsArray[i].receive ? "from" : "to"), "user":newSmsArray[i].from_id,"type_id": newSmsArray[i].type_id ,"from_type_name":from_type_name, "name":name, "time":time, "content":newSmsArray[i].content, "url":newSmsArray[i].url});
         }
         
         
         $('#smsbox_msg_container').html(html);
         window.setTimeout(function(){
            $('#smsbox_msg_container').animate({scrollTop: $('#smsbox_msg_container').attr('scrollHeight')}, 300);
         }, 1);   //�ӳ�1�����ȡ����scrollHeight������ȷ�ģ���ֵ�����
         
      });
      
      //ȫ������
      $('#smsbox_read_all').click(function(){
         var array = GetSmsIds();
         RemoveSms(array.recv, array.send, 0);
      });

      //ȫ��ɾ��
      $('#smsbox_delete_all').click(function(){
         var array = GetSmsIds();
         RemoveSms(array.recv, array.send, 1);
      });

      //ȫ������
      $('#smsbox_detail_all').click(function(){
         var sms_id_str = '';
         var sms_id_str0 = '';
         var nav_mail_url = '';
         for(var i=0; i< newSmsArray.length; i++)
         {
            if(newSmsArray[i].receive != '1')
               continue;
         
            nav_mail_url = newSmsArray[i].url;
            sms_id_str += newSmsArray[i].sms_id + ',';
            if(newSmsArray[i].type_id == '0')
               sms_id_str0 += newSmsArray[i].sms_id + ',';
         }
         newSmsArray = [];
         FormatSms();
         jQuery('#smsbox_msg_container').html('');
         openURL('', '', '/module/nav/?SMS_ID_STR='+sms_id_str+'&SMS_ID_STR0='+sms_id_str0+'&NAV_MAIN_URL='+encodeURIComponent(nav_mail_url), '1');
         selectedRecvSmsIdStr = selectedSendSmsIdStr = '';
      });
      
      
      //����
      $('#smsbox_toolbar_read').click(function(){
         var lis = $('#smsbox_list_container > div.list-block');
         var acliindex = lis.index($('div.list-block-active'));
         RemoveSms(selectedRecvSmsIdStr, selectedSendSmsIdStr, 0 ,acliindex);
      });

      //ɾ��
      $('#smsbox_toolbar_delete').click(function(){
         var lis = $('#smsbox_list_container > div.list-block');
         var acliindex = lis.index($('div.list-block-active'));
         RemoveSms(selectedRecvSmsIdStr, selectedSendSmsIdStr, 1,acliindex);
      });

      //���ݿ�hoverЧ��
      var msgBlocks = $('#smsbox_msg_container > div.msg-block');
      msgBlocks.live('mouseenter', function(){$(this).addClass('msg-hover');});
      msgBlocks.live('mouseleave', function(){$(this).removeClass('msg-hover');});

      //���ݿ�click�¼�
      msgBlocks.live('click', function(){
         $('#smsbox_msg_container > div.msg-block').removeClass('msg-active');
         $(this).addClass('msg-active');
      });

      //�ظ��¼�
      $('.head > .operation > a.reply', msgBlocks).live('click', function(){
         $('#smsbox_textarea').trigger('focus');
      });
      
      //�鿴�����¼�
      $('.head > .operation > a.detail', msgBlocks).live('click', function(){
         var sms_id = $(this).attr('sms_id');
         var url = $(this).attr('url');
         RemoveSms(sms_id, '', 0);
         openURL('', '', url, '1');
      });
      
      //���ٻظ�
      $('#smsbox_rapid_reply').change(function(){
         if(this.selectedIndex == 0)
            return;
         $('#smsbox_textarea').val(this.options[this.selectedIndex].text)
      });
      
      //�����Ctrl + Enter�¼�
      $('#smsbox_textarea').keypress(function(event){
         if(event.keyCode == 10 || event.ctrlKey && event.which == 13)
            $('#smsbox_send_msg').triggerHandler('click');
      });

      //����
      $('#smsbox_textarea').bind('focus', function(){
         $(this.parentNode).addClass('center-reply-focus');
      });
      $('#smsbox_textarea').bind('blur', function(){
         $(this.parentNode).removeClass('center-reply-focus');
      });
      
      $('#smsbox_send_msg').click(function(){
         var msg = $('#smsbox_textarea').val();
         if(!msg) return;

         var user_id = $('#smsbox_msg_container > div.msg-active:first').attr('user') || $('#smsbox_list_container > div.list-block-active:first').attr('user');
         if(!user_id)
         {
            alert(td_lang.inc.msg_22);//'��ѡ�����û�'
            return;
         }

         var reg = /\n/g;
         var content = msg.replace(reg,"<br />");
         var date = new Date();
         var html = CreateMsgBlock({"sms_id":"send_"+(maxSendSmsId++), "class":"to", "user":loginUser.uid, "name":loginUser.user_name, "time":date.toTimeString().substr(0,5), "content":content});
         $('#smsbox_msg_container').append(html);
         window.setTimeout(function(){
            $('#smsbox_msg_container').animate({scrollTop: $('#smsbox_msg_container').attr('scrollHeight')}, 300);
         }, 1);
         
         newSmsArray[newSmsArray.length] = {sms_id:"",to_id:user_id,from_id:loginUser.uid,from_name:loginUser.user_name,type_id:"0",type_name:"����΢Ѷ",send_time:date.toTimeString().substr(0,5),unread:0,content:content,url:"",receive:0};
         //newSmsArray = $.merge(array, newSmsArray);

         jQuery.ajax({
            type: 'POST',
            url: 'status_bar/msg_send.php',
            data: {'TO_UID':user_id, 'CONTENT':msg, 'charset':'utf-8'},
            dataType: 'text',
            success: function(data){
               $('#smsbox_textarea').val("");
            },
            error: function (request, textStatus, errorThrown){
            	var msg1 = sprintf(td_lang.inc.msg_108,textStatus);
               alert(msg1);
            }
         });

         $('#smsbox_textarea').trigger('focus');
      });
   }
	
	//lp �������ѳ�ʼ���¼�
	function initNocbox(){
		
		//�󶨴���������
      var openBtn = $('#nocbox');
      openBtn.click(function(){
      	var nocboxPanel = $('#new_noc_panel').css("display");
      	nocboxPanel == "none" ? noc_mon() : CloseNoc();	
      });
      
      //��ť�󶨹ر��¼�
      var closeBtn = $('#new_noc_panel > #new_noc_title > span.noc_iterm_close');
		closeBtn.click(function(){
         CloseNoc();
      });
      
		//���ݿ�hoverЧ��
	 	var nocBlocks = $('#new_noc_list > div.noc_iterm > ul.noc_iterm_data > li');
	   nocBlocks.live('mouseenter', function(){$(this).addClass('noc_iterm_hover');});
	   nocBlocks.live('mouseleave', function(){$(this).removeClass('noc_iterm_hover');});
		
		//����鿴����
	   $('#new_noc_list > div.noc_iterm > ul.noc_iterm_data > li').live('click', function(){
	      var url = $(this).attr('url');
	      var sms_id = $(this).attr('sms_id');
	      var type_id = $(this).attr('type_id');
         RemoveNoc($(this),sms_id, 0);
         if(url!=""){
        		openURL('', '', url, '1');
      	}
	   });
	   
	   $('#new_noc_list > div.noc_iterm > ul.noc_iterm_data > li > a').click(function(e){
	      e.stopPropagation();   
	   })
	   
	   //����鿴��ʷ��¼
	   $('#check_remind_histroy').live('click', function(){
         openURL('',td_lang.inc.msg_23, "sms/remind_center");   //'����������ʷ��¼'
	   });
	   
	   //���ȫ������
	   $('#ViewAllNoc').live('click',function(){
	   	var idstr = get_noc_idstr();
	   	jQuery.ajax({
		      type: 'POST',
		      url: 'status_bar/sms_submit.php',
		      data: {'SMS_ID': idstr},
		      cache: false,
		      success: function(){
				   jQuery('#new_noc_list').empty();
				   var datanum = get_noc_num();
	            jQuery("#new_noc_panel > #new_noc_title > .noc_iterm_num >span").html(datanum);
	            jQuery("#new_noc_panel > .button").find("#ViewAllNoc,#ViewDetail").hide();
	            CloseNoc();
				},
		      error: function(request, textStatus, errorThrown){
		      	jQuery('#new_noc_list').hide();
		         jQuery('#nocbox_tips').html('<div class="error">'+td_lang.inc.msg_24+'(' + request.status + ')��' + textStatus + '</div>').show();//����ʧ��
		      }
		   });
	   });
	   
	   //���ȫ������
	   $('#ViewDetail').live('click',function(){
	   	var idstr = firsturl = separator = "";
	   	var idobj = $('#new_noc_list > .noc_iterm > .noc_iterm_data > li');
	   	var readobj = $('#new_noc_list > div.noc_iterm > .noc_iterm_title > .noc_iterm_read');
	   	var idstr_all = get_noc_idstr();
	   	idobj.each(function(){
	   		url = $(this).attr("url");
	   		sms_id = $(this).attr("sms_id");
	   		if(url!="" && firsturl==""){
	   			firsturl = url;
	   		}
	   		if(url!=""){
	   			idstr += separator + jQuery(this).attr("sms_id");
	   			separator = ",";
	   		}
	   	});
	   	
	   	window.open('/module/nav/?SMS_ID_STR='+idstr+'&NAV_MAIN_URL='+encodeURIComponent(firsturl));
	   	RemoveNoc(readobj,idstr_all, 0);
	   	CloseNoc();
	   });
	   
	   $('#CloseBtn').live('click',function(){
	      CloseNoc();      
	   });
	   
	   //������ͣ�����ȫ������
	   $('#new_noc_list > div.noc_iterm > .noc_iterm_title > .noc_iterm_read').live('click',function(){
	   	var idstr = idstr_all = firsturl = "";
	   	var separator = ",";
	   	var type_id = $(this).attr('type_id');
	   	var idstr_all = get_noc_idstr(type_id);
	   	RemoveNoc($(this),idstr_all, 0);
	   	var idobj = jQuery("#new_noc_panel > #new_noc_list > .noc_iterm_" + type_id + " > .noc_iterm_data > li");
	   	idobj.each(function(){
	   		url = $(this).attr("url");
	   		sms_id = $(this).attr("sms_id");
	   		if(url!="" && firsturl==""){
	   			firsturl = url;
	   		}
	   		if(url!=""){
	   			idstr += separator + jQuery(this).attr("sms_id");
	   			separator = ",";
	   		}
	   	});
	   	url = '/module/nav/?SMS_ID_STR='+idstr+'&NAV_MAIN_URL='+encodeURIComponent(firsturl);
        	openURL('', '', url, '1');
	   });
	   
	   //������ͣ�����ȫ������
	   $('#new_noc_list > div.noc_iterm > .noc_iterm_title > .noc_iterm_cancel').live('click',function(){
	   	var type_id = $(this).attr('type_id');
	   	var idstr_all = get_noc_idstr(type_id);
	   	RemoveNoc($(this),idstr_all, 0);
	   });
	   
	};
	
   function initOrg()
   {
      //��֯����������Ա����ʾ�������¼�
      $('#org_panel').bind('_show', function(){
         if($('#user_online:visible').length > 0)
         {
            $('#user_online').triggerHandler('_show');
            $('#user_all').triggerHandler('_hide');
         }
         else
         {
            $('#user_online').triggerHandler('_hide');
            $('#user_all').triggerHandler('_show');
         }
      });
      $('#org_panel').bind('_hide', function(){
         if(timer_online_tree_ref)
            window.clearInterval(timer_online_tree_ref);
         if($('#user_online:hidden').length > 0)
            $('#user_online').triggerHandler('_hide');
      });

      //�����֯�����������Ӧ���
      $('#org').click(function(){
         if($(this).attr('class').indexOf('active') >= 0)
         {
            $('#org_panel').fadeOut(($.browser.msie ? 1 : 300));
            $(this).removeClass('active');
            $('#org_panel').triggerHandler('_hide');
         }
         else
         {
            if($('#org_panel').text() == "")
            {
               var html = '<div class="head">';
               html += '<div class="left"><a href="javascript:;" onClick="ActiveUserTab(this)" id="user_online_tab" class="active" hidefocus="hidefocus"><span><img src="/images/user_list3/user.png" align="absMiddle"> ' + td_lang.inc.msg_128 + '</span></a></div>';
               html += '<div class="right"><a href="javascript:;" onClick="ActiveUserTab(this)" hidefocus="hidefocus"><span><img src="/images/user_list3/group.png" align="absMiddle"> ' + td_lang.inc.msg_129 + '</span></a></div>';
               html += '</div>';
               html += '<div class="center">';
               html += '<div class="top">';
               html += '<div id="user_online"><div id="orgTree0"></div></div>';
               html += '<div id="user_all" style="display:none;"><div id="orgTree1"></div></div>';
               html += '</div>';
               html += '<div class="bottom">';
               html += '<a class="btn-white-b" href="javascript:;" onClick="SearchUser();" hidefocus="hidefocus">' + td_lang.inc.msg_41 + '</a>&nbsp;&nbsp;';
               html += '<a class="btn-white-b" href="javascript:;" onClick="jQuery(\'#org\').click();" hidefocus="hidefocus">' + td_lang.global.close + '</a>';
               html += '</div>';
               html += '</div>';
               html += '<div class="foot">';
               html += '<div class="left"></div>';
               html += '<div class="right"></div>';
               html += '</div>';
               html += '<div class="corner"></div>';
               $('#org_panel').html(html);
               
               //������Ա
               $('#user_online').bind('_show', function(){
                  if(timer_online_tree_ref)
                     window.clearInterval(timer_online_tree_ref);
         
                  if($("#orgTree0").html() == "")
                  {
                     $("#orgTree0").html('<iframe id="orgTree0_iframe" allowTransparency= "true" src="" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:98%;"></iframe>');
                     $("#orgTree0 iframe").attr('src', ("/inc/user_tree.php?TREE_ID=orgTree0&SHOW_IP=" + show_ip + "&SHOW_BUTTON=" + show_button + "&JSON_URL=" + jsonURL0));
                  }
                  else if((new Date()).getTime() - timeLastLoadOnline > monInterval.online*5*1000)
                  {
                     frames['orgTree0_iframe'].tree.reload();
                  }
                  
                  timer_online_tree_ref = window.setInterval(function(){frames['orgTree0_iframe'].tree.reload();}, monInterval.online*5*1000);
                  timeLastLoadOnline = (new Date()).getTime();
               });
               $('#user_online').bind('_hide', function(){
                  if(timer_online_tree_ref)
                     window.clearInterval(timer_online_tree_ref);
               });
         
               //ȫ����Ա��һ����ʾ����ȫ����Ա��
               $('#user_all').bind('_show', function(){
                  if($("#orgTree1").html() == "")
                  {
                     $("#orgTree1").html('<iframe id="orgTree1_iframe" allowTransparency= "true" src="" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:98%;"></iframe>');
                     $("#orgTree1 iframe").attr('src', ("/inc/user_tree.php?TREE_ID=orgTree1&SHOW_IP=" + show_ip + "&SHOW_BUTTON=" + show_button + "&JSON_URL=" + jsonURL1));
                  }
               });
               
               CheckBkImg('#'+this.id+'_panel div, #'+this.id+'_panel a');
            }
            
            $('#smsbox_panel').hide();
            $('#org_panel').fadeIn(($.browser.msie ? 1 : 600), function(){$(this).css('top', $('#south').offset().top-$(this).outerHeight()+3+'px');$('#org_panel').triggerHandler('_show');});
            $(this).addClass('active');
            window.setTimeout(checkActive, 300, this.id);
         }
      });
   }
   
   function initDatetime()
   {
      $('#date,#mdate').click(function(){
         $().addTab('dt_date', td_lang.inc.msg_25, "/general/calendar2/", true);//"������"
      });
      $('#time_area').click(function(){
         $().addTab('dt_time_area', td_lang.inc.msg_26, "/general/world_time/", true);//"����ʱ��"
      });
   }

   //����resize�¼�
   //if(!isTouchDevice())
      $(window).resize(function(){resizeLayout();});
   //else
   //   $(window).bind('orientationchange', function(){resizeLayout();});
   
   $(document).ready(function($){
      $('#loading').remove();

      //iPad �ȴ����豸
      if(isTouchDevice())
      {
         $('body').addClass('mobile-body');
         $('#center').addClass('mobile-center');
      }

      //����ڴ�С
      resizeLayout();

      //��ʼ�˵�
      initStartMenu();

      //��ǩ��
      initTabs();

      //��������
      initTaskCenter();
      
      //�Ż��л�
      initPortal();
      
      //�����л�
      initTheme();

      //��������¼�
      initPersonInfo();
      
      //ע���¼�
      initLogout();

      //����topbar�¼�
      initHideTopbar();

      //������
      initSearch();
      
      //IE6��ʾ��
      initIE6Tips();

      //��֯����������Ա
      initOrg();

      //΢Ѷ��
      initSmsbox();
      
      //��������
      initNocbox();

      //�����Ż�
      for(var i=0; i < portalLoadArray.length; i++)
      {
         $().addTab('portal_'+portalArray[portalLoadArray[i]].id, portalArray[portalLoadArray[i]].title, portalArray[portalLoadArray[i]].url, portalArray[portalLoadArray[i]].closable, (i==0));
      }

      //����ʱ�������
      mdate();
      timeview();
      if(bInitWeather)
      {
         InitProvince(weatherCity);
         if($('#weather').text() == "")
            $('#weather').html(sprintf('<a href="javascript:GetWeather();">%s</a>', td_lang.inc.msg_141));
      }
      initDatetime();

      //��΢Ѷ��������Ա��غ�״̬�����ֹ���
      timer_sms_mon = window.setTimeout(sms_mon, 3000);
      window.setTimeout(online_mon, monInterval.online*1000);
      window.setInterval(StatusTextScroll, statusTextScroll*1000);
      
      //���overlay_panelʱ�Զ��ջ��Ż������л���������
      $("#overlay_panel").click(function(){
         if($("#task_center_panel:visible").length){
            $("#task_center").trigger("click");   
         }
         else if($("#portal_panel:visible").length){
            $("#portal").trigger("click");   
         }
         else if($("#theme_panel:visible").length){
            $("#theme").trigger("click");   
         }
      });
      
      //����֪ͨ����¼�
      if($('#notify_panel').length > 0)
      {
         $('#overlay').show();
         $('#notify_panel').show();
         $('#notify_panel').css('left', ($(window).width()-$('#notify_panel').width())/2);
         
         $('#notify_panel .btn-ok').click(function(){
            $.cookie($(this).attr("cookie_name"), $(this).attr("cookie_value"), {expires:1000, path:'/'});
            $('#notify_panel .btn-close').click();
         });
         $('#notify_panel .btn-close').click(function(){
            $('#overlay').hide();
            $('#notify_panel').hide();
         });
         $('#notify_panel .head-close').click(function(){
            $('#notify_panel .btn-close').click();
         });
      }
      
      CheckBkImg('div,a,ul,li,span');
   });
})(jQuery);

function CheckBkImg(selector)
{
   jQuery(selector).each(function(){
      jQuery(this).css('background-image');
   });
}

//�޸�setTimeout bug��ʹ��window.setTimeout����
if(!+'\v1') {
    (function(f){
        window.setTimeout =f(window.setTimeout);
        window.setInterval =f(window.setInterval);
    })(function(f){
        return function(c,t){
            var a=[].slice.call(arguments,2);
            return f(function(){
                c.apply(this,a)},t)
            }
    });
}

var $ = function(id) {return document.getElementById(id);};

function HTML2Text(html)
{
   var div = document.createElement('div');
   div.innerHTML = html;
   return div.innerText;
}

function Text2Object(data)
{
   try{
      var func = new Function("return " + data);
      return func();
   }
   catch(ex){
      return '<b>' + ex.description + '</b><br /><br />' + HTML2Text(data) + '';
   }
}

function createTab(id, name, code, open_window)
{
   jQuery('#overlay_startmenu').triggerHandler('click');
   jQuery('#funcbar_left > div.second-tabs-container').hide();
   if(code.indexOf('http://') == 0 || code.indexOf('https://') == 0 || code.indexOf('ftp://') == 0)
   {
      openURL(id, name, code, open_window);
      return;
   }
   else if(code.indexOf('file://') == 0)
   {
      winexe(name, code.substr(7));
      return;
   }
   var url = "";
   if(id >= 10000 && id <= 14999)
      url = '/fis/' + code
   else if(id >= 15000 && id <= 15499)
      url = '/hr/' + code
   else if(id >= 650 && id <= 1000 || code.length > 4 && code.substr(code.length-4).toLowerCase() == ".jsp")
      url = '/app/' + code
   else
      url = '/general/' + code

   if(url.indexOf(".") < 0 && url.indexOf("?") < 0  && url.indexOf("#") < 0 && url.substring(url.length-1) != "/")
      url += "/";

   //�´��ڴ򿪻��OAģ��
   if(open_window == "1" || url.indexOf('/general/') != 0)
   {
      openURL(id, name, url, open_window);
      return;
   }

   var url2 = 'http://www.tongda2000.com' + url;
   var parse = url2.match(/^(([a-z]+):\/\/)?([^\/\?#]+)\/*([^\?#]*)\??([^#]*)#?(\w*)$/i); //*/
   var path = parse[4];
   var query = parse[5];

   //�˵���ֱַ�Ӷ���Ϊ�����ļ���·�����ݲ����ģ��
   var pos = path.lastIndexOf('/');
   if(pos > 0 && path.substr(pos+1).indexOf('.') > 0 || query != "")
   {
      openURL(id, name, url, open_window);
      return;
   }

   jQuery.ajax({
      type: 'GET',
      url: '/inc/second_tabs.php',
      data: {'FUNC_CODE':escape(url)},
      dataType: 'text',
      success: function(data){
         var array = Text2Object(data);
         if(typeof(array) != "object" || typeof(array.length) != "number" || array.length <= 0)
         {
            openURL(id, name, url, open_window);
            return;
         }

         var index = 0;
         var html = '';
         for(var i=0; i< array.length; i++)
         {
            index = (array[i].active == "1") ? i : index;//Ĭ�ϴ򿪵�һ����ǩҳ��ַ
            var className = (array[i].active == "1") ? ' class="active"' : '';
            var href = (url.substr(url.length-1) != "/" && array[i].href.substr(0,1) != "/") ? (url + '/' + array[i].href) : (url + array[i].href);
            html += '<a title="' + array[i].title + '" href="javascript:gotoURL(\'' + id + '\',\'' + href + '\');"' + className + ' hidefocus="hidefocus"><span>' + array[i].text + '</span></a>';
         }

         html = '<div id="second_tabs_' + id + '" class="second-tabs-container">' + html +'</div>';
         jQuery(html).appendTo('#funcbar_left');

         var secondTabs = jQuery('#second_tabs_' + id);
         jQuery('a', secondTabs).click(function(){
            jQuery('a.active', secondTabs).removeClass('active');
            jQuery(this).addClass('active');
         });

         if(jQuery('a.active', secondTabs).length <= 0)
            jQuery('a:first', secondTabs).addClass('active');
         jQuery('a:last', secondTabs).addClass('last');

         url += url.substr(url.length-1) != "/" ? ("/"+array[index].href) : array[index].href;
         jQuery().addTab(id, name, url, true);
      },
      error: function (request, textStatus, errorThrown){
         openURL(id, name, url, open_window);
      }
   });
   jQuery(document).trigger('click');
}

function closeTab(id)
{
   id = (typeof(id) != 'string') ? jQuery().getSelected() : id;
   jQuery().closeTab(id);
}

function IframeLoaded(id)
{
   var iframe = window.frames['tabs_' + id + '_iframe'];
   if(iframe && $('tabs_link_' + id) && $('tabs_link_' + id).innerText == '')
   {
      $('tabs_link_' + id).innerText = !iframe.document.title ?  td_lang.inc.msg_27: iframe.document.title;//"�ޱ���"
   }
   /*
   if(isTouchDevice())
   {
      jQuery(window).triggerHandler('orientationchange');
   }
   */
}

function openURL(id, name, url, open_window, width, height, left, top)
{
   id = !id ? ('w' + (nextTabId++)) : id;
   if(open_window != "1")
   {
      window.setTimeout(function(){jQuery().addTab(id, name, url, true)}, 1);
   }
   else
   {
      width = typeof(width) == "undefined" ? 780 : width;
      height = typeof(height) == "undefined" ? 550 : height;
      left = typeof(left) == "undefined" ? (screen.availWidth-width)/2 : left;
      top = typeof(top) == "undefined" ? (screen.availHeight-height)/2-30 : top;
      window.open(url, id, "height="+height+",width="+width+",status=0,toolbar=no,menubar=yes,location=no,scrollbars=yes,top="+top+",left="+left+",resizable=yes");
   }
   jQuery(document).trigger('click');
}

function gotoURL(id, url)
{
   $('tabs_'+id+"_iframe").src = url;
}

function BlinkTabs(id)
{
}

function getEvent() //ͬʱ����ie��ff��д��
{
    if(document.all)  return window.event;
    func=getEvent.caller;
    while(func!=null){
        var arg0=func.arguments[0];
        if(arg0)
        {
          if((arg0.constructor==Event || arg0.constructor ==MouseEvent) || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation))
          {
          return arg0;
          }
        }
        func=func.caller;
    }
    return null;
}

function mdate()
{
   var solarTerm=sTerm(OA_TIME.getFullYear(), OA_TIME.getMonth(), OA_TIME.getDate());
   if(solarTerm != "")
      $('mdate').innerHTML = solarTerm;
}
function timeview()
{
   $('time_area').innerHTML = OA_TIME.toTimeString().substr(0,5);
   OA_TIME.setSeconds(OA_TIME.getSeconds()+1);
   window.setTimeout( timeview, 1000);
}

function GetWeather(beUpdate)
{
   var WEATHER_CITY = $('w_county').options[$('w_county').selectedIndex].value;
   if(WEATHER_CITY.length != 6)
   {
      alert(td_lang.inc.msg_29);//"��ѡ�����"
      return;
   }

   $('weather').innerHTML='<div class="loading_blue_16">'+td_lang.inc.msg_30+'</div>';//���ڼ��أ����Ժ򡭡�
   jQuery.ajax({
      type: 'GET',
      url: '/inc/weather.php',
      data: {'WEATHER_CITY':WEATHER_CITY, 'UPDATE':beUpdate},
      dataType: 'text',
      success: function(data)
               {
                  if(data.substr(0, 6) == "error:")
                     $('weather').innerHTML = data.substr(6)+" <a href=\"javascript:GetWeather();\">"+td_lang.global.refresh_1+"</a> <a href=\"#\" onclick=\"$('area_select').style.display='block';$('weather').style.display='none';\">"+td_lang.inc.msg_33+"</a>";
                  else
                     $('weather').innerHTML = data;
               },
      error: function (request, textStatus, errorThrown)
             {
                $('weather').innerHTML = td_lang.inc.msg_31 + request.status + " <a href=\"javascript:GetWeather();\">"+td_lang.inc.msg_32+"</a> <a href=\"#\" onclick=\"$('area_select').style.display='block';$('weather').style.display='none';\">"+td_lang.inc.msg_33+"</a>";
             }
   });

   $('area_select').style.display='none';
   $('weather').style.display='block';
}

var relogin = 0;
function logout()
{
   var msg = sprintf(td_lang.inc.msg_109, loginUser.user_name) + "\n" + sprintf(td_lang.inc.msg_110, logoutText + "\n\n");
   if(window.confirm(msg))
   {
     relogin=1;
     window.location="relogin.php";
   }
}

function exit()
{
   var msg = sprintf(td_lang.inc.msg_109, loginUser.user_name) + "\n" + sprintf(td_lang.inc.msg_111, logoutText + "\n\n");
   if(window.confirm(msg))
   {
      var event = getEvent();
      if(ispirit != "1" || jQuery(document.body).width() - event.clientX < 50 || event.altKey || event.ctrlKey)
      {
         if(jQuery.browser.msie)
            jQuery.get('relogin.php');
         else
            window.location="relogin.php";
      }
      window.close();
   }
}

//-- ΢Ѷ���Ѻ�΢Ѷ����� --
function ViewNewSms()
{
   var pannelActive = $('smsbox').className.indexOf('active') >= 0;
   if(!pannelActive)
      jQuery('#smsbox').click();
   else
      LoadSms();

   CloseRemind();
   ResetTitle();
}

var documentTitle = document.title;
var blinkTitleInterval = null;
var blinkTitleType = '';
function sms_mon()
{
   if(!timer_sms_mon)
      window.clearTimeout(timer_sms_mon);
   timer_sms_mon = window.setTimeout(sms_mon, monInterval.sms*1000);
   
   jQuery.ajax({
      type: 'GET',
      url: '../attachment/new_sms/' + loginUser.uid + '.sms',
      data: {'now': new Date().getTime()},
      success: function(data){
         if(data.indexOf("1") >= 0)
         {
            $('new_sms_sound').innerHTML = newSmsSoundHtml;
            if(timer_sms_mon)
            {
               window.clearTimeout(timer_sms_mon);
               timer_sms_mon = null;
            }
            
            if(data.substr(0, 1) == "1")
            {
             	noc_mon();
             	blinkTitleType = 'noc';
            }
            else if(data.substr(1, 1) == "1")
            {
               var wWidth = (window.document.documentElement.clientWidth || window.document.body.clientWidth || window.innerWidth);
               var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
               var left = Math.floor((wWidth - jQuery('#new_sms_panel').outerWidth())/2);
               var top  = Math.floor((wHeight - jQuery('#new_sms_panel').outerHeight())/2) - 100;
   
               jQuery('#new_sms_panel').css({left:left, top:top});
               jQuery('#new_sms_mask').show();
               jQuery('#new_sms_panel').show().focus();
               blinkTitleType = 'sms';
            }
            
            if(data.substr(0, 2)=="11")
               blinkTitleType = 'sms&noc';
               
            blinkTitleInterval = window.setInterval(BlinkTitle, 1000);
         }
      }
   });
}

function noc_mon()
{
  	var wWidth = (window.document.documentElement.clientWidth || window.document.body.clientWidth || window.innerWidth);
 	var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
 	var left = Math.floor((wWidth - jQuery('#new_noc_panel').outerWidth())/2);
  	var top  = Math.floor((wHeight - jQuery('#new_noc_panel').outerHeight())/2) - 100;
  
  	jQuery('#new_noc_panel').css({left:left, top:top});
  
  	//lp ��ʾ�����������
  	jQuery('#new_noc_panel').show().focus();
  
  	//lp ������ȡ���Ѻ���
  	LoadNoc();
}

// lp ��ȡ���������Ϣ
var newNocArray = [];
function LoadNoc(flag)
{
   jQuery('#nocbox_tips').html('<div class="loading">'+td_lang.inc.msg_30+'</div>').show();//���ڼ��أ����Ժ򡭡�
   jQuery('#new_noc_list').hide();
   flag = typeof(flag) == "undefined" ? "1" : "0";
   jQuery.ajax({
      type: 'GET',
      url: 'status_bar/get_noc.php',
      data: {'FLAG': flag},
      dataType: "json",
      cache: false,
      success: function(data){
      	jQuery('#nocbox_tips').empty().hide();
	      if(data){
	      	FormatNoc(data);
	      }else{
      		jQuery("#new_noc_panel").css("padding-bottom","0px");
	      	autocloseNoc();
	      }
      },
      error: function(request, textStatus, errorThrown){
         jQuery('#nocbox_tips').html('<div class="error">'+td_lang.inc.msg_34+'(' + request.status + ')��' + textStatus + '</div>').show();//��ȡ�����������ʧ��
      }
   });
}

//lp ��ʽ���������
function FormatNoc(data)
{
   var html = totalnum ='';
   jQuery('#new_noc_list').empty();
	jQuery.each(data,function(key, item){ 
		if(item.type_id == "") return false;
		
		//ȡ�������ݵ�ǰ2��������ʾ
      var content = decodeURIComponent(item.content);
      var pos = content.indexOf('<br />');
      if(pos >= 0)
      {
         var pos2 = content.indexOf('<br />', pos + 6);
         if(pos2 >= 0)
            content = content.substr(0, pos2);
      }
      
		if(jQuery('#new_noc_list').find('.noc_iterm_'+item.type_id).size()!=0){
			html = '<li id="noc_li_'+item.sms_id+'" sms_id="'+item.sms_id+'" url="'+item.url+'" type_id="'+item.type_id+'">';
				html += '<p class="noc_iterm_info"><span class="noc_iterm_time">'+decodeURIComponent(item.send_time)+'</span>'+decodeURIComponent(item.from_name)+'</p>';
				html += '<p class="noc_iterm_content">'+content+'</p>';
			html += '</li>';
			jQuery('.noc_iterm_'+item.type_id+' > ul').append(html);	
		}else{
			html = '<div class="noc_iterm noc_iterm_'+item.type_id+'">';
			html += '<div class="noc_iterm_title"><span class="noc_iterm_read" type_id="'+item.type_id+'" title='+td_lang.inc.msg_35+'></span><span class="noc_iterm_cancel" type_id="'+item.type_id+'" title='+td_lang.inc.msg_36+'></span>'+ get_sms_type_img(item.type_id) + decodeURIComponent(item.type_name)+'</div>';
			html += '<ul class="noc_iterm_data">';
				html += '<li id="noc_li_'+item.sms_id+'" sms_id="'+item.sms_id+'" url="'+item.url+'" type_id="'+item.type_id+'">';
					html += '<p class="noc_iterm_info"><span class="noc_iterm_time">'+decodeURIComponent(item.send_time)+'</span>'+decodeURIComponent(item.from_name)+'</p>';
					html += '<p class="noc_iterm_content">'+content+'</p>';
				html += '</li>';	 
			html += '</ul>';
			html += '</div>';			
			jQuery('#new_noc_list').append(html);
		}
	});
	
	var num = get_noc_num();
	
	/*if(num == 1){
		var _obj = jQuery('#new_noc_list > div.noc_iterm > ul.noc_iterm_data > li');
		if(_obj.attr("url")!=""){
			_obj.click();
			autocloseNoc();
		}
	}*/
	

	jQuery('#new_noc_list').show(); 
	
	var num = get_noc_num();
	jQuery("#new_noc_panel > #new_noc_title > .noc_iterm_num >span").html(num);
	
	if(num >= 1){
		jQuery('#new_noc_panel').css("padding-bottom","0px");
		jQuery('#new_noc_panel > .button > a').show();
	}
}

//lp �����������ѷ���ͼƬ��ʾ
function get_sms_type_img(sms_type){
	return '<span style="float:left;margin-top:7px;margin-right:5px;"><img src="/images/sms_type' + sms_type +'.gif" /></span>';
}

//lp ����Ŀǰ�ж���������
function get_noc_num(){
	var totalnum = '';
	totalnum = jQuery("#new_noc_panel > #new_noc_list > .noc_iterm > .noc_iterm_data > li").length;
	return totalnum;	
}

//lp ��ʱ������δ���ѵ�����
function get_noc_idstr(type_id){
	var idstr = '';
	var separator = '';
	if(type_id!="" && typeof(type_id)!=="undefined")
	{
		var idsobj =jQuery("#new_noc_panel > #new_noc_list > .noc_iterm_" + type_id + " > .noc_iterm_data > li");
	}else
	{
		var idsobj = jQuery("#new_noc_panel > #new_noc_list > .noc_iterm > .noc_iterm_data > li");
	}	
	jQuery.each(idsobj,function(){
		idstr += separator + jQuery(this).attr("sms_id");
		separator = ",";
	});
	return idstr;
}	

function BlinkTitle()
{
   var t = blinkTitleType;
   var l = '';
   if(t=="sms")
      l = td_lang.inc.msg_37;//"�����µĶ������ѣ�"
   else if(t=="noc")
      l = td_lang.inc.msg_132;//"�����µ��������ѣ�"
   else if(t=="sms&noc")
      l = td_lang.inc.msg_133;//"�����µĶ���Ϣ���������ѣ�"
       
   document.title = document.title == "����������������" ?  l: "����������������";
}

function ResetTitle()
{
   window.clearInterval(blinkTitleInterval);
   document.title = documentTitle;
}

var maxSendSmsId = 0;
var newSmsArray = [];
var selectedRecvSmsIdStr = selectedSendSmsIdStr = "";
function LoadSms(flag)
{
   jQuery('#smsbox_tips').html('<div class="loading">'+td_lang.inc.msg_30+'</div>').show();//���ڼ��أ����Ժ򡭡�
   flag = typeof(flag) == "undefined" ? "1" : "0";
   jQuery.ajax({
      type: 'GET',
      url: 'status_bar/get_msg.php',
      dataType: "json",
      data: {'FLAG': flag},
      success: function(data){
      	var array = data;
         jQuery.each(array,function(key, item){ 
         	item.from_name = decodeURIComponent(item.from_name);
         	item.content = decodeURIComponent(item.content);
         	item.from_type_name = decodeURIComponent(item.from_type_name);
         	item.type_name = decodeURIComponent(item.type_name);
         	item.url = decodeURIComponent(item.url);
         	item.send_time = decodeURIComponent(item.send_time);
         	item.avatar = decodeURIComponent(item.avatar);
         });
         if(typeof(array) != "object" || typeof(array.length) != "number" || array.length < 0)
         {
            jQuery('#smsbox_tips').html('<div class="error">' + array + '</div>').show();
            return;
         }
         else if(array.length == 0)
         {
            jQuery('#smsbox_tips').html(jQuery('#no_sms').html()).show(0, function(){jQuery(this).triggerHandler('_show');});
            return;
         }
         for(var i=0; i< array.length; i++)
         {
            var sms_id = array[i].sms_id;
            var bFound = false;
            for(var j=0; j< newSmsArray.length; j++)
            {
               if(sms_id == newSmsArray[j].sms_id)
               {
                  bFound = true;
                  break;
               }
            }
            
            if(!bFound)
               newSmsArray[newSmsArray.length] = array[i];
         }
         
         FormatSms();

         //2012/12/11 12:51:35 lp ����OA�������µ�΢Ѷ�����滻
         replaceVMDom();
         
         jQuery('#smsbox_list_container > div.list-block:first').css("border-top","0");
         
      },
      error: function(request, textStatus, errorThrown){
         jQuery('#smsbox_tips').html('<div class="error">'+td_lang.inc.msg_38+'(' + request.status + ')��' + textStatus + '</div>').show();//��ȡ����Ϣ���ʧ��
      }
   });
}

function FormatSms(active)
{
   var array = [];
   var count = 0;
   for(var i=newSmsArray.length-1; i >= 0; i--)
   {
      if(newSmsArray[i].receive != '1')
         continue;
     
      var id = newSmsArray[i].from_id;
      if(typeof(array[id]) != "undefined")
      {
         array[id].count++;
         continue;
      }

      count++;
      var name = newSmsArray[i].from_name;
      var time = newSmsArray[i].send_time.indexOf(' ') > 0 ? newSmsArray[i].send_time : newSmsArray[i].send_time;
      if(newSmsArray[i].avatar.indexOf(".")!=-1){
      	var avatar = '/inc/attach_old.php?ATTACHMENT_ID=avatar&ATTACHMENT_NAME='+newSmsArray[i].avatar+'&DIRECT_VIEW=1'; 
      }else{
      	var avatar = '/images/avatar/avatar_'+newSmsArray[i].avatar+'.jpg';	
      }
      var unread = array[id] && array[id].unread ? (array[id].unread || newSmsArray[i].unread) : newSmsArray[i].unread;
      array[id] = {name:name, count:1, avatar:avatar, time:time, content:newSmsArray[i].content, unread:unread};
   }
   
   if(count == 0)
   {
      jQuery('#smsbox_tips').html(jQuery('#no_sms').html()).show(0, function(){jQuery(this).triggerHandler('_show');});
      return;
   }

   var html = '';
   for(var id in array)
   {

      //ȡ΢Ѷ���ݵ�ǰ2��������ʾ
      var content = array[id].content;
      var pos = content.indexOf('<br />');
      if(pos >= 0)
      {
         var pos2 = content.indexOf('<br />', pos + 6);
         if(pos2 >= 0)
            content = content.substr(0, pos2);
      }
      //alert(HTML2Text(content))
      html += '<div class="list-block" group_id="' + id + '" user="' + id + '">';
      html += '   <table class="' + (array[id].unread ? "unread" : "") + '">';
      //html += '      <tr><td class="name">' + array[id].name + '</td><td class="count">' + array[id].count + '</td><td class="time">' + array[id].time + '</td></tr>';
      html += '      <tr><td class="avatar"><img src="' + array[id].avatar + '" width="38" height="38" alt="'+array[id].name+'"/></td><td class="count">' + array[id].name + '<br />' + array[id].time + '</td></tr>';
      //html += '      <tr><td colspan="3" class="msg">' + content + '</td></tr>';
      html += '   </table>';
      html += '</div>';
   }
   jQuery('#smsbox_list_container').html(html);
   jQuery('#smsbox_list_container').triggerHandler('_change');
   jQuery('#smsbox_list_container > div.list-block:first').css("border-top","0");
   
   if(typeof(active) != "undefined")
   {
      if(jQuery('#smsbox_list_container > div.list-block:eq('+active+')').length > 0){
         jQuery('#smsbox_list_container > div.list-block:eq('+active+')').trigger("click");
      }else{
         if(jQuery('#smsbox_list_container > div.list-block:eq('+(active-1)+')').length > 0){
            jQuery('#smsbox_list_container > div.list-block:eq('+(active-1)+')').trigger("click");    
         } 
      }      
   }
   
}

function CreateMsgBlock(msg)
{
   var html = '';
   html += '<div class="msg-block ' + msg["class"] + '" sms_id="' + msg["sms_id"] + '" user="' + msg["user"] + '">';
   html += '   <div class="head">';
   if(msg["from_type_name"] && msg["type_id"]!=0){
      if(msg["type_id"]!=1)
   	   html += '   <div class="name">' + msg["name"] + '&nbsp;' + msg["time"] + ' - '+td_lang.inc.msg_39+ msg["from_type_name"] +'</div>';//����
   	else
   	   html += '   <div class="name">' + msg["name"] + '&nbsp;' + msg["time"] + ' - '+ msg["from_type_name"] +'</div>';	
   }else{
   	html += '   <div class="name">' + msg["name"] + '&nbsp;' + msg["time"] + '</div>';
	}
   if(msg["url"])
   {
      html += '   <div class="operation">';
      html += '      <a href="javascript:;" class="reply" hidefocus="hidefocus">'+td_lang.global.reply+'</a>';//�ظ�
      html += '   </div>';
   }
   html += '   </div>';
   html += '   <div class="msg">' + msg["content"] + '</div>';
   html += '</div>'; 
   return html;
  
}

function RemoveSms(recvIdStr, sendIdStr, del , acliindex)
{
   if(!recvIdStr) return;
   jQuery.ajax({
      type: 'POST',
      url: 'status_bar/sms_submit.php',
      data: {'MSG_ID':recvIdStr, 'DEL':del},
      dataType: 'text',
      success: function(data){
         var array = [];
         for(var i=0; i< newSmsArray.length; i++)
         {
            var id = newSmsArray[i].sms_id;
            if(id == recvIdStr || recvIdStr.indexOf(id+',') == 0 || recvIdStr.indexOf(','+id+',')> 0 ||
               id == sendIdStr || sendIdStr.indexOf(id+',') == 0 || sendIdStr.indexOf(','+id+',')> 0)
               continue;
            
            array[array.length] = newSmsArray[i];
         }
         newSmsArray = array;
         
         var _len = strlen(recvIdStr);
         recvIdStr = recvIdStr.substring(_len-1, _len) == "," ? recvIdStr.substring(0,_len-1) : recvIdStr;

         if(recvIdStr.indexOf(',') > 0) //����
         {
            selectedRecvSmsIdStr = selectedSendSmsIdStr = '';
            FormatSms();
         }
         else //һ��
         {
            jQuery('#smsbox_msg_container > div.msg-block[sms_id="' + recvIdStr + '"]').remove();
            
            if(selectedRecvSmsIdStr.indexOf(recvIdStr+',') == 0)
               selectedRecvSmsIdStr = selectedRecvSmsIdStr.substr(recvIdStr.length+1);
            if(selectedRecvSmsIdStr.indexOf(','+recvIdStr+',') > 0)
               selectedRecvSmsIdStr = selectedRecvSmsIdStr.replace(','+recvIdStr+',', '');
            
            if(jQuery('#smsbox_msg_container > div.msg-block').length == 0)
                  FormatSms(acliindex);   
               
         }
      },
      error: function (request, textStatus, errorThrown){
      	 var msg2 = sprintf(td_lang.inc.msg_112,textStatus);
         alert(msg2);
      }
   });
}

function RemoveNoc(obj,recvIdStr, del){
	if(!recvIdStr) return;
	jQuery.ajax({
      type: 'POST',
      url: 'status_bar/sms_submit.php',
      data: {'SMS_ID':recvIdStr},
      success: function(data){
      		var lis = obj.parents(".noc_iterm").find("ul").find("li").size();
      		if(recvIdStr.indexOf(",")!='-1'){
      			obj.parents(".noc_iterm").remove()
      		}else{
      			lis == 1 ? obj.parents(".noc_iterm").remove() :	obj.remove();
      		}
      		var num = get_noc_num();
      		jQuery("#new_noc_panel > #new_noc_title > .noc_iterm_num >span").html(num);
      		if(num == 0){
      			jQuery("#new_noc_panel > .button").find("#ViewAllNoc,#ViewDetail").hide();
      		}
      		autocloseNoc();
      },
      error: function (request, textStatus, errorThrown){
      	 var msg3 = sprintf(td_lang.inc.msg_112,textStatus);
         alert(msg3);
      }
   });
}

//lp ���û��������Ϣ�����Զ����غ���
function autocloseNoc(){
	var datanum = get_noc_num();
	jQuery("#new_noc_panel > #new_noc_title > .noc_iterm_num >span").html(datanum);
	if(!(datanum > 0)){
		jQuery('#new_noc_list').hide();
		var msg3 = sprintf(td_lang.inc.msg_113,"<span class='red'>"+nocbox_close_timeout+"</span>");
		jQuery('#nocbox_tips').html("<h2>"+td_lang.inc.msg_40+"</h2><br />"+ msg3).show();
		window.clearInterval(closeNocPanel);
		closeNocPanel = window.setInterval(timerclose,1000);	
	}
}

//lp ����ʱ
function timerclose(){
	var time = jQuery('#nocbox_tips > span').text();
	if(time > 1){
		jQuery('#nocbox_tips > span').text(time-1);
	}else{
		CloseNoc();	
	}
}

function CloseRemind()
{
   jQuery('#new_sms_mask').hide();
   jQuery('#new_sms_panel').hide();
   if(!timer_sms_mon)
      timer_sms_mon = window.setTimeout(sms_mon, monInterval.sms*1000);
   
   ResetTitle();
}

function CloseNoc(){
   jQuery('#new_noc_panel').hide();
   if(!timer_sms_mon)
      timer_sms_mon = window.setTimeout(sms_mon, monInterval.sms*1000);
   if(closeNocPanel){
   	window.clearInterval(closeNocPanel);
   	closeNocPanel = null;
   }
   ResetTitle();	
}

function GetSmsIds()
{
   var recvIds = sendIds = '';
   for(var i=newSmsArray.length-1; i >= 0; i--)
   {
      if(newSmsArray[i].sms_id == '')
         continue;
      
      if(newSmsArray[i].receive == '1')
         recvIds += newSmsArray[i].sms_id + ',';
      else
         sendIds += newSmsArray[i].sms_id + ',';
   }
   
   return { recv : recvIds, send : sendIds };
}
//-- ��֯��� --
function ActiveUserTab(obj)
{
   if(obj.className.indexOf('active') >= 0)
      return;

   jQuery('#org_panel > .head > div >a.active').removeClass('active');
   jQuery(obj).addClass('active');
   jQuery('#user_online').toggle();
   jQuery('#user_all').toggle();
   jQuery('#org_panel').triggerHandler('_show');
}

function ViewOnlineUser()
{
   var pannelActive = $('org').className.indexOf('active') >= 0;
   if(pannelActive)
   {
      var onlineActive = $('user_online_tab').className.indexOf('active') >= 0;
      if(onlineActive)
         jQuery('#org').click();
      else
         ActiveUserTab($('user_online_tab'));
   }
   else
   {
      jQuery('#org').click();
      ActiveUserTab($('user_online_tab'));
   }
}

//-- ������Ա --
function online_mon()
{
   jQuery.ajax({
      async:true,
      url: 'ipanel/user/user_count.php',
      dataType: 'text',
      success: function(data){
         try{
            eval(data);
         }
         catch(ex){
         }
         
         online_count = typeof(online_array) == 'object' ? online_array[0] : 0;
         $("user_count").innerHTML=online_count;
         
         var online_title = sprintf(td_lang.general.msg_34, user_total_count, online_count);
         $('user_count').title = online_title;
         $('online_link').title = online_title;
      },
      error: function (request, textStatus, errorThrown){}
   });
   
   window.setTimeout(online_mon, monInterval.online*1000);
}

function SearchUser(USER_ID)
{
   openURL('', td_lang.inc.msg_41, "ipanel/user/query.php");//'��Ա��ѯ'
}

function view_user(USER_ID)
{
   openURL('', '', "ipanel/user/user_info.php?USER_ID="+USER_ID+"&WINDOW=1", "1", 930, 585);
}

function send_msg(TO_UID,TO_NAME)
{
   mytop=(document.body.clientHeight - 350)/2;
   myleft=(document.body.clientWidth - 430)/2;
   window.open("status_bar/sms_back.php?TO_UID="+TO_UID+"&TO_NAME="+unescape(TO_NAME),"","height=310,width=450,status=0,toolbar=no,menubar=no,location=no,scrollbars=no,top="+mytop+",left="+myleft+",resizable=yes");
}

function send_email(TO_ID,TO_NAME)
{
   openURL('', '', "email/new/?TO_ID="+TO_ID+"&TO_NAME="+unescape(TO_NAME),"1");
}

function history_sms(SMS_ID)
{
   mytop=screen.availHeight-360;
   myleft=0;
//   window.open("/general/sms/history_sms.php?SMS_ID="+SMS_ID,SMS_ID,"height=310,width=370,status=0,toolbar=no,menubar=no,location=no,scrollbars=no,top="+mytop+",left="+myleft+",resizable=yes");
}
function winexe(NAME,PROG)
{
   var URL="/general/winexe/?PROG="+PROG+"&NAME="+NAME;
   window.open(URL,"winexe","height=100,width=350,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=0,left=0,resizable=no");
}

//-- ״̬������ --
function StatusTextScroll()
{
   var obj = jQuery('#status_text');
   var scrollTo = obj.scrollTop() + obj.height();
   if(scrollTo >= obj.attr('scrollHeight'))
      scrollTo = 0;
   obj.animate({scrollTop: scrollTo}, 300);
}
/*
function tree_dblclick(node)
{
   if(node.isStatusNode() || !node.data.uid || typeof(send_msg) != 'function')
      return ;
   
   send_msg(node.data.uid, node.data.title);
}
*/
//����������ʽ
function setTheme(theme) { 
   var wins = [];
    
   function iteratWins(win) { 
      try { 
         var iframes = win.document.getElementsByTagName("iframe"); 
         for (var i = 0; i < iframes.length; i++) { 
            var iframe = iframes[i]; 
            wins.push(iframe.contentWindow); 
            iteratWins(iframe.contentWindow); 
         } 
      }catch (e) { 
      
      } 
   }
    
   function setPerWin(w) { 
      try { 
         var imgs = w.document.getElementsByTagName("img"); 
         for (var i = 0; i < imgs.length; i++) { 
            var e = imgs[i]; 
            if (/theme\/[0-9]{1,}\//.test(e.src)) { 
               e.src = e.src.replace(/theme\/[0-9]{1,}\//, "theme/" + theme + "/"); 
            } 
         } 
     
         var links = w.document.getElementsByTagName("LINK"); 
         for (var i = 0; i < links.length; i++) { 
            var e = links[i]; 
            if(e.href.indexOf('portal.css') > 0 || e.href.indexOf('task_center.css') > 0 || e.href.indexOf('task_center_ie6.css') > 0 || e.href.indexOf('mytable.css') > 0)
               continue;
            
            if (/theme\/[0-9]{1,}\//.test(e.href)) { 
               e.href = e.href.replace(/theme\/[0-9]{1,}\//, "theme/" + theme + "/"); 
            } 
         } 
      } catch (e) {
          
      } 
   }
    
   iteratWins(top); 
   wins.push(top); 
   
   for (var i = 0; i < wins.length; i++) { 
      setPerWin(wins[i]); 
   }
   
   var flag = updateTheme(theme);
   if(flag)
      ostheme = theme; 
}

function updateTheme(themeid){
   var flag = false;
   jQuery.ajax({
      async: false,
      data: {"themeid": themeid},
      url: '/general/person_info/theme/switch.php',
      success: function(r) {
         if (r == "+ok") {
            flag = true;  
         }
      }
   });
   return flag;
}

function strlen(str)
{
  return str.replace(/[^\x00-\xff]/g, 'xx').length;
}

function isTouchDevice(){
    try{
        document.createEvent("TouchEvent");
        return userAgent.indexOf("mobile") >= 0 || userAgent.indexOf("maxthon") < 0;
    }catch(e){
        return false;
    }
}

function closePortal()
{
   jQuery("#portal").trigger("click");
}

function closeTaskCenter()
{
   jQuery("#task_center").trigger("click");
}

//��ģ��·������Win8��ʽ
function SetWin8Style(url)
{
   RemoveWin8Style('body');
   
   if(typeof(url) != 'string')
      return;
   
   var module = '';
   url = url.toLowerCase();
   if(url.substr(0, 9) != '/general/')
      return;
   
   module = url.substr(9);
   if(module.indexOf('/') > 0)
   {
      var sub_module = module.substr(module.indexOf('/') + 1);
      module = module.substr(0, module.indexOf('/'));
      if(module == 'portal')
      {
         if(sub_module.indexOf('/') > 0)
            module = sub_module.substr(0, sub_module.indexOf('/'));
         else
            module = sub_module;
         
         module = 'portal_' + module;
      }
   }
   else if(module.indexOf('?') > 0)
   {
      module = module.substr(0, module.indexOf('?'));
   }
   else if(module.indexOf('#') > 0)
   {
      module = module.substr(0, module.indexOf('#'));
   }
   
   if(module == '' || module.indexOf('.') > 0)
      return;
   
   jQuery('body').addClass('win8_module_' + module);
}
function RemoveWin8Style(selector)
{
   var className = jQuery(selector).attr('className');
   if(typeof(className) != 'string')
      return;
   
   var classArray = className.split(' ');
   for(var i=0; i<classArray.length; i++)
   {
      if(classArray[i].substr(0, 12) == 'win8_module_')
         jQuery(selector).removeClass(classArray[i]);
   }
}