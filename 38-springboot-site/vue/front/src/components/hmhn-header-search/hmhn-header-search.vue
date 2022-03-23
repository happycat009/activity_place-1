<template>
  <div class="hmhn-header-search-wrapper">
    <input type="text" placeholder="请输入关键词" v-model="searchVal"/>
    <button @click="fuzzSearch">
      <img src="~/assets/images/home/header/search.png" alt="" />
    </button>
  </div>
</template>

<script>
export default {
  name: 'hmhnHeaderSearch',
  setup() {
    return {}
  },
  data() {
    return {
      searchVal: ""
    }
  },
  beforeCreate() {},
  created() {},
  beforeMount() {},
  mounted() {},
  activated() {},
  deactivated() {},
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  errorCaptured(err, instance, info) {},
  renderTracked({ key, target, type }) {},
  renderTriggered({ key, target, type }) {},
  directives: {},
  components: {},
  computed: {},
  methods: {
    fuzzSearch() {
      this.$router.push({ path: "/policy/index/supporting-projects",query: {searchVal: this.searchVal} })
    }
  },
}
</script>

<style lang="scss" scoped>
$button-color: #ffae00;
.hmhn-header-search-wrapper {
  border: 4px solid rgba(255, 255, 255, 0.3);
  border-radius: 4px;
  display: flex;
  align-items: center;
  height: 48px + 8px;
  input {
    width: 528px;
    height: 100%;
    border: 0;
    outline: 0;
    padding: 0;
    background-color: #fff;
    font-size: 15px;
    font-weight: 400;
    line-height: 40px;
    color: #333;
    padding: 0 15px;
  }
  button {
    padding: 0;
    margin: 0;
    border: 0;
    outline: 0;
    width: 92px;
    height: 100%;
    background-color: $button-color;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s;
    &:hover {
      background-color: tint($button-color, 40%);
    }
    img {
      width: 26px;
    }
  }
}
</style>
