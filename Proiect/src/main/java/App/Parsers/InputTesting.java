package App.Parsers;

import App.Entities.Student;

import java.util.List;

public class InputTesting {
    public static void main(String[] args) {
        JSONParser<Student> studentJSONParser = new StudentJSONParser();
        List<Student> studentList = studentJSONParser.parseFile("src/main/resources/InputFiles/student.json");
        System.out.println(studentList);
    }
}
