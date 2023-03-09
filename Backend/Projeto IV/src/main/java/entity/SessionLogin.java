package entity;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.internal.SessionOwnerBehavior;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="Session")
@NamedQuery(name = "SessionLogin.findTokenById", query = "SELECT u FROM SessionLogin u WHERE u.sessionOwner.userId = :userId")
@NamedQuery(name = "SessionLogin.findSessionByToken", query = "SELECT u FROM SessionLogin u WHERE u.token = :token")
//@NamedQuery(name = "SessionLogin.findSessionByUsername", query = "SELECT u FROM SessionLogin u WHERE u.username = :username")
public class SessionLogin implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	private long id;
	
	@Column(name="token", nullable=true, unique = true, updatable = true)
	private String token=null;
	
	@Column(name="Session_Time", nullable=true, unique = false, updatable = true)
	private long timeSessionLoged;
	
//	@Column(name="Username", nullable=false, unique = false, updatable = true)
	//private String username;
	
	@ManyToOne
	private User sessionOwner;
	
	
	public SessionLogin() {}
		
	public SessionLogin(User sessionOwner) {
		this.sessionOwner=sessionOwner;
	//	this.username=sessionOwner.getUsername();
		this.token=createToken(sessionOwner.getUsername());
		this.timeSessionLoged=createTimeSession();
	}
	
	public long getTimeSessionLoged() {
		return timeSessionLoged;
	}

	public void setTimeSessionLoged(long timeSessionLoged) {
		this.timeSessionLoged = timeSessionLoged;
	}
	public String getToken() {
		return token;
	}

	public String createToken(String username) {
		long time=System.currentTimeMillis();
		String timeString=username+time;
		this.token =  DigestUtils.md5Hex(timeString).toUpperCase();
		return token;
	}
	
	//public String getUsername() {
	//	return username;
	//}

	//public void setUsername(String username) {
	//	this.username = username;
	//}

	public void setToken(String token) {
		this.token=token;
	}

	public User getSessionOwner() {
		return sessionOwner;
	}

	public void setSessionOwner(User sessionOwner) {
		this.sessionOwner = sessionOwner;
	}
	public long createTimeSession() {
		final int timeOutSession = 300000; // 5min em miliseg.
		long timeSession = Calendar.getInstance().getTimeInMillis() + timeOutSession;
		
		return timeSession;
	}
}
