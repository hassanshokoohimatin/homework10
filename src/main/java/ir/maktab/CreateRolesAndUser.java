package ir.maktab;

import ir.maktab.entities.db1.Address;
import ir.maktab.entities.db1.Category;
import ir.maktab.entities.db1.Role;
import ir.maktab.entities.db1.User;
import ir.maktab.entities.db1.enums.RoleType;
import ir.maktab.entities.db2.UserInfo;
import ir.maktab.repositories.db1.*;
import ir.maktab.repositories.db2.UserInfoRepository;

import java.util.ArrayList;
import java.util.List;

public class CreateRolesAndUser {
    static UserRepository userRepository = UserRepository.getInstance();
    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    static CategoryRepository categoryRepository = CategoryRepository.getInstance();
    static TagRepository tagRepository = TagRepository.getInstance();
    static RoleRepository roleRepository = RoleRepository.getInstance();
    static UserInfoRepository userInfoRepository = UserInfoRepository.getInstance();

    public static void main(String[] args) {

        Role role1 = new Role(null, RoleType.Admin);
        Role role2 = new Role(null,RoleType.Writer);
        Address address = new Address(null,"tehran","tehran","hengam","saba","1677865324",null);
        roleRepository.save(role1);
        roleRepository.save(role2);
        List<Role> list = new ArrayList<>();
        list.add(role1);
        list.add(role2);
        User user1 = new User(null,"hassan_shokoohi","0078720354","1365",
                "0078720354",null,address,list);

        address.setUser(user1);
        userRepository.save(user1);

        String firstName = "Hassan";
        String lastName = "Shokoohi";
        RoleType roleType = RoleType.AdminWriter;
        String city = "tehran";
        Long userId = user1.getId();

        UserInfo userInfo = new UserInfo(null,firstName,lastName,roleType,city,userId);
        userInfoRepository.save(userInfo);

        Category category = new Category(null,"java","programming",null);
        categoryRepository.save(category);
    }
}
