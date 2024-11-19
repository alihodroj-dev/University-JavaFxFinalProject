module com.example.javafxfinalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javafxfinalproject to javafx.fxml;
    exports com.example.javafxfinalproject;
}