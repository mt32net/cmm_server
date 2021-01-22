<template>
  <div>
    <div class="container">
      <small class="os">{{operatingSystemShort}}</small>
      <h1>{{deviceName}}</h1>
      <br />
      <p>Last Updated: {{lastUpdated}}</p>
      <p>Avialable Notifications: {{notificationsAvialable}}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';

enum iconType {
  base64Encoded,
  url
}

@Options({
  props: {
    deviceName: String,
    operatingSystemShort: String,
    lastUpdated: String,
    notificationsAvialable: Number
  }
})
export default class DeviceModuleInfo extends Vue {
  deviceName: String = ""
  operatingSystemShort: String = ""
  lastUpdated: String = ""
  notificationsAvialable: Number = 0
}
</script>

<style scoped>
.container {
  background-color: #252525;
  width: 90%;
  border-radius: 5px;
  padding: 2rem;
  margin: 0 auto 0;
  text-align: left;
}

h1 {
  display: inline;
}

.os {
  padding-right: 1rem;
}
</style>