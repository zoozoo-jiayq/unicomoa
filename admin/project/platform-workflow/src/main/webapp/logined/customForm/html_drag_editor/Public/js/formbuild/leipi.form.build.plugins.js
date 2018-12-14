/*e.preventDefault();//阻止元素发生默认的行为(例如,当点击提交按钮时阻止对表单的提交*/

/* 文本框控件 text
acc  是 class="component" 的DIV 
e 是 class="leipiplugins" 的控件
*/
LPB.plugins['text'] = function(active_component, leipiplugins) {
    var plugins = 'text',
      popover = $(".popover");
    //右弹form  初始化值
    $(popover).find("#orgname").val($(leipiplugins).attr("title"));
    $(popover).find("#orgvalue").val($(leipiplugins).val());
    $(popover).find("#contenttype").val($(leipiplugins).attr("contenttype"));
    if ($(leipiplugins).attr("required")) {
      $(popover).find('input[name="required"]').eq(0).attr("checked", true);
      $(popover).find('input[name="required"]').eq(1).attr("checked", false);
    } else {
      $(popover).find('input[name="required"]').eq(1).attr("checked", true);
      $(popover).find('input[name="required"]').eq(0).attr("checked", false);
    }
    if ($(leipiplugins).attr("clientshow") == "yes") {
      $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
      $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
    } else {
      $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
      $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
    }
    //右弹form  取消控件
    $(popover).delegate(".btn-danger", "click", function(e) {

      active_component.popover("hide");
    });
    $(popover).delegate(".btn-warning", "click", function(e) {
      active_component.popover("hide");
      active_component.remove();
      LPB.genSource();
    });
    //右弹form  确定控件
    $(popover).delegate(".btn-info", "click", function(e) {

      var inputs = $(popover).find("input");
      if ($(popover).find("textarea").length > 0) {
        inputs.push($(popover).find("textarea")[0]);
      }
      $.each(inputs, function(i, e) {
        var attr_name = $(e).attr("id"); //属性名称
        var attr_val = $(e).val();
        switch (attr_name) {
          //要break
          case 'orgvalue':
            //$(leipiplugins).val(attr_val);
            $(leipiplugins).attr("value", attr_val);
            break;
            //没有break
          case 'orgname':
            $(leipiplugins).attr("title", attr_val);
            $(leipiplugins).attr("cnname", "%" + attr_val + "%");
            $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
            active_component.find(".leipiplugins-orgname").text(attr_val);
            break;
          case 'required':
            if ($(popover).find('input[name="required"]:checked ').val() == '1') {
              $(leipiplugins).attr("required", true);
            } else {
              $(leipiplugins).attr("required", false);
            }
            break;
          case 'clientshow':
            if ($(popover).find('input[name="clientshow"]:checked ').val() == '1') {
              $(leipiplugins).attr("clientshow", "yes");
            } else {
              $(leipiplugins).attr("clientshow", "no");
            }
            break;
          default:
            $(leipiplugins).attr(attr_name, attr_val);
        }
        active_component.popover("hide");
        //LPB.genSource(); //重置源代码
      });
      if ($(popover).find("#contenttype").length > 0) {
        $(leipiplugins).attr("contenttype", $(popover).find("#contenttype").val());
        active_component.popover("hide");
        //LPB.genSource(); //重置源代码
      }
    });
  }
  /* 多行文本框控件 textarea
  acc  是 class="component" 的DIV 
  e 是 class="leipiplugins" 的控件
  */
LPB.plugins['textarea'] = function(active_component, leipiplugins) {
    var plugins = 'textarea',
      popover = $(".popover");
    //右弹form  初始化值
    $(popover).find("#orgname").val($(leipiplugins).attr("title"));
    $(popover).find("#orgvalue").val($(leipiplugins).val());
    if ($(leipiplugins).attr("required")) {
      $(popover).find('input[name="required"]').eq(0).attr("checked", true);
      $(popover).find('input[name="required"]').eq(1).attr("checked", false);
    } else {
      $(popover).find('input[name="required"]').eq(1).attr("checked", true);
      $(popover).find('input[name="required"]').eq(0).attr("checked", false);
    }
    if ($(leipiplugins).attr("clientshow") == "yes") {
      $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
      $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
    } else {
      $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
      $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
    }
    //右弹form  取消控件
    $(popover).delegate(".btn-danger", "click", function(e) {
      active_component.popover("hide");
    });
    $(popover).delegate(".btn-warning", "click", function(e) {
      active_component.popover("hide");
      active_component.remove();
      //LPB.genSource();
    });
    //右弹form  确定控件
    $(popover).delegate(".btn-info", "click", function(e) {

      var inputs = $(popover).find("input");
      if ($(popover).find("textarea").length > 0) {
        inputs.push($(popover).find("textarea")[0]);
      }
      $.each(inputs, function(i, e) {
        var attr_name = $(e).attr("id"); //属性名称
        var attr_val = $(e).val();
        switch (attr_name) {
          //要break
          case 'orgvalue':
            //$(leipiplugins).val(attr_val);
            $(leipiplugins).text(attr_val);
            break;
            //没有break
          case 'orgname':
            $(leipiplugins).attr("title", attr_val);
            $(leipiplugins).attr("cnname", "%" + attr_val + "%");
            $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
            active_component.find(".leipiplugins-orgname").text(attr_val);
            break;
          case 'required':
            if ($(popover).find('input[name="required"]:checked ').val() == '1') {
              $(leipiplugins).attr("required", true);
            } else {
              $(leipiplugins).attr("required", false);
            }
            break;
          case 'clientshow':
            if ($(popover).find('input[name="clientshow"]:checked ').val() == '1') {
              $(leipiplugins).attr("clientshow", "yes");
            } else {
              $(leipiplugins).attr("clientshow", "no");
            }
            break;
          default:
            $(leipiplugins).attr(attr_name, attr_val);
        }
        active_component.popover("hide");
        //LPB.genSource(); //重置源代码
      });

    });
  }
  /* 下拉框控件 select
  acc  是 class="component" 的DIV 
  e 是 class="leipiplugins" 的控件
  */
LPB.plugins['select'] = function(active_component, leipiplugins) {
  var plugins = 'select',
    popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname").val($(leipiplugins).attr("title"));
  var val = $.map($(leipiplugins).find("option"), function(e, i) {
    return $(e).text()
  });
  val = val.join("\n");
  $(popover).find("#orgvalue").text(val);
  if ($(leipiplugins).attr("required")) {
    $(popover).find('input[name="required"]').eq(0).attr("checked", true);
    $(popover).find('input[name="required"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="required"]').eq(1).attr("checked", true);
    $(popover).find('input[name="required"]').eq(0).attr("checked", false);
  }
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }
  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {
    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    //LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {

    var inputs = $(popover).find("input");
    if ($(popover).find("textarea").length > 0) {
      inputs.push($(popover).find("textarea")[0]);
    }
    $.each(inputs, function(i, e) {
      var attr_name = $(e).attr("id"); //属性名称
      var attr_val = $(e).val();
      switch (attr_name) {
        //要break
        case 'orgvalue':
          var options = attr_val.split("\n");
          $(leipiplugins).html("");
          $.each(options, function(i, e) {
            $(leipiplugins).append("\n      ");
            $(leipiplugins).append($("<option>").text(e));
          });
          //$(leipiplugins).text(attr_val);
          break;

        case 'orgname':
          $(leipiplugins).attr("title", attr_val);
          $(leipiplugins).attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          active_component.find(".leipiplugins-orgname").text(attr_val);
          break;
        case 'required':
          if ($(popover).find('input[name="required"]:checked ').val() == '1') {
            $(leipiplugins).attr("required", true);
          } else {
            $(leipiplugins).attr("required", false);
          }
          break;
        case 'clientshow':
          if ($(popover).find('input[name="clientshow"]:checked ').val() == '1') {
            $(leipiplugins).attr("clientshow", "yes");
          } else {
            $(leipiplugins).attr("clientshow", "no");
          }
          break;
        default:
          $(leipiplugins).attr(attr_name, attr_val);
      }
      active_component.popover("hide");
      //LPB.genSource(); //重置源代码
    });

  });
}

/* 单选控件 radio
acc  是 class="component" 的DIV 
e 是 class="leipiplugins" 的控件
*/
LPB.plugins['radio'] = function(active_component, leipiplugins) {
  var plugins = 'radio',
    popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname").val($(leipiplugins).attr("title"));
  val = $.map($(leipiplugins), function(e, i) {
    return $(e).val().trim()
  });
  val = val.join("\n");
  $(popover).find("#orgvalue").text(val);
  if ($(leipiplugins).attr("required")) {
    $(popover).find('input[name="required"]').eq(0).attr("checked", true);
    $(popover).find('input[name="required"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="required"]').eq(1).attr("checked", true);
    $(popover).find('input[name="required"]').eq(0).attr("checked", false);
  }
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }
  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {
    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    //LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {

    var inputs = $(popover).find("input");
    if ($(popover).find("textarea").length > 0) {
      inputs.push($(popover).find("textarea")[0]);
    }
    var _title = $("#orgname").val();
    var _name = "_" + pinyin.getFullChars(_title) + "_";
    var _cnname = "%" + _title + "%";
    $.each(inputs, function(i, e) {
      var attr_name = $(e).attr("id"); //属性名称
      var attr_val = $(e).val();
      switch (attr_name) {
        //要break
        case 'orgvalue':
          var checkboxes = attr_val.split("\n");
          var html = "<!-- Multiple Checkboxes -->\n";
          $.each(checkboxes, function(i, e) {
            if (e.length > 0) {
              var vName = $(leipiplugins).eq(i).attr("name"),
                vCnname = $(leipiplugins).eq(i).attr("cnname"),
                vTitle = $(leipiplugins).eq(i).attr("title"),
                orginline = $(leipiplugins).eq(i).attr("orginline");
              if (!vName) vName = _name;
              if (!vTitle) vTitle = _title;
              if (!orginline) orginline = 'inline';
              if (!vCnname) vCnname = _cnname;
              var vRequired = '';
              if ($(popover).find('input[name="required"]:checked ').val() == '1') {
                vRequired = 'required="required"';
              }
              var vClientshow = 'clientshow="yes"';
              if ($(popover).find('input[name="clientshow"]:checked ').val() == '0') {
                vClientshow = 'clientshow="no"';
              }
              html += '<label class="radio ' + orginline + '">\n<input type="radio" name="' + vName + '" ' + vClientshow + ' ' + vRequired + ' cnname="' + vCnname + '" title="' + vTitle + '" value="' + e + '" orginline="' + orginline + '" class="leipiplugins single_sel" leipiplugins="radio" >' + e + '\n</label>';
            }
            $(active_component).find(".leipiplugins-orgvalue").html(html);
          });
          break;
        case 'required':

          break;
        case 'orgname':
          $(leipiplugins).attr("title", attr_val);
          $(leipiplugins).attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          active_component.find(".leipiplugins-orgname").text(attr_val);
          break;
        default:
          $(leipiplugins).attr(attr_name, attr_val);
      }
      active_component.popover("hide");
      //LPB.genSource(); //重置源代码
    });

  });
}

/* 复选控件 checkbox
acc  是 class="component" 的DIV 
e 是 class="leipiplugins" 的控件
*/
LPB.plugins['checkbox'] = function(active_component, leipiplugins) {
  var plugins = 'checkbox',
    popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname").val($(leipiplugins).attr("title"));
  val = $.map($(leipiplugins), function(e, i) {
    return $(e).val().trim()
  });
  val = val.join("\n");
  $(popover).find("#orgvalue").text(val);
  if ($(leipiplugins).attr("required")) {
    $(popover).find('input[name="required"]').eq(0).attr("checked", true);
    $(popover).find('input[name="required"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="required"]').eq(1).attr("checked", true);
    $(popover).find('input[name="required"]').eq(0).attr("checked", false);
  }
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }

  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {

    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    //LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {

    var inputs = $(popover).find("input");
    if ($(popover).find("textarea").length > 0) {
      inputs.push($(popover).find("textarea")[0]);
    }
    var _title = $("#orgname").val();
    var _name = "_" + pinyin.getFullChars(_title) + "_";
    var _cnname = "%" + _title + "%";
    $.each(inputs, function(i, e) {
      var attr_name = $(e).attr("id"); //属性名称
      var attr_val = $(e).val();
      switch (attr_name) {
        //要break
        case 'orgvalue':
          var checkboxes = attr_val.split("\n");
          var html = "<!-- Multiple Checkboxes -->\n";
          $.each(checkboxes, function(i, e) {
            if (e.length > 0) {
              var vName = $(leipiplugins).eq(i).attr("name"),
                vCnname = $(leipiplugins).eq(i).attr("cnname"),
                vTitle = $(leipiplugins).eq(i).attr("title"),
                orginline = $(leipiplugins).eq(i).attr("orginline");
              if (!vName) vName = _name;
              if (!vTitle) vTitle = _title;
              if (!orginline) orginline = 'inline';
              if (!vCnname) vCnname = _cnname;
              var vRequired = '';
              if ($(popover).find('input[name="required"]:checked ').val() == '1') {
                vRequired = 'required="required"';
              }
              var vClientshow = 'clientshow="yes"';
              if ($(popover).find('input[name="clientshow"]:checked ').val() == '0') {
                vClientshow = 'clientshow="no"';
              }
              html += '<label class="checkbox ' + orginline + '">\n<input type="checkbox" name="' + vName + '" ' + vClientshow + ' ' + vRequired + ' cnname="' + vCnname + '" title="' + vTitle + '" value="' + e + '" orginline="' + orginline + '" class="leipiplugins more_sel" leipiplugins="checkbox" >' + e + '\n</label>';
            }
            $(active_component).find(".leipiplugins-orgvalue").html(html);
          });
          break;

        case 'orgname':
          $(leipiplugins).attr("title", attr_val);
          $(leipiplugins).attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          active_component.find(".leipiplugins-orgname").text(attr_val);
          break;
        case 'required':

          break;
        default:
          $(leipiplugins).attr(attr_name, attr_val);
      }
      active_component.popover("hide");
      //LPB.genSource(); //重置源代码
    });

  });
}

/* 日期控件 date
acc  是 class="component" 的DIV 
e 是 class="leipiplugins" 的控件
*/
LPB.plugins['date'] = function(active_component, leipiplugins) {
  var plugins = 'date',
    popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname").val($(leipiplugins).attr("title"));
  if ($(leipiplugins).attr("required")) {
    $(popover).find('input[name="required"]').eq(0).attr("checked", true);
    $(popover).find('input[name="required"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="required"]').eq(1).attr("checked", true);
    $(popover).find('input[name="required"]').eq(0).attr("checked", false);
  }
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }
  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {

    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    //LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {

    var inputs = $(popover).find("input");
    if ($(popover).find("textarea").length > 0) {
      inputs.push($(popover).find("textarea")[0]);
    }
    if ($(popover).find("#dateformat").length > 0) {
      inputs.push($(popover).find("#dateformat")[0]);
    }
    $.each(inputs, function(i, e) {
      var attr_name = $(e).attr("id"); //属性名称
      var attr_val = $(e).val();
      switch (attr_name) {
        //没有break
        case 'orgname':
          $(leipiplugins).attr("title", attr_val);
          $(leipiplugins).attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          active_component.find(".leipiplugins-orgname").text(attr_val);
          break;
        case 'dateformat':
          $(leipiplugins).attr("date_format", attr_val);
          break;
        case 'required':
          if ($(popover).find('input[name="required"]:checked ').val() == '1') {
            $(leipiplugins).attr("required", true);
          } else {
            $(leipiplugins).attr("required", false);
          }
          break;
        case 'clientshow':
          if ($(popover).find('input[name="clientshow"]:checked ').val() == '1') {
            $(leipiplugins).attr("clientshow", "yes");
          } else {
            $(leipiplugins).attr("clientshow", "no");
          }
          break;
        default:
          $(leipiplugins).attr(attr_name, attr_val);
      }
      active_component.popover("hide");
      //LPB.genSource(); //重置源代码
    });
  });
}

/* 人员选择控件 selectuser
acc  是 class="component" 的DIV 
e 是 class="leipiplugins" 的控件
*/
LPB.plugins['selectuser'] = function(active_component, leipiplugins) {
  var plugins = 'selectuser',
    popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname").val($(leipiplugins).attr("title"));
  if ($(leipiplugins).attr("required")) {
    $(popover).find('input[name="required"]').eq(0).attr("checked", true);
    $(popover).find('input[name="required"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="required"]').eq(1).attr("checked", true);
    $(popover).find('input[name="required"]').eq(0).attr("checked", false);
  }
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }
  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {

    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {

    var inputs = $(popover).find("input");
    if ($(popover).find("textarea").length > 0) {
      inputs.push($(popover).find("textarea")[0]);
    }
    $.each(inputs, function(i, e) {
      var attr_name = $(e).attr("id"); //属性名称
      var attr_val = $(e).val();
      switch (attr_name) {
        //没有break
        case 'orgname':
          $(leipiplugins).attr("title", attr_val);
          $(leipiplugins).attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          active_component.find(".leipiplugins-orgname").text(attr_val);
          break;
        case 'required':
          if ($(popover).find('input[name="required"]:checked ').val() == '1') {
            $(leipiplugins).attr("required", true);
          } else {
            $(leipiplugins).attr("required", false);
          }
          break;
        case 'clientshow':
          if ($(popover).find('input[name="clientshow"]:checked ').val() == '1') {
            $(leipiplugins).attr("clientshow", "yes");
          } else {
            $(leipiplugins).attr("clientshow", "no");
          }
          break;
        default:
          $(leipiplugins).attr(attr_name, attr_val);
      }
      active_component.popover("hide");
      LPB.genSource(); //重置源代码
    });
  });
}

/* 部门选择控件 selectgroup
acc  是 class="component" 的DIV 
e 是 class="leipiplugins" 的控件
*/
LPB.plugins['selectgroup'] = function(active_component, leipiplugins) {
  var plugins = 'selectgroup',
    popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname").val($(leipiplugins).attr("title"));
  if ($(leipiplugins).attr("required")) {
    $(popover).find('input[name="required"]').eq(0).attr("checked", true);
    $(popover).find('input[name="required"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="required"]').eq(1).attr("checked", true);
    $(popover).find('input[name="required"]').eq(0).attr("checked", false);
  }
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }
  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {

    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {

    var inputs = $(popover).find("input");
    if ($(popover).find("textarea").length > 0) {
      inputs.push($(popover).find("textarea")[0]);
    }
    $.each(inputs, function(i, e) {
      var attr_name = $(e).attr("id"); //属性名称
      var attr_val = $(e).val();
      switch (attr_name) {
        //没有break
        case 'orgname':
          $(leipiplugins).attr("title", attr_val);
          $(leipiplugins).attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          active_component.find(".leipiplugins-orgname").text(attr_val);
          break;
        case 'required':
          if ($(popover).find('input[name="required"]:checked ').val() == '1') {
            $(leipiplugins).attr("required", true);
          } else {
            $(leipiplugins).attr("required", false);
          }
          break;
        case 'clientshow':
          if ($(popover).find('input[name="clientshow"]:checked ').val() == '1') {
            $(leipiplugins).attr("clientshow", "yes");
          } else {
            $(leipiplugins).attr("clientshow", "no");
          }
          break;
        default:
          $(leipiplugins).attr(attr_name, attr_val);
      }
      active_component.popover("hide");
      LPB.genSource(); //重置源代码
    });
  });
}

/* 审批意见控件 advice
acc  是 class="component" 的DIV 
e 是 class="leipiplugins" 的控件
*/
LPB.plugins['advice'] = function(active_component, leipiplugins) {
  var plugins = 'advice',
    popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname").val($(leipiplugins).attr("title"));
  $(popover).find("#orgvalue").val($(leipiplugins).val());
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }
  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {
    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {

    var inputs = $(popover).find("input");
    if ($(popover).find("textarea").length > 0) {
      inputs.push($(popover).find("textarea")[0]);
    }
    $.each(inputs, function(i, e) {
      var attr_name = $(e).attr("id"); //属性名称
      var attr_val = $(e).val();
      switch (attr_name) {
        //要break
        case 'orgvalue':
          //$(leipiplugins).val(attr_val);
          $(leipiplugins).text(attr_val);
          break;
          //没有break
        case 'orgname':
          $(leipiplugins).attr("title", attr_val);
          $(leipiplugins).attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          active_component.find(".leipiplugins-orgname").text(attr_val);
          break;
        case 'clientshow':
          if ($(popover).find('input[name="clientshow"]:checked ').val() == '1') {
            $(leipiplugins).attr("clientshow", "yes");
          } else {
            $(leipiplugins).attr("clientshow", "no");
          }
          break;
        default:
          $(leipiplugins).attr(attr_name, attr_val);
      }
      active_component.popover("hide");
      LPB.genSource(); //重置源代码
    });

  });
}

/* 计算控件 calculate
acc  是 class="component" 的DIV 
e 是 class="leipiplugins" 的控件
*/
LPB.plugins['calculate'] = function(active_component, leipiplugins) {
  var plugins = 'calculate',
    popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname").val($(leipiplugins).attr("title"));
  $(popover).find("#expr").val($(leipiplugins).attr("expr"));
  if ($(leipiplugins).attr("required")) {
    $(popover).find('input[name="required"]').eq(0).attr("checked", true);
    $(popover).find('input[name="required"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="required"]').eq(1).attr("checked", true);
    $(popover).find('input[name="required"]').eq(0).attr("checked", false);
  }
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }
  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {

    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {

    var inputs = $(popover).find("input");
    if ($(popover).find("textarea").length > 0) {
      inputs.push($(popover).find("textarea")[0]);
    }
    $.each(inputs, function(i, e) {
      var attr_name = $(e).attr("id"); //属性名称
      var attr_val = $(e).val();
      switch (attr_name) {
        //没有break
        case 'orgname':
          $(leipiplugins).attr("title", attr_val);
          $(leipiplugins).attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          active_component.find(".leipiplugins-orgname").text(attr_val);
          break;
        case 'required':
          if ($(popover).find('input[name="required"]:checked ').val() == '1') {
            $(leipiplugins).attr("required", true);
          } else {
            $(leipiplugins).attr("required", false);
          }
          break;
        case 'clientshow':
          if ($(popover).find('input[name="clientshow"]:checked ').val() == '1') {
            $(leipiplugins).attr("clientshow", "yes");
          } else {
            $(leipiplugins).attr("clientshow", "no");
          }
          break;
        default:
          $(leipiplugins).attr(attr_name, attr_val);
      }
      active_component.popover("hide");
      LPB.genSource(); //重置源代码
    });
  });
}

/* 日期范围控件 date_range
acc  是 class="component" 的DIV 
e 是 class="leipiplugins" 的控件
*/
LPB.plugins['date_range'] = function(active_component, leipiplugins) {
  var plugins = 'date_range',
    popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname1").val($(leipiplugins).filter("[calendarrangeflag='start']").attr("title"));
  $(popover).find("#orgname2").val($(leipiplugins).filter("[calendarrangeflag='end']").attr("title"));
  $(popover).find("#orgname3").val($(leipiplugins).filter("[calendarrange_expr]").attr("title"));
  $(popover).find("#dateformat").val($(leipiplugins).filter("[calendarrangeflag='start']").attr("date_format"));
  if ($(leipiplugins).attr("required")) {
    $(popover).find('input[name="required"]').eq(0).attr("checked", true);
    $(popover).find('input[name="required"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="required"]').eq(1).attr("checked", true);
    $(popover).find('input[name="required"]').eq(0).attr("checked", false);
  }
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }
  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {
    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {
	  
	var dateformat =  $(popover).find("#dateformat").val();
	$(leipiplugins).filter("[calendarrangeflag='start']").attr("date_format",dateformat);
	$(leipiplugins).filter("[calendarrangeflag='end']").attr("date_format",dateformat);
	  
    var inputs = $(popover).find("input");
    if ($(popover).find("textarea").length > 0) {
      inputs.push($(popover).find("textarea")[0]);
    }
    
    $.each(inputs, function(i, e) {
      var attr_name = $(e).attr("id"); //属性名称
      var attr_val = $(e).val();
      switch (attr_name) {
        case 'orgname1':
          $(leipiplugins).filter("[calendarrangeflag='start']").attr("title", attr_val);
          $(leipiplugins).filter("[calendarrangeflag='start']").attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).filter("[calendarrangeflag='start']").attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          $(leipiplugins).filter("[calendarrangeflag='start']").attr("other",  $(popover).find("#orgname2").val());
          $(leipiplugins).filter("[calendarrange_expr]").attr("calendarrange_expr", $(popover).find("#orgname2").val() + "-" + $(popover).find("#orgname1").val());
          active_component.find(".leipiplugins-orgname1").text(attr_val);
          break;
        case 'orgname2':
          $(leipiplugins).filter("[calendarrangeflag='end']").attr("title", attr_val);
          $(leipiplugins).filter("[calendarrangeflag='end']").attr("other",  $(popover).find("#orgname1").val());
          $(leipiplugins).filter("[calendarrangeflag='end']").attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).filter("[calendarrangeflag='end']").attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          $(leipiplugins).filter("[calendarrange_expr]").attr("calendarrange_expr", $(popover).find("#orgname2").val() + "-" + $(popover).find("#orgname1").val());
          active_component.find(".leipiplugins-orgname2").text(attr_val);
          break;
        case 'orgname3':
          $(leipiplugins).filter("[calendarrange_expr]").attr("title", attr_val);
          $(leipiplugins).filter("[calendarrange_expr]").attr("cnname", "%" + attr_val + "%");
          $(leipiplugins).filter("[calendarrange_expr]").attr("name", "_" + pinyin.getFullChars(attr_val) + "_");
          active_component.find(".leipiplugins-orgname3").text(attr_val);
          break;
        case 'required':
          if ($(popover).find('input[name="required"]:checked ').val() == '1') {
            $(leipiplugins).attr("required", true);
          } else {
            $(leipiplugins).attr("required", false);
          }
          break;
        case 'clientshow':
          if ($(popover).find('input[name="clientshow"]:checked ').val() == '1') {
            $(leipiplugins).attr("clientshow", "yes");
          } else {
            $(leipiplugins).attr("clientshow", "no");
          }
          break;
        default:
          $(leipiplugins).attr(attr_name, attr_val);
      }
      active_component.popover("hide");
      LPB.genSource(); //重置源代码
    });
    if ($(popover).find("#dateformat").length > 0) {
      $(leipiplugins).attr("date_format", $(popover).find("#dateformat").val());
      active_component.popover("hide");
      LPB.genSource(); //重置源代码
    }

  });
}

/* 
明细控件 detailWidget
*/
LPB.plugins['detailWidget'] = function(active_component, leipiplugins) {
  var popover = $(".popover");
  //右弹form  初始化值
  $(popover).find("#orgname").val($(leipiplugins).attr("title"));
  if ($(leipiplugins).attr("clientshow") == "yes") {
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", false);
  } else {
    $(popover).find('input[name="clientshow"]').eq(1).attr("checked", true);
    $(popover).find('input[name="clientshow"]').eq(0).attr("checked", false);
  }
  //右弹form  取消控件
  $(popover).delegate(".btn-danger", "click", function(e) {
    active_component.popover("hide");
  });
  $(popover).delegate(".btn-warning", "click", function(e) {
    active_component.popover("hide");
    active_component.remove();
    LPB.genSource();
  });
  //右弹form  确定控件
  $(popover).delegate(".btn-info", "click", function(e) {

    var inputs = $(popover).find("input");
    var cs = $(popover).find('input[name="clientshow"]:checked').val();
    if (cs == 1) {
      $(leipiplugins).attr("clientshow", "yes");
    } else {
      $(leipiplugins).attr("clientshow", "no");
    }
    var plugname = $(popover).find("#orgname").val();
    $(leipiplugins).attr("title", plugname);
    $(leipiplugins).attr("value", "+ " + plugname);
    $(leipiplugins).attr("cnname", "%" + plugname + "%");
    $(leipiplugins).attr("name", "_" + pinyin.getFullChars(plugname) + "_");
    var dns = $(leipiplugins).attr("name").split("_");
    $(active_component).find("[ref_detail]").attr("ref_detail", dns[1]);
    active_component.popover("hide");
    LPB.genSource(); //重置源代码
  });
}