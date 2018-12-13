// pages/login/index.js
Page({

  data: {
    remembered: false
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
  loginA(e){
    console.log(e)
    wx.switchTab({
      url: '../workplan/index/index',
    })
  }
})