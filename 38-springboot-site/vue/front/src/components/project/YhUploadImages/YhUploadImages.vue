<template>
  <el-upload
    action="#"
    list-type="picture-card"
    :on-change="handleChange"
    :limit="props.maxLength"
    :auto-upload="false"
    ref="uploadRef"
  >
    <template #default>
      <el-icon><plus /></el-icon>
    </template>
    <template #file="{ file }">
      <div>
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
        <span class="el-upload-list__item-actions">
          <span
            class="el-upload-list__item-preview"
            @click="handlePictureCardPreview(file)"
          >
            <el-icon><zoom-in /></el-icon>
          </span>
          <!-- <span
            v-if="!disabled"
            class="el-upload-list__item-delete"
            @click="handleDownload(file)"
          >
            <el-icon><download /></el-icon>
          </span> -->
          <span
            v-if="!disabled"
            class="el-upload-list__item-delete"
            @click="handleRemove(file)"
          >
            <el-icon><delete /></el-icon>
          </span>
        </span>
      </div>
    </template>
  </el-upload>
  <el-dialog v-model="dialogVisible">
    <img width="100%" :src="dialogImageUrl" alt="" />
  </el-dialog>
</template>
<script lang="ts" setup>
import { ref, reactive } from "vue";
import { Plus, ZoomIn, Download, Delete } from "@element-plus/icons-vue";
// import type { UploadFile } from "element-plus/es/components/upload/src/upload.type";
import { requestUploadFile, requestUploadFileByBase64 } from "@/service";
import { ElMessage } from "element-plus";

const dialogImageUrl = ref("");
const dialogVisible = ref(false);
const disabled = ref(false);
const uploadRef = ref(null);

const emit = defineEmits(["getFilesList"]);

const props = defineProps({
  maxLength: {
    type: Number,
    default: 5,
  },
  // 限制文件上传类型 如 image application video
  fileType: {
    type: Array,
    default: () => [],
  },
  fileSize: {
    type: Number,
    default: 0,
  },
});

const data = reactive({
  fileList: [],
  imagesBase64Arr: [],
});

const removeAllFile = () => {
  console.log('aaa')
  uploadRef.value.uploadFiles = [];
  data.fileList = []
}

const handleRemove = (file) => {
  // 实现缩略图模板时删除文件
  let fileList = uploadRef.value.uploadFiles;
  let index = fileList.findIndex((fileItem) => {
    return fileItem.uid === file.uid;
  });
  fileList.splice(index, 1);
  data.fileList = [...fileList];
};
const handlePictureCardPreview = (file) => {
  dialogImageUrl.value = file.url!;
  dialogVisible.value = true;
};
const handleDownload = (file) => {
  console.log(file);
};
const handleChange = (file, list) => {
  let [fileType] = file.raw.type.split("/");
  if (props.fileType.length !== 0) {
    let res = props.fileType.find((item) => item == fileType);
    if (!res) {
      handleRemove(file);
      ElMessage.warning(`文件类型不为${props.fileType.join("、")},无法上传`);
      // return false;
    }
  }
  console.log(file, file.size / 1048576);
  if (props.fileSize > 0 && props.fileSize < file.size / 1048576) {
    handleRemove(file);
    ElMessage.warning(`文件大于${props.fileSize}M,无法上传`);
  }
  data.fileList = [...list];
};

const handlerGetBase64 = (item) => {
  return new Promise((resolve, reject) => {
    const file = item.raw;
    const fileReader = new FileReader();
    fileReader.readAsDataURL(file);
    fileReader.onload = (res) => {
      const base64 = fileReader.result;
      resolve(base64);
    };
    fileReader.onerror = (err) => {
      reject(err);
    };
  });
};

const handleUploadAllfiles = () => {
  return new Promise((resolve, reject) => {
    let arr = [];
    data.imagesBase64Arr = [];
    if (data.fileList.length == 0) {
      resolve(arr);
      return;
    }
    data.fileList.forEach(async (item, index) => {
      let base64 = await handlerGetBase64(item);
      // data.imagesBase64Arr.push(base64);
      let row = {};
      console.log(item,'va')
      let [,type] = item.raw.type.split("/");
      let [name] =item.name.split('.'+type)
      let value = {base64,name}
      let file = await uploadFile(value, row);
      arr.push({ file: item, hadUpfile: file });
      if (index == data.fileList.length - 1) {
        console.log(arr, "cccc");
        resolve(arr);
      }
    });
  });
};

const uploadFile = async (value, row) => {
  let {base64,name} = value
  const data = await requestUploadFileByBase64(base64,name);
  const { bucketid, objectid, objectkey } = data.data;
  row.path = base64;
  row.bucketid = bucketid;
  row.objectid = objectid;
  row.objectkey = objectkey;
  return data;
  // emit('upload', row)
};

defineExpose({
  handleUploadAllfiles,
  removeAllFile
});
</script>
