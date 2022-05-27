package app;

import app.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

//Am facut niste teste

        // StudentRepository studentRepository=new StudentRepository();
        // CaminRepository caminRepository=new CaminRepository();

        //Student student=new Student(1,"RSL2022","Popa","Razvan","B",2,"A6",8.00,"05-08-2001","poparazvan2001@yahoo.com");
        //Camin camin=new Camin(1,"C201",10);


        //studentRepository.create(student);
        // caminRepository.create(camin);

        // studentRepository.assignStudentToCamin(student,camin);

        //studentRepository.close();
        //caminRepository.close();

        Server server = new Server();
        try {
            server.runServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}