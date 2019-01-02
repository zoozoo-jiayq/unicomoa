import { $wuxDialog } from '../../../wux/index'
const { PG, REQ, loginUser } = require("../../common/base_1.js")
const CONFIG = require("../../common/config_1.js")
var bmap = require('../../common/bmap-wx.js');
var wxMarkerData = []; //定位成功回调对象
Page({
  data: {
    dakaType:null,
    seconds: 0,
    time: '00:00:00',
    week:"",
    date:"",
    day:"",
    signInButton:true,
    signOutButton:true,
    setLongitude: '', //经度
    setLatitude: '', //纬度
    range:'',
    longitude: '', //经度
    latitude: '', //纬度
    address: '', //地址
    cityInfo: {}, //城市信息
    markers: [],
    isHasPlan:true,
    outOfRange:0,
    attList:null,
    refreshTimeInterval:null
  }, 
  calDistance: function (obj){//计算两者之间距离 判断是否为外勤打卡
    var range = obj.data.range;
    var lat1 = obj.data.setLongitude;
    var lat2 = obj.data.longitude;
    var lng1 = obj.data.setLatitude;
    var lng2 = obj.data.latitude;
    var radLat1 = (lat1*Math.PI)/180.0;
    var radLat2 = (lat2 * Math.PI) / 180.0;
    var radLng1 = (lng1 * Math.PI) / 180.0;
    var radLng2 = (lng2 * Math.PI) / 180.0;
    var a = radLat1 - radLat2;
    var b = radLng1 - radLng2;
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
    s = s * 6378.137;
    // EARTH_RADIUS;
    s = Math.round(s * 10000) / 10000;
    s = Math.abs(s*1000);
    if (s > range){
      obj.setData({
        outOfRange:1
      });
    } 
  },
  getServiceTime:function(obj){//获取服务器时间和日期及星期
    REQ({
      isShowLoading: 1,
      method: "post",
      url: "/attendance/getServiceTime.action?_clientType=wap",
      data: {},
    }).then(res => {
      var result = res.data;
      if (result != "") {
        var timeArr = result.split(">");
        obj.setData({
          week: timeArr[2],
          date: timeArr[0],
          day: timeArr[7],
          time: timeArr[4],
          seconds: timeArr[5]
        })
      }
    })
  },
  getLocation:function(obj){//获取地理位置
    var that = obj;
    var BMap = new bmap.BMapWX({ 
      ak: '4rUyHiV7zfCtfqTg7v27b7nIYZqhv2ND'
    });
    var fail = function (data) { 
      console.log(data);
    };
    var success = function (data) {
      //返回数据内，已经包含经纬度
      console.log(data);
      //使用wxMarkerData获取数据
      wxMarkerData = data.wxMarkerData;
      //把所有数据放在初始化data内 
      that.setData({
        markers: wxMarkerData,
        latitude: wxMarkerData[0].latitude,
        longitude: wxMarkerData[0].longitude,
        address: wxMarkerData[0].address,
        cityInfo: data.originalData.result.addressComponent
      });
    }
    // 发起regeocoding检索请求
    BMap.regeocoding({
      fail: fail,
      success: success
    });
  },
  initAttendance: function (obj) {//加载考勤数据
    var userInfo = loginUser();
    REQ({
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: "post",
      url: "/attWap/init.action?_clientType=wap",
      data: { userId: userInfo.userId, companyId: userInfo.companyId },
    }).then(res => {
      var result = res.data;
      console.log(result);
      if (result != "") {
        var resArr = res.data.split("||");
        var status = resArr[0];
        if (status == "100") {
          var attendance = JSON.parse(resArr[1]);
          var hasPlan = attendance.hasPlan;
          obj.setData({
            setLongitude: attendance.longitude,
            setLatitude: attendance.latitude,
            range: attendance.range,
            attList: attendance.list
          });
          if (hasPlan != "1") {
            obj.setData({
              isHasPlan: false
            });
            return;
          }
          var signIn = attendance.signIn;
          var signOut = attendance.signOut;
            obj.setData({
              signInButton: signIn == "1" ? false : true,
              signOutButton: signOut == "1" ? false : true 
            });
        }
      }
    })
  },
  refreshTime:function(){  //刷新系统时间
    this.getServiceTime(this);
  } ,
  onLoad: function () { 
    var userInfo = wx.getStorageSync('userInfo');
    if (userInfo == null || userInfo.userId == '') {
      wx.showToast({
        title: '用户名不能为空',
        icon: "none"
      });
      wx.switchTab({
        url: '../../login/index'
      })
      return;
    }
    this.getServiceTime(this);
    this.initAttendance(this); 
    this.getLocation(this); 
    this.setData({
      refreshTimeInterval:setInterval(this.refreshTime, 1000)
    });
  },
  onUnload:function() {
    clearInterval(this.data.refreshTimeInterval);
  },
  view(){
      wx.navigateTo({ 
        url: '../view/index'
      })
  },
  rank() {
    wx.navigateTo({
      url: '../rank/index'
    })
  },
  daka(e){//考勤打卡
    this.setData({
      dakaType: e.currentTarget.dataset["type"]
    })
    this.calDistance(this);
    this.custom()
  },
  custom() {
    const alert = (content) => {
      $wuxDialog('#wux-dialog--alert').alert({
        resetOnClose: true,
        title: '提示',
        content: content,
      })
    }
    var that = this;
    $wuxDialog().open({ 
      resetOnClose: true,
      title: this.data.dakaType == "am"?"上班打卡":"下班打卡",
      content: this.data.date+' '+this.data.time+':'+this.data.seconds+'（地点：'+this.data.address+'）',
      buttons: [{
        text: '确定',
        type: 'primary',
        onTap(e) {
        that.getLocation(that);
        var attType = 10;
        if (that.data.dakaType == "pm"){
          attType = 21;
        }
        var userInfo = loginUser();
        var paramData = {
          userId: userInfo.userId,
          companyId: userInfo.companyId,
          position:that.data.address,
          longitude: that.data.longitude,
          latitude: that.data.latitude,
          attType: attType,
          outOfRange: that.data.outOfRange
        };
        REQ({
            header: {
              "Content-Type": "application/x-www-form-urlencoded"
            },
            method: "post",
            url: "/attWap/saveRecord.action?_clientType=wap",
            data: paramData,
          }).then(res => {
            var result = res.data;
            console.log(result);
            if (result != "") {
              var resArr = res.data.split("||");
              var status = resArr[0]; 
              if (status == "100") {
                alert('打卡成功！');
                that.initAttendance(that);
              }
            }
          })


        },
      },
      {
        text: '取消',
      },
      ],
    })
  }
})



