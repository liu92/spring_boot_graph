package com.graph.demo.example;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphTransaction;

/**
 * @ClassName: JanusGrapConnectionCassandra
 * @Description:
 * @Author: lin
 * @Date: 2019/6/2 17:31
 * History:
 * @<version> 1.0
 */
public class JanusGrapConnectionCassandra {
    public static void main(String[] args) {
        // 连接单机 Cassandra 成功了， 注意需要在虚拟机中开放端口，不然连接不上
        //创建Cassandra JanusGraph
        JanusGraph graph = JanusGraphFactory
                .build()
                .set("storage.backend","cql")
                .set("storage.hostname","192.168.199.117")
                .set("storage.port","9042")
                .open();

        JanusGraphTransaction tx = graph.newTransaction();

        Vertex v1 = tx.addVertex(T.label, "student");
        v1.property("id",1);

        Vertex v2 =tx.addVertex();
        Vertex v3 = tx.addVertex(T.label, "student");
        v3.property("id", 2);
        tx.commit();
        Edge edge12 = v1.addEdge("friends", v2);
        Edge edge13 = v1.addEdge("friends", v3);
        //Finally commit the transaction
        tx.commit();
//
//        System.out.println(graph.traversal().V());
//        System.out.println(graph.traversal().E());
    }
}
