module com.example.javafxfinalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxfinalproject to javafx.fxml;
    exports com.example.javafxfinalproject;
}