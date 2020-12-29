import findspark
findspark.init()
import pyspark as ps
from pyspark import SparkContext, SparkConf
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils
import os
import json

def getSparkInstance():
    """
    @return: Return Spark session
    """
    # java8_location = '/usr/lib/jvm/java-8-openjdk-amd64' # Set your own
    # os.environ['JAVA_HOME'] = java8_location

    spark = ps.sql.SparkSession.builder \
        .master("local[4]") \
        .appName("individual") \
        .getOrCreate()
    return spark

spark = getSparkInstance()


# df = spark \
#   .read \
#   .format("kafka") \
#   .option("kafka.bootstrap.servers", "localhost:9092") \
#   .option("subscribe", "uppercaseecho-out-0") \
#   .load()
# df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
# Subscribe to 1 topic
# df = spark \
#   .readStream \
#   .format("kafka") \
#   .option("kafka.bootstrap.servers", "host1:port1,host2:port2") \
#   .option("subscribe", "uppercaseecho-out-0") \
#   .load()
# df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")


df = spark \
  .readStream \
  .format("kafka") \
  .option("kafka.bootstrap.servers", "host1:localhost:9092") \
  .option("subscribe", "topic1") \
  .load()
df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")


if (__name__ == "__main__"):
    sc = SparkContext(appName="Kafa Spark Demo")
    sc.setLogLevel("WARN")
    ssc = StreamingContext(sc, 60)
