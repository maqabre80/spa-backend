package com.spa.spa_backend.controller;

import com.spa.spa_backend.model.Cita;
import com.spa.spa_backend.model.EstadoCita;
import com.spa.spa_backend.repository.CitaRepository;
import com.spa.spa_backend.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaService citaService;

    // GET /api/citas -> listar todas
    @GetMapping
    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }

    // GET /api/citas/fecha/2026-08-15 -> citas de un día específico
    @GetMapping("/fecha/{fecha}")
    public List<Cita> listarPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return citaRepository.findByFecha(fecha);
    }

    // GET /api/citas/trabajadora/{id}/fecha/{fecha} -> agenda de una trabajadora en un día
    @GetMapping("/trabajadora/{trabajadoraId}/fecha/{fecha}")
    public List<Cita> listarPorTrabajadoraYFecha(
            @PathVariable String trabajadoraId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return citaRepository.findByTrabajadoraIdAndFecha(trabajadoraId, fecha);
    }

    // GET /api/citas/cliente/{id} -> historial de citas de un cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Cita> listarPorCliente(@PathVariable String clienteId) {
        return citaRepository.findByClienteId(clienteId);
    }

    // GET /api/citas/estado/{estado} -> filtrar por estado
    @GetMapping("/estado/{estado}")
    public List<Cita> listarPorEstado(@PathVariable EstadoCita estado) {
        return citaRepository.findByEstado(estado);
    }

    // GET /api/citas/{id} -> obtener una cita
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable String id) {
        Optional<Cita> cita = citaRepository.findById(id);
        return cita.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/citas -> crear (valida conflicto de horario)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cita cita) {
        try {
            Cita nueva = citaService.crearCita(cita);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH /api/citas/{id}/estado -> cambiar solo el estado (ej: Confirmada -> En proceso -> Completada)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable String id, @RequestBody EstadoCambioRequest body) {
        return citaRepository.findById(id)
                .map(cita -> {
                    cita.setEstado(body.estado());
                    Cita actualizada = citaRepository.save(cita);
                    return ResponseEntity.ok(actualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/citas/{id} -> eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!citaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        citaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Record auxiliar para recibir { "estado": "CONFIRMADA" } en el PATCH
    public record EstadoCambioRequest(EstadoCita estado) {}
}