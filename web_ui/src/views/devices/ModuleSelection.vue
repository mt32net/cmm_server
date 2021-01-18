<template>
  <div class="container">
    <ModuleList :devices="devices" />
    <div class="content">
      <router-view :devices="devices" />
      <h1 v-if="devices.length == 0">No devices registered</h1>
      <h1 v-else-if="$route.params.module == undefined">No module selected</h1>
    </div>
  </div>
</template>

<script lang="ts">
//@ts-ignore
import ModuleList from '@/components/devices/ModuleList.vue'
//@ts-ignore
import { isLoggedIn } from '@/helper/cookieHelper'


export default {
  name: "Module_Selection",
  components: {
    //NavBarDevices,
    ModuleList
  },
  props: {
    devices: Array
  }
};
</script>

<style scoped>
.content {
  width: 100%;
  top: 0px;
}
</style>
