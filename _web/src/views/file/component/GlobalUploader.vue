<template>
  <div id="global-uploader">
    <!-- 上传文件组件 -->
    <uploader
      :options="options"
      :file-status-text="options.fileStatusText"
      :auto-start="false"
      class="uploader-example"
      @files-added="onFilesAdded"
      @file-success="onFileSuccess"
      @file-progress="onFileProgress"
      @file-error="onFileError"
    >
      <uploader-unsupport>不支持</uploader-unsupport>
      <uploader-drop class="uploader_drop">
        <uploader-btn ref="uploadBtn" class="upload_btn">上传文件</uploader-btn>
      </uploader-drop>
      <uploader-list>
        <template v-slot:default="props">
          <div class="uploader-list-el">
            <!-- 正在上传的文件列表 -->
            <el-collapse v-model="initOpen">
              <el-collapse-item :title="['上传列表（'+props.fileList.length]+')'" name="1">
                <ul>
                  <li
                    v-for="file in props.fileList"
                    :key="file.id"
                  >
                    <uploader-file ref="fileItem" :class="'file_' + file.id" :file="file" :list="true" />
                  </li>
                </ul>
              </el-collapse-item>
            </el-collapse>
          </div>
        </template>
      </uploader-list>
    </uploader>

  </div>
</template>

<script>
import SparkMD5 from 'spark-md5'
import util from '@/utils/util.js'
import requestFile from '@/api/file'

export default {
  components: {

  },
  props: {
    relativePath: {
      type: String,
      default: '/'
    },
    parentId: {
      type: String,
      default: '-1'
    }
  },
  data() {
    return {
      initOpen: [''],
      // parentId: '',
      // relativePath: '', // 当前路径
      options: {
        target: 'api/villageUpload/file/customUpload', // 上传的地址
        fileParameterName: 'file', // 文件上传时的参数名
        chunkSize: 2 * 1024 * 1024, //  每个分片的大小
        maxChunkRetries: 3, //  并发上传数，默认 3
        testChunks: false, // 是否校验片上传
        // 剩余时间提醒修改
        parseTimeRemaining: function(timeRemaining, parsedTimeRemaining) {
          return parsedTimeRemaining
            .replace(/\syears?/, '年')
            .replace(/\days?/, '天')
            .replace(/\shours?/, '小时')
            .replace(/\sminutes?/, '分钟')
            .replace(/\sseconds?/, '秒')
        },
        // 提示信息
        fileStatusText: {
          success: '上传成功',
          error: '上传出错',
          uploading: '上传中',
          paused: '暂停',
          waiting: '等待'
        },
        // 其他参数
        query: {}
      }
    }
  },
  computed: {

  },
  mounted() {
    /**
       * 监听点击添加文件事件 data预留参数
       */
    util.Bus.$on('openUploader', (data) => {
      if (data.type === 0) {
        this.$refs.uploadBtn.$el.click()
      }
    })
  },
  destroyed() {

  },
  methods: {
    /*global $*/
    /**
     * 添加批量文件的回调函数
     * @description 对单个或批量文件都按此逻辑处理
     * @param {object} files 批量文件信息
     */
    onFilesAdded(files) {
      debugger
      var _this = this
      // 添加文件后展开列表
      _this.initOpen = ['1']
      files.forEach(file => {
        _this.computeMD5(file)
      })
    },
    /**
     * 文件上传成功 回调函数
     * @param {object} rootFile 成功上传的文件所属的根 Uploader.File 对象，它应该包含或者等于成功上传文件
     * @param {object} file 当前成功的 Uploader.File 对象本身
     * @param {string} response 服务端响应内容，永远都是字符串
     */
    onFileSuccess(rootFile, file, response) {
      const data = {
        'filename': file.name,
        'relativePath': file.relativePath,
        'totalChunks': file.chunks.length,
        'identifier': file.uniqueIdentifier
      }
      // 只有一个片的时候不需要合并
      if (file.chunks.length !== 1) {
        // 合并文件的请求
        requestFile.merageFile(data).then(response => {
        }).then(this.$emit('query-file'))
      }
      if (file.chunks.length === 1) {
        this.$emit('query-file')
      }
    },

    /**
       * 文件上传中回调
       * @param {object} rootFile 成功上传的文件所属的根 Uploader.File 对象，它应该包含或者等于成功上传文件
       * @param {object} file 当前成功的 Uploader.File 对象本身
       * @param {chunk} 分片信息
       */
    onFileProgress(rootFile, file, chunk) {
      console.log(`上传中 ${file.name}，chunk：${chunk.startByte / 1024 / 1024} ~ ${chunk.endByte / 1024 / 1024}`)
    },
    /**
     * 文件上传失败 回调函数
     * @param {object} rootFile 成功上传的文件所属的根 Uploader.File 对象，它应该包含或者等于成功上传文件
     * @param {object} file 当前成功的 Uploader.File 对象本身
     * @param {string} response 服务端响应内容，永远都是字符串
     */
    onFileError(rootFile, file, response) {
      this.$message({
        message: response,
        type: 'error'
      })
    },
    // 计算MD5
    computeMD5(file) {
      const blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice
      const chunkSize = 2 * 1024 * 1024 // 2MB
      const chunks = Math.ceil(file.size / chunkSize)
      let currentChunk = 0
      const spark = new SparkMD5.ArrayBuffer()
      const fileReader = new FileReader()

      const time = new Date().getTime()
      // 文件状态设为"计算MD5"
      this.statusSet(file.id, 'md5')

      fileReader.onload = (e) => {
        spark.append(e.target.result) // 读取分片
        currentChunk++

        if (currentChunk < chunks) {
          console.log(`第${currentChunk}分片解析完成, 开始第${currentChunk + 1} / ${chunks}分片解析`)
          loadNext()
          // 实时展示MD5的计算进度
          this.$nextTick(() => {
            $(`.customStatus_${file.id}`).text('校验MD5 ' + ((currentChunk / chunks) * 100).toFixed(0) + '%')
          })
        } else {
          console.log('finished loading')
          const md5 = spark.end() // 得到md5
          console.log(`MD5计算完成：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${file.size} 用时：${new Date().getTime() - time} ms`)
          spark.destroy() // 释放缓存
          file.relativePath = this.relativePath
          file.uniqueIdentifier = md5 + '$$$' + this.parentId // 将文件md5赋值给文件唯一标识
          file.resume() // 开始上传
          this.statusRemove(file.id) // 移出自定义状态
        }
      }
      // 读取失败
      fileReader.onerror = function() {
        this.$notify({
          title: '错误',
          message: `文件${file.name}读取出错，请检查该文件`,
          type: 'error',
          duration: 2000
        })
        file.cancel()
      }

      const loadNext = () => {
        const start = currentChunk * chunkSize
        const end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize

        fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end))
      }

      loadNext()
    },
    /**
    * 新增的自定义的状态: 'md5'、'transcoding'、'failed'
    * @param id
    * @param status
    */
    statusSet(id, status) {
      const statusMap = {
        md5: {
          text: '校验MD5'
        },
        merging: {
          text: '合并中'
        },
        transcoding: {
          text: '转码中'
        },
        failed: {
          text: '上传失败'
        }
      }

      this.$nextTick(() => {
        $(`<p class="customStatus_${id}"></p>`).appendTo(`.file_${id} .uploader-file-status`).css({
          'position': 'absolute',
          'top': '0',
          'left': '0',
          'right': '0',
          'bottom': '0',
          'zIndex': '1'
        }).text(statusMap[status].text)
      })
    },
    statusRemove(id) {
      this.$nextTick(() => {
        $(`.customStatus_${id}`).remove()
      })
    }
  }
}

</script>

<style lang="stylus" scoped>
#global-uploader {
  .upload_btn{
    display:none;
  }
  .uploader_drop{
    border: 0px;
    background-color: rgba(0,0,0,0);
  }
  .uploader-dragover{
    background-color:rgba(204,204,204,0.5);
  }
  .uploader-list{
    position: fixed;
    width: 30%;
    right: 0px;
    bottom: 0px;
  }
  .uploader-list-el{
    .uploader-list-li-custom-status{
      width: 100%
    }
    ul {
        list-style: none;
        margin: 0;
        padding: 0
    }
    >>> .el-collapse-item__content{
      padding-bottom: 0px;
    }
  }
}

</style>
