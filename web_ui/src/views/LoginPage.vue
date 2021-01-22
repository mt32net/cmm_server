<template>
  <div class="container">
    <div class="loginCompContainer">
      <login id="loginComp"></login>
    </div>
  </div>
</template>

<script lang="ts">
// @ is an alias to /src
import { Options, Vue } from 'vue-class-component'
//@ts-ignore
import Login from '@/components/Login.vue'
import { nextTick } from 'vue'

@Options({
  components: { Login }
})
export default class LoginPage extends Vue {

  updateSpacerHeight() {
  }

  async mounted() {
  }

}
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
}

.loginCompContainer {
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translateY(-50%) translateX(50%);
  transform: translateY(-50%) translateX(-50%);
  width: fit-content;
}
</style>