package com.spa.spa_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "trabajadoras")
public class Trabajadora {

    @Id
    private String id;

    private String nombre;
    private String telefono;
    private String fotoUrl;
    private List<String> especialidades; // ej: "Uñas acrílicas", "Gel", "Nail art"
    private Double porcentajeComision;   // ej: 40.0 para 40%
    private boolean activa;

    public Trabajadora() {
    }

    public Trabajadora(String nombre, String telefono, String fotoUrl,
                        List<String> especialidades, Double porcentajeComision, boolean activa) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fotoUrl = fotoUrl;
        this.especialidades = especialidades;
        this.porcentajeComision = porcentajeComision;
        this.activa = activa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}