<template>
  <div id="app">
    <NavBar />
    <div class="content">
      <router-view />
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
//@ts-ignore
import NavBar from '@/components/navbar.vue'
//@ts-ignore
import { getJSONJWT, isLoggedIn } from '@/helper/cookieHelper'
import { nextTick } from 'vue'
import router from 'vue-router'

@Options({
  name: "App",
  components: {
    NavBar
  },
})
export default class App extends Vue {
  loggedIn: Boolean = false
  cookieData: String = ""

  updated() {
    this.setSettings()
  }

  async mounted() {
    await nextTick()
    this.setSettings()
  }

  setSettings() {
    //@ts-ignore
    document.title = this.$route.name
    this.cookieData = getJSONJWT()
    this.loggedIn = isLoggedIn()
  }
}
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