package service;

import java.util.ArrayList;
import java.util.HashMap;

import bean.AppManagement;
import bean.TaskBean;
import bean.UserBean;
import dto.CategoryDto;
import dto.TaskDto;
import dto.TokenDto;
import dto.UserDto;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/*Query para criar um novo Admin:
 
 INSERT INTO user (`userId`, `credencias admin`, `email`, `first Name`, `last Name`, `password`, `phone`, `photoUrl`, `estado da Conta`, `username`) 
VALUES ('999', 'yes', 'a@s.pt', 'drHelder', 'Antunes', '1232', '234324', 'aaaa', 'ativa','admin');

  */

@Path("/ToDo_app")
public class ManageService {

	@Inject
	AppManagement appManage;
	@Inject
	UserBean userBean;
	@Inject
	TaskBean taskBean;

	String failed = "Failed!";

	String unauthorized = "Unauthorized!";

	String success = "Sucess!";

	String userAlreadyRegisted = "Username already registed!";

	String forbiden = "Forbidden!";

//------------- OPERATIONS  ------------------

	//Iniciar a aplicação com dados
	@POST
	@Path("/users/start")
	@Consumes(MediaType.APPLICATION_JSON)
	public void startApp() {

		boolean testing=true;
		if(testing) {userBean.CRIAR_oBJETOS_TESTE();}
	}
	
	
	
	
	// ESPECIFICAÇÃO R1 - login
	@POST
	@Path("/users/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@HeaderParam("password")String password, @HeaderParam("username") String username ) {
		
		TokenDto loginResponse = userBean.validateLogin(username,password);
	
		String status=loginResponse.getStatus();
		switch (status) {
		case "400":
			return Response.status(400).entity(failed).build();
		case "401":
			return Response.status(401).entity(unauthorized).build();
		default:
		//	String token = loginResponse;
			return Response.status(200).entity(loginResponse).build();
		}

	}

	// logout
	@Context
	private HttpServletRequest request;

	@POST
	@Path("/users/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@HeaderParam("token") String token) {

		boolean logOut = appManage.logout(token);
		if (logOut) {
			HttpSession session = request.getSession();
			session.invalidate();

			return Response.status(200).entity(success).build();
		} else {
			return Response.status(400).entity(failed).build();
		}
	}

	// ESPECIFICAÇÃO U1 + A1 - editar perfil de utilizador
	@PUT
	@Path("/users/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(UserDto u, @HeaderParam("token") String token, @PathParam("userId") long userId) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			boolean updated = userBean.updateUser(userId, u, token);

			if (updated) {
				return Response.status(200).entity(success + "   " + token).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}

	}

	// ESPECIFICAÇÃO U2 - Adicionar uma categoria
	@POST
	@Path("/users/category/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategory(@HeaderParam("token") String token, CategoryDto updateCategory,
			@PathParam("userId") long userId) {

		String validLogin = appManage.authenticateUser(token);

		switch (validLogin) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			boolean newCategory = userBean.createNewCategory(updateCategory, userId, token);

			if (!newCategory) {
				return Response.status(400).entity(failed).build();
			} else {
				return Response.status(200).entity(success + "   " + token).build();
			}
		}
	}

	// ESPECIFICAÇÃO U3 - ALTERAR UMA CATEGORIA DE ATIVIDADES
	@PUT
	@Path("/users/categoryUpdate/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setCategory(@HeaderParam("token") String token, @PathParam("id") long id,
			CategoryDto updateCategory) {

		String validLogin = appManage.authenticateUser(token);

		switch (validLogin) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			Boolean setCategory = userBean.updateCategory(id, token, updateCategory);

			if (!setCategory) {
				return Response.status(400).entity(failed).build();
			} else {
				return Response.status(200).entity(success + "   " + token).build();
			}
		}
	}

	// ESPECIFICAÇÃO U4 - eliminar uma Categoria de determinado utilizador
	@DELETE
	@Path("/users/category/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCategory(@HeaderParam("token") String token, @PathParam("id") long id) {

		String validLogin = appManage.authenticateUser(token);

		switch (validLogin) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			Boolean deleteCategory = userBean.deepDeleteCategory(id, token);

			if (!deleteCategory) {
				return Response.status(400).entity(failed).build();
			} else {
				return Response.status(200).entity(success + "   " + token).build();
			}
		}
	}

	// ESPECIFICAÇÃO U5 - adicionar nova tarefa
	@POST
	@Path("/users/task")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTask(TaskDto a, @HeaderParam("token") String token) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			boolean added = taskBean.addTask(a, token);

			if (added) {
				return Response.status(200).entity(success + "   " + token).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}

	// ESPECIFICAÇÃO U6 - edita tarefa
	@PUT
	@Path("/users/activities/{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTask(TaskDto t, @PathParam("taskId") long taskId, @HeaderParam("token") String token) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			boolean updated = taskBean.updateUserTask(token, taskId, t);

			if (updated) {
				return Response.status(200).entity(success + "   " + token).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}

	// ESPECIFICAÇÃO U7 - eliminar uma tarefa
	@DELETE
	@Path("/users/task/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeTask(@PathParam("taskId") long taskId, @HeaderParam("token") String token) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			boolean deleted = taskBean.deleteUserTask(taskId, token);

			if (deleted) {
				return Response.status(200).entity(success + "   " + token).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}

	// ESPECIFICAÇÃO U7a - restaurar tarefa
	@PUT
	@Path("/users/task/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response abilityTask(@PathParam("taskId") long taskId, @HeaderParam("token") String token) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			boolean taskAbel = taskBean.abelTask(taskId, token);

			if (taskAbel) {
				return Response.status(200).entity(success + "   " + token).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}

	// Alterar estado de tarefa
	@POST
	@Path("/users/activities/state/{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTask(@PathParam("taskId") long taskId, @HeaderParam("token") String token) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			boolean updated = taskBean.updateTaskStatus(taskId,token);
			
			if (updated) {
				return Response.status(200).entity(success + "   " + token).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}

	// ESPECIFICAÇÃO Obter Lista de tarefas eliminadas- solicitação exclusiva do
	// admin
	@GET
	@Path("/users/deletedTasks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeletedTasks(@HeaderParam("token") String token) {

		String validLogin = appManage.authenticateUser(token);

		switch (validLogin) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			ArrayList<HashMap<String, String>> deletedList = taskBean.getDeletedTasks(token);

			if (deletedList == null) {
				return Response.status(400).entity(failed).build();
			} else {
				return Response.status(200).entity(deletedList).build();
			}
		}
	}

	// ESPECIFICAÇÃO A2 - adiciona novo utilizador
	@POST
	@Path("/users/newUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(UserDto u, @HeaderParam("token") String token, @HeaderParam("password") String password) {

		String validLogin = appManage.authenticateUser(token);

		switch (validLogin) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		default:

			// falta impedir que o user não possa escolher o mesmo username

			// não dá para introduzir mais que um user! definições de criar e atuaizar
			// tabelas??

			Boolean response = userBean.addUser(u, password, token);

			if (response) {
				return Response.status(200).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}

	// ESPECIFICAÇÃO A3 - listar todos os utilizadores (nome, apelido, foto, id)
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsersList(@HeaderParam("token") String token) {

		String validLogin = appManage.authenticateUser(token);

		switch (validLogin) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			ArrayList<UserDto> users = userBean.getAllUsersList(token);

			if (users == null) {
				return Response.status(400).entity(failed).build();
			} else {
				return Response.status(200).entity(users).build();
			}
		}
	}

	// ESPECIFICAÇÃO A4 + A10- remover um User/Admin
	@DELETE
	@Path("/users/deleted/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeUser(@HeaderParam("token") String token, @PathParam("userId") long userId) {

		String validLogin = appManage.authenticateUser(token);

		switch (validLogin) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			Boolean removeUser = userBean.removeUser(token, userId);

			if (removeUser == null) {
				return Response.status(400).entity(failed).build();
			} else {
				return Response.status(200).entity(success + " - Conta inativada!").build();

			}
		}
	}

	// ESPECIFICAÇÃO A5 - obter o perfil de um User
	@GET
	@Path("/users/info/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("userId") long userId, @HeaderParam("token") String token) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			UserDto user = userBean.getUserProfile(token, userId);

			if (user != null) {
				return Response.status(200).entity(user).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}

	// ESPECIFICAÇÃO A6 - obter lista de atividades de um utilizador
	@GET
	@Path("/users/activities/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTask(@PathParam("userId") long userId, @HeaderParam("token") String token) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			ArrayList<TaskDto> tasks = taskBean.getTaskList(token, userId);

			

			if (tasks != null) {
				return Response.status(200).entity(tasks).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}
	// ESPECIFICAÇÃO extra - obter lista de categorias de um utilizador
		@GET
		@Path("/users/categories/{userId}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getCategories(@PathParam("userId") long userId, @HeaderParam("token") String token) {

			String authenticateUser = appManage.authenticateUser(token);

			switch (authenticateUser) {
			case "401":
				return Response.status(401).entity(unauthorized).build();
			case "403":
				return Response.status(403).entity(forbiden).build();
			default:

				ArrayList<CategoryDto> categories = userBean.getCategories(token, userId);

				if (categories != null) {
					return Response.status(200).entity(categories).build();
				} else {
					return Response.status(400).entity(failed).build();
				}
			}
		}


	// A7- altera a role de um user/admin
	@PUT
	@Path("/users/userRole/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAdminUserRole(@HeaderParam("token") String token, @PathParam("userId") long userId) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:

			boolean updatedRole = userBean.updateUserRole(userId, token);

			if (updatedRole) {
				return Response.status(200).entity(success).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}

	}

	// ESPECIFICAÇÃO A8 - adiciona novo administrador
	@POST
	@Path("/users/newAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAdmin(UserDto u, @HeaderParam("token") String token, @HeaderParam("password") String password) {

		String authenticateAdmin = appManage.authenticateUser(token);

		switch (authenticateAdmin) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		default:

			Boolean response = userBean.addAdmin(u, token, password);

			if (response) {
				return Response.status(200).entity(success).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}

	// ESPECIFICAÇÃO A9 - obter lista de Admins
	@GET
	@Path("/users/allAdmins")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAdmin(@HeaderParam("token") String token) {

		String authenticateUser = appManage.authenticateUser(token);

		switch (authenticateUser) {
		case "401":
			return Response.status(401).entity(unauthorized).build();
		case "403":
			return Response.status(403).entity(forbiden).build();
		default:
			String adminRole = "yes";
			ArrayList<UserDto> tasks = userBean.getAllAdmins(token, adminRole);

			if (tasks != null) {
				return Response.status(200).entity(tasks).build();
			} else {
				return Response.status(400).entity(failed).build();
			}
		}
	}

}
