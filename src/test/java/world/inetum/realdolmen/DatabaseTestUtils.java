package world.inetum.realdolmen;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DatabaseTestUtils {

    private static EntityManager entityManager;
    private static EntityManagerFactory emf;

    private DatabaseTestUtils() {
    }

    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("trm4j-test");
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        var emf = getEntityManagerFactory();
        if (entityManager == null) {
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }

    public static void doInTx(InTx block) {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            block.apply(entityManager);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }
}
