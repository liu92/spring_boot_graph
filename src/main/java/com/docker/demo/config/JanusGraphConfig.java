package com.docker.demo.config;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;

/**
 * @ClassName JanusGraphConfig
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/5/29 12:01
 **/
public class JanusGraphConfig {

    public void janusGraphBuild(){
        JanusGraph janusGraph = JanusGraphFactory.build().set("","").set("","").open();
    }
}
