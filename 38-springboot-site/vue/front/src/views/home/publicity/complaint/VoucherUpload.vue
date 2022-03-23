<template>
  <div class="voucher-upload">
    <YhTitle title="凭证上传" type="03"></YhTitle>
    <div class="upload">
      <div class="reminder">
        温馨提示:最多可同时上传5个文件，每个文件只允许上传2M大小。附件仅限于文件名后缀为:
        jpg、 png、 gif、bmp、jpeg、 tiff、
        svg、webp的文件,禁止exe、bat等格式文件上传!
      </div>
      <YhUploadImages @getFilesList="handleGetFilesList" :fileType="['image']" :fileSize="2" ref="YhUploadImagesRef" ></YhUploadImages>
    </div>
  </div>
</template>
<script>
import YhTitle from "@/components/project/YhTitle/YhTitle.vue";
import YhUploadImages from "@/components/project/YhUploadImages/YhUploadImages.vue";

import { requestUploadFile, requestUploadFileByBase64 } from "@/service";
export default {
  name: "VoucherUpload",
  components: {
    YhTitle,
    YhUploadImages,
  },
  data() {
    return {
      files: [],
      upLoadBase64Arr: [],
    };
  },
  methods: {
    async handleUpLoad(){
      let arr = await this.$refs.YhUploadImagesRef.handleUploadAllfiles()
      let err = false
      arr.forEach(item => {
        if(item.hadUpfile.code !== "1") {
          err = true
        }
      })
      return {arr,err}
    },
    removeAllFile(){
      this.$refs.YhUploadImagesRef.removeAllFile()
    }
  },
};
</script>
<style lang="scss" scoped>
.voucher-upload {
  .upload {
    margin: -15px 0 0 100px;
    padding: 20px;
    border: 1px solid #c6ddff;
    width: 1010px;
    .reminder {
      background: #fff2f1;
      color: #f73627;
      width: 775px;
      font-size: 14px;
      // line-height: 14px;
      padding: 10px;
      margin-bottom: 10px;
    }
  }
}
</style>