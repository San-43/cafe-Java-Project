package org.cerveza.cafe.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "receta")
public class Receta {

    @Id
    @Column(name = "id_receta")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false, unique = true)
    private Producto producto;

    @Column(name = "costo_preparacion", nullable = false)
    private BigDecimal costoPreparacion;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProporcionIngrediente> ingredientes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Paso> pasos = new LinkedHashSet<>();

    public Receta() {
    }

    public Receta(Long id, Producto producto, BigDecimal costoPreparacion) {
        this.id = id;
        this.producto = producto;
        this.costoPreparacion = costoPreparacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        if (producto != null && producto.getReceta() != this) {
            producto.setReceta(this);
        }
    }

    public BigDecimal getCostoPreparacion() {
        return costoPreparacion;
    }

    public void setCostoPreparacion(BigDecimal costoPreparacion) {
        this.costoPreparacion = costoPreparacion;
    }

    public Set<ProporcionIngrediente> getIngredientes() {
        return ingredientes;
    }

    public Set<Paso> getPasos() {
        return pasos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receta receta)) return false;
        return Objects.equals(id, receta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
