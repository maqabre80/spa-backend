package com.spa.spa_backend.repository;

import com.spa.spa_backend.model.Servicio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends MongoRepository<Servicio, String> {
    List<Servicio> findByActivoTrue();
}