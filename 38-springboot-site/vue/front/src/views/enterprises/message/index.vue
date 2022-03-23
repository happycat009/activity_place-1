<template>
  <div class="enterprise-message">
    <Toolbar @updateParam="updateParam" :unSeeNum="messageUnreadCount"></Toolbar>
    <TableMessage :toolbarParam="toolbarParam" @updateMessageUnreadCount="updateMessageUnreadCount"></TableMessage>
  </div>
</template>


<script>
  import Toolbar from './Toolbar.vue'
  import TableMessage from './TableMessage.vue'

  export default {
    name: "MessageIndex",
    components: {Toolbar,TableMessage},
    data() {
      return {
        toolbarParam: {},
        messageUnreadCount: 0
      }
    },
    methods: {
      updateParam(paramObj) {
        this.toolbarParam = {
          ...this.toolbarParam,
          ...paramObj
        }
      },
      updateMessageUnreadCount(val) {
        this.messageUnreadCount = val
      }
    }
  }
</script>

<style lang="scss" scoped>
.enterprise-message {
  width: 100%;
  border: solid 2px rgba(216, 232, 255, 0.51);
  padding: 0 30px;
}
</style>
