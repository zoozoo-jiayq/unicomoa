// pages/login/index.js

const { PG, REQ, loginUser } = require("../common/base_1.js")
const CONFIG = require("../common/config_1.js")
Page({
  data: {
    remembered: false,
    username:"",
    password:""
  },
  onLoad: function (options) {
    var userInfo = loginUser();
    if (userInfo != null && userInfo.userId!=''){
      wx.switchTab({
        url: '../workplan/index/index',
      })
    }
  },
  remember(e){
    if(e.detail.value && e.detail.value[0] == '1'){
      this.setData({
        remembered:true
      })
    }else{
      this.setData({
        remembered:false
      })
    }
  },
  bindUserName(e) {
    this.setData({
      username: e.detail.value
    })
  },
  bindPassword(e) {
    this.setData({
      password: e.detail.value
    })
  },
  check() {
    var username = this.data.username;
    var password = this.data.password;
    if (!username) {
      wx.showToast({
        title: '用户名不能为空',
        icon: "none"
      });
      return false;
    }
    if (!password) {
      wx.showToast({
        title: '密码不能为空',
        icon: "none"
      })
      return false;
    }
    return true;
  },
  loginA(e){
    console.log(e);
    var flag = this.check();
    if (!flag) {
      return;
    }
  REQ({
      method: "post",
      url: "/wap/loginAjax.action?userName=" + this.data.username + "&passWord=" + this.data.password,
      data: { userName: this.data.username, passWord: this.data.password }
    }).then(res => {
      console.log(res.data);
      var resArr = res.data.split("||");
      var status = resArr[0];
      if (status == "100") {
        var userInfoText = resArr[1];
        var userInfo = JSON.parse(userInfoText);
        wx.setStorageSync('userInfo', userInfo);
        wx.switchTab({
          url: '../workplan/index/index',
        })
      } else {
        wx.showToast({
          title: resArr[1],
          icon: "none"
        })
      }
    })
  }
})