import findspark
findspark.init()
import pyspark as ps
from pyspark import SparkContext, SparkConf
import avro.schema
from avro.io import BinaryDecoder, DatumReader
import io
from pyspark.sql import Row
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

schema = '''
{"namespace" : "org.mddarr.rides.event.dto",
 "type": "record",
 "name": "AvroRideCoordinate",
 "fields": [
     {"name": "rideid", "type": "string"},
     {"name": "latitude", "type": "double"},
     {"name": "longitude", "type": "double"}
 ]
}
'''

def decode(msg_value):
    message_bytes = io.BytesIO(msg_value)
    message_bytes.seek(5)
    decoder = BinaryDecoder(message_bytes)
    event_dict = reader.read(decoder)
    return event_dict

def process_row(row):
    row_dictionary = row.asDict()
    print("THE TYPE OF THE ROW IS ")
    print(str(list(row.asDict().keys())))

    # byte_row = bytearray(row_dictionary['value'])
    print(row_dictionary['value'])
    decoded_message = decode(row_dictionary['value'])
    # print(decoded_message)
    # print("THE ROW LOOKS LIKE " + str(row))
    # print("THE ROW VALUE IS " + str(row.value.decode('utf-8')))
    #
    # print("THE ROW VALUE IS " + str(type(row.value.decode('utf-8'))))


reader = avro.io.DatumReader(schema)

jsonFormatSchema = '''{"type":"record","name":"topLevelRecord","fields":
        [{"name":"avro","type":[{"type":"record","name":"value","namespace":"topLevelRecord",
        "fields":[{"name":"age","type":["long","null"]},
        {"name":"name","type":["string","null"]}]},"null"]}]}'''
data = [(1, Row(name='Alice', age=2))]
df = spark.createDataFrame(data, ("key", "value"))
avroDf = df.select(to_avro(df.value).alias("avro"))
avroDf.select(from_avro(avroDf.avro, jsonFormatSchema).alias("value"))



# avroStreamingDF = spark \
#   .readStream \
#   .format("kafka") \
#   .option("kafka.bootstrap.servers", "localhost:9092") \
#   .option("subscribe", "coordinates") \
#   .load()
# query = avroStreamingDF \
#     .select(from_avro("value", schema).alias("value")).collect()


# query = streamingDF.writeStream.foreach(lambda x: process_row(x)).start()

# topic1_stream.writeStream \
#     .foreach(s) \
#     # .format("console") \
#     .start()
#
# spark.streams.awaitAnyTermination()