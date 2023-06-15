/**
 * 存储流程设计相关参数
 */
export default class BpmData {
  constructor () {
    this.controls = [] // 设计器控件
    this.init()
  }

  init () {
    this.controls = [
      {
        action: 'create.start-event',
        title: '开始'
      },
      {
        action: 'create.intermediate-event',
        title: '中间'
      },
      {
        action: 'create.end-event',
        title: '结束'
      },
      {
        action: 'create.exclusive-gateway',
        title: '网关'
      },
      {
        action: 'create.task',
        title: '任务'
      },
      {
        action: 'create.user-task',
        title: '用户任务'
      },
      {
        action: 'create.call-activity',
        title: '调用活动'
      },
      {
        action: 'create.subprocess-expanded',
        title: '子流程'
      },
    ]
  }

  //  获取控件配置信息
  getControl (action) {
    const result = this.controls.filter(item => item.action === action)
    return result[0] || {}
  }
}