package dto;

import java.text.SimpleDateFormat;
import java.time.Instant;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class UserDto {
	private String username;
	private long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String photoUrl;
	private String token;
	private String admin ="no";
	private String state="ativa";
	


	// CONSTRUTORES	
	// cria construtor vazio para o objeto User (é sempre necessário)
	public UserDto() {
	}
	
	// cria construtor parametrizado para o objeto User
	public UserDto(String username, String firstName, String lastName, String email, String phone, String photoUrl, String token, String admin) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.photoUrl = photoUrl;
		this.token=token;
		this.admin=admin;
		
	}
	
	
	
	
// GETTERS AND SETTERS	
	@XmlElement
	public String getUsername() {
		return username;	
	}
	@XmlAttribute
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@XmlAttribute
	public long getUserId() {
		return userId;
	}

	@XmlElement
	public String getFirstName() {
		return firstName;	
	}
	
	@XmlElement
	public String getLastName() {
		return lastName;	
	}
	
	@XmlElement
	public String getEmail() {
		return email;	
	}
	
	@XmlElement
	public String getPhone() {
		return phone;
	}
	
	@XmlElement
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long setUserId() {
		long time=System.currentTimeMillis();
	    this.userId=time;
		return userId;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	
}
