package entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Task")
@NamedQuery(name = "Task.findTaskById", query = "SELECT a FROM Task a WHERE a.id = :id")
//@NamedQuery(name = "Task.findTaskListByUserId", query = "SELECT a FROM Task a WHERE a.ownerTask.getOwnerUser().getUserId() = :userId")
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	private long id;

	@Column(name = "title", nullable = false, unique = false, updatable = true)
	private String title;

	@Column(name = "details", nullable = true, unique = false, updatable = true, length = 65535, columnDefinition = "TEXT")
	private String details;

	// @CreationTimestamp
	@Column(name = "creation_date", nullable = false, unique = false, updatable = false)
	private String creationDate;
	// private Timestamp creationDate;

	@Column(name = "deadline", nullable = true, unique = false, updatable = true)
	private String deadline;

	@Column(name = "category_Title", nullable = true, unique = false, updatable = true)
	private String categoryTitle;

	@Column(name = "finishTime", nullable = false, unique = false, updatable = true)
	private String finishTime = "0";

	@Column(name = "done", nullable = false, unique = false, updatable = true)
	private String done = "no";

	@Column(name = "alert", nullable = false, unique = false, updatable = true)
	private boolean alert = false;

	@Column(name = "deletedTask", nullable = false, unique = false, updatable = true)
	private boolean deletedTask = false;

	@Column(name = "TimeReminder", nullable = true, unique = false, updatable = true)
	private Duration timeReminder;

	// Owning Side Category - Activity
	@ManyToOne
	private Category ownerTask;

	// @ManyToOne
	// private User user;
	public Task() {
	}

	public Task(String title, String details, String deadline, Category ownerTask, long id, String creationDate,
			boolean alert, Duration timeReminder) {
		this.title = title;
		this.details = details;
		this.deadline = deadline;
		this.categoryTitle = ownerTask.getTitle();
		this.id = id;
		this.alert = alert;
		this.ownerTask = ownerTask;
		this.timeReminder = timeReminder;

	
		if (creationDate == "") {
			creationDate = createCreationTime();
			this.creationDate = creationDate;
		} else {
			this.creationDate = creationDate;
		}
		if (alert && timeReminder.isZero()) {
			timeReminder = Duration.ofDays(7); // por default se o User definir Alerta mas não passar o tempo para o
												// reminder, este é definido com 7 dias
			this.timeReminder = timeReminder;
		}
	}

	public Duration getTimeReminder() {
		return timeReminder;
	}

	public void setTimeReminder(Duration timeReminder) {
		this.timeReminder = timeReminder;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long createId() {

		long time = System.currentTimeMillis();
		String timeString = time + "";
		// this.id = timeString;
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getDelete() {
		return deletedTask;
	}

	public void setDelete(boolean delete) {
		this.deletedTask = delete;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDeadline() {
		return deadline;
	}

	@PostConstruct
	public void addTaskToCategory(Task this) {
		ownerTask.setUserTaskList(this);
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Category getCategory() {
		return this.ownerTask;
	}

	public void setCategory(Category category) {
		this.ownerTask = category;
	}

	public String getCategoryTitle() {
		categoryTitle = ownerTask.getTitle();
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getDone() {
		return done;
	}

	public void setDone(String done) {
		this.done = done;
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	public Category getOwnerTask() {
		return ownerTask;
	}

	public String createFinishtime() {
		long time = System.currentTimeMillis();
		String timeString = time + "";
		this.finishTime = timeString;
		return finishTime;
	}

	public String createCreationTime() {

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime todayDate = LocalDate.now().atTime(0, 1);

		return todayDate.toString();
	}

	

}