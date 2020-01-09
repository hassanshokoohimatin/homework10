package ir.maktab;

import ir.maktab.entities.db1.*;
import ir.maktab.entities.db1.enums.IsPublished;
import ir.maktab.entities.db1.enums.RoleType;
import ir.maktab.entities.db2.*;
import ir.maktab.features.articlemanagement.impl.EditArticleImpl;
import ir.maktab.features.articlemanagement.impl.EnterNewArticleImpl;
import ir.maktab.features.articlemanagement.impl.SeeArticlesByUserIdImpl;
import ir.maktab.features.articlemanagement.usecase.EditArticle;
import ir.maktab.features.articlemanagement.usecase.EnterNewArticle;
import ir.maktab.features.articlemanagement.usecase.SeeArticlesByUserId;
import ir.maktab.features.usermanagement.impl.ChangePasswordImpl;
import ir.maktab.features.usermanagement.impl.DashboardImpl;
import ir.maktab.features.usermanagement.usecase.ChangePassword;
import ir.maktab.features.usermanagement.usecase.Dashboard;
import ir.maktab.repositories.db1.*;
import ir.maktab.repositories.db2.*;
import ir.maktab.share.AuthenticationService;
import ir.maktab.share.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Application {
    static UserRepository userRepository = UserRepository.getInstance();
    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    static CategoryRepository categoryRepository = CategoryRepository.getInstance();
    static TagRepository tagRepository = TagRepository.getInstance();
    static RoleRepository roleRepository = RoleRepository.getInstance();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String manner = "";
        while ( ! manner.equals("exit")){
            System.out.println("Signin\nSignup\nArticles\n(Find1)...Find an optional city users" +
                    "(using a generic method with a Predicate argument)\n" +
                    "(Find2)...See published articles...(using a generic method)Exit\n");
            manner = scanner.next();
            if (manner.equalsIgnoreCase("signup")){


                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null) {
                    int existUserCount = 0;
                    System.out.println("Enter username : ");
                    String username = scanner.next();
                    System.out.println("Enter national code : ");
                    String nationalCode = scanner.next();
                    List<User> userList = userRepository.findAll();
                    for (User u : userList) {
                        if (u.getUsername().equals(username) || u.getNationalCode().equals(nationalCode)) {
                            existUserCount++;
                            break;
                        }
                    }
                    if (existUserCount == 0) {
                        System.out.println("Enter birthday : ");
                        String birthday = scanner.next();
                        List<Role> roleList = new ArrayList<>();
                        roleList.add(roleRepository.findById(2L));
                        System.out.println("Address...");
                        System.out.println("State : ");
                        String state = scanner.next();
                        System.out.println("City : ");
                        String city = scanner.next();
                        System.out.println("Street : ");
                        String street = scanner.next();
                        System.out.println("Alley : ");
                        String alley = scanner.next();
                        System.out.println("Postal code : ");
                        String postalCode = scanner.next();
                        Address address = new Address(null,state,city,street,alley,postalCode,null);
                        User user = new User(null, username, nationalCode, birthday, nationalCode,address,roleList);
                        address.setUser(user);
                        userRepository.save(user);
                        System.out.println("signed up successfully...you can sign in now as a writer");
                    } else {
                        System.out.println("This username or password already exist...try another");
                    }

                } else {
                    System.out.printf("the user %s is logged in...", loginUser.getUsername());
                    AuthenticationService.getInstance().setLoginUser(null);
                    System.out.println("signed out successfully...");
                }
            }
            if(manner.equalsIgnoreCase("articles")){

                List<Article> articles = articleRepository.findAll();

                if (articles.size() == 0) {
                    System.out.println("there is no articles...");
                } else {
                    System.out.println("id\t\ttitle\t\tbrief");
                    for (Article a : articles) {
                        System.out.printf("%d\t\t%s\t\t%s\n", a.getId(), a.getAbout().getTitle(), a.getAbout().getBrief());
                    }
                    System.out.println("enter the id of the article too see more detail or press 0 to back to main menu");
                    Long id = scanner.nextLong();
                    if (id != 0) {

                        articleRepository.findAll().stream().
                                filter(Article->Article.getId()==id).
                                forEach(Article-> System.out.println(Article));
                    }
                }
            }
            if (manner.equalsIgnoreCase("exit")){
                System.out.println("END");
            }
            if (manner.equalsIgnoreCase("signin")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null) {
                    System.out.println("Enter username : ");
                    String username = scanner.next();
                    System.out.println("Enter password : ");
                    String password = scanner.next();
                    try {
                        List<User> users = userRepository.findAll().stream().
                                filter(User -> User.getUsername().equals(username)).
                                filter(User -> User.getPassword().equals(password)).
                                collect(Collectors.toList());
                        loginUser = users.get(0);
                    }catch (Exception e){ loginUser =null; }

                    AuthenticationService.getInstance().setLoginUser(loginUser);
                    if (loginUser == null) {
                        System.out.println("Wrong username or password...");
                    } else {
                        //After successful sign in
                        System.out.println("Your first name : ");
                        String firstName = scanner.next();
                        System.out.println("Your last name : ");
                        String lastName = scanner.next();
                        String fullName = firstName+" "+lastName;
                        UserInfo userInfo = new UserInfo(fullName);
                        if (loginUser.getRoles().size()==1){
                            if (loginUser.getRoles().get(0).getRoleType().equals(RoleType.Admin)){
                                userInfo.setRoleType(RoleType.Admin);
                                admin(loginUser , userInfo);
                            }

                            else{
                                userInfo.setRoleType(RoleType.Writer);
                                writer(loginUser,userInfo);
                            }
                        }
                        else{
                            userInfo.setRoleType(RoleType.AdminWriter);
                            adminWriter(loginUser , userInfo);
                        }
                    }
                }else
                {
                    System.out.printf("the user %s is logged in...getting sign out...\n", loginUser.getUsername());
                    AuthenticationService.getInstance().setLoginUser(null);
                    System.out.println("signed out successfully...");
                }
            }

            if (manner.equalsIgnoreCase("find1")){
                System.out.println("Which city do you want its users?...enter city...");
                String city = scanner.next();
                Predicate<User> predicate = user -> user.getAddress().getCity().equals(city);
                List<User> users = userRepository.findAll(predicate);
                if (users.size()==0)
                    System.out.println("There is no any users from this city...");
                else{
                    for (User u : users)
                        System.out.println(u);
                }
            }

            if (manner.equalsIgnoreCase("find2")){
                System.out.println("Published articles...\n");
                Predicate<Article> predicate = article -> article.getIsPublished().equals(IsPublished.Yes);
                List<Article> articles = articleRepository.findAll(predicate);
                if (articles.size()==0)
                    System.out.println("There is no any published articles...");
                else{
                    for (Article a : articles)
                        System.out.println(a);
                }
            }
        }
    }

    public static void adminWriter(User user , UserInfo userInfo){
        Scanner scanner = new Scanner(System.in);
        int command = 0;
        while(command!=13) {
            System.out.println("You signed in as an adminWriter...choose an action");
            System.out.println("1.See all articles\n2.Publish an article\n3.Publish off an article\n4.Remove an article\n" +
                    "5.Create a category\n6.Create a tag\n7.Change the role of users\n8.Change password\n9.See your articles\n10.Enter new article\n" +
                    "11.Edit article\n12.Dashboard\n13.exit");
            command = scanner.nextInt();
            if (command==1){
                articleRepository.findAll().stream().
                        forEach(Article-> System.out.println(Article));
            }
            if (command==2){
                articleRepository.findAll().stream().
                        filter(Article->Article.getIsPublished().equals(IsPublished.No)).
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to publish?\nEnter id : ");
                Long id = scanner.nextLong();
                Article article = articleRepository.findById(id);
                article.setIsPublished(IsPublished.Yes);
                articleRepository.update(article);
            }
            if (command==3){
                articleRepository.findAll().stream().
                        filter(Article->Article.getIsPublished().equals(IsPublished.Yes)).
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to publish off?\nEnter the id : ");
                Long id = scanner.nextLong();
                Article article = articleRepository.findById(id);
                article.setIsPublished(IsPublished.No);
                articleRepository.update(article);
            }
            if (command==4){
                articleRepository.findAll().stream().
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to remove?\nEnter id : ");
                Long id = scanner.nextLong();

                articleRepository.removeById(id);
            }
            if (command==5){
                Category category = new Category();
                System.out.println("Enter title : ");
                String title = scanner.next();
                System.out.println("Enter description : ");
                String description = scanner.next();
                category.setId(null);
                category.setTitle(title);
                category.setDescription(description);
                categoryRepository.save(category);
            }
            if (command==6){
                Tag tag = new Tag();
                System.out.println("Enter title : ");
                String title = scanner.next();
                tag.setId(null);
                tag.setTitle(title);
                tagRepository.save(tag);
            }
            if (command==7){
                System.out.println("1.You can change the role of the writer to adminWriter...\nor\n2.You can change the role of the" +
                        " admin to adminWriter" +
                        "\nselect one...");
                int choice = scanner.nextInt();

                if (choice==1) {
                    userRepository.findAll().stream().
                            filter(User -> User.getRoles().size() == 1).
                            filter(User -> User.getRoles().get(0).getRoleType().equals(RoleType.Writer)).
                            forEach(User -> System.out.println(User));

                    System.out.print("Which user do you want to change its role?\nenter id : ");
                    Long id = scanner.nextLong();
                    User selectUser = userRepository.findById(id);
                    Role role1 = new Role(null,RoleType.Admin);
                    Role role2 = new Role(null, RoleType.Writer);
                    List<Role> roles = new ArrayList<>();
                    roles.add(role1);
                    roles.add(role2);
                    selectUser.setRoles(roles);
                    userRepository.update(selectUser);
                }
                if (choice==2){
                    userRepository.findAll().stream().
                            filter(User -> User.getRoles().size() == 1).
                            filter(User -> User.getRoles().get(0).getRoleType().equals(RoleType.Admin)).
                            forEach(User -> System.out.println(User));

                    System.out.print("Which user do you want to change its role?\nenter id : ");
                    Long id = scanner.nextLong();
                    User selectUser = userRepository.findById(id);
                    Role role1 = new Role(null, RoleType.Admin);
                    Role role2 = new Role(null, RoleType.Writer);
                    List<Role> roles = new ArrayList<>();
                    roles.add(role1);
                    roles.add(role2);
                    selectUser.setRoles(roles);
                    userRepository.update(selectUser);
                }

            }
            if (command==8){
                ChangePassword changePassword = new ChangePasswordImpl();
                changePassword.changePass(user);
            }
            if (command==9){

                SeeArticlesByUserId seeArticlesByUserId = new SeeArticlesByUserIdImpl();
                seeArticlesByUserId.listArticles(userInfo);
            }
            if (command==10){
                EnterNewArticle enterNewArticle = new EnterNewArticleImpl();
                enterNewArticle.createArticle(userInfo);
            }
            if (command==11){
                EditArticle editArticle = new EditArticleImpl();
                editArticle.editArticle(userInfo);
            }
            if (command==12){
                Dashboard dashboard = new DashboardImpl();
                dashboard.dashboard(userInfo);
            }

            if (command==13){
                AuthenticationService.getInstance().setLoginUser(null);
                System.out.println("signing out...Back to main menu...");
            }
        }

    }
    public static void admin(User user , UserInfo userInfo){

        Scanner scanner = new Scanner(System.in);
        int command = 0;
        while(command!=9) {
            System.out.println("You signed in as an admin...choose an action");
            System.out.println("1.See all articles\n2.Publish an article\n3.Publish off an article\n4.Remove an article\n" +
                    "5.Create a category\n6.Create a tag\n7.Change the role of users\n8.Change password\n9.exit");
            command = scanner.nextInt();
            if (command==1){
                articleRepository.findAll().stream().
                        forEach(Article-> System.out.println(Article));
            }
            if (command==2){
                articleRepository.findAll().stream().
                        filter(Article->Article.getIsPublished().equals("no")).
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to publish?\nEnter id : ");
                Long id = scanner.nextLong();
                Article article = articleRepository.findById(id);
                article.setIsPublished(IsPublished.Yes);
                articleRepository.update(article);
            }
            if (command==3){
                articleRepository.findAll().stream().
                        filter(Article->Article.getIsPublished().equals(IsPublished.Yes)).
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to publish off?\nEnter the id : ");
                Long id = scanner.nextLong();
                Article article = articleRepository.findById(id);
                article.setIsPublished(IsPublished.No);
                articleRepository.update(article);
            }
            if (command==4){
                articleRepository.findAll().stream().
                        forEach(Article-> System.out.println(Article));

                System.out.print("Which article do you want to remove?\nEnter id : ");
                Long id = scanner.nextLong();

                articleRepository.removeById(id);
            }
            if (command==5){
                Category category = new Category();
                System.out.println("Enter title : ");
                String title = scanner.next();
                System.out.println("Enter description : ");
                String description = scanner.next();
                category.setId(null);
                category.setTitle(title);
                category.setDescription(description);
                categoryRepository.save(category);
            }
            if (command==6){
                Tag tag = new Tag();
                System.out.println("Enter title : ");
                String title = scanner.next();
                tag.setId(null);
                tag.setTitle(title);
                tagRepository.save(tag);
            }
            if (command==7){
                System.out.println("You can only change the role of the writers to adminWriters...");

                userRepository.findAll().stream().
                        filter(User->User.getRoles().size() == 1).
                        filter(User->User.getRoles().get(0).getRoleType().equals(RoleType.Writer)).
                        forEach(User-> System.out.println(User));

                System.out.print("Which user do you want to change its role?\nenter id : ");
                Long id = scanner.nextLong();
                User selectUser = userRepository.findById(id);
                Role role1 = new Role(null,RoleType.Admin);
                Role role2 = new Role(null, RoleType.Writer);
                List<Role> roles = new ArrayList<>();
                roles.add(role1);
                roles.add(role2);
                selectUser.setRoles(roles);
                userRepository.update(selectUser);
            }
            if (command==8){
                ChangePassword changePassword = new ChangePasswordImpl();
                changePassword.changePass(user);
            }
            if (command==9){
                AuthenticationService.getInstance().setLoginUser(null);
                System.out.println("signing out...Back to main menu...");
            }
        }

    }
    public static void writer(User user , UserInfo userInfo){
        Scanner scanner = new Scanner(System.in);
        int command = 0;
        while(command!=6){
            System.out.println("You signed in as a writer...choose an action");
            System.out.println("1.See your articles\n2.Create new article\n3.Edit article\n4.Change password\n5.dashboard" +
                    "\n6.exit");
            command = scanner.nextInt();
            if (command == 1){
                SeeArticlesByUserId seeArticlesByUserId = new SeeArticlesByUserIdImpl();
                seeArticlesByUserId.listArticles(userInfo);
            }


            if (command == 2){
                EnterNewArticle enterNewArticle = new EnterNewArticleImpl();
                enterNewArticle.createArticle(userInfo);
            }


            if (command == 3){
                EditArticle editArticle = new EditArticleImpl();
                editArticle.editArticle(userInfo);
            }


            if (command == 4){
                ChangePassword changePassword = new ChangePasswordImpl();
                changePassword.changePass(user);
            }


            if (command == 5){
                Dashboard dashboard = new DashboardImpl();
                dashboard.dashboard(userInfo);
            }
            if (command == 6){
                AuthenticationService.getInstance().setLoginUser(null);
                System.out.println("signing out...Back to main menu...");
            }
        }
    }
}
