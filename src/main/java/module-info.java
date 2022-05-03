module pl.polsl.biai {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.opencsv;


    opens pl.polsl.biai to javafx.fxml,java.sql;
    exports pl.polsl.biai;
    opens pl.polsl.biai.controllers to javafx.fxml,java.sql;
    exports pl.polsl.biai.controllers;
    opens pl.polsl.biai.models to javafx.fxml,java.sql;
    exports pl.polsl.biai.models;
}