<template>
  <div class="policy-filters">
    <YhTitle title="筛选条件" type="03"></YhTitle>
    <div class="items">
      <template v-for="item in dimensionList" :key="item.id">
        <div class="item">
          <FiltersItem :contentData="item" @itemSelected="itemValChange"></FiltersItem>
        </div>
      </template>
    </div>
  </div>
</template>


<script>
  import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
  import FiltersItem from './FiltersItem.vue'
  export default {
    name: 'PolicyFilters',
    props: {
      dimensionList: {
        type: Array,
        default: () => []
      }
    },
    components: {YhTitle,FiltersItem},
    data() {
      return {
        dimensionParam:{}
      }
    },
    mounted() {
    },
    methods: {
      itemValChange(newValObj) {
        for (let i = 0;i < newValObj["keyList"].length;i++) {
          delete this.dimensionParam[newValObj["keyList"][i]]
        }
        this.dimensionParam = {...this.dimensionParam,...newValObj["renderParam"]}
      }
    },
    watch: {
      dimensionParam: {
        handler: function(newVal) {
          let _this = this
          this.$emit("queryPolicyDimenParamChange",newVal)
        },
        deep: true,
        immediate: true
      }
    }
  }
</script>

<style lang="scss" scoped>
.policy-filters {
  width: 100%;
  padding-bottom: 30px;
  .items {
    padding-top: 30px;
    .item {
      margin-bottom: 10px;
      &:last-child {
        margin-bottom: 0;
      }
    }
  }
}
</style>
