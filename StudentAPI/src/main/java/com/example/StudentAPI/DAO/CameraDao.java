package com.example.StudentAPI.DAO;

import com.example.StudentAPI.Entities.Camera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraDao extends CrudRepository<Camera, Integer> {
    @Query("SELECT MAX(c.id) FROM Camera c")
    int getCountOfCamera();
    @Query("UPDATE Camera c SET c.idCamin=?1 WHERE c.id=?2")
    void assignCameraToCamin(int idCamin,int idCamera);

}
