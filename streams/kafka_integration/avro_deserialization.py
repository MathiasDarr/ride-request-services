import findspark
findspark.init()
import pyspark as ps
from pyspark import SparkContext, SparkConf
from pyspark.sql.avro.functions import from_avro, to_avro


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

streamingDF = spark \
  .readStream \
  .format("kafka") \
  .option("kafka.bootstrap.servers", "localhost:9092") \
  .option("subscribe", "topic1") \
  .load()

jsonFormatSchema = open("schema/ride_request.avsc", "r").read()

def process_row(row):
    print("THE ROW LOOKS LIKE " + str(row))
    # print("THE ROW VALUE IS " + str(row.value.decode('utf-8')))
    #
    # print("THE ROW VALUE IS " + str(type(row.value.decode('utf-8'))))

output = streamingDF \
    .select(from_avro("key", jsonFormatSchema).alias("requests")) \
    .writeStream \
    .foreach(lambda x: process_row(x)) \
    .start()
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



spark.streams.awaitAnyTermination()



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
