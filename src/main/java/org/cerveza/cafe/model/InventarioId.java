package org.cerveza.cafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InventarioId implements Serializable {

    @Column(name = "id_tienda")
    private Long tiendaId;

    @Column(name = "id_producto")
    private Long productoId;

    public InventarioId() {
    }

    public InventarioId(Long tiendaId, Long productoId) {
        this.tiendaId = tiendaId;
        this.productoId = productoId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventarioId that)) return false;
        return Objects.equals(tiendaId, that.tiendaId) &&
               Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tiendaId, productoId);
    }
}
