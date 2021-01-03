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
import NavBar from '@/components/navbar.vue'

function getCookie(cname): string {
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

function getDecodedJWT(): string {
  var cookieRAW = getCookie("cmmJWT")
  if (cookieRAW != "") {
    var encodedPayload = cookieRAW.split(".")[1]
    return atob(encodedPayload)
  }
  else return ""
}

function getJSONJWT(): any {
  var json = getDecodedJWT()
  if (json != "")
    return JSON.parse(json)
  else return ""
}

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
      this.cookieData = getDecodedJWT()
      this.loggedIn = (this.cookieData != "")
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