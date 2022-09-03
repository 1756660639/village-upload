<template>
  <div class="menutree">
    <label v-for="menu in menuList" :key="menu.id">
      <el-submenu v-if="menu.children.length>0" :index="menu.id">
        <template slot="title">
          <span>{{ menu.name }}</span>
        </template>
        <label>
          <Menutree :menu-list="menu.children" />
        </label>
      </el-submenu>
      <el-menu-item v-else :index="menu.path">
        <span slot="title">{{ menu.name }}</span>
      </el-menu-item>
    </label>
  </div>
</template>
<script>

export default {
  name: 'Menutree',
  components: {
  },
  props: {
    menuList: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
    }
  }
}
</script>
