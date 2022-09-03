<template>
  <div>
    <el-button size="mini" icon="el-icon-plus" type="primary" round @click="selectOrg">选择抄送人</el-button>
    <div class="option">
      允许发起人添加抄送人:
      <el-switch v-model="config.shouldAdd" />
    </div>
    <div style="margin-top: 20px">
      <el-tag
        v-for="(org, index) in select"
        :key="index + '_org'"
        class="org-item"
        :type="org.type === 'dept'?'':'info'"
        closable
        size="mini"
        @close="removeOrgItem(index)"
      >
        {{ org.name }}
      </el-tag>
    </div>
    <!-- <org-picker :show="showOrgSelect" :selected="select" @close="closeSelect" @selectOver="selected" /> -->
  </div>
</template>

<script>
// import orgPicker from '@/components/common/organizationPicker'
export default {
  name: 'CcNodeConfigVue',
  // components: { orgPicker },
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
      showOrgSelect: false
    }
  },
  computed: {
    select() {
      return this.config.assignedUser || []
    }
  },
  methods: {
    closeSelect() {

    },
    selectOrg() {
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
.option{
  color: #606266;
  margin-top: 20px;
  font-size: small;
}

.desc{
  font-size: small;
  color: #8c8c8c;
}
.org-item{
  margin: 5px;
}
</style>
