<template>
  <div id="app">
    <NavBar ref="nav" v-bind:loggedIn="loggedIn" />
    <div ref="content" class="content">
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
      window.addEventListener("resize", this.updateContentWidth)
      this.content = this.$refs.content
      this.navBar = this.$refs.nav
      this.updateContentWidth()
      this.setSettings()
    })
  },
  methods: {
    updateContentWidth() {
      var contentSubtract = "0.5rem"
      var navBarWidth = this.navBar.$el.clientWidth
      this.content.style.paddingLeft = "calc(" + navBarWidth + "px - " + contentSubtract + ")"
    },

    setSettings() {
      document.title = this.$route.name
      this.cookieData = getJSONJWT()
      this.loggedIn = isLoggedIn()
    }
  },
  data(): {
    loggedIn: Boolean,
    cookieData: String,
    content: Element,
    navBar: Element
  } {
    return {
      loggedIn: false,
      cookieData: "",
      content: this.$refs.content as Element,
      navBar: this.$refs.nav as Element
    }
  }
})
</script>

<style>
body {
  background-color: #2d2d2d;
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
}
</style>

<style scoped>
</style>