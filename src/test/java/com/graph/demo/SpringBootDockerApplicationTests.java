package com.graph.demo;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.spark.process.computer.SparkGraphComputer;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphIndex;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.SchemaAction;
import org.janusgraph.core.schema.SchemaStatus;
import org.janusgraph.diskstorage.BackendException;
import org.janusgraph.example.GraphOfTheGodsFactory;
import org.janusgraph.graphdb.database.management.GraphIndexStatusReport;
import org.janusgraph.graphdb.database.management.ManagementSystem;
import org.janusgraph.hadoop.MapReduceIndexManagement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;

import static org.apache.tinkerpop.gremlin.structure.T.label;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringBootDockerApplicationTests {
	protected static final String CONF_FILE = "resouces/jgex-tinkergraph.properties";

	@Test
	public void contextLoads() {
		System.out.println("ddd");
	}

	@Test
	public void mapReduceIndex() throws BackendException, ExecutionException, InterruptedException {
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

		JanusGraph graph = JanusGraphFactory
				.open("D:space\\spring_boot_graphsrcmain\\esourcesjgex-cassandra.properties");
//		JanusGraph graph = JanusGraphFactory
//				.open("E:\\testspace\\spring_boot_docker\\src\\main\\resources\\jgex-cassandra.properties");
		// 遍历
		GraphTraversalSource g = graph.traversal();
		//定义属性
		JanusGraphManagement mgmt  = graph.openManagement();
		PropertyKey desc = mgmt.makePropertyKey("desc").dataType(String.class).make();
		mgmt.commit();

		//插入数据
		graph.addVertex("desc", "foo bar");
		graph.addVertex("desc", "foo baz");
		graph.tx().commit();

		// Create an index
		mgmt = graph.openManagement();

		PropertyKey desc1 = mgmt.getPropertyKey("name");
		JanusGraphIndex janusGraphIndex = mgmt.buildIndex("mixedExample", Vertex.class)
				.addKey(desc1).buildMixedIndex("search");
		mgmt.commit();
		//在定义索引之前， 回滚或提交事务
		graph.tx().rollback();
		GraphIndexStatusReport report  = ManagementSystem.awaitGraphIndexStatus(graph, "mixedExample").call();

		// 运行 janugraph-hadoop 重新构建索引
		mgmt = graph.openManagement();
		MapReduceIndexManagement mr1 = new MapReduceIndexManagement(graph);
		mr1.updateIndex(mgmt.getGraphIndex("mixedExample"), SchemaAction.REINDEX).get();

		// 开启索引
		mgmt = graph.openManagement();
		mgmt.updateIndex(mgmt.getGraphIndex("mixedExample"), SchemaAction.ENABLE_INDEX).get();
		mgmt.commit();

		mgmt = graph.openManagement();
		GraphIndexStatusReport report1 = ManagementSystem.
				awaitGraphIndexStatus(graph, "mixedExample").status(SchemaStatus.ENABLED).call();
		mgmt.rollback();
		// 排除janusgraph最后一个是查询缓存，索引重启一个实例
		graph.close();
		JanusGraphFactory
				.open("D:\\space\\spring_boot_docker\\src\\main\\resources\\jgex-cassandra.properties");
		g.V().has("desc","baz");


//		MapReduceIndexManagement mr = new MapReduceIndexManagement(graph);
//		ScanMetrics scanMetrics = mr.updateIndex(mgmt.getRelationIndex
//						(mgmt.getRelationType("battled"), "battlesByTime"),
//				SchemaAction.REINDEX).get();
//		mgmt.commit();

	}

	@Test
	public void sparkDemo(){
		JanusGraph graph = JanusGraphFactory
				.open("D:\\space\\spring_boot_graph\\src\\main\\resources\\spark-conf.properties");
		GraphTraversalSource g = graph.traversal().withComputer(SparkGraphComputer.class);
		// 3. Run some OLAP traversals
        g.V().count();
        g.E().count();

	}

	@Test
	public void searGraph(){
// 这个地址是公司电脑地址
		//通过配置文件构建图对象
		JanusGraph graph = JanusGraphFactory
				.open("D:\\space\\spring_boot_graph\\src\\main\\resources\\jgex-cql.properties");


		// 这个地址是家里电脑地址
//		JanusGraph graph = JanusGraphFactory
//				.open("E:\\testspace\\spring_boot_graph\\src\\main\\resources\\jgex-cql.properties");

//		GraphOfTheGodsFactory.load(graph);

		//Create Schema
//		JanusGraphManagement mgmt  = graph.openManagement();
//		//设置顶点标签属性, 顶点
//		VertexLabel student = mgmt.makeVertexLabel("student").make();
//		//设置边标签属性
//		EdgeLabel friends = mgmt.makeEdgeLabel("friends").make();
//		mgmt.commit();
//		//使用label student 创建一个顶点v1,并向顶点添加属性
//		JanusGraphVertex v1 = graph.addVertex(label, "student");
//	    v1.property("id", "1");
//
//		JanusGraphVertex v2 = graph.addVertex();
//	    JanusGraphVertex v3 = graph.addVertex(label, "student");
//	    v3.property("id", "2");
//	    graph.tx().commit();
//		v1.addEdge("friends", v2);
//        v1.addEdge("friends", v3);
//        graph.tx().commit();
//
//		System.out.println(graph.traversal().V());
//		System.out.println(graph.traversal().V().values("id"));
//		System.out.println(graph.traversal().E());



//
//		//插入数据
//		graph.addVertex("desc", "foo bar");
//		graph.addVertex("desc", "foo baz");
//		graph.tx().commit();





		// traversal 遍历
		GraphTraversalSource g = graph.traversal().withComputer(SparkGraphComputer.class);
//		GraphTraversal<Vertex, Long> count = g.V().count();
//		GraphTraversal<Edge, Long> count1 = g.E().count();

		GraphTraversal<Vertex, Vertex> has1 = g.V().has("student", "id");
		System.out.println(has1);
		GraphTraversal<Vertex, Vertex> out = g.V().has("friends", "id");
		System.out.println(out);
		// (Step代表每次遍历的一个步骤)
//		Step在图服务中一般指的是遍历中的计算最小单元(而非生活中常说的步子) ,
		// 更好理解的解释是步骤, 每个Step接受一个元素对象作为入参, 加工处理后返回.
		// 在一次Traversal中, 会有很多个步骤(Step), 它们就以流的方式加载, 并且产生一个延迟的加工计算链 (流式编程的显著优点之一)
//
//		而Path表示一次遍历(Traversal)中的某一条路径选择, 这个应该是最贴合我们日常说的”路径”的,
		// 任何实现的XxxPath都有两个list : 一个是这条路径经过所包含的元素,另一个记录这些元素的标签名(比如person/age/name..) ,
		// Path因为代表的是某条路径, 所以使用上比Step少很多

		// 遍历结果 [GraphStep(vertex,[]), HasStep([friends.eq(id)])]


//		GraphTraversal<Vertex, Vertex> has = g.V().has("student", "baz");
//		System.out.println(count);
//		System.out.println(count1);
//		System.out.println(has);

//		GraphTraversal<Vertex, Object> values = g.V().has("name", "hercules").values("name");
//		System.out.println(values);
//		SparkConf sparkConf = new SparkConf().setAppName("Janusgraph").setMaster("192.168.199.115");
//		SparkContext context = new SparkContext(sparkConf);
//		JanusGraph graph = JanusGraphFactory
//				.open("E:\\testspace\\spring_boot_docker\\src\\main\\resources\\jgex-cql.properties");
//		graph.openManagement();
	}


//	@Test
//	public  void VisitJanusGraph(){
//		//First configure the graph
//		JanusGraphFactory.Builder builder = JanusGraphFactory.build();
//		builder.set("storage.backend", "cassandra");
//		builder.set("storage.hostname", "192.168.199.117");
//		builder.set("storage.cql.keyspace","test");
//		builder.set("storage.port", "9042");
//		//ip address where cassandra is installed
//		//builder.set("storage.username", “cassandra”);
//		//buder.set("storage.password", “cassandra”);
//		//builder.set("storage.cassandra.keyspace", "testing");
//
//		//open a graph database
//		JanusGraph graph = builder.open();
//		//Open a transaction
//		JanusGraphTransaction tx = graph.newTransaction();
//		//Create a vertex v1 with label student, add property to the vertex
//		Vertex v1 = tx.addVertex(T.label, "student");
//		v1.property("id", 1);
//		//create a vertex v2 without label and property
//		Vertex v2 = tx.addVertex();
//		//create a vertex v3 with label student, then add property to the vertex
//		Vertex v3 = tx.addVertex(T.label, "student");
//		v3.property("id", 2);
//		tx.commit();
//
//		//Create edge between 2 vertices
//		Edge edge12 = v1.addEdge("friends", v2);
//		Edge edge13 = v1.addEdge("friends", v3);
//		//Finally commit the transaction
//		tx.commit();
//
//		System.out.println(graph.traversal().V());
//		System.out.println(graph.traversal().E());
//	}
}
