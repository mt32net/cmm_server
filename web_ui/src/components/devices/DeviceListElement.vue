<template>
  <div v-if="device != undefined">
    <div :class="onlineClass" class="name">{{device.name}}</div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'

@Component({
  name: 'DeviceListElement'
})
export default class DeviceListElement extends Vue {

  @Prop({ default: undefined })
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