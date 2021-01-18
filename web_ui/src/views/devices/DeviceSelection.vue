<template>
  <div class="container">
    <DeviceList :devices="devices" />
    <div class="content">
      <router-view :devices="devices" />
      <h1 v-if="$route.params.uuid == undefined">No device selected</h1>
    </div>
  </div>
</template>

<script lang="ts">
// @ is an alias to /src
//@ts-ignore
//import NavBarDevices from "@/components/devices/navBarDevices.vue"
import { Component, Prop, Vue } from 'vue-property-decorator'
//@ts-ignore
import DeviceList from '@/components/devices/DeviceList.vue'
//@ts-ignore
import { isLoggedIn } from '@/helper/cookieHelper'
//@ts-ignore
import { getDevices } from '@/helper/deviceHelper'
import axios from 'axios'


export default {
  name: "Device_Selection",
  components: {
    //NavBarDevices,
    DeviceList
  },
  mounted() {
    getDevices().then(data => this.devices = data)
  },
  data(): {
    devices: Array<any>,
  } {
    return {
      devices: []
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
}

.content {
  width: 100%;
}
</style>
