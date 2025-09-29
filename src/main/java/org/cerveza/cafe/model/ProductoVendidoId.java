package org.cerveza.cafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class ProductoVendidoId implements Serializable {

    @Column(name = "id_tienda")
    private Long tiendaId;

    @Column(name = "id_producto")
    private Long productoId;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;

    public ProductoVendidoId() {
    }

    public ProductoVendidoId(Long tiendaId, Long productoId, LocalDateTime fechaCompra) {
        this.tiendaId = tiendaId;
        this.productoId = productoId;
        this.fechaCompra = fechaCompra;
    }

    public Long getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Long tiendaId) {
        this.tiendaId = tiendaId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductoVendidoId that)) return false;
        return Objects.equals(tiendaId, that.tiendaId) &&
               Objects.equals(productoId, that.productoId) &&
               Objects.equals(fechaCompra, that.fechaCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tiendaId, productoId, fechaCompra);
    }
}
