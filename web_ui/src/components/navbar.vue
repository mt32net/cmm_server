<template>
  <div id="nav">
    <div class="sticky">
      <router-link to="/">Home</router-link>
      <router-link to="/about">About</router-link>
      <router-link to="/info">Info</router-link>
      <router-link to="/login">{{loggedIn?"Logout":"Login"}}</router-link>
      <router-link v-if="loggedIn" to="/devices/">Devices</router-link>
      <router-link to="/formtest">test</router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'

@Component
export default class NavBar extends Vue {

  @Prop({ default: false })
  loggedIn: Boolean = false

}
</script>

<style scoped>
#nav {
  width: fit-content;
  background-color: #393939;
}

.sticky {
  position: -webkit-sticky; /* Safari */
  position: sticky;
}

#nav a {
  font-weight: bold;
  color: #b1b1b1;
  display: block;
  text-decoration: none;
  margin: 1rem;
}

#nav a.router-link-exact-active {
  color: #ffffff;
}
</style>