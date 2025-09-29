package org.cerveza.cafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProporcionIngredienteId implements Serializable {

    @Column(name = "id_receta")
    private Long recetaId;

    @Column(name = "id_ingrediente")
    private Long ingredienteId;

    public ProporcionIngredienteId() {
    }

    public ProporcionIngredienteId(Long recetaId, Long ingredienteId) {
        this.recetaId = recetaId;
        this.ingredienteId = ingredienteId;
    }

    public Long getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(Long recetaId) {
        this.recetaId = recetaId;
    }

    public Long getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(Long ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProporcionIngredienteId that)) return false;
        return Objects.equals(recetaId, that.recetaId) &&
               Objects.equals(ingredienteId, that.ingredienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recetaId, ingredienteId);
    }
}
