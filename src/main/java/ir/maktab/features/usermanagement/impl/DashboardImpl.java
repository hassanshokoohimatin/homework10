package ir.maktab.features.usermanagement.impl;

import ir.maktab.entities.db1.User;
import ir.maktab.entities.db1.enums.IsPublished;
import ir.maktab.entities.db1.enums.RoleType;
import ir.maktab.features.usermanagement.usecase.Dashboard;
import ir.maktab.repositories.db1.UserRepository;
import ir.maktab.repositories.db2.ArticleRepository;
import ir.maktab.share.UserInfo;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class DashboardImpl implements Dashboard {

    static UserRepository userRepository = UserRepository.getInstance();
    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    @Override
    public void dashboard(UserInfo userInfo , RoleType roleType) {
        Scanner scanner = new Scanner(System.in);
        if (roleType!=RoleType.Admin) {
            System.out.println("choose a number to see  details...\n1.See number of all of your articles\n" +
                    "2.See number of all of your published articles\n3.Some information(using generic method with function argument");
            int dashboardChoice = scanner.nextInt();
            if (dashboardChoice == 1) {
                Long count = 0L;
                try {
                    count = articleRepository.findAll().stream().
                            filter(Article -> Article.getAuthor().equals(userInfo.getFullName())).
                            count();
                } catch (Exception e) {
                }
                System.out.printf("%s%d\n", "Number of all of your articles : ", count);

            } else if (dashboardChoice == 2) {
                Long count = 0L;
                try {
                    count = articleRepository.findAll().stream().
                            filter(Article -> Article.getAuthor().equals(userInfo.getFullName())).
                            filter(Article -> Article.getIsPublished().equals(IsPublished.Yes)).
                            count();
                } catch (Exception e) {
                }
                System.out.printf("%s%d\n", "Number of all of your published articles : ", count);
            } else if (dashboardChoice == 3) {

                Function<User, UserInfo> function = user -> {
                    UserInfo userInformation = new UserInfo();
                    userInformation.setCity(user.getAddress().getCity());
                    userInformation.setFullName(user.getUsername());
                    userInformation.setRoleType(roleType);
                    return userInformation;
                };

                List<UserInfo> list = userRepository.findAll(function);
                System.out.println(list);
            }
        }else
        {
            Function<User, UserInfo> function = user -> {
                UserInfo userInformation = new UserInfo();
                userInformation.setCity(user.getAddress().getCity());
                userInformation.setFullName(user.getUsername());
                userInformation.setRoleType(roleType);
                return userInformation;
            };

            List<UserInfo> list = userRepository.findAll(function);
            System.out.println(list);
        }
    }
}
