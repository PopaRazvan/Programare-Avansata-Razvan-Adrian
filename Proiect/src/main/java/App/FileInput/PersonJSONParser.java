package App.FileInput;

import App.Entities.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonJSONParser implements JSONParser<Person>{
    private ObjectMapper objectMapper;

    public PersonJSONParser() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Person mapObject(String filePath) {
        try {
            Person person = objectMapper.readValue(new File(filePath), Person.class);
            return person;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> mapObjects(String filePath) {
        try {
            List<Person> personList = objectMapper.readValue(new File(filePath), new TypeReference<List<Person>>() {});
            return personList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> parseFile(String path) {
        if(isValid(path)){
            if(isArray(path)){
                return mapObjects(path);
            }
            List<Person> personList = new ArrayList<>();
            personList.add(mapObject(path));
            return personList;
        }
        return null;
    }
}
