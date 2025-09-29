package org.cerveza.cafe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.cerveza.cafe.model.Cliente;
import org.cerveza.cafe.model.DumpRecord;
import org.cerveza.cafe.model.Ingrediente;
import org.cerveza.cafe.model.IngredienteTienda;
import org.cerveza.cafe.model.Inventario;
import org.cerveza.cafe.model.Paso;
import org.cerveza.cafe.model.Producto;
import org.cerveza.cafe.model.ProductoVendido;
import org.cerveza.cafe.model.ProporcionIngrediente;
import org.cerveza.cafe.model.Receta;
import org.cerveza.cafe.model.Tienda;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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

            long clientes = entityManager.createQuery("SELECT COUNT(c) FROM Cliente c", Long.class).getSingleResult();
            long productos = entityManager.createQuery("SELECT COUNT(p) FROM Producto p", Long.class).getSingleResult();
            long tiendas = entityManager.createQuery("SELECT COUNT(t) FROM Tienda t", Long.class).getSingleResult();
            long ingredientes = entityManager.createQuery("SELECT COUNT(i) FROM Ingrediente i", Long.class).getSingleResult();
            long recetas = entityManager.createQuery("SELECT COUNT(r) FROM Receta r", Long.class).getSingleResult();
            long dumps = entityManager.createQuery("SELECT COUNT(d) FROM DumpRecord d", Long.class).getSingleResult();

            if (clientes == 0L && productos == 0L && tiendas == 0L && ingredientes == 0L && recetas == 0L) {
                Cliente ana = new Cliente(1L, "Ana Torres");
                ana.setTelefono("555-0101");
                ana.setCorreo("ana@example.com");

                Cliente marco = new Cliente(2L, "Marco Díaz");
                marco.setTelefono("555-0202");
                marco.setCorreo("marco@example.com");

                Producto espresso = new Producto(1L, "Espresso", new BigDecimal("12.50"), new BigDecimal("30.00"));
                Producto capuccino = new Producto(2L, "Capuccino", new BigDecimal("15.00"), new BigDecimal("38.00"));

                Ingrediente cafe = new Ingrediente(1L, "Café arábica", "Moler fino antes de la extracción");
                Ingrediente leche = new Ingrediente(2L, "Leche entera", "Espumar antes de servir");

                Tienda central = new Tienda(1L, "Café Central");
                central.setTelefono("555-0303");
                central.setDireccion("Av. Reforma 120, CDMX");
                central.setEmpleadoResponsable("Lucía Herrera");

                Receta espressoReceta = new Receta(1L, espresso, new BigDecimal("4.50"));
                espresso.setReceta(espressoReceta);

                Receta capuccinoReceta = new Receta(2L, capuccino, new BigDecimal("6.00"));
                capuccino.setReceta(capuccinoReceta);

                ProporcionIngrediente espressoCafe = new ProporcionIngrediente(espressoReceta, cafe, new BigDecimal("7.5"));
                ProporcionIngrediente capuccinoCafe = new ProporcionIngrediente(capuccinoReceta, cafe, new BigDecimal("7.5"));
                ProporcionIngrediente capuccinoLeche = new ProporcionIngrediente(capuccinoReceta, leche, new BigDecimal("120"));

                espressoReceta.getIngredientes().add(espressoCafe);
                capuccinoReceta.getIngredientes().add(capuccinoCafe);
                capuccinoReceta.getIngredientes().add(capuccinoLeche);

                cafe.getRecetas().add(espressoCafe);
                cafe.getRecetas().add(capuccinoCafe);
                leche.getRecetas().add(capuccinoLeche);

                Paso pasoEspresso = new Paso(1L, espressoReceta, "Compacta el café y extrae durante 25 segundos.");
                espressoReceta.getPasos().add(pasoEspresso);

                Paso pasoCapuccino1 = new Paso(2L, capuccinoReceta, "Prepara un espresso como base.");
                Paso pasoCapuccino2 = new Paso(3L, capuccinoReceta, "Vierte la leche espumada sobre el espresso.");
                capuccinoReceta.getPasos().add(pasoCapuccino1);
                capuccinoReceta.getPasos().add(pasoCapuccino2);

                Inventario inventarioEspresso = new Inventario(central, espresso, 18);
                Inventario inventarioCapuccino = new Inventario(central, capuccino, 12);
                central.getInventario().add(inventarioEspresso);
                central.getInventario().add(inventarioCapuccino);
                espresso.getInventarios().add(inventarioEspresso);
                capuccino.getInventarios().add(inventarioCapuccino);

                ProductoVendido ventaAna = new ProductoVendido(
                        central,
                        espresso,
                        ana,
                        LocalDateTime.now().minusDays(2),
                        espresso.getPrecioVenta()
                );
                ProductoVendido ventaMarco = new ProductoVendido(
                        central,
                        capuccino,
                        marco,
                        LocalDateTime.now().minusDays(1),
                        capuccino.getPrecioVenta()
                );
                central.getVentas().add(ventaAna);
                central.getVentas().add(ventaMarco);
                espresso.getVentas().add(ventaAna);
                capuccino.getVentas().add(ventaMarco);
                ana.getCompras().add(ventaAna);
                marco.getCompras().add(ventaMarco);

                IngredienteTienda cafeEnTienda = new IngredienteTienda(
                        central,
                        cafe,
                        40,
                        new BigDecimal("160.00"),
                        LocalDate.now().plusMonths(2)
                );
                IngredienteTienda lecheEnTienda = new IngredienteTienda(
                        central,
                        leche,
                        25,
                        new BigDecimal("95.00"),
                        LocalDate.now().plusWeeks(3)
                );
                central.getIngredientes().add(cafeEnTienda);
                central.getIngredientes().add(lecheEnTienda);
                cafe.getDisponibilidad().add(cafeEnTienda);
                leche.getDisponibilidad().add(lecheEnTienda);

                entityManager.persist(ana);
                entityManager.persist(marco);
                entityManager.persist(central);
                entityManager.persist(cafe);
                entityManager.persist(leche);
                entityManager.persist(espresso);
                entityManager.persist(capuccino);
                entityManager.persist(espressoReceta);
                entityManager.persist(capuccinoReceta);
                entityManager.persist(espressoCafe);
                entityManager.persist(capuccinoCafe);
                entityManager.persist(capuccinoLeche);
                entityManager.persist(pasoEspresso);
                entityManager.persist(pasoCapuccino1);
                entityManager.persist(pasoCapuccino2);
                entityManager.persist(inventarioEspresso);
                entityManager.persist(inventarioCapuccino);
                entityManager.persist(ventaAna);
                entityManager.persist(ventaMarco);
                entityManager.persist(cafeEnTienda);
                entityManager.persist(lecheEnTienda);
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
