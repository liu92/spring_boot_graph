package com.docker.demo.config;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.VertexLabel;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.graphdb.management.JanusGraphManager;

/**
 * @ClassName JanusGraphConfig
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/5/29 12:01
 **/
public class JanusGraphConfig {

    public static  void main(String[] args){
        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-cassandra-es.properties");

        //创建顶点
        Vertex v1 = graph.addVertex("USER");
        v1.property("uid", "100");
        v1.property("name", "张三");
        v1.property("age", 23);


        ////////////使用内存作为存储端
        //config.setProperty("storage.backend", "inmemory");
        //////////使用cassandra+es作为存储端
//        config.setProperty("storage.backend", "cassandrathrift");
//        config.setProperty("storage.cassandra.keyspace", "janus");
//        config.setProperty("storage.hostname", "127.0.0.1");
//        config.setProperty("index.search.backend", "elasticsearch");
//        config.setProperty("index.search.hostname", "127.0.0.1");


        Vertex v2 = graph.addVertex("PHONE");
        v2.property("phone", "13811111111");

        //创建边
        Edge e12 = v1.addEdge("USER_PHONE", v2);
        e12.property("create_time", "2018-08-08");

        graph.tx().commit();

        graph.close();
    }
}
