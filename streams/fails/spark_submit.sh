#!/bin/bash
$SPARK_HOME/bin/spark-submit --jars /usr/local/spark/spark-2.4.7-bin-hadoop2.7/jars/spark-streaming-kafka-0-10_2.12-2.4.7.jar  kafka_spark_streams.py


#/usr/local/spark/spark-2.4.7-bin-hadoop2.7/jars/spark-streaming_2.11-2.4.7.jar
#
#
#conda create -n sparkenv python=3.7