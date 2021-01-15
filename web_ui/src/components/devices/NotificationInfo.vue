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
import { Component, Prop, Vue } from 'vue-property-decorator'

enum iconType {
  base64Encoded,
  url
}

@Component
export default class DeviceModuleInfo extends Vue {

  @Prop({ default: "NA" })
  deviceName: String;

  @Prop({ default: "NA" })
  operatingSystemShort: String;

  @Prop({ default: "now" })
  lastUpdated: String;

  @Prop({ default: 0 })
  notificationsAvialable: Number;
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