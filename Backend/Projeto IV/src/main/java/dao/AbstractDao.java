package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.Task;
import entity.User;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDao<T extends Serializable> implements Serializable  {

	private static final long serialVersionUID = 1L;

private final Class<T> clazz;
	
	@PersistenceContext(unitName = "PersistenceUnit")
	protected EntityManager em;

	
	
	
	public AbstractDao(Class<T> clazz){
		this.clazz = clazz;
	}

	
	public T find(Object id){
		return em.find(clazz, id);
	}
	 

	//persistir os objetos na BD
	public void persist(final T entity){
		em.persist(entity);
	}
	
	
	public void merge(final T entity){
		em.merge(entity);
	}
	
	public void remove(final T entity){
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}
	

	public List<T> findAll(){
		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		return em.createQuery(criteriaQuery).getResultList();
	}

	public void deleteAll(){
		final CriteriaDelete<T> criteriaDelete = em.getCriteriaBuilder().createCriteriaDelete(clazz);
		criteriaDelete.from(clazz);
		em.createQuery(criteriaDelete).executeUpdate();
	}
	
	public void flush() {
		em.flush();
	}
	
	public User getUserByName(String username) {
		
		User user = new User();
		// Query for a List of objects.
		try {
			Query query = em.createQuery("SELECT u FROM User u WHERE u.username like :username");
			query.setParameter("username", "%" + username + "%");
			user = (User) query.getSingleResult();
			
			System.out.println(user.getEmail());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public Task getTaskById(String taskId) {
		Task activity = (Task) em.createNamedQuery("Task.findTaskById").setParameter("id", taskId)
				.getSingleResult();
		return activity;
	}
	public User getUserByUsernameXX(String username) {
		User user = (User) em.createNamedQuery("User.findUserByUsername").setParameter("username", username)
				.getSingleResult();
		return user;
	}
}
