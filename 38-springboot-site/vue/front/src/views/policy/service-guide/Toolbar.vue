<template>
  <div class="service-guide-toolbar" >
    <div class="items" :class="{setTop: isFixed}" id="items" >
      <template v-for="(item,index) in tabInfo" :key="index">
        <div class="item" @click="handlerTabChange(index)" :class="{checked: isChecked == index}">{{item.name}}</div>
      </template>
    </div>
  </div>
</template>

<script>
// import YhBreadcrumb from '@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue'

export default {
  name: 'ServiceGuideToolbar',
  data() {
    return {
      isChecked: 0,
      tabInfo: [
        {
          name: '基本信息',
        },
        {
          name: '申请条件',
        },
        {
          name: '申报材料',
        },
        {
          name: '办理流程',
        },
        {
          name: '附件',
        }
      ],
      // 用于判断是否吸顶
      isFixed: false,
      offsetTop: 0,
      offsetData: [],
    }
  },
  components: {},
  mounted() {
    window.document.querySelector('#app').addEventListener('scroll', this.initHeight);
    this.$nextTick(() => {
    //获取对象相对于版面或由 offsetTop 属性指定的父坐标的计算顶端位置
      this.offsetTop = document.querySelector('#items').offsetTop;
      this.$bus.on('getOffsetTop',(res) => {
        this.offsetData = res
      })
    })
  },
  //回调中移除监听
  destroyed() {
      window.document.querySelector('#app').removeEventListener('scroll')
  },
  computed: {},
  methods:{
    handlerTabChange(value){
      this.isChecked = value
      this.$emit("handlerTabChange",value)
    },
    // 处理吸顶
    initHeight() {
      var scrollTop = window.document.querySelector('#app').scrollTop
      this.isFixed = scrollTop > this.offsetTop ? true : false;
      this.offsetData.forEach((item,index) =>{
        if(scrollTop+10 >= item.offsetTop && scrollTop+10 < item.offsetTop + item.offsetHeight) {
          this.isChecked = index
        }
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.service-guide-toolbar {
  width: 150px;
  height: 100%;
  background-color: #fff;
  border-right: 2px solid #d8e9ff;
  .item {
    width: 106px;
    height: 48px;
    line-height: 48px;
    color: #333333;
    font-size: 18px;
    cursor: pointer;
    user-select: none;
    text-align: center;
    margin: 0 auto;
    margin-bottom: 22px;
    transition: all 0.3s;
    &:hover,
    &.checked {
      font-weight: 700;
      color: $primary;
      background-color: rgba($primary, 0.15);
    }
  }
}
.setTop{
  position: fixed;
  top: 0px;
  width: 148px;
}
</style>
