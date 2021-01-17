<template>
  <div class="container">
    <h1>{{currentDevice.name}}</h1>
    <h1 class="title">{{selectedModule}}</h1>

    <div v-if="selectedModule == 'info'">
      <p>Installed OS: {{currentDevice.os}} ({{currentDevice.osShort}})</p>
      <p>
        Online:
        <b :class="onlineClass">{{onlineString}}</b>
      </p>
    </div>
    <div v-else-if="selectedModule == 'notifications'">
      <p>No notifications avialable</p>
    </div>
    <div v-else>
      <h1>No Module selected</h1>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
//@ts-ignore
import { getDevices } from '@/helper/deviceHelper'

@Component
export default class DeviceModuleInfo extends Vue {
  devices: Array<any>
  currentDevice: any = { modules: [] }

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
    getDevices().then(data => {
      this.devices = data
      this.devices.forEach(element => {
        //@ts-ignore
        if (element.deviceUUID == this.$route.params.uuid)
          this.currentDevice = element
      })
    })
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