package graphs

import org.apache.spark.sql.SparkSession

object CassandraWriteReadDemo {
  def main(args: Array[String]): Unit = {
     val  spark = SparkSession.builder()
        .appName("cassandraSearch")
        .master("local[*]")
        // spark.cassandra.connection.host 可以写入Cassandra多个节点，形成高可用
        .config("spark.cassandra.connection.host","192.168.199.117")
        .config("spark.sql.shuffle.partitions",20)
        .config("spark.cassandra.connection.port", "9042")
        .getOrCreate()
     //从Cassandra读取数据
    val dfFormCassandra = spark.read
      .format("org.apache.spark.sql.cassandra")
      .option("keyspace", "jgex").option("table", "edgestore").load()
     //显示五行Cassandra数据
     dfFormCassandra.show(5,false)
     spark.stop()

  }
}
