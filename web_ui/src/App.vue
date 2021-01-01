<template>
  <div id="app">
    <NavBar id="nav" ref="nav" v-bind:loggedIn="loggedIn" />
    <div ref="content" class="content">
      <router-view />
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
//@ts-ignore
import info from '@/components/info.vue'
//@ts-ignore
import NavBar from '@/components/navbar.vue'

function getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function getDecodedJWT() {
  var cookieRAW = getCookie("cmmJWT")
  var encodedPayload = cookieRAW.split(".")[1]
  return atob(encodedPayload)
}

function getJSONJWT() {
  return JSON.parse(getDecodedJWT())
}


export default Vue.extend({
  components: {
    NavBar
  },
  updated() {
    this.setSettings()
  },
  mounted() {
    this.$nextTick(function () {
      window.addEventListener('resize', this.updateContentWidth)
      this.content = this.$refs.content
      this.navBar = this.$refs.nav
      this.updateContentWidth()
      this.setSettings()
    })
    this.declareGlobalVariables()


  },
  methods: {
    declareGlobalVariables() {
      //@ts-ignore
      //window.apiServer = "localhost:9000"
    },

    updateContentWidth() {
      var contentSubtract = ".5rem"
      var navBarWidth = this.navBar.$el.clientWidth
      this.content.style.paddingLeft = "calc(" + navBarWidth + "px - " + contentSubtract + ")"
    },

    setSettings() {
      document.title = this.$route.name
      this.cookieData = getDecodedJWT()
      this.loggedIn = (this.cookieData != "" || this.cookieData != undefined)
    }
  },
  data(): {
    loggedIn: Boolean,
    cookieData: String,
    content: any,
    nacBar: any
  } {
    return {
      loggedIn: false,
      cookieData: "",
      content: this.$refs.content,
      nacBar: this.$refs.nav
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