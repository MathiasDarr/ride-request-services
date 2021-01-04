<template>
  <v-container>
    <v-card flat>
      
      <v-row>
        <v-col cols="3" sm="3">
          <v-btn color="primary" v-on:click="await_connection()">
            Connect
          </v-btn>
        </v-col>

        <v-col cols="3" sm="3">
          <v-btn color="primary" v-on:click="send()">
            Send
          </v-btn>
        </v-col>

        <v-col class="d-flex" cols="3"  sm="3">
          <v-btn color="primary" v-on:click="connect()">
            Disconnect
          </v-btn>
        </v-col>
      </v-row>

    </v-card>


        <v-card  tile flat>
          <div>
            <div id="main-content" class="container"
                  >
                    <div class="row">
                      <div class="col-md-12">
                        <table id="conversation" class="table table-striped" >
                          <thead>
                            <tr>
                              <th>Ride Requests</th>
                            </tr>
                          </thead>
                          
                          <tbody>
                            <tr v-for="item in received_ride_requests" :key="item.rideid">
                              <td>{{ item }}</td>
                            </tr>
                          </tbody>

                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </v-card>


  </v-container>
</template>

<script>
/* eslint-disable */

import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import RideRequest from './RideRequest'
import { mapGetters, mapActions } from "vuex";

import axios from 'axios';

export default {

  methods: {

    ...mapActions(["addRequest"]),

    send() {
      console.log("Send message:" + this.send_message);
      if (this.stompClient && this.stompClient.connected) {  
        var request = {userid:"charles",riders:2,destination:"church",city: "Seattle" }
        var request_body = JSON.stringify(request)
        this.stompClient.send("/app/rides/requests/post", request_body, {});
      }
    },

    send_ride_request(){

    },




    async establish_connection(){
            try{
                //var url = window.__runtime_configuration.apiEndpoint + '/categories'
                var url ='http://localhost:8080/rides/requests'
                const response = await axios.put(url, {userid:'jerryjones', riders:2, destination:"San Juan", city:"San Fransansico"})            
                
                console.log(response)                


                // var response_articles = JSON.parse(response.data.body)
                // // this.setArticles(response_articles.articles)
                // console.log(response_articles.articles)
                // this.articles = response_articles.articles
            }catch(err){
                console.log(err)
            }
        },

    async await_connection(){
        await this.establish_connection()
          
    },






    // establish_connection(){
    //   axios.put()
    // },


    ride_matching_socket_connect(){
      this.socket = new SockJS("http://localhost:8080/ride-request-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          console.log(frame);
          this.stompClient.subscribe("/topic/rides/requests/alert", tick => {
          var request = JSON.parse(tick.body)
          console.log(request);
          console.log(request.userid)
            this.received_ride_requests.push(JSON.parse(tick.body));
          });
        },
        error => {
          console.log(error);
          this.connected = false;
        }
      );
    },


    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    },

    postRequest(){
      var request = {requestid:'request1', userid:'charles', riders:3}
      this.addRequest()
    },




  },

    data() {
    return {
      received_ride_requests: [],
      ride_requested: null,
      connected: false,

    }
  },
    
}
</script>

