package App.FileInput;

import App.Entities.Person;

import java.util.ArrayList;
import java.util.List;

public class InputTesting {
    public static void main(String[] args) {
        PersonJSONParser personJSONParser = new PersonJSONParser();
        List<Person> personList = personJSONParser.parseFile("src/main/resources/InputFiles/person.json");
        System.out.println(personList);
    }
}
