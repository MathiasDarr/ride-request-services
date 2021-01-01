<template>
  <v-container>
    <v-layout>
      <v-flex md3>
        <!-- <BaseNavBar v-bind:categories=cate /> -->
        <ProfileNavBar /> 
      </v-flex>
      <v-flex md9>
          <!-- <v-card flat>
            <ProductList />
          </v-card> -->
      </v-flex>

    </v-layout>
  </v-container>

</template>

<script>

/* eslint-disable */

// import BaseNavBar from '../BaseNavBar'
import { mapGetters, mapActions } from "vuex";
import ProfileNavBar from './ProfileNavBar'

export default {


   components:{
        ProfileNavBar
    },

    props:{

    },


    methods:{
        ...mapActions(["fetchProducts"]),
    },
    computed: {
        ...mapGetters(["getProducts"]),
    },
    created(){

    },

    data () {
      return {
        items: [
          { title: 'Dashboard', icon: 'mdi-view-dashboard' },
          { title: 'Photos', icon: 'mdi-image' },
          { title: 'About', icon: 'mdi-help-box' },
        ],
        right: null,

      }
    },


}

</script>