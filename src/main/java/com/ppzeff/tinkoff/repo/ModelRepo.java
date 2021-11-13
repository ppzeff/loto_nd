package com.ppzeff.tinkoff.repo;

import com.ppzeff.tinkoff.model.Model;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ModelRepo extends CrudRepository<Model, Long> {

    @Query(value = "select g from Model g where (g.LastUpdate = (select max(u.LastUpdate) from Model u where u.code = :code) and g.code = :code)")
    List<Model> findLastDataByCode(@Param("code") int code);

    @Query(value = "select d from Model d WHERE (d.code = :code)  and d.LastUpdate BETWEEN  :start and :stop")
    List<Model> findAll(@Param("code") int code, @Param("start") long start, @Param("stop") long stop);

    @Query(value = "select DISTINCT u.code  from Model u")
    List<Integer> findAllCode();
}
