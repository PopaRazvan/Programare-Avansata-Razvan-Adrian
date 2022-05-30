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

    public User getById(Integer id)
    {
        return entityManager.find(User.class, id);
    }

    public boolean verifyLogin(String username, String password) {
        Query query = entityManager.createQuery(
                        "SELECT u FROM User u where u.username = :username and u.password=:password")
                .setParameter("username", username)
                .setParameter("password", password);
        return query.getResultList().size() == 1;
    }

    public void setPassword(User user,String password)
    {
        user.setPassword(password);
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                        "UPDATE User u SET u.password=:password WHERE u.id=:idUser")
                .setParameter("idUser", user.getId())
                .setParameter("password",password);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    public User getByUsername(String username)
    {
        return entityManager.find(User.class, username);
    }

}
