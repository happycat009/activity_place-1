<template>
    <div class="login-reminder">
        <div class="box">
            <div class="header" :style="headerStyle">
                <img src="~assets/images/home/loginreminder/01.png" @click="handleCloseModal" alt="">
            </div>
            <div class="content">
                <div class="items" v-for="(item,index) in messageInfoList" :key="item">
                    <div class="items-title">
                        新提示
                    </div>
                    <div class="item">
                        <span class="index">{{index + 1}}</span>
                        <span class="text">
                            {{item.title}}
                        </span>
                        <el-button class="toDetailBtn" @click="toMessageDetail()">
                            查看详情
                        </el-button>
                        <el-button class="readBtn" @click="messageRead(item)">
                            标记已读
                        </el-button>
                    </div>
                </div>
            </div>
            <div class="know-btn">
                <button @click="handleCloseModal">我知道了</button>
            </div>
        </div>
    </div>
</template>
<script>
    import imageBgHeader from '@/assets/images/home/loginreminder/02.png'
    import {requestB302, requestB413} from "@/service";
    import {mapGetters} from "vuex";
    import {CORPORATE_INFO} from "@/store/constants";

    export default {
        name: 'LoginReminder',
        props: {
            loadMessage: {
                type: [Boolean],
                required: true,
                default: () => false
            }
        },
        data() {
            return {
                imageBgHeader,
                queryParam: {
                    pageNo: '1',
                    pageSize: '10',
                    issee: '0'
                },
                messageInfoList: []
            }
        },
        components: {},
        mounted() {
        },
        methods: {
            handleCloseModal() {
                this.$emit('handleCloseModal')
            },
            loadMessageLastUnsee() {
              let _this = this
              console.log("aab001",this.corporateInfo.aab001)
              if (this.corporateInfo.aab001){
                let {pageNo, pageSize, issee} = _this.queryParam
                requestB413(pageNo, pageSize, this.corporateInfo.aab001, issee).then((res) => {
                  const {code, data} = res
                  if("200" == code) {
                    const {messageList} = data
                    if(messageList && messageList.length) {
                      _this.$emit("showMe")
                      _this.messageInfoList = messageList
                    }
                    // this.$set(this,"messageInfoList",messageList)

                  }
                })
              }


            },
            toMessageDetail() {
                this.$router.push("/enterprises/message")
            },
            messageRead(data) {
                requestB302(this.corporateInfo.aab001, data.mgid).then((res) => {
                    const {code} = res
                    if('200' == code) {
                        this.loadMessageLastUnsee()
                    }
                })
            }
        },
        computed: {
            ...mapGetters([CORPORATE_INFO]),
            headerStyle() {
                let str = ''
                const imageBgHeader = this.imageBgHeader
                str += `background-image: url(${imageBgHeader})`
                return str
            },
        },
        watch: {
            loadMessage: {
                handler(val) {
                    if(val) {
                        this.loadMessageLastUnsee()
                    }
                },
                immediate: true
            }
        }
    }
</script>
<style lang="scss" scoped>
    .login-reminder {
        position: fixed;
        width: 100%;
        height: 100%;
        background: rgba(51, 51, 51, .3);

        .box {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 792px;
            background: #fff;
            box-shadow: 1px 1px 8px;

            .header {
                width: 100%;
                height: 42px;
                // background: blue;
                padding: 0 10px;
                text-align: right;
                background-repeat: no-repeat;
                background-size: cover;

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
            height: 350px;
            max-height: 350px;
            padding: 10px;
            overflow-y: scroll;

            .items {
                .items-title {
                    font-size: 16px;
                    color: #1378FF;
                    font-weight: 700;
                    margin-bottom: 3px;
                }

                .item {
                    padding: 10px 80px 10px 40px;
                    height: 74px;
                    background: inherit;
                    background-color: #eff6ff;
                    margin-bottom: 10px;
                    position: relative;
                    display: flex;
                    align-items: center;

                    .index {
                        position: absolute;
                        width: 20px;
                        height: 20px;
                        border: 1px solid #70B603;
                        border-radius: 50%;
                        top: 50%;
                        left: 10px;
                        transform: translate(0, -50%);
                        color: #70B603;
                        font-size: 16px;
                        text-align: center;
                        line-height: 20px;
                    }

                    .text {
                        font-size: 14px;

                        .show-detail {
                            a {
                                color: #1378FF;
                            }
                        }
                    }

                    .toDetailBtn {
                        position: absolute;
                        width: 60px;
                        height: 20px;
                        background-image: linear-gradient(0deg, #1378FF 0%, #99b4d9 100%);
                        font-size: 12px;
                        color: #FFFFFF;
                        bottom: 50%;
                        right: 80px;
                    }

                    .readBtn {
                        position: absolute;
                        width: 60px;
                        height: 20px;
                        background-image: linear-gradient(0deg, #dc132c 0%, #ec8390 100%);
                        font-size: 12px;
                        color: #FFFFFF;
                        bottom: 50%;
                        right: 10px;
                    }
                }
            }
        }

        .content::-webkit-scrollbar {
            display: none;
        }

        .know-btn {
            height: 72px;
            background-color: #fff;
            text-align: center;

            button {
                background-color: #5098ff;
                border-radius: 5px;
                width: 140px;
                height: 36px;
                font-size: 13px;
                color: #fff;
            }
        }
    }
</style>
