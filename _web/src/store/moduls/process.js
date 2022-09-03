const process = {
  type: 'ROOT',
  name: '发起人',
  id: '10000',
  props: {
    // 审批人选项类型
    type: 'ASSIGN_USER',
    // 审批模式 会签/或签/依次
    mode: 'AND',
    // 审批时限
    timeLimit: {
      // 时限单位
      type: 'HOUR',
      limit: 0, // 时限值
      event: {
        type: 'PASS', // 触发超时执行事件
        loop: false, // 循环触发
        loopTime: 0 // 循环频率
      }
    },
    sign: false, // 是否需要签字
    // 如果审批人为空该如何做
    userEmpty: 'TO_PASS',
    // 主管级别
    leaderLevel: 1,
    // 结束条件
    endCondition: 'TOP',
    // 目标对象 人员/部门/角色
    targetObj: {
      // 是否多选
      multiple: false,
      // 角色
      roles: [],
      // 用户或部门
      objs: []
    }
  }
}

export default {
  namespaced: true,
  state: {
    nodeMap: new Map(),
    isEdit: null,
    selectedNode: { },
    design: {
      formId: null,
      formName: '未命名表单',
      logo: {
        icon: 'el-icon-eleme',
        background: '#1e90ff'
      },
      settings: {
        commiter: [],
        admin: [],
        sign: false,
        notify: {
          types: ['APP'],
          title: '消息通知标题'
        }
      },
      group: 0,
      formItems: [],
      process: {
        id: 'root',
        parentId: null,
        type: 'ROOT',
        name: '开始',
        desc: '任何人',
        props: {

        },
        children: {
          'id': 'node_end',
          'parentId': 'root',
          'props': { },
          'type': 'END',
          'name': '结束'
        }
      },
      remark: '备注说明'
    },
    template: {
      baseSetup: {
        icon: 'el-icon-s-custom',
        background: '#718dff',
        name: '新的审批',
        group: null,
        remark: '',
        sign: false,
        whoCommit: {
          names: [],
          values: []
        },
        whoEdit: {
          names: [],
          values: []
        },
        whoExport: [],
        notify: {
          types: [],
          title: ''
        }
      },
      // 表单设计
      form: [],
      // 流程设计
      /* process: {
         name: '发起人',
         type: 'root'
       }*/
      process: process
    }

  },
  mutations: {
    setTemplate(state, val) {
      state.template = val
    },
    clearTemplate(state) {
      state.template = {}
    },
    selectedNode(state, val) {
      state.selectedNode = val
    },
    setIsEdit(state, val) {
      state.isEdit = val
    },
    setDesign(state, val) {
      state.design = val
    }
  },
  getters: {},
  actions: {}
}
