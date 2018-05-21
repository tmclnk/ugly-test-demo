package com.prairiegrade.ugly;

import java.util.Random;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.prairiegrade.ugly.entity.Account;
import com.prairiegrade.ugly.entity.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dumps some random rows into the database, which we can use for testing in our
 * unit test.
 */
public class SeedApp {
	private static final Logger logger = LoggerFactory.getLogger(SeedApp.class);
	
	public static void main(String[] args) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT);
		EntityManager em = emf.createEntityManager();	

		Random rand = new Random();
	
		Long count = em.createQuery("select count(1) from com.prairiegrade.ugly.entity.Account", Long.class).getSingleResult();
		logger.info("{} EXISTING ROWS", count);
	
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		IntStream.range(0, 10).forEach(i ->{
			logger.debug("Creating person {}", i);
			Person person = new Person();
			person.setFirstName("FirstName" + String.format("%04d", i));
			person.setLastName("LastName" + String.format("%04d", i));
			
			Account account = new Account();
			account.setBalance(rand.nextLong());
		
			person.getAccounts().add(account);
			account.setOwner(person);
			
			em.persist(person);
			em.persist(account);
		});
		
		tx.commit();
		
		logger.debug("Done inserting, cleaning up...");
		em.close();
		emf.close();
	}
}
