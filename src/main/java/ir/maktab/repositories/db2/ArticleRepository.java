package ir.maktab.repositories.db2;

import ir.maktab.config.CrudRepository;
import ir.maktab.config.HibernateUtil;
import ir.maktab.entities.db2.Article;
import org.hibernate.Session;

public class ArticleRepository extends CrudRepository<Article , Long> {

    private static ArticleRepository articleRepository;
    private ArticleRepository(){}

    public static ArticleRepository getInstance(){
        if (articleRepository == null)
            articleRepository = new ArticleRepository();
        return articleRepository;
    }

    @Override
    protected Class<Article> getEntityClass() {
        return Article.class;
    }
    protected Session getSession(){ return HibernateUtil.getSessionTwo(); }
}
