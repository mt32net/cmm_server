<template>
  <div class="container">
    <!-- not logged in -->
    <div v-if="viewD == 2 || viewD == 3">
      <form @submit="checkRegisterForm" novalidate="true">
        <div class="block">
          <div class="label">Username</div>
          <input v-model="username" type="text" name="username" class="border" />
          <br />
        </div>
        <div class="block" v-if="viewD == 3">
          <div class="label">E-Mail</div>
          <input v-model="mail" type="text" name="mail" class="border" />
        </div>

        <br />

        <input type="submit" value="Sign in" v-if="viewD == 2" />
        <input type="submit" value="Sign up" v-if="viewD == 3" />
      </form>
      <br />
      <a @click="switchLogin" class="switching" v-if="viewD == 2">Switch to register</a>
      <a @click="switchLogin" class="switching" v-if="viewD == 3">Switch to login</a>
    </div>

    <!-- logged in -->
    <div v-if="viewD == 6">
      <h2>You are already logged in as {{ username }}</h2>
      <form @submit="logout" novalidate="true">
        <input type="submit" value="Logout" />
      </form>
    </div>

    <!-- verification -->
    <div v-if="viewD == 0">
      <h1>The verification process is running in background</h1>
    </div>

    <!-- responses -->
    <!--verified-->
    <div v-if="viewD == 1">
      <h1>Verified</h1>
      <h3>Successfully signed up</h3>
    </div>
    <!--signed in-->
    <div v-if="viewD == 7">
      <h1>Signin</h1>
      <h3>Successfully signed in with {{ username }}</h3>
    </div>
    <!--signed up-->
    <div v-if="viewD == 8">
      <h1>Signup</h1>
      <h3>
        Successfully signed up with {{ username }}, check your e-mails to verify
        and login
      </h3>
    </div>

    <div v-if="viewD == 4">
      <h1>Signin</h1>
      <h3>Successfully send signin request with {{ username }}, check your e-mails to login</h3>
    </div>

    <div class="error">{{error}}</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
//@ts-ignore
import { getJSONJWT, delete_cookie } from '@/helper/cookieHelper'
import axios from 'axios'


enum View {
  verifying = 0, //verification is in the process
  verified = 1, //user verified
  signInWindow = 2, //login window
  signUpWindow = 3, //register window
  signInRequest = 4, //requesting login
  alreadyLoggedIn = 6, //already logged in
  signedIn = 7, //successfully signed in
  signedUp = 8 //successfully signed up
}

@Options({})
export default class Login extends Vue {

  username: String = ""
  mail: String = ""
  topic: String = ""

  viewD: View = View.signInWindow

  loggedIn: Boolean = false

  error: String = ""

  checkRegisterForm(e: any) {
    e.preventDefault()
    if (this.viewD == View.signInWindow && this.username != "") {
      this.error = ""
      axios.post("/api/user/login", null, {
        params: {
          username: this.username,
        }
      })
        .then(() => {
          this.viewD = View.signInRequest
        })
        .catch(error => {
          if (error.response.status == 412) {
            this.error = "Username does not exist"
          } else {
            this.error = "An error occured while requesting login mail. Code: " + error.response.status
          }
          console.error(error)
        })
    } else if (this.viewD == View.signUpWindow && this.mail != "" && this.username != "") {
      axios.post("/api/user/register", null, {
        params: {
          username: this.username,
          mail: this.mail
        }
      })
        .then(() => {
          this.viewD = View.signedUp
        })
        .catch(error => {
          console.error(error)
          this.error = "An error occured while requesting login mail. Code: " + error.response.status
        })
    } else {
      this.error = "You have to at least specifiy a username"
    }

    this.init()
  }

  switchLogin() {
    if (this.viewD === View.signInWindow)
      this.viewD = View.signUpWindow
    else if (this.viewD === View.signUpWindow)
      this.viewD = View.signInWindow
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
      this.viewD = View.alreadyLoggedIn
    }
    //@ts-ignore
    var verify = this.$route.query.verify
    if (verify != undefined) {
      this.viewD = View.verifying
      //@ts-ignore
      this.username = this.$route.query.username
      axios.get("/api/user/verify", { params: { loginSecret: verify } })
        .then(() => {
          this.viewD = View.verified
          var jwt = getJSONJWT()
          if (jwt != "") {
            this.username = getJSONJWT().username
            if (this.username != "")
              this.loggedIn = true
            this.viewD = View.alreadyLoggedIn
          }
        })
        .catch(error => {
          console.error(error)
          this.error = "An error occured while requesting login mail. Code: " + error.response.status
        })
    }
    //@ts-ignore
    var token = this.$route.query.token
    if (token != undefined) {
      this.viewD = View.verifying
      axios.post("/api/user/login", null, { params: { token: token } })
        .then(() => {
          this.viewD = View.signedIn
          //@ts-ignore
          window.location = "/login"
          window.location.reload(true)
        })
        .catch(error => {
          console.error(error)
          this.error = "An error occured while requesting login mail. Code: " + error.response.status
        })
    }
  }

  logout() {
    delete_cookie("cmmJWT", "/", "")
    //@ts-ignore
    window.location = "/login"
    window.location.reload(true)
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