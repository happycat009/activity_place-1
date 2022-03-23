<template>
  <div class="policy-details-match-report">
    <MatchInfo :matchReportItemVoList="matchReportItemVoList" :matchStatisticsInfo="matchStatisticsInfo"></MatchInfo>
    <MatchDetails :matchReportItemVoList="matchReportItemVoList"></MatchDetails>
  </div>
</template>

<script>
  import {
    CORPORATE_INFO
  } from '@/store/constants'
import MatchDetails from './MatchDetails.vue'
import MatchInfo from './MatchInfo.vue'
  import {requestB401, requestD401} from "@/service";
  import {mapGetters} from "vuex";

export default {
  name: 'PolicyDetailsMatchReport',
  data() {
    return {
      matchReportItemVoList: [],
      matchStatisticsInfo: {}
    }
  },
  components: { MatchInfo, MatchDetails },
  mounted() {

    let _this = this
    const {aab001} = this.corporateInfo
    requestD401(aab001,_this.$route.params.id).then((res) => {
      _this.matchReportItemVoList = res.data.matchReportItemVoList
    });

    requestB401(aab001).then((res) => {
      const { fillrate } = res.data
      this.matchStatisticsInfo.fillrate = fillrate
    })
  },
  computed: {
    ...mapGetters([CORPORATE_INFO]),
  },
}
</script>

<style lang="scss" scoped>
.policy-details-match-report {
  width: 100%;
  padding: 0 40px 30px;
}
</style>
