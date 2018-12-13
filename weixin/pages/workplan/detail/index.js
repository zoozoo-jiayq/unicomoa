const { PG } = require("../../common/base.js")

Page({

  data: {
    processs:[{
      title:"张三",
      content:"dsgsdvfs"
    }, {
        title: "张三",
        content: "sdjflsdjflsd"
      }]
  },
  onLoad: function (options) {
      console.log(options)
  },
  onReady: function () {
  },
  progress(){
    wx.navigateTo({
      url: '../progress/index',
    })
  }

})