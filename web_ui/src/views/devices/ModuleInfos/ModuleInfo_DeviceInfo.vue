<template>
  <div class="container">
    <p>Installed OS: {{currentDevice.os}} ({{currentDevice.osShort}})</p>
    <p>
      Online:
      <b :class="boolToClass(currentDevice.online)">{{boolToString(currentDevice.online)}}</b>
    </p>
    <p>
      Verified:
      <b
        :class="boolToClass(currentDevice.verified)"
      >{{boolToString(currentDevice.verified)}}</b>
    </p>
    <p>Architecture: {{currentDevice.arch}}</p>
    <p>Mac-Address: {{currentDevice.mac}}</p>
    <p>Installed Modules:</p>
    <ul>
      <li
        v-for="module in currentDevice.modules"
        :key="module.name"
      >{{module.name}} {{module.version}}</li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({
  name: "ModuleInfo_DeviceInfo",
  props: {
    currentDevice: { online: false, os: "", osShort: "", modules: [] }
  }
})
export default class ModuleInfo_DeviceInfo extends Vue {

  currentDevice: any = { online: false, os: "", osShort: "", modules: [] }

  boolToString(value: Boolean): String {
    return value ? "yes" : "no"
  }

  boolToClass(value: Boolean): String {
    return value ? "online" : "offline"
  }

  get selectedModule() {
    //@ts-ignore
    return this.$route.params.module.toLowerCase()
  }

  mounted() {
  }
}
</script>

<style scoped>
.container {
  width: fit-content;
  height: fit-content;
  padding: 2rem;
  margin: 0 auto 0;
  text-align: left;
  top: 0px;
}

.title {
  display: block;
}

h1 {
  display: block;
}

.os {
  padding-right: 1rem;
}

.online {
  color: green;
}

.offline {
  color: red;
}
</style>