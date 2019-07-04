package com.graph.demo.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 创建调用log日志的Bolt,用于处理Spout产生的数据
 * @ClassName CreatorBolt
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/4 16:30
 **/
public class CreatorBolt implements IRichBolt {
    private OutputCollector collector;

    @Override
    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
      this.collector = outputCollector;
    }

    /**
     * 处理Spout输出的元组数据
     * @param tuple
     */
    @Override
    public void execute(Tuple tuple) {
        System.out.println("CreateBolt.execute");
        //处理通话记录
        String from = tuple.getString(0);
        String to = tuple.getString(1);
        Integer duration = tuple.getInteger(2);
        //产生新的tuple
        collector.emit(new Values(from+"_"+to,duration));
    }

    @Override
    public void cleanup() {

    }

    /**
     * 设置输出字段的描述
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
       outputFieldsDeclarer.declare(new Fields("call","duration"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
