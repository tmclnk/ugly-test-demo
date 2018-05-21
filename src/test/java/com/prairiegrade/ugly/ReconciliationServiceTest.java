package com.prairiegrade.ugly;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prairiegrade.ugly.entity.Account;
import com.prairiegrade.ugly.entity.AccountType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses canned data in data/input to test
 * the {@link ReconciliationService}.
 */
public class ReconciliationServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ReconciliationServiceTest.class);
    private EntityManager em;
    private EntityManagerFactory emf;
  
    /** Accounts assembled from JSON in setup */
    private List<Account> accounts;
    private ReconciliationService service;

    @Before
    public void setup() throws Exception {
        emf = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT);
        em = emf.createEntityManager();	

        service = new ReconciliationService(em);

        // prep all the test data by placing it into accounts variable
        Path dir = Paths.get("").resolve("data").resolve("input");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.excludeFieldsWithoutExposeAnnotation().create();
        accounts = Files.list(dir).map(input ->{
            logger.info("Importing data from {}", input);
            try {
                Reader reader = Files.newBufferedReader(input);
                return gson.fromJson(reader, Account.class);
			} catch (IOException e) {
                throw new RuntimeException("Failed to read " + input, e);
			}
        }).collect(Collectors.toList());
    }

    /**
     * Simple test which asserts, using SQL, whether or not
     * the bankrupt method did, in fact, set all {@link com.prairiegrade.ugly.entity.AccountType#SAVINGS} 
     * accounts to 0.
     */
    @Test
    public void testBankruptWithSQL() {
        // save all the Account/Person data loaded during setup()
        this.accounts.forEach( account -> {
            var owner = account.getOwner();
            logger.debug("Account {}", account);
            logger.debug("Owner {}", owner);

            // this is dumb JPA stuff which is out of scope for this
            // example... oops
            // have to manually maintain both sides of this relationship
            owner.getAccounts().add(account);
            em.persist(owner);
            em.persist(account);
        });
        em.flush();

        // bankrupt all the savings accounts!
        this.accounts.forEach(service::bankrupt);
        em.flush();

        // assert that the SAVINGS accounts are all 0 (just use SQL)
        this.accounts.forEach(account ->{
            if(account.getAccountType() == AccountType.SAVINGS){
                var query = em.createNativeQuery("select balance from Acct where id=:id");
                query.setParameter("id", account.getId());
                var result = query.getSingleResult();
                assertEquals("SAVINGS should have been 0", BigInteger.ZERO, result);
            }
        });
    }

    @After
    public void teardown(){
        em.close();
        emf.close();
    }
}
