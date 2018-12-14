(function() {
  var LPB = window.LPB = window.LPB || {
    plugins: [],
    genSource: function() {
      // $("#source").val($("#build").html());
    },
    //true 没有重复；false有重复
    checkTitleIsRepeat: function(inputTitle) {
      var flag = true;
      var titlelist = new Array();
      var r = $("#build").find(".control-label");
      for (var i = 0; i < r.length; i++) {
        var title = $(r[i]).text();
        if (titlelist.indexOf(title) > -1) {
          flag = false;
        } else {
          titlelist.push(title);
        }
      }
      if (inputTitle) {
        if (titlelist.indexOf(inputTitle) > -1) {
          flag = false;
        }
      }
      return flag;
    }
  };
})();
$(document).ready(function() {
  $(document).on("mousedown", ".component", function(md) {
    md.stopPropagation();
    $(".popover").remove();
    md.preventDefault();
    var tops = [];
    var mouseX = md.pageX;
    var mouseY = md.pageY;
    var $temp;
    var timeout;
    var $this = $(this);
    var delays = {
      main: 0,
      form: 300
    }
    var type;
    if ($this.parent().parent().parent().parent().attr("id") === "components") {
      type = "main";
    } else {
      type = "form";
    }
    //明细控件禁止移动
    if (type == "form") {
      if ($(this).find("[detail='detail']").length > 0 || $(this).parent(".component").length > 0) {
        //return;
      }
    }
    var delayed = setTimeout(function() {
      if (type === "main") {
        $temp = $("<form class='form-horizontal span6' id='temp'></form>").append($this.clone());
      } else {
        if ($this.attr("id") !== "legend") {
          $temp = $("<form class='form-horizontal span6' id='temp'></form>").append($this);
        }
      }
      $("body").append($temp);
      $temp.css({
        "position": "absolute",
        "top": mouseY - ($temp.height() / 2) + "px",
        "left": mouseX - ($temp.width() / 2) + "px",
        "opacity": "0.9"
      }).show();

      var half_box_height = ($temp.height() / 2);
      var half_box_width = ($temp.width() / 2);
      var $target = $("#target");
      var tar_pos = $target.position();
      var $target_component = $("#target .component");

      $(document).delegate("body", "mousemove", function(mm) {
//    	  $(document).delegate("body", "mousemove", function(mm) {
        var mm_mouseX = mm.pageX;
        var mm_mouseY = mm.pageY;
        $temp.css({
          "top": mm_mouseY - half_box_height + "px",
          "left": mm_mouseX - half_box_width + "px"
        });
        if (mm_mouseX > tar_pos.left &&
          mm_mouseX < tar_pos.left + $target.width() + $temp.width() / 2 &&
          mm_mouseY > tar_pos.top &&
          mm_mouseY < tar_pos.top + $target.height() + $temp.height() / 2
        ) {
          $("#target").css("background-color", "#fafdff");
          $target_component.css({
            "border-top": "1px solid white",
            "border-bottom": "none"
          });
          tops = $.grep($target_component, function(e) {
            return ($(e).position().top - mm_mouseY + half_box_height > 0 && $(e).attr("id") !== "legend");
          });
          if (tops.length > 0) {
            $(tops[0]).css("border-top", "1px solid #22aaff");
            $(tops[0]).css("border-bottom", "1px solid #22aaff");
          } else {
            if ($target_component.length > 0) {
              $($target_component[$target_component.length - 1]).css("border-bottom", "1px solid #22aaff");
            }
          }
        } else {
          $("#target").css("background-color", "#fff");
          $target_component.css({
            "border-top": "1px solid white",
            "border-bottom": "none"
          });
          $target.css("background-color", "#fff");
        }
      });

      $("body").on("mouseup", "#temp", function(mu) {
        mu.preventDefault();

        var mu_mouseX = mu.pageX;
        var mu_mouseY = mu.pageY;
        var tar_pos = $target.position();

        $("#target .component").css({
          "border-top": "1px solid white",
          "border-bottom": "none"
        });

        // acting only if mouse is in right place
        if ((mu_mouseX + half_box_width > tar_pos.left &&
            mu_mouseX - half_box_width < tar_pos.left + $target.width() &&
            mu_mouseY + half_box_height > tar_pos.top &&
            mu_mouseY - half_box_height < tar_pos.top + $target.height()) || type == "main" || true) {
          $temp.find(".controls").show();
          $temp.find(".control-group").removeClass("span2.8");
          $temp.find(".control-group").removeClass("plugDesign");
          $temp.find(".control-group").attr("onmouseover", "showPlugRange(this)");
          $temp.find(".control-group").attr("onmouseout", "hidePlugRange(this)");
          //处理详情控件,隐藏lable
          if ($temp.find("input[detail='detail']").length > 0) {
            $temp.find(".detaillabel").remove();
          }
          //处理日期范围，显示lable
          if ($temp.find("input[leipiplugins='date_range']").length > 0) {
            $temp.find(".leipiplugins-orgname").remove();
            $temp.find(".control-label").show();
          }
          // where to add
          if (tops.length > 0) {
            //如果是拖动到详情控件，则单独处理，和其它控件区别开    
            if ($(tops[0]).find("[detail='detail']").length > 0) {
              if ($temp.find("input[detail='detail']").length > 0) {} else {
                //标记该控件所属的明细控件
                var detailName = $(tops[0]).find(".detailcontrols").find("[detail='detail']").attr("name");
                var dns = detailName.split("_");
                $temp.find(".leipiplugins").attr("ref_detail", dns[1]);
                $($temp.html()).insertBefore($(tops[0]).find(".detailcontrols"));

              }
            } else {
              $temp.find(".leipiplugins").removeAttr("ref_detail");
              $($temp.html()).insertBefore($(tops[0]));
            }
          } else {
            $temp.find(".leipiplugins").removeAttr("ref_detail");
            $("#target fieldset").append($temp.append("\n\n\ \ \ \ ").html());
          }
        } else {
          // no add
          $("#target .component").css({
            "border-top": "1px solid white",
            "border-bottom": "none"
          });
          tops = [];
        }
        //clean up & add popover
        $target.css("background-color", "#fff");
        $(document).undelegate("body", "mousemove");
        $("body").undelegate("#temp", "mouseup");
        $("#target .component").popover({
          trigger: "manual"
        });
        $temp.remove();

      });
    }, delays[type]);

    $(document).mouseup(function() {
      clearInterval(delayed);
      return false;
    });
    $(this).mouseout(function() {
      clearInterval(delayed);
      return false;
    });
  });

  //activate legend popover
  $("#build .component").popover({
    trigger: "manual"
  });
  //popover on click event
  $("#build").on("click", ".component", function(e) {
    e.preventDefault();
    e.stopPropagation();
    var active_component = $(this);
    active_component.popover("show");
    //class="leipiplugins"
    var leipiplugins = active_component.find(".controls").last().find(".leipiplugins"),
      plugins = $(leipiplugins).attr("leipiplugins"); //leipiplugins="text"

    //明细控件的话只取最后一个，非明细控件取全部
    if (plugins != "detailWidget") {
      leipiplugins = active_component.find(".controls").find(".leipiplugins");
    }

    //exec plugins
    if (typeof(LPB.plugins[plugins]) == 'function') {
      try {
        LPB.plugins[plugins](active_component, leipiplugins);
      } catch (e) {
        alert('控件异常');
      }
    } else {
      alert("控件有误或不存在，请与我们联系！");
    }

  });

});
if(!String.prototype.trim){
	String.prototype.trim = function(){
		return this.replace(/^s+/g, '');
	};
}