package com.example.dmy.kafkastorm.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: daimengying
 * @Date: 2018/12/29 11:45
 * @Description:
 */
@RestController
@RequestMapping("/product")
public class SendController {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping(value = "/send")
    public String sendKafka(HttpServletRequest request, HttpServletResponse response) {
        try {
            String message = request.getParameter("message");
            System.out.println("kafka的消息="+message);
            kafkaTemplate.send("test_topic", message);
            System.out.println("发送kafka成功...");
            return "发送kafka成功";
        } catch (Exception e) {
            System.out.println("发送kafka失败..."+e.getMessage());
            return "发送kafka失败";
        }
    }
}
