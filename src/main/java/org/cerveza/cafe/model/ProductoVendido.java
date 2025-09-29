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
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "producto_vendido")
public class ProductoVendido {

    @EmbeddedId
    private ProductoVendidoId id = new ProductoVendidoId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tiendaId")
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productoId")
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "precio_venta", nullable = false)
    private BigDecimal precioVenta;

    public ProductoVendido() {
    }

    public ProductoVendido(Tienda tienda, Producto producto, Cliente cliente, LocalDateTime fechaCompra, BigDecimal precioVenta) {
        this.tienda = tienda;
        this.producto = producto;
        this.cliente = cliente;
        this.precioVenta = precioVenta;
        this.id = new ProductoVendidoId(tienda.getId(), producto.getId(), fechaCompra);
    }

    public ProductoVendidoId getId() {
        return id;
    }

    public LocalDateTime getFechaCompra() {
        return id.getFechaCompra();
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.id.setFechaCompra(fechaCompra);
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductoVendido that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
