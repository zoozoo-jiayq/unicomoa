const {
  PG,
  REQ
} = require("../../common/base.js")
const {
  formatDate
} = require("../../../utils/util")
const {
  $wuxSelect
} = require('../../../wux/index')

PG({
  data: {
    spinning: false,
    workplan: {
      planType: "sale",
      beginDate: formatDate(new Date()),
      beginTime: "10:00",
      endDate: formatDate(new Date()),
      endTime: "18:00",
      target: 15,
      createrId: 1,
      createrName: "张三"
    }
  },
  setValue(key, value) {
    var wp = this.data.workplan;
    wp[key] = value;
    this.setData({
      workplan: wp
    })
  },
  selectPlanType(e) {
    this.setValue("planType", e.detail.value);
  },
  selectAllOneDay(e) {
    this.setValue("oneDay", e.detail.value);
  },
  changeBeginDate(e) {
    this.setValue("beginDate", (e.detail.value))
  },
  changeBeginTime(e) {
    this.setValue("beginTime", e.detail.value)
  },
  changeEndDate(e) {
    this.setValue("endDate", e.detail.value)
  },
  changeEndTime(e) {
    this.setValue("endTime", e.detail.value)
  },
  showSelectUser() {
    $wuxSelect('#selectUser').open({
      multiple: true,
      options: [{
          title: '画画',
          value: '1',
        },
        {
          title: '打球',
          value: '2',
        },
      ],
      onConfirm: (value, index, options) => {
        if (index !== -1) {
          this.setValue("canyurenIds", value);
          this.setValue("canyurennames", index.map((n) => options[n].title))
        }
      },
    })
  },
  inputAddr(e) {
    this.setValue("addr", e.detail.value)
  },
  inputContent(e) {
    this.setValue("content", e.detail.value)
  },
  changeTarget(e) {
    this.setValue("target", e.detail.value)
  },
  check() {
    var wp = this.data.workplan;
    if (!wp.canyurenIds) {
      wx.showToast({
        title: '参与人不能为空',
        icon:"none"
      });
      return false;
    }
    if(!wp.addr){
      wx.showToast({
        title: '活动地址不能为空',
        icon:"none"
      })
      return false;
    }
    if(!wp.content){
      wx.showToast({
        title: '工作计划不能为空',
        icon:"none"
      })
      return false;
    }
    return true;

  },
  submit() {
    var flag = this.check();
    if(!flag){
      return;
    }
    REQ({
      method: "post",
      url: "/workplan/add",
      data: this.data.workplan
    }).then(res=>{
      if (res.data.result == "success") {
        wx.switchTab({
          url: '../index/index',
        })
      }
    })

  }
})