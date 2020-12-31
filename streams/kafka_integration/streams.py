import findspark
findspark.init()
import pyspark as ps
from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry.avro import AvroDeserializer
from confluent_kafka.serialization import SerializationContext


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
    # print("THE ROW LOOKS LIKE " + str(row))
    # print(row.asDict().keys())
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
    schemaRegistryClient = SchemaRegistryClient({"url": "http://localhost:8081"})
    avroDeserializer = AvroDeserializer(schema, schemaRegistryClient)
    serializationContext = SerializationContext("coordinates", schema)
    deserialized_row = avroDeserializer(row.value, serializationContext)
    print("DESERIALIZED ROW")
    print(str(deserialized_row))


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
  .foreach(process_row)\
  .option("checkpointLocation", "checkpoints")\
  .option("kafka.bootstrap.servers", "localhost:9092")\
  .option("topic", "topic2")\
  .start()


spark.streams.awaitAnyTermination()