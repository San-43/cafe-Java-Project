package org.cerveza.cafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class IngredienteTiendaId implements Serializable {

    @Column(name = "id_tienda")
    private Long tiendaId;

    @Column(name = "id_ingrediente")
    private Long ingredienteId;

    @Column(name = "fecha_caducidad")
    private LocalDate fechaCaducidad;

    public IngredienteTiendaId() {
    }

    public IngredienteTiendaId(Long tiendaId, Long ingredienteId, LocalDate fechaCaducidad) {
        this.tiendaId = tiendaId;
        this.ingredienteId = ingredienteId;
        this.fechaCaducidad = fechaCaducidad;
    }

    public Long getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Long tiendaId) {
        this.tiendaId = tiendaId;
    }

    public Long getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(Long ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredienteTiendaId that)) return false;
        return Objects.equals(tiendaId, that.tiendaId) &&
               Objects.equals(ingredienteId, that.ingredienteId) &&
               Objects.equals(fechaCaducidad, that.fechaCaducidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tiendaId, ingredienteId, fechaCaducidad);
    }
}
