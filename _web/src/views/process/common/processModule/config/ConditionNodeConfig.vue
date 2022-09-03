<template>
  <div>
    <el-form inline label-width="100px">
      <el-input v-model="config.expression" size="mini" placeholder="输入符合此节点的sql" />
      <span class="item-desc">例如：select count(1) from record where id = '{dataId}'</span>
    </el-form>
    <!-- <div>
      <el-button type="primary" size="mini" icon="el-icon-plus" style="margin-bottom: 15px" round @click="addConditionGroup">
        添加条件组
      </el-button>
      <span style="font-size: small; color: #7a7a7a; margin-left: 20px">只有必填选项才能作为审批条件</span>
    </div> -->
    <!-- <group-item /> -->
  </div>
</template>

<script>
import GroupItem from './ConditionGroupItemConfig.vue'

export default {
  name: 'ConditionNodeConfig',
  components: { GroupItem },
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
      sortOption: {
        animation: 300,
        chosenClass: 'choose',
        scroll: true,
        sort: true
      }
    }
  },
  computed: {
    selectedNode() {
      return this.$store.state.process.selectedNode
    },
    select() {
      return this.config.assignedUser || []
    },
    nowNodeLeave() {
      return this.prioritySortList.indexOf(this.selectedNode)
    },
    // 条件节点
    prioritySortList() {
      const node = this.$store.state.process.nodeMap.get(this.selectedNode.parentId)
      console.log(this.selectedNode.id, node)
      if (node) {
        return node.branchs || []
      }
      return []
    }
  },
  methods: {
    addConditionGroup() {
      this.config.groups.push({
        groupType: 'OR',
        conditions: []
      })
    },
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
.choose {
  border-radius: 5px;
  margin-top: 2px;
  background: #f4f4f4;
  border: 1px dashed #1890FF !important;
}

.drag-hover {
  background: #79bbff;
  color: #1890FF
}

.drag-no-choose {
  cursor: move;
  background: #f4f4f4;
  border-radius: 5px;
  position: relative;
  margin-top: 2px;
  padding: 5px 10px;
  border: 1px solid #bcbcbc;
  height: 20px;

  div:nth-child(1) {
    font-size: x-small;
    position: absolute;
    width: 160px;
    left: 10px;
    height: 20px;
    overflow: hidden;
  }

  div:nth-child(2) {
    position: absolute;
    right: 10px;
  }
}
</style>
