package com.ppzeff.loto_nd.repository;

import com.ppzeff.loto_nd.models.LOTONdModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LOTONdRepository extends CrudRepository<LOTONdModel, Long> {

    @Query(value = "select DISTINCT u.specialistFIO  from LOTONdModel u")
    List<String> findAllSpecialistFIO();
}
