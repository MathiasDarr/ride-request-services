import Vuex from 'vuex';
import Vue from 'vue';

import snotel from './modules/snotel'

// Load Vuex
Vue.use(Vuex);

// Create store
export default new Vuex.Store({
  modules: {
    snotel,
  }
});
