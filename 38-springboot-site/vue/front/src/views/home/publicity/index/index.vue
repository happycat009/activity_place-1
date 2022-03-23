<template>
  <div class="publicity-index">
    <div class="container">
      <YhBreadcrumb :renderData="breadcrumbData"></YhBreadcrumb>
    </div>

    <PublicityQuery
      :form="form"
      @handleSubmit="handleSubmit"
      @handleReset="handleReset"
    ></PublicityQuery>
    <PublicityQueryResult :form="form" ref="queryResult"></PublicityQueryResult>
  </div>
</template>

<script>
import YhBreadcrumb from "@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue";
import PublicityQuery from "./Query.vue";
import PublicityQueryResult from "./QueryResult.vue";
export default {
  name: "PublicityIndex",
  data() {
    return {
      breadcrumbData: [
        {
          id: "01",
          path: "/home",
          name: "首页",
        },
        {
          id: "02",
          path: "",
          name: "申报结果公示",
        },
      ],
      form: {
        chb015: "",
        chb017: "",
        chi031: "",
        aab012: "",
        chi037: "",
      },
    };
  },
  components: {
    YhBreadcrumb,
    PublicityQuery,
    PublicityQueryResult,
  },
  mounted() {},
  computed: {},
  methods: {
    handleSubmit() {
      this.$refs.queryResult.reloadForm();
    },
    handleReset() {
      this.form = {
        chb015: "",
        chb017: "",
        chi031: "",
        aab012: "",
        chi037: "",
      };
      this.$nextTick(() => {
        this.$refs.queryResult.reloadForm();
      })
    },
  },
  watch: {
    form: {
      handler(val) {
        console.log("val",val)
      },
      immediate: true,
      deep: true
    }
  }
};
</script>

<style lang="scss" scoped>
.publicity-index {
  width: 100%;
  background-color: #f7faff;
  padding-bottom: 30px;
  min-height: 600px;
}
</style>
