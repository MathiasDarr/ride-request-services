import Vuex from 'vuex';
import Vue from 'vue';
import auth from './modules/auth'
import upload from './modules/upload'
import ecom from './modules/ecom'
import orders from './modules/orders'
import cart from './modules/cart'



// Load Vuex
Vue.use(Vuex);

// Create store
export default new Vuex.Store({
  modules: {
    auth,
    upload,
    ecom,
    orders,
    cart
  }
});
