<template>
  <div class="onlyoffice">
    <div id="placeholder" />
  </div>
</template>

<script>
import fileApi from '@/api/file/index'
import baseUrl from '@/config/index'

export default {
  name: 'OnlyOffice',
  data() {
    return {
      docEditor: null, //  文档编辑器
      platform: 'desktop' //  查看平台
    }
  },
  computed: {
    // 文件信息，来自于路由参数query
    fileInfo() {
      return this.$route.query
    }
  },
  created() {
  },
  mounted() {
    this.$nextTick(() => {
      switch (this.fileInfo.ot) {
        // 添加
        case 'add':
          this.initOnlyoffice()
          break
        // 编辑
        case 'edit':
          this.editDoc()
          break
        // 详情
        case 'detail':
          this.showDocDetail()
          break
      }
    })
  },
  destroyed() {
    this.docEditor.destroyEditor()
  },
  methods: {
    /**
     * 初始化 查看文档
     */
    showDocDetail() {
      const data = {
        id: this.fileInfo.id
      }
      fileApi.findById(data).then((res) => {
        if (res.success) {
          const config = {
            'document': {
              'fileType': 'xls',
              'key': '138E9734B413sas',
              'title': 'kq.xls',
              'url': fileApi.download(data)
            },
            'documentType': 'cell',
            'editorConfig': {
              // 'callbackUrl': fileApi.callBack({ 'status': 1 }),
              'callbackUrl': 'http://127.0.0.1:6543/villageUpload/file/callBack',
              // 'mode': 'view',// 预览时放开禁止编辑
              'lang': 'zh'
            },
            'height': '100%',
            'width': '100%'
          }
          // config.editorConfig.callbackUrl = config.editorConfig.callbackUrl.replace('/IndexServlet', ONLYOFFICE_BASE_URL + '/IndexServlet')
          this.initDocEditor(baseUrl.baseUrl.docserviceApiUrl, config)
        }
      })
    },
    /**
     * 初始化文档编辑器
     * @param {string} docserviceApiUrl 文档服务API url
     * @param {object} config 文件相关配置信息
     */
    initDocEditor(docserviceApiUrl, config) {
      this.loadOnlyOfficeAPI(docserviceApiUrl).then(() => {
        /* global DocsAPI */
        this.docEditor = new DocsAPI.DocEditor('placeholder', {
          ...config
        })
      })
    },

    /**
     * 加载 onlyoffice api
     * @return {Promise} 返回 api 加载状态
     */
    loadOnlyOfficeAPI(src) {
      return new Promise((resolve, reject) => {
        const script = document.createElement('script')
        script.type = 'text/javascript'
        script.src = src + '/web-apps/apps/api/documents/api.js'
        document.body.appendChild(script)
        script.onload = () => {
          resolve()
        }
        script.onerror = () => {
          reject()
        }
      })
    }
  }
}

</script>

<style lang="stylus" scoped>
.onlyoffice {
  height: 100%;
  width: 100%;
}
#app .mainContent {
  height: 100%;
  width: 100%;
}
</style>
