<template>
    <div class="apply-instructions">
        <div class="box">
            <div class="header" :style="headerStyle">
                <span class="header-title">申报须知</span>
                <img src="~assets/images/home/loginreminder/01.png" @click="handleCloseModal" alt="">
            </div>
            <div class="content">
                <div class="title">
                    {{projectInfo.chi032}}
                </div>
                <div class="inner-content">
                    <div class="condition">
                        <div class="inner-title">申报条件</div>
                        <div class="items">
                            <div class="item" :data-index="index+1" v-for="(item,index) in showInfo" :key="item.id">
                                {{item}}
                            </div>
                        </div>
                    </div>
                    <div class="careful">
                        <div class="inner-title">注意事项</div>
                        <div class="careful-content">
                            获得扶持资格后，年度内出现环保、安全、综治等方便重大事故，或存在偷税漏税行为的，取消企业当年奖补资格。
                        </div>
                    </div>
                </div>
            </div>
            <div class="know-btn">
                <button class="permary" @click="$router.go(-1)">返回上级</button>
                <div style="width: 50px;"></div>
                <button class="info" @click="handleCloseModal">我已知晓，继续申报</button>
            </div>
        </div>
    </div>
</template>
<script>
    import imageBgHeader from '@/assets/images/home/loginreminder/02.png'
    import {mapGetters} from "vuex";
    import {CORPORATE_INFO} from "@/store/constants";
    import {watch} from "vue";

    export default {
        name: 'LoginReminder',
        props: {
            loadMessage: {
                type: [Boolean],
                required: true,
                default: () => false
            },
            projectInfo: {
                type: [Object],
                default: {},
            }
        },
        data() {
            return {
                imageBgHeader,
                showInfo:[]
            }
        },
        components: {},
        mounted() {

        },
        methods: {
            handleCloseModal() {
                this.$emit('handleCloseModal')
            },
        },
        computed: {
            headerStyle() {
                let str = ''
                const imageBgHeader = this.imageBgHeader
                str += `background-image: url(${imageBgHeader})`
                return str
            },
            doNoticePoint() {

            }
        },
        watch:{
            projectInfo: {
                handler(newVal ,oldVal) {
                    for (let item of this.projectInfo.chi035.trim().split("&")){
                        if(item.indexOf(")")<4){
                            item = item.substring(4,item.length);
                        }
                        if(item=='')return
                        this.showInfo.push(item)
                    }
                }
            }
        }
    }
</script>
<style lang="scss" scoped>
    .apply-instructions {
        position: fixed;
        width: 100%;
        height: 100%;
        background: rgba(51, 51, 51, .3);
        top: 0;
        left: 0;
        z-index: 9999;

        .box {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 1097px;
            background: #fff;
            box-shadow: 1px 1px 8px;

            .header {
                width: 100%;
                height: 42px;
                // background: blue;
                padding: 0 20px;
                text-align: left;
                line-height: 42px;
                background-repeat: no-repeat;
                background-size: cover;

                .header-title{
                    font-size: 18px;
                    font-weight: 700;
                    color: #fff;
                }

                img {
                    float: right;
                    margin-top: 11px;
                    width: 20px;
                    height: 20px;
                    transition: all .5;

                    &:hover {
                        transform: scale(1.1);
                    }
                }
            }
        }

        .content {
            min-height: 400px;
            max-height: 500px;
            padding: 10px;
            overflow-y: scroll;

            .title{
                font-size: 18px;
                margin: 10px 0;
                text-align: center;
            }
            .inner-content{
                margin: 0 20px;
                padding: 20px;
                background: #eff6ff;

                .inner-title{
                    font-size: 14px;
                    color: #333;
                    margin: 5px;
                    font-weight: 700;
                }

                .condition{
                    .items{
                        .item{
                            position: relative;
                            background: #fff;
                            width: 100%;
                            padding: 15px 0;
                            padding-left: 60px;
                            color: #000;
                            font-size: 13px;
                            margin-bottom: 2px;
                            &::before{
                                content: attr(data-index);
                                position: absolute;
                                width: 18px;
                                height: 18px;
                                top: 13px;
                                left: 30px;
                                color: #fff;
                                font-size: 13px;
                                line-height: 18px;
                                text-align: center;
                                background: #4c8cfb;
                                border-radius: 50%;
                            }
                        }
                    }
                }
                .careful{
                    margin-top: 20px;
                    .careful-content{
                        width: 100%;
                        border: 1px solid #facd91;
                        background: #fef8ef;
                        font-size: 14px;
                        color: #B8741A;
                        padding: 20px;
                    }
                }
            }
        }

        .content::-webkit-scrollbar {
            display: none;
        }

        .know-btn {
            height: 86px;
            background-color: #fff;
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: center;

            button {
                background-color: #5098ff;
                border-radius: 5px;
                width: 140px;
                height: 36px;
                font-size: 13px;
                color: #fff;
            }

            .permary{
                background: #fff;
                border: 1px solid #5098ff;
                color: #5098ff;
            }
        }
    }
</style>
