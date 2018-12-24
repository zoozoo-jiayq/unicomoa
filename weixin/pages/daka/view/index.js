const {
  PG,
  REQ
} = require("../../common/base.js")
// pages/daka/view/index.js
PG({
  /**
   * 页面的初始数据
   */
  data: {
      year:"",  
      month:"",
      changeMonthType:"",
      currDate:null,
      normalCounts:0,
      lateCounts:0,
      lackCounts:0,
      leaveCounts:0,
      attList: null
  },
  loadAttendance:function(obj){
    var paramData = { userId: 12, companyId: 1, year: obj.data.year, month: obj.data.month};
    REQ({
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: "post",
      url: "/attWap/recordReport.action?_clientType=wap",
      data: paramData,
      success: function (res) {
        var result = res.data;
        if (result != "") {
         console.log(result);
         var resArr = result.split("||");
         var status = resArr[0];
         if (status == "100") {
           var attendance = JSON.parse(resArr[1]);
           obj.setData({
             normalCounts: attendance.normalCounts,
             lateCounts: attendance.lateCounts,
             lackCounts: attendance.lackCounts,
             leaveCounts: attendance.leaveCounts,
             attList: attendance.list
           });
         }else{
           wx.showToast({
             title: resArr[0],
             icon: "none"
           });
         }
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
    this.setData({
      currDate:myDate,
      year:year,
      month:month
    });
    this.loadAttendance(this);
  },
  changeMonth(e) {
    this.setData({
      changeMonthType: e.currentTarget.id
    });
    var num = 1;
    if (this.data.changeMonthType =="reduce"){
      num = -1;
    }
    var sDate = this.dateToDate(this.data.currDate);
    var sYear = sDate.getFullYear();
    var sMonth = sDate.getMonth() + 1;
    var sDay = sDate.getDate();
    var eYear = sYear;
    var eMonth = sMonth + num;
    var eDay = sDay;
    while (eMonth > 12) {
      eYear++;
      eMonth -= 12;
    }
    if(eMonth==0){
      eMonth = 12;
      eYear--;
    }
    var eDate = new Date(eYear, eMonth - 1, 1); 
    var year = eDate.getFullYear();
    var month = eDate.getMonth() + 1;
    this.setData({ 
      currDate: eDate,
      year: year,
      month: month
    });
    this.loadAttendance(this);
  },
  dateToDate:function(date){
    var sDate = new Date();
    if (typeof date == 'object'
      && typeof new Date().getMonth == "function"
    ) {
      sDate = date;
    }
    else if (typeof date == "string") {
      var arr = date.split('-')
      if (arr.length == 3) {
        sDate = new Date(arr[0] + '-' + arr[1] + '-' + arr[2]);
      }
    }
    return sDate;
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

  }
})