package com.example.dmy.kafkastorm.service.impl;

import com.example.dmy.kafkastorm.dao.DataMapper;
import com.example.dmy.kafkastorm.domain.Game;
import com.example.dmy.kafkastorm.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("dataService")
public class DataServiceImpl implements DataService {
    @Autowired
    DataMapper dataMapper;
    @Override
    public List<Game> queryList() {
        return dataMapper.queryList();
    }
}
