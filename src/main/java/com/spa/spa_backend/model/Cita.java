package com.spa.spa_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "citas")
public class Cita {

    @Id
    private String id;

    private String clienteId;
    private String trabajadoraId;
    private String servicioId;

    private LocalDate fecha;
    private LocalTime hora;
    private Integer duracionMin;

    private EstadoCita estado;
    private Double total;

    public Cita() {
    }

    public Cita(String clienteId, String trabajadoraId, String servicioId,
                LocalDate fecha, LocalTime hora, Integer duracionMin,
                EstadoCita estado, Double total) {
        this.clienteId = clienteId;
        this.trabajadoraId = trabajadoraId;
        this.servicioId = servicioId;
        this.fecha = fecha;
        this.hora = hora;
        this.duracionMin = duracionMin;
        this.estado = estado;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getTrabajadoraId() {
        return trabajadoraId;
    }

    public void setTrabajadoraId(String trabajadoraId) {
        this.trabajadoraId = trabajadoraId;
    }

    public String getServicioId() {
        return servicioId;
    }

    public void setServicioId(String servicioId) {
        this.servicioId = servicioId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Integer getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(Integer duracionMin) {
        this.duracionMin = duracionMin;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}