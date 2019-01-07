const {
  PG,
  REQ,
  loginUser
} = require("../../common/base.js")
const CONFIG = require("../../common/config.js")
Page({

  data: {
    id: null,
    plan: {},
    progress: [],
    server: CONFIG.server,
    qrcodeurl: null,
    showdialog: false
  },
  onLoad: function(options) {
    this.setData({
      id: options.id
    })
    this.refresh(() => {
    
    });
  },
  onPullDownRefresh: function() {
    wx.showNavigationBarLoading();
    this.refresh(() => {
      wx.hideNavigationBarLoading();
      wx.stopPullDownRefresh();
    })
  },
  refresh(cb) {
    REQ({
      url: "/workplan/detail?id=" + this.data.id,
    }).then(res => {
      if (res.data.result == "success") {
        this.setData({
          progress: res.data.data.progress
        })
        var data = res.data.data.plan;
        var canyureninfo = data.canyuren;
        if(canyureninfo){
          canyureninfo = JSON.parse(canyureninfo);
          canyureninfo = canyureninfo.map(e => {
            return e.name;
          }).join(",");
          data.canyuren = canyureninfo;
        }
        this.setData({
          plan: data
        })
      }
      cb ? cb() : null;
    })
  },
  goprogress() {
    wx.navigateTo({
      url: '../progress/index?id=' + this.data.id,
    })
  },
  qrcode(e) {
    if (this.data.plan.planType == 2) { //客户扫码评价二维码
      var pid = e.currentTarget.dataset.pid;
      this.setData({
        qrcodeurl: CONFIG.server + "/visit/index?id=" + pid
      })
    } else if (this.data.plan.planType == 1) { //营销二维码
      this.setData({
        qrcodeurl: CONFIG.server + "/sale/index?id=" + this.data.id
      })
    }
    this.setData({
      showdialog: true
    })
  },
  hidedialog() {
    this.setData({
      showdialog: false
    })
  },
  preview(e) {
    var url = e.currentTarget.dataset.url;
    this.setData({
      preview: true,
      previewurl: url
    })
  },
  hideimg() {
    this.setData({
      preview: false,
      previewurl: null
    })
  },
  domore() {
    var self = this;
    wx.showActionSheet({
      itemList: ["结束计划", "删除计划"],
      itemColor: "#1296db",
      success(res) {
        var promise = null;
        if (res.tapIndex == 0) {
          promise = REQ({
            url: "/workplan/end?id=" + self.data.id
          })
        } else if (res.tapIndex == 1) {
          promise = REQ({
            url: "/workplan/del?id=" + self.data.id
          })
        }
        promise.then(res => {
          wx.switchTab({
            url: '../index/index',
          })
        })
      }
    })
  }
})