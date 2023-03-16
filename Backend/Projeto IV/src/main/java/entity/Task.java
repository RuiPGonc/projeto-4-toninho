package entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import jakarta.persistence.Transient;

@Entity
@Table(name = "Task")
@NamedQuery(name = "Task.findTaskById", query = "SELECT a FROM Task a WHERE a.id = :id")
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

	@Column(name = "done", nullable = false, unique = false, updatable = true)
	private boolean done = false;

	@Column(name = "alert", nullable = false, unique = false, updatable = true)
	private boolean alert = false;

	@Column(name = "deletedTask", nullable = false, unique = false, updatable = true)
	private boolean deletedTask = false;

	@Column(name = "creation_date", nullable = false, unique = false, updatable = false)
	private LocalDateTime creationDate;
	
	@Column(name = "deadline", nullable = true, unique = false, updatable = true)
	private LocalDateTime deadline;
	
	@Column(name = "category_Title", nullable = true, unique = false, updatable = true)
	private String categoryTitle;
	
	@Column(name = "startTime", nullable = false, unique = false, updatable = true)
	private LocalDateTime startTime;
	
	@Column(name = "finishTime", nullable = true, unique = false, updatable = true)
	private LocalDateTime finishTime;
	@Column(name = "TimeReminder", nullable = true, unique = false, updatable = true)
	private LocalDateTime timeReminder;

	@ManyToOne
	private Category ownerTask;

	
	public Task() {
	}

	public Task(String title, String details, LocalDateTime deadline, Category ownerTask, long id,
			boolean alert, LocalDateTime timeReminder,boolean done,LocalDateTime startTime,LocalDateTime creationDate, LocalDateTime finishTime) {
		this.title = title;
		this.details = details;
		this.ownerTask = ownerTask;
		this.categoryTitle = ownerTask.getTitle();
		this.id = id;
		this.alert = alert;
		this.done=done;
		this.creationDate = creationDate;
		this.startTime=startTime;
		this.deadline=deadline;
		this.timeReminder = timeReminder;
		this.done=done;
		this.finishTime=finishTime;
		
	}
	public LocalDateTime getTimeReminder() {
		return timeReminder;
	}

	public LocalDateTime getStartTime() {
		return startTime;
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public void setTimeReminder(LocalDateTime timeReminder) {
		this.timeReminder = timeReminder;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate=creationDate;
	}
	
	@PostConstruct
	public void addTaskToCategory(Task this) {
		ownerTask.setUserTaskList(this);
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline =deadline;
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

	public LocalDateTime getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(LocalDateTime time) {
		this.finishTime =time;
	}
	public void setFinishTime_null() {
		this.finishTime=null;
	}
	
	public boolean getDone() {
		return done;
	}

	public void setDone(boolean done) {
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



}