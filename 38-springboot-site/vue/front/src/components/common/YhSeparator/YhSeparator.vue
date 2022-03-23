<template>
  <div class="yh-separator">
    <template v-for="(item,index) in renderData" :key="index">
      <span v-if="!collection">{{ item }}</span>
      <span v-else>{{ getCollectionDesc(collection,item.aka131) }}</span>
      <span class="separator">{{ separator }}</span>
    </template>
  </div>
</template>

<script>
export default {
  name: 'YhSeparator',
  props: {
    renderData: {
      type: [Array],
      required: true,
    },
    separator: {
      type: [String],
      default: '|',
    },
    collection: {
      type: [String],
      default: ''
    }
  },
  setup() {
    return {}
  },
  data() {
    return {}
  },
  created() {},
  mounted() {
    this.userCodeTableMap(this.collection)
  },
  components: {},
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.yh-separator {
  display: inline-flex;
  align-items: center;
  color: $primary;
  font-size: 16px;
  line-height: 1;
  .separator {
    font-size: 16px;
    line-height: 1;
    padding: 0 8px;
    font-weight: bold;
    color: #000000;
    margin-top: -2px;
    &:last-of-type {
      display: none;
    }
  }
}
</style>
