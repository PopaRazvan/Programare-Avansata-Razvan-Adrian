package app.repositories;

import app.entities.Camera;
import app.entities.Camin;
import app.entities.Student;
import app.manager.Manager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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

    public void remove(Camera entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public Camera findById(Integer id) {
        return entityManager.find(Camera.class, id);
    }

    public List<Camera> getAll() {
        Query query = entityManager.createQuery(
                "SELECT c FROM Camera c");
        return query.getResultList();
    }



    public void assignCameraToCamin(Camera camera, Camin camin) {
        camera.setIdCamin(camin.getId());
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                        "UPDATE Camera c SET c.idCamin=:idCamin WHERE c.id=:idCamera")
                .setParameter("idCamin", camin.getId())
                .setParameter("idCamera", camera.getId());
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

}
