<template>
    <div class="apply-info-from" :style="PositionStyle" @click="closeModel">
        <!-- <div class="close" @click="closeModel">
            <img src="~assets/images/home/loginreminder/01.png" alt="">
        </div> -->
        <p>填写方式: 系统自动填充</p>
        <p>数据来源: 工商局</p>
        <!-- <p>数据有误？清空手动录入<button>清空</button></p> -->
        <p class="tips">如需更正数据，请联系数据来源方。</p>
    </div>
</template>
<script setup >
const props = defineProps({
    PositionStyle:{
        type: Object,
        default: () => {
          return  {}
        }
    }
})

const emit = defineEmits(['closeModel'])

const closeModel = () => {
    emit('closeModel')
}

</script>
<style lang="scss" scoped>
.apply-info-from{
    position: absolute;
    max-width: 400px;
    min-width: 240px;
    transform: translate(-100%,-100%);
    background: #fff;
    color: #9e9e9e;
    padding: 10px;
    font-size: 14px;
    box-shadow: 1px 1px 4px #95d1f3;
    .close{
        height: 18px;
        img{
            float: right;
            width: 16px;
            height: 16px;
            border-radius: 50%;
            padding: 1px;
            background: #16a8f8;
            border: 1px solid #fff;
        }
    }
    > p{
        padding: 0;
        margin-bottom: 5px;
        button{
            padding: 3px 5px;
            background: #16a8f8;
            color: #fff;
            border-radius: 3px;
            margin-left: 10px;
        }
    }
    .tips{
        color: red;
    }
    &::after{
        position: absolute;
        content: '';
        display: block;
        bottom: -3px;
        right: 10px;
        width: 10px;
        height: 10px;
        transform: rotate(-45deg);
        background: #fff;
    }
}
</style>