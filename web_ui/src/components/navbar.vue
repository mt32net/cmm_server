<template>
  <div id="nav" class="sticky">
    <router-link to="/">Home</router-link>
    <router-link to="/about">About</router-link>
    <router-link to="/info">Info</router-link>
    <router-link to="/login">{{loggedIn?"Logout":"Login"}}</router-link>
    <router-link v-if="loggedIn" to="/devices/">Devices</router-link>
    <router-link v-if="loggedIn" to="/user/">User</router-link>
    <router-link to="/formtest">test</router-link>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
//@ts-ignore
import { isLoggedIn } from '@/helper/cookieHelper'

@Options({
  name: "NavBar",
})
export default class NavBar extends Vue {
  get loggedIn() {
    return isLoggedIn()
  }
}
</script>

<style scoped>
#nav {
  width: fit-content;
  background-color: #393939;
  height: 100vh;
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