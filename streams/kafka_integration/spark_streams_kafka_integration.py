import findspark
findspark.init()
import pyspark as ps
from pyspark import SparkContext, SparkConf


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

def process_row(row):
    print("THE ROW LOOKS LIKE " + str(row))
    print("THE ROW VALUE IS " + str(row.value.decode('utf-8')))

    print("THE ROW VALUE IS " + str(type(row.value.decode('utf-8'))))
query = streamingDF.writeStream.foreach(lambda x: process_row(x)).start()

# topic1_stream.writeStream \
#     .foreach(s) \
#     # .format("console") \
#     .start()

spark.streams.awaitAnyTermination()