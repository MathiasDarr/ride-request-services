import findspark
findspark.init()
import pyspark as ps
from pyspark.conf import SparkConf
from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry.avro import AvroDeserializer
from confluent_kafka.serialization import SerializationContext


def getSparkInstance():
    """
    @return: Return Spark session
    """
    # java8_location = '/usr/lib/jvm/java-8-openjdk-amd64' # Set your own
    # os.environ['JAVA_HOME'] = java8_location

    conf = SparkConf().setAppName('Mnist_Spark_MLP').set('spark.executor.memory', '8g').set('spark.driver.memory',
                                                                                            '4g').setMaster('local[8]')
    spark = ps.sql.SparkSession.builder\
        .config(conf=conf)\
        .getOrCreate()

    # spark = ps.sql.SparkSession.builder \
    #     .master("local[4]") \
    #     .appName("individual") \
    #     .getOrCreate()
    return spark


def process_avro_row(row):
    print("THE ROW LOOKS LIKE " + str(row))
    print(row.value)

def process_avro_streams():

    df = spark\
      .readStream\
      .format("kafka")\
      .option("kafka.bootstrap.servers", "localhost:9092")\
      .option("subscribe", "coordinates")\
      .load()

    # 1. Decode the Avro data into a struct;
    # 2. Filter by column `favorite_color`;
    # 3. Encode the column `name` in Avro format.
    output = df\
        .select(df.value)
      # .select(from_avro("value", schema))

    query = output\
      .writeStream\
      .foreach(process_avro_row) \
      .format("kafka")\
      .option("checkpointLocation", "checkpoints")\
      .option("kafka.bootstrap.servers", "localhost:9092")\
      .option("topic", "coordinates-out")\
      .start()

    spark.streams.awaitAnyTermination()


def process_row(row):
    print("THE ROW LOOKS LIKE " + str(row))
    print(row.value)
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
    print(str(deserialized_row))
    return deserialized_row


def stream_processing():
    streamingDF = spark \
        .readStream \
        .format("kafka") \
        .option("kafka.bootstrap.servers", "localhost:9092") \
        .option("subscribe", "coordinates") \
        .load()

    outputDF = streamingDF \
        .writeStream \
        .foreach(process_row) \
        .option("checkpointLocation", "checkpoints") \
        .format("kafka") \
        .option("kafka.bootstrap.servers", "localhost:9092") \
        .option("topic", "coordinates-out") \
        .start()

    spark.streams.awaitAnyTermination()

if __name__ =='__main__':
    spark = getSparkInstance()
    process_avro_streams()