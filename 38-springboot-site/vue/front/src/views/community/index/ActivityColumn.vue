<template>
  <div
    class="activity-column-wrapper"
    v-if="renderData && renderData.length > 0"
  >
    <div class="container">
      <YhTitle title="活动专栏" titleEn="ACTIVITY COLUMN" type="01"></YhTitle>
      <div class="content-wrapper">
        <div class="items">
          <template v-for="(item, index) in renderData" :key="item.chm650">
            <div class="item" @click="handleJumpRoute(item)">
              <div class="card">
                <div class="left">
                  <img v-if="item.chi215"
                      :src="requestUrl+'/upload/downFileByFileId?fileId='+item.chi215"
                      alt=""
                  />
                </div>
                <div class="right">
                  <div class="name">
                    {{ item.chm651 }}
                  </div>
                  <div class="info">
                    <span>信息来源：{{ item.chm653 }}</span>
                    <span style="margin-left: 10px">发布时间：{{ formattedAae036(item.aae036) }}</span>
                  </div>
                  <div class="desc">
                    {{ item.chm652 }}
                  </div>
                </div>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import imagebg02 from '@/assets/images/community/bg-02.png'
  import {requestUrl} from "@/config/city";
  // import {useMoment} from '@/use'
  import moment from 'moment'
  export default {
    name: "CommunityActivityColumn",
    props: {
      renderData: {
        type: Array,
        default: () => [],
      },
    },
    data() {
      return {
        imagebg02: imagebg02,
        requestUrl:requestUrl
      }
    },
    mounted() {
      this.userCodeTableMap("chi037")
    },
    methods: {
      handleJumpRoute(item) {
        const { chm650 } = item
        this.$router.push(`/news/${chm650}`)
      },
      formattedAae036(aae036) {
        return moment(aae036).format("yyyy-MM-DD HH:mm:ss")
      }
    }
  }
</script>

<style lang="scss" scoped>
.activity-column-wrapper {
  width: 100%;
  padding-bottom: $spacing-title * 2;

  .items {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    padding-top: $spacing-title;
  }
  .item {
    margin-bottom: 32px;
    .card {
      width: 576px;
      height: 196px;
      border: solid 2px rgba(216, 232, 255, 0.51);
      background-image: linear-gradient(#f7faff, #f7faff),
        linear-gradient(#ffffff, #ffffff);
      cursor: pointer;
      padding: 24px;
      display: flex;
      font-size: 12px;
      line-height: 1;
      color: #666666;
      overflow: hidden;
      transition: all 0.3s;
      &:hover {
        transform: scale(1.1);
      }
    }
    .left {
      width: 198px;
      margin-right: 16px;
    }
    .right {
      flex: 1;
      overflow: hidden;
    }
    .name {
      font-size: 18px;
      font-weight: 700;
      line-height: 1;
      color: #323232;
      @include textOverflow(1);
    }
    .info {
      padding-top: 6px;
      padding-bottom: 12px;
      color: #999999;
      @include textOverflow(1);
    }
    .desc {
      font-size: 14px;
      line-height: 16px;
      max-height: 96px;
      color: #666666;
      @include textOverflow(6);
    }
  }
}
</style>
