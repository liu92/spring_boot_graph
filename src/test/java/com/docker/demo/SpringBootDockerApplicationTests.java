package com.docker.demo;

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

	public void sparkDemo(){
		JanusGraph graph = JanusGraphFactory.open("D:\\space\\spring_boot_docker\\src\\main\\resources\\spark-conf.properties");
		graph.traversal().withComputer();
	}

}
