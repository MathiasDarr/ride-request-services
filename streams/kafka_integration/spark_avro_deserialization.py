import findspark
findspark.init()
import pyspark as ps

from avro.io import BinaryDecoder, DatumReader
from pyspark.sql import Row

import json
from avro import schema, datafile, io
import io
import avro


from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry.avro import AvroDeserializer
from confluent_kafka.serialization import SerializationContext

schemaRegistryClient = SchemaRegistryClient({"url":"http://localhost:8081"})

schema = '''
{
"namespace": "org.mddarr.rides.event.dto",
 "type": "record",
 "name": "AvroRideCoordinate",
 "fields": [
     {"name": "rideid", "type": "string"},
     {"name": "latitude", "type": "double"},
     {"name": "longitude", "type": "double"}
 ]
}
'''

avroDeserializer = AvroDeserializer(schema, schemaRegistryClient)
serializationContext = SerializationContext("coordinates", "value")


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




# df = spark\
#   .readStream\
#   .format("kafka")\
#   .option("kafka.bootstrap.servers", "localhost:9092")\
#   .option("subscribe", "coordinates")\
#   .load()
#
# # 1. Decode the Avro data into a struct;
# # 2. Filter by column `favorite_color`;
# # 3. Encode the column `name` in Avro format.
# output = df\
#     .select(df.value)
#   # .select(from_avro("value", schema))
#
# query = output\
#   .writeStream\
#   .format("kafka")\
#   .option("checkpointLocation", "checkpoints")\
#   .option("kafka.bootstrap.servers", "localhost:9092")\
#   .option("topic", "coordinates-out")\
#   .start()

# spark.streams.awaitAnyTermination()


def process_row(row):
    print("THE ROW LOOKS LIKE " + str(row))
    print(row.value)
    deserialized_row = avroDeserializer(row.value, serializationContext)
    # print(str(deserialized_row))

streamingDF = spark\
  .readStream\
  .format("kafka")\
  .option("kafka.bootstrap.servers", "localhost:9092")\
  .option("subscribe", "coordinates")\
  .load()

# .select(from_avro("value", schema))
#   .format("kafka")\
# .option("kafka.bootstrap.servers", "localhost:9092") \
#     .option("topic", "coordinates-out") \
#  \
outputDF = streamingDF\
  .writeStream\
  .foreach(process_row)\
  .option("checkpointLocation", "checkpoints")\
  .start()
#
#
spark.streams.awaitAnyTermination()