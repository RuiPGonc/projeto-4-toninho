package bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import dao.CategoryDao;
import dao.SessionDao;
import dao.TaskDao;
import dao.UserDao;
import entity.LoginRequestPojo;
import entity.SessionLogin;
import entity.User;

public class UserBeanTest {

	UserBean userBean;

	UserDao userDao;
	TaskDao taskDao;
	CategoryDao categoryDao;
	AppManagement appBean;
	SessionLogin sessionLogin;
	SessionDao sessionDao;
	User user;

/*	@BeforeEach
	void setup() {
		userBean = new UserBean();

		user = new User("johndoe", 2L,"1D83D72D29B6EE42AF20FDD66346A758", "John", "Doe", "johndoe@mail.com", "+3519100000", "http//localhost:8080/photo",
				"yes");
		sessionLogin = new SessionLogin(user);
		
		sessionLogin = new SessionLogin(user);
		sessionDao = mock(SessionDao.class);
		userBean.setSessionDao(sessionDao);

		userDao = mock(UserDao.class);
		userBean.setUserDao(userDao);
 
		taskDao = mock(TaskDao.class);
		userBean.setTaskDao(taskDao);

		categoryDao = mock(CategoryDao.class);
		userBean.setCategoryDao(categoryDao);

		appBean = mock(AppManagement.class);
		userBean.setAppManagement(appBean);
	}
/*
	@AfterEach
	void clean() {
		userBean = null;
	}

	// VERIFICA MÉTODO 'addUser()' ----- NÃO ESTÁ TESTADO PARA O CASO DE INSUCESSO

	@Test
	void when_a_user_make_a_login_with_valid_username_and_password_it_should_not_null_and_return_a_session_token() {
		// Given clause
		LoginRequestPojo login = new LoginRequestPojo("johndoe", "password1");

		// when clause
		// mock interdependencies services
		when(userDao.getUserByUsername(any())).thenReturn(user);
		doNothing().when(sessionDao).persist(any());

		String token = userBean.validateLogin(login);
		
		// Then
		assertNotNull(token);
		//assertEquals(sessionLogin.getToken().toUpperCase(), token);

		//verify(userDao, times(1)).getUserByUsername(any());
		//verify(appBean, times(1)).updateSessionTime(any());
		//verify(userDao, times(1)).persist(any());
	}

	@Test
	void when_a_user_make_a_login_with_invalid_username_it_should_return_401() {
		// Given clause
		LoginRequestPojo login = new LoginRequestPojo("", "password1");

		// when clause
		String response = userBean.validateLogin(login);

		// Then
		assertNotNull(response);
		assertEquals("401", response);

		verify(userDao, times(0)).getUserByUsername(any());
		verify(appBean, times(0)).updateSessionTime(any());
		verify(userDao, times(0)).persist(any());
	}

	void when_a_user_make_a_login_with_valid_username_and_password_but_user_is_not_found_it_should__return_400() {
		// Given clause
		LoginRequestPojo login = new LoginRequestPojo("johndoe", "password1");

		// when clause
		// mock interdependencies services
		when(userDao.getUserByUsername(any())).thenReturn(null);

		String response = userBean.validateLogin(login);

		// Then
		assertNotNull(response);
		assertEquals("400", response);

		verify(userDao, times(1)).getUserByUsername(any());
		verify(appBean, times(0)).updateSessionTime(any());
		verify(userDao, times(0)).persist(any());
	}

	@Test
	void when_a_user_make_a_login_with_invalid_password_it_should_return_400() {
		// 0Given clause 
		LoginRequestPojo login = new LoginRequestPojo("johndoe", "");

		// when clause 
		String response = userBean.validateLogin(login);

		// Then 
		assertNotNull(response); 
		assertEquals("400", response);

		//verify(userDao, times(0)).getUserByUsername(any());
		//verify(appBean, times(0)).updateSessionTime(any());
		//verify(userDao, times(0)).persistToken(any());
	}

	/*
	// introdução de dados é null
	@Test
	void validationLogin_userNull() {
		
		 LoginRequestPojo lgin=null; 
		 User user = null;
		
		// when(userDao.getUserByUsername(lgin.getUsername())).thenReturn(user);
		 //String response; assertFalse(true);
		String username = "abc";
		assertNotNull(userDao.getUserByUsername(username));
	}*/
}
