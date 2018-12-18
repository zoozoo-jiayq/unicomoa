const CONFIG = require("./config.js")
module.exports = {
  PG: function(args) {
    var defaultargs = {
      onPullDownRefresh: function() {
        wx.showNavigationBarLoading();
        wx.hideNavigationBarLoading();
        wx.stopPullDownRefresh();
      }
    }
    for (var k in args) {
      defaultargs[k] = args[k];
    }
    Page(defaultargs);
  },
  REQ: function(arg) {
    wx.showLoading({
      title: '数据处理中',
    })
    arg.url = CONFIG.server + arg.url;
    arg.complete = function() {
      wx.hideLoading()
    }
    arg.fail = function() {
      wx.showToast({
        title: "服务异常,请稍后重试!"
      })
    }
    wx.request(arg)
  }
}