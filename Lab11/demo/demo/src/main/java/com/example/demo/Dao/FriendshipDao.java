package com.example.demo.Dao;

import com.example.demo.Entities.Friendship;

import com.example.demo.Entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipDao extends CrudRepository<Friendship, Integer> {
    @Query("SELECT MAX(f.id) FROM Friendship f")
    Integer getCountOfFriendship();


}