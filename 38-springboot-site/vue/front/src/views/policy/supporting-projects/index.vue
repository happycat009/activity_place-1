<template>
    <div class="supporting-projects">
        <Filters :dimensionList="dimensionList" @queryPolicyDimenParamChange="dimenParamChange"></Filters>
        <QueryResults @queryPolicyOtherParamChange="otherParamChange" :total="total" :renderData="queryResultList"></QueryResults>
    </div>
</template>
<script>
    import {requestGetData} from '@/service'
    import Filters from './Filters.vue'
    import QueryResults from './QueryResults.vue'

    var timer;
    export default {
        name: 'SupportingProjects',
        components: {
            Filters,
            QueryResults
        },
        data() {
            return {
                dimensionList: [],
                dimenParam: {},
                otherParam: {
                    pageNo: '1',
                    pageSize: '10',
                    fuzzChi032: ''
                },
                queryResultList: [],
                total: 0,
                // 全部信息默认val为 '0' 避免判空，选中时置为空
                mockData: [
                    {
                        id: "1",
                        name: "主管部门",
                        dimen: 'chi037',
                        list: [
                            {key: "全部", val: "0"}, {key: "内江市商务局", val: "78"}, {key: "内江市经济和信息化局", val: "47"}
                        ],
                        enable: true
                    },
                    {
                        id: "2",
                        name: "企业规模",
                        dimen: 'aka131',
                        list: [{key: "全部",val: "0"}, {key: "规模以上工业企业", val: "5"}, {key: "限额以上商贸企业", val: "6"}, {key: "规模以上服务业企业", val: "7"}, {key: "限额以上商贸企业", val: "9"}, {key: "其他", val: "9999"}],
                        enable: true
                    },
                    {
                        id: "3",
                        name: "扶持行业",
                        dimen: 'che241',
                        list: [{key: "全部", val: "0"}, {key: "工业类", val: "1001"}, {key: "外经贸类", val: "1002"}, {key: "内贸流通类", val: "1003"}, {key: "服务外包类", val: "1004"}, {key: "服务业类", val: "1005"}, {key: "其他", val: "9999"}],
                        enable: true
                    },
                    {
                        id: "4",
                        name: "政策级别",
                        dimen: 'chi034',
                        list: [{key: "全部", val: "0"},{key: "省级及以上", val: "2"}, {key: "市级", val: "3"}],
                        enable: true
                    },
                    {
                        id: "5",
                        name: "项目状态",
                        dimen: 'bhi036',
                        list: [{key: "全部", val: "0"},{key: "申报中", val: "1"}, {key: "已结束", val: "2"}],
                        enable: true
                    }
                ],
                isRequery: false
            }
        },
        methods: {
            // 获取筛选维度
            loadDimensionList() {
                this.dimensionList = this.mockData
            },
            dimenParamChange(param) {
                let _this = this
                _this.dimenParam = param
            },
            otherParamChange(param) {
                let _this = this
                _this.otherParam = param
            },
            //初始化获取数据
            queryPolicyList() {
                let _this = this

                requestGetData("C401", _this.queryPolicyParam).then((res) => {
                    const {code, data} = res
                    if(code == '200' && data) {
                        const {c401List, c401ListPageInfo} = data
                        this.total = Number(c401ListPageInfo.total);
                        if(c401List && c401List.length) {
                            let renderList = []
                            for(let i = 0; i < c401List.length; i++) {
                                let loopItem = c401List[i]
                                renderList.push({
                                    ...loopItem
                                })
                            }
                            this.queryResultList = renderList
                        } else {
                            _this.queryResultList = []
                        }
                    } else {
                        _this.$message.error({
                            showClose: false,
                            message: '获取政策法规失败！！！',
                            center: true,
                            type: "error"
                        })
                    }
                    this.isRequery = false
                })
            },
        },
        mounted() {
            this.loadDimensionList()
            this.queryPolicyList()
        },
        computed: {
            queryPolicyParam() {
                return {
                    ...this.otherParam,
                    ...this.dimenParam
                }
            }
        },
        watch: {
            queryPolicyParam: {
                handler(newVal) {
                    if(timer) {
                        clearTimeout(timer)
                    }
                    timer = setTimeout(()=>{
                        this.queryPolicyList()
                    },500)
                },
                deep: true,
                immediate: true
            }
        }
    }
</script>
