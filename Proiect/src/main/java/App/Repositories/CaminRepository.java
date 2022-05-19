package App.Repositories;

import App.Entities.Camin;
import App.Entities.Student;
import App.Manager.Manager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CaminRepository {

    private EntityManager entityManager = Manager.getInstance().entityManagerFactory.createEntityManager();

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


    public List<Camin> getAll(String name) {
        Query query = entityManager.createQuery(
                "SELECT c FROM Camin c");
        return query.getResultList();
    }

}
