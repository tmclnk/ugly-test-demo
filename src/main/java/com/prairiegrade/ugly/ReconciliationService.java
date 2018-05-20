package com.prairiegrade.ugly;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ReconciliationService {
	private EntityManager em;
	
	public ReconciliationService(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT);
		em = emf.createEntityManager();	
	}
	
}
