<template>
  <div class="container">
    <ModuleList />
    <div class="content">
      <router-view />
      <h1 v-if="$route.params.module == undefined">No module selected</h1>
    </div>
  </div>
</template>

<script lang="ts">
// @ is an alias to /src
//@ts-ignore
//import NavBarDevices from "@/components/devices/navBarDevices.vue"
//@ts-ignore
import ModuleList from '@/components/devices/ModuleList.vue'
//@ts-ignore
import { isLoggedIn } from '@/helper/cookieHelper'


export default {
  name: "Device_Overview",
  components: {
    //NavBarDevices,
    ModuleList
  }
};
</script>

<style scoped>
.content {
  width: 100%;
  top: 0px;
}
</style>
