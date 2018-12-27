const { PG, REQ } = require("../common/base_1.js")
const CONFIG = require("../common/config_1.js")
PG({
  /**
   * 页面的初始数据
   */
  data: {
    dataType:''
  },
  logout(e) {
    wx.showModal({
      title: '',
      content: '确认退出登录?',
      confirmColor:"#1296db",
      success(res) {
        if (res.confirm) {
          console.log('用户点击确定')
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  }
})