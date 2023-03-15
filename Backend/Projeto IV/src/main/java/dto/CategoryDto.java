package dto;

public class CategoryDto {

	private String title;
	private long categoryId;

	public CategoryDto(String title) {
		this.title = title;
	}

	public CategoryDto(String title, long id) {
		this.title=title;
		this.categoryId=id;
		
	}
	public CategoryDto() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

}
