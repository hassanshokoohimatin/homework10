package ir.maktab.features.articlemanagement.impl;

import ir.maktab.entities.db1.User;
import ir.maktab.entities.db1.embeddables.About;
import ir.maktab.entities.db2.Article;
import ir.maktab.entities.db2.Category;
import ir.maktab.entities.db2.Tag;
import ir.maktab.features.articlemanagement.usecase.EditArticle;
import ir.maktab.repositories.db2.ArticleRepository;
import ir.maktab.repositories.db2.CategoryRepository;
import ir.maktab.repositories.db2.TagRepository;
import ir.maktab.share.UserInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EditArticleImpl implements EditArticle {

    static ArticleRepository articleRepository = ArticleRepository.getInstance();
    static CategoryRepository categoryRepository = CategoryRepository.getInstance();
    static TagRepository tagRepository = TagRepository.getInstance();

    @Override
    public void editArticle(UserInfo userInfo) {
        Scanner scanner = new Scanner(System.in);
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


            System.out.println("Enter the id of article you want to edit...");
            Long id = scanner.nextLong();
            Article article = articleRepository.findById(id);
            System.out.println("Enter new title : ");
            String title = scanner.next();
            System.out.println("Enter new brief : ");
            String brief = scanner.next();
            System.out.println("Enter new content : ");
            String content = scanner.next();
            About about = new About(title,brief,content);
            article.setAbout(about);
            List<Category> categories = categoryRepository.findAll();
            System.out.println("id\t\t\ttitle");
            for (Category c : categories) {
                System.out.printf("%d\t\t\t%s\n", c.getId(), c.getTitle());
            }
            System.out.println("Enter the id of category for adding to your article...");
            int categoryChoice = scanner.nextInt();
            article.setCategory(categories.get(categoryChoice - 1));
            article.getDate().setLastUpdateDate(new Date().toString());

            List<Tag> tagList = tagRepository.findAll();
            System.out.println("id\t\t\ttitle");
            for (Tag t : tagList) {
                System.out.printf("%d\t\t\t%s\n", t.getId(), t.getTitle());
            }
            System.out.println("Which tags do you want to add?...enter 0 to end entering id...");
            List<Tag> tags = new ArrayList<>();
            int choice = 1;
            while (choice != 0) {
                choice = scanner.nextInt();
                try {
                    tags.add(tagList.get(choice - 1));
                    System.out.print("added");
                } catch (Exception e) {
                    if (choice == 0)
                        System.out.print("done");
                    else
                        System.out.print("failed!!!");
                }
            }
            article.setTags(tags);

            articleRepository.update(article);
        }
    }
}
