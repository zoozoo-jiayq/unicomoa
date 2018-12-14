/**
 * 中文配置
 **/
//修改默认行为可以实现国际化
var defaults = {

    header:{
        left:'prev,next today',
        center:'title',
        right:'month,agendaWeek,agendaDay'
    },
    buttonText:{ //按钮对应的文本
        prevYear:'去年', //不建议改这个值，因为它本身是含[去年、上一周、前天]三个意思，你就让它默认
        nextYear:'明年', //同上
        today:'今天',
        month:'月',
        week:'周',
        day:'日'
    },
    monthNames:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'], //默认为英文月分，这里可以改成中文
    monthNamesShort:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
    dayNames:['周日', '周一', '周二', '周三', '周四', '周五', '周六'], //短格式的星期
    dayNamesShort:['周日', '周一', '周二', '周三', '周四', '周五', '周六'], //短格式的星期
    titleFormat:{ //格式化标题
        month:'MMMM yyyy', // 如：September 2009
        week:"MMM d[yyyy]{'—'[ MMM] d yyyy}", // 如：Sep 7 - 13 2009
        day:'dddd, MMM d, yyyy' // 如：Tuesday, Sep 8, 2009
    },

    allDayText:'全天',
    selectable:true,
    selectHelper:true,
    editable:true

}