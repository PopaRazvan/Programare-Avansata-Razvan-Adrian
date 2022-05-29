package com.example.StudentAPI.Controllers;

import com.example.StudentAPI.DAO.StudentDao;
import com.example.StudentAPI.Entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private StudentDao studentDao;

    @GetMapping("/studenti")
    public List<Student> getAllStudents(){
        return (List<Student>) studentDao.findAll();
    }

    @GetMapping("/student/{id}")
    public Optional<Student> getStudent(@PathVariable("id") int id){
        return studentDao.findById(id);
    }

    @GetMapping("/studenti/camin/{id}")
    public List<Student> getStudentsByIdCamin(@PathVariable("id") int id){
        return studentDao.getStudentsByIdCamin(id);
    }

    @GetMapping("/studenti/camera/{id}")
    public List<Student> getStudentsByIdCamera(@PathVariable("id") int id){
        return studentDao.getStudentsByIdCamera(id);
    }

    @PostMapping("/addStudent")
    public int addPerson(@RequestBody Student student)
    {
        int id=1+studentDao.getCountOfStudent();
        studentDao.save(new Student(id,student.getNrMatricol(),student.getNume(),student.getPrenume(),student.getGen(),student.getAn(),student.getGrupa(),student.getMedia(),student.getDataNastere(),student.getEmail()));
        return id;
    }

    @PutMapping("/assignStudentToCamera/{id}")
    public String assignStudentToCamera(@PathVariable("id") int id,@RequestBody Student student){
        studentDao.assignStudentToCamera(id,student.getId());
        return "Assigment Completed!";
    }

    @PutMapping("/assignStudentToCamera/{id}")
    public String assignStudentToCamin(@PathVariable("id") int id,@RequestBody Student student){
        studentDao.assignStudentToCamin(id,student.getId());
        return "Assigment Completed!";
    }

}
