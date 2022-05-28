package app.repositories;

import app.entities.Student;
import app.entities.User;
import app.manager.Manager;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UserRepository {
    private EntityManager entityManager = Manager.getInstance().entityManagerFactory.createEntityManager();

    public void create(User entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
    }

    public void remove(User entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public boolean verifyLogin(String username, String password) {
        Query query = entityManager.createQuery(
                        "SELECT u FROM User u where u.username = :username and u.password=:password")
                .setParameter("username", username)
                .setParameter("password", password);
        return query.getResultList().size() == 1;
    }
}
