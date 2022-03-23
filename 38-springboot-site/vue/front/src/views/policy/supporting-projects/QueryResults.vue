<template>
  <div class="policy-query-results">
    <YhTitle title="查询结果" type="03"></YhTitle>
    <div class="tool-wrapper">
      <div class="left">
        <el-checkbox v-model="queryParam.applyingChecked" size="large">
          <div class="checked-label">
            <span>仅查询正在申报中的项目</span>
            <!--<span>（<b style="color: green">{{applyingCount}}</b>项）</span>-->
          </div>
        </el-checkbox>
      </div>
      <div class="right">
        <div class="search-wrapper">
          <el-input
            style="width: 260px"
            v-model="fuzzChi032Input"
            placeholder="输入事项名称查询"
          >
            <!--TODO 推荐模糊查询-->
            <!--:fetch-suggestions="querySearchAsync"-->
            <!--@select="handleSelect"-->
            <template #suffix>
              <img
                src="~assets/images/policy/iconimg-search.png"
                alt=""
                class="img"
                @click="fuzzSearch"
              />
            </template>
          </el-input>
        </div>
      </div>
    </div>
    <div class="query-results">
      <template v-for="item in renderData" :key="item.id">
        <YhPolicyCard
          :renderData="item"
        ></YhPolicyCard>
      </template>
    </div>
    <div>
      <div class="pagination-wrapper">
        <el-pagination
            background
            layout="prev, pager, next,sizes,jumper,slot"
            :total="total"
            :page-sizes="['1','10', '20', '30']"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        >
          <span>共{{pageAll}}页</span>

        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
  import YhPolicyCard from '@/components/project/YhPolicyCard/YhPolicyCard.vue'

  export default {
  name: 'PolicyQueryResults',
  props: {
    renderData: {
      type: [Array],
      required: true,
    },
    total:{
      type: Number,
      required: true,
    }
  },

  data() {
    return {
      // 正在申报项目
      queryParam: {
        applyingChecked: false,
        chi032: '',
      },
      pageParam: {
        pageNo: 0,
        pageSize: 10,
      },
      applyingCount: 0,
      // 正在输入的名称
      fuzzChi032Input: '',
      pageTotal: 0,
      loadAll: [
      ],
      timer: null,
    }
  },
  components: { YhTitle, YhPolicyCard },
  mounted() {
    const { searchVal } =  this.$route.query
    if(searchVal) {
      this.fuzzChi032Input = searchVal
    }
    this.$nextTick(() => {
      this.fuzzSearch()
    })
  },
  computed: {
    pageAll() {
      return Math.ceil(this.total /  (this.pageParam.pageSize || 1))
    },
    otherParam() {
      let queryParamTemp = this.queryParam
      if(!queryParamTemp.applyingChecked) {
        delete queryParamTemp.applyingChecked
      }
      return {
        ...this.queryParam,
        ...this.pageParam
      }
    }
  },
  methods: {
    fuzzSearch() {
      this.queryParam.chi032 = this.fuzzChi032Input
    },
    handleCurrentChange(pageNo){
      this.pageParam.pageNo = pageNo
    },
    handleSizeChange(pageSize){
      this.pageParam.pageSize = pageSize
    },
  },
  watch: {
    // 参数变化时触发查询
    otherParam: {
      handler(val) {
        this.$emit("queryPolicyOtherParamChange",val)
      },
      deep: true,
      immediate: true
    },
  }
}
</script>

<style lang="scss" scoped>
.policy-query-results {
  width: 100%;
  .query-results{
    min-height: 600px;
  }
  .tool-wrapper {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 16px 0;
  }
  .search-wrapper {
    position: relative;
    img {
      cursor: pointer;
      position: absolute;
      top: 50%;
      right: 0;
      width: 15px;
      transform: translateY(-50%);
    }
  }
}
</style>
