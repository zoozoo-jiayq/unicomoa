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
    var realarg = arg;
    return new Promise((resolve, reject) => {
      realarg.url = CONFIG.server + arg.url;
      realarg.complete = function() {
        wx.hideLoading()
      }
      realarg.fail = function() {
        wx.showToast({
          title: "服务异常,请稍后重试!"
        })
        reject();
      }
      realarg.success = function(res) {
        resolve(res);
      }
      wx.request(realarg)
    });
  },
  UPLOAD: function(arg) {
    wx.showLoading({
      title: '文件上传中',
    })
    var realarg = arg;
    realarg.url = CONFIG.server + arg.url;
    return new Promise((resolve, reject) => {
      realarg.complete = function() {
        wx.hideLoading();
      }
      realarg.fail = function() {
        wx.showToast({
          title: '服务异常,请稍后重试!',
        })
        reject();
      }
      realarg.success = function(res) {
        resolve(res);
      }
      console.log(realarg);
      wx.uploadFile(realarg)
    });
  }
}