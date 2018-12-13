import { $wuxDialog } from '../../../wux/index'
Page({
  data: {
    dakaType:null
  },
  view(){
      wx.navigateTo({
        url: '../view/index',
      })
  },
  daka(e){
    this.setData({
      dakaType: e.currentTarget.dataset["type"]
    })
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

    $wuxDialog().open({
      resetOnClose: true,
      title: this.data.dakaType == "am"?"上班打卡":"下班打卡",
      content: '2018-12-13 12:01:01（地点：收费记录是分解落实到房间里）',
      buttons: [{
        text: '确定',
        type: 'primary',
        onTap(e) {
          alert('打卡成功！')
        },
      },
      {
        text: '取消',
      },
      ],
    })
  },
})