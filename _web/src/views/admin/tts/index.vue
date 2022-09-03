<template>
  <div>
    <i class="el-icon-microphone" @click="play()" />
    <i class="el-icon-video-pause" @click="paused()" />
    <i class="el-icon-video-play" @click="goahead()" />
  </div>
</template>

<script>
// tts
import Speech from 'speak-tts'

export default {
  data() {
    return {
      speech: null
    }
  },
  mounted() {
    this.SpeechInit()
  },
  // 离开页面取消语音
  destroyed() {
    this.speech.cancel()
  },
  methods: {
    SpeechInit() {
      this.speech = new Speech()
      this.speech.setLanguage('zh-CN')
      this.speech.init().then(() => {})
    },
    // 播放按钮
    play() {
      alert(1)
      this.speech.speak({
        text: '收款40万元',
        listeners: {
          // 开始播放
          onstart: () => { alert(2) },
          // 判断播放是否完毕
          onend: () => { alert(3) },
          // 恢复播放
          onresume: () => { console.log('Resume utterance') }
        }
      }).then(() => { console.log('读取成功') })
    },
    // 暂停
    paused() {
      this.speech.pause()
    },
    // 从暂停处继续播放
    goahead() {
      this.speech.resume()
    }
  }
}
</script>

