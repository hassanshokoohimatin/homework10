package ir.maktab.features.usermanagement.impl;

import ir.maktab.entities.db1.User;
import ir.maktab.entities.db1.enums.IsPublished;
import ir.maktab.features.usermanagement.usecase.Dashboard;
import ir.maktab.repositories.db2.ArticleRepository;
import ir.maktab.share.UserInfo;

import java.util.Scanner;

public class DashboardImpl implements Dashboard {

    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    @Override
    public void dashboard(UserInfo userInfo) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("choose a number to see  details...\n1.See number of all of your articles\n" +
                "2.See number of all of your published articles");
        int dashboardChoice = scanner.nextInt();
        if (dashboardChoice == 1) {
            Long count = 0L;
            try {
                count = articleRepository.findAll().stream().
                        filter(Article -> Article.getAuthor().equals(userInfo.getFullName())).
                        count();
            }catch (Exception e){}
            System.out.printf("%s%d\n","Number of all of your articles : ",count);

        } else if (dashboardChoice == 2) {
            Long count = 0L;
            try {
                count = articleRepository.findAll().stream().
                        filter(Article -> Article.getAuthor().equals(userInfo.getFullName())).
                        filter(Article -> Article.getIsPublished().equals(IsPublished.Yes)).
                        count();
            }catch (Exception e){}
            System.out.printf("%s%d\n","Number of all of your published articles : ",count);
        }
    }
}
