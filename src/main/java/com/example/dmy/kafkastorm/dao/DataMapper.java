package com.example.dmy.kafkastorm.dao;

import com.example.dmy.kafkastorm.domain.Game;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DataMapper {
    /**
     * 查询所有
     * @return
     */
    List<Game> queryList();
}
