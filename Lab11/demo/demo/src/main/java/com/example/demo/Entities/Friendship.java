package com.example.demo.Entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "friendships")
public class Friendship implements Serializable {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "friend_id_1")
    private int idFriendOne;
    @Column(name = "friend_id_2")
    private int idFriendTwo;

    public Friendship() {
    }
    public Friendship(int id, int idFriendOne, int idFriendTwo) {
        this.id = id;
        this.idFriendOne = idFriendOne;
        this.idFriendTwo = idFriendTwo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFriendOne() {
        return idFriendOne;
    }

    public void setIdFriendOne(int idFriendOne) {
        this.idFriendOne = idFriendOne;
    }

    public int getIdFriendTwo() {
        return idFriendTwo;
    }

    public void setIdFriendTwo(int idFriendTwo) {
        this.idFriendTwo = idFriendTwo;
    }
}

