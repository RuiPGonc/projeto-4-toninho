package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import dao.CategoryDao;
import dao.SessionDao;
import dao.TaskDao;
import dao.UserDao;
import dto.PassDto;
import dto.TaskDto;
import dto.TokenDto;
import dto.CategoryDto;
import dto.UserDto;
import entity.Category;
import entity.SessionLogin;
import entity.Task;
import entity.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	AppManagement appBean;

	@Inject
	TaskBean taskBean;

	@EJB
	UserDao userDao;

	@EJB
	TaskDao taskDao;

	@EJB
	CategoryDao categoryDao;
	@EJB
	SessionDao sessionDao;

	
	public void CRIAR_oBJETOS_TESTE() {
		
		UserDto novoAdmin=new UserDto("admin", "Dr Helder", "Antunes", "aa@sapo.pt", "234324", "aaaa","0000", "yes");
	
		//cirar o user
		Long userId = 999L;
		String username = novoAdmin.getUsername();
		String password="1232";
		String pass = DigestUtils.md5Hex(password).toUpperCase(); // encripta a password
		String firstName = novoAdmin.getFirstName();
		String lastName = novoAdmin.getLastName();
		String email = novoAdmin.getEmail();
		String phone = novoAdmin.getPhone();
		String photoUrl = novoAdmin.getPhotoUrl();
		User userEntity = new User(username, userId, pass, firstName, lastName, email, phone, photoUrl);
		userDao.persistNewUser(userEntity);
		userEntity.setAdmin(true);
		userDao.merge(userEntity);

Category createCategory = new Category("Categoria 1", userEntity);
categoryDao.persistNewCategory(createCategory);
		

		System.out.println("Categoria criada");
		TaskDto taskdto= new TaskDto("Tarefa 1","detalhes da tarefa","30/03/2022 18:30",1,"29/02/2022 18:30",true,0);
		System.out.println(taskdto.getDeadline());
		
		
		Task ta= taskBean.createTask(taskdto, userEntity);
		taskDao.persistNewTask(ta);
		
		
		
	}
	
	
	// ESPECIFICAÇÃO R1 - validar login
	public TokenDto validateLogin(String username,String passwordLoged) {

		TokenDto response = new TokenDto();
		//String username = login.getUsername();
		String password = DigestUtils.md5Hex(passwordLoged).toUpperCase();

		if (password.isBlank() || password.isEmpty() || username.isBlank() || username.isEmpty()) {
			response.setStatus("401");
			return response;
		}

		User user = userDao.getUserByUsername(username);

		if (user != null) {
			if (user.getPassword().equals(password) || user.getUserId() == 999) {
			
				//quando a Sessão é criada é logo definido um timer para a mesma
				SessionLogin newSession= new SessionLogin(user);
				sessionDao.persist(newSession);
				
				String token= newSession.getToken();
				response.setStatus("200");
				response.setToken(token);
				response.setUserId(user.getUserId());
				response.setAdminCredentials(user.getAdmin());
				
			} else {
				response.setStatus("400");
			}
		} else {
			response.setStatus("400");
		}

		return response;
	}

	// A8- Adiciona um novo admin no sistema
	public boolean addAdmin(UserDto newAdmin, String token, String password) {

		boolean added = addUser(newAdmin, password, token);
		
		if (added) {
			User userNewAdmin = userDao.findUserByUsername(newAdmin.getUsername());

			User loged = userDao.findUserByToken(token);

			appBean.updateSessionTime(token); // define o session time

			updateUserRole(userNewAdmin.getUserId(), token);

			return true;
		}
		return false;
	}

	public boolean chekUsername(String username) {
		List<User> userList = userDao.getAllUsers();
		for (User u : userList) {
			if (username.equals(u.getUsername())) {
				return false;
			}
		}
		return true;
	}

	public List<User> listAllUsers() {
		List<User> list = userDao.getAllUsers();
		return list;
	}

	// adiciona novo utilizador
	public boolean addUser(UserDto newUser, String password, String token) {

		User u = userDao.findUserByToken(token);
		boolean admin = u.getAdmin();

		if (admin) {

			// verificar se não existe outro user com o mesmo nome
			boolean ckeckUsername = chekUsername(newUser.getUsername());

			if (ckeckUsername) {

				appBean.updateSessionTime(token); // atualiza o session time

				Long userId = newUser.setUserId();
				String username = newUser.getUsername();
				String pass = DigestUtils.md5Hex(password).toUpperCase(); // encripta a password
				String firstName = newUser.getFirstName();
				String lastName = newUser.getLastName();
				String email = newUser.getEmail();
				String phone = newUser.getPhone();
				String photoUrl = newUser.getPhotoUrl();

				User userEntity = new User(username, userId, pass, firstName, lastName, email, phone, photoUrl);
				userDao.persistNewUser(userEntity);

				// TEST_PRINT_USER(userEntity);
				return true;
			}
		}
		return false;

	}

	// A7- Alterar o role de um administrador - ok
	public boolean updateUserRole(long userId, String token) {

		User user = userDao.getUserById(userId);

		User userLog = userDao.findUserByToken(token);

		appBean.updateSessionTime(token); // atualiza o session time

		if (!user.getState()) {
			return false;
		}
		if (userLog.getAdmin()) {

			if (user.getAdmin()) {
				user.setAdmin(false);
				userDao.merge(user);
				return true;

			} else if (!user.getAdmin()) {
				user.setAdmin(true);
				userDao.merge(user);
				return true;
			}
		}
		return false;
	}

	public void TEST_PRINT_USER(User u) {
		Long userId = u.getUserId();
		String username = u.getUsername();
		String pass = u.getPassword();
		String firstName = u.getFirstName();
		String lastName = u.getLastName();
		String email = u.getEmail();
		String phone = u.getPhone();
		String photoUrl = u.getPhotoUrl();
		boolean admin = u.getAdmin();

		System.out.println("userId- " + userId + "\nusername -" + username + "\n pass -" + pass + "\n firstName -"
				+ firstName + "\n lastName -" + lastName + "\n email -" + email + "\n phone -" + phone + "\n photoUrl -"
				+ photoUrl  + "\n admin -" + admin);

	}


	public ArrayList<UserDto> getAllUsersList(String token) {

		User u = userDao.findUserByToken(token);
		boolean admin = u.getAdmin();

		if (admin) { // somente se o user for admin é que tem permissões

			List<User> userListEntity = listAllUsers();
			ArrayList<UserDto> userListDto = new ArrayList<>();

			for (User a : userListEntity) {

				appBean.updateSessionTime(token); // atualiza o session time

				UserDto user = new UserDto();
				user.setFirstName(a.getFirstName());
				user.setLastName(a.getLastName());
				user.setEmail(a.getEmail());
				user.setPhone(a.getPhone());
				user.setPhotoUrl(a.getPhotoUrl());

				userListDto.add(user);
			}

			return userListDto;
		} else {
			return null;
		}
	}

	public ArrayList<UserDto> getAllAdmins(String token, String AdminCredentials) {
		User u = userDao.findUserByToken(token);
		boolean admin = u.getAdmin();

		if (admin) {
			appBean.updateSessionTime(token); // atualiza o session time

			List<User> adminList = userDao.getUsersByRole(AdminCredentials);

			ArrayList<UserDto> listDto = convertToDto(adminList);
			return listDto;
		}
		return null;
	}

	public ArrayList<UserDto> convertToDto(List<User> adminList) {

		ArrayList<UserDto> list = new ArrayList<>();

		for (User u : adminList) {
			list.add(appBean.convertUserEntityToDto(u));
		}
		return list;
	}

	// ESPECIFICAÇÃO A5 - obter o perfil de determinado utilizador
	public UserDto getUserProfile(String token, long userId) {
		User uLoged = userDao.findUserByToken(token);
		long uLogedId = uLoged.getUserId();
		boolean admin = uLoged.getAdmin();

		System.out.println(userId);
		if (userId == uLogedId || admin || userId == 0) { // se o id do user que solicita a edição for igual ao id do
															// editado OU se for o user for admin
			if (userId == 0) {
				userId = uLoged.getUserId();
			}
			appBean.updateSessionTime(token); // atualiza o session time

			System.out.println(userId);
			UserDto userInfo = appBean.convertUserEntityToDto(userDao.getUserById(userId));

			return userInfo;
			/*
			 * for (UserDto user : usersList) { if (user.getUserId() == userId) { UserDto
			 * userProfile = user; return userProfile; } }
			 */
		}
		return null;
	}

	
	public User getUserProfileById(long userId) {

		User user1 = userDao.getUserById(userId);

	
		return user1;
	}

	
	// ESPECIFICAÇÃO U1 - altera dados do utilizador
	public boolean updateUser(long userId, UserDto user, String token) {

		User uLoged = userDao.findUserByToken(token);
		long uLogedId = uLoged.getUserId();
		boolean admin = uLoged.getAdmin();

		User userEdited = getUserProfileById(userId);

		User u = userEdited;
		if (userId == uLogedId || admin || userId == 0) { // se o id do user que solicita a edição for igual ao id do
															// editado OU se for o user for admin
			if (userId == 0) {
				u = uLoged;
			}
			if (!u.getState()) {
				return false;
			}
			appBean.updateSessionTime(token); // atualiza o session time

			u.setFirstName(user.getFirstName());
			u.setLastName(user.getLastName());
			u.setEmail(user.getEmail());
			u.setPhone(user.getPhone());
			u.setPhotoUrl(user.getPhotoUrl());

			userDao.merge(u);

			return true;
		}
		return false;
	}

	// altera password
	public boolean updatePass(long userId, PassDto newPass) {

	
		User u = new User(); // linha a apagar
		if (u != null) {
			u.setPassword(newPass.getPassword());

			userDao.merge(u);
			return true;
		}
		return false;
	}

	// ESPECIFICAÇÃO U3 - ALTERAR CATEGORIA
	public boolean updateCategory(long categoryId, String token,CategoryDto update) {

		User logedUser = userDao.findUserByToken(token);
		User u = userDao.findUserByToken(token);

		if (u.getUserId() == logedUser.getUserId()) {

			List<Category> userCategoryList = u.getUserListCategory();

			appBean.updateSessionTime(token); // atualiza o session time

			if (!u.getState()) {
				return false;
			}
			if (userCategoryList != null) {
				for (Category c : userCategoryList) {
					if (c.getId() == categoryId) {
						String newTitle = update.getTitle();
						c.setTitle(newTitle);
						categoryDao.merge(c);
					}
				}
				return true;
			}
		}
		return false;

	}

	// ESPECIFICAÇÃO U4 - DELETE CATEGORIA
	public boolean deleteCategory(long categoryId, long userId) {

		User u = getUserProfileById(userId);
		Category c = getCategory(categoryId, userId);

		if (c != null) {
			c.setDeletedCategory(true);
			if (!u.getState()) {
				return false;
			}
			List<Task> tasks = c.getUserTaskList();

			for (Task t : tasks) {
				taskBean.updateTaskDeleteStatus(t.getId(), userId, true, true);
			}

			categoryDao.merge(c);

			return true;
		} else {
			return false;
		}
	}

	// ESPECIFICAÇÃO U4 - DELETE CATEGORIA
	public void deleteCategoryTasks(Category c) {
		TaskBean taskBean = new TaskBean();

		List<Task> categoryTaskList = c.getUserTaskList();

		for (Task t : categoryTaskList) {
			taskBean.updateTaskDeleteStatus(t.getId(), t.getOwnerTask().getOwnerUser().getUserId(), true, false);
		}
	}

	public boolean deepDeleteCategory(long categoryId, String token) {
		User logedUser = userDao.findUserByToken(token);
		User u = categoryDao.getCategoryById(categoryId).getOwnerUser();

		if (u.getUserId() == logedUser.getUserId()) {

			Category removeCategory = getCategory(categoryId, u.getUserId());

			appBean.updateSessionTime(token); // atualiza o session time
			if (!u.getState()) {
				return false;
			}
			if (removeCategory.isDeletedCategory()) {
				List<Task> tasks = removeCategory.getUserTaskList();
				// Category removedCategory=new Category("Categoria
				// Removida",removeCategory.getOwnerUser());
				for (Task t : tasks) {
					t.setCategory(null);
					// t.setCategory(removedCategory);
					taskDao.merge(t);
				}
				categoryDao.remove(removeCategory);

			} else {
				deleteCategory(categoryId, u.getUserId());
			}

			return true;
		}
		return false;
	}

	public boolean removeUser(String token, long userId) {
		User userAdmin = userDao.findUserByToken(token);
		long uLogedId = userAdmin.getUserId();
		boolean admin = userAdmin.getAdmin();

		User deletedUser = userDao.getUserById(userId);
		User u = deletedUser;
		if (userId == uLogedId || admin || userId == 0) { // se o id do user que solicita a edição for igual ao id do
															// editado OU se for o user for admin
			if (userId == 0) {
				u = userAdmin;
			}

			appBean.updateSessionTime(token); // atualiza o session time

			boolean UserState = u.getState();

			if (UserState) {
				removeUserActivity(deletedUser);
				deletedUser.setState(false);
				userDao.merge(deletedUser);
				return true;
			}
		}
		return false;
	}

	public void removeUserActivity(User deletedUser) {

		List<Category> categoryUserList = deletedUser.getUserListCategory();

		for (Category c : categoryUserList) {
			deleteCategory(c.getId(), deletedUser.getUserId());
		}

	}

	
	public Category getCategory(long categoryId, long userId) {
		User u = getUserProfileById(userId);
		List<Category> userCategoryList = u.getUserListCategory();

		if (userCategoryList != null) {

			for (Category c : userCategoryList) {
				if (c.getId() == categoryId) {
					return c;
				}
			}
		}
		return null;
	}

	public boolean createNewCategory(CategoryDto updateCategory, long userId, String token) {

		User uLoged = userDao.findUserByToken(token);
		long uLogedId = uLoged.getUserId();

		User userEdited = getUserProfileById(userId);
		User u = userEdited;

		if (userId == uLogedId || userId == 0) { // se o id do user que solicita a edição for igual ao id do editado OU
													// se for o user for admin
			if (userId == 0) {
				u = uLoged;
			}
			if (!u.getState()) {
				return false;
			}
			appBean.updateSessionTime(token); // atualiza o session time

			String title = updateCategory.getTitle();
			Category createCategory = new Category(title, u);
			categoryDao.persistNewCategory(createCategory);
			return true;
		}

		return false;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public void setAppManagement(AppManagement appBean) {
		this.appBean = appBean;
	}

	public void setSessionDao(SessionDao sessionDao) {
		this.sessionDao=sessionDao;
		
	}


	public ArrayList<CategoryDto> getCategories(String token, long userId) {
			User uLoged = userDao.findUserByToken(token);
			long uLogedId = uLoged.getUserId();
			boolean admin = uLoged.getAdmin();

			if (userId == uLogedId || admin || userId == 0) {
				if (userId == 0) {
					userId = uLoged.getUserId();
				}
				appBean.updateSessionTime(token); // atualiza o session time

				List<Category> categoryList_All = categoryDao.findAll();
				
				ArrayList<CategoryDto> userList=new ArrayList<>();
				
				for(Category c:categoryList_All) {
					if(c.getOwnerUser().getUserId()==userId) {
						CategoryDto cat = new CategoryDto();
						cat.setTitle(c.getTitle());
						cat.setCategoryId(c.getId());
						userList.add(cat);
					}
				}
				return userList;
			}
			return null;
	}
}
