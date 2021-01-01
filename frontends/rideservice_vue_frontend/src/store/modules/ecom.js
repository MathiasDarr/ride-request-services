/* eslint-disable */

import axios from 'axios';


const state = {
    products_map: Object.create(null),
    products: [],
    cart: [],
    categories: []
};

const getters = {
  getProducts: state => state.products,
  getCategories: state => state.categories,
  getProductsCategoryMap: state => state.products_map
};

const actions = {

  async setCategoryProducts({commit}, category_products){
    commit('setCategoryProducts', category_products)
  },

  async setCategories({commit}, categories){
      commit('setCategories', categories)
  },



  async fetchProducts({commit}){
    axios.get('http://localhost:3000/products').then((response) => {
      commit('setProducts', response.data)
    }, (error) => {
      console.log(error);
    });
  },

  async addDeleteItem({commit}, product){
    console.log("The product looks like with quantity" + product.quantity)
    commit('addDeleteItem', product)
  } 


};

const mutations = {
    setCategoryProducts(state, category_products){
      state.products_map[category_products.category] = category_products.products
      // console.log(state.products_map[category_products.category])
    },

    setCategories: (state, categories) => (state.categories = categories),
    setProducts: (state, products) => (state.products = products),
    addDeleteItem (state, product ) {

        if(state.cart.has(product.id)){
             state.cart.set(state.cart.get(product.id) + product.quantity)
        }else {
           state.cart.set(product.id, product.quantity)
        }
        // if(state.cart.get(id) == 0){
        //     state.cart.delete(id)
        // }
      },
};

export default {
  state,
  getters,
  actions,
  mutations
};
