<template>
  <node
    :title="config.name"
    :is-root="true"
    :content="content"
    :is-end="true"
    placeholder="流程结束"
    header-bgc="#576a95"
    header-icon=""
    @selected="$emit('selected')"
  />
</template>

<script>
import Node from './Node'

export default {
  name: 'EndNode',
  components: { Node },
  props: {
    config: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      content: '流程结束'
    }
  },
  computed: {
    aa() {
      console.log(this.config.name)
      return ''
    }
  },
  methods: {}
}
</script>

<style scoped>

</style>
