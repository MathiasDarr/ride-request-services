import findspark
findspark.init()
import pyspark as ps
from pyspark import SparkContext, SparkConf
import avro.schema
from avro.io import BinaryDecoder, DatumReader

from pyspark.sql import Row
from pyspark.sql.avro.functions import from_avro, to_avro
import json
from avro import schema, datafile, io
import io
import avro



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

def process_row(row):
    print("THE ROW LOOKS LIKE " + str(row))
    print(row.asDict().keys())


# `from_avro` requires Avro schema in JSON string format.
schema = open("schema/coordinates.avsc", "r").read()

# SCHEMA = avro.schema.Parse(json.dumps({
#     "namespace": "org.mddarr.rides.event.dto",
#     "type": "record",
#     "name": "AvroRideRequest",
#     "fields": [
#         {"name": "rideid", "type": "string"},
#         {"name": "latitude", "type": "double"},
#         {"name": "longitude", "type": "double"}
#     ]
# }))


streamingDF = spark\
  .readStream\
  .format("kafka")\
  .option("kafka.bootstrap.servers", "localhost:9092")\
  .option("subscribe", "coordinates")\
  .load()

# .select(from_avro("value", schema)) \
    # .select(from_avro("value", schema))
# .select(from_avro(output['value'], schema)) \
# avroDF = streamingDF\
#   .writeStream \
#   .foreach(process_row) \
#   .format("kafka")\
#   .option("kafka.bootstrap.servers", "localhost:9092")\
#   .option("checkpointLocation", "checkpoints") \
#   .option("topic", "coordinates-out")\
#   .start()


avroDF = streamingDF\
  .writeStream\
  .format("kafka")\
  .foreach(lambda x: print(x.value))\
  .option("checkpointLocation", "checkpoints")\
  .option("kafka.bootstrap.servers", "localhost:9092")\
  .option("topic", "topic2")\
  .start()


spark.streams.awaitAnyTermination()