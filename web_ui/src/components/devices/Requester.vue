<template>
  <div class="container">
    <div v-if="viewD == 0">
      <h1>Request login Token for new Device</h1>
      <form @submit="newRequest" action="/" method="post" novalidate="true">
        <input type="submit" value="Request new device-token" />
      </form>
    </div>
    <div v-if="viewD == 1">
      <h1>Requeest sent</h1>
      <h2>Check mails for new generated token</h2>
    </div>
    <div class="error">{{error}}</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import axios from 'axios'
//@ts-ignore
import { getJSONJWT } from '@/helper/cookieHelper'

enum View {
  newDevice = 0, //request new device login
  requestSent = 1 //request for new device login sent
}

@Options({})
export default class Requester extends Vue {

  viewD: View = View.newDevice

  loggedIn: Boolean = false

  error: String = ""

  newRequest(e: any) {
    e.preventDefault()

    this.viewD = View.requestSent
    this.init()
  }

  mounted() {
    this.init()
  }

  init() {
    var jwt = getJSONJWT()
    //@ts-ignore
  }
}

</script>

<style scoped>
.error {
  color: red;
}

.container {
  background-color: #252525;
  width: fit-content;
  border-radius: 5px;
  padding: 2rem;
  margin: 0 auto 0;
}

.block {
  margin: 1rem;
  min-width: 15rem;
  width: 90%;
}

input {
  border-radius: 5px;
  width: 100%;
  height: 1.5rem;
}

input.border:focus {
  border-color: #10abff;
  border-width: 2%;
  border-style: solid;
}

input[type="submit"] {
  width: 80%;
  height: 2rem;
  background-color: #237e3d;
  border-color: #237e3d;
  color: whitesmoke;
}

.label {
  padding-bottom: 0.5rem;
  font-weight: bold;
  font-size-adjust: 0.65;
}

.switching {
  color: #c9c9c9;
  text-decoration: underline;
}

.switching:hover {
  color: #8b8b8b;
}
</style>