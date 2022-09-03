<template>
  <node
    :title="config.name"
    :is-root="true"
    :content="content"
    placeholder="流程开始"
    header-bgc="#576a95"
    header-icon=""
    @selected="$emit('selected')"
    @insertNode="type => $emit('insertNode', type)"
  />
</template>

<script>
import Node from './Node'

export default {
  name: 'RootNode',
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
      content: '流程开始'
    }
  },
  methods: {}
}
</script>

<style scoped>

</style>
