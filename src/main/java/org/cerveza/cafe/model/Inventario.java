package org.cerveza.cafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "inventario")
public class Inventario {

    @EmbeddedId
    private InventarioId id = new InventarioId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tiendaId")
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productoId")
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer stock;

    public Inventario() {
    }

    public Inventario(Tienda tienda, Producto producto, Integer stock) {
        this.tienda = tienda;
        this.producto = producto;
        this.stock = stock;
        this.id = new InventarioId(tienda.getId(), producto.getId());
    }

    public InventarioId getId() {
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        if (producto != null) {
            this.id.setProductoId(producto.getId());
        }
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventario that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
