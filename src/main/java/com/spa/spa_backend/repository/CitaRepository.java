package com.spa.spa_backend.repository;

import com.spa.spa_backend.model.Cita;
import com.spa.spa_backend.model.EstadoCita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends MongoRepository<Cita, String> {
    List<Cita> findByTrabajadoraIdAndFecha(String trabajadoraId, LocalDate fecha);
    List<Cita> findByFecha(LocalDate fecha);
    List<Cita> findByClienteId(String clienteId);
    List<Cita> findByEstado(EstadoCita estado);
}