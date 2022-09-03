<template>
  <div>
    <div style="margin-top: 20px" />
  </div>
</template>

<script>
export default {
  name: 'RootConfig',
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

    }
  },
  computed: {
  },
  methods: {

  }
}
</script>

<style lang="stylus" scoped>
.desc{
  font-size: small;
  color: #8c8c8c;
}
.org-item{
  margin: 5px;
}
</style>
