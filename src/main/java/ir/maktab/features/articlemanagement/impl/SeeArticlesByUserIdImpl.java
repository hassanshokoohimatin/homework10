package ir.maktab.features.articlemanagement.impl;

import ir.maktab.entities.db1.User;
import ir.maktab.entities.db2.Article;
import ir.maktab.features.articlemanagement.usecase.SeeArticlesByUserId;
import ir.maktab.repositories.db2.ArticleRepository;
import ir.maktab.share.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

public class SeeArticlesByUserIdImpl implements SeeArticlesByUserId {

    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    @Override
    public void listArticles(UserInfo userInfo) {
        List<Article> articles = null;
        try {
            articles = articleRepository.findAll().stream().
                    filter(Article -> Article.getAuthor().equals(userInfo.getFullName())).
                    collect(Collectors.toList());
        }catch (Exception e){}

        if (articles.size() == 0)
            System.out.println("You dont have any articles...");
        else {
            for (Article a : articles) {
                System.out.println(a);
            }
        }
    }
}
