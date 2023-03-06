package dto;

public class UpdateCategoryDto {

	private String title;

	public UpdateCategoryDto(String title) {
		this.title = title;
	}

	public UpdateCategoryDto() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
