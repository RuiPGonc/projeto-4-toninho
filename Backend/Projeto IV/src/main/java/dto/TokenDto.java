package dto;

public class TokenDto {

	private String token;
	private String status;
	
	public TokenDto(String status, String token) {
		this.token=token;
		this.status=status;
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
	
	
	
}
