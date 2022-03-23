import { requestGetCollectionData } from '@/service'

const getCollectionData = (collection, val) => {
  return requestGetCollectionData(collection, val)
}

export { getCollectionData }
