<template>
  <div v-if="videoShow" class="video">
    <h3>{{ videoName }}</h3>
    <div class="video_player">
      <video-player
        ref="videoPlayer"
        class="video-player vjs-custom-skin"
        :playsinline="true"
        :options="playerOptions"
      />
    </div>
    <div class="videoClose" @click="handleClose">
      <div class="videoClose__off" />
    </div>
  </div>

</template>

<script>
export default {
  // name: 'VideoPlayer',
  props: {
    videoUrl: {
      type: String,
      default: null
    },
    videoName: {
      type: String,
      default: null
    },
    videoShow: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      playerOptions: {
        playbackRates: [0.5, 1.0, 1.5, 2.0], // 可选的播放速度
        autoplay: false, // 如果为true,浏览器准备好时开始回放。
        muted: false, // 默认情况下将会消除任何音频。
        loop: false, // 是否视频一结束就重新开始。
        preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: 'zh-CN',
        aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        sources: [{
          type: 'video/mp4', // 类型
          src: '' // url地址
        }],
        poster: '', // 封面地址
        notSupportedMessage: '此视频暂无法播放，请稍后再试', // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controlBar: {
          timeDivider: true, // 当前时间和持续时间的分隔符
          durationDisplay: true, // 显示持续时间
          remainingTimeDisplay: false, // 是否显示剩余时间功能
          fullscreenToggle: true // 是否显示全屏按钮
        }
      }
    }
  },
  watch: {
    videoUrl: function() {
      this.playerOptions.sources[0].src = this.videoUrl
    }
  },
  methods: {
    // On close
    handleClose() {
      this.$emit('update:video-show', false)
    }
  }
}
</script>

<style  lang="stylus"  scoped>
.video{
  position: fixed;
  top: 0;
  left: 0;
  z-index: 10000;
  width: 100%;
  height: 100vh;
  background-color: #fff;
}
.video_player{
  margin-left: 10%;
  width: 80%;
  height: 80%;
}
.videoClose {
  background-color: rgba(0, 0, 0, 0.9);
  border-radius: 0% 0% 0% 100%;
  cursor: pointer;
  height: 50px;
  overflow: hidden;
  position: absolute;
  right: 0;
  top: 0;
  transition: background-color 0.15s;
  width: 50px;
  z-index: 10;
  line-height: 50px;
  text-align: center;
}
.videoClose__off {
  margin: 18px 12px;
  position: relative;
  width: 2em;
  height: 0.3em;
  background-color: #fff;
  transform: rotate(45deg);
  border-radius: 0.15em;
}
.videoClose__off:after {
  content: "";
  position: absolute;
  width: 2em;
  height: 0.3em;
  top: 0;
  left: 0;
  background-color: #fff;
  transform: rotate(90deg);
  border-radius: 0.15em;
}
</style>
