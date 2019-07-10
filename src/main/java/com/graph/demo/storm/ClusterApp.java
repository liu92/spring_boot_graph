package com.graph.demo.storm;

import org.apache.hadoop.mapreduce.task.reduce.Shuffle;
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


    /*
      Grouping策略介绍
       storm里面有6种类型的stream grouping:

        1.Shuffle Grouping: 随机分组， 随机派发stream里面的tuple， 保证每个bolt接收到的tuple数目相同。轮询，平均分配。

        2. Fields Grouping：按字段分组， 比如按userid来分组， 具有同样userid的tuple会被分到相同的Bolts， 而不同的userid则会被分配到不同的Bolts。

        3. All Grouping： 广播发送， 对于每一个tuple， 所有的Bolts都会收到。

        4. Global Grouping: 全局分组， 这个tuple被分配到storm中的一个bolt的其中一个task。再具体一点就是分配给id值最低的那个task。

        5. Non Grouping: 不分组， 这个分组的意思是说stream不关心到底谁会收到它的tuple。目前这种分组和Shuffle grouping是一样的效果，不平均分配。

        6. Direct Grouping: 直接分组， 这是一种比较特别的分组方法，
        用这种分组意味着消息的发送者举鼎由消息接收者的哪个task处理这个消息。
        只有被声明为Direct Stream的消息流可以声明这种分组方法。而且这种消息tuple必须使用emitDirect方法来发射。
        消息处理者可以通过TopologyContext来或者处理它的消息的taskid(OutputCollector.emit方法也会返回taskid)
      */
}
