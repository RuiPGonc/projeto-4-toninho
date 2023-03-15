package dto;

public class TokenDto {

	private String token;
	private String status;
	private long userId;
	private String adminCredentials;
	
	public TokenDto(String status, String token,long userId,String adminCredentials) {
		this.token=token;
		this.status=status;
		this.userId=userId;
		this.adminCredentials=adminCredentials;
	}
	public TokenDto() {}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getAdminCredentials() {
		return adminCredentials;
	}
	public void setAdminCredentials(String adminCredentials) {
		this.adminCredentials = adminCredentials;
	}
	
	
	
}
