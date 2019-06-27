package com.example.dmy.kafkastorm;

import com.example.dmy.kafkastorm.config.GetSpringBean;
import com.example.dmy.kafkastorm.storm.TopologyApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaStormApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =SpringApplication.run(KafkaStormApplication.class, args);
		GetSpringBean springBean=new GetSpringBean();
		springBean.setApplicationContext(context);
		TopologyApp app = context.getBean(TopologyApp.class);
		TopologyApp.runStorm(args);
	}

}

