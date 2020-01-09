package ir.maktab.repositories.db2;

import ir.maktab.config.CrudRepository;
import ir.maktab.config.HibernateUtil;
import ir.maktab.entities.db2.Category;
import org.hibernate.Session;

public class CategoryRepository extends CrudRepository<Category, Long> {

    private static CategoryRepository categoryRepository;
    private CategoryRepository(){}

    public static CategoryRepository getInstance(){
        if (categoryRepository == null)
            categoryRepository = new CategoryRepository();
        return categoryRepository;
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }
    protected Session getSession() {
        return HibernateUtil.getSessionTwo();
    }
}
