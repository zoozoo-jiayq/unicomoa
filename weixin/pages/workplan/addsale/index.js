const { PG } = require("../../common/base.js")
const { formatDate } = require("../../../utils/util")
const { $wuxSelect } = require('../../../wux/index')
PG({
  data: {
    beginDate: formatDate(new Date()),
    beginTime: "12:00",
    endDate: formatDate(new Date()),
    endTime: "12:00",
    canyurenids:[],
    canyurennames:[]
  },
  changeBeginDate(e){
      this.setData({
        beginDate:e.detail.value
      })
  },
  changeBeginTime(e){
    this.setData({
      beginTime:e.detail.value
    })
  },
  changeEndDate(e){
    this.setData({
      endDate:e.detail.value
    })
  },
  changeEndTime(e){
    this.setData({
      endTime:e.detail.value
    })
  },
  showSelectUser(){
    $wuxSelect('#selectUser').open({
      multiple: true,
      options: [
        {
          title: '画画',
          value: '1',
        },
        {
          title: '打球',
          value: '2',
        },
      ],
      onConfirm: (value, index, options) => {
        console.log('onConfirm', value, index, options)
        if (index !== -1) {
          this.setData({
            canyurenids: value,
            canyurennames: index.map((n) => options[n].title),
          })
        }
      },
    })
  }
})