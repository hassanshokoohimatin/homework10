package ir.maktab.repositories.db1;

import ir.maktab.config.CrudRepository;
import ir.maktab.config.TestHibernateUtil;
import ir.maktab.entities.db1.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends CrudRepository {

    @Override
    protected Class getEntityClass() {
        return User.class;
    }

    @Override
    protected Session getSession() {
        return TestHibernateUtil.getSession();
    }



    @Test
    void testSave() {

        User user = new User(null , "hassan" , "1" , "1365" , "1" , null , null);
        UserRepository.getInstance().save(user);
    }
}