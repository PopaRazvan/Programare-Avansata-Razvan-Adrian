package App.Parsers;

import App.Entities.Camera;
import App.Entities.Printable;
import App.Entities.Student;

import java.util.ArrayList;
import java.util.List;

public class PrintTest {
    public static void main(String[] args) {
        JSONPrinter jsonPrinter = new JSONPrinter();
        Camera camera = new Camera(1, 20, 4);
        List<Printable> entityList = new ArrayList<>();
        entityList.add(camera);
        Student student = new Student();
        student.setNume("John");
        student.setPrenume("Wick");
        jsonPrinter.setFilePath("src/main/resources/InputFiles/output.json");
        camera.setIdCamin(300);
        entityList.add(camera);
        entityList.add(student);
        jsonPrinter.printToFile(entityList);
    }
}
