package bean;

import java.io.Serializable;
import java.util.Calendar;

import dao.CategoryDao;
import dao.SessionDao;
import dao.TaskDao;
import dao.UserDao;
import dto.TaskDto;
import dto.UserDto;
import entity.SessionLogin;
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
	@EJB
	SessionDao sessionDao;

	// LOGOUT
	public boolean logout(String token) {
		SessionLogin s = sessionDao.findSessionByToken(token);

		if (s != null) {

			s.setToken(null);
			sessionDao.merge(s);

			return true;
		}
		return false;

	}

	public boolean checkSessionTime(String token) {
		SessionLogin s = sessionDao.findSessionByToken(token);

		boolean res = false;
		if (s != null) {
			long sessionTimeOut;
			long currentTime = Calendar.getInstance().getTimeInMillis();

			if (s.getTimeSessionLoged() != 0) {
				sessionTimeOut = s.getTimeSessionLoged();
			} else {
				sessionTimeOut = 0;
			}

			if (sessionTimeOut != 0 && sessionTimeOut > currentTime) {
				res = true;
			}

			if (!res) {
				s.setTimeSessionLoged(0);
				sessionDao.merge(s);
			}
		}
		return res;
	}

	public void updateSessionTime(String token) {
		SessionLogin s = sessionDao.findSessionByToken(token);

		if (s != null) {
			long newTimeOut = s.createTimeSession();
			s.setTimeSessionLoged(newTimeOut);
			sessionDao.merge(s);
		}

	}

	// verificar se o user logado é o mesmo que efetua o pedido
	public String authenticateUser(String token) {
		String response="";

		if (token.isEmpty() || token == null || token.isBlank()) {
			response = "401";
		} else {

			boolean sessionTime = checkSessionTime(token); // verifica se o session Time está ativo
			
			SessionLogin sessionLoged = sessionDao.findSessionByToken(token);
			
			if(sessionLoged!=null) {
			
				User logedUser=sessionLoged.getSessionOwner(); 

			if (!sessionTime) {
				response = "401";
			}
			if (logedUser != null) {
				response = "200";
			}}
			
			else {
				response = "403";
			}
		}
		return response;
	}

	public TaskDto convertEntityTaskToDTO(Task task) {

		if (task != null) {
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

		if (userDTO != null) {

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

		if (userEntity != null) {
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

	public void setSessionDao(SessionDao sessionDao) {
		this.sessionDao = sessionDao;

	}

}
