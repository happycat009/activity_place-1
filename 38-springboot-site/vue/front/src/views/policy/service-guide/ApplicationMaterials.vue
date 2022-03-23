<template>
  <div class="service-guide-application-materials">
    <YhTitle title="申请材料" type="03" ></YhTitle>
    <div class="custom-table-wrapper">
      <el-collapse v-model="activeName" accordion class="el-collapse-table">
        <template v-for="(item,index) in materialsInfo" :key="index">
          <el-collapse-item :name="item.che091 + ''">
            <template #title>
              <div class="custom-table-header">
                <div class="name">{{numToChinese(index + 1) + '、' + item.che091}}</div>
              </div>
            </template>
            <div class="custom-table-cells">
              <div class="custom-table-cell">
                <div class="key">材料说明</div>
                <div class="value">
                  {{item.che09b}}
<!--                  <ul>-->
<!--                    <li>材料类型：{{getCollectionDesc("che093",item.che093)}}</li>-->
<!--                    <li>材料份数：{{item.che094}}份</li>-->
<!--                  </ul>-->
                </div>
              </div>
              <div class="custom-table-cell">
                <div class="key">提供要求</div>
                <div class="value">
                  {{getCollectionDesc("che099",item.che099)}}
                </div>
              </div>
              <div class="custom-table-cell">
                <div class="key">是否必须</div>
                <div class="value">{{getCollectionDesc("yesorno",item.che09a)}}</div>
              </div>
            </div>
          </el-collapse-item>
        </template>
      </el-collapse>
    </div>
  </div>
</template>

<script>
import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
export default {
  name: 'ServiceGuideApplicationMaterials',
  data() {
    return { activeName: 1 }
  },
  props: {
    materialsInfo: {
      type: [Array],
      required: true,
      default: () => []
    }
  },
  mounted() {
    this.userCodeTableMap("yesorno")
    this.userCodeTableMap("che093")
    this.userCodeTableMap("che099")
  },
  methods: {
    numToChinese(num) {
      if (!/^\d*(\.\d*)?$/.test(num)) {
        alert("Number is wrong!");
        return "Number is wrong!";
      }
      var AA = new Array("零", "一", "二", "三", "四", "五", "六", "七", "八", "九");
      var BB = new Array("", "十", "百", "千", "万", "亿", "点", "");
      var a = ("" + num).replace(/(^0*)/g, "").split("."),
          k = 0,
          re = "";
      for (var i = a[0].length - 1; i >= 0; i--) {
        switch (k) {
          case 0:
            re = BB[7] + re;
            break;
          case 4:
            if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$").test(a[0]))
              re = BB[4] + re;
            break;
          case 8:
            re = BB[5] + re;
            BB[7] = BB[5];
            k = 0;
            break;
        }
        if (k % 4 == 2 && a[0].charAt(i + 2) != 0 && a[0].charAt(i + 1) == 0) re = AA[0] + re;
        if (a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k % 4] + re;
        k++;
      }
      if (a.length > 1) //加上小数部分(如果有小数部分)
      {
        re += BB[6];
        for (var i = 0; i < a[1].length; i++) re += AA[a[1].charAt(i)];
      }
      return re;
    }
  },
  components: { YhTitle },
  computed: {},
  watch: {
    materialsInfo: {
      handler(val) {
        console.log("val",val)
        if(val && val.length) {
          this.activeName = val[0].che091
        }
      },
      immediate: true,
      deep: true
    },
    activeName: {
      handler(val) {
        console.log("========================",val)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.service-guide-application-materials {
  width: 100%;
  padding-bottom: 48px;
  .custom-table-wrapper {
    padding-top: 28px;
    padding-left: 14px;
  }
}
.custom-table-cells {
  custom-table-cell {
    &:first-child {
      border-top: 0;
    }
  }
}
.custom-table-cells {
  .custom-table-cell {
    &:first-child {
      border-top: 0;
    }
  }
}
.el-collapse-item {
  margin-bottom: 24px;
  &:last-child {
    margin-bottom: 0;
  }
}
</style>
