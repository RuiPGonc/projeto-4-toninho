package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")
@NamedQuery(name = "Category.findCategoryById", query = "SELECT a FROM Category a WHERE a.id = :id")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	private long id;
	
	@Column(name = "title", nullable = false, unique = false, updatable = true)
	private String title;
	
	@Column(name = "deletedCategory", nullable = false, unique = false, updatable = true)
	private boolean deletedCategory=false;

	@ManyToOne
	private User ownerUser;

	@OneToMany(mappedBy = "ownerTask", fetch = FetchType.EAGER)
	private List <Task> userTaskList=new ArrayList<>();


	public Category() {}
	
	public Category(String title, User ownerUser) {
		this.title=title;
		this.ownerUser=ownerUser;
	}

	@PostConstruct
	public void addCategoryToUser(Category this) {
		ownerUser.setUserListCategory(this);
	}
	
	public long getId() {
		return id;
	}

	public boolean isDeletedCategory() {
		return deletedCategory;
	}

	public void setDeletedCategory(boolean deletedCategory) {
		this.deletedCategory = deletedCategory;
	}


	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public List<Task> getUserTaskList() {
		return userTaskList;
	}

	public void setUserTaskList(Task newTask) {
		this.userTaskList.add(newTask);
	}

	
	
}
