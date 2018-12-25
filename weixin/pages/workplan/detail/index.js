const { PG,REQ } = require("../../common/base.js")
const CONFIG = require("../../common/config.js")
Page({

  data: {
    id:null,
    plan:{},
    progress:[],
    server:CONFIG.server,
    qrcodeurl:null,
    showdialog:false
  },
  onLoad: function (options) {
    this.setData({
      id:options.id
    })
    this.setData({
      qrcodeurl:CONFIG.server+"/customer/index?id="+this.data.id
    })
    this.refresh();
  },
  onPullDownRefresh:function(){
    wx.showNavigationBarLoading();
    this.refresh(()=>{
      wx.hideNavigationBarLoading();
      wx.stopPullDownRefresh();
    })
  },
  refresh(cb){
    REQ({
      url:"/workplan/detail?id="+this.data.id,
    }).then(res=>{
      if (res.data.result == "success") {
        this.setData({
          progress: res.data.data.progress
        })
        var data = res.data.data.plan;
        var canyureninfo = data.canyuren;
        canyureninfo = JSON.parse(canyureninfo);
        canyureninfo = canyureninfo.map(e => {
          return e.name;
        }).join(",");
        data.canyuren = canyureninfo;
        this.setData({
          plan: data
        })
      }
      cb?cb():null;
    })
  },
  goprogress(){
    wx.navigateTo({
      url: '../progress/index?id='+this.data.id,
    })
  },
  qrcode(){
      this.setData({
        showdialog:true
      })
  },
  hidedialog(){
    this.setData({
      showdialog:false
    })
  },
  domore(){
    var self = this;
    wx.showActionSheet({
      itemList: ["结束计划","删除计划"],
      itemColor: "#1296db",
      success(res){
        var promise = null;
        if(res.tapIndex == 0){
          promise = REQ({
            url:"/workplan/end?id="+self.data.id
          })
        }else if(res.tapIndex == 1){
          promise = REQ({
            url:"/workplan/del?id="+self.data.id
          })
        }
        promise.then(res=>{
          wx.switchTab({
            url: '../index/index',
          })
        })
      }
    })
  }
})