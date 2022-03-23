import { ref, computed, watch } from 'vue'
import { requestGetCollectionData } from '@/service'

const codeTableDesc = ref('')

const getCollectionData = (key, val) => {
  requestGetCollectionData(key, val).then((res) => {
    const aaa103 = res
    codeTableDesc.value = aaa103
  })
  console.log('getCollectionData===')
}

const useGetCollectionDesc = (key, val) => {
  debugger
  console.log('useGetCollectionDesc===')
  getCollectionData(key, val)
  if (codeTableDesc.value) {
    return codeTableDesc.value
  } else {
    return ''
  }
}

watch(codeTableDesc, (val) => {
  console.log(val, 'watch codeTableDesc val')
})

export { useGetCollectionDesc }
