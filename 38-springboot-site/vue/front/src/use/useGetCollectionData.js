import { ref, computed, watch } from 'vue'
import { requestGetCollectionData } from '@/service'

const a = ref('')

const getCollectionData = async (key, val) => {
  const res = await requestGetCollectionData(key, val)
  const aaa103 = res
  return aaa103
}

const useGetCollectionData = (key, val) => {
  const data = getCollectionData(key, val)
  data.then((res) => {
    console.log(res, '99999')
  })
  console.log(data, '888')
}

export { useGetCollectionData }
