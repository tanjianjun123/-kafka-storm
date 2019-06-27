package com.example.dmy.kafkastorm.storm.bolt;

import com.example.dmy.kafkastorm.util.JedisUtil;
import com.example.dmy.kafkastorm.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @Author: daimengying
 * @Date: 2019/1/2 17:18
 * @Description:
 */
@Slf4j
public class ResultBolt extends BaseRichBolt {

    private final static Logger log = LoggerFactory.getLogger(ResultBolt.class);
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    @Override
    public void execute(Tuple tuple) {
        Jedis jedis = JedisUtil.getJedis();
        String msg = tuple.getStringByField("msg");
        Long count = tuple.getLongByField("count");
        String reportMessage = "{'msg': '" + msg + "', 'count': '" + count + "'}";
        System.out.println("处理后信息为："+reportMessage);
        jedis.set(msg,String.valueOf(count));
        // 释放jedis资源
        JedisUtil.returnJedis(jedis);
        System.out.println(msg + "------->写入数据到Redis");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
