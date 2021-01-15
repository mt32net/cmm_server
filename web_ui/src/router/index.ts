import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'CMM - Home',
    component: () => import('../views/Home.vue')
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
    component: () => import('../views/LoginPage.vue')
  },
  {
    path: '/info',
    name: 'System Info',
    component: () => import('../components/info.vue')
  },
  {
    path: '/devices',
    name: 'Device Overview',
    component: () => import('../views/devices/DevicesHome.vue'),
    children: [
      {
        path: '/devices/devicesummary',
        name: 'Device Module Summary',
        component: () => import('../views/devices/ModuleInfo.vue')
      },
      {
        path: '/devices/loginRequest',
        name: 'Device Login Request',
        component: () => import('../views/devices/DeviceLoginRequest.vue')
      },
      {
        path: '/devices/notifications',
        name: 'notifications info',
        component: () => import('../views/devices/NotificationsInfo.vue')
      },
    ]
  },
  {
    path: "/formtest",
    name: "Form Test",
    component: () => import('../components/formTest.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
