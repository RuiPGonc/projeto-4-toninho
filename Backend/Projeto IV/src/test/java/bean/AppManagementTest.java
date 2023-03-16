package bean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;

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
/*
class AppManagementTest {
	
	User user;
	
	UserDao userDao;
	
	TaskDao taskDao;
	
	CategoryDao categoryDao;
	
	Category category;
	
	Task task;
	
	TaskDto taskDto;
	
	SessionDao sessionDao;
	SessionLogin sessionLogin;
	
	AppManagement appBean= new AppManagement();
	
	
	@BeforeEach
	void setUp() {
		
		user = new User("johndoe", 2L, "John", "Doe", "johndoe@mail.com",
				"+3519100000", "http//localhost:8080/photo", "yes");
		
		sessionLogin=new SessionLogin(user);
		category = new Category("Unit test", user);
		
		task = new Task("Unit test", "Create unit tests", "25/03/2023 15:30", category, 999L, "03/03/2023 15:30", false,
				Duration.ofMinutes(500000L));
		

		taskDto = new TaskDto("Unit test", "Create unit tests", "25/03/2023 15:30", 1, 999L, "03/03/2023 15:30", false,
				123L);
		
		sessionDao=mock(SessionDao.class);
		appBean.setSessionDao(sessionDao);
		
		userDao=mock(UserDao.class);
		appBean.setUserDao(userDao);
		
		taskDao=mock(TaskDao.class);
		appBean.setTaskDao(taskDao);
		
		categoryDao=mock(CategoryDao.class);
		appBean.setCategoryDao(categoryDao);
		
		
	}

	@AfterEach
	void tearDown() {
		this.appBean=null;
	}
/*
	@Test
	void when_logout() {
		
		when(sessionDao.findSessionByToken(sessionLogin.getToken())).thenReturn(sessionLogin);
		doNothing().when(userDao).merge(user);
		
		assertTrue(appBean.logout(sessionLogin.getToken()));
	}
	
	@Test
	void when_logout_and_user_is_null() {
		
		when(sessionDao.findSessionByToken(sessionLogin.getToken())).thenReturn(null);
		//doNothing().when(userDao).merge(user);
		
		assertFalse(appBean.logout(sessionLogin.getToken()));
	}
	
	@Test
	void check_session_time_ok () {
		
		when(sessionDao.findSessionByToken(sessionLogin.getToken())).thenReturn(sessionLogin);
		assertTrue(appBean.checkSessionTime(sessionLogin.getToken()));
	}
	
	@Test
	void check_session_time_when_user_null () {
		
		when(sessionDao.findSessionByToken(sessionLogin.getToken())).thenReturn(null);
		assertFalse(appBean.checkSessionTime(sessionLogin.getToken()));
	}
	
	@Test
	void authenticateUser_OK () {
		
		when(sessionDao.findSessionByToken("same_Token")).thenReturn(sessionLogin);
		String response=appBean.authenticateUser("same_Token");
		assertEquals("200", response);
	}
	
	@Test
	void authenticateUser_when_user_one_token_different_from_another () {
		
		when(sessionDao.findSessionByToken("gsdfsdf")).thenReturn(sessionLogin);
		String response=appBean.authenticateUser("dfgdfgsfd");
		assertEquals("403", response);
	}
	
	@Test
	void authenticateUser_when_user_is_null () {
		
		when(sessionDao.findSessionByToken("gsdfsdf")).thenReturn(null);
		String response=appBean.authenticateUser("gsdfsdf");
		assertEquals("403", response);
	}
	
	@Test
	void convertEntityTaskToDto_null () {
		
		assertNull(appBean.convertEntityTaskToDTO(null));
	}
	
	@Test
	void convertUserDtoToEntity_null () {
		
		assertNull(appBean.convertUserDtoToEntity(null));
	}
	
	@Test
	void convertUserEntityToDto_null () {
		
		assertNull(appBean.convertUserEntityToDto(null));
	}
	
	
	
	


}
*/