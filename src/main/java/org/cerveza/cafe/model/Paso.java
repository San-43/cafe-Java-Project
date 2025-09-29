package org.cerveza.cafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "paso")
public class Paso {

    @Id
    @Column(name = "id_paso")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;

    @Column(name = "base_descripcion", nullable = false, length = 1024)
    private String descripcion;

    public Paso() {
    }

    public Paso(Long id, Receta receta, String descripcion) {
        this.id = id;
        this.receta = receta;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paso paso)) return false;
        return Objects.equals(id, paso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
