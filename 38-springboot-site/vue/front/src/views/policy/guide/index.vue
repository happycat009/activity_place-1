<template>
    <div class="guide">
        <YhTitle title="惠企政策" type="03"></YhTitle>
        <PolicyItem :hi05="item" v-for="(item,index) in hi05list" :key="index"></PolicyItem>
    </div>

</template>
<script>
    import {requestC413} from '@/service'
    import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
    import PolicyItem from './PolicyItem.vue'

    export default {
        name: 'guide',
        components: {
            YhTitle,
            PolicyItem
        },
        data() {
            return {
                hi05list: [],
            }
        },
        methods: {
            hanleGetData() {
                requestC413().then((res) => {
                    const { code, data} = res
                    if(code == '200') {
                        const {hi05List} = data
                        this.hi05list = [...hi05List]
                    } else {
                        console.error("惠企政策导读查询失败",res)
                    }
                }).catch((err) => {
                    console.error(err)
                })
            },
        },
        mounted() {
            this.hanleGetData()
        }
    }
</script>
