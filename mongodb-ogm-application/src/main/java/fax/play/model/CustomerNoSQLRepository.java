package fax.play.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository("customerRepository")
public class CustomerNoSQLRepository {

	public List<Customer> findAll(){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ogm-jpa-tutorial" );
		EntityManager entitymanager = emfactory.createEntityManager();

		Query query = entitymanager.createQuery("SELECT c FROM Customer c");
		List<Customer> list = query.getResultList();

		for(Customer e:list) {
			System.out.println("Customer NAME :"+e.getFirstName()+" "+e.getLastName());
		}

		return list;
	}

}
