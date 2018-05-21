package com.prairiegrade.ugly;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.prairiegrade.ugly.entity.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReconciliationService {
    private static final Logger logger = LoggerFactory.getLogger(ReconciliationService.class);
    private EntityManager em;
    
    public ReconciliationService(EntityManager em){
        this.em = em;
    }

    /**
     * Does some arbitrary stuff 
     */
    public void doubleSavings(Account account){
        switch(account.getAccountType()){
            case CHECKING:
                logger.debug("Skipping {}", account.getId());
                break;
            case SAVINGS:
                // just side effects, ug!
                account.setLedgerBalance(account
                    .getLedgerBalance()
                    .multiply(BigDecimal.valueOf(2)));
            break;
        }
    }
    
}
