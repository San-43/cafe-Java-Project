package org.cerveza.cafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "proporcion_ingrediente")
public class ProporcionIngrediente {

    @EmbeddedId
    private ProporcionIngredienteId id = new ProporcionIngredienteId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recetaId")
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredienteId")
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    @Column(nullable = false)
    private BigDecimal medida;

    public ProporcionIngrediente() {
    }

    public ProporcionIngrediente(Receta receta, Ingrediente ingrediente, BigDecimal medida) {
        this.receta = receta;
        this.ingrediente = ingrediente;
        this.medida = medida;
        this.id = new ProporcionIngredienteId(receta.getId(), ingrediente.getId());
    }

    public ProporcionIngredienteId getId() {
        return id;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
        if (receta != null) {
            this.id.setRecetaId(receta.getId());
        }
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
        if (ingrediente != null) {
            this.id.setIngredienteId(ingrediente.getId());
        }
    }

    public BigDecimal getMedida() {
        return medida;
    }

    public void setMedida(BigDecimal medida) {
        this.medida = medida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProporcionIngrediente that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
