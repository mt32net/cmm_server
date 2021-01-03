<template>
  <div>
    <router-link to="/">Home</router-link>
    <router-link to="/about">About</router-link>
    <router-link to="/info">Info</router-link>
    <router-link v-if="!loggedIn" to="/login">Login</router-link>
    <router-link to="/formtest">Formtest</router-link>
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
  vertical-align: left;
  width: fit-content;
  height: 100%;
  left: 0;
  top: 0;
  position: fixed;
  background-color: #393939;
  z-index: 1;
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