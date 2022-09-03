# 

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run dev
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).


流程使用说明
1.添加表单  

2.审批流设置
 a.条件分值 填写sql 判断走那个分值 例：select count(1) from flow_lcgl 后台判断如果有符合数据》0 则走此分支

3.在需要审批的页面数据提交后调用审批发起页面
// dataId 业务数据主键
// lcid 审批流id
// type 类型 update(设置审批人发起流程) detail(查看流程审批详情) approve(审批)
// backUrl 返回按钮返回返回路径
this.$router.push({ path: '/sysAdmin/approvateUserList', query: { dataId: '566774fe-4d18-11ec-acd0-00163e1668fd', lcid: 'e5854eb62a5a4499b6047d8b4914ae69', type: 'update', backUrl: '/sysAdmin/recordList' }})

4.查看详情
this.$router.push({ path: '/sysAdmin/approvateUserList', query: { dataId: id, lcid: 'e5854eb62a5a4499b6047d8b4914ae69', type: 'detail', backUrl: '/sysAdmin/recordList' }})