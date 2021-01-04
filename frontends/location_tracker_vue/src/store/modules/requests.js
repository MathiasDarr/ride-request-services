/* eslint-disable */


const state = {
    destination:'',
    nriders:'',
    requests: [{rideid:"ride1",user_name:"charles", driver_name:"edward", riders: 3, destination: "131 3rd Ave"}],
    rides:[],
    requestid:String
};

const getters = {
    getRideRequests: state => state.requests,
    getRequestID: state => state.requestid
  };

const actions = {
    
    async setRequestID({commit}, data){
      console.log("THE DATA COMING IN IS " + data)
        commit('setRideRequestID', data)
      },
  
    async addRequest({commit}, request){
        commit('addRideRequest', request)
      },  
  
    };


    // };

const mutations = {
    addRideRequest: (state, request) => (state.requests.push(request)),
    setRideRequestID: (state, requestid) => (state.requestid = requestid),
};

export default {
  state,
  getters,
  actions,
  mutations
};
