#!/bin/bash
docker exec connect confluent-hub install  debezium/debezium-connector-postgresql:0.9.4 --no-prompt
docker restart connect
sleep 30
bash scripts/kafka/post.sh connectors/debezium_source_connector.json
bash scripts/kafka/post.sh connectors/elasticsearch_sink_connector.json
