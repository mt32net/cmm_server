<template>
  <div class="container">
    <p>Installed OS: {{currentDevice.os}} ({{currentDevice.osShort}})</p>
    <p>
      Online:
      <b :class="onlineClass">{{onlineString}}</b>
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
//@ts-ignore
import { getDevices } from '@/helper/deviceHelper'

@Component
export default class ModuleInfo_DeviceInfo extends Vue {
  @Prop({ default: { online: false, os: "", osShort: "", modules: [] } })
  currentDevice: any

  get onlineClass() {
    return (this.currentDevice.online) ? "online" : "offline"
  }

  get onlineString() {
    return (this.currentDevice.online) ? "yes" : "no"
  }

  get selectedModule() {
    //@ts-ignore
    return this.$route.params.module.toLowerCase()
  }

  mounted() {

  }
}
</script>

<style scoped>
.container {
  width: fit-content;
  height: fit-content;
  padding: 2rem;
  margin: 0 auto 0;
  text-align: left;
  top: 0px;
}

.title {
  display: block;
}

h1 {
  display: block;
}

.os {
  padding-right: 1rem;
}

.online {
  color: green;
}

.offline {
  color: red;
}
</style>