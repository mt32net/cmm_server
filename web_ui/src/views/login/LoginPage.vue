<template>
  <div ref="relBody">
    <div ref="spacer"></div>
    <div ref="loginCompContainer" class="loginCompContainer">
      <login ref="loginComp" id="loginComp"></login>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Login from "@/components/Login.vue"

export default {
  name: "LoginPage",
  components: { Login },
  methods: {
    updateSpacerHeight() {
      var spacer = this.$refs.spacer
      var login = this.$refs.loginCompContainer
      var windowHeight = window.innerHeight
      var loginHeight = login.clientHeight
      spacer.style.height = (windowHeight - loginHeight) / 2 + "px"
    }
  },
  mounted() {
    this.updateSpacerHeight()
    this.$nextTick(function () {
      window.addEventListener('resize', this.updateSpacerHeight)
    })
  }
};
</script>

<style scoped>
</style>