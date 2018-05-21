package com.prairiegrade.ugly;

import javax.persistence.EntityManager;

import com.prairiegrade.ugly.entity.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
