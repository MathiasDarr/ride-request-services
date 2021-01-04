<template>
  <v-container>
    <v-card flat>
      
      <v-row>
        <v-col cols="3" sm="3">
          <v-btn color="primary" v-on:click="ride_matching_socket_connect()">
            Connect
          </v-btn>
        </v-col>
        <v-col class="d-flex" cols="3"  sm="3">
          <v-btn color="primary" v-on:click="connect()">
            Disconnect
          </v-btn>
        </v-col>
      </v-row>

    </v-card>

      <div class="row">
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="connect">WebSocket connection:</label>
              
              <button
                id="connect"
                class="btn btn-default"
                type="submit"
                :disabled="connected == true"
                @click.prevent="connect">Connect</button>
              <button
                id="disconnect"
                class="btn btn-default"
                type="submit"
                :disabled="connected == false"
                @click.prevent="disconnect"
              >Disconnect
              </button>
            </div>
          </form>
        </div>

        
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="name">What is your name?</label>
              <input
                type="text"
                id="name"
                class="form-control"
                v-model="ride_requested"
                placeholder="Your name here..."
              >
            </div>
            <button
              id="send"
              class="btn btn-default"
              type="submit"
              @click.prevent="send"
            >Send</button>
          </form>
        </div>
      </div>





              <v-card  tile flat>
                <div>
                  <div
                    id="main-content"
                    class="container"
                  >
                    <div class="row">
                      <div class="col-md-12">
                        <table
                          id="conversation"
                          class="table table-striped"
                        >
                          <thead>
                            <tr>
                              <th>Greetings</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr
                              v-for="item in received_messages"
                              :key="item"
                            >
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
export default {
     data() {
    return {
      received_messages: [],
      ride_requested: null,
      connected: false
    };
  },
  methods: {
    send() {
      console.log("Send message:" + this.send_message);
      if (this.stompClient && this.stompClient.connected) {
        const coordinates = {lat:12.1, lng:39.1}
        this.stompClient.send("/app/coordinates", JSON.stringify(coordinates), {});
      }
    },

    ride_matching_socket_connect(){
      this.socket = new SockJS("http://localhost:8080/ride-request-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          console.log(frame);
          this.stompClient.subscribe("/topic/rides", tick => {
            console.log(tick);
            // this.received_messages.push(JSON.parse(tick.body).content);
          });
        },
        error => {
          console.log(error);
          this.connected = false;
        }
      );
    },

    connect() {
      this.socket = new SockJS("http://localhost:8080/location-tracker-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          console.log(frame);
          this.stompClient.subscribe("/topic/coordinates", tick => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body).content);
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
    }
  },
    
}
</script>

