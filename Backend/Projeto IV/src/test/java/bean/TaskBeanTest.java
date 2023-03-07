package bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.CategoryDao;
import dao.SessionDao;
import dao.TaskDao;
import dao.UserDao;
import dto.TaskDto;
import entity.Category;
import entity.SessionLogin;
import entity.Task;
import entity.User;

class TaskBeanTest {

	TaskBean taskBean;

	AppManagement appBean;
	TaskDao taskDao;
	UserDao userDao;
	CategoryDao categoryDao;

	User user;
	Task task;
	Category category;

	TaskDto taskDto;
	
	SessionLogin sessionLogin;
	SessionDao sessionDao;

	@BeforeEach
	void setUp() {
		taskBean = new TaskBean();

		user = new User("johndoe", 2L,"John", "Doe", "johndoe@mail.com",
				"+3519100000", "http//localhost:8080/photo", "yes");

		sessionLogin=new SessionLogin(user);
		category = new Category("Unit test", user);

		task = new Task("Unit test", "Create unit tests", "25/03/2023 15:30", category, 999L, "03/03/2023 15:30", false,
				Duration.ofMinutes(500000L));

		taskDto = new TaskDto("Unit test", "Create unit tests", "25/03/2023 15:30", 1, 999L, "03/03/2023 15:30", false,
				123L);

		sessionDao=mock(SessionDao.class);
		taskBean.setSessionDao(sessionDao);
		
		userDao = mock(UserDao.class);
		taskBean.setUserDao(userDao);
		taskDao = mock(TaskDao.class);
		taskBean.setTaskDao(taskDao);
		categoryDao = mock(CategoryDao.class);
		taskBean.setCategoryDay(categoryDao);
		appBean = mock(AppManagement.class);
		taskBean.setAppBean(appBean);

	}

	@AfterEach
	void tearDown() {
		this.taskBean = null;
	}

	@Test
	void create_new_task() {
		// Given
		TaskDto newTaskDto = taskDto;
		User newTaskUser = user;

		// When
		when(categoryDao.getCategoryById(anyLong())).thenReturn(category);
		Task newTask = taskBean.createTask(newTaskDto, newTaskUser);

		// Then
		assertNotNull(newTask);
		assertEquals(task.getDetails(), newTask.getDetails());
		assertEquals(task.getTitle(), newTask.getTitle());
		assertEquals(task.getCategory().getId(), newTask.getCategory().getId());

		verify(categoryDao, times(1)).getCategoryById(anyLong());
	}

	@Test
	void delete_task() {
		// Given
		Task t = task;

		// When
		doNothing().when(taskDao).merge(any());

		// Then
		assertNotNull(t);
	}

	@Test
	void test_remove_task() {
		taskDao.deleteAll();
		assertEquals(0, taskDao.getAllTasks().size(), "It is expected to have no activity in the list!");
		taskDao.persist(task);
		taskDao.merge(task);
		// assertEquals(1, taskDao.getAllTasks().size(), "It is expected to have one
		// activity in the list!");
		taskDao.remove(task);
		assertEquals(0, taskDao.getAllTasks().size(), "It is expected to have no activity in the list!");
	}

	@Test
	void test_remove_User_task_fail_by_null() {

		when(taskDao.findTaskById(task.getId())).thenReturn(null);

		// assertTrue(taskBean.removeUserTask(task.getId(),
		// task.getOwnerTask().getOwnerUser().getUserId(), true));
		assertFalse(taskBean.removeUserTask(task.getId(), task.getOwnerTask().getOwnerUser().getUserId(), true));
	}

	@Test
	void test_remove_Task_with_diferent_userId() {

		when(taskDao.findTaskById(task.getId())).thenReturn(task);
		assertFalse(taskBean.removeUserTask(task.getId(), 30, true));
	}


	@Test
	void test_update_title_task() {
		String newTitle = "novo Titulo na tarefa";
		task.setTitle(newTitle);
		assertEquals(newTitle, task.getTitle(), "it's expected to have an updated value in task Title");
	}

	@Test
	void test_update_Status_task() {

		when(taskDao.findTaskById(task.getId())).thenReturn(task);
		assertTrue(taskBean.updateTaskStatus(task.getId(), user.getUserId()));
	}

	@Test
	void test_update_Status_task_when_task_is_null() {

		when(taskDao.findTaskById(task.getId())).thenReturn(null);
		assertFalse(taskBean.updateTaskStatus(task.getId(), user.getUserId()));
	}

	@Test
	void test_addTask_when_user_is_null() {
		
		when(sessionDao.findSessionByToken(sessionLogin.getToken())).thenReturn(null);
		when(categoryDao.getCategoryById(category.getId())).thenReturn(category);

		doNothing().when(appBean).updateSessionTime(sessionLogin.getToken());
		taskDto.setCategoryId(category.getId());

		assertFalse(taskBean.addTask(taskDto,sessionLogin.getToken()));
	}
	@Test
	void test_addTask_when_category_is_null() {
		
		when(sessionDao.findSessionByToken(sessionLogin.getToken())).thenReturn(sessionLogin);
		when(categoryDao.getCategoryById(category.getId())).thenReturn(null);

		doNothing().when(appBean).updateSessionTime(sessionLogin.getToken());
		taskDto.setCategoryId(category.getId());

		assertFalse(taskBean.addTask(taskDto, sessionLogin.getToken()));
	}
	@Test
	void test_addTask_when_user_is_deleted() {
		
		when(sessionDao.findSessionByToken(sessionLogin.getToken())).thenReturn(sessionLogin);
		when(categoryDao.getCategoryById(category.getId())).thenReturn(category);
		user.setState("inativa");
		
		doNothing().when(appBean).updateSessionTime(sessionLogin.getToken());
		taskDto.setCategoryId(category.getId());

		assertFalse(taskBean.addTask(taskDto, sessionLogin.getToken()));
	}
	@Test
	void test_addTask_when_categoryId_doesnt_exists() {
		
		when(sessionDao.findSessionByToken(sessionLogin.getToken())).thenReturn(sessionLogin);
		when(categoryDao.getCategoryById(category.getId())).thenReturn(category);
		
		doNothing().when(appBean).updateSessionTime(sessionLogin.getToken());
		
		taskDto.setCategoryId(15);

		assertFalse(taskBean.addTask(taskDto,sessionLogin.getToken()));
	}
	
	@Test
	void test_get_UserTasks() {
		List<Task> allTasks=new ArrayList<Task>();
		when(taskDao.findAll()).thenReturn(allTasks);
		assertNotNull(taskBean.getUserTasks(user.getUserId()));
	}
	
	
}
