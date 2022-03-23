<template>
  <div class="yh-file-icon-name" :style="tagStyle" :title="fileName">
    <img :src="imgSrc" alt="" />
    <div class="file-name" style="width: 100%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap">
      {{fileName}}
    </div>
  </div>
</template>

<script>
import imagePdf from './images/pdf.png'
import imageWord from './images/word.png'
import imageExcel from './images/excel.png'
/**
 * 文件图标和文字 组件
 * @param {String} fileNmae 文件名称
 * @param {String} fileType 文件类型 pdf word excel
 */
export default {
  name: 'YhFileIconName',
  props: {
    fileName: {
      type: [String],
      default: '',
    },
    fileType: {
      type: [String],
      default: '',
    },
  },
  setup() {
    return {}
  },
  data() {
    return {}
  },
  created() {},
  mounted() {},
  components: {},
  computed: {
    imgSrc() {
      const fileType = this.fileType
      let str = ''
      switch(fileType) {
        case 'word':
        case 'doc':
          str = imageWord
          break
        case 'pdf':
          str = imagePdf
          break
        case 'excel':
        case 'xls':
        case 'xlsx':
          str = imageExcel
          break
      }
      return str
    },
  },
  methods: {},
}
</script>

<style lang="scss" scoped>
.yh-file-icon-name {
  position: relative;
  padding-bottom: 28px;
  cursor: pointer;
  user-select: none;
  img {
    width: 78px;
  }
  .file-name {
    position: absolute;
    font-size: 16px;
    line-height: 1;
    color: #333;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    font-size: 16px;
    white-space: nowrap;
  }
}
</style>
