#!/bin/bash

docker cp kafka-connect-cassandra-sink-1.4.0/ connect:/usr/share/java/kafka-connect-cassandra-sink-1.4.0/
docker cp kafka-connect-cassandra-sink-1.4.0/conf/cassandra-sink.properties connect:/etc/cassandra-sink.properties