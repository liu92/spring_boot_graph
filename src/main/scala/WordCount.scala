import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {

    val input = "hdfs://192.168.199.117:50070/hadoop/dfs/name"
    val output =""

    val appName = "word count"
    val master = "spark://192.168.199.117:7077"
    val jars =Array("D:\\space\\spring_boot_docker\\src\\main\\scala\\WordCount.jar")

    val conf = new SparkConf().setAppName(appName).setMaster(master).setJars(jars)
      .set("spark.eventLog.enabled","true")
      .set("spark.eventLog.dir","hdfs://node10:8020/eventLog")

    val sc = new SparkContext(conf)

    val line = sc.textFile(input)

    line.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).collect().foreach(println)

    sc.stop()
  }


}
