package dao;

import entity.Category;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
public class CategoryDao  extends AbstractDao<Category>{

	private static final long serialVersionUID = 1L;
	
	
	public CategoryDao() {
		super(Category.class);
	}

	public Category getCategoryById(long id) {
		Category category = new Category();

		// Query for a List of objects.
		try {
			System.out.println("get Category by Id - Teste");
			category = em.createNamedQuery("Category.findCategoryById",Category.class)
					.setParameter("id",id)
					.getSingleResult();
			System.out.println(category.getTitle());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}
	
	public void persistNewCategory(Category newCategory) {
		try {
			this.persist(newCategory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
