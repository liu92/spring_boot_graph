package com.docker.demo;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SQLContext;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.spark.process.computer.SparkGraphComputer;
import org.apache.tinkerpop.gremlin.spark.structure.Spark;
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

//		Graph graph = GraphFactory.open("E:\\testspace\\spring_boot_docker\\src\\main\\resources\\jgex-cql.properties");
//		GraphTraversalSource g = graph.traversal().withComputer(SparkGraphComputer.class);
//		GraphTraversal<Vertex, Long> count = g.V().count();
//		GraphTraversal<Edge, Long> count1 = g.E().count();

		SparkConf sparkConf = new SparkConf().setAppName("Janusgraph").setMaster("192.168.199.115");
		SparkContext context = new SparkContext(sparkConf);
		JanusGraph graph = JanusGraphFactory.open("E:\\testspace\\spring_boot_docker\\src\\main\\resources\\jgex-cql.properties");
		graph.openManagement();
	}

}
