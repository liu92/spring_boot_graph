package com.graph.demo.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * 通话记录计数器Bolt,处理上一个Blot输出的数据
 * @ClassName CounterBolt
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/4 16:35
 **/
public class CounterBolt implements IRichBolt {
    private OutputCollector collector;
    private Map<String,Integer> counterMap;

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
      this.counterMap = new HashMap<>();
      this.collector = outputCollector;
    }

    /**
     * 处理数据
     * @param tuple
     */
    @Override
    public void execute(Tuple tuple) {
       String call = tuple.getString(0);
       Integer duration = tuple.getInteger(1);
       if(!counterMap.containsKey(call)){
           counterMap.put(call,1);
       }else{
           Integer count = counterMap.get(call)+1;
           counterMap.put(call,count);
       }

       collector.ack(tuple);
    }

    /**
     * Storm停掉之后运行
     */
    @Override
    public void cleanup() {
         for (Map.Entry<String,Integer> entry : counterMap.entrySet()){
             System.out.println("============>"+entry.getKey()+":"+entry.getValue());
         }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("call"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
