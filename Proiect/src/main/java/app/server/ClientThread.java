package app.server;

import app.Algorithm.Algorithm;
import app.entities.*;
import app.parsers.JSONPrinter;
import app.repositories.CameraRepository;
import app.repositories.CaminRepository;
import app.repositories.StudentRepository;
import app.repositories.UserRepository;
import app.server.states.AdminState;
import app.server.states.ClientState;
import app.server.states.LoginState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {

    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private final RequestDecoder requestDecoder;
    private boolean servingClient;
    private boolean adminUser;

    public ClientThread(Socket socket, ServerSocket serverSocket) {
        this.socket = socket;
        this.serverSocket = serverSocket;
        this.servingClient = true;
        this.requestDecoder = RequestDecoder.getInstance();
        this.adminUser = false;
    }

    public void run() {
        //Thread responsible for communicating with the client
        try {
            while (servingClient) { //Main While loop
                String request = readFromClient();
                String response = processRequest(request); //Generating the response based on request
                sendResponse(response);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String processRequest(String request) throws IOException { //Handling the request
        switch (requestDecoder.decodeRequest(request)) {
            case RequestDecoder.STOP_CODE:
                serverSocket.close();
                return "Server stopped!";
            case RequestDecoder.UNKNOWN_REQUEST_CODE:
                return "Server received unknown request: " + request;
            case RequestDecoder.CLIENT_EXIT_CODE:
                servingClient = false;
                return "Closing connection...";
            case RequestDecoder.ADD_USER_CODE:
                return addUser();
            case RequestDecoder.LOGIN_CODE:
                return login();
            case RequestDecoder.LOGOUT_CODE:
                return logout();
            case RequestDecoder.LAUNCH_INTERFACE_CODE:
                return launchInterface();
            case RequestDecoder.SEND_FILE_CODE:
                return sendFile("src/main/resources/IOFiles/test.txt");
            case RequestDecoder.LOGIN_CREDENTIALS_CODE:
                return loginCredentials(request);
            case RequestDecoder.CHANGE_PASSWORD_CODE:
                return changePassword(request);
            case RequestDecoder.GIVE_CREDENTIALS_CODE:
                return giveCredentials(request);
            case RequestDecoder.SAVE_NEW_STUDENT_CODE:
                return saveNewStudent(request);
            case RequestDecoder.ADD_NEW_USER_CODE:
                return addNewUser(request);
            case RequestDecoder.GET_ALL_DORMS_CODE:
                return getAllDorms();
            case RequestDecoder.GET_ALL_ROOMS_CODE:
                return getAllRooms();
            case RequestDecoder.GET_ALL_STUDENTS_CODE:
                return getAllStudents();
            case RequestDecoder.APPLY_ALGORITHM_CODE:
                return applyAlgorithm();

            default:
                return "Server received the request " + request;
        }
    }

    private String applyAlgorithm() { //Apply the auto matching algorithm
        Algorithm algorithm = new Algorithm();
        return "Done";
    }

    private String getAllStudents() { //Sends all students from DB as a json file
        StudentRepository studentRepository = new StudentRepository();
        JSONPrinter jsonPrinter = new JSONPrinter();
        List<Printable> students = new ArrayList<>();
        List<Student> listStudents = studentRepository.getAll();

        for (Student student : listStudents)
            students.add(student);

        jsonPrinter.setFilePath("src/main/resources/IOFiles/output.json");
        try {
            jsonPrinter.printToFile(students);
            sendFile(jsonPrinter.getFilePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "nores"; //Doesn't send anything to server
    }

    private String getAllRooms() { //Sends all rooms from DB as a json file
        CameraRepository cameraRepository = new CameraRepository();
        JSONPrinter jsonPrinter = new JSONPrinter();
        List<Printable> cameras = new ArrayList<>();
        List<Camera> listCameras = cameraRepository.getAll();

        for (Camera camera : listCameras)
            cameras.add(camera);
        jsonPrinter.setFilePath("src/main/resources/IOFiles/output.json");
        try {
            jsonPrinter.printToFile(cameras);
            sendFile(jsonPrinter.getFilePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "nores"; //Doesn't send anything to server
    }

    private String getAllDorms() { //Sends all dorms (camine) from DB as a json file
        CaminRepository caminRepository = new CaminRepository();
        JSONPrinter jsonPrinter = new JSONPrinter();
        List<Printable> camine = new ArrayList<>();
        List<Camin> listCamine = caminRepository.getAll();

        for (Camin camin : listCamine)
            camine.add(camin);
        jsonPrinter.setFilePath("src/main/resources/IOFiles/output.json");
        try {
            jsonPrinter.printToFile(camine);
            sendFile(jsonPrinter.getFilePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "nores"; //Doesn't send anything to server
    }

    private String addNewUser(String request) { //Adds new user to DB
        UserRepository userRepository = new UserRepository();
        request = request.substring(4);
        String[] info = request.split(",");
        User user = new User();
        user.setId(Integer.parseInt(info[0]));
        user.setUsername(info[1]);
        user.setPassword(info[2]);
        if (info[3].equals("t")) {
            user.setSuperUse(true);
        } else {
            user.setSuperUse(false);
        }
        userRepository.create(user);
        return user.toString();
    }

    private String saveNewStudent(String request) { //Saves a new student to the DB
        StudentRepository studentRepository = new StudentRepository();
        boolean hasId = false;
        if (request.charAt(2) == 'i') { //Checking if client passed the ID
            hasId = true;
            request = request.substring(5);
        } else {
            request = request.substring(4);
        }
        Student student = new Student();
        String[] info = request.split(",");
        /*
        Info: (hasId)
        0 - id
        1 - register
        2 - name
        3 - surname
        4 - gender
        5 - year
        6 - group
        7 - mark
        8 - birthdate
        9 - email
        10 - prefStud
        */
        if (hasId) {
            student.setId(Integer.parseInt(info[0]));
            student.setNrMatricol(info[1]);
            student.setNume(info[2]);
            student.setPrenume(info[3]);
            student.setGen(info[4]);
            student.setAn(Integer.parseInt(info[5]));
            student.setGrupa(info[6]);
            student.setMedia(Double.parseDouble(info[7]));
            student.setDataNastere(info[8]);
            student.setEmail(info[9]);
            int idPreferredStudent = Integer.parseInt(info[10]);
            Student preferredStudent = studentRepository.getById(idPreferredStudent);
            studentRepository.setPreferredStudent(student, preferredStudent);

        } else {
            student.setNrMatricol(info[0]);
            student.setNume(info[1]);
            student.setPrenume(info[2]);
            student.setGen(info[3]);
            student.setAn(Integer.parseInt(info[4]));
            student.setGrupa(info[5]);
            student.setMedia(Double.parseDouble(info[6]));
            student.setDataNastere(info[7]);
            student.setEmail(info[8]);
            student.setPreferredStudent(null);
        }


        studentRepository.create(student);
        Algorithm algorithm=new Algorithm();

        return student.toString(); //returning to client the saved student data
    }

    private String giveCredentials(String request) { //Gives to user information about its linked student
                                                     //request is like: "gc:username"
        UserRepository userRepository=new UserRepository();
        StudentRepository studentRepository=new StudentRepository();
        String username = request.substring(3);

        User user=userRepository.getByUsername(username);
        Student student=studentRepository.getById(user.getId());

        System.out.println("Giving credentials");
        String response;
        String location;
        if(student.getCamin() == null || student.getCamera() == null){ //if a student has no room then location is set accordingly
            location = "Fara cazare";
        }
        else{
            location = student.getCamin().getName()+" camera "+student.getCamera().getId();
        }

        response=student.getNume()+" "+student.getPrenume()+","+location+","+student.getNrMatricol();

        return response;
    }

    private String changePassword(String request) { //Changes the password for the current user
                                                    // like: "cp:username,new_password"
        UserRepository userRepository = new UserRepository();
        request = request.substring(3);
        String[] credentials = request.split(",");
        String username = credentials[0];
        String pass = credentials[1];
        User user = userRepository.getByUsername(username);
        userRepository.setPassword(user, pass);
        return "Done";
    }

    private String loginCredentials(String request) { //Tries to authenticate and authorize the client
        request = request.substring(2);
        String[] credentials = request.split("\\|");
        String username;
        String password;
        if (credentials.length == 0) {
            username = null;
            password = null;
        } else if (credentials.length == 1) {
            username = credentials[0];
            password = null;
        } else {
            username = credentials[0];
            password = credentials[1];
        }

        if (username == null || username.isEmpty()) {
            return "ILU"; //Tells the client "INVALID LOGIN USER" if no username was passed
        }
        if (isValidUser(username, password)) {
            if (isSuperUser(username)) {
                System.out.println("Admin State:");
                return "VLA"; ////Tells the client "VALID LOGIN ADMIN"
            }
            System.out.println("Client State:");
            return "VLU"; ////Tells the client "VALID LOGIN USER"
        }
        return "ILU"; //Tells the client "INVALID LOGIN USER"
    }

    private boolean isSuperUser(String username) { //Determines if user is a superuser
        UserRepository userRepository=new UserRepository();
        User user=userRepository.getByUsername(username);

        return user.getSuperUse();
    }

    private boolean isValidUser(String username, String password) { //Determines if user is present in DB
        UserRepository userRepository = new UserRepository();
        return userRepository.verifyLogin(username, password);

    }

    //OUTDATED
    private String addUser() throws IOException {
        sendResponse("Provide username:");
        String name = readFromClient();
        sendResponse("Provide password:");
        String surname = readFromClient();
        return "Added";
    }

    private String readFromClient() throws IOException { //Reads message from client through socket
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return in.readLine();
    }

    private void sendResponse(String response) throws IOException { //Sends message to client through socket
        if (response.equals("nores")) return;
        response += "*";
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.print(response);
        out.flush();
    }

    //OUTDATED
    private String login() throws IOException {
        sendResponse("Provide username:");
        String username = readFromClient();
        if (username.trim().equals("admin")) {
            requestDecoder.setState(new AdminState());
        } else {
            requestDecoder.setState(new ClientState());
        }
        return "Logged in!";
    }

    //OUTDATED
    private String logout() throws IOException {
        requestDecoder.setState(new LoginState());
        return "Logged out!";
    }
    //OUTDATED
    private String launchInterface() {
        return "Launching app...";
    }

    private String sendFile(String path) throws IOException { //Sends a desired file to client through socket
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        File myFile = new File(path);
        byte[] byteArray = new byte[(int) myFile.length()];
        fileInputStream = new FileInputStream(myFile);
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        bufferedInputStream.read(byteArray, 0, byteArray.length);
        bufferedInputStream.close();
        outputStream = socket.getOutputStream();
        outputStream.write(byteArray, 0, byteArray.length);
        outputStream.flush();
        return "nores";
    }

}
