package com.example.dmy.kafkastorm.service;

import com.example.dmy.kafkastorm.domain.Game;

import java.util.List;

public interface DataService {
    /**
     * 查询
     * @return
     */
    public List<Game> queryList();
}
