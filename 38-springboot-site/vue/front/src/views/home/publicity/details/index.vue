<template>
  <div class="publicity-details">
    <div class="container">
      <YhBreadcrumb :renderData="breadcrumbData"></YhBreadcrumb>
      <DetailsTable></DetailsTable>
    </div>
  </div>
</template>

<script>
import YhBreadcrumb from "@/components/elementPlusExtend/YhBreadcrumb/YhBreadcrumb.vue";
import DetailsTable from "./DetailsTable.vue";
export default {
  name: "PublicityDetails",
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
          path: "/home/publicity",
          name: "阳光公示",
        },
        {
          id: "03",
          path: "",
          name: "查看详情",
        },
      ],
    };
  },
  components: {
    YhBreadcrumb,
    DetailsTable,
  },
  computed: {},
};
</script>

<style lang="scss" scoped>
.publicity-details {
  width: 100%;
  background-color: #f7faff;
  padding-bottom: 30px;
  min-height: 500px;
}
</style>
