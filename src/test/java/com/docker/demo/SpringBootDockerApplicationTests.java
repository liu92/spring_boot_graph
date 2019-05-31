package com.docker.demo;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.spark.process.computer.SparkGraphComputer;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.GraphFactory;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringBootDockerApplicationTests {
	protected static final String CONF_FILE = "resouces/jgex-tinkergraph.properties";

	@Test
	public void contextLoads() {
		System.out.println("ddd");
	}

	@Test
	public void mapReduceIndex(){
		//打开JanusGraph图，其配置存储在可访问的属性配置文件中
		//JanusGraphFactory是一个类，它通过在每次访问图形时提供一个配置对象来提供对图形的访问点。
		//ConfiguredGraphFactory提供了对您之前使用ConfigurationManagementGraph为其创建配置的图形的访问点。它还提供了一个访问点来管理图形配置。
		//ConfigurationManagementGraph允许您管理图形配置。
		//JanusGraphManager是一个跟踪图形引用的内部服务器组件，前提是您的图形被配置为使用它。

		//然而，这两个图形工厂之间有一个重要的区别:
		//1、只有将服务器配置为在服务器启动时使用ConfigurationManagementGraph api，才可以使用ConfiguredGraphFactory。
		//使用ConfiguredGraphFactory的好处是:
		//1、每次打开图形时，您只需要提供一个字符串来访问图形，而不是JanusGraphFactory——它要求您指定访问图形时希望使用的后端信息。
		//2、如果配置了分布式存储后端，那么集群中的所有JanusGraph节点都可以使用您的图形配置。

		final  JanusGraph graph = JanusGraphFactory
				.open("D:\\space\\spring_boot_docker\\src\\main\\resources\\jgex-cassandra.properties");
		final JanusGraphManagement mgmt  = graph.openManagement();
	}

	@Test
	public void sparkDemo(){
		JanusGraph graph = JanusGraphFactory.open("D:\\space\\spring_boot_docker\\src\\main\\resources\\spark-conf.properties");
		graph.traversal().withComputer();

	}

	@Test
	public void searGraph(){

		Graph graph = GraphFactory.open("E:\\testspace\\spring_boot_docker\\src\\main\\resources\\jgex-cql.properties");
		GraphTraversalSource g = graph.traversal().withComputer(SparkGraphComputer.class);
		GraphTraversal<Vertex, Long> count = g.V().count();
		GraphTraversal<Edge, Long> count1 = g.E().count();

//		SparkConf sparkConf = new SparkConf().setAppName("Janusgraph").setMaster("192.168.199.115");
//		SparkContext context = new SparkContext(sparkConf);
//		JanusGraph graph = JanusGraphFactory
//				.open("E:\\testspace\\spring_boot_docker\\src\\main\\resources\\jgex-cql.properties");
//		graph.openManagement();
	}

}
