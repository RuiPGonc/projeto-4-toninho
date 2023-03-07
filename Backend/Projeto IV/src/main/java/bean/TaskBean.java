package bean;

import java.io.Serializable;
import java.time.Duration;
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

	// ESPECIFICAÇÃO U5 - adicionar nova tarefa
	public Task createTask(TaskDto newTask, User u) {

		// obtido diretamente do user
		String title = newTask.getTitle();
		String details = newTask.getDetails();
		String deadline = newTask.getDeadline();

		boolean alert = newTask.isAlert();
		Duration timeReminder = newTask.getTimeReminder();

		// gerado
		long id = newTask.getId();
		String creationDate = newTask.getCreationDate();

		long categoryId = newTask.getCategoryId();

		Category category = categoryDao.getCategoryById(categoryId);

		if (category == null) {
			return null;
		}

		return (new Task(title, details, deadline, category, id, creationDate, alert, timeReminder));
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

			if (u.getState().equals("inativa")) {
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
			if (u.getState().equals("inativa")) {
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
		boolean admin = logUser.getAdmin().equals("yes");

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
	public ArrayList<HashMap<String, String>> getTaskList(String token, long userId) {
		User uLoged = userDao.findUserByToken(token);
		long uLogedId = uLoged.getUserId();
		boolean admin = uLoged.getAdmin().equals("yes");

		if (userId == uLogedId || admin || userId == 0) {
			if (userId == 0) {
				userId = uLoged.getUserId();
			}
			appBean.updateSessionTime(token); // atualiza o session time

			List<Task> userList = getUserTasks(userId);
			// taskDao.findAll();

			ArrayList<HashMap<String, String>> taskHash = convertToHask(userList);

			return taskHash;
		}
		return null;

	}

	public ArrayList<HashMap<String, String>> convertToHask(List<Task> taskList) {
		ArrayList<HashMap<String, String>> ohMyGod = new ArrayList<>();

		for (Task task : taskList) {

			HashMap<String, String> taskHash = new HashMap<>();
			taskHash.put("Creation Date", task.getCreationDate());
			taskHash.put("Deadline", task.getDeadline());
			taskHash.put("Done", task.getDone());
			taskHash.put("Details", task.getDetails());
			taskHash.put("Category", task.getCategoryTitle());
			taskHash.put("title", task.getTitle());

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

			if (u.getState().equals("inativa")) {
				return false;
			}

			appBean.updateSessionTime(token); // atualiza o session time

			t.setTitle(task.getTitle());
			t.setDetails(task.getDetails());
			t.setDeadline(task.getDeadline());
			t.setAlert(task.isAlert());
			t.setDone(task.getDone());
			t.setCreationDate(task.getCreationDate());
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
			taskData.setDeadline(t.getDeadline());
			// taskData.setCategory(t.getCategory());
			taskData.setAlert(t.isAlert());
			taskData.setCreationDate(t.getCreationDate());
			taskData.setDone(t.getDone());
			taskData.setFinishTime(t.getFinishTime());

			return taskData;
		}

		return null;
	}

// ESPECIFICAÇÃO U5 - adicionar nova tarefa
	public boolean addTask(TaskDto newTask, String token) {
		User logedUser = userDao.findUserByToken(token);

		if (logedUser != null && logedUser.getState().equals("ativa")) {

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
	public boolean updateTaskStatus(long taskId, long userId) {

		Task t = taskDao.findTaskById(taskId);
		if (t != null) {
			if (t.getOwnerTask().getOwnerUser().getUserId() == userId) {
				if (t.getDone().equals("no")) {
					t.setDone("yes");
					t.createFinishtime();
					taskDao.merge(t);
					return true;
				} else {
					t.setDone("no");
					t.setFinishTime("0");
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
		this.sessionDao=sessionDao;
		
	}

}