package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class ClassicUserRepository {

	 @PersistenceContext EntityManager em;

	 public List<User> findByFullName(String fullName) {
		 return getEntityManger()
				 .createNamedQuery("User.classisQuery", User.class)
				 .setParameter("fullName", fullName)
				 .getResultList();
	 }
	 
	 
	 private EntityManager getEntityManger() {
		 return em;
	 }
	 
	
}
