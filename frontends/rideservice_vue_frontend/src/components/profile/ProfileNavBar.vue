<template>
  <div>
    <v-card
        height="400"
        width="256"
        class="mx-auto"
        flat
    >
        <v-navigation-drawer permanent>
        <v-list-item>
            <v-list-item-content>
            <v-list-item-title class="title">
                Application
            </v-list-item-title>
            <v-list-item-subtitle>
                subtext
            </v-list-item-subtitle>
            </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>

        <v-list
            dense
            nav
        >
            <v-list-item
            v-for="item in items"
            :key="item.title"
            link
            >
            <v-list-item-icon>
                <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>

            <v-list-item-content @click="navigate(item.route)">
                <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
            </v-list-item>
        </v-list>
        </v-navigation-drawer>
    </v-card>
  </div>

</template>

<script>

/* eslint-disable */

// import BaseNavBar from '../BaseNavBar'
import { mapGetters, mapActions } from "vuex";
import router from '../../router'



export default {


   components:{

    },

    props:{

    },


    methods:{
        ...mapActions(["fetchProducts"]),

        navigate(item_route_name){
            router.push({ name: item_route_name})
            // if(route != this.$route.path){
            //     router.push({ name: 'transcription_detail', params: { fileID: fileID } })
            // }
        }
    },
    computed: {
        ...mapGetters(["getProducts"]),
    },
    created(){

    },



    data () {
      return {
        items: [
          { title: 'Profile', icon: 'mdi-view-dashboard', route:'profile' },
          { title: 'Rides', icon: 'mdi-image', route: 'orders' }
        ],
        right: null,

      }
    },


}

</script>