import Vuex from 'vuex';
import Vue from 'vue';

import requests from './modules/requests'

// Load Vuex
Vue.use(Vuex);

// Create store
export default new Vuex.Store({
  modules: {
    requests,
  }
});
