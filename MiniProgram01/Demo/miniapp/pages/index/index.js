Page({
  data: {
    studentId: '',
    studentName: ''
  },
  onInput(e) {
    this.setData({
      studentId: e.detail.value
    });
  },
  onSubmit() {
    const that = this;
    if (!this.data.studentId) {
      wx.showToast({
        title: '请输入学号',
        icon: 'none'
      });
      return;
    }
    wx.request({
      url: 'http://localhost:8080/api/student/name', // 后端接口地址
      method: 'GET',
      data: {
        studentId: this.data.studentId
      },
      success(res) {
        if (res.data && res.data.name) {
          that.setData({
            studentName: res.data.name
          });
        } else {
          that.setData({
            studentName: '未找到该学生'
          });
        }
      },
      fail() {
        wx.showToast({
          title: '请求失败',
          icon: 'none'
        });
      }
    });
  }
}); 