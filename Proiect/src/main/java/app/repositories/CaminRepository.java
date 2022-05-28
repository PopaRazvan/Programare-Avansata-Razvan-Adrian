package app.repositories;

import app.entities.Camin;
import app.entities.Student;
import app.manager.Manager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CaminRepository {

    private final EntityManager entityManager = Manager.getInstance().entityManagerFactory.createEntityManager();

    public void create(Camin entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
    }

    public void remove(Camin entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public Camin findById(Integer id) {
        return entityManager.find(Camin.class, id);
    }

    public List<Camin> getAll() {
        Query query = entityManager.createQuery(
                "SELECT c FROM Camin c");
        return query.getResultList();
    }

    public List<Camin> getByRating() {
        Query query = entityManager.createQuery(
                "SELECT c FROM Camin c order by c.rating asc ");
        return query.getResultList();
    }

}
