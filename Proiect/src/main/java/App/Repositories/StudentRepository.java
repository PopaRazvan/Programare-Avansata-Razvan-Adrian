package App.Repositories;

import App.Entities.Camin;
import App.Entities.Student;
import App.Manager.Manager;

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

    public void remove(Student entity){
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public Student getById(Integer id) {
        return entityManager.find(Student.class,id);
    }

    public List<Student> getByIdCamera(Integer id){
        Query query = entityManager.createQuery(
                        "SELECT s FROM Student s where s.idCamera = :id")
                .setParameter("id", id);
        return query.getResultList();
    }

    public List<Student> getByMedia ()
    {
        Query query = entityManager.createQuery(
                        "SELECT s FROM Student s order by s.media desc");
        return query.getResultList();
    }



}
