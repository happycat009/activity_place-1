<template>
  <div class="service-guide-attachment">
    <div class="title-wrapper">
      <div class="left">
        <div class="iconfont iconfont-attachment"></div>
        <div>附件</div>
      </div>
      <div class="right">
        <div @click="loadAllFile" v-if="downloadFiles.length">一键下载所有附件</div>
        <div v-else style="color: gray;font-weight: bold;" @click="tipOnNoAttachment">一键下载所有附件</div>
        <i class="iconfont iconfont-download"></i>
      </div>
    </div>
    <div class="files">
      <template v-for="item in downloadFiles" :key="item.chi230">
        <div class="file" @click="downloadTargetFile(item)">
          <YhFileIconName
            :fileName="item.chi231"
            :fileType="item.chi233.replaceAll('.', '')"
          ></YhFileIconName>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import {  downloadFile, downloadZipFile } from '@/service/axios'

import YhTitle from '@/components/project/YhTitle/YhTitle.vue'
import YhFileIconName from '@/components/common/YhFileIconName/YhFileIconName.vue'

export default {
  name: 'ServiceGuideAttachment',
  data() {
    return {}
  },
  props: {
    downloadFiles: {
      type: [Array],
      required: true,
      default: () => [],
    },
  },
  components: { YhTitle, YhFileIconName },
  mounted() {},
  methods: {
    loadAllFile() {
      var chi215List = []
      for(var value of this.downloadFiles){
        if(!!value.chi215){
          chi215List.push(value.chi215)
        }
      }
      if(!!chi215List){
        console.log("chi215List",chi215List)
        // downloadZipFile(JSON.stringify(chi215List))
        downloadZipFile({
          "fileIdListStr" : chi215List
        })
      }
    },
    downloadTargetFile(target) {
      const { chi215, chi231 ,chi233 } = target
      // const fileId = chi215
      // const fileName = chi231 + chi233
      downloadFile(chi215)
    },
    dataToFile(params) {
      let type = params.type
      let fileName = params.fileName
      let data = params.data
      // 兼容 IE
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(new Blob([data]), fileName)
      } else {
        // 非IE 浏览器
        const url = window.URL.createObjectURL(new Blob([data], { type }))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', `${fileName}`)
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(url) // 释放内存
      }
    },
    tipOnNoAttachment() {
      this.$message.warning("暂无附件可下载！")
    }
  },
  computed: {},
}
</script>

<style lang="scss" scoped>
.service-guide-attachment {
  width: 100%;

  .title-wrapper {
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: var(--primary);
    border-bottom: 1px solid #c9e0ff;
    padding-bottom: 6px;
    line-height: 1;

    .left {
      display: flex;
      align-items: center;
      font-size: 18px;
      color: #333;

      .iconfont {
        color: var(--primary);
      }
    }

    .right {
      display: flex;
      align-items: center;
      font-size: 14px;
      cursor: pointer;
      user-select: none;
    }
  }

  .files {
    padding-top: 48px;
    display: flex;
    flex-wrap: wrap;
    .file {
      margin-right: 40px;
      margin-bottom: 20px;
    }
  }
}
</style>
