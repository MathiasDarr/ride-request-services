import Vue from 'vue'
import Router from 'vue-router'
// import GalleryMenu from './components/gallery/GalleryMenu'
import Landing from './components/Landing'



Vue.use(Router)

export default new Router({
  mode:'history',
  base: process.env.BASE_URL,
  routes: [

    {
      path: '/',
      component: Landing
    },

  ]
})
