<template>
  <v-container>
    <div>
      <v-form>
        <v-container fluid>
            <v-row>
            <v-col cols="12" sm="6">
              <v-text-field label="Destination" value="" v-model="destination"></v-text-field>
            </v-col>
            <v-col class="d-flex" cols="12" offset="2" sm="3">
              <v-select :items="riders" label="Number of riders" v-model="nriders"></v-select>
            </v-col>
            
          </v-row>
        </v-container>  
      </v-form>
    </div>

   <SocketConnection @postEvent="onClickButton"/>
    
    <!-- <v-layout row>
      <v-flex md2>
      </v-flex> 
      <v-flex md10>
        <div class="title font-weight-medium">
          <v-layout row>
            <v-flex md8>
              <SocketConnection />
            </v-flex>
            <v-flex md4>
            </v-flex>
          </v-layout>
        </div>
      </v-flex>
    </v-layout>  -->



  <template>
    <v-data-table
      :headers="headers"
      :items="getRideRequests"
      :items-per-page="5"
      class="elevation-1"
    ></v-data-table>
  </template>


  </v-container>
</template>

<script>
/* eslint-disable */

import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import SocketConnection from './SocketConnection'
import { mapGetters, mapActions } from "vuex";

export default {




  created(){
    console.log("The ride requests " +this.getRideRequests )
  },
  components:{
    SocketConnection,
  },
  computed:{
    ...mapGetters(["getRideRequests"])

  },



  methods: {
    ...mapActions(["addRequest"]),
    // send() {
    //   console.log("Send message:" + this.send_message);
    //   if (this.stompClient && this.stompClient.connected) {
    //     const msg = { name: this.send_message };
    //     console.log(JSON.stringify(msg));
    //     this.stompClient.send("/app/hello", JSON.stringify(msg), {});
    //   }
    // },
    // connect() {
    //   this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
    //   this.stompClient = Stomp.over(this.socket);
    //   this.stompClient.connect(
    //     {},
    //     frame => {
    //       this.connected = true;
    //       console.log(frame);
    //       this.stompClient.subscribe("/topic/greetings", tick => {
    //         console.log(tick);
    //         this.received_messages.push(JSON.parse(tick.body).content);
    //       });
    //     },
    //     error => {
    //       console.log(error);
    //       this.connected = false;
    //     }
    //   );
    // },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    },

    onClickButton(value){
      console.log(value)
      console.log(this.category)
      this.$emit('post_ride_request',{'userid': 'dakobedbard', 'destination':'24th Ave', 'nriders':2})
    },


  },
  mounted() {
    // this.connect();
  },


  data() {
      return {

      destination:null,
      nriders:1,
      riders: [1,2,3,4,5,6],

        received_messages: [],
        send_message: null,
        connected: false,
                headers: [
          {
            text: 'rideid',
            align: 'start',
            sortable: false,
            value: 'rideid',
          },
          { text: 'driver name', value: 'driver_name' },
          { text: 'user name', value: 'user_name' },
          { text: 'riders', value: 'riders' },
          { text: 'destination', value: 'destination' },
        ],
        ride_reqeusts: [
          {
            rideid: 'ride1',
            driver_name: "charles",
            user_name: "erik",
            riders: 2,
            destination: "24th Ave",
          },
        ]
      };

    },


}
</script>


