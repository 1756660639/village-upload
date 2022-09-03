<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-edit-outline" /> 定时任务</span>
        <task />
      </el-tab-pane>
      <el-tab-pane name="second">
        <span slot="label"><i class="el-icon-date" /> 定时任务log</span>
        <tasklog />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import task from './task/task.vue'
import tasklog from './task/tasklog.vue'
export default {
  components: { task, tasklog },
  data() {
    return {
      activeName: 'first'
    }
  }
}
</script>
<style lang="stylus" scoped>
.app-container {
    padding: 20px;
}
</style>
