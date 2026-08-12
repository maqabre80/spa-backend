package com.spa.spa_backend.repository;

import com.spa.spa_backend.model.Trabajadora;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajadoraRepository extends MongoRepository<Trabajadora, String> {
    List<Trabajadora> findByActivaTrue();
}