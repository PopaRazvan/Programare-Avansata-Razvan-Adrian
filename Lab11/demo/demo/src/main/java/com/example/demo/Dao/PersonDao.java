package com.example.demo.Dao;

import com.example.demo.Entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends CrudRepository<Person, Integer> {

    @Query("SELECT MAX(p.id) FROM Person p")
    Integer getCountOfPerson();

}
