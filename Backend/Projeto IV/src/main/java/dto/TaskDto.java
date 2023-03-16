package dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Deallocate;

import dao.CategoryDao;
import jakarta.ejb.EJB;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.xml.bind.annotation.XmlElement;
import net.bytebuddy.implementation.MethodCall.WithoutSpecifiedTarget;

public class TaskDto{

@EJB
CategoryDao categoryDao;

	// ATRIBUTOS
	@XmlElement
	private String title;
	@XmlElement
	private String details;
	@XmlElement
	private long categoryId; 
	@XmlElement
	private String categoryTitle;
	@XmlElement
	private long id;
	@XmlElement
	private boolean alert;
	@XmlElement
	private boolean done;
	@XmlElement
	private String deadline;
	@XmlElement
	private String startTime;
	@XmlElement
	private String timeReminder;
	@XmlElement
	private boolean delete = false;
	@XmlElement
	private String creationDate;
	@XmlElement
	private String finishTime;
	
	// CONSTRUTORES
	public TaskDto() {
	}

	public TaskDto(String title, String details, String deadline, long categoryId, long id,
			boolean alert, String timeReminder,boolean done, String startTime) {
		//campos obrigatório
		this.title = title;
		this.categoryId = categoryId;
		this.id = id;
		this.done=done;
		this.alert = alert;	
		this.timeReminder=timeReminder;
		this.details = details;
		
		//campos facultativos
		if(startTime!="" && startTime!=null) {
			this.startTime=startTime;}
		if (deadline != "" || deadline!=null) {
			this.deadline = deadline;}
		
	}
	
	

	//Para teste
	public TaskDto(String title, String details, String deadline, long categoryId, String creationDate,
			boolean alert, long timeReminder) {	}

	public String getTimeReminder() {
		return this.timeReminder;
	}

	public void setTimeReminder(String timeReminder) {
		this.timeReminder=timeReminder;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String time) {
		this.finishTime=time;
	}

	public void setCreationDate(String time) {
		this.creationDate=time;
	}

	public boolean getDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String time) {
		this.deadline=time;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long category) {
		this.categoryId = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}


}
