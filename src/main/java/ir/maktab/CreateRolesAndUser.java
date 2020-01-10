package ir.maktab;

import ir.maktab.entities.db1.Address;
import ir.maktab.entities.db1.Role;
import ir.maktab.entities.db1.User;
import ir.maktab.entities.db1.enums.RoleType;
import ir.maktab.entities.db2.Category;
import ir.maktab.repositories.db1.*;
import ir.maktab.repositories.db2.*;
import ir.maktab.share.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class CreateRolesAndUser {
    static UserRepository userRepository = UserRepository.getInstance();
    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    static CategoryRepository categoryRepository = CategoryRepository.getInstance();
    static TagRepository tagRepository = TagRepository.getInstance();
    static RoleRepository roleRepository = RoleRepository.getInstance();

    public static void main(String[] args) {


//
//        Role role1 = new Role(null, RoleType.Admin);
//        Role role2 = new Role(null,RoleType.Writer);
//        Address address = new Address(null,"tehran","tehran","hengam","saba","1677865324",null);
//        roleRepository.save(role1);
//        roleRepository.save(role2);
//        List<Role> list = new ArrayList<>();
//        list.add(role1);
//        list.add(role2);
//
//        Predicate<User> predicate = user -> user.getAddress().getCity().equals("tehran");
//        System.out.println(userRepository.findAll(predicate));
//        User user1 = new User(null,"hassan_shokoohi","0078720354","1365",
//                "0078720354",address,list);
//
//        address.setUser(user1);
//        userRepository.save(user1);
//
//        Category category = new Category(null,"java","programming",null);
//        categoryRepository.save(category);
    }
}
