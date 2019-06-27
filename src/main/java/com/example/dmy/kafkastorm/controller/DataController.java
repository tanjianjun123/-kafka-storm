package com.example.dmy.kafkastorm.controller;


import com.example.dmy.kafkastorm.util.JedisUtil;
import com.example.dmy.kafkastorm.util.RedisUtil;
import com.example.dmy.kafkastorm.util.Result;
import com.example.dmy.kafkastorm.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author tanjianjun
 * @create 2018-12-19 10:51
 */
@Controller
@RequestMapping("url")
public class DataController {
    private final static Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    RedisUtil redisUtil;

    /**
     * 跳转到登录界面
     *
     * @return
     */
    @RequestMapping(value = "/login.action", method = RequestMethod.GET)
    public String login() {
        //登录信息灵活书写 这里加上model是应用freemarker ，此时已经不用了就不要添加了
//        model.addAttribute("title", "欢迎登录商城后台");
        System.out.println("跳转页面");
        return "/login.html";
    }

    @RequestMapping(value="/data.action",method = RequestMethod.GET)
    @ResponseBody
    public Result queryGoods(){
        ArrayList<Object> list = new ArrayList<>();
        Jedis redis = JedisUtil.getJedis();
        // 获取所有key
        Set<String> set = redis.keys("*");
        for (String key : set) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(key,redis.get(key));
            list.add(map);
        }
        // 释放jedis资源
        JedisUtil.returnJedis(redis);
        return ResultUtil.success(list);
    }
}