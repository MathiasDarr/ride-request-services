/* eslint-disable */

import axios from 'axios';

const state = {
    destination:'',
    nriders:'',
    requests: [{rideid:"ride1",user_name:"charles", driver_name:"edward", riders: 3, destination: "131 3rd Ave"}],
    rides:[],

};

const getters = {
    getRideRequests: state => state.requests,

  };

const actions = {
    
    async addRequest({commit}, request){
        commit('addRideRequest', request)
      },

  };

const mutations = {
    addRideRequest: (state, request) => (state.requests.push(request)),
};

export default {
  state,
  getters,
  actions,
  mutations
};
