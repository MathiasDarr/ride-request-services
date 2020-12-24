# RideShare MicroServices #
This repository contains microservices implemented using Spring, frontend developed using Vue JS, 
* Spring boot, Spring data, Spring amqp, Spring security
* Front end developed using Vue JS
* Python scripts for seeding postgres & cassandra databases


## This repository contains ##

* Spring MicroServices
    * authenication_service
        * Spring application leveraging Spring security 
        * Return & Validate JWTs to autheticated users
        
    * rideservice,
        * Spring Boot application exposes endpoints to allow authenticated users to request rides
        * Ride requests are inserted into Cassandra database, and and added to
        * Unit & Integration test suite 
    * relationalrideservice,
        * Same service as described above but instead uses a postgres backend 
        
    * driver-dispatch-service 
        * Spring service consumes rabbitmq messages 
        * Assigns driver in driver pool to ride and saves a Ride object to cassandra
    * locationtracker 
        * Spring boot service connects with clients with WebSockets and STOMP protocol 
        * Write time series location data received over socket to Cassandra w/ ride ID that was generated from the driver-dispatch-service
        
* Vue JS front end
    * Technologies Used
        * Vuex state store
        * sockjs-client for socket communication
        * webstomp-client Stomp client
    * communicates with the locationtracker service w/ 

* Python data seeding scripts
    * interact with Cassandra using datastax cassandra-driver
    * interact with postgresql using psycopg2
    
    
    
## Replication Steps  ##
#### Seed database ####
* Run cassandra, postgres & rabbitmq in docker
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



//    "schema.whitelist": "snowpack",



