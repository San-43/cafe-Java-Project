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
@Table(name = "ingredientes")
public class Ingrediente {

    @Id
    @Column(name = "id_ingrediente")
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column
    private String preparacion;

    @OneToMany(mappedBy = "ingrediente")
    private Set<ProporcionIngrediente> recetas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "ingrediente")
    private Set<IngredienteTienda> disponibilidad = new LinkedHashSet<>();

    public Ingrediente() {
    }

    public Ingrediente(Long id, String descripcion, String preparacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.preparacion = preparacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public Set<ProporcionIngrediente> getRecetas() {
        return recetas;
    }

    public Set<IngredienteTienda> getDisponibilidad() {
        return disponibilidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingrediente that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
