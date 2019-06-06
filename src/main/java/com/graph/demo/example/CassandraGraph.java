package com.graph.demo.example;

import org.janusgraph.core.ConfiguredGraphFactory;
import org.janusgraph.core.JanusGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CassandraGraph
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/6/6 17:57
 **/
public class CassandraGraph {
    public static void main(String[] args) {
        //ConfigurationManagementGraph单例允许您创建用于打开特定图形的配置
       Map<String,Object> map = new HashMap<>();
        map.put("storage.backend", "cql");
        map.put("storage.hostname", "127.0.0.1");
        map.put("graph.graphname", "graph1");
//        ConfiguredGraphFactory.createConfiguration(new MapConfiguration(map));
        JanusGraph graph1 = ConfiguredGraphFactory.open("graph1");
    }
}
