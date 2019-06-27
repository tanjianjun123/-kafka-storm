package com.example.dmy.kafkastorm.job;

import com.example.dmy.kafkastorm.domain.Game;
import com.example.dmy.kafkastorm.service.DataService;
import com.example.dmy.kafkastorm.storm.TopologyApp;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: daimengying
 * @Date: 2019/1/3 14:31
 * @Description:定时发送Kafka消息,2秒执行一次
 */
@Slf4j
@Component
public class sendMessageJob {
    private final static Logger log = LoggerFactory.getLogger(sendMessageJob.class);
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private DataService dataService;

    private static Integer size=0;
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
//        String message=new Date().toString();
//        String message=String.valueOf((int) (Math.random() * 10));
        List<Game> games = dataService.queryList();
//        if(games.size()==size){
//            log.info("未产生最新数据");
//            return;
//        }else{
            for(Game game:games){
                String message=String.valueOf(game.getUserId());
                kafkaTemplate.send("test_topic",message );
                log.info("定时器发送消息："+message);
            }
            size=games.size();
//        }
    }
}
