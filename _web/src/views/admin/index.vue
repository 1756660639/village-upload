<template>
  <div id="menu">
    <!-- <div style="width:100%; height:40px; backgroundColor:red">1</div> -->
    <el-col class="menu_left" :span="4">
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        :router="true"
        @open="handleOpen"
        @close="handleClose"
      >
        <menutree :menu-list="menuList" />
      </el-menu>
    </el-col>
    <div class="el-col-20 app-container"><router-view /></div>
  </div>
</template>
<script>

import menutree from '@/views/admin/common/menuTree.vue'
import { queryAllMenu } from '@/api/admin/menu/index.js'
import util from '@/utils/util.js'

export default {
  name: 'SysAdminIndex',
  components: {
    menutree: menutree
  },
  data() {
    return {
      menuList: null
    }
  },
  created() {
    this.queryAllMenu()
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath)
    },
    queryAllMenu() {
      queryAllMenu({}).then(response => {
        if (response.success) {
          this.menuList = response.data
        } else {
          util.errorMessage
        }
      })
    }
  }
}
</script>

<style lang="stylus" scoped>
.menu_left{
  ul{
    height: 100vh
  }
}
.app-container{
  padding 20px;
}
</style>
