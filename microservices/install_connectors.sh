#!/bin/bash
docker exec connect confluent-hub install  debezium/debezium-connector-postgresql:0.9.4 --no-prompt
docker exec connect confluent-hub install confluentinc/kafka-connect-cassandra:latest --no-prompt
docker restart connect
sleep 15
#bash post.sh connectors/cassandra_coordinates_sink_connector.json
