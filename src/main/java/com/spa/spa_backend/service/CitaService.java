package com.spa.spa_backend.service;

import com.spa.spa_backend.model.Cita;
import com.spa.spa_backend.model.EstadoCita;
import com.spa.spa_backend.model.Servicio;
import com.spa.spa_backend.repository.CitaRepository;
import com.spa.spa_backend.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    /**
     * Crea una cita nueva, validando conflicto de horario
     * y calculando duración/total a partir del servicio.
     */
    public Cita crearCita(Cita cita) {
        Optional<Servicio> servicioOpt = servicioRepository.findById(cita.getServicioId());
        if (servicioOpt.isEmpty()) {
            throw new IllegalArgumentException("El servicio indicado no existe");
        }
        Servicio servicio = servicioOpt.get();

        // Completar duración y total automáticamente desde el servicio
        cita.setDuracionMin(servicio.getDuracionMin());
        cita.setTotal(servicio.getPrecio());

        if (cita.getEstado() == null) {
            cita.setEstado(EstadoCita.PENDIENTE);
        }

        if (hayConflicto(cita)) {
            throw new IllegalStateException("La trabajadora ya tiene una cita en ese horario");
        }

        return citaRepository.save(cita);
    }

    /**
     * Verifica si la nueva cita se cruza en el tiempo con otra cita
     * ya existente de la misma trabajadora, en la misma fecha.
     */
    private boolean hayConflicto(Cita nuevaCita) {
        List<Cita> citasDelDia = citaRepository.findByTrabajadoraIdAndFecha(
                nuevaCita.getTrabajadoraId(), nuevaCita.getFecha());

        LocalTime nuevoInicio = nuevaCita.getHora();
        LocalTime nuevoFin = nuevoInicio.plusMinutes(nuevaCita.getDuracionMin());

        for (Cita existente : citasDelDia) {
            // Ignorar citas canceladas al validar conflictos
            if (existente.getEstado() == EstadoCita.CANCELADA) {
                continue;
            }
            // Si estamos actualizando una cita, no comparar consigo misma
            if (nuevaCita.getId() != null && nuevaCita.getId().equals(existente.getId())) {
                continue;
            }

            LocalTime existenteInicio = existente.getHora();
            LocalTime existenteFin = existenteInicio.plusMinutes(existente.getDuracionMin());

            boolean seCruzan = nuevoInicio.isBefore(existenteFin) && existenteInicio.isBefore(nuevoFin);
            if (seCruzan) {
                return true;
            }
        }
        return false;
    }
}