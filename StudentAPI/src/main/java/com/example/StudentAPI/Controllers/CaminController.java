package com.example.StudentAPI.Controllers;

import com.example.StudentAPI.DAO.CaminDao;
import com.example.StudentAPI.Entities.Camin;
import com.example.StudentAPI.Entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@RequestMapping("/api")
public class CaminController {
    @Autowired
    private CaminDao caminDao;

    @GetMapping("/camine")
    public List<Camin> getAllCamin() {
        return (List<Camin>) caminDao.findAll();
    }

    @GetMapping("/camin/{id}")
    public Optional<Camin> getCamin(@PathVariable("id") int id){
        return caminDao.findById(id);
    }

    @PostMapping("/addCamin")
    public int addCamin(@RequestBody Student student)
    {
        int id=1+caminDao.getCountOfCamin();
        caminDao.save(new Camin());
        return id;
    }

}
