<template>
  <div ref="relBody">
    <div ref="spacer"></div>
    <div ref="loginCompContainer" class="loginCompContainer">
      <login ref="loginComp" id="loginComp"></login>
    </div>
  </div>
</template>

<script lang="ts">
// @ is an alias to /src
//@ts-ignore
import Login from '@/components/Login.vue'

var spacer: any
var login: any

export default {
  name: "LoginPage",
  components: { Login },
  methods: {
    updateSpacerHeight() {
      var windowHeight = window.innerHeight
      //@ts-ignore
      var loginHeight = this.login.$el.clientHeight
      //@ts-ignore
      this.spacer.style.height = (windowHeight - loginHeight) / 2 + "px"
    }
  },
  mounted() {
    this.$nextTick(function () {
      this.spacer = this.$refs.spacer as Element
      this.login = this.$refs.loginComp as Element
      window.addEventListener('resize', this.updateSpacerHeight)
      this.updateSpacerHeight()
    })
  },
  data(): {
    spacer: any,
    login: any
  } {
    return {
      spacer: this.$refs.spacer as Element,
      login: this.$refs.loginComp as Element
    }
  }
};
</script>

<style scoped>
</style>