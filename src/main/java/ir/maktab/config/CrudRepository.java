package ir.maktab.config;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class CrudRepository<Entity , ID extends Serializable> {


    protected abstract Class<Entity> getEntityClass();
    protected abstract Session getSession();

    public void save(Entity entity) {
        getSession().beginTransaction();
        getSession().save(entity);
        getSession().getTransaction().commit();
    }

    public Entity update(Entity entity) {
        getSession().beginTransaction();
        getSession().update(entity);
        getSession().getTransaction().commit();
        return entity;
    }

    public void remove(Entity entity) {
        getSession().beginTransaction();
        getSession().remove(entity);
        getSession().getTransaction().commit();
    }

    public void removeById(ID id) {
        Entity entity = findById(id);
        if (entity != null) {
            getSession().beginTransaction();
            getSession().remove(entity);
            getSession().getTransaction().commit();
        }
    }

    public Entity findById(ID id) {
        getSession().beginTransaction();
        Entity entity = getSession().get(getEntityClass(), id);
        getSession().getTransaction().commit();
        return entity;
    }

    public List<Entity> findAll() {
        getSession().beginTransaction();
        Query<Entity> query = getSession()
                .createQuery("from " + getEntityClass().getName(), getEntityClass());
        List<Entity> entities = query.list();
        getSession().getTransaction().commit();
        return entities;
    }


    public List<Entity> findAll(int start, int row) {
        getSession().beginTransaction();
        Query<Entity> query = getSession()
                .createQuery("from " + getEntityClass().getName(), getEntityClass());
        query.setFirstResult(start);
        query.setMaxResults(row);
        List<Entity> entities = query.list();
        getSession().getTransaction().commit();
        return entities;
    }

    //Generic method with a predicate argument
    public List<Entity> findAll(Predicate<Entity> predicate){
        List<Entity> list = new ArrayList<>();
        List<Entity> entities = findAll();
        entities.stream().filter(predicate).forEach(entity -> list.add(entity));
        return list;
    }

    //Generic method with a function argument
    public <T> List<T> findAll(Function<Entity , T> function){
        List<T> list = new ArrayList<>();
        List<Entity> entities = findAll();
        entities.stream().map(function).forEach(t->list.add(t));
        return list;
    }
}
