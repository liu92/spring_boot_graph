package com.graph.demo;

import org.apache.commons.configuration.MapConfiguration;
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

import java.util.HashMap;
import java.util.Map;
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

//		如果用于打开图形的配置指定graph.graphname但未指定后端的存储目录，
//       tablename或keyspacename，则相关参数将自动设置为值graph.graphname。
//       但是，如果您提供其中一个参数，则该值始终优先。如果您不提供，则默认为配置选项的默认值

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
//      v1.addEdge("friends", v3);
//      graph.tx().commit();
//
//		System.out.println(graph.traversal().V());
//		System.out.println(graph.traversal().V().values("id"));
//		System.out.println(graph.traversal().E());


// -----------------------------------------------------------------
		// 下面 的方式创建 有错
//		Map<String,String> map = new HashMap<>();
//		map.put("storage.backend", "cql");
//		map.put("storage.hostname", "192.168.199.117");
//		ConfiguredGraphFactory.createTemplateConfiguration(new MapConfiguration(map));
//
//		JanusGraph graph1 = ConfiguredGraphFactory.create("graph1");
//		JanusGraphManagement mgmt  = graph1.openManagement();
////		//设置顶点标签属性, 顶点
//		VertexLabel student = mgmt.makeVertexLabel("student").make();
//		//设置边标签属性
//		EdgeLabel friends = mgmt.makeEdgeLabel("friends").make();
//		mgmt.commit();
//		//使用label student 创建一个顶点v1,并向顶点添加属性
//		JanusGraphVertex v1 = graph1.addVertex(label, "student");
//	    v1.property("id", "1");
//
//		JanusGraphVertex v2 = graph1.addVertex();
//	    JanusGraphVertex v3 = graph1.addVertex(label, "student");
//	    v3.property("id", "2");
//		graph1.tx().commit();
//		v1.addEdge("friends", v2);
//        v1.addEdge("friends", v3);
//		graph1.tx().commit();
//
//		System.out.println(graph1.traversal().V());
//		System.out.println(graph1.traversal().V().values("id"));
//		System.out.println(graph1.traversal().E());

// ----------------------------------------------------------------------


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



// 官网 示例 添加，这个指定了keyspacename
//		map = new HashMap();
//		map.put("storage.backend", "cql");
//		map.put("storage.hostname", "127.0.0.1");
//		ConfiguredGraphFactory.createTemplateConfiguration(new
//				MapConfiguration(map));
//
//		g1 = ConfiguredGraphFactory.create("graph1"); //keyspace === graph1
//		g2 = ConfiguredGraphFactory.create("graph2"); //keyspace === graph2
//		g3 = ConfiguredGraphFactory.create("graph3"); //keyspace === graph3
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



	@Test
	public void build(){
		//使用配置文件来连接到相应服务器上的存储数据库
		JanusGraph graph = JanusGraphFactory
				.open("D:\\space\\spring_boot_graph\\src\\main\\resources\\jgex-cql.properties");
		//Create Schema
		JanusGraphManagement mgmt  = graph.openManagement();

		//设置顶点标签属性, 顶点 银行卡
		//创建了一个名字为name的属性，并设置值类型为LONG，且只能保存一个值
		//使用cardinality(Cardinality)定义与在任何给定的顶点的键关联的值允许的基数。


		//SINGLE：对于此类密钥，每个元素最多允许一个值。换句话说，键→值映射对于图中的所有元素都是唯一的。
		// 属性键birthDate是具有SINGLE基数的示例，因为每个人只有一个出生日期。
		//创建了一个名字为birthDate的属性，并设置值类型为LONG，且只能保存一个值
		PropertyKey birthDate  =  mgmt.makePropertyKey("birthDate").dataType(Long.class).cardinality(Cardinality.SINGLE).make();
		//SET：允许多个值，但每个元素没有重复值用于此类键。换句话说，密钥与一组值相关联。
		// name如果我们想要捕获个人的所有姓名（包括昵称，婚前姓名等），则属性键具有SET基数。
        //创建了一个名字为name的属性，并设置值类型为String，且可以保存不能重复的多个值
		final PropertyKey name = mgmt.makePropertyKey("name").dataType(String.class).cardinality(Cardinality.SET).make();
		//LIST：允许每个元素的任意数量的值用于此类键。换句话说，密钥与允许重复值的值列表相关联。
		// 假设我们将传感器建模为图形中的顶点，则属性键sensorReading是具有LIST基数的示例，以允许记录大量（可能重复的）传感器读数。
		//创建了一个名字为sensorReading的属性，并设置值类型为Double，且可以保存可以重复的多个值
		PropertyKey sensorReading = mgmt.makePropertyKey("sensorReading").dataType(Double.class).cardinality(Cardinality.LIST).make();

		// t1_crm_customer_open_card、 客户开卡表
		// t1_crm_customer_bill、客户开卡表
		// t1_crm_stop_elec、停止电催表
		// t1_crm_commission_case、委案表
		// t1_crm_ovedue、逾期表
		// t1_crm_natural_payoff、自然结清表
		// t1_crm_legal_support、法务支持
		// t1_crm_tm_loan、腾铭贷款合同
		// t1_crm_elec_payment_register 电催回款登记

		//设置银行顶点和属性
		final VertexLabel bankCard = mgmt.makeVertexLabel("bankCard").make();
		//设置银行卡属性 卡号
		final PropertyKey bankCardTye = mgmt.makePropertyKey("bankCardTye").dataType(String.class).cardinality(Cardinality.SINGLE).make();
		//设置银行卡属性 编码
		final PropertyKey bankCode = mgmt.makePropertyKey("bankCode").dataType(String.class).cardinality(Cardinality.SINGLE).make();
        //将顶点和属性 加入到
		final VertexLabel vertexLabel = mgmt.addProperties(bankCard, bankCardTye, bankCode);
		mgmt.commit();

		JanusGraphTransaction tx = graph.newTransaction();

		Vertex saturn = tx.addVertex(T.label, "bankCard", "name", "saturn", "age", 10000);

	}
}

