package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.dialect.function.CastingConcatFunction;

import entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
public class UserDao extends AbstractDao<User> {

	private static final long serialVersionUID = 1L;

	public UserDao() {
		super(User.class);
	}

	public User findUserByToken(String token) {
		User ent = null;

		try {

			ent = (User) em.createNamedQuery("User.findUserByToken")
					.setParameter("token", token)
					.getSingleResult();

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

		return ent;
	}

	public User findUserByEmail(String email) {
		User ent = null;

		try {

			ent = (User) em.createNamedQuery("User.findUserByEmail").setParameter("email", email).getSingleResult();

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

		return ent;
	}

	public User findUserByUsername(String username) {
		User ent = null;

		try {

			ent = (User) em.createNamedQuery("User.findUserByUsername").setParameter("username", username)
					.getSingleResult();

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

		return ent;
	}

	/*public void persistToken(String token, String username) {
		User ent = null;
		try {

			ent = findUserByUsername(username);
			ent.setToken(token);

			merge(ent);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

	public void persistNewUser(User user) {
		try {
			this.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
	
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u",User.class);	
			
			users = query.getResultList();	
			
			System.out.println(users.size());
			System.out.println("Aqui");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	public List<User> getUsersByRole(String role) {
		List<User> users = new ArrayList<>();
	
		try {
			TypedQuery<User> query =em.createNamedQuery("User.findUsersByRole",User.class)
					.setParameter("admin", role);
			users=query.getResultList();
										
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public User getUserByUsername(String username) {
		User user = null;
		
		// Query for a List of objects.
		try {
			TypedQuery<User> query = em.createNamedQuery("User.findUserByUsername",User.class);
			query.setParameter("username", username);
			
			user = query.getSingleResult();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return user;
	}
	
	 
	public User getUserById(long userId) {
		User user = new User();

		// Query for a List of objects.
		try {
			TypedQuery<User> query = em.createNamedQuery("User.findUserById",User.class);
			query.setParameter("userId",userId);
			
			user = query.getSingleResult();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
