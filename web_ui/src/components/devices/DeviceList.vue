<template>
  <div id="nav" class="sticky">
    <router-link :to="deviceURL(deviceE)" v-for="deviceE in devices" :key="deviceE.deviceUUID">
      <DeviceListElement :device="deviceE" />
    </router-link>
    <a v-if="devices.length == 0">No devices registered</a>
    <p class="error">{{error}}</p>
  </div>
</template>

<script lang="ts">
//@ts-ignore
import DeviceListElement from '@/components/devices/DeviceListElement.vue'
import { Options, Vue } from 'vue-class-component'
import axios from 'axios'
//@ts-ignore
import { getDevices } from '@/helper/deviceHelper'

@Options({
  name: "DeviceList",
  components: { DeviceListElement },
  props: {
    devices: Array
  }
})
export default class DeviceList extends Vue {

  devices: Array<any> = []

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