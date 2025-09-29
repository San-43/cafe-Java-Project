package org.cerveza.cafe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cerveza.cafe.model.Cliente;
import org.cerveza.cafe.model.DumpRecord;
import org.cerveza.cafe.model.Producto;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona el ciclo de vida del {@link EntityManagerFactory} y el archivo de base de datos.
 */
public final class DatabaseManager {

    private static EntityManagerFactory entityManagerFactory;
    private static Path databasePath;

    private DatabaseManager() {
        // Utility class
    }

    /**
     * Inicializa el {@link EntityManagerFactory} si todavía no se ha creado.
     */
    public static synchronized void initialize() {
        if (entityManagerFactory != null) {
            return;
        }

        try {
            databasePath = resolveDatabasePath();
            Files.createDirectories(databasePath.getParent());
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo preparar el directorio de la base de datos", e);
        }

        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", databasePath.toString());
        properties.put("javax.persistence.jdbc.url", databasePath.toString());

        entityManagerFactory = Persistence.createEntityManagerFactory("CafePU", properties);

        seedDatabase();
    }

    private static Path resolveDatabasePath() {
        String userHome = System.getProperty("user.home");
        Path downloadsDir = Paths.get(userHome, "Downloads");
        return downloadsDir.resolve("cafe.odb").toAbsolutePath();
    }

    /**
     * Libera los recursos asociados al {@link EntityManagerFactory}.
     */
    public static synchronized void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("La base de datos no ha sido inicializada.");
        }
        return entityManagerFactory;
    }

    public static Path getDatabasePath() {
        if (databasePath == null) {
            throw new IllegalStateException("La base de datos no ha sido inicializada.");
        }
        return databasePath;
    }

    private static void seedDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            long clientes = entityManager.createQuery("SELECT COUNT(c) FROM Cliente c", Long.class)
                    .getSingleResult();
            long productos = entityManager.createQuery("SELECT COUNT(p) FROM Producto p", Long.class)
                    .getSingleResult();
            long dumps = entityManager.createQuery("SELECT COUNT(d) FROM DumpRecord d", Long.class)
                    .getSingleResult();

            if (clientes == 0L && productos == 0L) {
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
            }

            if (dumps == 0L) {
                DumpRecord dumpRecord = new DumpRecord(
                        databasePath.getFileName().toString(),
                        databasePath.toString(),
                        LocalDateTime.now()
                );
                entityManager.persist(dumpRecord);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
