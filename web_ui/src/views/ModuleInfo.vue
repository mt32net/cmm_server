<template>
  <div>
    <div ref="spacer"></div>
    <div v-if="devices.length > 0">
      <div class="grid-container">
        <DeviceModuleInfo
          v-for="device in devices"
          :key="device.deviceUUID"
          :deviceName="device.name"
          :operatingSystemShort="device.osShort"
          :modules="device.modules"
          :online="device.online"
          class="info grid-container"
        />
      </div>
    </div>
    <h1 v-else>No devices registered yet</h1>
    <h1 class="error">{{error}}</h1>
  </div>
</template>

<script lang="ts">
import axios from 'axios'
//@ts-ignore
import DeviceModuleInfo from "@/components/DeviceModuleInfo.vue"

export default {
  name: "Device Summary",
  components: { DeviceModuleInfo },
  methods: {
    getDevices() {
      axios.get("/api/devices/list")
        .then((response) => {
          console.log(response.data.deviceList)
          //@ts-ignore
          this.devices = response.data.deviceList
        })
        .catch(error => {
          console.error(error)
          //@ts-ignore
          this.error = "An error occured while requesting device list. Code: " + error.response.status
        })
    }
  },
  mounted() {
    this.$nextTick(function () {
      this.getDevices()
    })
  },
  data(): {
    devices: Array<any>,
    error: String
  } {
    return {
      devices: [],
      error: ""
    }
  }
}
</script>

<style scoped>
.error {
  color: red;
}

.info {
  width: fit-content;
}

.grid-container {
  display: flex;
  flex-wrap: wrap;
  margin: 1rem;
}

.grid-container {
  width: fit-content;
  flex-grow: 1;
}
</style>