import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
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
    path: '/user',
    name: 'User info',
    component: () => import('../views/User.vue')
  },
  {
    path: '/devices',
    name: 'Device Selection',
    props: true,
    component: () => import('../views/devices/DeviceSelection.vue'),
    children: [
      {
        path: ':uuid',
        name: 'Module Selection',
        component: () => import('../views/devices/ModuleSelection.vue'),
        children: [
          //module routes
          {
            path: 'info',
            name: 'Device Info',
            component: () => import('../views/devices/ModuleInfos/ModuleInfo_DeviceInfo.vue')
          },
          {
            path: 'notifications',
            name: 'Notifications',
            component: () => import('../views/devices/ModuleInfos/ModuleInfo_DeviceNotifications.vue')
          }
        ]
      }
    ]
  },
  {
    path: "/formtest",
    name: "Form Test",
    component: () => import('../components/formTest.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
