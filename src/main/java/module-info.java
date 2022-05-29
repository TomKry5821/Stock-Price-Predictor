module pl.polsl.biai {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.opencsv;


    opens pl.polsl.biai to javafx.fxml,java.sql;
    exports pl.polsl.biai;
    opens pl.polsl.biai.controller to javafx.fxml,java.sql;
    exports pl.polsl.biai.controller;
    opens pl.polsl.biai.model to javafx.fxml,java.sql;
    exports pl.polsl.biai.model;
    exports pl.polsl.biai.model.activationfunction;
    opens pl.polsl.biai.model.activationfunction to java.sql, javafx.fxml;
    exports pl.polsl.biai.builder;
    opens pl.polsl.biai.builder to java.sql, javafx.fxml;
    exports pl.polsl.biai.learningmethod;
    opens pl.polsl.biai.learningmethod to java.sql, javafx.fxml;
    exports pl.polsl.biai.normalizationmethod;
    opens pl.polsl.biai.normalizationmethod to java.sql, javafx.fxml;
    exports pl.polsl.biai.model.data;
    opens pl.polsl.biai.model.data to java.sql, javafx.fxml;
}