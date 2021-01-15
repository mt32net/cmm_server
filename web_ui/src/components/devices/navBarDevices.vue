<template>
  <div id="nav">
    <router-link class="router" to="/devices/devicesummary">Installed Modules</router-link>
    <router-link class="router" to="/devices/notifications">Notifications</router-link>
    <router-link class="router" to="/devices/loginRequest">Add Device</router-link>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'

@Component
export default class NavBarDevices extends Vue {

}
</script>

<style scoped>
#nav {
  vertical-align: left;
  width: 100%;
  left: 0;
  top: 0;
  z-index: 1;
  margin-top: 0.7rem;
  margin-bottom: 3rem;
}

.router {
  font-weight: bold;
  color: #b1b1b1;
  display: inline;
  text-decoration: none;
  margin: 1rem;
  padding: 1rem;
  border-radius: 0.2rem;
  background-color: #393939;
}

.router.router-link-exact-active {
  color: #ffffff;
}
</style>