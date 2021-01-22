<template>
  <div v-if="device != undefined">
    <div :class="onlineClass" class="name">{{device.name}}</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({
  name: "DeviceListElement",
  props: {
    device: { online: false, os: "", osShort: "", modules: [] }
  }
})
export default class DeviceListElement extends Vue {

  device: any

  get onlineClass() {
    return (this.device.online) ? "online" : "offline"
  }

  get onlineString() {
    return (this.device.online) ? "yes" : "no"
  }
}
</script>

<style scoped>
.os {
  padding-right: 0.5rem;
}

.name {
  width: fit-content;
  max-width: 15rem;
  overflow: hidden;
}

.online {
  color: green;
}

.offline {
  color: red;
}
</style>