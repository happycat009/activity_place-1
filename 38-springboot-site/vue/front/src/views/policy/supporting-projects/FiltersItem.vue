<template>
  <div class="policy-filters-item">
    <div class="key">{{contentData.name}}</div>
    <div class="value">
      <div class="radio-group">
        <template v-for="(item,index) in contentData.list" :key="index">
          <div v-if="contentData.enable && item.val" class="radio" @click="handleCheckTab(item,index)" :class="{checked: isChecked == index,'mini': item.key == '全部' || item.key == '其他'}" :title="item.key">{{item.key}}</div>
          <div v-else class="radio-disabled" :class="{checked: isChecked == index}" :title="item.key">{{item.key}}</div>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
  import { requestGetData } from '@/service'
  export default {
    name: 'PolicyFiltersItem',
    props: {
      contentData: {
        type: Object,
        default: () => {}
      }
    },
    data() {
      return {
        isChecked: 0,
        selectedVal: '0'
      }
    },
    components: {},
    mounted() {
    },
    methods: {
      handleCheckTab (item,index){
        let _this = this
        _this.isChecked = index
        _this.selectedVal = (item.val && (item.val != '0')) ? item.val : ''
      }
    },
    computed: {},
    watch: {
      selectedVal: {
        handler(newVal ,oldVal) {
          let _this = this
          let newValObj = {
            keyList: [_this.contentData.dimen,"exclude" + _this.contentData.dimen.slice(0,1).toUpperCase() + _this.contentData.dimen.slice(1).toLowerCase() + "List"]
          }
          let newRenderParam = {}
          if(newVal == '9999') {
            let tempList = []
            const { list: dataList } = _this.contentData
            for (let i = 0;i < dataList.length;i++) {
              if(dataList[i].val != '0' && dataList[i].val != '9999') {
                tempList.push(dataList[i].val)
              }
            }
            newRenderParam["exclude" + _this.contentData.dimen.slice(0,1).toUpperCase() + _this.contentData.dimen.slice(1).toLowerCase() + "List"] = tempList
          } else {
            newRenderParam[_this.contentData.dimen] = newVal
          }
          newValObj["renderParam"] = newRenderParam
          _this.$emit("itemSelected",newValObj)
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
.policy-filters-item {
  width: 100%;
  display: flex;
  .key {
    width: 90px;
    line-height: 40px;
    font-size: 16px;
    color: #333333;
    padding-left: 14px;
  }
  .value {
    flex: 1;
    overflow: hidden;
  }
  .radio-group {
    display: flex;
    flex-wrap: wrap;
  }
  .radio {
    width: 136px;
    height: 40px;
    background-color: #f2faff;
    border-radius: 4px;
    margin-right: 6px;
    font-size: 13px;
    line-height: 40px;
    color: #006bfd;
    text-align: center;
    cursor: pointer;
    user-select: none;
    transition: all 0.3s;
    margin-bottom: 6px;
    &:hover,
    &.checked {
      color: #fff;
      background-color: #006bfd;
    }
  }
  .mini{
    width: 60px;
  }
  .radio-disabled {
    width: 88px;
    height: 40px;
    background-color: #9c9da2;
    border-radius: 4px;
    margin-right: 6px;
    font-size: 13px;
    line-height: 40px;
    color: #fff;
    text-align: center;
    cursor: not-allowed;
    user-select: none;
    transition: all 0.3s;
    margin-bottom: 6px;
    &:hover,
    &.checked {
      color: #fff;
      background-color: #9c9da2;
    }
  }
}
</style>
