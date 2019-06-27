package com.example.dmy.kafkastorm.storm.bolt;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: daimengying
 * @Date: 2019/1/2 17:18
 * @Description:
 */
@Slf4j
public class PrintOutBolt extends BaseRichBolt {

    private final static Logger log = LoggerFactory.getLogger(PrintOutBolt.class);
    private Map<String, Long> counts = null;
    OutputCollector collector;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.counts = new ConcurrentHashMap<>();
        this.collector=outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        //获取到spout数据，处理逻辑
        String msg=tuple.getStringByField("printout");
        try{
            List<String> listStr =JSON.parseArray(msg,String.class);
            Long count=0L;
            if(listStr!=null&&listStr.size()>0){
                Iterator<String> iterator = listStr.iterator();
                while (iterator.hasNext()) {
                    String data =iterator.next();//一个遍历中只能使用一次iterator.next()
                    System.out.println("最终从Kafka获取的数据>>>"+ data);
                    count = this.counts.get(data);
                    if (count == null) {
                        count = 0L;
                    }
                    count++;
                    this.counts.put(data, count);
                    collector.emit(new Values(data, count));
                }
            }

        }catch(Exception e){
            log.error("Bolt的数据处理失败!数据:{}",msg,e);
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("msg", "count"));
    }
}
