<template>
  <div>
    <el-form label-position="top" label-width="90px">
      <el-form-item label="选择审批对象" prop="text" class="user-type">
        <el-radio-group v-model="nodeProps.assignedType">
          <el-radio v-for="t in approvalTypes" :key="t.type" :label="t.type">{{ t.name }}</el-radio>
        </el-radio-group>
        <div v-if="nodeProps.assignedType === 'ASSIGN_USER'">
          <el-button size="mini" icon="el-icon-plus" type="primary" round @click="selectUser">选择人员</el-button>
        </div>
        <div v-else-if="nodeProps.assignedType === 'SELF_SELECT'">
          <el-radio-group v-model="nodeProps.selfSelect.multiple" size="mini">
            <el-radio-button :label="false">自选一个人</el-radio-button>
            <el-radio-button :label="true">自选多个人</el-radio-button>
          </el-radio-group>
        </div>
        <div v-else-if="nodeProps.assignedType === 'LEADER_TOP'">
          <el-divider />
          <el-form-item label="审批终点" prop="text" class="approve-end">
            <el-radio-group v-model="nodeProps.leaderTop.endCondition">
              <el-radio label="TOP">直到最上层主管</el-radio>
              <el-radio label="LEAVE">不超过发起人的</el-radio>
            </el-radio-group>
            <div v-if="nodeProps.leaderTop.endCondition === 'LEAVE'" class="approve-end-leave">
              <span>第 </span>
              <el-input-number v-model="nodeProps.leaderTop.level" :min="1" :max="20" :step="1" size="mini" />
              <span> 级主管</span>
            </div>
          </el-form-item>
        </div>
        <div v-else-if="nodeProps.assignedType === 'LEADER'">
          <el-divider />
          <el-form-item label="指定主管" prop="text">
            <span>发起人的第 </span>
            <el-input-number
              v-model="nodeProps.leader.level"
              :min="1"
              :max="20"
              :step="1"
              size="mini"
            />
            <span> 级主管</span>
            <div style="color: #409EFF; font-size: small;">👉 直接主管为 第 1 级主管</div>
          </el-form-item>
        </div>
        <div v-else-if="nodeProps.assignedType === 'ROLE'">
          <el-button size="mini" icon="el-icon-plus" type="primary" round @click="selectUser">选择系统角色</el-button>

        </div>
        <div v-else>
          <span class="item-desc">发起人自己作为审批人进行审批</span>
        </div>
      </el-form-item>

      <el-divider />
      <el-form-item label="审批人为空时" prop="text" class="line-mode">
        <el-radio-group v-model="nodeProps.nobody.handler">
          <el-radio label="TO_PASS">自动通过</el-radio>
          <el-radio label="TO_REFUSE">自动驳回</el-radio>
          <el-radio label="TO_ADMIN">转交审批管理员</el-radio>
          <el-radio label="TO_USER">转交到指定人员</el-radio>
        </el-radio-group>

        <div v-if="nodeProps.nobody.handler === 'TO_USER'" style="margin-top: 10px">
          <el-button size="mini" icon="el-icon-plus" type="primary" round @click="selectUser">选择人员</el-button>
        </div>

      </el-form-item>

      <div v-if="showMode">
        <el-divider />
        <el-form-item label="多人审批时审批方式" prop="text" class="approve-mode">
          <el-radio-group v-model="nodeProps.mode">
            <el-radio label="NEXT">按选择顺序依次审批</el-radio>
            <el-radio label="AND">会签（可同时审批，每个人必须同意）</el-radio>
            <el-radio label="OR">或签（有一人同意即可）</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>

      <el-divider>高级设置</el-divider>
      <el-form-item label="审批同意时是否需要签字" prop="text">
        <el-switch v-model="nodeProps.sign" inactive-text="不用" active-text="需要" />
        <el-tooltip class="item" effect="dark" content="如果全局设置了需要签字，则此处不生效" placement="top-start">
          <i class="el-icon-question" style="margin-left: 10px; font-size: medium; color: #b0b0b1" />
        </el-tooltip>
      </el-form-item>
      <el-form-item label="审批期限（为 0 则不生效）" prop="timeLimit">
        <el-input v-model="nodeProps.timeLimit.timeout.value" style="width: 180px;" placeholder="时长" size="small" type="number">
          <el-select slot="append" v-model="nodeProps.timeLimit.timeout.unit" style="width: 75px;" placeholder="请选择">
            <el-option label="天" value="D" />
            <el-option label="小时" value="H" />
          </el-select>
        </el-input>
      </el-form-item>
      <el-form-item v-if="nodeProps.timeLimit.timeout.value > 0" label="审批期限超时后执行" prop="level">
        <el-radio-group v-model="nodeProps.timeLimit.handler.type">
          <el-radio label="PASS">自动通过</el-radio>
          <el-radio label="REFUSE">自动驳回</el-radio>
          <el-radio label="NOTIFY">发送提醒</el-radio>
        </el-radio-group>
        <div v-if="nodeProps.timeLimit.handler.type === 'NOTIFY'">
          <div style="color:#409EEF; font-size: small">默认提醒当前审批人</div>
          <el-switch v-model="nodeProps.timeLimit.handler.notify.once" inactive-text="循环" active-text="一次" />
          <span v-if="!nodeProps.timeLimit.handler.notify.once" style="margin-left: 20px">
            每隔
            <el-input-number v-model="nodeProps.timeLimit.handler.notify.hour" :min="0" :max="10000" :step="1" size="mini" />
            小时提醒一次
          </span>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  name: 'ApprovalNodeConfig',
  props: {
    config: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      showOrgSelect: false,
      approvalTypes: [
        { name: '指定人员', type: 'ASSIGN_USER' },
        { name: '发起人自选', type: 'SELF_SELECT' },
        { name: '连续多级主管', type: 'LEADER_TOP' },
        { name: '主管', type: 'LEADER' },
        { name: '角色', type: 'ROLE' },
        { name: '发起人自己', type: 'SELF' }
      ]
    }
  },
  computed: {
    nodeProps() {
      return this.$store.state.process.selectedNode.props
    },
    select() {
      return this.config.assignedUser || []
    },
    showMode() {
      switch (this.nodeProps.assignedType) {
        case 'ASSIGN_USER':
          return this.nodeProps.assignedUser.length > 0
        case 'SELF_SELECT':
          return this.nodeProps.selfSelect.multiple
        case 'LEADER_TOP':
          return true
        case 'ROLE':
          return true
        default:
          return false
      }
    }
  },
  methods: {
    closeSelect() {

    },
    selectUser() {
      this.showOrgSelect = true
    },
    selected(select) {
      console.log(select)
      this.showOrgSelect = false
      select.forEach(val => this.select.push(val))
    },
    removeOrgItem(index) {
      this.select.splice(index, 1)
    }
  }
}
</script>

<style lang="stylus" scoped>
.user-type {
  /deep/ .el-radio {
    width: 110px;
    margin-top: 10px;
    margin-bottom: 20px;
  }
}

/deep/ .line-mode{
  .el-radio{
    width: 150px;
    margin: 5px;
  }
}

/deep/ .el-form-item__label{
  line-height: 25px;
}

/deep/ .approve-mode {
  .el-radio {
    float: left;
    width: 100%;
    display: block;
    margin-top: 15px;
  }
}

/deep/ .approve-end {
  position: relative;

  .el-radio-group {
    width: 160px;
  }

  .el-radio {
    margin-bottom: 5px;
    width: 100%;
  }

  .approve-end-leave {
    position: absolute;
    bottom: -5px;
    left: 150px;
  }
}

/deep/ .el-divider--horizontal {
  margin: 10px 0;
}
</style>
