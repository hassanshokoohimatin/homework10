package ir.maktab.repositories.db2;

import ir.maktab.config.CrudRepository;
import ir.maktab.config.HibernateUtil;
import ir.maktab.entities.db2.UserInfo;
import org.hibernate.Session;

public class UserInfoRepository extends CrudRepository<UserInfo , Long> {

    private static UserInfoRepository userInfoRepository;
    private UserInfoRepository(){}
    public static UserInfoRepository getInstance(){
        if (userInfoRepository == null)
            userInfoRepository = new UserInfoRepository();
        return userInfoRepository;
    }

    @Override
    protected Class<UserInfo> getEntityClass() {
        return UserInfo.class;
    }
    protected Session getSession() {
        return HibernateUtil.getSessionTwo();
    }
}
