package app;

import app.Algorithm.Algorithm;
import app.entities.Student;
import app.repositories.StudentRepository;
import app.server.Server;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //List<Student> studentList;
        //StudentRepository studentRepository = new StudentRepository();
        //studentList=studentRepository.getByMedia();

       // studentRepository.setPreferredStudent(studentList.get(0),studentList.get(12));
       // studentRepository.setPreferredStudent(studentList.get(12),studentList.get(17));
        //studentRepository.setPreferredStudent(studentList.get(17),studentList.get(25));
        //studentRepository.setPreferredStudent(studentList.get(25),studentList.get(7));
        //studentRepository.setPreferredStudent(studentList.get(7),studentList.get(0));
        //studentRepository.close();
        //Algorithm algorithm =new Algorithm();

        Server server = new Server();
        try {
           server.runServer();
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }

}