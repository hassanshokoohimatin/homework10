package ir.maktab.features.usermanagement.usecase;

import ir.maktab.entities.db1.User;
import ir.maktab.entities.db1.enums.RoleType;
import ir.maktab.share.UserInfo;

public interface Dashboard {

    void dashboard(UserInfo userInfo , RoleType roleType);
}
