const { PG, REQ, loginUser } = require("../../common/base_1.js")
const CONFIG = require("../../common/config_1.js")
Page({
  /**
   * 页面的初始数据
   */
  data: {
    date:null
  },
  changeDate(e) {
    this.setData({ date: e.detail.value });
  },
  loadRankList:function(obj){ 
    var month = obj.data.month;
    if (month<10){
      month = "0"+month;
    }
    var paramData = { userId: 12, companyId: 1, selectTime: '2019-01-02'};
    REQ({
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: "post",
      url: "/attWap/rankList.action?_clientType=wap",
      data: paramData
    }).then(res => {
      var result = res.data;
      if (result != "") {
        console.log(result);
        var resArr = result.split("||");
        var status = resArr[0];
        if (status == "100") {
          var attendance = JSON.parse(resArr[1]);
         
        } else {
          wx.showToast({
            title: resArr[0],
            icon: "none"
          });
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth() + 1;
    console.log(myDate);
    this.setData({
      currDate:myDate
    });
    this.loadRankList(this);
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
    this.loadRankList(this);
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.loadRankList(this);
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})