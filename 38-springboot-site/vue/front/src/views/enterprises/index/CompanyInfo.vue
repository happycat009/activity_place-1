<template>
  <div class="company-info">
    <img src="~assets/images/enterprise-center/bg-card.png" alt="" class="bg" />
    <div class="con">
      <!--<div class="img-wrapper" v-if="false">
        <template v-if="flase">
          <img src="" alt="" class="company-img" v-if="false" />
          <img
            src="~assets/images/enterprise-center/no-img.png"
            alt=""
            class="company-no-img"
            v-else
          />
          <div class="text">暂无图片</div>
        </template>
        <template v-else> </template>
      </div>-->
      <div class="upload-wrapper">
        <el-upload
          class="avatar-uploader"
          action="#"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :http-request="uploadAvatar"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          <span class="upload-tip">点击上传</span>
        </el-upload>
      </div>
      <div class="company-basic-info">
        <div class="name d-f ai-c">
          <span>{{ corporateInfo.aab012 }}</span>
          <span :class="{'reg_status':true,'notice': corporateInfo.aab01c>1}">{{regState}}</span>
        </div>
        <div class="cell">
          <div class="key">法定代表人：</div>
          <div class="value">{{ corporateInfo.aac003 }}</div>
        </div>
        <div class="cell">
          <div class="key">联系电话：</div>
          <div class="value">{{ corporateInfo.aae005 }}</div>
        </div>
        <div class="cell">
          <div class="key">企业地址：</div>
          <div class="value">{{ corporateInfo.aab019 }}</div>
        </div>
      </div>
      <div class="company-status">
        <!--        <template v-for="item in 2">-->
        <!--          <div class="item">-->
        <!--            <img src="~assets/images/enterprise-center/bg-tag.png" alt="" />-->
        <!--            <div class="content">-->
        <!--              <div class="value">AAA</div>-->
        <!--              <div class="key">信用等级</div>-->
        <!--            </div>-->
        <!--          </div>-->
        <!--        </template>-->

        <div class="item" v-if="hasEi05">
          <img src="~assets/images/enterprise-center/bg-tag.png" alt="" />
          <div class="content">
            <div class="value">AAA</div>
            <div class="key">A级纳税人（{{ pjnd }}）</div>
          </div>
        </div>
      </div>
      <div class="icons">
        <template v-for="item in customData.iconList" :key="item.id">
          <div class="icon-item">
            <YhIconKeyValue
              :renderData="item"
              isActive
              @click="toIconDetail(item)"
            ></YhIconKeyValue>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import image01 from '@/assets/images/enterprise-center/01.png'
import image02 from '@/assets/images/enterprise-center/02.png'
import image03 from '@/assets/images/enterprise-center/03.png'
import image04 from '@/assets/images/enterprise-center/04.png'
import image05 from '@/assets/images/enterprise-center/05.png'
import image06 from '@/assets/images/enterprise-center/06.png'

import YhIconKeyValue from '@/components/common/YhIconKeyValue/YhIconKeyValue.vue'

import { mapGetters } from 'vuex'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

import { requestB404, requestE401, requestE414, requestB301 } from '@/service'
import { CORPORATE_INFO, M_UPDATE_CORPORATE_INFO, CURRENT_MENU_LIST, USER_INFO } from '@/store/constants'
import {requestUrl} from "@/config/city";
import { requestUploadFileByBase64, requestGetCollectionData } from '@/service'

export default {
  name: 'CompanyInfo',
  components: { YhIconKeyValue, Plus },
  mounted() {
    this.loadStatistics()
    let _this = this
    requestE414('915110007208680786').then((res) => {
      const { code, data } = res
      if ('200' == code) {
        if (data.ei05List && data.ei05List.length > 0) {
          _this.hasEi05 = true
          _this.pjnd = data.ei05List[0].pjnd
        }
      }
    })
    let aab01p = this.corporateInfo.aab01p;
    this.imageUrl = aab01p ? requestUrl + '/upload/downFileByFileId?fileId=' + aab01p : '';
    requestGetCollectionData('AAB01C', this.corporateInfo.aab01c).then((res)=>{
      _this.regState = res
    });
  },
  data() {
    return {
      statisticsInfo: {
        backbusnum: '0',
        cancelbusnum: '0',
        finishbusnum: '0',
        ingbusnum: '0',
        messagenum: '0',
        recommendnum: '0',
        asknum: '0',
      },
      hasEi05: false,
      pjnd: '',
      imageUrl: '',
      regState:''
    }
  },
  methods: {
    loadStatistics() {
      requestE401([
        {
          method: 'enterprisesStatisticsCount',
          param: {
            aab001: this.corporateInfo.aab001,
            orgid: this.corporateInfo.aab001,
          },
        },
      ]).then((res) => {
        const { code, data } = res
        if ('200' == code) {
          const { enterprisesStatisticsCountMap } = data
          this.statisticsInfo = enterprisesStatisticsCountMap
        }
      })
    },
    toIconDetail(data) {
      let routePath = ''
      switch (data.id) {
        case 'my_message':
          routePath = '/enterprises/message'
          break
        // 申报中
        case 'apply_ing':
          routePath = '/enterprises/record?recordType=0'
          this.$emit('updateRecordTypeIndex', 0)
          break
        // 申报失败
        case 'apply_fail':
          routePath = '/enterprises/record?recordType=1'
          this.$emit('updateRecordTypeIndex', 1)
          break
        // 已享受
        case 'apply_success':
          routePath = '/enterprises/record?recordType=2'
          this.$emit('updateRecordTypeIndex', 2)
          break
        // 邀请我申请
        case 'apply_add':
          routePath = '/enterprises/recommend'
          break
        // 为我推荐
        case 'recommand':
          routePath = '/enterprises/recommend'
          break
      }
      this.$router.push(routePath)
    },
    beforeAvatarUpload(file) {
      const types = ['image/jpeg', 'image/gif', 'image/png'];
      const isImage = types.indexOf(file.type)>-1;
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        ElMessage.error('上传头像图片只能是 PNG、JPG、GIF 格式!');
      }
      if (!isLt2M) {
        ElMessage.error('上传头像图片大小不能超过 2MB!');
      }
      return isImage && isLt2M;
    },
    handleAvatarSuccess(res, file) {
      console.log(res, file)
      this.imageUrl = URL.createObjectURL()
    },
    uploadAvatar(data) {
      const fileReader = new FileReader()
      fileReader.readAsDataURL(data.file)
      fileReader.onload = async () => {
        const base64 = fileReader.result
        const res = await requestUploadFileByBase64(base64, data.file.name)
        console.log(data.file)
        const { bucketid, objectid, objectkey } = res.data
        const { aab010 } = this.corporateInfo
        //更新头像信息
        requestB301('ab01', {
          aab010,
          aab01p: objectid,
          filename: data.file.name,
          filesize: data.file.size
        }).then((res) => {
          const { code, data } = res
          if ('200' == code) {
            this.$message.success('企业头像上传成功！')
            this.corporateInfo.aab01p = objectid;
            this.imageUrl = requestUrl + '/upload/downFileByFileId?fileId=' + objectid
            this.$store.commit(M_UPDATE_CORPORATE_INFO, this.corporateInfo)
          }
        })
      }
    },
  },
  watch: {
    userInfo: {
      async handler(val) {
        // if (val) {
        //   const data = await requestB404(val.orgid)
        //   console.log(data, 999)
        // }
      },
      immediate: true,
      deep: true,
    },
  },
  computed: {
    ...mapGetters([USER_INFO, CURRENT_MENU_LIST, CORPORATE_INFO]),
    customData() {
      return {
        iconList: [
          {
            id: 'my_message',
            iconSrc: image01,
            value: this.statisticsInfo.messagenum,
            unit: '',
            key: '新消息',
          },
          {
            id: 'apply_ing',
            iconSrc: image02,
            value: this.statisticsInfo.ingbusnum,
            unit: '',
            key: '申报中',
          },
          {
            id: 'apply_fail',
            iconSrc: image03,
            value: this.statisticsInfo.backbusnum,
            unit: '',
            key: '被驳回',
          },
          {
            id: 'apply_success',
            iconSrc: image04,
            value: this.statisticsInfo.finishbusnum,
            unit: '',
            key: '已享受',
          },
          {
            id: 'apply_add',
            iconSrc: image05,
            value: this.statisticsInfo.asknum,
            unit: '',
            key: '邀我申请',
          },
          {
            id: 'recommand',
            iconSrc: image06,
            value: this.statisticsInfo.recommendnum,
            unit: '',
            key: '为我推荐',
          },
        ],
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.company-info {
  width: 100%;
  position: relative;
  .con {
    position: absolute;
    width: 100%;
    left: 0;
    top: 0;
    padding: 30px;
    padding-left: 254px;
  }
  .img-wrapper {
    position: absolute;
    top: 50%;
    left: 30px;
    transform: translateY(-50%);
    width: 185px;
    height: 220px;
    background-color: #f3f8ff;
    .company-no-img {
      width: 60px;
      margin: 60px auto 0;
    }
    .text {
      text-align: center;
      font-size: 16px;
      line-height: 1;
      color: #006bfd;
      padding-top: 12px;
    }
  }
  .upload-wrapper {
    position: absolute;
    top: 50%;
    left: 30px;
    transform: translateY(-50%);
    width: 185px;
    height: 220px;
    background-color: #f3f8ff;
    .avatar-uploader {
      width: 100%;
      height: 100%;
      .upload-tip{
        display: none;
      }
    }
    .avatar-uploader-icon {
      width: 185px;
      height: 220px;
      font-size: 28px;
      color: #8c939d;
      text-align: center;
    }

    .avatar {
      width: 185px;
      height: 220px;
      display: block;
    }
    .avatar-uploader:hover {
      .upload-tip{
        height: 30px;
        display: block;
        position: relative;
        margin-top: -30px;
        line-height: 30px;
        color: rgba(255, 255, 255, 1);
        background: rgba(0, 0, 0, 0.6);
      }
    }
  }
  .company-basic-info {
    font-size: 14px;
    line-height: 24px;
    color: #666666;
    .name {
      font-size: 20px;
      line-height: 1;
      color: #333;
      padding-bottom: 12px;
      font-weight: 700;
    }
    .cell {
      display: flex;
      align-items: center;
    }
  }
  .company-status {
    position: absolute;
    top: 30px;
    right: 30px;
    display: flex;
    align-items: center;
    .item {
      margin-right: 10px;
      position: relative;
      cursor: pointer;
      .content {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        padding-top: 50px;
      }
      .value {
        position: absolute;
        top: 9px;
        right: 0;
        width: 65px;
        height: 24px;
        background-image: linear-gradient(-90deg, #0072ff 0%, #00deff 100%);
        font-size: 14px;
        color: #fff;
        line-height: 24px;

        text-align: center;
      }
      .key {
        font-size: 14px;
        line-height: 1;
        color: #333333;
        text-align: center;
      }
      &:last-child {
        margin-right: 0;
      }
    }
  }
  .icons {
    display: flex;
    justify-content: space-between;
    padding-top: 53px;
    padding-bottom: 33px;
  }
  .reg_status{
    border: solid 1px #57c657;
    font-size: 13px;
    color: #57c657;
    padding: 3px 5px;
    position: relative;
    font-weight: 400;
    margin-left: 10px;
  }
  .notice{
    border: solid 1px #f6222a;
    color: #fb3128;
  }
}
</style>
