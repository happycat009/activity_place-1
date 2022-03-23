<template>
  <div class="nav-bar">
    <ul>
      <li @click="handleJumpRoute('/policy', '政策直通车')">
        <div class="item">
          <div class="img-wrapper">
            <img src="~assets/images/home/nav-01.png" alt="" />
          </div>
          <div class="key">政策直通车</div>
          <div class="desc">扶持政策查询、申报</div>
        </div>
      </li>
      <li @click="handleJumpRoute('/community', '甜企云社区')">
        <div class="item">
          <div class="img-wrapper">
            <img src="~assets/images/home/nav-02.png" alt="" />
          </div>
          <div class="key">甜企云社区</div>
          <div class="desc">查看企业活动</div>
        </div>
      </li>
      <li class="nav-03" @click="handleJumpRoute('/city', '走进内江')">
        <div class="item">
          <div class="img-wrapper">
            <img src="~assets/images/home/nav-03.png" alt="" />
          </div>
          <div class="key">走进内江</div>
          <div class="desc">了解内江产业、园区</div>
        </div>
      </li>
      <li class="nav-04">
        <div class="item">
          <div class="button-wrapper">
            <button
              @click="handleJumpRoute('/policy/index/supporting-projects')"
            >
              <div class="dit-cricle">2</div>
              <span>当前申报项目</span>
            </button>
            <button @click="handleJumpRoute('/calculator')">
              <span>政策计算器</span>
            </button>
            <button @click="handleJumpRoute('/enterprises/record')">
              <span>申报进度查询</span>
            </button>
            <button @click="handleJumpRoute('/home/publicity')">
              <span>兑付名单查询</span>
            </button>
          </div>
          <div class="desc">热门服务</div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import imageIcon01 from '@/assets/images/home/icon01.png'
import imageIcon01Active from '@/assets/images/home/icon01-active.png'
import imageIcon02 from '@/assets/images/home/icon02.png'
import imageIcon02Active from '@/assets/images/home/icon02-active.png'
import imageIcon03 from '@/assets/images/home/icon03.png'
import imageIcon03Active from '@/assets/images/home/icon03-active.png'
import { M_UPDATE_CURRENT_MENU_LIST } from '@/store/constants'
import { mapGetters } from 'vuex'
export default {
  name: 'HomeNavBar',
  data() {
    return {
      imageIcon01,
      imageIcon01Active,
      imageIcon02,
      imageIcon02Active,
      imageIcon03,
      imageIcon03Active,
    }
  },
  components: {},
  mounted() {},
  methods: {
    // 点击跳转路由
    handleJumpRoute(path, name) {
      if (path == '/city') {
        window.open('https://www.neijiang.gov.cn/njs/zjnj/zjnj.shtml', '_blank')
      } else {
        if (name) {
          this.$store.commit(M_UPDATE_CURRENT_MENU_LIST, [
            {
              name,
              path,
            },
          ])
        }
        this.$router.push(path)
      }
    },
  },
  computed: {},
  watch: {
    // currentMenuList: {
    //   handler(val) {
    //     if (val) {
    //       const { path } = val[0]
    //       this.initNavbarData(path)
    //     } else {
    //       this.initNavbarData('home')
    //     }
    //   },
    //   immediate: true,
    // },
  },
}
</script>

<style lang="scss" scoped>
.nav-bar {
  width: 880px;
  width: 45.833333%;
  padding-left: 130px;
  padding-right: 40px;
  padding-top: 70px;
  ul {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
  }
  li {
    width: 346px;
    width: 48.732394%;
    height: 328px;
    border: 6px solid transparent;
    transition: all 0.3s;
    border-radius: 8px;
    margin-bottom: 40px;
    cursor: pointer;
    user-select: none;
    .item {
      width: 100%;
      height: 100%;
      border-radius: 8px;
      background-image: linear-gradient(0deg, #006cff 0%, #00c0ff 100%);
      box-shadow: 0px 30px 28px 4px rgba(0, 15, 217, 0.17);
      border: solid 2px #7bdbff;
      opacity: 0.93;
      color: #fff;
      font-size: 30px;
      font-weight: 700;
      text-align: center;
      line-height: 1;
      transition: all 0.3s;
    }
    .desc {
      font-size: 18px;
      padding-top: 10px;
    }
    .img-wrapper {
      height: 210px;
      display: flex;
    }
    img {
      width: 192px;
      width: 58.895706%;
      margin: auto;
    }
    &:nth-of-type(2) {
      img {
        width: 170px;
        width: 52.147239%;
      }
    }
    &:nth-of-type(3) {
      img {
        width: 218px;
        width: 66.871166%;
      }
    }
    &:hover {
      border-color: #d1fcff;
      .item {
        border-radius: 0;
        border-color: #d1fcff;
      }
    }
    &:last-child {
      border-color: transparent;
      .item {
        border-radius: 8px;
        border-color: #7bdbff;
      }
    }
  }

  .nav-03 {
    margin-bottom: 0;
  }
  .nav-04 {
    margin-bottom: 0;

    .button-wrapper {
      display: flex;
      height: 190px;
      flex-wrap: wrap;
      justify-content: space-between;
      padding: 0 10px;
    }

    img {
      display: block;
      width: 40px;
    }
    .img-hover {
      display: none;
    }
    .item {
      padding-top: 46px;
      background: unset;
    }

    button {
      position: relative;
      width: 215px;
      width: 48%;
      background-image: linear-gradient(0deg, #011dd9 0%, #0053ff 100%);
      border-radius: 4px;
      border: solid 1px #00c0ff;
      font-size: 13px;
      font-weight: 700;
      color: #ffffff;
      margin-bottom: 12px;
      text-align: center;
      display: flex;
      align-items: center;
      span {
        flex: 1;
        overflow: hidden;
      }
      .dit-cricle {
        position: absolute;
        top: 10px;
        right: 10px;
        width: 18px;
        height: 18px;
        font-size: 13px;
        color: #fff;
        background-color: #ec808d;
        line-height: 18px;
        border-radius: 50%;
      }
      &:nth-child(2) {
        background-image: linear-gradient(0deg, #7f543b 0%, #fec400 100%);
      }
      &:nth-child(3) {
        background-image: linear-gradient(0deg, #013bd9 0%, #00abff 100%);
      }
      &:nth-child(4) {
        background-image: linear-gradient(0deg, #34582a 0%, #c8f780 100%);
      }
      &:hover {
        background-image: linear-gradient(0deg, #ff9000 0%, #ffba00 100%);
        border-color: #ffe400;
        opacity: 1;
        img {
          display: none;
        }
        .img-hover {
          display: block;
        }
      }
    }
    .desc {
      margin-top: 8px;
      font-size: 18px;
      font-weight: 400;
      line-height: 1;
      color: #ffffff;
    }
  }

  @media screen and (max-width: 1500px) {
    padding: 40px;
    li {
      height: 260px;
      border-width: 2px;
      margin-bottom: 20px;
      .img-wrapper {
        height: 180px;
      }
      .item {
        font-size: 20px;
      }
      .desc {
        font-size: 12px;
      }
    }
    .nav-04 {
      .item {
        padding-top: 20px;
        img {
          width: 28px;
        }
        button {
          line-height: 44px;
          font-size: 14px;
        }
        .desc {
          font-size: 12px;
        }
      }
    }
  }
}
</style>
