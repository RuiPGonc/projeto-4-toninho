package dao;

import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.Query;

import entity.Task;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@Stateless
public class TaskDao extends AbstractDao<Task> {

	private static final long serialVersionUID = 1L;

	public TaskDao() {
		super(Task.class);
	}

	public Task findTaskById(long id) {
		Task ent = null;

		System.out.println("Aqui!! findTaskById check");
		try {

			ent = (Task) em.createNamedQuery("Task.findTaskById", Task.class).setParameter("id", id).getSingleResult();

		}catch (NoResultException e) {
			ent =null;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

		return ent;
	}

	public ArrayList<Task> findTaskBytoken(String token) {

		ArrayList<Task> list = new ArrayList<Task>();

		// Query for a List of objects.
		try {
			
			
			Query query = (Query) em.createQuery("SELECT t FROM Task t").getResultList();

			list = (ArrayList<Task>) query;

			ArrayList<Task> userList = new ArrayList<Task>();

			for (Task element : list) {
				if (element.getOwnerTask().getOwnerUser().getToken().equals(token)) {
					userList.add(element);
				}
			}
			return userList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public void persistNewTask(Task task) {
		try {
			this.persist(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<>();

		try {
			TypedQuery<Task> query = em.createQuery("SELECT u FROM Task u", Task.class);

			tasks = query.getResultList();

			System.out.println(tasks.size());
			System.out.println("Aqui");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tasks;
	}

	

}
