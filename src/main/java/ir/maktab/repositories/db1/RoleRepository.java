package ir.maktab.repositories.db1;

import ir.maktab.config.CrudRepository;
import ir.maktab.config.HibernateUtil;
import ir.maktab.entities.db1.Role;
import org.hibernate.Session;

public class RoleRepository extends CrudRepository<Role, Long> {

    private static RoleRepository roleRepository;
    private RoleRepository(){}
    public static RoleRepository getInstance(){
        if (roleRepository == null)
            roleRepository = new RoleRepository();
        return roleRepository;
    }

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
    protected Session getSession() { return HibernateUtil.getSessionOne(); }
}
