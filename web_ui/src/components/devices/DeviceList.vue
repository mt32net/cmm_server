<template>
  <div id="nav" class="sticky">
    <router-link :to="deviceURL(deviceE)" v-for="deviceE in devices" :key="deviceE.deviceUUID">
      <DeviceListElement :device="deviceE" />
    </router-link>
    <div v-if="devices.lenght == 0">No devices registered</div>
    <p class="error">{{error}}</p>
  </div>
</template>

<script lang="ts">
//@ts-ignore
import DeviceListElement from '@/components/devices/DeviceListElement.vue'
import { Component, Prop, Vue } from 'vue-property-decorator'
import axios from 'axios'
//@ts-ignore
import { getDevices } from '@/helper/deviceHelper'

@Component({
  name: 'DeviceList',
  components: { DeviceListElement }
})
export default class DeviceList extends Vue {

  devices: Array<any> = []
  error: String = ""

  mounted() {
    getDevices().then(data => this.devices = data)
  }

  deviceURL(device) {
    return "/devices/" + device.deviceUUID
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
}

.content {
  width: 100%;
  top: 0px;
}
</style>