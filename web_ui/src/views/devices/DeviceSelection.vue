<template>
  <div class="container">
    <DeviceList :devices="devices" />
    <div class="content">
      <h1 v-if="$route.params.uuid == undefined">No device selected</h1>
      <router-view :devices="devices" v-else />
    </div>
  </div>
</template>

<script lang="ts">
// @ is an alias to /src
//@ts-ignore
//import NavBarDevices from "@/components/devices/navBarDevices.vue"
import { Options, Vue } from 'vue-class-component'
//@ts-ignore
import DeviceList from '@/components/devices/DeviceList.vue'
//@ts-ignore
import { isLoggedIn } from '@/helper/cookieHelper'
//@ts-ignore
import { getDevices } from '@/helper/deviceHelper'

@Options({
  name: "DeviceSelection",
  components: {
    //NavBarDevices,
    DeviceList
  }
})
export default class DeviceSelection extends Vue {
  devices: Array<any> = []

  mounted() {
    getDevices().then(data => this.devices = data as Array<any>)
  }

}
</script>

<style scoped>
.container {
  display: flex;
}

.content {
  width: 100%;
}
</style>
