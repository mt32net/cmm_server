//import './plugins/axios'
import { createApp } from 'vue'
import { Vue } from 'vue-class-component'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(router)
app.mount('#app')
