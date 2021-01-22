<template>
  <div class="container">
    <ModuleList :devices="devices" />
    <div class="content">
      <h1 v-if="devices.length == 0">No devices registered</h1>
      <h1 v-else-if="$route.path.split('/').pop() == ''">No module selected</h1>
      <DeviceModuleInfo v-else :devices="devices" />
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
//@ts-ignore
import ModuleList from '@/components/devices/ModuleList.vue'
//@ts-ignore
import DeviceModuleInfo from '@/views/devices/DeviceModuleInfo.vue'
//@ts-ignore
import { isLoggedIn } from '@/helper/cookieHelper'

@Options({
  name: "ModuleSelection",
  components: {
    //NavBarDevices,
    ModuleList,
    DeviceModuleInfo
  },
  props: {
    devices: Array
  }
})
export default class ModuleSelection extends Vue {
  devices: Array<any> = []
}
</script>

<style scoped>
.content {
  width: 100%;
  top: 0px;
}
</style>
