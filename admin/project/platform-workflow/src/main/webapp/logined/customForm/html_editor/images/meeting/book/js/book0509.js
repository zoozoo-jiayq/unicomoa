function WeekBooker(cfg) {
  var opts = {
    line: 0,
    start: 9 * 60,
    limit: 9 * 60 + 1,
    distance: 5,
    //width: 9 * 60 / 5 * 10,
    renderTo: '',
    occupied: [],
    ppm: 2,
    update: $.noop,
    day: "2012-04-04",
    oncreate: $.noop,
    name: "会议室",
    update: $.noop,
    date: [],
    beforecreate: $.noop
  }
  var c = $.extend(true, opts, cfg);
  
  var height = c.date.length * 50;
  var el = $("<div class='week-booker'></div>").height(height);
  var axis = $("<div class='axis-y-date'></div>").height(height);
  var name = $("<div class='axis-y-name'></div>").html("<span>" + c.name || '' + "</span>").height(height);
  var table = $("<table></table>").appendTo(c.renderTo);
  
  table.append($("<tbody></tbody>")
      .append($("<tr></tr>")
          .append($("<td></td>").append(name))
          .append($("<td></td>").append(axis))
          .append($("<td></td>").append(el))));
  
  var helper = $("<div class='drag-helper'></div>");
  function doDragHelper() {
    helper.clientX = 0, helper.clientY = 0;
    el.mousedown(function(e) {
      helper.appendTo('body');
      helper.width(0).height(0).show();
      helper.clientX = e.clientX;
      helper.clientY = e.clientY;
      helper.css({
        left: e.clientX + "px",
        top: e.clientY + "px"
      });
    }).bind("mousemove", function(evt) {
      if (helper.clientX > 0 && helper.clientY > 0) {
        helper.css({
          left: Math.min(evt.clientX, helper.clientX) + "px",
          top: Math.min(evt.clientY, helper.clientY) + "px"
        });
        helper.width(Math.abs(evt.clientX - helper.clientX)).height(Math.abs(evt.clientY - helper.clientY));
      }
    }).bind("mouseup mouseleave", function(evt) {
      helper.hide();
      helper.clientX = 0, helper.clientY = 0;
    });
    
  }
  doDragHelper();
  
  var bookers = [];
  $.each(c.date, function(i, e) {
    axis.append("<div class='item " + (i % 2 ? "odd" : "even") + "'>" + e + "</div>");
    var booker = new Booker({
      renderTo: el,
      line: i,
      day: e,
      distance: c.distance,
      oncreate: c.oncreate,
      beforecreate: c.beforecreate,
      update: function(id, start, limit) {
        c.update(id, start, limit);
      }
    });
    bookers.push(booker);
  });
  $("<div class='meeting-sp'></div>").appendTo(c.renderTo);
  
  return {
    bookers: bookers,
    crowd: function() {
      for (var i in bookers) {
        b = bookers[i];
        if (b && b.crowd && b.crowd()) {
          return true;
        }
      }
      return false;
    }
  }
}

function Booker(cfg) {
  var opts = {
    line: 0,
    start: 9 * 60,
    limit: 9 * 60 + 1,
    distance: 5,
    //width: 9 * 60 / 5 * 10,
    renderTo: '',
    day: "2012-04-04",
    occupied: [],
    ppm: 2,
    update: $.noop,
    id: "",
    oncreate: $.noop,
    beforecreate: $.noop
  }
  var c = $.extend(true, opts, cfg);
  c.width = c.limit * c.ppm;
  var meetings = [];
  var el = $("<div class='container'>").addClass(c.line % 2 ? "odd" : "even").css({width: c.width});
  var distance = (c.ppm * c.distance) ^ 0;
  c.renderTo && el.appendTo(c.renderTo);
  
  /**
   * 判断是否有冲突
   */
  function crowd() {
    var flag = false;
    $.each(meetings, function(k, v) {
      var s = v.getStart();
      var l = v.getLimit();
      $.each(meetings, function(j, o) {
        if (j == k) {
          return;
        }
        var s1 = o.getStart();
        var l1 = o.getLimit()
        if ((s <= s1 && s + l > s1) || (s1 <= s && s1 + l1 > s)) {
          flag = true;
        }
      });
    });
    return flag;
  }
  
  function add(meeting) {
    var me = meeting.el;
    var s = Math.floor(meeting.getStart() - c.start);
    var l = Math.floor(meeting.getLimit() * c.width / c.limit);
    el.append(me);
    meetings.push(meeting);
    meeting.el.bind("_remove", function() {
      meetings = $.grep(meetings, function(e, i) {
        return e != meeting;
      });
    });
    function doCollision() {
      
    }
    me.css({
      position: "absolute",
      left: (c.ppm * s) + "px",
      width: l + "px"
    });
    meeting.disabled || me.resizable({
      containment: ".week-booker",
      handles: "w,e",
      grid: c.distance * c.ppm,
      resize: function() {
        meeting.refreshTimes(c.start);
      },
      minWidth: c.distance * c.ppm,
      stop: function(e) {
        meeting.refreshTimes(c.start);
        c.update(c.line, meeting.getStart(), meeting.getLimit());
        if (crowd()) {
          alert("会议时间有冲突")
        }
      }
    }).draggable({
      containment: ".container",
      axis: "x",
      grid: [c.distance * c.ppm, 10],
      start: function(e) {
        
      },
      drag: function() {
        meeting.refreshTimes(c.start);
      },
      stop: function() {
        meeting.refreshTimes(c.start);
        c.update(c.line, meeting.getStart(), meeting.getLimit());
        if (crowd()) {
          alert("会议时间有冲突")
        }
      }
    }).mousedown(function() {
      return false;
    });
  }
  
  function doMouseEvt() {
    var cancel = false;
    el.dblclick(function() {
      Booker.newMeeting && Booker.newMeeting.remove();
    }).mousedown(function(e) {
      cancel = false;
      e = e || window.event;
      var start = e.offsetX || e.layerX;
      if ((e.srcElement || e.target) != el[0]) {
        return;
      }
      el.one("mouseup", function(evt) {
        evt = evt || window.event;
        if (cancel) {
          return true;
        }
        var end = evt.offsetX || evt.layerX;
        var len = Math.abs(end - start);
        if (c.beforecreate(c.line) === false) {
          return false;
        }
        Booker.newMeeting && Booker.newMeeting.remove();
        var limit;
        var st = c.start + Math.floor(Math.min(start, end) / distance) * c.distance;
        if (Math.floor(Math.min(start, end) / distance) < Math.round(Math.min(start, end) / distance)) {
          limit = (Math.ceil((len + 30)/ distance)) * c.distance
        }
        else {
          limit = (Math.ceil(len / distance)) * c.distance;
        }
        if (limit < 30) {
          limit = 30;
        }
        Booker.newMeeting = new Meeting({
          content: "待预约",
          start: st,
          limit: limit,
          ppm: 2,
          day: c.day && c.day.replace(/\(\W\W\)/, ""),
          status: 4,
          onclick: function(e, id, s, l) {
          }
        });
        Booker.newMeeting.el.addClass("active");
        add(Booker.newMeeting);
        c.oncreate(c.line, st, limit);
        evt.preventDefault();
      });
      e.preventDefault();
    }).mousemove(function(evt) {
      if ((evt.srcElement || evt.target) != el[0]) {
        cancel = true;
      }
    });
  }
  
  doMouseEvt();
  
  return {
    add: add,
    id: c.id,
    el: el,
    crowd: crowd
  }
}

function Meeting(cfg) {
  var opts = {
    data: "",
  //开始时间(单位分钟)
    start: 10 * 60,
    //持续时间(单位分钟)
    limit: 11 * 60,
    content: "",
    //每分钟的像素数
    ppm: 2,
    status: 0,
    day: "2012-04-04",
    onclick: $.noop,
    id: "new",
    statusInfo: [{
      text: "待批准",
      cls: "status0"
    }, {
      text: "已批准",
      cls: "status1"
    }, {
      text: "进行中",
      cls: "status2"
    }, {
      text: "已结束",
      cls: "status3"
    }, {
      text: "待预约",
      cls: ""
    }]
  }
  
  var c = $.extend(true, opts, cfg);
  var el = $("<div class='meeting'></div>");
  var table = $("<table></table>");
  var tbody = $("<tbody></tbody>");
  var tr = $("<tr></tr>");
  var tdl = $("<td class='start-time'></td>");
  var tdc = $("<td></td>");
  var tdr = $("<td class='end-time'></td>");
  var disabled = c.disabled;
  disabled && el.addClass("disabled");
  var times = $("<p class='time-info'></p>");
  var content = $("<p class='content'></p>").text(c.content);
  var status = c.statusInfo.length > c.status ? c.statusInfo[c.status] : {};
  
  el.addClass(status.cls)
  .append(table
      .append(tbody
          .append(tr
              .append(tdl)
              .append(tdc)
              .append(tdr))));
  
  tdc.append(content).append(times);
  
  if (c.limit >= 120) {
    el.addClass("long");
  }
  
  function doTimes() {
    tdl.html("<p class='date'>" + c.day + "</p><p class='time'>" + mins2Time(c.start) + "</p>");
    tdr.html("<p class='date'>" + c.day + "</p><p class='time'>" + mins2Time(c.start + c.limit) + "</p>");
    if (c.status >= 4) {
      times.text(((c.limit / 6) ^ 0) / 10 + "H");
    }
    else {
      times.text(mins2Time(c.start) + "-" + mins2Time(c.start + c.limit) + "(" + ((c.limit / 6) ^ 0) / 10 + "H)");
    }
    //times.text(((c.limit / 6) ^ 0) / 10 + "H");
  }
  
  function mins2Time(i) {
    var h = (i / 60 ) ^ 0;
    var m = (i % 60) ^ 0;
    m = m < 10 ? "0" + m : m;
    return h + ":" + m;
  }
  
  doTimes();
  
  function refreshTimes(start) {
    c.start = ((start || 0) + el.position().left / c.ppm) ^ 0;
    c.limit = (parseInt(el.css("width")) / c.ppm) ^ 0;
    if (c.limit >= 120) {
      el.addClass("long");
    }
    else {
      el.removeClass("long");
    }
    doTimes();
  }
  
  el.click(function(e) {
    c.onclick(e, c.id, c.start, c.limit);
  });
  
  return {
    el: el,
    getStart: function() {
      return c.start;
    },
    getLimit: function() {
      return c.limit;
    },
    refreshTimes: refreshTimes,
    getId: function() {
      return c.id;
    },
    disabled: disabled,
    remove: function() {
      el.trigger("_remove");
      el.remove();
    },
    refreshView: function() {
      
    }
  }
}