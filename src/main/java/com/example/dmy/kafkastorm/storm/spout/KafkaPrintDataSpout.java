package com.example.dmy.kafkastorm.storm.spout;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: daimengying
 * @Date: 2019/1/2 17:18
 * @Description:
 */
@Slf4j
public class KafkaPrintDataSpout extends BaseRichSpout {
    private final static Logger log = LoggerFactory.getLogger(KafkaPrintDataSpout.class);

    private SpoutOutputCollector collector;

    private KafkaConsumer<String, String> consumer;

    private ConsumerRecords<String, String> msgList;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        this.collector = collector;
        kafkaInit();
    }

    @Override
    public void nextTuple() {
        for (;;) {
            try {
                msgList = consumer.poll(1000);
                if (null != msgList && !msgList.isEmpty()) {
                    String msg = "";
                    List<String> list=new ArrayList<>();
                    for (ConsumerRecord<String, String> record : msgList) {
                        // 原始数据
                        msg = record.value();
                        if (null == msg || "".equals(msg.trim())) {
                            continue;
                        }
                        try{
                            list.add(msg);
                        }catch(Exception e){
                            log.error("数据格式不符!数据:{}",msg);
                            continue;
                        }
                    }
                    log.info("Spout发射的数据:"+list);
                    //发送到bolt中
                    this.collector.emit(new Values(JSON.toJSONString(list)));
                    consumer.commitAsync();
                    TimeUnit.SECONDS.sleep(2);
                }else{
                    log.info("未拉取到数据...");
                    TimeUnit.SECONDS.sleep(5);
                }
            }catch (Exception e) {
                log.error("消息队列处理异常!", e);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e1) {
                    log.error("暂停失败!",e1);
                }
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("printout"));
    }

    /**
     * 初始化kafka配置
     */
    private void kafkaInit(){
        Properties props = new Properties();
        props.put("group.id", "springboot-kafka-storm");
        props.put("bootstrap.servers", "121.43.176.216:9092");
//        props.put("max.poll.records", "100");
        props.put("enable.auto.commit", "false");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        String topic = "test_topic";

        this.consumer.subscribe(Arrays.asList(topic));
        log.info("消息队列[" + topic + "] 开始初始化...");
    }
}
