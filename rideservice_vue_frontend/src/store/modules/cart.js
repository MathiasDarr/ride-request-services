// import axios from 'axios';

const state = {
    cart: new Map()

};

const getters = {
    getCart: state => state.cart
};

const actions = {


  async addDeleteItem({commit}, product){
    console.log("The product looks like with quantity" + product.quantity)
    commit('addDeleteItem', product)

  }
};

const mutations = {
    addDeleteItem (state, product ) {
        var id = product.vendor + product.productName
        if(state.cart.has(id)){
             state.cart.set(state.cart.get(id) + product.quantity)
        }else {
           state.cart.set(product.id, product.quantity)
        }

      },
};

export default {
  state,
  getters,
  actions,
  mutations
};
