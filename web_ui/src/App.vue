<template>
  <div id="app">
    <NavBar v-bind:loggedIn="loggedIn" />
    <div class="content">
      <router-view />
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
//@ts-ignore
import NavBar from '@/components/navbar.vue'
//@ts-ignore
import { getJSONJWT, isLoggedIn } from '@/helper/cookieHelper'

export default Vue.extend({
  components: {
    NavBar
  },
  updated() {
    this.setSettings()
  },
  mounted() {
    this.$nextTick(() => {
      this.setSettings()
    })
  },
  methods: {
    setSettings() {
      document.title = this.$route.name
      this.cookieData = getJSONJWT()
      this.loggedIn = isLoggedIn()
    }
  },
  data(): {
    loggedIn: Boolean,
    cookieData: String,
  } {
    return {
      loggedIn: false,
      cookieData: ""
    }
  }
})
</script>

<style>
body {
  background-color: #2d2d2d;
  padding: 0;
  margin: 0;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #bdbdbd;
  height: 100%;
  width: 100%;
  font-size: 1rem;
  display: flex;
}
</style>

<style scoped>
.content {
  width: 100%;
  top: 0px;
}
</style>