package dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.xml.bind.annotation.XmlElement;
import net.bytebuddy.asm.Advice.This;

public class TaskDto {

	// ATRIBUTOS
	@XmlElement
	private String title;
	@XmlElement
	private String details;
	@XmlElement
	@JsonbDateFormat("dd/MM/yyyy HH:mm")
	private LocalDateTime deadline;
	@XmlElement
	private long categoryId; // a taskDto tem CategoryId, a Task tem CategoryTitle e Category ownerTask,
								// porque é a Task que pressite na BD
	@XmlElement
	private long id;
	@XmlElement
	private String done = "no";
	@XmlElement
	@JsonbDateFormat("dd/MM/yyyy HH:mm")
	private LocalDateTime creationDate;
	@XmlElement
	private boolean alert = false;
	@XmlElement
	@JsonbDateFormat("dd/MM/yyyy HH:mm")
	private LocalDateTime finishTime;
	@XmlElement
	private boolean delete = false;
	@XmlElement
	private long timeReminder;

	// CONSTRUTORES
	public TaskDto() {
	}

	public TaskDto(String title, String details, String deadline, long categoryId, long id, String creationDate,
			boolean alert, long timeReminder) {
		this.title = title;
		this.details = details;

		this.categoryId = categoryId;
		this.id = id;
		this.alert = alert;
		this.timeReminder=timeReminder;

		if (creationDate == null) {
			this.creationDate = createDate();
		} else {
			this.creationDate = convertToLocalDateTime(creationDate);
		}

		if (deadline == "0") {
			this.deadline = createDate();
			this.deadline.plusDays(7);// por default se o User não definir data deadline mas definir Alert como true o
										// deadline é definido para uma semana depois
		} else {
			this.deadline = convertToLocalDateTime(deadline);
		}

		if(alert && timeReminder==0) {
			this.timeReminder=10080;  //10 080 => minutos = 7 dias * 24h*60min
		}
	}
	
	
	
	//Para teste
	public TaskDto(String title, String details, String deadline, long categoryId, String creationDate,
			boolean alert, long timeReminder) {	}

	public LocalDateTime convertToLocalDateTime(String creationDate) {

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		String dateTrim = creationDate.replaceAll("\\s", "");

		String date = dateTrim.substring(0, 10); // primeira substring (data)
		String time = dateTrim.substring(10); // segunda substring (hora)

		LocalDate dateLocalDate = LocalDate.parse(date, dateFormatter);
		LocalDateTime dateTime;
		if (time == "") {
			dateTime = dateLocalDate.atTime(0, 0);

		} else {
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:MM");
			LocalTime localTime = LocalTime.parse(time, timeFormatter);
			dateTime = dateLocalDate.atTime(localTime);
		}

		return dateTime;
	}

	public Duration getTimeReminder() {
		Duration time= Duration.ofMinutes(this.timeReminder);
		return time;
	}

	public void setTimeReminder(long timeReminder) {
		this.timeReminder = timeReminder;
	}

	public String getCreationDate() {
		return creationDate.toString();
	}

	public LocalDateTime createDate() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDateTime todayDate = LocalDate.now().atTime(0, 1);
		return todayDate;
	}

	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String time) {
		this.finishTime = convertToLocalDateTime(time);
	}

	public void setCreationDate(String time) {
		this.creationDate = convertToLocalDateTime(time);
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
		return deadline.toString();
	}

	public void setDeadline(String time) {
		if(time=="")time="0";
		this.deadline = convertToLocalDateTime(time);
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

	public long createId() {

		long time = System.currentTimeMillis();
		String timeString = time + "";
		// this.id = timeString;
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDone() {
		return done;
	}

	public void setDone(String done) {
		this.done = done;
	}

	public LocalDateTime createFinishtime() {
		long time = System.currentTimeMillis();
		String timeString = time + "";
		LocalDateTime finishTimeLocalDate = convertToLocalDateTime(timeString);
		return finishTimeLocalDate;
	}

}
