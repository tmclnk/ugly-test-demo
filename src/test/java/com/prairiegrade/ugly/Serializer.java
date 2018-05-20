package com.prairiegrade.ugly;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.prairiegrade.ugly.entity.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dumps some rows into the database, which we can use for testing in our unit
 * test.
 */
public class Serializer {
    private static final Logger logger = LoggerFactory.getLogger(Serializer.class);

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        // create a place to store all this test data
        Path dir = Paths.get("").resolve("data");
        Files.createDirectories(dir);

        try {
            TestHarness harness = new TestHarness(em);
            em.createQuery("from Account", Account.class).getResultList().forEach(account -> {
                String filename = String.format("%s.%s.json", account.getClass().getSimpleName(), account.getId());

                Path file = dir.resolve(filename);
                try {
                    Files.delete(file);
                    harness.transform(Account.class, account.getId(), System.out);
                } catch(IOException e){
                    
                }
            });

            logger.debug("Done inserting, cleaning up...");
        } finally {
            em.close();
            emf.close();
        }
    }
}
