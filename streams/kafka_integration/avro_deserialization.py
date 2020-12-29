import findspark
findspark.init()
import pyspark as ps
from pyspark import SparkContext, SparkConf
from pyspark.sql.avro.functions import from_avro, to_avro
from pyspark.sql.functions import col, struct



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


# jsonFormatSchema = open("schema/ride_request.avsc", "r").read()



avro_type_struct = """
{
  "type": "record",
  "name": "struct",
  "fields": [
    {"name": "user_id", "type": "string"},
    {"name": "riders", "type": "integer"}
  ]
}"""


df = spark.range(10).select(struct(
    col("id"),
    col("id").cast("string").alias("id2")
).alias("struct"))
avro_struct_df = df.select(to_avro(col("struct")).alias("avro"))
avro_struct_df.show(3)



# streamingDF = spark \
#   .readStream \
#   .format("kafka") \
#   .option("kafka.bootstrap.servers", "localhost:9092") \
#   .option("subscribe", "ride-requests") \
#   .load() \
#   .select(from_avro("key", jsonFormatSchema).alias("requests"))
#
# streamingDF \
#     .writeStream \
#     .foreach(lambda x: process_row(x)) \
#     .start()
#
# spark.streams.awaitAnyTermination()

# output = streamingDF \
#     .select(from_avro("key", jsonFormatSchema).alias("requests")) \
#     .writeStream \

    # .select(to_avro("requests", jsonFormatSchema))\



# output.readStream.foreach(lambda x: process_row(x))\
#     .start()


# query = output \
#   .writeStream \
#   .format("kafka") \
# .option("checkpointLocation", "checkpoints") \
#   .option("kafka.bootstrap.servers", "localhost:9092") \
#   .option("topic", "processed-requests") \
#   .start()



# query = output.writeStream \
#         .format("kafka")



# output = streamingDF\
#     .select(from_avro("value", jsonFormatSchema).alias("requests")) \
#     .writeStream \
#     .format("console") \
#     .start()
# spark.streams.awaitAnyTermination()

#
#
# df = spark\
#   .readStream\
#   .format("kafka")\
#   .option("kafka.bootstrap.servers", "host1:port1,host2:port2")\
#   .option("subscribe", "topic1")\
#   .load()
#
# # 1. Decode the Avro data into a struct;
# # 2. Filter by column `favorite_color`;
# # 3. Encode the column `name` in Avro format.
