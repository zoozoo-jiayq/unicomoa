const {
  PG,
  REQ,
  UPLOAD,
  loginUser
} = require("../../common/base.js")
var bmap = require('../../common/bmap-wx.js');
PG({

  /**
   * 页面的初始数据
   */
  data: {
    id: null,
    content: null,
    userId: null,
    userName: null,
    imgs: [],
    imgsresult: [],
    address:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      id: options.id
    })
    var u = loginUser();
    if (u) {
      this.setData({
        userId: u.userId,
        userName: u.userName
      })
    }
    var BMap = new bmap.BMapWX({
      ak: '4rUyHiV7zfCtfqTg7v27b7nIYZqhv2ND'
    });
    var self = this;
    BMap.regeocoding({
      success(data){
        var wxMarkerData = data.wxMarkerData;
        self.setData({
          address: wxMarkerData[0].address
        })
      }
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },
  delimg(e) {
    var file = e.currentTarget.dataset["file"];
    var imgs = this.data.imgs;
    var result = [];
    for (var i = 0; i < imgs.length; i++) {
      if (imgs[i] != file) {
        result.push(imgs[i])
      }
    }
    this.setData({
      imgs: result
    })
  },
  chooseimg() {
    var self = this;
    wx.chooseImage({
      success(res) {
        const tempFilePaths = res.tempFilePaths
        self.setData({
          imgs: tempFilePaths
        })
      }
    })
  },
  changeContent(e) {
    this.setData({
      content: e.detail.value
    })
  },
  uploadImg(fileIter, cb) {
    var f = fileIter.next();
    if (f) {
      UPLOAD({
        url: "/upload",
        filePath: f,
        name: "file",
      }).then(res => {
        var d = JSON.parse(res.data);
        if (d.result == "success") {
          var r = this.data.imgsresult;
          r.push(d.data);
          this.setData({
            imgsresult: r
          })
        }
        this.uploadImg(fileIter, cb)
      })
    } else {
      cb()
    }
  },
  submitA() {
    if (!this.data.content) {
      wx.showToast({
        title: '处理结果不能为空',
        icon: "none"
      })
      return;
    }
    if (!this.data.imgs || this.data.imgs.length == 0) {
      wx.showToast({
        title: '图片不能为空',
        icon: "none"
      })
      return;
    }
    var fileIter = {
      data: this.data.imgs,
      index: 0,
      next: function() {
        return this.data[this.index++];
      }
    }
    this.uploadImg(fileIter, () => {
      REQ({
        url: "/workplan/progress",
        method: "post",
        data: {
          id: this.data.id,
          content: this.data.content,
          userId: this.data.userId,
          userName: this.data.userName,
          imgsresult: this.data.imgsresult,
          address:this.data.address
        }
      }).then(res => {
        wx.navigateTo({
          url: '../detail/index?id=' + this.data.id,
        })
      })
    })
  }
})