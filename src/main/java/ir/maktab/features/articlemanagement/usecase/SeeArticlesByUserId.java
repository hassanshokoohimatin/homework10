package ir.maktab.features.articlemanagement.usecase;

import ir.maktab.entities.db1.User;
import ir.maktab.share.UserInfo;

public interface SeeArticlesByUserId {

    void listArticles(UserInfo userInfo);
}
