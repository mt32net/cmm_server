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
    name: 'Device Selection',
    component: () => import('../views/devices/DeviceSelection.vue'),
    children: [
      {
        path: ':uuid',
        name: 'Module Selection',
        component: () => import('../views/devices/ModuleSelection.vue'),
        children: [
          {
            path: ':module',
            name: 'Module Overview',
            component: () => import('../views/devices/DeviceModuleInfo.vue'),
            children: [
              {
                path: '/info',
                name: 'Device Info',
                component: () => import('../views/devices/ModuleInfos/ModuleInfo_DeviceInfo.vue')
              }
            ]
          }]
      }]
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
