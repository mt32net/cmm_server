import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import Home from '../views/Home.vue'
import LoginPage from '../views/login/LoginPage.vue'
import FormTest from '../components/formTest.vue'
import info from '../components/info.vue'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'CMM - Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginPage
  },
  {
    path: '/info',
    name: 'System Info',
    component: info
  },
  {
    path: "/formtest",
    name: "Form Test",
    component: FormTest
  }
]

const router = new VueRouter({
  routes
})

export default router
