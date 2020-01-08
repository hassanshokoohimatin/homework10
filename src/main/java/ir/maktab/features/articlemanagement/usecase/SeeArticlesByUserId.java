package ir.maktab.features.articlemanagement.usecase;

import ir.maktab.entities.db1.User;

public interface SeeArticlesByUserId {

    void listArticles(User user);
}
