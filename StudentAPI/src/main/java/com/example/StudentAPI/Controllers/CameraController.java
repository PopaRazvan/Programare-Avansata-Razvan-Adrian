package com.example.StudentAPI.Controllers;

import com.example.StudentAPI.DAO.CameraDao;
import com.example.StudentAPI.DAO.StudentDao;
import com.example.StudentAPI.Entities.Camera;
import com.example.StudentAPI.Entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@RequestMapping("/api")
public class CameraController {
    @Autowired
    private CameraDao cameraDao;

    @GetMapping("/camere")
    public List<Camera> getAllCamera() {
        return (List<Camera>) cameraDao.findAll();
    }

    @GetMapping("/camera/{id}")
    public Optional<Camera> getCamera(@PathVariable("id") int id) {
        return cameraDao.findById(id);
    }

    @PostMapping("/addCamera")
    public int addCamera(@RequestBody Camera camera) {
        int id;
        if (cameraDao.getCountOfCamera() == null)
            id = 1;
        else
            id = 1 + cameraDao.getCountOfCamera();

        cameraDao.save(new Camera(id,camera.getIdCamin(),camera.getNrLocuri()));
        return id;
    }

    @PutMapping("/assignCameraToCamin/{id}")
    public String assignCameraToCamin(@PathVariable("id") int id,@RequestBody Camera camera){
        cameraDao.assignCameraToCamin(id,camera.getId());
        return "Assigment Completed!";
    }


}
