<template>
  <div class="sticky nav">
    <ModuleListElement
      :v-bind="devices"
      class="module"
      v-for="module in currentDevice.modules"
      :key="module.name"
      :module="module"
    />
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
//@ts-ignore
import { getDevices } from '@/helper/deviceHelper'
import styled from 'styled-components'
import { Notification } from '@styled-icons/boxicons-regular/Notification'
//@ts-ignore
import ModuleListElement from '@/components/devices/ModuleListElement.vue'

@Component({
  components: { ModuleListElement }
})
export default class ModuleList extends Vue {
  error: String = ""
  devices: Array<any>
  currentDevice: any = { modules: [] }

  async storeDevice() {
    this.devices = await getDevices()
    this.devices.forEach(element => {
      //@ts-ignore
      if (element.deviceUUID == this.$route.params.uuid)
        this.currentDevice = element
    })
    this.currentDevice.modules.unshift({ name: "Info", version: "" })
  }

  mounted() {
    this.storeDevice()
  }
}
</script>

<style scoped>
.nav {
  width: auto;
  background-color: #393939;
  height: 100vh;
  top: 0px;
  max-width: 15rem;
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

.nav a {
  font-weight: bold;
  color: #b1b1b1;
  display: block;
  text-decoration: none;
  margin: 1rem;
  text-align: center;
}

a.router-link-exact-active.nav {
  color: #ffffff;
}

.content {
  width: 100%;
  top: 0px;
}
</style>