<template>
  <div class="sticky nav">
    <ModuleListElement
      :v-bind="devices"
      class="module"
      v-for="module in currentDevice.modules"
      :key="module.name"
      :module="module"
    />
    <a v-if="devices.length == 0">Could not load modules</a>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
//@ts-ignore
import { getDevices } from '@/helper/deviceHelper'
//@ts-ignore
import ModuleListElement from '@/components/devices/ModuleListElement.vue'

@Options({
  name: "ModuleList",
  components: { ModuleListElement },
  props: {
    devices: Array
  }
})
export default class ModuleList extends Vue {
  error: String = ""
  devices: Array<any> = []

  get currentDevice() {
    var device = { name: "", modules: [{ name: "", version: "" }] }
    this.devices.forEach(element => {
      //@ts-ignore
      if (element.deviceUUID == this.$route.params.uuid)
        device = element
    })
    if (device.modules.length > 0 && device.modules[0].name != "Info")
      device.modules.unshift({ name: "Info", version: "" })
    return device

  }

  mounted() {
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