package app.parsers;

import app.entities.Camera;
import app.entities.Camin;
import app.entities.Printable;
import app.entities.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONPrinter { //Class for managing output of Printable objects to files
    private ObjectMapper objectMapper;
    private String filePath;


    public JSONPrinter(String filePath) {
        objectMapper = new ObjectMapper();
        this.filePath = filePath;
    }

    public JSONPrinter() {
        objectMapper = new ObjectMapper();
        filePath = null;
    }

    public void printToFile(Camera object) throws IOException {
        objectMapper.writeValue(new File(filePath), object);

    }


    public void printToFile(List<Printable> object) throws IOException { //Prints to file a list of any Printable objects
        objectMapper.writeValue(new File(filePath), object);
    }



    public void printToFile(Student object) throws IOException {
        objectMapper.writeValue(new File(filePath), object);
    }


    public void printToFile(Camin object) throws IOException {
        objectMapper.writeValue(new File(filePath), object);

    }

    public void setFilePath(String path) {
        this.filePath = path;
    }

    public String getFilePath() {
        return filePath;
    }
}
