module com.example.studentaccomodation {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

//    opens com.example.studentaccomodation to javafx.fxml;
//    exports com.example.studentaccomodation;
    exports com.example.studentaccomodation;
    opens com.example.studentaccomodation to javafx.fxml;
    exports com.example.studentaccomodation.UI;
    opens com.example.studentaccomodation.UI to javafx.fxml;
}