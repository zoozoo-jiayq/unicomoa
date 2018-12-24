// pages/login/index.js

const {
  PG,
  REQ
} = require("../common/base.js")


PG({

  data: {
    remembered: false,
    username:"",
    password:""
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
      url: "/wap/loginAjax.action?userName=" + this.data.username + "&passWord=" +    this.data.password,
      data: { userName: this.data.username, passWord:this.data.password},
      success: function (res) {
        console.log(res.data);
        var resArr = res.data.split("||");
        var status = resArr[0];
        if (status=="100"){
          var userInfoText = resArr[1];
          var userInfo = JSON.parse(userInfoText);
          console.log(userInfo.userId);
          wx.setStorageSync('userInfo', userInfo);
          //userInfo = wx.getStorageSync('userInfo')

          wx.switchTab({
            url: '../workplan/index/index',
          })
        }else{
          wx.showToast({
            title: resArr[1],
            icon: "none"
          })
        }
      }
    })
  }
})