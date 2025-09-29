package org.cerveza.cafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tienda")
public class Tienda {

    @Id
    @Column(name = "id_tienda")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String telefono;

    @Column
    private String direccion;

    @Column(name = "empleado_responsable")
    private String empleadoResponsable;

    @OneToMany(mappedBy = "tienda")
    private Set<Inventario> inventario = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tienda")
    private Set<ProductoVendido> ventas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tienda")
    private Set<IngredienteTienda> ingredientes = new LinkedHashSet<>();

    public Tienda() {
    }

    public Tienda(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmpleadoResponsable() {
        return empleadoResponsable;
    }

    public void setEmpleadoResponsable(String empleadoResponsable) {
        this.empleadoResponsable = empleadoResponsable;
    }

    public Set<Inventario> getInventario() {
        return inventario;
    }

    public Set<ProductoVendido> getVentas() {
        return ventas;
    }

    public Set<IngredienteTienda> getIngredientes() {
        return ingredientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tienda tienda)) return false;
        return Objects.equals(id, tienda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
