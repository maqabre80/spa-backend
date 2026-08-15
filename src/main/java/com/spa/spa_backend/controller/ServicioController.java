package com.spa.spa_backend.controller;

import com.spa.spa_backend.model.Servicio;
import com.spa.spa_backend.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    @GetMapping("/activos")
    public List<Servicio> listarActivos() {
        return servicioRepository.findByActivoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable String id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        return servicio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody Servicio servicio) {
        Servicio nuevo = servicioRepository.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable String id, @RequestBody Servicio datos) {
        return servicioRepository.findById(id)
                .map(servicio -> {
                    servicio.setNombre(datos.getNombre());
                    servicio.setDescripcion(datos.getDescripcion());
                    servicio.setPrecio(datos.getPrecio());
                    servicio.setDuracionMin(datos.getDuracionMin());
                    servicio.setActivo(datos.isActivo());
                    Servicio actualizado = servicioRepository.save(servicio);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!servicioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servicioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}