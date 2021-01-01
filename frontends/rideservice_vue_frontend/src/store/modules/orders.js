/* eslint-disable */
import axios from 'axios';


const state = {
    orders: [],
};

const getters = {
  getOrders: state => state.orders,

};

const actions = {

  async setOrders({commit}, orders){
    commit('commitOrders', orders)
  },

};

const mutations = {
    commitOrders: (state, orders) => (state.orders = orders),
};

export default {
  state,
  getters,
  actions,
  mutations
};
