const {PG} = require("../../common/base.js")
const { $wuxCalendarPlain } = require( '../../../wux/index')
const { formatDate} = require("../../../utils/util")
Page({
  data: {
    selectedDate: "sdfooosf",
    list:[{
      id:"1",
      title:"拜访客户拜访客户拜访客户拜访客户",
      canyuren:"张三，李四，王五"
    },{
      id:"2",
      title:"拜访客户拜访客户拜访客户拜访客户拜访客户",
      canyuren:"sfsfs"
    },
      {
        id: "3",
        title: "拜访客户拜访客户拜访客户拜访客户拜访客户",
        canyuren: "sfsfs"
      }]
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
  },
  search(){
    wx.navigateTo({
      url: '../search/index',
    })
  },
  onShow: function () {
  }
})