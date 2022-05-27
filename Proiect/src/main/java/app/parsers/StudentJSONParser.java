package app.parsers;

import app.entities.Student;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentJSONParser implements JSONParser<Student> {

    private ObjectMapper objectMapper;

    public StudentJSONParser() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Student mapObject(String path) throws IOException {
        return objectMapper.readValue(new File(path), Student.class);
    }

    @Override
    public List<Student> mapObjects(String path) throws IOException {
        return objectMapper.readValue(new File(path), new TypeReference<List<Student>>() {
        });
    }

    @Override
    public List<Student> parseFile(String path) throws IOException {
        if (isValid(path)) {
            if (isArray(path)) {
                return mapObjects(path);
            }
            List<Student> studentList = new ArrayList<>();
            studentList.add(mapObject(path));
            return studentList;
        }
        return null;
    }
}
