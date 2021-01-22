<template>
  <div class="container">
    <h1>{{currentDevice.name}}</h1>
    <h1 class="title">{{moduleName}}</h1>
    <router-view :currentDevice="currentDevice" :key="devices" />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
//@ts-ignore
import { getDevices } from '@/helper/deviceHelper'

@Options({
  name: "DeviceModuleInfo",
  props: {
    devices: Array
  }
})
export default class DeviceModuleInfo extends Vue {

  devices: Array<any> = []

  get onlineClass() {
    return (this.currentDevice.online) ? "online" : "offline"
  }

  get onlineString() {
    return (this.currentDevice.online) ? "yes" : "no"
  }

  get selectedModule() {
    //@ts-ignore
    return this.$route.path.split('/').pop().toLowerCase()
  }

  get moduleName() {
    return this.selectedModule.charAt(0).toUpperCase() + this.selectedModule.slice(1)
  }

  get currentDevice() {
    var device = { modules: [{ name: "Not found", version: "NaN" }], online: false, name: "Not found", os: "Undefined", osShort: "undef" }
    this.devices.forEach(element => {
      //@ts-ignore
      if (element.deviceUUID == this.$route.params.uuid)
        device = element
    })
    if (device.modules.length > 0 && device.modules[0].name.toLowerCase() != "Info".toLowerCase())
      device.modules.unshift({ name: "Info", version: "" })
    return device
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