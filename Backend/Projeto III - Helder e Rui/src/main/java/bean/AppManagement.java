package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dao.CategoryDao;
import dao.TaskDao;
import dao.UserDao;
import dto.TaskDto;
import dto.UserDto;
import entity.LoginRequestPojo;
import entity.Task;
import entity.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppManagement implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	UserDao userDao;
	@EJB
	TaskDao taskDao;
	@EJB
	CategoryDao categoryDao;

	
	// LOGOUT
	public boolean logout(String token) {
		UserBean userBean = new UserBean();
		User u = userDao.findUserByToken(token);

		if (u != null) {

			u.setToken(null);
			userDao.merge(u);
			return true;
		}
		return false;

	}

	public boolean checkSessionTime(String token) {
		User user = userDao.findUserByToken(token);

		boolean res = false;
		if (user != null) {
			long sessionTimeOut;
			long currentTime = Calendar.getInstance().getTimeInMillis();

			if (user.getTimeSessionLoged() != 0) {
				sessionTimeOut = user.getTimeSessionLoged();
			} else {
				sessionTimeOut = 0;
			}

			if (sessionTimeOut != 0 && sessionTimeOut > currentTime) {
				res = true;
			}

			if (!res) {
				user.setTimeSessionLoged(0);
				userDao.merge(user);
			}
		}
		return res;
	}

	public void updateSessionTime(User user) {
		if (user != null) {
			long newTimeOut = user.createTimeSession();
			user.setTimeSessionLoged(newTimeOut);
			userDao.merge(user);
		}

	}

	// verificar se o user logado é o mesmo que efetua o pedido
	public String authenticateUser(String token) {
		String response;

		if (token.isEmpty() || token == null || token.isBlank()) {
			response = "401";
		} else {

			boolean sessionTime = checkSessionTime(token); // verifica se o session Time está ativo
			User logedUser = userDao.findUserByToken(token);

			if (!sessionTime) {
				response = "401";
			}

			// User logedUser = userDao.findUserByToken(token);

			if (logedUser != null) {
				response = "200";
			} else {
				response = "403";
			}
		}
		return response;
	}

	public TaskDto convertEntityTaskToDTO(Task task) {
		
		if (task!=null) {
		// converte as tarefas do formato Entity para DTO
		TaskDto element = new TaskDto();
		element.setTitle(task.getTitle());
		element.setDetails(task.getDetails());
		element.setCategoryId(task.getId());
		element.setId(task.getId());
		element.setDone(task.getDone());
		element.setFinishTime(task.getFinishTime());
		element.setAlert(task.isAlert());
		element.setDeadline(task.getDeadline());
		element.setCreationDate(task.getCreationDate());

		return element;
		}
		return null;
		
	}

	public User convertUserDtoToEntity(UserDto userDTO) {
		
		if(userDTO!=null) {
		
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setPhone(userDTO.getPhone());
		user.setPhotoUrl(userDTO.getPhotoUrl());

		return user;
		}
		return null;
	}

	public UserDto convertUserEntityToDto(User userEntity) {
		
		if(userEntity!=null) {
		UserDto user = new UserDto();
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());
		user.setEmail(userEntity.getEmail());
		user.setPhone(userEntity.getPhone());
		user.setPhotoUrl(userEntity.getPhotoUrl());

		return user;
		}
		return null;
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

}
