package com.example.StudentAPI.DAO;

import com.example.StudentAPI.Entities.Camin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaminDao extends CrudRepository<Camin, Integer> {

    @Query("SELECT MAX(c.id) FROM Camin c")
    int getCountOfCamin();

}