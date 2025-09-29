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
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ingrediente_tienda")
public class IngredienteTienda {

    @EmbeddedId
    private IngredienteTiendaId id = new IngredienteTiendaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tiendaId")
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredienteId")
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private BigDecimal costo;

    public IngredienteTienda() {
    }

    public IngredienteTienda(Tienda tienda, Ingrediente ingrediente, Integer stock, BigDecimal costo, LocalDate fechaCaducidad) {
        this.tienda = tienda;
        this.ingrediente = ingrediente;
        this.stock = stock;
        this.costo = costo;
        this.id = new IngredienteTiendaId(tienda.getId(), ingrediente.getId(), fechaCaducidad);
    }

    public IngredienteTiendaId getId() {
        return id;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
        if (tienda != null) {
            this.id.setTiendaId(tienda.getId());
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.id.setFechaCaducidad(fechaCaducidad);
    }

    public LocalDate getFechaCaducidad() {
        return this.id.getFechaCaducidad();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredienteTienda that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
