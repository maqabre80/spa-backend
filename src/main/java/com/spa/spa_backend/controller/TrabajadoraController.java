package com.spa.spa_backend.controller;

import com.spa.spa_backend.model.Trabajadora;
import com.spa.spa_backend.repository.TrabajadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trabajadoras")
@CrossOrigin(origins = "*")
public class TrabajadoraController {

    @Autowired
    private TrabajadoraRepository trabajadoraRepository;

    // GET /api/trabajadoras -> listar todas
    @GetMapping
    public List<Trabajadora> listarTodas() {
        return trabajadoraRepository.findAll();
    }

    // GET /api/trabajadoras/activas -> listar solo activas
    @GetMapping("/activas")
    public List<Trabajadora> listarActivas() {
        return trabajadoraRepository.findByActivaTrue();
    }

    // GET /api/trabajadoras/{id} -> obtener una por id
    @GetMapping("/{id}")
    public ResponseEntity<Trabajadora> obtenerPorId(@PathVariable String id) {
        Optional<Trabajadora> trabajadora = trabajadoraRepository.findById(id);
        return trabajadora.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/trabajadoras -> crear nueva
    @PostMapping
    public ResponseEntity<Trabajadora> crear(@RequestBody Trabajadora trabajadora) {
        Trabajadora nueva = trabajadoraRepository.save(trabajadora);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // PUT /api/trabajadoras/{id} -> actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Trabajadora> actualizar(@PathVariable String id, @RequestBody Trabajadora datos) {
        return trabajadoraRepository.findById(id)
                .map(trabajadora -> {
                    trabajadora.setNombre(datos.getNombre());
                    trabajadora.setTelefono(datos.getTelefono());
                    trabajadora.setFotoUrl(datos.getFotoUrl());
                    trabajadora.setEspecialidades(datos.getEspecialidades());
                    trabajadora.setPorcentajeComision(datos.getPorcentajeComision());
                    trabajadora.setActiva(datos.isActiva());
                    Trabajadora actualizada = trabajadoraRepository.save(trabajadora);
                    return ResponseEntity.ok(actualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/trabajadoras/{id} -> eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!trabajadoraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        trabajadoraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}