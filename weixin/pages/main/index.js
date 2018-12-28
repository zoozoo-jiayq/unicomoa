// pages/main/index.js
const {PG} = require("../common/base.js");
PG({

  /**
   * 页面的初始数据
   */
  data: {

  },
  goworkplan(){
    wx.switchTab({
      url: '../workplan/index/index',
    })
  },
  godaka(){
    wx.navigateTo({
      url: '../daka/index/index',
    })
  }
})