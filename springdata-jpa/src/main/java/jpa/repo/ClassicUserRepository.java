package jpa.repo;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jpa.domain.User;

import org.springframework.stereotype.Repository;

@Repository
public class ClassicUserRepository {

	 @PersistenceContext EntityManager em;

	 public List<User> findByFullName(String fullName) {
		 return getEntityManger()
				 .createNamedQuery("User.classicQuery", User.class)
				 .setParameter("fullName", fullName)
				 .getResultList();
	 }
	 
	 
	 private EntityManager getEntityManger() {
		 return em;
	 }
	
}
