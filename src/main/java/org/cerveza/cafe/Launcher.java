package org.cerveza.cafe;

import javafx.application.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cerveza.cafe.model.Cliente;
import org.cerveza.cafe.model.Producto;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Launcher {
    public static void main(String[] args) {
        seedDatabase();
        Application.launch(HelloApplication.class, args);
    }

    private static void seedDatabase() {
        try {
            Path dbPath = Paths.get("target", "database", "cafe.odb").toAbsolutePath();
            Files.createDirectories(dbPath.getParent());

            Map<String, String> properties = new HashMap<>();
            properties.put("jakarta.persistence.jdbc.url", dbPath.toString());

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CafePU", properties);
            EntityManager entityManager = emf.createEntityManager();

            try {
                Long clientes = entityManager.createQuery("SELECT COUNT(c) FROM Cliente c", Long.class)
                        .getSingleResult();

                if (clientes == 0L) {
                    entityManager.getTransaction().begin();

                    Cliente ana = new Cliente(1L, "Ana Torres");
                    ana.setTelefono("555-0101");
                    ana.setCorreo("ana@example.com");

                    Cliente marco = new Cliente(2L, "Marco Díaz");
                    marco.setTelefono("555-0202");
                    marco.setCorreo("marco@example.com");

                    Producto espresso = new Producto(1L, "Espresso", new BigDecimal("12.50"), new BigDecimal("30.00"));
                    Producto capuccino = new Producto(2L, "Capuccino", new BigDecimal("15.00"), new BigDecimal("38.00"));

                    entityManager.persist(ana);
                    entityManager.persist(marco);
                    entityManager.persist(espresso);
                    entityManager.persist(capuccino);

                    entityManager.getTransaction().commit();
                }
            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                throw e;
            } finally {
                entityManager.close();
                emf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
