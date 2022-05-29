package com.example.StudentAPI.DAO;
import com.example.StudentAPI.Entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentDao extends CrudRepository<Student, Integer> {
    @Query("SELECT MAX(s.id) FROM Student s")
    Integer getCountOfStudent();

    @Query("SELECT s FROM Student s where s.idCamera = ?1")
    List<Student> getStudentsByIdCamera(int idCamera);

    @Query("SELECT s FROM Student s where s.idCamin = ?1")
    List<Student> getStudentsByIdCamin(int idCamin);

    @Query("UPDATE Student s SET s.idCamin=?1 WHERE s.id=?2")
    void assignStudentToCamin(int idCamin,int idStudent);

    @Query("UPDATE Student s SET s.idCamera=?1 WHERE s.id=?2")
    void assignStudentToCamera(int idCamera,int idStudent);
}