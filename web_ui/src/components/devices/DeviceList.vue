<template>
  <div id="nav" class="sticky">
    <router-link :to="deviceURL(deviceE)" v-for="deviceE in devices" :key="deviceE.deviceUUID">
      <DeviceListElement :device="deviceE" />
    </router-link>
    <a v-if="devices.length == 0">No devices registered</a>
    <a @click="addDevice" href="/#/devices/">{{addDeviceText}}</a>
    <p class="error">{{error}}</p>
  </div>
</template>

<script lang="ts">
//@ts-ignore
import DeviceListElement from '@/components/devices/DeviceListElement.vue'
import { Options, Vue } from 'vue-class-component'
import axios from 'axios'
//@ts-ignore
import { addNewDevice } from '@/helper/cmm'

@Options({
  name: "DeviceList",
  components: { DeviceListElement },
  props: {
    devices: Array
  }
})
export default class DeviceList extends Vue {

  devices: Array<any> = []

  addDeviceText: String = "Add new Device"

  mounted() {
  }

  deviceURL(device: any) {
    return "/devices/" + device.deviceUUID
  }

  get error() {
    if (this.devices.length == 0)
      return "No devices"
    return ""
  }

  async addDevice() {
    this.addDeviceText = "Sending request..."
    await addNewDevice()
    this.addDeviceText = "Request sent"
  }
}
</script>

<style scoped>
#nav {
  width: fit-content;
  background-color: #353535;
  height: 100vh;
  top: 0px;
}

.sticky {
  position: -webkit-sticky;
  position: sticky;
  top: 0;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  -ms-box-sizing: border-box;
  box-sizing: border-box;
  overflow: visible;
}

#nav a {
  font-weight: bold;
  color: #b1b1b1;
  display: block;
  text-decoration: none;
  margin: 1rem;
}

#nav a.router-link-exact-active {
  color: #ffffff;
}

.error {
  color: red;
  font-weight: bold;
}

.content {
  width: 100%;
  top: 0px;
}
</style>