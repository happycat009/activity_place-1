<template>
  <div class="query">
    <div class="input-wrapper">
      <input type="text" placeholder="输入关键字搜索" v-model="searchVal" />
      <button  @click="fuzzSearch">
        <i class="iconfont iconfont-search"></i>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeQuery',
  data() {
    return {
      searchVal: ''
    }
  },
  components: {},
  mounted() {},
  methods: {
    fuzzSearch() {
      this.$router.push({ path: "/policy/index/supporting-projects",query: {searchVal: this.searchVal} })
    }
  },
  computed: {},
}
</script>

<style lang="scss" scoped>
.query {
  width: 734px;
  width: 100%;
  flex: 1;
  overflow: hidden;
  .input-wrapper {
    width: 100%;
    position: relative;
    line-height: 46px;
    button {
      position: absolute;
      top: 0;
      right: 0;
      width: 92px;
      background-image: linear-gradient(0deg, #006cff 0%, #00c0ff 100%);
      box-shadow: 0px 30px 28px 4px rgba(0, 15, 217, 0.17);
      border-radius: 2px;
      border: solid 1px #7bdbff;
      color: #fff;
      font-size: 24px;
      font-weight: 700;
      transition: all 0.3s;
      font-weight: 700;
      background-color: none;
    }
    input {
      display: block;
      width: 100%;
      height: 48px;
      font-size: 16px;
      padding-left: 20px;
      padding-right: 112px;
      transition: all 0.3s;
      color: #fff;
      background-color: rgba(#00156d, 0.3);
      border: solid 1px #00d2ff;
      outline: 0;
      &::-webkit-input-placeholder {
        color: #fff;
        font-size: 16px;
        transition: all 0.3s;
      }
      &::-moz-placeholder {
        /* Mozilla Firefox 19+ */
        color: #fff;
        font-size: 16px;
        transition: all 0.3s;
      }
      &:-moz-placeholder {
        /* Mozilla Firefox 4 to 18 */
        color: #fff;
        font-size: 16px;
        transition: all 0.3s;
      }
      &:-ms-input-placeholder {
        /* Internet Explorer 10-11 */
        color: #fff;
        font-size: 16px;
        transition: all 0.3s;
      }
      &:focus {
        border-color: rgba(0, 114, 255, 0.22);
        background-color: #fff;
        color: #333;
        &::-webkit-input-placeholder {
          color: #b6b6b6;
          font-weight: 700;
        }
        &::-moz-placeholder {
          /* Mozilla Firefox 19+ */
          color: #b6b6b6;
          font-weight: 700;
        }
        &:-moz-placeholder {
          /* Mozilla Firefox 4 to 18 */
          color: #b6b6b6;
          font-weight: 700;
        }
        &:-ms-input-placeholder {
          /* Internet Explorer 10-11 */
          color: #b6b6b6;
          font-weight: 700;
        }
        ~ button {
          background-color: var(--color-secondary);
          background-image: none;
          border: solid 1px #fdffbe;
          box-shadow: none;
        }
      }
    }
  }
}
</style>
