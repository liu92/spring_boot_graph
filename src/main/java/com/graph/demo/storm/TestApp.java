package com.graph.demo.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * 本地测试
 * Stormd的Topology（由Spout + bolt连接在一起形成一个拓扑，
 * 形成有向图，定点就是计算，边是数据流。）
 * @ClassName TestApp
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/4 16:42
 **/
public class TestApp {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        //设置Spout
        builder.setSpout("spout", new CreatSpout());
        //设置creator-bolt
        builder.setBolt("creator-bolt", new CreatorBolt()).shuffleGrouping("spout");
        //设置counter-Bolt
        builder.setBolt("counter-bolt", new CounterBolt()).fieldsGrouping("creator-bolt",new Fields("call"));
        Config config = new Config();
        config.setDebug(true);

        //提交top
        if(args !=null&&args.length>0){ //有参数时，表示向集群提交作业，并把第一个参数当做topology名称
            StormSubmitter. submitTopology(args[0], config, builder.createTopology());
        } else{//没有参数时，本地提交
            //本地模式Storm
            LocalCluster localCluster=new LocalCluster();
            localCluster.submitTopology("wordcountapp" , config , builder.createTopology());
            Thread. sleep(10000);
            localCluster.shutdown();
        }


    }
}
