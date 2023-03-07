package dao;

import entity.SessionLogin;
import entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

@Stateless
public class SessionDao extends AbstractDao<SessionLogin> {

	private static final long serialVersionUID = 1L;

	public SessionDao() {
		super(SessionLogin.class);
	}

	public User findUserById(long userid) {
		SessionLogin ent = null;
		User userSession = null;

		try {

			ent = (SessionLogin) em.createNamedQuery("SessionLogin.findTokenById", SessionLogin.class).setParameter("userid", userid)
					.getSingleResult();

			userSession = ent.getSessionOwner();

		} catch (NoResultException e) {
			ent = null;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

		return userSession;
	}
public SessionLogin findSessionByToken(String token) {
	SessionLogin session = null;

	try {

		session = (SessionLogin) em.createNamedQuery("SessionLogin.findSessionByToken", SessionLogin.class).setParameter("token", token)
				.getSingleResult();
	
	} catch (NoResultException e) {
		session = null;
	} catch (Exception e) {

		e.printStackTrace();
		return null;
	}

	return session;
}
	/*
public SessionLogin findSessionByUsername(String username) {
	SessionLogin session = null;

	try {

		session = (SessionLogin) em.createNamedQuery("SessionLogin.findSessionByUsername", SessionLogin.class).setParameter("username", username)
				.getSingleResult();
	
	} catch (NoResultException e) {
		session = null;
	} catch (Exception e) {

		e.printStackTrace();
		return null;
	}

	return session;
}
	
*/
	/*public void persistToken(String token, long userId) {
		Session ent = null;
		try {
			
	Session.findSessionByUsername		
			
			ent = userDao.findUserByUsername(username);
			ent.setToken(token);

			merge(ent);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/
	
	public void persistSession(SessionLogin newSession) {
		try {
			this.persist(newSession);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
