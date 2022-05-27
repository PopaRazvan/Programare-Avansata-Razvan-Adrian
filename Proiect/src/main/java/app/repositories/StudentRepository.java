package app.repositories;

import app.entities.Camera;
import app.entities.Camin;
import app.entities.Student;
import app.manager.Manager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class StudentRepository {
    private EntityManager entityManager = Manager.getInstance().entityManagerFactory.createEntityManager();

    public void create(Student entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
    }

    public void remove(Student entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public List<Student> getAll() {
        Query query = entityManager.createQuery("SELECT s FROM Student s");
        return query.getResultList();
    }

    public Student getById(Integer id) {
        return entityManager.find(Student.class, id);
    }

    public List<Student> getByIdCamera(Integer id) {
        Query query = entityManager.createQuery(
                        "SELECT s FROM Student s where s.idCamera = :id")
                .setParameter("id", id);
        return query.getResultList();
    }

    public List<Student> getByIdCamin(Integer id) {
        Query query = entityManager.createQuery(
                        "SELECT s FROM Student s where s.idCamin = :id")
                .setParameter("id", id);
        return query.getResultList();
    }

    public List<Student> getByMedia() {
        Query query = entityManager.createQuery(
                "SELECT s FROM Student s order by s.media desc");
        return query.getResultList();
    }

    public void assignStudentToCamin(Student student, Camin camin) {
        student.setIdCamin(camin.getId());
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                        "UPDATE Student s SET s.idCamin=:idCamin WHERE s.id=:idStudent")
                .setParameter("idCamin", camin.getId())
                .setParameter("idStudent", student.getId());
        query.executeUpdate();
        entityManager.getTransaction().commit();

    }

    public void assignStudentToCamera(Student student, Camera camera) {
        student.setIdCamin(camera.getId());
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(
                        "UPDATE Student s SET s.idCamera=:idCamera WHERE s.id=:idStudent")
                .setParameter("idCamera", camera.getId())
                .setParameter("idStudent", student.getId());
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}
