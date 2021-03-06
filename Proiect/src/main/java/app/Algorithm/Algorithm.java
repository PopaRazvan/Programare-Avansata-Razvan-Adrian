package app.Algorithm;

import app.repositories.StudentRepository;
import app.repositories.CaminRepository;
import app.repositories.CameraRepository;
import app.entities.Camera;
import app.entities.Camin;
import app.entities.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Algorithm {
    List<Student> studentList;
    HashMap<Student, Boolean> checkList = new HashMap<>();
    List<Student> preferredStudents = new ArrayList<>();
    List<Camera> cameraList = new ArrayList<>();
    List<Camin> caminList;
    String GEN_MASCULIN = "M";
    String GEN_FEMININ = "F";
    String GEN_UNKNOWN = "U";


    // Algortimul merge in felul uramtor:
    // - ia toti studenti din baza de data in ordinea crescatoare a mediilor lor
    // - pentru fiecare student in primul rand daca acesta are o preferinta de a fi cu cineva in camera
    //  * daca da, atunci metoda recursiva getPrefferedStudents va adauga intr-o lista studenti care vor sa fie impreuna
    //   ex: Studentul A vrea sa fie cu studentul B
    //                 B vrea sa fie cu studentul C
    //                 C nu are nici o preferinta
    //            Grupul va fi format din studentii A,B si C
    //        Dupa algoritmul va cauta o camera libera in toate caminile incepand cu cel care are ratingul cel mai mic iar camera daca nu esta goala
    //       va vedea se afla deja fete sau baieti si vor fi repartizati ca atare
    //        Daca grupul nu incape in nici o camera a nici unui camin atunci primul student va fi repartizat intr-o camera iar restul grupul va fi
    //       repartizat la urmatoare iterare si tot asa
    //  * daca nu, pur si simplu va fi repatizat intr-o camera in functie de media sa si colegi de acelasi gen


    public Algorithm() {
        StudentRepository studentRepository = new StudentRepository();
        CaminRepository caminRepository = new CaminRepository();
        CameraRepository cameraRepository = new CameraRepository();

        studentList = studentRepository.getByMedia();
        caminList = caminRepository.getByRating();

        for (Camin camin : caminList) {
            cameraList.addAll(camin.getCamere());
        }

        for (Student student : studentList) {
            checkList.put(student, false);
        }

        for (Student student : studentList)
            if (student.getPreferredStudent() != null && !checkList.get(student)) {
                preferredStudents = new ArrayList<>();
                preferredStudents.add(student);

                getPreferredStudents(student.getPreferredStudent());

                boolean assignCompleted = assignMultipleStudent(preferredStudents, studentRepository);

                if (!assignCompleted) {
                    assignSingleStudent(student, studentRepository);
                }
            } else if (!checkList.get(student))
                assignSingleStudent(student, studentRepository);


        studentRepository.close();
        caminRepository.close();
        cameraRepository.close();
    }

    private void assignSingleStudent(Student student, StudentRepository studentRepository) {
        for (Camera camera : cameraList) {
            String genStudentsInCamera = getGenOfStudentsInCamera(camera);
            if (camera.getNrLocuri() >= 1 && (student.getGen().equals(genStudentsInCamera) || genStudentsInCamera.equals(GEN_UNKNOWN))) {
                studentRepository.assignStudentToCamera(student, camera);
                studentRepository.assignStudentToCamin(student, camera.getCamin());
                camera.setNrLocuri(camera.getNrLocuri() - 1);
                camera.getStudenti().add(student);
                checkList.put(student,true);
                break;
            }
        }
    }

    private boolean assignMultipleStudent(List<Student> group, StudentRepository studentRepository) {
        for (Camera camera : cameraList) {

            String genStudentsInCamera = getGenOfStudentsInCamera(camera);
            String genStudentsInGroup=GEN_UNKNOWN;

            for(Student student : group)
            {
                if (student.getGen().equals(GEN_MASCULIN))
                    genStudentsInGroup = GEN_MASCULIN;
                else
                    genStudentsInGroup = GEN_FEMININ;
            }

            if (camera.getNrLocuri() >= group.size()&& (genStudentsInGroup.equals(genStudentsInCamera) || genStudentsInCamera.equals(GEN_UNKNOWN))) {
                for (Student student : group) {
                    studentRepository.assignStudentToCamera(student, camera);
                    studentRepository.assignStudentToCamin(student, camera.getCamin());
                    camera.setNrLocuri(camera.getNrLocuri() - 1);
                    camera.getStudenti().add(student);
                    checkList.put(student, true);
                }
                return true;
            }
        }
        return false;
    }

    private void getPreferredStudents(Student preferredStudent) {
        if (preferredStudents.contains(preferredStudent)||preferredStudent==null)
            return;

        preferredStudents.add(preferredStudent);

        for (Student student : studentList) {
            if (student.equals(preferredStudent) && !checkList.get(student)) {
                getPreferredStudents(student.getPreferredStudent());
            }
        }
    }
    private String getGenOfStudentsInCamera(Camera camera)
    {
        List<Student> studentsInCamera = camera.getStudenti();
        String genStudentsInCamera = GEN_UNKNOWN;
        for (Student student1 : studentsInCamera) {
            if (student1.getGen().equals(GEN_MASCULIN))
                genStudentsInCamera = GEN_MASCULIN;
            else
                genStudentsInCamera = GEN_FEMININ;
        }
        return genStudentsInCamera;
    }

}
