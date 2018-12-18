const {PG,REQ} = require("../../common/base.js")
const { $wuxCalendarPlain } = require( '../../../wux/index')
const { formatDate} = require("../../../utils/util")
PG({
  data: {
    selectedDate: "sdfooosf",
    list:[],
    userId:1
  },
  addPlan(){
    wx.navigateTo({
      url: '../addsale/index'
    })
  },
  godetail(event){
    var id = event.currentTarget.dataset['id'];
    wx.navigateTo({
      url: '../detail/index?id='+id,
    })
  },
  onReady: function(){
    $wuxCalendarPlain().open({
      direction: 'vertical',
      onChange: (values, displayValues) => {
        this.setData({"selectedDate":displayValues})
      },
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
    this.list(cb);
  },
  list(cb){
    REQ({
      url:"/workplan/list?userId="+this.data.userId,
      success:(res)=>{
        if(res.data.result == "success"){
          var data = res.data.data;
          data.forEach(d=>{
            var canyureninfo = d.canyuren;
            canyureninfo = JSON.parse(canyureninfo);
            var names = canyureninfo.map(e=>{
                return e.name;
            });
            d.canyuren = names;
            d.startTime = d.startTime.split(" ")[1];
            d.endTime = d.endTime.split(" ")[1];
          })
          this.setData({
            list:data
          })
          cb?cb():null;
        }
      }
    })
  },
  search(){
    wx.navigateTo({
      url: '../search/index',
    })
  }
})