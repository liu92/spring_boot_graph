package com.graph.demo.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.janusgraph.core.schema.Index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 创建spout, Spout类，负责产生数据流
 * @ClassName CreatSpout
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/4 16:18
 **/
public class CreatSpout implements IRichSpout{
    //Spout输出收集器
    private SpoutOutputCollector collector;
    //是否完成
    private boolean completed = false;
    //上下文
    private TopologyContext context;
    //随机生成器
    private Random randomGenerator = new Random();
    private Integer idx = 0;


    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.context = topologyContext;
        this.collector = spoutOutputCollector;
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    /**
     * 下一个元组，用来产生数据
     */
    @Override
    public void nextTuple() {
        if(this.idx <= 1000){
            List<String> mobileNumber = new ArrayList<>();
            mobileNumber.add("12345601");
            mobileNumber.add("12345602");
            mobileNumber.add("12345603");
            mobileNumber.add("12345604");
            Integer localIdx = 0;
            while (localIdx++<100 && this.idx++<1000){
                //主叫
                String caller = mobileNumber.get(randomGenerator.nextInt(4));
                //被叫
                String callee = mobileNumber.get(randomGenerator.nextInt(4));
                while (caller == callee){
                    //主叫被叫是同一个
                    //被叫
                    callee = mobileNumber.get(randomGenerator.nextInt(4));
                }
                //模拟通话时长
                Integer duration = randomGenerator.nextInt(60);
                this.collector.emit(new Values(caller,callee,duration));
            }
        }
    }

    @Override
    public void ack(Object o) {

    }

    @Override
    public void fail(Object o) {

    }

    /**
     * 定义输出字段的描述信息
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("from","to","duration"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
