package ir.maktab.repositories.db2;

import ir.maktab.config.CrudRepository;
import ir.maktab.config.HibernateUtil;
import ir.maktab.entities.db2.Tag;
import org.hibernate.Session;

public class TagRepository extends CrudRepository<Tag, Long> {

    private static TagRepository tagRepository;
    private TagRepository(){}

    public static TagRepository getInstance(){
        if (tagRepository == null)
            tagRepository = new TagRepository();
        return tagRepository;
    }

    @Override
    protected Class<Tag> getEntityClass() {
        return Tag.class;
    }
    protected Session getSession() { return HibernateUtil.getSessionTwo(); }
}
