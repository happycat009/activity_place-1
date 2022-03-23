<template>
  <div class="news">
    <div class="container">
      <!-- <YhBreadcrumb :renderData="breadcrumbData"></YhBreadcrumb> -->
      <div class="news-wrapper" v-if="newsData.chm651">
        <div class="title">
          <span>{{ newsData.chm651 }}</span>
        </div>
        <div class="news-info">
          <div class="unit">{{ newsData.chm653 }}</div>
          <div class="time">
            {{ newsData.chm656Desc }}
          </div>
        </div>
        <div class="content">
          <div class="article-content" v-if="newsData.chm654">
            <div v-html="newsData.chm654"></div>
          </div>
        </div>
      </div>
      <YhNoData description="未查询到相关新闻" v-else></YhNoData>
    </div>
  </div>
</template>

<script >
  import YhBreadcrumb from '@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue'
  import YhNoData from '@/components/common/YhNoData/YhNoData.vue'
  import {requestE406} from '@/service'
  import {useMoment} from '@/use'
  export default {
    name: "newsMainIndex",
    components: {YhBreadcrumb,YhNoData},
    data() {
      return {
        newsData: {},
      }
    },
    mounted() {
      this.getData(this.$route.params.id)
    },
    methods: {
      async getData(newsId) {
        const res = await requestE406(newsId)
        const {code, data} = res
        if(code != '-1' && data) {
          data.chm656Desc = useMoment(data.chm656, 'YYYY年MM月DD日 HH:mm:ss')
          this.newsData = {...data}
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
.news {
  width: 100%;
  background-color: #f7faff;
  padding: 30px 0;

  .news-wrapper {
    background-color: #fff;
    padding: $spacing * 2;
    line-height: 1.4;
    .title {
      font-weight: 700;
      color: $color-title;
      font-size: 40px;
      text-align: center;
      span {
        display: inline-block;
        text-align: left;
      }
    }
    .news-info {
      display: flex;
      align-items: center;
      padding-top: 20px;
      color: $color-placeholder;
      font-size: 14px;
      .unit {
        padding-right: 20px;
      }
    }
    .img-wrapper {
      width: 100%;
      padding-top: 20px;
    }

    .article-content {
      line-height: 1.4;
      color: $color;
      font-size: 16px;
      p {
        text-indent: 2em;
        margin-bottom: 10px;
      }
    }
  }
}
</style>
