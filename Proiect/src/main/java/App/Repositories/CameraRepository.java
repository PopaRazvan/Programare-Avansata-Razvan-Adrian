package App.Repositories;

import App.Entities.Camera;
import App.Entities.Camin;
import App.Entities.Student;
import App.Manager.Manager;

import javax.persistence.EntityManager;

public class CameraRepository {

    private EntityManager entityManager = Manager.getInstance().entityManagerFactory.createEntityManager();

    public void create(Camera entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
    }

    public void remove(Camera entity){
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public Camera findById(Integer id) {
        return entityManager.find(Camera.class,id);
    }


}
