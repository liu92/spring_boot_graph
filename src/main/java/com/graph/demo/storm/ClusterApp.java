package com.graph.demo.storm;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * @ClassName ClusterApp
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/8 10:41
 **/
public class ClusterApp {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();
        //设置Spout
        builder.setSpout("spout", new CreatSpout());
        //设置creator-bolt
        builder.setBolt("creator-bolt", new CreatorBolt()).shuffleGrouping("spout");
        //设置counter-Bolt
        builder.setBolt("counter-bolt", new CounterBolt()).fieldsGrouping("creator-bolt",new Fields("call"));
        Config config = new Config();
        config.setDebug(true);
        StormSubmitter.submitTopology("mytop", config, builder.createTopology());
    }
}
