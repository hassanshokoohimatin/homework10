package ir.maktab.repositories.db1;

import ir.maktab.config.CrudRepository;
import ir.maktab.config.HibernateUtil;
import ir.maktab.entities.db1.User;
import org.hibernate.Session;

public class UserRepository extends CrudRepository<User , Long> {

    private static UserRepository userRepository;
    private UserRepository(){}
    public static UserRepository getInstance(){
        if (userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
    protected Session getSession() {
        return HibernateUtil.getSessionOne();
    }


}
