import org.apache.spark.{SparkConf, SparkContext}

object ConnectionTest {
  def main(args: Array[String]): Unit = {
    val  config = new SparkConf().setMaster("spark://192.168.199.117:7070").setAppName("ConnectionTest");
    val  sc = new SparkContext(config);
    sc.addJar("D:\\space\\spring_boot_docker\\src\\main\\scala\\ConnectionTest.jar");
//    val count = sc.parallelize(1 to 4).filter{_ =>
//      val x = math.random()
//      val y = math.random()
//      x*x + y*y < 1
//    }.count()
//    println(s"Pi is roughly ${4.0 * count / 4}")
    sc.stop()
  }

}
