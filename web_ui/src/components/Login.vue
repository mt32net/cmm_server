<template>
  <div class="container">
    <div v-if="!loggedIn">
      <div v-if="!userActionDone && !verify">
        <form @submit="checkRegisterForm" novalidate="true">
          <div class="block">
            <div class="label">Username</div>
            <input v-model="username" type="text" name="username" class="border" />
            <br />
          </div>
          <div class="block" v-if="!login">
            <div class="label">E-Mail</div>
            <input v-model="mail" type="text" name="mail" class="border" />
          </div>

          <br />

          <input type="submit" value="Sign in" v-if="login" />
          <input type="submit" value="Sign up" v-if="!login" />
        </form>
        <br />
        <a @click="switchLogin" class="switching" v-if="login">Switch to register</a>
        <a click="switchLogin" class="switching" v-if="!login">Switch to login</a>
      </div>
    </div>

    <div v-if="loggedIn">
      <h2>You are already logged in as {{ username }}</h2>
      <form @submit="logout" novalidate="true">
        <input type="submit" value="Logout" />
      </form>
    </div>

    <div v-if="verify">
      <h1>The verification process is running in background</h1>
    </div>

    <div v-if="userActionDone">
      <div v-if="verified">
        <h1>Verified</h1>
        <h3>{{ username }} was successfully verified</h3>
      </div>
      <div v-if="signin">
        <h1>Signin</h1>
        <h3>Successfully signed in with {{ username }}</h3>
      </div>
      <div v-if="signup">
        <h1>Signup</h1>
        <h3>
          Successfully signup with {{ username }}, check your e-mails to verify
          and login
        </h3>
      </div>
      <div v-if="signup">User created, check your mails to verify user</div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import axios from 'axios'

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

function delete_cookie(name: String, path: String, domain: String) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? ";path=" + path : "") +
      ((domain) ? ";domain=" + domain : "") +
      ";expires=Thu, 01 Jan 1970 00:00:01 GMT";
  }
}

@Component
export default class Login extends Vue {

  username: String = ""
  mail: String = ""
  login: Boolean = true

  verify: Boolean = false
  userActionDone: Boolean = false

  verified: Boolean = false
  signin: Boolean = false
  signup: Boolean = false
  topic: String = ""

  loggedIn: Boolean = false

  checkRegisterForm(e: any) {
    e.preventDefault()
    if (this.login && this.username != "") {
      axios.get("/api/user/login", {
        params: {
          username: this.username,
        }
      })
        .then(() => {
          this.topic = "signin"
          this.userActionDone = true
        })
        .catch(error => {
          console.log(error)
        })
    }
    if (!this.login && this.mail != "" && this.username != "") {
      axios.post("/api/user/register", null, {
        params: {
          username: this.username,
          mail: this.mail
        }
      })
        .then(() => {
          this.topic = "signup"
          this.userActionDone = true
        })
        .catch(error => {
          console.error(error)
        })
    }

    switch (this.topic) {
      case "verified":
        this.verified = true
        break
      case "signin":
        this.signin = true
        break
      case "signup":
        this.signup = true
        break
    }

    this.init()
  }

  switchLogin() {
    this.login = !this.login
  }

  mounted() {
    this.init()
  }

  init() {
    var jwt = getJSONJWT()
    if (jwt != "") {
      this.username = getJSONJWT().username
      if (this.username != "")
        this.loggedIn = true
    }
    //@ts-ignore
    var verify = this.$route.query.verify
    if (verify != undefined) {
      this.verify = true
      axios.get("/api/user/verify", { params: { loginSecret: verify } })
        .then(() => {
          this.verify = false
          this.userActionDone = true
          this.topic = "verify"
        })
        .catch(error => {
          console.error(error)
        })
    }
  }

  logout() {
    delete_cookie("cmmJWT", "/", "")
  }
}

</script>

<style scoped>
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