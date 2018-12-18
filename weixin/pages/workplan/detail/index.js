const { PG,REQ } = require("../../common/base.js")

Page({

  data: {
    id:null,
    plan:{},
    processs:[{
      title:"张三",
      content:"dsgsdvfs"
    }, {
        title: "张三",
        content: "sdjflsdjflsd"
      }]
  },
  onLoad: function (options) {
    this.setData({
      id:options.id
    })
    this.refresh();
  },
  onPullDownRefresh:function(){
    wx.showNavigationBarLoading();
    this.refresh(()=>{
      wx.hideNavigationBarLoading();
      wx.stopPullDownRefresh();
    })
  },
  refresh(cb){
    REQ({
      url:"/workplan/detail?id="+this.data.id,
      success:(res)=>{
        if(res.data.result == "success"){
          var data = res.data.data;
          var canyureninfo = data.canyuren;
          canyureninfo = JSON.parse(canyureninfo);
          canyureninfo = canyureninfo.map(e=>{
            return e.name;
          }).join(",");
          data.canyuren = canyureninfo;
          this.setData({
            plan:data
          })
        }
      }
    })
  },
  progress(){
    wx.navigateTo({
      url: '../progress/index',
    })
  }

})