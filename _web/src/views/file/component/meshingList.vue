<template>
  <div id="meshing-list-out">
    <slot>
      <div v-for="(item,index) in fileDataList" :key="item.id" class="content-box">
        <div class="info">
          <div class="image-box" @dblclick="fileOpen(item)">
            <img v-if="'pic'!==fileIcon(item.fileType)" :src="require('../../../assets/imageIcon/file_'+fileIcon(item.fileType)+'.png')" class="content-img" :class="{'content-img-click' : item.isClick === 1}">
            <img v-else :src="filePreview(item.id)" class="content-img" :class="{'content-img-click' : item.isClick === 1}">
          </div>
          <label class="content-file-name">{{ item.oldName }}</label>
        </div>
        <div class="mask" @click="fileOpen(item)">
          <div>
            <el-dropdown>
              <div class="dropout"><i class="yuan" /><i class="yuan" /><i class="yuan" /></div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="downFile(item.id,item.oldName)">下载</el-dropdown-item>
                <el-dropdown-item @click.native="rename(item)">重命名</el-dropdown-item>
                <el-dropdown-item @click.native="delFile(item.id,index)">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </div>
    </slot>
    <!-- 图片查看 -->
    <duo-image-viewer
      :list="srcList"
      :show.sync="showViewer"
      :current-index="currentIndex"
      :img-name="imgName"
      @open="openCallback"
      @close="closeCallback"
    />
    <!-- 视频查看 -->
    <video-player :video-show.sync="videoShow" :video-name="videoName" :video-url="videoUrl" />
  </div>
</template>

<script>

import fileServer, { rename, delFile } from '@/api/file'
import basePath from '@/config/index'
import util from '@/utils/util.js'
import videoPlayer from '@/components/videoPlayer/index.vue'

export default {
  name: 'MeshingList',
  components: {
    videoPlayer
  },
  props: {
    fileDataList: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      videoShow: false, // 视频播放是否显示
      currentIndex: 0, // 打开图片查看器时，需要定位到的图片的索引
      srcList: [], // 图片查看器数据集
      showViewer: false, // 是否打开图片查看器
      imgName: null, // 文件名称
      videoUrl: null, // 视频播放路径
      videoName: null// 视频名称
    }
  },
  computed: {

  },
  watch: {
    fileDataList: function() {
      this.updSrcUrlList()
    }
  },
  mounted() {

  },
  created() {
    setTimeout(() => {
      this.updSrcUrlList()
    }, 1000)
  },
  destroyed() {

  },
  methods: {
    updSrcUrlList() {
      this.srcList = []
      this.fileDataList.map(m => {
        if (this.fileIcon(m.fileType) === 'pic') {
          const url = basePath.baseUrl.baseApiPrefix + '/file/filePreview?id=' + m.id
          this.srcList.push(url)
        }
      })
    },
    handleOpen(file) {
      var url = basePath.baseUrl.baseApiPrefix + '/file/filePreview?id=' + file.id
      this.currentIndex = this.srcList.indexOf(url)
      this.imgName = file.oldName
      this.showViewer = true
    },
    openCallback() {}, // 打开时的回调
    closeCallback() {}, // 关闭时的回调
    // 文件预览
    filePreview(id) {
      return basePath.baseUrl.baseApiPrefix + '/file/filePreview?id=' + id
    },
    fileOpen(file) {
      if (file.isFile === 1) {
        if (file.fileType === 'pdf' || file.fileType === 'xls' || file.fileType === 'xlsx') {
          return false
        } else if (this.fileIcon(file.fileType) === 'pic') {
          this.handleOpen(file)
          return false
        } else if (this.fileIcon(file.fileType) === 'video') {
          this.videoShow = true
          this.videoUrl = this.filePreview(file.id)
          this.videoName = file.oldName
          return false
        } else {
          return false
        }
      }
      // 移除其他组件的样式
      // this.fileDataList.map(m => {
      //  if (m.isClick !== undefined) {
      //    m.isClick = 0
      //  }
      // })
      // 当前点击的增加样式  使用$set 是为了保证响应式
      // this.$set(file, 'isClick', 1)
      this.$emit('file-open', { file })
    },
    /**
     * 下载文件
     * id 主键
     * name生成文件的名字
     * */
    downFile(id, name) {
      fileServer.download({ 'id': id }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response]))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', name)

        document.body.appendChild(link)

        link.click()
        document.body.removeChild(link)
        // downloadFile(response, name, '')
      })
    },
    /**
     * 重命名
     */
    rename(data) {
      this.$prompt('请输入名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        const newName = data.isFile === 1 ? value + '.' + data.fileType : value
        const renameForm = { 'id': data.id, 'oldName': newName }
        rename(renameForm).then(response => {
          if (response.success) {
            util.successMessage('重命名成功')
            data.oldName = value
          }
        })
      }).catch(() => {
        util.warnMessage('取消输入！')
      })
    },
    // 删除文件
    delFile(id, index) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFile({ id }).then(resp => {
          if (resp.success) {
            util.successMessage('删除成功！')
            this.fileDataList = this.fileDataList.filter(n => {
              return n.id !== id
            })
          } else {
            util.errorMessage('删除失败！')
          }
        })
      }).catch(() => {
        util.warnMessage('已取消删除')
      })
    },
    // 统一处理文件图标
    fileIcon(type) {
      const typeMap = {
        pic: ['gif', 'jpg', 'jpeg', 'png', 'bmp', 'webp'],
        video: ['mp4', 'm3u8', 'rmvb', 'avi', 'swf', '3gp', 'mkv', 'flv'],
        music: ['mp3', 'wav', 'wma', 'ogg', 'aac', 'flac'],
        document: ['doc', 'txt', 'docx', 'pages', 'epub', 'pdf', 'numbers', 'csv', 'xls', 'xlsx', 'keynote', 'ppt', 'pptx']
      }
      Object.keys(typeMap).forEach((_type) => {
        const extensions = typeMap[_type]
        if (extensions.indexOf(type) > -1) {
          type = _type
        }
      })
      return type
    }
  }
}

</script>

<style lang="stylus" scoped>
#meshing-list-out{
  text-align: center
    .content-box{
      position: relative;
        display inline-block;
        float left;
        width: 7rem;
        height 8rem;
        .image-box{
            .content-img{
                width: 5rem;
                height: 5rem;
            }
            .content-img-click{
                background-color: antiquewhite;
            }
        }
        .content-file-name{
            display: block;
            width: 6em;
            height: 2em;
            overflow: hidden;
            margin: 0 auto
        }
        .info{
          position: absolute;
          width: 7rem;
          height 8rem;
          z-index: 1;
          background-color: #fff;
        }
        .mask{
          position: absolute;
          width: 7rem;
          height: 8rem;
          border-radius: 1rem;
          background-color: rgba(169,169,169,0.3);
          .dropout{
            margin-left: 5rem;
            margin-top: 0.5rem;
            width: 1rem;
            height: 2rem;
            background-color: #fff;
            border-radius: 0.5rem;
          }
          .yuan{
            display: block;
            width: 0.5rem;
            height: 0.5rem;
            background-color: #bbb9af;
            border-radius: 0.25rem;
            margin: 0 auto;
            margin-top: 0.25rem;
          }
        }
    }
    // 像hover一般是给父类加hover,子类添加动画不然会频繁闪烁
    .content-box:hover .info{
      z-index: 0;
    }
    .dropout:hover .yuan{
      background-color: black
    }
}

</style>
