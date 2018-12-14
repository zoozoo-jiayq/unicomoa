/**
 * 警告
 * @param	{String}	消息内容
 */
artDialog.alert = function (content) {
    return artDialog({
        id: 'Alert',
        icon: 'warning',
        fixed: true,
        lock: true,
        opacity: 0,
        content: content,
        ok: true
    });
};

/**
 * 确认
 * @param	{String}	消息内容
 * @param	{Function}	确定按钮回调函数
 * @param	{Function}	取消按钮回调函数
 */
artDialog.confirm = function (content, yes, no) {
    return artDialog({
        id: 'Confirm',
        icon: 'question',
        fixed: true,
        lock: true,
        opacity: .1,
        content: content,
        ok: function (here) {
            return yes.call(this, here);
        },
        cancel: function (here) {
            return no && no.call(this, here);
        }
    });
};


/**
 * 提问
 * @param	{String}	提问内容
 * @param	{Function}	回调函数. 接收参数：输入值
 * @param	{String}	默认值
 */
artDialog.prompt = function (content, yes, value) {
    value = value || '';
    var input;

    return artDialog({
        id: 'Prompt',
        icon: 'question',
        fixed: true,
        lock: true,
        opacity: .1,
        content: [
            '<div style="margin-bottom:5px;font-size:12px">',
            content,
            '</div>',
            '<div>',
            '<input value="',
            value,
            '" style="width:18em;padding:6px 4px" />',
            '</div>'
        ].join(''),
        init: function () {
            input = this.DOM.content.find('input')[0];
            input.select();
            input.focus();
        },
        ok: function (here) {
            return yes && yes.call(this, input.value, here);
        },
        cancel: true
    });
};


/**
 * 短暂提示
 * @param	{String}	提示内容
 * @param	{Number}	显示时间 (默认1.5秒)
 */
artDialog.tips = function (content, time) {
    return artDialog({
        id: 'Tips',
        title: false,
        cancel: false,
        fixed: true,
        lock: false
    })
        .content('<div style="padding: 0 1em;">' + content + '</div>')
        .time(time || 1.5);
};