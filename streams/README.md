sudo update-java-alternatives --set /usr/lib/jvm/java-1.8.0-openjdk-amd64

/usr/lib/jvm/java-1.11.0-openjdk-amd64



How to run spark submit with specific version of java

The Java version would need to be set for both the Spark App Master and the Spark Executors which will be launched on YARN. Thus the spark-submit command must include two JAVA_HOME settings: spark.executorEnv.JAVA_HOME and spark.yarn.appMasterEnv.JAVA_HOME