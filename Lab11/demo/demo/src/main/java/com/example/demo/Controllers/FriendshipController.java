package com.example.demo.Controllers;

import com.example.demo.Dao.FriendshipDao;
import com.example.demo.Dao.PersonDao;
import com.example.demo.Entities.Friendship;
import com.example.demo.Entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Controller
@RequestMapping("/api")
public class FriendshipController {
    @Autowired
    private FriendshipDao friendshipDao;

    @Autowired
    private PersonDao personDao;

    @PostMapping("/insertFriendship")
    public String insertFriend(@RequestBody Friendship friend) {
        int id;
        if (friendshipDao.getCountOfFriendship() == null)
            id = 1;
        else
            id = 1 + friendshipDao.getCountOfFriendship();
        System.out.println(id);
        friend.setId(id);
        friendshipDao.save(friend);
        return "friend was added";
    }

    @GetMapping("/friendship/{id}")
    public Optional<Friendship> getFriendship(@PathVariable int id) {
        return friendshipDao.findById(id);
    }

    @GetMapping("/friendships")
    public List<Friendship> getAllFriendship() {
       return (List<Friendship>) friendshipDao.findAll();
    }

    @GetMapping("/getPopularPersons/{number}")
    public List<Optional<Person>> getPopularUsers(@PathVariable int number) {
        List<Friendship> friends = (List<Friendship>) friendshipDao.findAll();

        List<Integer> userIds = new ArrayList<>();

        List<Optional<Person>> popularUsers = new ArrayList<>();

        for (Friendship friend : friends) {
            userIds.add(friend.getIdFriendOne());
            userIds.add(friend.getIdFriendTwo());
        }

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer id : userIds) {
            Integer count = frequencyMap.get(id);
            if (count == null) {
                count = 0;
            }
            frequencyMap.put(id, count + 1);
        }

        for (int index = 0; index < number; index++) {
            int maxIdApparitions = 0;
            int maxValue = 0;

            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                if (entry.getValue() > maxValue) {
                    maxValue = entry.getValue();
                    maxIdApparitions = entry.getKey();
                }
            }
            popularUsers.add(personDao.findById(maxIdApparitions));
            frequencyMap.remove(maxIdApparitions);
        }
        return popularUsers;
    }
}
