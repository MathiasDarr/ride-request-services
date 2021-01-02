# RideShare MicroServices #
This repository contains microservices implemented using Spring, frontend developed using Vue JS, 
* Spring boot, Spring data, Spring amqp, Spring security
* Front end developed using Vue JS
* Python scripts for seeding postgres & cassandra databases


## This repository contains ##
* [Data Model](data_model/README.md)





* Spring MicroServices

* [](data_model/README.md)
    * Ride Request Service
        * exposes REST endpoint for authenticated users to request ride
        * Uses kafka producer API to post ride request message to kafka
    * Ride Coordinate Service
        * communicates with clients via web sockets 
        * receives timestamps & coordinates from client, producing to kafka with avro serializaiton
    * Dispatch Service
        * stream processing service leveraging kafka cloud streams API with Kafka binder
        * Reads from drivers topic & ride requests topic
    * Rides Query Service
        * exposes REST endpoints for querying the rides


## Vue JS front end ##
  * Vuex state store
  * sockjs-client for socket communication
  * webstomp-client Stomp client
  * communicates with the locationtracker service w/ 

* Python data seeding scripts
    * interact with Cassandra using datastax cassandra-driver
    * interact with postgresql using psycopg2
    
   
## Replication Steps  ##
#### Seed database ####
* Run cassandra, postgres, kafka, zookeeper, schema registry & connect
    docker-compose up 
* populate cassandra & postgres databases ()
    * cd data_model
    * Install psycopg2 & cassandra-driver
        * pip install cassandra-driver
        * pip install psycopg2
    * python3 seed_cassandra_data.py


#### Run microservices #####
* cd microservices
* mvn clean package
* run authentication_service
    java -jar authentication_service/target/authentication_service-0.0.1-SNAPSHOT.jar
* run rideservice
    * java -jar rideservice/target/rideservice-0.0.1-SNAPSHOT.jar
* run driver-dispatch-service
    * java -jar driver-dispatch-service/target/driver-dispatch-service-0.0.1-SNAPSHOT.jar
* run location tracker
   java -jar 

