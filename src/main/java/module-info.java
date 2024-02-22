module com.example.aps {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.aps to javafx.fxml;
    exports com.example.aps;
    exports com.example.aps.controllers;
    opens com.example.aps.controllers to javafx.fxml;
}