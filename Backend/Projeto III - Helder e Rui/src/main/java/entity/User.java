package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="User")
@NamedQuery(name = "User.findUsersByRole", query = "SELECT u FROM User u WHERE u.admin = :role")
@NamedQuery(name = "User.findUserByToken", query = "SELECT DISTINCT u FROM User u WHERE u.token = :token")
@NamedQuery(name = "User.findUserByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
@NamedQuery(name = "User.findUserById", query = "SELECT u FROM User u WHERE u.userId = :userId")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="userId", nullable=false, unique = true, updatable = false)
	private long userId;
	
	@Column(name="username", nullable=false, unique = false, updatable = true)
	private String username;
	
	@Column(name="password",nullable=false, unique = false, updatable = true)
	private String password;
	
	@Column(name="first Name", nullable=false, unique = false, updatable = true)
	private String firstName;
	
	@Column(name="last Name", nullable=false, unique = false, updatable = true)
	private String lastName;
	
	@Column(name="email", nullable=false, unique = true, updatable = true)
	private String email;
	
	@Column(name="phone", nullable=false, unique = false, updatable = true)
	private String phone;
	
	@Column(name="photoUrl", nullable=false, unique = false, updatable = true)
	private String photoUrl;
	
	@Column(name="token", nullable=true, unique = true, updatable = true)
	private String token=null;

	@Column(name="credencias admin",nullable=false, unique = false, updatable = true )
	private String admin="no";
	
	@Column(name="estado da Conta",nullable=false, unique = false, updatable = true )
	private String state="ativa";
	
	@OneToMany(mappedBy = "ownerUser", fetch = FetchType.EAGER)
	private List<Category> userListCategory=new ArrayList<Category>();
	//private List <Task> userTaskList=new ArrayList<>();

	private long timeSessionLoged; 
	
	
	public User() {}

	// cria construtor parametrizado para o objeto User
	public User(String username,long userId, String password, String firstName, String lastName, String email, String phone, String photoUrl) {
		this.username = username;
		this.userId=userId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.photoUrl = photoUrl;
		//this.token=token;
	}
	
	public User(String username,long userId, String password, String firstName, String lastName, String email, String phone, String photoUrl,String admin) {
		this.username = username;
		this.userId=userId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.photoUrl = photoUrl;
		this.admin=admin;
		//this.token=token;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getUserId() {
		return userId;
	}

	public List<Category> getUserListCategory() {
		return userListCategory;
	}

	public void setUserListCategory(Category newCategory) {
		this.userListCategory.add(newCategory);
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getToken() {
		return token;
	}

	public String createToken() {
		long time=System.currentTimeMillis();
		String timeString=username+time;
		this.token = timeString;
		return token;
	}
	
	public void setToken(String token) {
		this.token=token;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public long createTimeSession() {
		final int timeOutSession = 300000; // 5min em miliseg.
		long timeSession = Calendar.getInstance().getTimeInMillis() + timeOutSession;
		
		return timeSession;
	}

	public long getTimeSessionLoged() {
		return timeSessionLoged;
	}

	public void setTimeSessionLoged(long timeSessionLoged) {
		this.timeSessionLoged = timeSessionLoged;
	}
	
	
	

	
	
	
}
