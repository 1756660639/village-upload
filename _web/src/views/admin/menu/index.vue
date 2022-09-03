<template>
  <div>
    <el-table
      :data="menuList"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column
        prop="orderNum"
        label="排序"
        sortable
        width="180"
      />
      <el-table-column
        prop="name"
        label="菜单名称"
        sortable
        width="180"
      />
      <el-table-column
        prop="path"
        label="菜单路径"
      />
    </el-table>

  </div>
</template>

<script>
import { queryAllMenu } from '@/api/admin/menu/index.js'
import { errorMessage } from '@/utils/util.js'
export default {
  name: 'Menu',
  data() {
    return {
      menuList: []
    }
  },
  created() {
    this.queryAllMenu()
  },
  methods: {
    queryAllMenu() {
      queryAllMenu({}).then(response => {
        if (response.success) {
          this.menuList = response.data
        } else {
          errorMessage()
        }
      })
    }
  }
}
</script>
