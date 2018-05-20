package com.prairiegrade.ugly;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prairiegrade.ugly.entity.Account;

public class ReconciliationServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ReconciliationServiceTest.class);
    private EntityManager em;
    private EntityManagerFactory emf;
    
    @Before
    public void setup(){
        emf = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT);
        em = emf.createEntityManager();	
    }
    @Test
    public void test() {
        em.createQuery("from Account", Account.class)
            .getResultStream()
            .forEach(account -> {
                logger.info("{}", account.getId());
            });
    }

    @After
    public void teardown(){
        em.close();
        emf.close();
    }
}
