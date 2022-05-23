package App.Parsers;

import App.Entities.Student;
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
    public Student mapObject(String path) {
        try {
            Student student = objectMapper.readValue(new File(path), Student.class);
            return student;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> mapObjects(String path) {
        try {
            List<Student> studentList = objectMapper.readValue(new File(path), new TypeReference<List<Student>>() {
            });
            return studentList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Student> parseFile(String path) {
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
