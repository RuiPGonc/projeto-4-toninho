package bean;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Deallocate;

import dao.CategoryDao;
import dao.SessionDao;
import dao.TaskDao;
import dao.UserDao;
import dto.TaskDto;
import dto.UserDto;
import entity.Category;
import entity.SessionLogin;
import entity.Task;
import entity.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class TaskBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	AppManagement appBean;

	@EJB
	TaskDao taskDao;
	@EJB
	UserDao userDao;

	@EJB
	CategoryDao categoryDao;
	@EJB
	SessionDao sessionDao;

	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	// ESPECIFICAÇÃO U5 - adicionar nova tarefa
	public Task createTask(TaskDto newTask, User u) {

		String title = newTask.getTitle();
		String details = newTask.getDetails();
//	System.out.println("deadline"+deadline);
		LocalDateTime deadline = convert_Deadline_ToLocalDateTime(newTask.getDeadline());
		boolean alert = newTask.isAlert();
		LocalDateTime timeReminder=null;
		if(alert) {
			timeReminder = convert_TimeReminder_ToLocalDateTime(newTask.getTimeReminder(), deadline);
		}
//System.out.println("timeReminder "+ timeReminder);
		LocalDateTime startTime = convert_StartTime_ToLocalDateTime(newTask.getStartTime());
//	System.out.println("startTime "+startTime);
		boolean done = newTask.getDone();
		LocalDateTime finishTime = null;
		if (done) {
			
			finishTime = getLocalDateTime_System().withSecond(0);
		}
		long id = newTask.getId();
		LocalDateTime creationDate = getLocalDateTime_System().withSecond(0);
		long categoryId = newTask.getCategoryId();
		Category category = categoryDao.getCategoryById(categoryId);
		if (category == null) {
			return null;
		}

		return (new Task(title, details, deadline, category, id, alert, timeReminder, done, startTime, creationDate,
				finishTime));
	}

//Especificação U4
	public void deleteTask(Task t) {
		t.setDelete(true);
		taskDao.merge(t);
	}

	// U7a- altera estado da tarefa para "apagada"
	public boolean updateTaskDeleteStatus(long taskId, long userId, boolean statusDeleted, boolean updateCategory) {

		Task t = taskDao.findTaskById(taskId);

		if (t.getOwnerTask().getOwnerUser().getUserId() == userId) {
			t.setDelete(statusDeleted);

			if (updateCategory) {
				t.setCategoryTitle("Categoria removida");
				// t.setCategory(removedCategory);
			}
			taskDao.merge(t);
			return true;
		}
		return false;
	}

	// U7- remover tarefa da base dados
	public boolean deleteUserTask(long taskId, String token) {
		User logedUser = userDao.findUserByToken(token);
		Task t = taskDao.findTaskById(taskId);
		User u = t.getOwnerTask().getOwnerUser();

		if (u.getUserId() == logedUser.getUserId()) {

			appBean.updateSessionTime(token); // atualiza o session time

			if (!u.getState()) {
				return false;
			}
			if (!t.getDelete()) {
				updateTaskDeleteStatus(taskId, u.getUserId(), true, false);

			} else {

				taskDao.remove(t);
			}

			return true;
		}
		return false;
	}

//U7a - abilitar tarefa eliminada
	public boolean abelTask(long taskId, String token) {
		User logedUser = userDao.findUserByToken(token);
		Task t = taskDao.findTaskById(taskId);
		User u = t.getOwnerTask().getOwnerUser();

		if (u.getUserId() == logedUser.getUserId()) {
			if (!u.getState()) {
				return false;
			}
			appBean.updateSessionTime(token); // atualiza o session time

			t.setDelete(false);

			taskDao.merge(t);
			return true;
		}
		return false;
	}

	public ArrayList<HashMap<String, String>> getDeletedTasks(String token) {

		User logUser = userDao.findUserByToken(token);
		boolean admin = logUser.getAdmin();

		if (admin) {
			List<Task> taskList = taskDao.getAllTasks();

			if (taskList != null) {

				appBean.updateSessionTime(token); // atualiza o session time

				ArrayList<Task> deletedList = new ArrayList<Task>();
				for (Task t : taskList) {
					if (t.getDelete()) {
						deletedList.add(t);
					}
				}
				ArrayList<HashMap<String, String>> taskss = convertToHask(deletedList);

				return taskss;
			}
		}
		return null;

	}

// ESPECIFICAÇÃO A6 - obter lista de tarefas de um determinado utilizador
	public ArrayList<TaskDto> getTaskList(String token, long userId) {
		User uLoged = userDao.findUserByToken(token);
		long uLogedId = uLoged.getUserId();
		boolean admin = uLoged.getAdmin();

		if (userId == uLogedId || admin || userId == 0) {
			if (userId == 0) {
				userId = uLoged.getUserId();
			}
			appBean.updateSessionTime(token); // atualiza o session time

			List<Task> userList = getUserTasks(userId);
			// taskDao.findAll();

			// ArrayList<HashMap<String, String>> taskHash = convertToHask(userList);

			////////////////////// teste
			ArrayList<TaskDto> lista = new ArrayList<TaskDto>();

			for (Task t : userList) {
				TaskDto element = new TaskDto();

				element.setTitle(t.getTitle());
				element.setDetails(t.getDetails());

				Category category = categoryDao.getCategoryById(t.getCategory().getId());
				String CategoryTitle = category.getTitle();
				element.setCategoryTitle(CategoryTitle);

				element.setId(t.getId());
				element.setDone(t.getDone());
				element.setAlert(t.isAlert());
				if(element.getDone()) {
				element.setFinishTime(t.getFinishTime().format(dateFormatter)); //////
				System.out.println(element.getFinishTime());}
				
				System.out.println("imprime o Deadline    - "+t.getDeadline());
				if(t.getDeadline()!=null){
				element.setDeadline(t.getDeadline().format(dateFormatter));
				System.out.println("imprime o Deadline2    - "+element.getDeadline());
				}
				
				element.setCreationDate(t.getCreationDate().format(dateFormatter)); ///////
				System.out.println("************"+t.getCreationDate());
				System.out.println("************"+element.getCreationDate());

				element.setStartTime(t.getStartTime().format(dateFormatter));
				
				System.out.println("imprime o Time Reminder    - "+t.getTimeReminder());
				if(t.getTimeReminder()!=null){
				element.setTimeReminder(t.getTimeReminder().format(dateFormatter));
				System.out.println("imprime o Time Reminder2  - "+element.getTimeReminder());
				}

				System.out.println(element.getTitle());
				System.out.println(element.getStartTime());
				System.out.println(element.getTimeReminder());
				System.out.println(element.getDone());

				// TaskDto taskConverted= appBean.convertEntityTaskToDTO(t);
				lista.add(element);
			}

			return lista;
		}
		return null;

	}

	public ArrayList<HashMap<String, String>> convertToHask(List<Task> taskList) {
		ArrayList<HashMap<String, String>> ohMyGod = new ArrayList<>();

		for (Task task : taskList) {

			HashMap<String, String> taskHash = new HashMap<>();
			taskHash.put("Creation Date", task.getCreationDate().format(dateFormatter));
			taskHash.put("Deadline", task.getDeadline().format(dateFormatter));
			taskHash.put("Done", task.getDone() + "");
			taskHash.put("Details", task.getDetails());
			taskHash.put("Category", task.getCategoryTitle());
			taskHash.put("Title", task.getTitle());
			String taskId = task.getId() + "";
			taskHash.put("Id", taskId);

			ohMyGod.add(taskHash);
		}
		return ohMyGod;
	}

	public List<Task> getUserTasks(long userId) {
		List<Task> allTasks = taskDao.findAll();
		List<Task> userList = new ArrayList<Task>();
		if (allTasks != null) {
			for (Task t : allTasks) {
				if (t.getOwnerTask().getOwnerUser().getUserId() == userId) {
					userList.add(t);
				}
			}
			return userList;
		}
		return null;
	}

// ESPECIFICAÇÃO U6- edita tarefa
	public boolean updateUserTask(String token, long taskId, TaskDto task) {

		User logedUser = userDao.findUserByToken(token);
		Task t = taskDao.findTaskById(taskId);
		User u = t.getOwnerTask().getOwnerUser();

		if (u.getUserId() == logedUser.getUserId()) {

			if (!u.getState()) {
				return false;
			}

			appBean.updateSessionTime(token); // atualiza o session time

			t.setTitle(task.getTitle());
			t.setDetails(task.getDetails());
			t.setDeadline(convert_Deadline_ToLocalDateTime(task.getDeadline()));
			t.setAlert(task.isAlert());
			t.setDone(task.getDone());
			
			if(task.getCreationDate()!=null) {
			t.setCreationDate(convert_CreationDate_ToLocalDateTime(task.getCreationDate(),t.getCreationDate()));
			}
			
			t.setDelete(task.getDelete());

			if (t.getCategory().getId() != task.getCategoryId()) {
				// nova categoria
				Category category = categoryDao.getCategoryById(task.getCategoryId());
				t.setCategoryTitle(category.getTitle());
				t.setCategory(category);
			}

			taskDao.merge(t);
			return true;

		}
		return false;
	}

// elimina tarefa de determinado utilizador
	public boolean removeUserTask(long taskId, long userId, boolean deepDelete) {

		Task t = taskDao.findTaskById(taskId);

		if (t != null) {
			if (t.getOwnerTask().getOwnerUser().getUserId() == userId) {
				if (deepDelete) {
					// remover permanentemente a tarefa
					return true;
				} else {
					t.setDelete(true);
					taskDao.merge(t);
					return true;
				}
			}
		}
		return false;
	}

//obter, por id, detalhes da tarefa do utilizador logado
	public TaskDto getTaskDetails(long userId, long taskId) {
		Task t = taskDao.findTaskById(taskId);

		TaskDto taskData = new TaskDto();

		if (t.getOwnerTask().getOwnerUser().getUserId() == userId) {
			taskData.setTitle(t.getTitle());
			taskData.setDetails(t.getDetails());
			taskData.setDeadline(t.getDeadline().format(dateFormatter));
			// taskData.setCategory(t.getCategory());
			taskData.setAlert(t.isAlert());
			taskData.setCreationDate(t.getCreationDate().format(dateFormatter));
			taskData.setDone(t.getDone());
			taskData.setFinishTime(t.getFinishTime().format(dateFormatter));

			return taskData;
		}

		return null;
	}

// ESPECIFICAÇÃO U5 - adicionar nova tarefa
	public boolean addTask(TaskDto newTask, String token) {
		User logedUser = userDao.findUserByToken(token);
		System.out.println("estou aquiaaa!!!");

		if (logedUser != null && logedUser.getState()) {

			Task taskEntity = createTask(newTask, logedUser);

			if (taskEntity != null) {

				appBean.updateSessionTime(token); // atualiza o session time

				// PRINT_TASK(taskEntity);
				taskDao.persistNewTask(taskEntity);

				return true;
			}
		}
		return false;
	}

// altera estado da tarefa
	public boolean updateTaskStatus(long taskId, String token) {

		Task t = taskDao.findTaskById(taskId);
		SessionLogin session = sessionDao.findSessionByToken(token);
		User u = session.getSessionOwner();

		if (t != null) {
			if (t.getOwnerTask().getOwnerUser().getUserId() == u.getUserId() || u.getAdmin()) {
				if (!t.getDone()) {
					t.setDone(true);
					t.setFinishTime(getLocalDateTime_System().withSecond(0));
					// t.createFinishtime();
					taskDao.merge(t);
					return true;
				} else {
					t.setDone(false);
					t.setFinishTime_null();
					taskDao.merge(t);

					return true;
				}
			}
		}
		return false;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void setCategoryDay(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public void setAppBean(AppManagement appBean) {
		this.appBean = appBean;
	}

	public void setSessionDao(SessionDao sessionDao) {
		this.sessionDao = sessionDao;

	}

	public LocalDateTime getLocalDateTime_System() {
		LocalDateTime timeNow = LocalDateTime.now().withSecond(0);
		return timeNow;
	}

	public LocalDateTime convert_CreationDate_ToLocalDateTime(String creationDate, LocalDateTime inicialCreationDate) {
		LocalDateTime convertedDateTime = null;
			try {
				convertedDateTime = LocalDateTime.parse(creationDate, dateFormatter).withSecond(0);

			} catch (DateTimeParseException dateTimeParseException) {
				System.out.println(dateTimeParseException);
				System.out.println(
						"Entrada de Data no Formato incorrecto ou em falta, será mantida a hora definida inicial");
			convertedDateTime=inicialCreationDate;
		}
		return convertedDateTime;
	}

	public LocalDateTime convert_Deadline_ToLocalDateTime(String deadline) {
		// deadline opcional. se não for preenchido fica null
		LocalDateTime convertedDateTime = null;
		if (deadline != null) {
			try {
				convertedDateTime = LocalDateTime.parse(deadline, dateFormatter).withSecond(0);
			} catch (DateTimeParseException dateTimeParseException) {
				System.out.println(dateTimeParseException);
				System.out.println("Entrada de Data no Formato incorrecto ou em falta, será assumida data e hora null");
			}
		}
		return convertedDateTime;
	}

	public LocalDateTime convert_StartTime_ToLocalDateTime(String startTime) {
		// startTime opcional. se não for preenchido fica null
		LocalDateTime convertedDateTime = getLocalDateTime_System().withSecond(0);
		if (startTime != null) {
			try {
				convertedDateTime = LocalDateTime.parse(startTime, dateFormatter).withSecond(0);

			} catch (DateTimeParseException dateTimeParseException) {
				System.out.println(dateTimeParseException);
				System.out.println(
						"Entrada de Data no Formato incorrecto ou em falta, será assumida data e hora do sistema");
			}
		}
		return convertedDateTime;
	}

	public LocalDateTime convert_TimeReminder_ToLocalDateTime(String date, LocalDateTime deadline) {
		// se Alert true-> timeReminder opcional. se não for preenchido assume a
		// Deadline. Se não houver, assume mais uma semana.
		LocalDateTime convertedDateTime = getLocalDateTime_System().withSecond(0);
		if (date == null) {
			if (deadline != null) {
				convertedDateTime = deadline;
			} else {
				convertedDateTime = convertedDateTime.plusDays(7);
			}

		} else {
			try {
				convertedDateTime = LocalDateTime.parse(date, dateFormatter).withSecond(0);
			} catch (DateTimeParseException dateTimeParseException) {
				System.out.println(dateTimeParseException);
				System.out
						.println("Entrada de Data no Formato incorrecto será assumida a data Deadline ou mais 7 dias");

				if (deadline != null) {
					convertedDateTime = deadline;
				} else {
					convertedDateTime = convertedDateTime.plusDays(7);
				}
			}

		}
		return convertedDateTime;
	}
}