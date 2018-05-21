package com.prairiegrade.ugly;

import javax.persistence.EntityManager;

import com.prairiegrade.ugly.entity.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo service class which is pure-side effect.  This one
 * is very simple, and may not accurately capture what I'm
 * trying to demonstrate, which is that you sometimes
 * have to assert based on deep knowledge of a database (or some
 * other external thing) in order to get a decent automated
 * integration test.
 */
public class ReconciliationService {
    private static final Logger logger = LoggerFactory.getLogger(ReconciliationService.class);
    private final EntityManager em;

    public ReconciliationService(EntityManager em){
        this.em = em;
    }

    /**
     * Does some arbitrary stuff 
     */
    public void bankrupt(Account account){
        switch(account.getAccountType()){
            case CHECKING:
                logger.debug("Skipping {}", account.getId());
                break;
            case SAVINGS:
                // just side effects, ug!
                logger.debug("Setting of {} to 0", account.getId());
                account.setBalance(0L);
                break;
        }
        em.persist(account);
    }
    
}
