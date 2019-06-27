package com.example.dmy.kafkastorm.storm;

import com.example.dmy.kafkastorm.storm.spout.KafkaPrintDataSpout;
import com.example.dmy.kafkastorm.storm.bolt.PrintOutBolt;
import com.example.dmy.kafkastorm.storm.bolt.ResultBolt;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: daimengying
 * @Date: 2019/1/3 09:35
 * @Description:
 */
@Slf4j
@Component
public class TopologyApp {
    private final static Logger log = LoggerFactory.getLogger(TopologyApp.class);
    //    @Value("${storm.args}")
//    private String[] args;
//    servlet初始化时执行一次
//    @PostConstruct
    public static void runStorm(String[] args) {
        // 定义一个拓扑
        TopologyBuilder builder = new TopologyBuilder();
        // 设置1个Executeor(线程)，默认一个
        builder.setSpout("KafkaPrintDataSpout", new KafkaPrintDataSpout(), 1);
        // shuffleGrouping:表示是随机分组
        // 设置1个Executeor(线程)，和两个task
        //     shuffleGrouping（随机分组）    2）fieldsGrouping（按照字段分组，在这里即是同一个单词只能发送给一个Bolt）
        builder.setBolt("PrintOutBolt", new PrintOutBolt(), 1).setNumTasks(1).fieldsGrouping("KafkaPrintDataSpout",new Fields(("printout")));
        builder.setBolt("ResultBolt", new ResultBolt(), 1).setNumTasks(1).fieldsGrouping("PrintOutBolt",new Fields("msg"));
        Config conf = new Config();
        //设置一个应答者
        conf.setNumAckers(1);
        //设置一个work
        conf.setNumWorkers(1);
        try {
            // 有参数时，表示向集群提交作业，并把第一个参数当做topology名称
            // 没有参数时，本地提交
            if (args != null && args.length > 0) {
                log.info("运行远程模式");
//                没有配置storm命令环境变量，切换到storm的bin目录执行：
//                ./storm jar /opt/libs/storm-0.0.1-SNAPSHOT.jar com.XXX.storm.XXXTopology [参数]
                StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
            } else {
                // 启动本地模式
                log.info("运行本地模式");
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("TopologyApp", conf, builder.createTopology());
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            log.error("storm启动失败!程序退出!",e);
//            System.exit(1);
        }

        log.info("storm启动成功...");

    }
}
