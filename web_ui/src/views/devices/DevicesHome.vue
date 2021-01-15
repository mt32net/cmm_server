<template>
  <div>
    <NavBarDevices />
    <router-view v-if="loggedIn" />
    <h1 v-else>You are not logged in, you are unable to see further information</h1>
  </div>
</template>

<script lang="ts">
// @ is an alias to /src
//@ts-ignore
import NavBarDevices from "@/components/devices/navBarDevices.vue"
//@ts-ignore
import { getJSONJWT, isLoggedIn } from '@/helper/cookieHelper'


export default {
  name: "Device_Overview",
  components: {
    NavBarDevices,
  },
  created() {
  },
  computed: {
    loggedIn() {
      return isLoggedIn()
    }
  }
};
</script>
