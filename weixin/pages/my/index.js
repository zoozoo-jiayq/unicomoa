import { $wuxDialog } from '../../wux/index'
const { PG, REQ } = require("../common/base_1.js")
const CONFIG = require("../common/config_1.js")
Page({
  /**
   * 页面的初始数据
   */
  data: {
    dataType:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  logOut(e) {
    this.custom()
  },
  custom() {
    const alert = (content) => {
      $wuxDialog('#wux-dialog--alert').alert({
        resetOnClose: true,
        title: '提示',
        content: content
      })
    }
    var that = this;
    $wuxDialog().open({
      resetOnClose: true,
      title: '退出登录',
      content: '确定要退出登录吗?',
      buttons: [{
        text: '确定',
        type: 'primary',
        onTap(e) {
          wx.removeStorage({
            key: 'userInfo',
            success: function (res) {
              console.log(res.data)
            }
          })
        }
      },
      {
        text: '取消',
      },
      ]
    })
  }
})