<template>
  <div>
    <div class="container">
      <small class="os">{{operatingSystemShort}}</small>
      <h1>{{deviceName}}</h1>
      <br />
      <p>
        Online:
        <b :class="onlineClass">{{onlineString}}</b>
      </p>
      <p>Installed Modules:</p>
      <ul>
        <li v-for="module in modules" :key="module.name">{{module.name}}: {{module.version}}</li>
      </ul>
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

  @Prop({ default: false })
  online: Boolean;

  @Prop({ default: [] })
  modules: Array<any>;


  get onlineClass() {
    return (this.online) ? "online" : "offline"
  }

  get onlineString() {
    return (this.online) ? "yes" : "no"
  }
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

.online {
  color: green;
}

.offline {
  color: red;
}
</style>