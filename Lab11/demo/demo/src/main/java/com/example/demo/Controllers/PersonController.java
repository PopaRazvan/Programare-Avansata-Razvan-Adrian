package com.example.demo.Controllers;

import com.example.demo.Dao.PersonDao;
import com.example.demo.Entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@Controller
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private PersonDao personDao;

    @GetMapping("/persons")
    public List<Person> getAllPersons(){
        return (List<Person>) personDao.findAll();
    }

    @PostMapping("/addPerson")
    public int addPerson(@RequestBody Person person)
    {
        int id;
        if (personDao.getCountOfPerson() == null)
            id = 1;
        else
            id = 1 + personDao.getCountOfPerson();
        personDao.save(new Person(id,person.getName()));
        return id;
    }

    @GetMapping("/person/{id}")
    public Optional<Person> getPerson(@PathVariable("id") int id) {
       return personDao.findById(id);
    }

    @PutMapping("/updatePerson/{id}")
    private void updatePerson(@PathVariable("id") int id, @RequestBody Person person) {
        personDao.findById(id)
                .map(personIterator -> {
                    personIterator.setName(person.getName());
                    return personDao.save(personIterator);
                });
    }

    @DeleteMapping("/deletePerson/{id}")
    private void deletePerson(@PathVariable("id") int id) {
        personDao.deleteById(id);
    }
}

